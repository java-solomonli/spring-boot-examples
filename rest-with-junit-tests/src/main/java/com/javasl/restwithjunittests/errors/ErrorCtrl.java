package com.javasl.restwithjunittests.errors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorCtrl implements ErrorController {

  private static final Logger log = LoggerFactory.getLogger(ErrorCtrl.class);

  @RequestMapping("/error")
  public ResponseEntity<ErrorDto> handleError(HttpServletRequest request) {
    var status = (Integer) Optional.ofNullable(
        request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).orElse(500);
    var exception = (Exception) Optional.ofNullable(
            request.getAttribute(RequestDispatcher.ERROR_EXCEPTION))
        .orElse(new Exception("unexpected error"));
    var requestUri = (String) Optional.ofNullable(
        request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).orElse("");
    var message = (String) Optional.ofNullable(
        request.getAttribute(RequestDispatcher.ERROR_MESSAGE)).orElse("unexpected error");

    log.error("--> handleError: status: {}, requestUri: {}, message: {}, exception: {}", status,
        requestUri, message, exception);
    var error = new ErrorDto(
        status,
        requestUri,
        message + "(Exception message: " + exception.getMessage(),
        "please check your request"
    );
    return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(error);
  }
}
