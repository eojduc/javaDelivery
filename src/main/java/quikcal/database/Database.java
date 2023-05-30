package quikcal.database;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ResourceBundle;
import quikcal.Configuration;
import quikcal.model.Calendar;
import quikcal.model.Event;

/**
 * Represents a database that stores calendars and events.
 */
public interface Database {

  /**
   * Creates a database based on the configuration.
   */
  static Database create(Configuration configuration)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    ResourceBundle bundle = ResourceBundle.getBundle(Database.class.getSimpleName());
    return Class.forName(bundle.getString(configuration.database().type()))
        .asSubclass(Database.class)
        .getConstructor(Configuration.class).newInstance(configuration);
  }

  CalendarTable calendars();

  EventTable events();

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
}
