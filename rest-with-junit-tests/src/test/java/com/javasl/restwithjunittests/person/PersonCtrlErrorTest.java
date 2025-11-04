package com.javasl.restwithjunittests.person;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.javasl.restwithjunittests.errors.ResourceNotFoundException;
import com.javasl.restwithjunittests.person.service.PersonService;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@WebMvcTest(controllers = {PersonCtrl.class})
public class PersonCtrlErrorTest {

  final static String URL = "/v1/persons";
  final static String PATH = "src/test/resources/person/";

  @Autowired
  MockMvc ctrl;

  @MockitoBean
  PersonService personService;

  @Test
  void getNotFoundError() throws Exception {

    var path1 = Paths.get(PATH + "idNotFound.json");
    var response = Files.readString(path1);
    when(personService.getPerson(1)).thenThrow(new ResourceNotFoundException("Person", "1"));

    ctrl.perform(get(URL + "/{id}", 1))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(response));
  }

  @Test
  void invalidId() throws Exception {

    var path1 = Paths.get(PATH + "invalidId.json");
    var response = Files.readString(path1);
    var invalidId = "invalidId";

    ctrl.perform(get(URL + "/{id}", invalidId))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(response));
  }

  @Test
  void invalidPath() throws Exception {

    var path1 = Paths.get(PATH + "invalidPath.json");
    var response = Files.readString(path1);

    ctrl.perform(get(URL + "/a/a"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(response));
  }

  @Test
  void resourceNotFound() throws Exception {

    var path1 = Paths.get(PATH + "resourceNotFound.json");
    var response = Files.readString(path1);

    when(personService.getPerson(1)).thenThrow(
        new RuntimeException(new NoResourceFoundException(HttpMethod.GET, "favicon")));

    ctrl.perform(get(URL + "/1"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(response));
  }

  @Test
  void methodNotSupported() throws Exception {

    var path1 = Paths.get(PATH + "methodNotSupported.json");
    var response = Files.readString(path1);

    ctrl.perform(post(URL + "/1"))
        .andExpect(status().isMethodNotAllowed())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(response));
  }
}
