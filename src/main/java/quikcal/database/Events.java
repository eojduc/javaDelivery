package quikcal.database;

import java.util.List;
import quikcal.model.Event;

public interface Events {

  Event insert(String calendarId, Event event) throws Exception;

  Event get(String calendarId, String eventId) throws Exception;

  Event update(String calendarId, String eventId, Event event) throws Exception;

  void delete(String calendarId, String eventId) throws Exception;

  List<Event> list(String calendarId) throws Exception;

}
