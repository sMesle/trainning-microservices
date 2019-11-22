package com.example.restaurant.valueobject;

import com.example.restaurant.model.entity.Address;
import com.example.restaurant.model.entity.Table;

import java.util.List;
import java.util.Optional;

public class RestaurantVO {
    private Optional<List<Table>> tables = Optional.empty();
    private String name;
    private String id;
    private Address address;

    public RestaurantVO() {}

    public Address getAddress() {
        return address;
    }

    public Optional<List<Table>> getTables() {
        return tables;
    }

    public void setTables(Optional<List<Table>> tables) {
        this.tables = tables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, name: %s, address: %s %s %s %s, tables: %s}", this.getId(),
                this.getName(),
                this.getAddress().getNumber(),
                this.getAddress().getStreet(),
                this.getAddress().getZipcode(),
                this.getAddress().getCountry(),
                this.getTables());
    }
}
