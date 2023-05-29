package quikcal.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import quikcal.database.Database;
import quikcal.database.GoogleDatabase;
import quikcal.model.Calendar;


@RestController
public class CalendarsController {

  private final Database database;

  CalendarsController() throws Exception {
    this.database = new GoogleDatabase();
  }

  @GetMapping("/calendars")
  List<Calendar> calendars() {
    try {
      return database.calendars().list();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/calendars/{id}")
  Calendar calendarEvent(@PathVariable String id) {
    try {
      return database.calendars().get(id);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @DeleteMapping("/calendars/{id}")
  String calendarDelete(@PathVariable String id) {
    try {
      database.calendars().delete(id);
      return "Calendar deleted";
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @PatchMapping("/calendars/{id}")
  Calendar calendarPatch(@PathVariable String id, @RequestBody Calendar calendar) {
    try {
      return database.calendars().update(id, calendar);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping("/calendars")
  Calendar calendarPost(@RequestBody Calendar calendar) {
    try {
      return database.calendars().insert(calendar);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
