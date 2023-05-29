package quikcal.database;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import quikcal.Application;

/**
 * Represents a database that stores calendars and events.
 */
public interface Database {

  /**
   * Creates a database based on the configuration.
   */
  static Database create(Application.Config config)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    ResourceBundle bundle = ResourceBundle.getBundle(Database.class.getSimpleName());
    return Class.forName(bundle.getString(config.database().type())).asSubclass(Database.class)
        .getConstructor(Application.Config.class).newInstance(config);
  }

  Calendars calendars();

  Events events();


}
