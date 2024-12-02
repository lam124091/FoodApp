package com.example.finaltermproject.model;

public class Admin extends User{
    private String adminname;
    public Admin(int userid, String emai, String password, String adminname) {
        super(userid, emai, password);
        this.adminname = adminname;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }
}
