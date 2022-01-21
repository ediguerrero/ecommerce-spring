package com.entity;

import com.enums.CarStatusEnum;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;


public class Car implements Serializable {

    private static final long serialVersionUID = -6835059655510577819L;
    private String id;
    private List<Product> products;
    private CarStatusEnum status;

    public Car(List<Product> products,CarStatusEnum status){
        this.products= products;
        this.status= status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public CarStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CarStatusEnum status) {
        this.status = status;
    }
}
