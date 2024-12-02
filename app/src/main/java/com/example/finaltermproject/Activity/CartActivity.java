package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finaltermproject.Adapter.ListViewCartAdapter;
import com.example.finaltermproject.DAO.CartDAO;
import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Cart;
import com.example.finaltermproject.model.Customer;
import com.example.finaltermproject.model.LineItem;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity
{
    ListView lvCart;
    ImageButton btnBack;
    TextView txtTotalCart;
    Button btnPayment;

    ListViewCartAdapter lvcartadap;

    CustomerDAO customerDAO;
    CartDAO cartDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvCart = findViewById(R.id.lvCart);
        btnBack = findViewById(R.id.btnBack);
        txtTotalCart = findViewById(R.id.txtTotalCart);
        btnPayment = findViewById(R.id.btnPayment);
        addEventsForWidgets();
        loadCart();
    }

    private void addEventsForWidgets()
    {
        final String email = getEmailFromSharedPreferences();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartIntent = new Intent(CartActivity.this, InvoiceActivity.class);
                cartIntent.putExtra("customer_email", email);
                startActivity(cartIntent);
            }
        });
    }

    private String getEmailFromSharedPreferences() {
        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        return sharedpreferences.getString("email", "");
    }

    private void loadCart()
    {
//        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//        String email = sharedpreferences.getString("email", "");

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("customer_email"))
        {
            String email = intent.getStringExtra("customer_email");
            customerDAO = new CustomerDAO(CartActivity.this);
            Customer customer = customerDAO.layCustomer(email);

            cartDAO = new CartDAO(CartActivity.this);
            Cart cart = cartDAO.getCartByCustomer2(customer);
            //  LINEITEM =  lineitemDAO.getAllLineBycart(cart.getcartid) => (this, LINEITEM);

            if (cart != null)
            {
                lvcartadap = new ListViewCartAdapter(CartActivity.this, cart.getLINEITEM(), txtTotalCart, cart);
                lvCart.setAdapter(lvcartadap);
                double totalAmount = cart.calculateTotalAmount();
                String totalAmountFormatted = String.format("Tổng giá: %.0fđ", totalAmount);
                txtTotalCart.setText(totalAmountFormatted);
            }
            else
                txtTotalCart.setText("Tổng giá: 0");
        }
    }
}