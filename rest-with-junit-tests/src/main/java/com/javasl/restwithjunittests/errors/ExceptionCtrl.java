package com.javasl.restwithjunittests.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionCtrl {

  private static final Logger log = LoggerFactory.getLogger(ExceptionCtrl.class);

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorDto> handleNoResourceResolver(NoHandlerFoundException e) {
    log.warn("handleBadPath: {}", e.getMessage());

    var error = new ErrorDto(HttpStatus.NOT_FOUND.value(), e.getRequestURL(),
        e.getMessage(),
        "use a valid path");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorDto> handleNoResourceFoundException(NoResourceFoundException e) {
    log.warn("handleBadPath: {}", e.getMessage());

    var error = new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getResourcePath(),
        e.getMessage(),
        "use a valid path");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorDto> handleBadPath(MethodArgumentTypeMismatchException e) {
    log.warn("handleBadPath: {}", e.getMessage());

    var error = new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getName() + e.getParameter(),
        e.getMessage(),
        "use a valid id");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDto> handleResourceNotFound(ResourceNotFoundException e) {
    log.warn("handleResourceNotFound: id: {}, resource: {}", e.getId(), e.getResourceName());

    var error = new ErrorDto(HttpStatus.NOT_FOUND.value(), e.getId(),
        "resource does not exist", "provide a valid id for " + e.getResourceName());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
