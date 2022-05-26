package com.main.pt_assignment_4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product extends MenuItem {
    private Double rating, price;
    private Integer calories, protein, fat, sodium;

    public Product(
            String name, Double rating, Double price, Integer calories,
                   Integer protein, Integer fat, Integer sodium) {
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
    }

    public Product(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getSodium() {
        return sodium;
    }

    public void setSodium(Integer sodium) {
        this.sodium = sodium;
    }

    @Override
    public Double computePrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name) && rating.equals(product.rating) && price.equals(product.price) && calories.equals(product.calories) && protein.equals(product.protein) && fat.equals(product.fat) && sodium.equals(product.sodium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rating, price, calories, protein, fat, sodium);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", sodium=" + sodium +
                '}';
    }
}
