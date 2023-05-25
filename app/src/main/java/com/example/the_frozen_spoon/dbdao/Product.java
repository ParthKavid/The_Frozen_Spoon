package com.example.the_frozen_spoon.dbdao;

import java.io.Serializable;

public class Product implements Serializable {
    private Integer id;
    private String product_name;
    private String price;
    private Integer img;
    private String description;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                ", img=" + img +
                ", description='" + description + '\'' +
                '}';
    }

    public Product(){}
    public Product(Integer id, String product_name, String price, Integer img, String description) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.img = img;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
