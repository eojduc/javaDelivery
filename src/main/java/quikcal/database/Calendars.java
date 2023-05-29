package quikcal.database;

import java.io.IOException;
import java.util.List;
import quikcal.model.Calendar;

public interface Calendars {

  Calendar insert(Calendar request) throws IOException;

  Calendar get(String calendarId) throws IOException;

  Calendar update(String calendarId, Calendar element) throws IOException;

  void delete(String calendarId) throws IOException;

  List<Calendar> list() throws IOException;

}
