package com.service;

import com.entity.Car;
import com.entity.Product;
import com.enums.CarStatusEnum;
import com.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class CarService {


    @Autowired
    private CarRepository carRepository;


    public void saveProducts(List<Product> products, String carId) {

        products.stream().filter(Product::isDiscount).forEach(x -> {
            x.setPrice(x.getPrice().divide(BigDecimal.valueOf(2)));
        });
        if (Objects.isNull(carId)) {
            Car car = new Car(products, CarStatusEnum.PENDIENTE);
            carRepository.save(car);
        } else {
            Car currentCar = carRepository.findById(carId);
            if (Objects.isNull(currentCar.getProducts())) {
                currentCar.setProducts(products);

            } else {
                currentCar.getProducts().addAll(products);
            }
            carRepository.update(currentCar);
        }

    }

    public Map<String, Car> getAllCars() {

        return carRepository.finAll();
    }


}
