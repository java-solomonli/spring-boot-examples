package com.javasl.restwithjunittests.person;

import com.javasl.restwithjunittests.person.service.PersonService;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/persons")
@RestController
public class PersonCtrl {

  private static final Logger log = LoggerFactory.getLogger(PersonCtrl.class);

  private final PersonService personService;

  public PersonCtrl(PersonService personService) {
    this.personService = personService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PersonDto insert(@RequestBody PersonDto person) {
    log.info("insert: person: {}", person);
    return personService.insert(person);
  }

  @GetMapping("/{id}")
  public PersonDto getPerson(@PathVariable Integer id) {
    log.info("get: id: {}", id);
    return personService.getPerson(id);
  }

  @GetMapping
  public List<PersonDto> getAll() {
    log.info("getAll");
    return personService.getAll();
  }

  @PutMapping("/{id}")
  public PersonDto edit(@PathVariable Integer id, @RequestBody PersonDto person) {
    log.info("edit: id: {}, person: {}", id, person);
    return personService.edit(id, person);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Integer id) {
    log.info("delete: id: {}", id);
    personService.delete(id);
  }
}
