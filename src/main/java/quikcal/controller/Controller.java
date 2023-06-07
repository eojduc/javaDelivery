package quikcal.controller;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides default exception handling for all controllers.
 */
@RestController
public interface Controller {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  default Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  @ExceptionHandler(GoogleJsonResponseException.class)
  default String handleIOExceptions(IOException exception) {
    return "Failed to connect to database: " + exception.getMessage();
  }

}
