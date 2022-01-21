package com.service;

import com.entity.Car;
import com.entity.Product;
import com.enums.CarStatusEnum;
import com.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CarService {


    @Autowired
    private CarRepository carRepository;


    public String saveProducts(List<Product> products, String carId) throws Exception {

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

    public Map<String, Car> getAllCars() throws Exception {

        return carRepository.finAll();
    }

    public void deleteCarOrProduct(String idCar, String idProduct) throws Exception {
        if (Objects.isNull(idProduct)) {
            carRepository.delete(idCar);
        } else {
            Car currentCar = carRepository.findById(idCar);
            Product productToDelete = currentCar.getProducts().stream().filter(x -> x.getId().equals(idProduct)).findAny().get();
            if (Objects.isNull(productToDelete)) {
                throw new Exception("el producto con el id " + idProduct + " no fue encontrado");
            } else {
                currentCar.getProducts().remove(productToDelete);
                carRepository.update(currentCar);
            }
        }
    }

    public double makeCheckout(String idCar) throws Exception {
        Car currentCar = carRepository.findById(idCar);
        currentCar.setStatus(CarStatusEnum.COMPLETADO);
        carRepository.update(currentCar);
        return currentCar.getProducts().stream().map(x -> x.getPrice().doubleValue()).reduce(0.0, Double::sum);
    }

    public void updateProduct(Product product, String idCar) throws Exception {

        Car currentCar = carRepository.findById(idCar);

        Product productToUpdate = currentCar.getProducts().stream().filter(x -> x.getId().equals(product.getId())).findAny().get();

        int index = currentCar.getProducts().indexOf(productToUpdate);

        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setDiscount(product.isDiscount());
        productToUpdate.setName(product.getName());
        productToUpdate.setSku(product.getSku());

        currentCar.getProducts().set(index, productToUpdate);

        carRepository.update(currentCar);


    }
}
