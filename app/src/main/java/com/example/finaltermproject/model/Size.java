package com.example.finaltermproject.model;

import java.io.Serializable;

public class Size implements Serializable
{
    private int sizeid;
    private String sizename;

    public Size(int sizeid, String sizename) {
        this.sizeid = sizeid;
        this.sizename = sizename;
    }

    public int getSizeid() {
        return sizeid;
    }

    public void setSizeid(int sizeid) {
        this.sizeid = sizeid;
    }

    public String getSizename() {
        return sizename;
    }

    public void setSizename(String sizename) {
        this.sizename = sizename;
    }
}