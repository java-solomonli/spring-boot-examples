package com.javasl.persistingdata.person.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person, UUID> {

  @EntityGraph(attributePaths = {"addresses"})
  @Override
  Optional<Person> findById(UUID uuid);

  List<Person> findBySurnameOrderByGivenNames(String surname);
}
