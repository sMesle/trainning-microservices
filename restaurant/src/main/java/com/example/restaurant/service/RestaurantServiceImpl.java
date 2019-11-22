package com.example.restaurant.service;

import com.example.restaurant.common.DuplicateRestaurantException;
import com.example.restaurant.common.InvalidRestaurantException;
import com.example.restaurant.model.entity.Restaurant;
import com.example.restaurant.model.entity.Entity;
import com.example.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service("restaurantService")
public class RestaurantServiceImpl extends BaseService<Restaurant, String>
        implements RestaurantService {

    private RestaurantRepository<Restaurant, String> restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository<Restaurant, String> restaurantRepository) {
        super(restaurantRepository);
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void add(Restaurant restaurant) throws Exception {
        if (restaurantRepository.containsName(restaurant.getName())) {
            Object[] args = {restaurant.getName()};
            throw new DuplicateRestaurantException("duplicateRestaurant", args);
        }

        if (restaurant.getName() == null || "".equals(restaurant.getName())) {
            Object[] args = {"Restaurant with null or empty name"};
            throw new InvalidRestaurantException("invalidRestaurant", args);
        }
        super.add(restaurant);
    }

    /**
     * @param name
     */
    @Override
    public Collection<Restaurant> findByName(String name) throws Exception {
        return restaurantRepository.findByName(name);
    }

    /**
     * @param restaurant
     */
    @Override
    public void update(Restaurant restaurant) throws Exception {
        restaurantRepository.update(restaurant);
    }

    /**
     * @param id
     */
    @Override
    public void delete(String id) throws Exception {
        restaurantRepository.remove(id);
    }

    /**
     * @param restaurantId
     */
    @Override
    public Entity findById(String restaurantId) throws Exception {
        return restaurantRepository.get(restaurantId);
    }

    /**
     * @param name
     */
    @Override
    public Collection<Restaurant> findByCriteria(Map<String, ArrayList<String>> name)
            throws Exception {
        throw new UnsupportedOperationException(
                "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
