package com.example.restaurant.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Entity<T> {

    T id;
    String name;

}
