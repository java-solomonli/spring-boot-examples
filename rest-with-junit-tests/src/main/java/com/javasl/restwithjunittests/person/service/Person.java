package com.javasl.restwithjunittests.person.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String givenNames;

  @Column(nullable = false)
  private String surname;

  public Person() {
  }

  public Person(Integer id, String givenNames, String surname) {
    this.id = id;
    this.givenNames = givenNames;
    this.surname = surname;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getGivenNames() {
    return givenNames;
  }

  public void setGivenNames(String givennames) {
    this.givenNames = givennames;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(id, person.id) && Objects.equals(givenNames,
        person.givenNames) && Objects.equals(surname, person.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, givenNames, surname);
  }

  @Override
  public String toString() {
    return "Person{" + "id=" + id + ", givennames='" + givenNames + '\'' + ", surname='" + surname
        + '\'' + '}';
  }

  public static final class Builder {

    private Integer id;
    private String givenNames;
    private String surname;

    private Builder() {
    }

    public static Builder aPerson() {
      return new Builder();
    }

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder givenNames(String givennames) {
      this.givenNames = givennames;
      return this;
    }

    public Builder surname(String surname) {
      this.surname = surname;
      return this;
    }

    public Person build() {
      return new Person(id, givenNames, surname);
    }
  }
}
