package code.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<UUID> handle(IllegalArgumentException ex) {
    UUID uuid = UUID.randomUUID();
    log.error("IllegalArgumentException: {} UUID: {}", ex, uuid);
    return new ResponseEntity<>(uuid, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PropertyReferenceException.class)
  public ResponseEntity<UUID> handle(PropertyReferenceException ex) {
    UUID uuid = UUID.randomUUID();
    log.error("PropertyReferenceException: {} UUID: {}", ex, uuid);
    return new ResponseEntity<>(uuid, HttpStatus.BAD_REQUEST);
  }


}