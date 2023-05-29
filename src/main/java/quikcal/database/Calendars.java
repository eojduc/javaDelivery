package quikcal.database;

import java.util.List;
import quikcal.model.Calendar;

public interface Calendars {

  Calendar insert(Calendar request) throws Exception;

  Calendar get(String id) throws Exception;

  Calendar update(String id, Calendar element) throws Exception;

  void delete(String id) throws Exception;

  List<Calendar> list() throws Exception;

}
