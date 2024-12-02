package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finaltermproject.R;

public class AdminActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnQuanLyCategory = findViewById(R.id.btnQuanLyCategory);
        Button btnQuanLyFood = findViewById(R.id.btnQuanLyFood);
        Button btnQuanLyCustomer = findViewById(R.id.btnQuanLyCustomer);
        Button btnQuanLyInvoice = findViewById(R.id.btnQuanLyInvoice);

        btnQuanLyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CategoryManagerActivity.class);
                startActivity(intent);
            }
        });

        btnQuanLyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, FoodManagerActivity.class);
                startActivity(intent);
            }
        });

        btnQuanLyCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CustomerManagerActivity.class);
                startActivity(intent);
            }
        });

        btnQuanLyInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, InvoiceManagerActivity.class);
                startActivity(intent);
            }
        });
    }
}