package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.finaltermproject.DAO.AdminDAO;
import com.example.finaltermproject.DAO.CartDAO;
import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Customer;

public class LoginActivity extends AppCompatActivity
{
    ImageButton btnBack;
    Button btnDangNhap;
    RadioButton rdbAdmin, rdbCustomer;
    EditText edtTK, edtMK;

    AdminDAO adminDAO;
    CustomerDAO customerDAO;
    CartDAO cartDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getFormWidgets();
        addEventsForWidgets();
    }

    private void getFormWidgets()
    {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        rdbAdmin = (RadioButton) findViewById(R.id.rdbAdmin);
        rdbCustomer = (RadioButton) findViewById(R.id.rdbCustomer);
        edtTK = (EditText) findViewById(R.id.edtTK);
        edtMK = (EditText) findViewById(R.id.edtMK);
    }

    private void addEventsForWidgets()
    {
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (rdbAdmin.isChecked())
                    processLoginAdmin();
                else
                    if (rdbCustomer.isChecked())
                        processLoginCustomer();
                    else
                        Toast.makeText(LoginActivity.this, "Vui lòng chọn loại tài khoản", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processLoginAdmin()
    {
        String email = edtTK.getText().toString().trim();
        String password = edtMK.getText().toString().trim();

        adminDAO = new AdminDAO(this);
        if (adminDAO.checkTKAdmin(email, password))
        {
            Toast.makeText(LoginActivity.this, "Đăng nhập tài khoản admin thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
    }

    private void processLoginCustomer()
    {
        String email = edtTK.getText().toString().trim();
        String password = edtMK.getText().toString().trim();

        customerDAO = new CustomerDAO(LoginActivity.this);
        if (customerDAO.checkTKCustomer(email, password))
        {
            Customer customer = customerDAO.layCustomer(email);

            SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("userid", customer.getUserid());
            editor.putString("email", customer.getEmai());
            editor.apply();

            Toast.makeText(LoginActivity.this, "Đăng nhập tài khoản khách hàng thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);    // truyen tham so customerid bang intent tao doi tuong .getusserid
            startActivity(intent);
        }
        else
            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
    }
}