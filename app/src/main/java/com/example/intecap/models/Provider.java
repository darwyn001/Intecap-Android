package com.example.intecap.models;

import java.io.Serializable;

public class Provider implements Serializable {
    int id;
    String name;
    String address;
    String product;
    String picturePath;

    public Provider(int id, String name, String address, String product, String picturePath) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.product = product;
        this.picturePath = picturePath;
    }

    public Provider(String name, String address, String product, String picturePath) {
        this.name = name;
        this.address = address;
        this.product = product;
        this.picturePath = picturePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getProduct() {
        return product;
    }

    public String getPicturePath() {
        return picturePath;
    }
}
