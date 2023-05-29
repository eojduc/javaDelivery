package quikcal.database;

import com.google.api.client.util.DateTime;
import java.time.Instant;
import java.util.List;
import quikcal.model.Event;

public class GoogleEvents implements Events {

  private final com.google.api.services.calendar.Calendar service;

  public GoogleEvents(com.google.api.services.calendar.Calendar service) {
    this.service = service;
  }

  private static Event toEvent(com.google.api.services.calendar.model.Event googleEvent) {
    return new Event(googleEvent.getSummary(), googleEvent.getId(),
        Instant.ofEpochMilli(googleEvent.getStart().getDateTime().getValue()),
        Instant.ofEpochMilli(googleEvent.getEnd().getDateTime().getValue()));
  }

  private static com.google.api.services.calendar.model.Event toGoogleEvent(Event event) {
    com.google.api.services.calendar.model.Event googleEvent = new com.google.api.services.calendar.model.Event();
    googleEvent.setStart(new com.google.api.services.calendar.model.EventDateTime().setDateTime(
        new DateTime(event.start().toEpochMilli())));
    googleEvent.setEnd(new com.google.api.services.calendar.model.EventDateTime().setDateTime(
        new DateTime(event.end().toEpochMilli())));
    return googleEvent;
  }

  @Override
  public Event insert(String calendarId, Event event) throws Exception {
    return GoogleEvents.toEvent(service.events().insert(calendarId, toGoogleEvent(event)).execute());
  }

  @Override
  public Event get(String calendarId, String eventId) throws Exception {
    return GoogleEvents.toEvent(service.events().get(calendarId, eventId).execute());
  }

  @Override
  public Event update(String calendarId, String eventId, Event event) throws Exception {
    service.events().update(calendarId, eventId, toGoogleEvent(event)).execute();
    return event;
  }

  @Override
  public void delete(String calendarId, String eventId) throws Exception {
    service.events().delete(calendarId, eventId).execute();
  }

  @Override
  public List<Event> list(String calendarId) throws Exception {
    return service.events().list(calendarId).execute().getItems().stream()
        .map(GoogleEvents::toEvent)
        .toList();
  }
}
