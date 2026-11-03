package com.javasl.persistingdata.address.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  @Column(nullable = false)
  private String street;
  @Column(nullable = false)
  private String city;
  @Column(nullable = true)
  private String state;
  @Column(nullable = false)
  private String postcode;
  @Column(nullable = false)
  private String country;
  @Column(nullable = true)
  private String additionalInfo;

  public Address() {
  }

  public Address(UUID id, String street, String city, String state, String postcode,
      String country, String additionalInfo) {
    this.id = id;
    this.street = street;
    this.city = city;
    this.state = state;
    this.postcode = postcode;
    this.country = country;
    this.additionalInfo = additionalInfo;
  }

  public UUID getId() {
    return id;
  }
  public void setId(UUID id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(String additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(id, address.id) && Objects.equals(street,
        address.street) && Objects.equals(city, address.city) && Objects.equals(
        state, address.state) && Objects.equals(postcode, address.postcode)
        && Objects.equals(country, address.country) && Objects.equals(
        additionalInfo, address.additionalInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, street, city, state, postcode, country, additionalInfo);
  }

  @Override
  public String toString() {
    return "Address{" +
        "id='" + id + '\'' +
        ", street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", postcode='" + postcode + '\'' +
        ", country='" + country + '\'' +
        ", additionalInfo='" + additionalInfo + '\'' +
        '}';
  }


  public static final class Builder {

    private UUID id;
    private String street;
    private String city;
    private String state;
    private String postcode;
    private String country;
    private String additionalInfo;

    private Builder() {
    }

    public static Builder anAddress() {
      return new Builder();
    }

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder street(String street) {
      this.street = street;
      return this;
    }

    public Builder city(String city) {
      this.city = city;
      return this;
    }

    public Builder state(String state) {
      this.state = state;
      return this;
    }

    public Builder postcode(String postcode) {
      this.postcode = postcode;
      return this;
    }

    public Builder country(String country) {
      this.country = country;
      return this;
    }

    public Builder additionalInfo(String additionalInfo) {
      this.additionalInfo = additionalInfo;
      return this;
    }

    public Address build() {
      Address address = new Address();
      address.setId(id);
      address.setStreet(street);
      address.setCity(city);
      address.setState(state);
      address.setPostcode(postcode);
      address.setCountry(country);
      address.setAdditionalInfo(additionalInfo);
      return address;
    }
  }
}
