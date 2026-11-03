package com.javasl.persistingdata.address.service;

import com.javasl.persistingdata.address.AddressDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

  public AddressDto toDto(Address address) {
    var id = Optional.ofNullable(address.getId()).map(UUID::toString).orElse("");
    return new AddressDto(id, address.getStreet(), address.getCity(), address.getState(),
        address.getPostcode(), address.getCountry(), address.getAdditionalInfo());
  }

  public Address toAddress(AddressDto addressDto) {
    var id = Optional.ofNullable(addressDto.id()).map(UUID::fromString).orElse(null);
    return Address.Builder.anAddress()
        .id(id)
        .additionalInfo(addressDto.additionalInfo())
        .city(addressDto.city())
        .country(addressDto.country())
        .postcode(addressDto.postcode())
        .state(addressDto.state())
        .street(addressDto.street()).build();
  }

  public List<Address> toAddressList(List<AddressDto> addressDtos) {
    if (addressDtos == null || addressDtos.isEmpty()) {
      return List.of();
    }
    return addressDtos.stream().map(this::toAddress).toList();
  }

  public List<AddressDto> toDtoList(List<Address> addresses) {
    if (addresses == null || addresses.isEmpty()) {
      return List.of();
    }
    return addresses.stream().map(this::toDto).toList();
  }
}
