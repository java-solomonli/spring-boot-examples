package javasl.simple;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleCtrl {

  @GetMapping("/ping")
  public String ping() {
    return "ping";
  }
}
