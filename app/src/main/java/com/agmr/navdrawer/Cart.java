package com.agmr.navdrawer;

public class Cart {
    private String model, key, email;
    private float price;
    private int quantity;

    public Cart() {

    }


    public Cart(String email, String model, float price, int quantity) {
        this.email = email;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
