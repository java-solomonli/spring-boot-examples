package com.javasl.persistingdata.person.service;

import com.javasl.persistingdata.person.PersonDto;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private final PersonRepo repo;
  private final PersonMapper mapper;

  public PersonService(PersonRepo repo, PersonMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  public PersonDto getPersonById(String id) {
    var person = repo.findById(UUID.fromString(id))
        .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + id));
    return mapper.toDto(person);
  }

  public PersonDto insert(PersonDto personDto) {
    var person = repo.save(mapper.toPerson(personDto));
    return mapper.toDto(person);
  }

  public PersonDto edit(String id, PersonDto personDto) {
    var person = repo.findById(UUID.fromString(id))
        .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + id));
    person.setGivenNames(personDto.givenNames());
    person.setSurname(personDto.surname());
    person.setDateOfBirth(personDto.dateOfBirth());
    repo.save(person);
    return mapper.toDto(person);
  }
}
