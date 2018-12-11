package com.agmr.navdrawer.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CartItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String model;
    private String key;
    private String uid;
    private String imageUrl;
    private float price;
    private int quantity;

    //Empty constructor
    public CartItem() {

    }

    public CartItem(String model, String key, String uid, String imageUrl, float price, int quantity) {
        this.model = model;
        this.key = key;
        this.imageUrl = imageUrl;
        this.uid = uid;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
