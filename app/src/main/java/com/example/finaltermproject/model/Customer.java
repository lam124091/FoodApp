package com.example.finaltermproject.model;

import java.io.Serializable;

public class Customer extends User implements Serializable
{
    private String customername;
    private String address;
    private String phonenumber;

    public Customer(int userid, String emai, String password, String customername, String address, String phonenumber) {
        super(userid, emai, password);
        this.customername = customername;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public Customer() {
    }

    public Customer(String emai, String password) {
        super(emai, password);
    }

    public String getcustomername() {
        return customername;
    }

    public void setcustomername(String customername) {
        this.customername = customername;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
