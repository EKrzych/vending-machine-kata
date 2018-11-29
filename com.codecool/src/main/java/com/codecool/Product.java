package com.codecool;

public class Product {
    private String name;
    private Integer quantity;
    private Double price;

    public Product(String name, Double price, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
