package quikcal.database;

/**
 * Represents a database that stores calendars and events.
 */
public interface Database {

  Calendars calendars();

  Events events();


}
