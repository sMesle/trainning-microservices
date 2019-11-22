package com.example.restaurant.repository;

import com.example.restaurant.common.RestaurantNotFoundException;
import com.example.restaurant.model.entity.Address;
import com.example.restaurant.model.entity.Restaurant;
import org.springframework.stereotype.Repository;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 */
@Repository("restaurantRepository")
public class MemRestaurantRepository implements RestaurantRepository<Restaurant, String> {
    private static final Map<String, Restaurant> entities;

    static {
        entities = new ConcurrentHashMap<>(Map.ofEntries(
                new AbstractMap.SimpleEntry<>("1",
                        new Restaurant("Le Meurice", "1", new Address("1","rue de rivoli", 228,
                                "rue de rivoli",75008,"France"), Optional.empty()))));
    }


    @Override
    public boolean containsName(String name) {
        try {
            return !this.findByName(name).isEmpty();
        } catch (RestaurantNotFoundException ex) {
            return false;
        } catch (Exception ex) {
            //Exception Handler
        }
        return false;
    }

    @Override
    public void add(Restaurant entity) {
        entities.put(entity.getId(), entity);
    }


    @Override
    public void remove(String id) {
        entities.remove(id);
    }

    @Override
    public void update(Restaurant entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        }
    }

    @Override
    public boolean contains(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Restaurant get(String id) {
        return entities.get(id);
    }

    @Override
    public Collection<Restaurant> getAll() {
        return entities.values();
    }

    @Override
    public Collection<Restaurant> findByName(String name) throws RestaurantNotFoundException {
        int noOfChars = name.length();
        Collection<Restaurant> restaurants = entities.entrySet().stream()
                .filter(e -> e.getValue().getName().toLowerCase().contains(name.subSequence(0, noOfChars)))
                .collect(Collectors.toList())
                .stream()
                .map(k -> k.getValue())
                .collect(Collectors.toList());
        if (restaurants.isEmpty()) {
            Object[] args = {name};
            throw new RestaurantNotFoundException("restaurantNotFound", args);
        }
        return restaurants;
    }
}
