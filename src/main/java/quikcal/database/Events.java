package quikcal.database;

import java.io.IOException;
import java.util.List;
import quikcal.model.Event;

public interface Events {

  Event insert(String calendarId, Event event) throws IOException;

  Event get(String calendarId, String eventId) throws IOException;

  Event update(String calendarId, String eventId, Event event) throws IOException;

  void delete(String calendarId, String eventId) throws IOException;

  List<Event> list(String calendarId) throws IOException;

}
