package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Customer;

public class CustomerProfileActivity extends AppCompatActivity
{
    TextView txtEmail, txtPassword, txtCustomerName, txtAddress, txtPhoneNumber;
    Button btnAnHien, btnSua;
    ImageButton btnBack;
    CustomerDAO customerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        getFormWidgets();
        getInfo();
        addEventsForWidgets();
    }

    private void getFormWidgets()
    {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnAnHien = findViewById(R.id.btnAnHien);
        btnSua = findViewById(R.id.btnSua);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    private void getInfo()
    {
        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        String email = sharedpreferences.getString("email", "");

        customerDAO = new CustomerDAO(CustomerProfileActivity.this);
        Customer customer = customerDAO.layCustomer(email);

        txtEmail.setText(customer.getEmai());
        txtPassword.setText(customer.getPassword()) ;
        txtCustomerName.setText(customer.getcustomername());
        txtAddress.setText(customer.getAddress());
        txtPhoneNumber.setText(customer.getPhonenumber());
    }

    private void addEventsForWidgets()
    {
        btnAnHien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CustomerProfileActivity.this, SuaCustomerActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerProfileActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });
    }
}