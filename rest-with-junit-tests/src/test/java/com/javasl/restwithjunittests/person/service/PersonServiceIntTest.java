package com.javasl.restwithjunittests.person.service;

import static org.assertj.core.api.Assertions.assertThat;


import com.javasl.restwithjunittests.person.PersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(PersonService.class)
@DataJpaTest
public class PersonServiceIntTest {

  @Autowired
  PersonRepo personRepo;

  @Autowired
  PersonService personService;

  @Test
  void insert() {
    var personDto = new PersonDto(null, "Daniel Joshua", "Solomon");
    var response = personService.insert(personDto);

    assertThat(response).isNotNull();
    assertThat(response.id()).isNotNull();
    assertThat(response.givenNames()).isEqualTo("Daniel Joshua");
    assertThat(response.surname()).isEqualTo("Solomon");
  }

  @Test
  void getAllInCorrectOrder() {
    personRepo.save(
        Person.Builder.aPerson().givenNames("Daniel Joshua").surname("Solomon").build());
    personRepo.save(Person.Builder.aPerson().givenNames("Jan").surname("Wurst").build());
    personRepo.save(Person.Builder.aPerson().givenNames("Hans").surname("Dampf").build());

    var persons = personService.getAll();
    assertThat(persons).isNotNull();
    assertThat(persons).hasSize(3);
    assertThat(persons.get(0).surname()).isEqualTo("Dampf");
    assertThat(persons.get(1).surname()).isEqualTo("Solomon");
    assertThat(persons.get(2).surname()).isEqualTo("Wurst");
  }

  @Test
  void edit() {
    var personEntity = personRepo.save(
        Person.Builder.aPerson().givenNames("Daniel Joshua").surname("Solomon").build());
    var personDto = new PersonDto(null, "Hans", "Dampf");
    var response = personService.edit(personEntity.getId(), personDto);

    assertThat(response).isNotNull();
    assertThat(response.id()).isEqualTo(personEntity.getId());
    assertThat(response.givenNames()).isEqualTo("Hans");
    assertThat(response.surname()).isEqualTo("Dampf");

    personEntity = personRepo.findById(personEntity.getId()).get();
    assertThat(personEntity.getGivenNames()).isEqualTo("Hans");
    assertThat(personEntity.getSurname()).isEqualTo("Dampf");
  }

  @Test
  void delete() {
    var personEntity = personRepo.save(
        Person.Builder.aPerson().givenNames("Daniel Joshua").surname("Solomon").build());
    personService.delete(personEntity.getId());
    assertThat(personRepo.findById(personEntity.getId())).isEmpty();
  }

  @Test
  void getById() {
    var personEntity = personRepo.save(
        Person.Builder.aPerson().givenNames("Daniel Joshua").surname("Solomon").build());
    assertThat(personService.getPerson(personEntity.getId())).isNotNull();
    var person = personService.getPerson(personEntity.getId());

    var personExpected = new PersonDto(personEntity.getId(), "Daniel Joshua", "Solomon");
    assertThat(person).isEqualTo(personExpected);
  }
}
