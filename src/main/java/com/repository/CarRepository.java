package com.repository;

import com.entity.Car;

import java.util.Map;

public interface CarRepository
{
	Map<String, Car> finAll() throws Exception;

	Car findById(String id) throws Exception;

	String save(Car car);

	void delete(String id);

	void update(Car car);
}
