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
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CarService {


    @Autowired
    private CarRepository carRepository;


    public String saveProducts(List<Product> products, String carId) {

        products.stream().filter(Product::isDiscount).forEach(x -> {
            x.setPrice(x.getPrice().divide(BigDecimal.valueOf(2)));
        });
        if (Objects.isNull(carId)) {
            Car car = new Car(products, CarStatusEnum.PENDIENTE);
            return carRepository.save(car);
        } else {
            Car currentCar = carRepository.findById(carId);
            if (Objects.isNull(currentCar.getProducts())) {
                currentCar.setProducts(products);

            } else {

                List<Product> currentProducts = currentCar.getProducts().stream()
                        .filter(two -> products.stream()
                                .anyMatch(one -> one.getId().equals(two.getId())))
                        .collect(Collectors.toList());

                currentCar.getProducts().addAll(currentProducts);

            }
            carRepository.update(currentCar);
            return currentCar.getId();
        }

    }

    public Map<String, Car> getAllCars() {

        return carRepository.finAll();
    }

public double makeCheckout(String idCar){
        Car currentCar= carRepository.findById(idCar);
        currentCar.setStatus(CarStatusEnum.COMPLETADO);
        carRepository.update(currentCar);
    return currentCar.getProducts().stream().map(x->x.getPrice().doubleValue()).reduce(0.0, Double::sum);
}
}
