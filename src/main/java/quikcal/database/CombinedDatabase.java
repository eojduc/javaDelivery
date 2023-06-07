package quikcal.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CombinedDatabase implements Database {

  @Autowired
  private GoogleDatabase googleDatabase;
  @Autowired
  private MongoDatabase mongoDatabase;

  @Override
  public CalendarTable calendars() {
    return googleDatabase.calendars();
  }

  @Override
  public EventTable events() {
    return googleDatabase.events();
  }

  @Override
  public UserTable users() {
    return mongoDatabase.users();
  }
}
