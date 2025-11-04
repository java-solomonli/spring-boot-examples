package com.javasl.restwithjunittests.favicon;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconCtrl {

  @RequestMapping(value = "favicon.ico", method = {RequestMethod.GET, RequestMethod.HEAD})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void returnNoFavicon() {
  }

}
