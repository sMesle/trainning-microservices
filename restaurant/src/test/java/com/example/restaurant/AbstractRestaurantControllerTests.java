package com.example.restaurant;

import com.example.restaurant.controller.RestaurantController;
import com.example.restaurant.model.entity.Address;
import com.example.restaurant.model.entity.Entity;
import com.example.restaurant.model.entity.Restaurant;
import com.example.restaurant.valueobject.RestaurantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.logging.Logger;

public abstract class AbstractRestaurantControllerTests {

    /**
     * RESTAURANT ID constant having value 1
     */
    protected static final String RESTAURANT = "1";

    /**
     * RESTAURANT name constant having value Big-O Restaurant
     */
    protected static final String RESTAURANT_NAME = "Le Meurice";

    protected static final Address RESTAURANT_ADDRESS = new Address("1","le meurice address", 3, "pablito",
            75001, "France");


    @Autowired
    RestaurantController restaurantController;

    @Test
    public void validResturantById() throws Exception {
        Logger.getGlobal().info("Start validResturantById test");
        ResponseEntity<Entity> restaurant = restaurantController.findById(RESTAURANT);

        Assert.assertEquals(HttpStatus.OK, restaurant.getStatusCode());
        Assert.assertTrue(restaurant.hasBody());
        Assert.assertNotNull(restaurant.getBody());
        Assert.assertEquals(RESTAURANT, restaurant.getBody().getId());
        Assert.assertEquals(RESTAURANT_NAME, restaurant.getBody().getName());
        Logger.getGlobal().info("End validResturantById test");
    }

    /**
     * Test method for findByName method
     */
    @Test
    public void validRestaurantByName() throws Exception {
        Logger.getGlobal().info("Start validResturantByName test");
        ResponseEntity<Collection<Restaurant>> restaurants = restaurantController
                .findByName(RESTAURANT_NAME);
        Logger.getGlobal().info("In validAccount test");

        Assert.assertEquals(HttpStatus.OK, restaurants.getStatusCode());
        Assert.assertTrue(restaurants.hasBody());
        Assert.assertNotNull(restaurants.getBody());
        Assert.assertFalse(restaurants.getBody().isEmpty());
        Restaurant restaurant = (Restaurant) restaurants.getBody().toArray()[0];
        Assert.assertEquals(RESTAURANT, restaurant.getId());
        Assert.assertEquals(RESTAURANT_NAME, restaurant.getName());
        Logger.getGlobal().info("End validResturantByName test");
    }

    /**
     * Test method for add method
     */
    @Test
    public void validAdd() throws Exception {
        Logger.getGlobal().info("Start validAdd test");
        RestaurantVO restaurant = new RestaurantVO();
        restaurant.setId("999");
        restaurant.setName("Test Restaurant");
        restaurant.setAddress(new Address("1","test",3,"test",9100,"Test"));

        ResponseEntity<Restaurant> restaurants = restaurantController.add(restaurant);
        Assert.assertEquals(HttpStatus.CREATED, restaurants.getStatusCode());
        Logger.getGlobal().info("End validAdd test");
    }
}
