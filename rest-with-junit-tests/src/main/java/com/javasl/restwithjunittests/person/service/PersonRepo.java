package com.javasl.restwithjunittests.person.service;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

  List<Person> findAllBySurnameOrderBySurname(String surname);

  List<Person> findAllByOrderBySurname();
}
