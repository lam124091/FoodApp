package com.example.finaltermproject.model;

public class Status
{
    int statusid;
    String statusname;

    public Status(int statusid, String statusname) {
        this.statusid = statusid;
        this.statusname = statusname;
    }

    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }
}
