package com.example.finaltermproject.model;

import java.io.Serializable;

public class Category implements Serializable
{
    private int categoryid;
    private String categoryname;

    public Category(int categoryid, String categoryname) {
        this.categoryid = categoryid;
        this.categoryname = categoryname;
    }

    public Category() {};

    public Category(String categoryname) {
        this.categoryname = categoryname;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
