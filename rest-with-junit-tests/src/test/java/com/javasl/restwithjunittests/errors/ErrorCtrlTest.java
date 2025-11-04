package com.javasl.restwithjunittests.errors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.servlet.RequestDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class ErrorCtrlTest {

  ErrorCtrl errorCtrl = new ErrorCtrl();

  @Test
  void unexpectedError() {
    var request = new MockHttpServletRequest();
    request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 400);
    request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, "/wrong/path");
    request.setAttribute(RequestDispatcher.ERROR_MESSAGE, "Bad request");
    request.setAttribute(RequestDispatcher.ERROR_EXCEPTION,
        new RuntimeException("test"));

    var response = errorCtrl.handleError(request);

    assertEquals(400, response.getStatusCode().value());
    assertEquals("/wrong/path", response.getBody().id());
  }

  @Test
  void handleUnexpectedError() {
    var request = new MockHttpServletRequest();
    request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, "/path/ok");
    request.setAttribute(RequestDispatcher.ERROR_MESSAGE, "error");
    request.setAttribute(RequestDispatcher.ERROR_EXCEPTION,
        new RuntimeException("Fehler unexpected error"));

    var response = errorCtrl.handleError(request);

    assertEquals(500, response.getStatusCode().value());
    assertEquals("/path/ok", response.getBody().id());
  }
}
