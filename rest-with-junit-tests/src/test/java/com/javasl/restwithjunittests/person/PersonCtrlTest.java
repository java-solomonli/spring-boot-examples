package com.javasl.restwithjunittests.person;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.javasl.restwithjunittests.errors.ExceptionCtrl;
import com.javasl.restwithjunittests.errors.ResourceNotFoundException;
import com.javasl.restwithjunittests.person.service.PersonService;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = PersonCtrl.class)
class PersonCtrlTest {

  final static String URL = "/v1/persons";
  final static String PATH = "src/test/resources/person/";

  @Autowired
  MockMvc ctrl;

  @MockitoBean
  PersonService personService;

  @Autowired
  ExceptionCtrl exceptionCtrl;

  @Test
  void insert() throws Exception {
    var path1 = Paths.get(PATH + "insertPerson.json");
    var path2 = Paths.get(PATH + "insertedPerson.json");
    var insertPerson = Files.readString(path1);
    var response = Files.readString(path2);

    var personToInsert = new PersonDto(null, "Daniel Joshua", "Solomon");
    var personResponse = new PersonDto(1, "Daniel Joshua", "Solomon");
    when(personService.insert(personToInsert)).thenReturn(personResponse);

    ctrl.perform(post(URL).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(insertPerson))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(response));
  }

  @Test
  void getPerson() throws Exception {
    var path1 = Paths.get(PATH + "getPerson.json");
    var response = Files.readString(path1);

    var personResponse = new PersonDto(9, "D J", "S");
    when(personService.getPerson(9)).thenReturn(personResponse);

    ctrl.perform(get(URL + "/{id}", 9).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(response));
  }

  @Test
  void getAll() throws Exception {
    var path1 = Paths.get(PATH + "getAll.json");
    var response = Files.readString(path1);

    var persons = Arrays.asList(
        new PersonDto(9, "D J", "S"),
        new PersonDto(1, "Daniel", "Solomon")
    );

    when(personService.getAll()).thenReturn(persons);

    ctrl.perform(get(URL).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(response));
  }

  @Test
  void edit() throws Exception {
    var path1 = Paths.get(PATH + "editPerson.json");
    var path2 = Paths.get(PATH + "editedPerson.json");
    var editPerson = Files.readString(path1);
    var response = Files.readString(path2);

    var personToEdit = new PersonDto(1, "Daniel Joshua", "Solomon");
    var personResponse = new PersonDto(1, "Daniel Joshua", "Solomon");
    when(personService.edit(1, personToEdit)).thenReturn(personResponse);

    ctrl.perform(put(URL + "/{id}", 1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(editPerson))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(response));
  }

  @Test
  void delete() throws Exception {
    doNothing().when(personService).delete(1);

    ctrl.perform(MockMvcRequestBuilders.delete(URL + "/{id}", 1))
        .andExpect(status().isNoContent());
  }

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
}
