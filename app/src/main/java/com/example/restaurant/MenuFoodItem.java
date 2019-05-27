package com.example.restaurant;

import java.io.Serializable;

public class MenuFoodItem implements Serializable {
    private String name, description, imageUrl, category;
    private Double price;

    public MenuFoodItem(String name, String description, String imageUrl, String category, Double price) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

}
