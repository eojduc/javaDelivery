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
import quikcal.model.Event;

@RestController
public class EventsController {

  private final Database database;

  public EventsController() throws Exception {
    this.database = new GoogleDatabase();
  }

  @GetMapping("/events/{calendarId}")
  List<Event> events(@PathVariable String calendarId) {
    try {
      return this.database.events().list(calendarId);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/events/{calendarId}/{eventId}")
  Event event(@PathVariable String calendarId, @PathVariable String eventId) {
    try {
      return this.database.events().get(calendarId, eventId);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @PatchMapping("/events/{calendarId}/{eventId}")
  Event eventPatch(@PathVariable String calendarId, @PathVariable String eventId,
      @RequestBody Event event) {
    try {
      return this.database.events().update(calendarId, eventId, event);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping("/events/{calendarId}")
  Event eventPost(@PathVariable String calendarId, @RequestBody Event event) {
    try {
      return this.database.events().insert(calendarId, event);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @DeleteMapping("/events/{calendarId}/{eventId}")
  String eventDelete(@PathVariable String calendarId, @PathVariable String eventId) {
    try {
      this.database.events().delete(calendarId, eventId);
      return "Event deleted";
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}
