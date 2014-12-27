package com.learn.model;

import java.util.Date;

public class Person {
 private int id;
 private String firstName;
 private String lastName;
 private Date birthDate;
 private double weightInKilograms;
 private double heightInMeters;

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


}