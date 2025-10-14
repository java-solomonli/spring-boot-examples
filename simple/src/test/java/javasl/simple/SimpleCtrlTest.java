package javasl.simple;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(SimpleCtrl.class)
public class SimpleCtrlTest {

  final static String PING_URL = "/ping";

  @Autowired
  MockMvc ctrl;

  @Test
  void ping() throws Exception {
    ctrl.perform(MockMvcRequestBuilders.get(PING_URL)).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=UTF-8"))
        .andExpect(content().string("ping"));
  }

  @Test
  void negativePing() throws Exception {
    ctrl.perform(MockMvcRequestBuilders.get("/pong")).andDo(print())
        .andExpect(status().isNotFound());
  }
}
