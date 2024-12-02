package com.example.finaltermproject.model;

import java.util.ArrayList;

public class WhishList {
    private int id;
    private int customerid;
    private ArrayList<Food> foods;

    public WhishList(int id, int customerid) {
        this.id = id;
        this.customerid = customerid;
        this.foods = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getCustomerid() {
        return customerid;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public void removeFood(Food food) {
        foods.remove(food);
    }
}
