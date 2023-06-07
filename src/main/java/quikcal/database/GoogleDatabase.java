package quikcal.database;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import quikcal.controller.CalendarsController;
import quikcal.database.Database.CalendarTable;
import quikcal.database.Database.EventTable;
import quikcal.model.Calendar;
import quikcal.model.Event;

@Component
public class GoogleDatabase {
  /**
   * Directory to store authorization tokens for this application.
   */
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private final com.google.api.services.calendar.Calendar service;

  public GoogleDatabase() throws Exception {
    final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    Credential credential = GoogleDatabase.getCredentials(httpTransport, "/credentials.json", List.of(CalendarScopes.CALENDAR), jsonFactory);
    this.service = new com.google.api.services.calendar.Calendar.Builder(httpTransport,
        jsonFactory,
        credential).build();
  }

  /**
   * Creates an authorized Credential object.
   */
  private static Credential getCredentials(final NetHttpTransport httpTransport,
      final String credentialsFilePath, final List<String> scopes, final JsonFactory jsonFactory) throws IOException {
    // Load client secrets.
    InputStream in = CalendarsController.class.getResourceAsStream(credentialsFilePath);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
    }
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
        new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport,
        jsonFactory, clientSecrets, scopes).setDataStoreFactory(
            new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH))).setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    //returns an authorized Credential object.
    return credential;
  }

  public CalendarTable calendars() {
    return new CalendarTable() {
      private static Calendar toCalendar(
          final com.google.api.services.calendar.model.Calendar googleCalendar) {
        return new quikcal.model.Calendar(googleCalendar.getSummary(), googleCalendar.getId());
      }
      private static com.google.api.services.calendar.model.Calendar toGoogleCalendar(
          final Calendar calendar) {
        com.google.api.services.calendar.model.Calendar googleCalendar = new com.google.api.services.calendar.model.Calendar();
        googleCalendar.setSummary(calendar.summary());
        googleCalendar.setId(calendar.id());
        return googleCalendar;
      }

      @Override
      public Calendar insert(final Calendar calendar) throws IOException {
        com.google.api.services.calendar.model.Calendar googleCalendar = GoogleDatabase.this.service.calendars()
            .insert(toGoogleCalendar(calendar)).execute();
        com.google.api.services.calendar.model.CalendarListEntry calendarListEntry = new com.google.api.services.calendar.model.CalendarListEntry();
        calendarListEntry.setId(googleCalendar.getId());
        GoogleDatabase.this.service.calendarList().insert(calendarListEntry).execute();
        return toCalendar(googleCalendar);
      }

      @Override
      public Calendar update(final String calendarId, final Calendar calendar)
          throws IOException {
        return toCalendar(
            GoogleDatabase.this.service.calendars().update(calendarId, toGoogleCalendar(calendar)).execute());

      }

      @Override
      public Calendar get(final String calendarId) throws IOException {
        return toCalendar(
            GoogleDatabase.this.service.calendars().get(calendarId).execute());
      }

      @Override
      public List<Calendar> list() throws IOException {
        List<Calendar> calendars = new ArrayList<>();
        for (com.google.api.services.calendar.model.CalendarListEntry calendarListEntry : GoogleDatabase.this.service.calendarList()
            .list().execute().getItems()) {
          calendars.add(toCalendar(
              GoogleDatabase.this.service.calendars().get(calendarListEntry.getId()).execute()));
        }
        return calendars;
      }

      @Override
      public void delete(final String calendarId) throws IOException {
        GoogleDatabase.this.service.calendars().delete(calendarId).execute();
      }
    };
  }

  public EventTable events() {
    return new EventTable() {
      private static Event toEvent(final com.google.api.services.calendar.model.Event googleEvent) {
        Instant start, end;
        if (googleEvent.getStart().getDateTime() != null) {
          start = Instant.ofEpochMilli(googleEvent.getStart().getDateTime().getValue());
          end = Instant.ofEpochMilli(googleEvent.getEnd().getDateTime().getValue());
        } else {
          start = Instant.ofEpochMilli(googleEvent.getStart().getDate().getValue());
          end = Instant.ofEpochMilli(googleEvent.getEnd().getDate().getValue());
        }
        return new Event(googleEvent.getSummary(), googleEvent.getId(), start, end);
      }

      private static com.google.api.services.calendar.model.Event toGoogleEvent(final Event event) {
        com.google.api.services.calendar.model.Event googleEvent = new com.google.api.services.calendar.model.Event();
        googleEvent.setStart(new com.google.api.services.calendar.model.EventDateTime().setDateTime(
            new DateTime(event.start().toEpochMilli())));
        googleEvent.setEnd(new com.google.api.services.calendar.model.EventDateTime().setDateTime(
            new DateTime(event.end().toEpochMilli())));
        return googleEvent;
      }

      @Override
      public Event insert(final String calendarId, final Event event) throws IOException {
        return toEvent(
              GoogleDatabase.this.service.events().insert(calendarId, toGoogleEvent(event)).execute());
      }

      @Override
      public Event get(final String calendarId, final String eventId) throws IOException {
          return toEvent(
              GoogleDatabase.this.service.events().get(calendarId, eventId).execute());
      }

      @Override
      public Event update(final String calendarId, final String eventId, final Event event) throws IOException {
        return toEvent(
              GoogleDatabase.this.service.events().update(calendarId, eventId, toGoogleEvent(event)).execute());
      }

      @Override
      public void delete(final String calendarId, final String eventId) throws IOException {
          GoogleDatabase.this.service.events().delete(calendarId, eventId).execute();
      }

      @Override
      public List<Event> list(final String calendarId) throws IOException {
          return GoogleDatabase.this.service.events().list(calendarId).execute().getItems().stream()
              .map(googleEvent -> toEvent(googleEvent)).toList();
      }
    };
  }
}
