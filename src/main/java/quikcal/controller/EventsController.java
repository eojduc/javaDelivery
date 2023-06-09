package quikcal.controller;

import jakarta.validation.constraints.NotNull;
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
import quikcal.database.EventTable;
import quikcal.model.Event;

@RestController
@RequestMapping("/events")
public class EventsController implements Controller {
  private final EventTable events;

  @Autowired
  public EventsController(@NotNull EventTable events) {
    this.events = events;
  }

  @GetMapping("{projectId}")
  List<Event> list(@PathVariable String projectId) throws IOException {
    return events.list(projectId);
  }

  @GetMapping("{projectId}/{eventId}")
  Event get(@PathVariable String projectId, @PathVariable String eventId)
      throws IOException {
    return events.get(projectId, eventId);
  }

  @PatchMapping("{projectId}/{eventId}")
  Event patch(@PathVariable String projectId, @PathVariable String eventId,
      @RequestBody Event event) throws IOException {
    return events.update(projectId, eventId, event);
  }

  @PostMapping("{calendarId}")
  Event post(@PathVariable String calendarId, @RequestBody Event event)
      throws IOException {
    return events.insert(calendarId, event);
  }

  @DeleteMapping("{projectId}/{eventId}")
  String delete(@PathVariable String projectId, @PathVariable String eventId) throws IOException {
    events.delete(projectId, eventId);
    return "Event deleted";
  }
}
