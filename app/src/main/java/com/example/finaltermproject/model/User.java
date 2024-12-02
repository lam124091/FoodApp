package com.example.finaltermproject.model;

public class User
{
    private int userid;
    private String emai;
    private String password;

    public User(int userid, String emai, String password) {
        this.userid = userid;
        this.emai = emai;
        this.password = password;
    }
    public User(String emai, String password) {
        this.emai = emai;
        this.password = password;
    }

    public User() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getEmai() {
        return emai;
    }

    public void setEmai(String emai) {
        this.emai = emai;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
