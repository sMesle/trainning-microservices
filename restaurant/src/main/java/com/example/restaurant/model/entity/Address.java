package com.example.restaurant.model.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

public class Address extends BaseEntity<String> {

    private int number;
    private String street;
    private int zipcode;
    private String country;

    public Address(String id, String name, int number, String street, int zipcode, String country){
        super(id, name);
        this.number = number;
        this.street = street;
        this.zipcode = zipcode;
        this.country = country;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
