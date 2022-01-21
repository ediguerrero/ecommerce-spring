package com.repository.impl;

import com.entity.Car;
import com.repository.CarRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Repository
public class CarRepositoryImpl implements CarRepository {

    private static final String KEY = "Car";
    private RedisTemplate<String, Car> redisTemplate;
    private HashOperations hashOperations;

    public CarRepositoryImpl(RedisTemplate<String, Car> redistemplate) {
        this.redisTemplate = redistemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<String, Car> finAll() throws Exception {
        Map<String, Car> allCars = hashOperations.entries(KEY);
        if (Objects.isNull(allCars)) {
            throw new Exception("no existe ningun carrito en el momento");
        }
        return allCars;
    }

    @Override
    public Car findById(String id) throws Exception {

        Car car = (Car) hashOperations.get(KEY, id);
        if (Objects.isNull(car)) {
            throw new Exception("no existe ningun carrito en el momento");
        }
        return car;
    }

    @Override
    public String save(Car car) {
        car.getProducts().forEach(x -> {
            String id = UUID.randomUUID().toString();
            x.setId(id);
        });
        String id = UUID.randomUUID().toString();
        car.setId(id);
        hashOperations.put(KEY, id, car);

        return id;
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }

    @Override
    public void update(Car car) {
        car.getProducts().stream().filter(x -> Objects.isNull(x.getId())).forEach(x -> {
            String id = UUID.randomUUID().toString();
            x.setId(id);
        });
        hashOperations.put(KEY, car.getId(), car);
    }

}
