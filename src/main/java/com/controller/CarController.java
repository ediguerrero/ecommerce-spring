package com.controller;


import com.entity.Product;
import com.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public ResponseEntity findAll() {
        return new ResponseEntity(carService.getAllCars(), HttpStatus.OK);
    }

    @PostMapping("/save-products")
    public ResponseEntity createCar(@RequestBody List<Product> products,
                                    @RequestParam(value = "carId", required = false) String carId) {
        carService.saveProducts(products, carId);
        return new ResponseEntity<>("productos guardados con exito", HttpStatus.OK);

    }
}