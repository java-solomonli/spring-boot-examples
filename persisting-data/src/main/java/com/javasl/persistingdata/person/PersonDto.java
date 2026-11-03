package com.javasl.persistingdata.person;

import com.javasl.persistingdata.address.AddressDto;
import java.time.LocalDate;
import java.util.List;

public record PersonDto(String id, String givenNames, String surname, LocalDate dateOfBirth, List<AddressDto> addresses
) {
}
