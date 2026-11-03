package com.javasl.persistingdata.person.service;

import com.javasl.persistingdata.address.service.AddressMapper;
import com.javasl.persistingdata.person.PersonDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PersonMapper {

  private final AddressMapper addressMapper;
  private final Predicate<String> hasText = StringUtils::hasText;

  public PersonMapper(AddressMapper addressMapper) {
    this.addressMapper = addressMapper;
  }

  public PersonDto toDto(Person person) {
    var addresses = addressMapper.toDtoList(person.getAddresses());
    return new PersonDto(
        Optional.ofNullable(person.getId()).map(UUID::toString).orElse(null),
        person.getGivenNames(),
        person.getSurname(),
        person.getDateOfBirth(),
        addresses);
  }

  public List<PersonDto> toDtoList(List<Person> persons) {
    if (persons == null || persons.isEmpty()) {
      return List.of();
    }
    return persons.stream().map(this::toDto).toList();
  }

  public Person toPerson(PersonDto personDto) {
    var addresses = addressMapper.toAddressList(personDto.addresses());
    var id = Optional.ofNullable(personDto.id()).filter(hasText).map(UUID::fromString).orElse(null);
    return Person.Builder.aPerson()
        .id(id)
        .givenNames(personDto.givenNames())
        .surname(personDto.surname())
        .dateOfBirth(personDto.dateOfBirth())
        .addresses(addresses)
        .build();
  }
}
