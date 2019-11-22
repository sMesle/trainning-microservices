package com.example.restaurant.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

public class Restaurant extends BaseEntity<String> {

    @Setter
    @Getter
    private Optional<List<Table>> tables;
    @Setter
    @Getter
    private Address address;

    public Restaurant(String name, String id, Address address, Optional<List<Table>> tables) {
        super(id, name);
        this.address = address;
        this.tables = tables;
    }

    private Restaurant(String name, String id) {
        super(id, name);
        this.tables = Optional.empty();
    }

    public static Restaurant getDummyRestaurant() {
        return new Restaurant(null, null);
    }

    @Override
    public String toString() {
        return String.format("{id: %s, name: %s, address: %s, tables: %s}", this.getId(),
                this.getName(), this.getAddress(), this.getTables());
    }


}
