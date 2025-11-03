package com.javasl.restwithjunittests.person.service;

import com.javasl.restwithjunittests.errors.ResourceNotFoundException;
import com.javasl.restwithjunittests.person.PersonDto;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private final PersonRepo personRepo;

  public PersonService(PersonRepo personRepo) {
    this.personRepo = personRepo;
  }

  public PersonDto insert(PersonDto person) {
    var personEntity = Person.Builder.aPerson().givenNames(person.givenNames())
        .surname(person.surname()).build();
    personEntity = personRepo.save(personEntity);
    return toDto(personEntity);
  }

  public PersonDto edit(Integer id, PersonDto person) {
    var personEntity = personRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Person", id.toString()));
    personEntity.setGivenNames(person.givenNames());
    personEntity.setSurname(person.surname());
    personEntity = personRepo.save(personEntity);
    return toDto(personEntity);
  }

  public PersonDto getPerson(Integer id) {
    var personEntity = personRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Person", id.toString()));
    return toDto(personEntity);
  }

  public void delete(Integer id) {
    personRepo.deleteById(id);
  }

  public List<PersonDto> getAll() {
    var persons = personRepo.findAllByOrderBySurname().stream().map(this::toDto)
        .collect(Collectors.toList());
    return persons;
  }

  private PersonDto toDto(Person person) {
    return new PersonDto(person.getId(), person.getGivenNames(), person.getSurname());
  }
}
