package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Customer;

public class SuaCustomerActivity extends AppCompatActivity
{
    TextView txtEmail;
    EditText edtPassword, edtName, edtAddress, edtPhoneNumber;
    Button btnLuu, btnHuy;
    ImageButton btnBack;
    CustomerDAO customerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_customer);

        getFormWidgets();
        getInfo();
        addEventsForWidgets();
    }

    private void getFormWidgets()
    {
        txtEmail = findViewById(R.id.txtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtDiaChi);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    private void getInfo()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        customerDAO = new CustomerDAO(SuaCustomerActivity.this);
        Customer customer =  customerDAO.layCustomer(email);

        txtEmail.setText(customer.getEmai());
        edtName.setText(customer.getcustomername());
        edtAddress.setText(customer.getAddress());
        edtPhoneNumber.setText(customer.getPhonenumber());
        edtPassword.setText(customer.getPassword());
    }

    private void addEventsForWidgets()
    {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processUpdate();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuaCustomerActivity.this, CustomerProfileActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuaCustomerActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void processUpdate()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userid", -1);

        String email = txtEmail.getText().toString().trim();
        String tenKhachHang = edtName.getText().toString().trim();
        String diaChi = edtAddress.getText().toString().trim();
        String soDienThoai = edtPhoneNumber.getText().toString().trim();
        String matKhau = edtPassword.getText().toString().trim();


        if (!tenKhachHang.isEmpty() && !diaChi.isEmpty() && !soDienThoai.isEmpty())
        {
            Customer customer = new Customer(userId, email, matKhau, tenKhachHang, diaChi, soDienThoai);
            customerDAO.suaCustomer(customer);
            Toast.makeText(this, "Đã lưu thông tin khách hàng", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SuaCustomerActivity.this, CustomerProfileActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
    }
}