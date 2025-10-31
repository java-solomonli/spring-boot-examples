package javasl.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionCtrl {

  private static final Logger log = LoggerFactory.getLogger(ExceptionCtrl.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDto> handleResourceNotFound(ResourceNotFoundException e) {
    log.warn("handleResourceNotFound: id: {}, resource: {}", e.getId(), e.getResourceName());

    var error = new ErrorDto(HttpStatus.NOT_FOUND.value(), e.getId(),
        "resource does not exist", "provide a valid id for " + e.getResourceName());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
