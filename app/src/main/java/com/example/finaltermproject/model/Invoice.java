package com.example.finaltermproject.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Invoice
{
    private int invoiceid;
    private Customer customer;
    private Date date;
    private double totalmoney;
    private String paymentmethod;
    private Status status;

    public Invoice(int invoiceid, Customer customer, Date date, double totalmoney, String paymentmethod, Status status) {
        this.invoiceid = invoiceid;
        this.customer = customer;
        this.date = date;
        this.totalmoney = totalmoney;
        this.paymentmethod = paymentmethod;
        this.status = status;
    }

    public Invoice(Customer customer, Date date, double totalmoney, String paymentmethod, Status status) {
        this.customer = customer;
        this.date = date;
        this.totalmoney = totalmoney;
        this.paymentmethod = paymentmethod;
        this.status = status;
    }

    public int getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(int invoiceid) {
        this.invoiceid = invoiceid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(double totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public static Date getCurrentDate() {
        return new Date(); // Trả về ngày hiện tại
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
