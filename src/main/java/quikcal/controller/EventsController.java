package quikcal.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import quikcal.Application;
import quikcal.database.Database;
import quikcal.database.GoogleDatabase;
import quikcal.model.Event;

@RestController
public class EventsController {

  private final Database database;
  private static final String PATH = "/events";

  public EventsController()
      throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    this.database = Database.create(Application.config());
  }

  @GetMapping(PATH + "/{calendarId}")
  List<Event> events(@PathVariable String calendarId) throws IOException {
    return this.database.events().list(calendarId);
  }

  @GetMapping(PATH + "/{calendarId}/{eventId}")
  Event event(@PathVariable String calendarId, @PathVariable String eventId) throws IOException {
    return this.database.events().get(calendarId, eventId);
  }

  @PatchMapping(PATH + "/{calendarId}/{eventId}")
  Event eventPatch(@PathVariable String calendarId, @PathVariable String eventId,
      @RequestBody Event event) throws IOException {
      return this.database.events().update(calendarId, eventId, event);
  }

  @PostMapping(PATH + "/{calendarId}")
  Event eventPost(@PathVariable String calendarId, @RequestBody Event event) throws IOException {
    return this.database.events().insert(calendarId, event);
  }

  @DeleteMapping(PATH + "/{calendarId}/{eventId}")
  String eventDelete(@PathVariable String calendarId, @PathVariable String eventId) throws IOException {
    this.database.events().delete(calendarId, eventId);
    return "Event deleted";
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException exception) {
    return Controller.handleValidationException(exception);
  }

  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  @ExceptionHandler(IOException.class)
  public String handleIOExceptions(IOException exception) {
    return Controller.handleIOException(exception);
  }
}
