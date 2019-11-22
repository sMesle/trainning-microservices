package com.example.restaurant.service;

import com.example.restaurant.model.entity.Entity;
import com.example.restaurant.model.entity.Restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface RestaurantService {

    /**
     * @param restaurant
     */
    public void add(Restaurant restaurant) throws Exception;

    /**
     * @param restaurant
     */
    public void update(Restaurant restaurant) throws Exception;

    /**
     * @param id
     */
    public void delete(String id) throws Exception;

    /**
     * @param restaurantId
     */
    public Entity findById(String restaurantId) throws Exception;

    /**
     * @param name
     */
    public Collection<Restaurant> findByName(String name) throws Exception;

    /**
     * @param name
     */
    public Collection<Restaurant> findByCriteria(Map<String, ArrayList<String>> name)
            throws Exception;
}
