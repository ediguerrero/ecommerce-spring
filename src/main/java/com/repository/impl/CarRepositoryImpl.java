package com.repository.impl;

import com.entity.Car;
import com.repository.CarRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;


@Repository
public class CarRepositoryImpl implements CarRepository {

    private static final String KEY= "Car";
    private RedisTemplate<String, Car> redisTemplate;
    private HashOperations hashOperations;

    public CarRepositoryImpl(RedisTemplate<String, Car> redistemplate) {
        this.redisTemplate=redistemplate;
    }

    @PostConstruct
    private void init() { hashOperations = redisTemplate.opsForHash(); }

    @Override
    public Map<String, Car> finAll() {	return hashOperations.entries(KEY);	}

    @Override
    public Car findById(String id) { return (Car) hashOperations.get(KEY, id); }

    @Override
    public String save(Car roulette) {
        String id= UUID.randomUUID().toString();
        roulette.setId(id);
        hashOperations.put(KEY, id, roulette);

        return id;
    }

    @Override
    public void delete(String id) {	hashOperations.delete(KEY, id);	}

    @Override
    public void update(Car roulette) {	hashOperations.put(KEY, roulette.getId(), roulette); }

}
