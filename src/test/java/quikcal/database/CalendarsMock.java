package quikcal.database;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import quikcal.model.Calendar;

public class CalendarsMock implements Calendars {
  private final List<Calendar> calendars;
  private int count;
  public CalendarsMock() {
    count = 0;
    this.calendars = new ArrayList<>();
  }

  @Override
  public Calendar insert(Calendar calendar) {
    calendars.add(new Calendar(calendar.summary(), Integer.toString(count++)));
    return calendar;
  }

  @Override
  public Calendar get(String calendarId) {
    return calendars.stream().filter(calendar -> calendar.id().equals(calendarId)).findFirst().orElse(null);
  }

  @Override
  public Calendar update(String calendarId, Calendar element) {
    List<Calendar> calendarList = calendars.stream().map(calendar -> {
      if (calendar.id().equals(calendarId)) {
        return element;
      }
      return calendar;
    }).collect(Collectors.toList());
    calendars.clear();
    calendars.addAll(calendarList);
    return null;
  }

  @Override
  public void delete(String calendarId) {
    calendars.removeIf(calendar -> calendar.id().equals(calendarId));
  }

  @Override
  public List<Calendar> list() {
    return calendars;
  }
}
