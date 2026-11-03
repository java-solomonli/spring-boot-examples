package com.javasl.persistingdata.address.service;

import com.javasl.persistingdata.address.AddressDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AddressService {
  private final AddressRepo repo;
  private final AddressMapper mapper;

  private Predicate<String> hasText = StringUtils::hasText;

  public AddressService(AddressRepo repo, AddressMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  public List<AddressDto> getAllAddresses() {
    var addresses = repo.findAll(Sort.by(Sort.Direction.ASC, "postcode"));
    return addresses.stream().map(mapper::toDto).toList();
  }

  public AddressDto getAddress(String id) {
    var address = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + id));
    return mapper.toDto(address);
  }

  public AddressDto editAddress(String id, AddressDto addressDto) {
    var entity = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + id));

    Optional.ofNullable(addressDto.street()).filter(hasText).ifPresent(entity::setStreet);
    Optional.ofNullable(addressDto.city()).filter(hasText).ifPresent(entity::setCity);
    Optional.ofNullable(addressDto.postcode()).filter(hasText).ifPresent(entity::setPostcode);
    Optional.ofNullable(addressDto.country()).filter(hasText).ifPresent(entity::setCountry);
    entity.setAdditionalInfo(addressDto.additionalInfo());
    entity.setState(addressDto.state());

    var savedAddress = repo.save(entity);
    return mapper.toDto(savedAddress);
  }

  public AddressDto insert(AddressDto addressDto) {
    var address = repo.save(mapper.toAddress(addressDto));
    return mapper.toDto(address);
  }

  public void delete(String... ids) {
    repo.deleteAllById(List.of(ids));
  }
}
