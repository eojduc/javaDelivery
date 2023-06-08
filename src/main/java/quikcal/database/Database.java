package quikcal.database;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import quikcal.model.Calendar;
import quikcal.model.Event;
import quikcal.model.User;

/**
 * Represents a database that stores calendars and events.
 */
public interface Database {

  CalendarTable calendars();

  EventTable events();

  UserTable users();

  interface EventTable {

    Event insert(String calendarId, Event event) throws IOException;

    Event get(String calendarId, String eventId) throws IOException;

    Event update(String calendarId, String eventId, Event event) throws IOException;

    void delete(String calendarId, String eventId) throws IOException;

    List<Event> list(String calendarId) throws IOException;

  }
  interface CalendarTable {

    Calendar insert(Calendar request) throws IOException;

    Calendar get(String calendarId) throws IOException;

    Calendar update(String calendarId, Calendar element) throws IOException;

    void delete(String calendarId) throws IOException;

    List<Calendar> list() throws IOException;
  }

  interface UserTable {

      User insert(User user) throws IOException;

      User get(String userId) throws IOException;

      User update(String userId, User element) throws IOException;

      void delete(String userId) throws IOException;

      List<User> list() throws IOException;
  }

  record Config(String type, String credentials) {
  }
}
