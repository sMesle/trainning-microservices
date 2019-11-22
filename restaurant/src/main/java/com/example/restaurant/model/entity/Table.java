package com.example.restaurant.model.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

public class Table extends BaseEntity<Integer> {

    @Getter
    @Setter
    private int capacity;

    public Table(@JsonProperty("name") String name, @JsonProperty("id") int id,
                 @JsonProperty("capacity") int capacity) {
        super(id, name);
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, name: %s, capacity: %s}",
                this.getId(), this.getName(), this.getCapacity());
    }
}
