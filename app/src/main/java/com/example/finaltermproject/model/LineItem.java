package com.example.finaltermproject.model;

import java.text.NumberFormat;

public class LineItem
{
    private int lineitemid;
    private Food food;
    private int quantity;
    //private int foodsize;
    //private String foodsize;

    public LineItem(int lineitemid, Food food, int quantity)
    {
        this.lineitemid = lineitemid;
        this.food = food;
        this.quantity = quantity;
    }
    public LineItem(Food food, int quantity)
    {
        this.food = food;
        this.quantity = quantity;
    }

    public int getLineitemid() {
        return lineitemid;
    }

    public void setLineitemid(int lineitemid) {
        this.lineitemid = lineitemid;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        double total = food.getPrice() * quantity;
        return total;
    }

    public String getTotalCurrencyFormat()
    {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getTotal());
    }
}
