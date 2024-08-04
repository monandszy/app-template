package code.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void handleCustomException(IllegalArgumentException ex) {
//    ErrorResponse error = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()).build();
//    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}