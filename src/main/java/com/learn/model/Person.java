package com.learn.model;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

public class Person extends BaseBean{
 private int id;
 private String firstName;
 private String lastName;
 private Date birthDate;
 private double weightInKilograms;
 private double heightInMeters;
    private Address address;

 public Person(PersonBuilder builder) {
     this.firstName = builder.firstName;
     this.lastName = builder.lastName;
     this.birthDate = builder.birthDate;
     this.weightInKilograms = builder.weightInKilograms;
     this.heightInMeters = builder.heightInMeters;
 }

    public Person() {

    }


    public int getId () {
 return id;
 }
 public void setId (int id) {
 this.id = id;
 }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public double getWeightInKilograms() {
    return weightInKilograms;
  }

  public void setWeightInKilograms(double weightInKilograms) {
    this.weightInKilograms = weightInKilograms;
  }

  public double getHeightInMeters() {
    return heightInMeters;
  }

  public void setHeightInMeters(double heightInMeters) {
    this.heightInMeters = heightInMeters;
  }

    public static class PersonBuilder {

        private int id;
        private String firstName;
        private String lastName;
        private Date birthDate;
        private double weightInKilograms;
        private double heightInMeters;

        public PersonBuilder(int id, String firstName, String lastName, Date birthDate) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDate = birthDate;
        }

        public PersonBuilder weightInKilograms(double weightInKilograms) {
            this.weightInKilograms = weightInKilograms;
            return this;
        }

        public PersonBuilder heightInMeters(double heightInMeters) {
            this.heightInMeters = heightInMeters;
            return this;
        }

        public Person build() {
            return new Person(this);
        }

    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).
                add("lastName", this.lastName).
                add("firstName", this.firstName).
                add("id", this.id).toString();
    }
}