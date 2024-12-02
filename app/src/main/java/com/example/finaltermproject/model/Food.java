package com.example.finaltermproject.model;

import java.util.ArrayList;
import java.util.List;

public class Food
{
    private int foodid;
    private String foodname;
    private double price;
    private String description;
    private int likes;
    private Category foodcategory;
    private List<Size> foodsize;
    private byte[] image;

    public Food(int foodid, String foodname, double price, String description, int likes, Category foodcategory, List<Size> foodsize, byte[] image) {
        this.foodid = foodid;
        this.foodname = foodname;
        this.price = price;
        this.description = description;
        this.likes = likes;
        this.foodcategory = foodcategory;
        this.foodsize = foodsize;
        this.image = image;
    }
    public Food(String foodname, double price, String description, int likes, Category foodcategory, List<Size> foodsize, byte[] image) {
        this.foodname = foodname;
        this.price = price;
        this.description = description;
        this.likes = likes;
        this.foodcategory = foodcategory;
        this.foodsize = foodsize;
        this.image = image;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Category getFoodcategory() {
        return foodcategory;
    }

    public void setFoodcategory(Category foodcategory) {
        this.foodcategory = foodcategory;
    }

    public List<Size> getFoodsize() {
        return foodsize;
    }

    public void setFoodsize(List<Size> foodsize) {
        this.foodsize = foodsize;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}