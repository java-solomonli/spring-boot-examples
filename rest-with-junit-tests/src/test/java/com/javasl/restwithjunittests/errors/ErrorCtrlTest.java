package com.javasl.restwithjunittests.errors;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.servlet.RequestDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
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
    var expected = new ErrorDto(
        HttpStatus.BAD_REQUEST.value(),
        "/wrong/path",
        "Bad request(Exception message: test",
        "please check your request");

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode().value()).isEqualTo(400);
    assertThat(response.getBody()).isEqualTo(expected);
  }

  @Test
  void handleUnexpectedError() {
    var request = new MockHttpServletRequest();
    request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, "/path/ok");
    request.setAttribute(RequestDispatcher.ERROR_MESSAGE, "error");
    request.setAttribute(RequestDispatcher.ERROR_EXCEPTION,
        new RuntimeException("Fehler unexpected error"));

    var response = errorCtrl.handleError(request);
    var expected = new ErrorDto(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "/path/ok",
        "error(Exception message: Fehler unexpected error",
        "please check your request"
    );
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode().value()).isEqualTo(500);
    assertThat(response.getBody()).isEqualTo(expected);
  }
}
