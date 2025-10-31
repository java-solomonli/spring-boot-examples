package javasl.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import javasl.errors.ResourceNotFoundException;
import javasl.person.PersonDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  @InjectMocks
  PersonService personService;

  @Mock
  PersonRepo personRepo;

  @Test
  void insert() {
    PersonDto person = new PersonDto(null, "Daniel Joshua", "Solomon");
    Person personEntity = Person.Builder.aPerson().givenNames("Daniel Joshua").surname("Solomon")
        .build();
    var personEntity2 = Person.Builder.aPerson().id(1).givenNames("Daniel Joshua")
        .surname("Solomon").build();
    when(personRepo.save(personEntity)).thenReturn(personEntity2);

    var p = personService.insert(person);
    assertThat(p).isNotNull();
    assertThat(p).isEqualTo(new PersonDto(1, "Daniel Joshua", "Solomon"));

    verify(personRepo).save(personEntity);
  }

  @Test
  void edit() {
    PersonDto person = new PersonDto(1, "Dan", "Joshua");
    var originalPerson = Person.Builder.aPerson().id(1).givenNames("Daniel Joshua")
        .surname("Solomon").build();
    when(personRepo.findById(1)).thenReturn(Optional.of(originalPerson));
    originalPerson.setGivenNames("Dan");
    originalPerson.setSurname("Joshua");
    when(personRepo.save(originalPerson)).thenReturn(originalPerson);

    var p = personService.edit(1, person);
    assertThat(p).isNotNull();
    assertThat(p).isEqualTo(new PersonDto(1, "Dan", "Joshua"));

    verify(personRepo).findById(1);
    verify(personRepo).save(originalPerson);
  }

  @Test
  void getPerson() {
    var personEntity = Optional.of(
        Person.Builder.aPerson().id(1).givenNames("Daniel Joshua").surname("Solomon")
            .build());
    when(personRepo.findById(1)).thenReturn(personEntity);

    var p = personService.getPerson(1);
    assertThat(p).isNotNull();
    assertThat(p).isEqualTo(new PersonDto(1, "Daniel Joshua", "Solomon"));

    verify(personRepo).findById(1);
  }

  @Test
  void getPersonBadId() {
    when(personRepo.findById(1)).thenThrow(new ResourceNotFoundException("Person", "1"));

    try {
      personService.getPerson(1);
      Assertions.fail("Should have thrown exception");
    } catch (ResourceNotFoundException e) {
      assertThat(e.getId()).isEqualTo("1");
      assertThat(e.getResourceName()).isEqualTo("Person");
    }

    verify(personRepo).findById(1);
  }

  @Test
  void delete() {
    doNothing().when(personRepo).deleteById(1);
    personService.delete(1);
    verify(personRepo).deleteById(1);
  }

  @Test
  void getAll() {
    var persons = Arrays.asList(
        Person.Builder.aPerson().id(1).givenNames("Daniel Joshua").surname("Solomon")
            .build(),
        Person.Builder.aPerson().id(2).givenNames("Hans").surname("Dampf").build());
    when(personRepo.findAllByOrderBySurname()).thenReturn(persons);

    var personsList = personService.getAll();
    assertThat(personsList).isNotNull();
    assertThat(personsList).hasSize(2);
    assertThat(personsList).contains(new PersonDto(1, "Daniel Joshua", "Solomon"));
    assertThat(personsList).contains(new PersonDto(2, "Hans", "Dampf"));

    verify(personRepo).findAllByOrderBySurname();
  }
}
