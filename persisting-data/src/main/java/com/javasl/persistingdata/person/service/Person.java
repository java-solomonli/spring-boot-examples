package com.javasl.persistingdata.person.service;

import com.javasl.persistingdata.address.service.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Person {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private UUID id;
  @Column(nullable = false)
  private String givenNames;
  @Column(nullable = false)
  private String surname;
  @Column
  private LocalDate dateOfBirth;
  @OneToMany
  private List<Address> addresses;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getGivenNames() {
    return givenNames;
  }

  public void setGivenNames(String givenNames) {
    this.givenNames = givenNames;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(id, person.id) && Objects.equals(givenNames,
        person.givenNames) && Objects.equals(surname, person.surname)
        && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(
        addresses, person.addresses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, givenNames, surname, dateOfBirth, addresses);
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", givenNames='" + givenNames + '\'' +
        ", surname='" + surname + '\'' +
        ", dateOfBirth='" + dateOfBirth + '\'' +
        ", addresses=" + addresses +
        '}';
  }

  public static final class Builder {

    private UUID id;
    private String givenNames;
    private String surname;
    private LocalDate dateOfBirth;
    private List<Address> addresses;

    private Builder() {
    }

    public static Builder aPerson() {
      return new Builder();
    }

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder givenNames(String givenNames) {
      this.givenNames = givenNames;
      return this;
    }

    public Builder surname(String surname) {
      this.surname = surname;
      return this;
    }

    public Builder dateOfBirth(LocalDate dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    public Builder addresses(List<Address> addresses) {
      this.addresses = addresses;
      return this;
    }

    public Person build() {
      Person person = new Person();
      person.setId(id);
      person.setGivenNames(givenNames);
      person.setSurname(surname);
      person.setDateOfBirth(dateOfBirth);
      person.setAddresses(addresses);
      return person;
    }
  }
}
