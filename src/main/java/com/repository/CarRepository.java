package com.repository;

import com.entity.Car;

import java.util.Map;

public interface CarRepository
{
	Map<String, Car> finAll();

	Car findById(String id) ;

	String save(Car roulette);

	void delete(String id);

	void update(Car car);
}
