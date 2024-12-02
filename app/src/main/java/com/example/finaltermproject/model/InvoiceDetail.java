package com.example.finaltermproject.model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InvoiceDetail
{
    private int invoicedetailid;
    private Invoice invoice;
    private Food food;
    private int quantity;

    public InvoiceDetail(int invoicedetailid, Invoice invoice, Food food, int quantity) {
        this.invoicedetailid = invoicedetailid;
        this.invoice = invoice;
        this.food = food;
        this.quantity = quantity;
    }

    public InvoiceDetail(Invoice invoice, Food food, int quantity) {
        this.invoice = invoice;
        this.food = food;
        this.quantity = quantity;
    }

    public int getInvoicedetailid() {
        return invoicedetailid;
    }

    public void setInvoicedetailid(int invoicedetailid) {
        this.invoicedetailid = invoicedetailid;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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

    public double getInvoiceTotal() {return 0;}

    public String getInvoiceTotalCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getInvoiceTotal());
    }
}
