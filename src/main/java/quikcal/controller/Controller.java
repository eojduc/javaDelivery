package quikcal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * static methods common amongst controllers
 */

public interface Controller {

  static Map<String, String> handleValidationException(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  static String handleIOException(IOException exception) {
    return "Failed to connect to database: " + exception.getMessage();
  }

}
