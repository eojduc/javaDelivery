package quikcal.controller;

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
import quikcal.model.Event;

@RestController
@RequestMapping("/events")
public class EventsController implements Controller {

  @Autowired
  private Database database;

  @GetMapping("{calendarId}")
  List<Event> list(@PathVariable String calendarId) throws IOException {
    return this.database.events().list(calendarId);
  }

  @GetMapping("{calendarId}/{eventId}")
  Event get(@PathVariable String calendarId, @PathVariable String eventId)
      throws IOException {
    return this.database.events().get(calendarId, eventId);
  }

  @PatchMapping("{calendarId}/{eventId}")
  Event patch(@PathVariable String calendarId, @PathVariable String eventId,
      @RequestBody Event event) throws IOException {
    return this.database.events().update(calendarId, eventId, event);
  }

  @PostMapping("{calendarId}")
  Event post(@PathVariable String calendarId, @RequestBody Event event)
      throws IOException {
    return this.database.events().insert(calendarId, event);
  }

  @DeleteMapping("{calendarId}/{eventId}")
  String delete(@PathVariable String calendarId, @PathVariable String eventId) throws IOException {
    this.database.events().delete(calendarId, eventId);
    return "Event deleted";
  }
}
