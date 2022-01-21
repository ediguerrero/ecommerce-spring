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
    public ResponseEntity findAll() throws Exception {
        return new ResponseEntity(carService.getAllCars(), HttpStatus.OK);
    }

    @PostMapping("/save-products")
    public ResponseEntity createCar(@RequestBody List<Product> products,
                                    @RequestParam(value = "carId", required = false) String carId) throws Exception {
        String id = carService.saveProducts(products, carId);
        return new ResponseEntity<>("productos guardados con exito en el carrito con el id: " + id, HttpStatus.OK);

    }

    @PutMapping("/checkout")
    public ResponseEntity createCar(@RequestParam(value = "carId", required = false) String carId) throws Exception {

        return new ResponseEntity<>("total a pagar: " + carService.makeCheckout(carId), HttpStatus.OK);

    }

    @DeleteMapping("/cars")
    public void deleteRoulette(   @RequestParam(value = "idCar") String idCar, @RequestParam(value = "idProduct", required = false) String idProduct) throws Exception {
        carService.deleteCarOrProduct(idCar, idProduct);
    }

    @PutMapping("/modify-product")
    public ResponseEntity modifyProduct(@RequestBody Product product,@RequestParam(value = "idCar") String idCar) throws Exception {
        carService.updateProduct(product,idCar);
        return new ResponseEntity<>("el producto ha sido modificado", HttpStatus.OK);

    }
}
