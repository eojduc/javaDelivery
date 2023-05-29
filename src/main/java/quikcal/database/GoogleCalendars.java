package quikcal.database;

import com.google.api.services.calendar.model.CalendarListEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import quikcal.model.Calendar;

public class GoogleCalendars implements Calendars {

  private final com.google.api.services.calendar.Calendar service;

  public GoogleCalendars(com.google.api.services.calendar.Calendar service) {
    this.service = service;
  }

  private static Calendar toCalendar(
      com.google.api.services.calendar.model.Calendar googleCalendar) {
    return new Calendar(googleCalendar.getSummary(), googleCalendar.getId());
  }

  private static com.google.api.services.calendar.model.Calendar toGoogleCalendar(
      Calendar calendar) {
    com.google.api.services.calendar.model.Calendar googleCalendar = new com.google.api.services.calendar.model.Calendar();
    googleCalendar.setSummary(calendar.summary());
    googleCalendar.setId(calendar.id());
    return googleCalendar;
  }

  @Override
  public Calendar insert(Calendar calendar) throws IOException {
    com.google.api.services.calendar.model.Calendar googleCalendar = service.calendars()
        .insert(toGoogleCalendar(calendar)).execute();
    CalendarListEntry calendarListEntry = new CalendarListEntry();
    calendarListEntry.setId(googleCalendar.getId());
    service.calendarList().insert(calendarListEntry).execute();
    return toCalendar(googleCalendar);

  }
  @Override
  public Calendar get(String calendarId) throws IOException {
    return toCalendar(service.calendars().get(calendarId).execute());
  }

  @Override
  public Calendar update(String calendarId, Calendar calendar) throws IOException {
    service.calendars().update(calendarId, toGoogleCalendar(calendar)).execute();
    return calendar;
  }

  @Override
  public List<Calendar> list() throws IOException {
    List<Calendar> calendars = new ArrayList<>();
    for (CalendarListEntry calendarListEntry : service.calendarList().list().execute().getItems()) {
      calendars.add(toCalendar(service.calendars().get(calendarListEntry.getId()).execute()));
    }
    return calendars;
  }

  @Override
  public void delete(String calendarId) throws IOException {
    service.calendars().delete(calendarId).execute();
  }
}
