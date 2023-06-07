package quikcal.controller;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quikcal.database.Database;
import quikcal.model.Calendar;


@RestController
@RequestMapping("/calendars")
public class CalendarsController implements Controller {
  @Autowired
  private Database database;

  @GetMapping("")
  List<Calendar> list() throws IOException {
    return this.database.calendars().list();
  }

  @PostMapping("")
  Calendar post(@Valid @RequestBody Calendar calendar) throws IOException {
    return this.database.calendars().insert(calendar);
  }

  @GetMapping("{calendarId}")
  Calendar get(@PathVariable String calendarId) throws IOException {
    return this.database.calendars().get(calendarId);
  }

  @DeleteMapping("{calendarId}")
  String delete(@PathVariable String calendarId) throws IOException {
    this.database.calendars().delete(calendarId);
    return "Calendar deleted";
  }

  @PatchMapping("{calendarId}")
  Calendar patch(@PathVariable String calendarId, @Valid @RequestBody Calendar calendar)
      throws IOException {
    return this.database.calendars().update(calendarId, calendar);
  }



}
