package quikcal.controller;

import jakarta.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
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
import quikcal.model.Calendar;


@RestController
public class CalendarsController {

  private static final String PATH = "/calendars";
  private final Database database;

  CalendarsController()
      throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    this.database = Database.create(Application.config());
  }

  @GetMapping(PATH)
  List<Calendar> calendars() throws IOException {
    return database.calendars().list();
  }

  @PostMapping(PATH)
  Calendar calendarPost(@Valid @RequestBody Calendar calendar) throws IOException {
    return database.calendars().insert(calendar);
  }

  @GetMapping(PATH + "/{calendarId}")
  Calendar calendarEvent(@PathVariable String calendarId) throws IOException {
    return database.calendars().get(calendarId);
  }

  @DeleteMapping(PATH + "/{calendarId}")
  String calendarDelete(@PathVariable String calendarId) throws IOException {
    database.calendars().delete(calendarId);
    return "Calendar deleted";
  }

  @PatchMapping(PATH + "/{calendarId}")
  Calendar calendarPatch(@PathVariable String calendarId, @Valid @RequestBody Calendar calendar) throws IOException {
    return database.calendars().update(calendarId, calendar);
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
