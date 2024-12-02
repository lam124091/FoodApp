package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.finaltermproject.Adapter.ListviewCustomerAdapter;
import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.model.Customer;
import com.example.finaltermproject.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity
{
    // Bộ đôi để custom ListView Customer
    ListviewCustomerAdapter lvcustomeradapter;
    List<Customer> CUSTOMER;
    CustomerDAO customerDAO;

    ImageButton btnBack;
    EditText edtTK, edtMK, edtNhapLai;
    Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getFormWidgets();
        addEventsForWidgets();


    }

    private void getFormWidgets()
    {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        edtTK = (EditText) findViewById(R.id.edtTK);
        edtMK = (EditText) findViewById(R.id.edtMK);
        edtNhapLai = (EditText) findViewById(R.id.edtNhapLai);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);

        customerDAO = new CustomerDAO(this);
        CUSTOMER = customerDAO.layTatCaCustomer();

    }

    private void addEventsForWidgets()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = edtTK.getText().toString().trim();
                String password = edtMK.getText().toString().trim();
                String confirmPassword = edtNhapLai.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                else
                    if (!password.equals(confirmPassword))
                        Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                else
                {
                    Customer customer = new Customer(email, password);

                    // Thêm khách hàng vào cơ sở dữ liệu
                    CustomerDAO customerDAO = new CustomerDAO(RegisterActivity.this);
                    customerDAO.themCustomer(customer);

                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void processRegister()
    {
//         boolean isValid;
//         do {
//             // Nút 1: Lấy input
//             String email = edtTK.getText().toString().trim();
//             String password = edtMK.getText().toString().trim();
//             String confirmpassword = edtNhapLai.getText().toString().trim();

//             // Nút 2-6: Validate
//             if (email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
//                 Toast.makeText(this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
//                 isValid = false;
//             } else if (!password.equals(confirmpassword)) {
//                 Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show(); 
//                 isValid = false;
//             } else if (isCustomerExists(email)) {
//                 Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
//                 isValid = false;
//             } else {
//                 isValid = true;
//             }

//             if (isValid) {
//                 try {
//                     // Nút 7: Tạo customer
//                     Customer customer = new Customer(email, password);
//                     // Nút 8: Thêm vào DB
//                     CustomerDAO customerDAO = new CustomerDAO(RegisterActivity.this);
//                     customerDAO.themCustomer(customer);
//                     // Nút 9: Chuyển màn hình
//                     Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                     startActivity(intent);
//                 } catch (Exception e) {
//                     // Nút 10: Xử lý lỗi
//                     isValid = false;
//                 }
//             }
//         } while (!isValid);
        String email = edtTK.getText().toString().trim();
        String password = edtMK.getText().toString().trim();
        String confirmpassword = edtNhapLai.getText().toString().trim();

        if (validateInput(email, password, confirmpassword))
        {
            try
            {
                Customer customer = new Customer(email, password);

                CustomerDAO customerDAO = new CustomerDAO(RegisterActivity.this);
                customerDAO.themCustomer(customer);

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                //lvcustomeradapter.notifyDataSetChanged();
            }
            catch (NumberFormatException e)
            {
                //txtCheck.setText("Vui lòng nhập ID sinh viên hợp lệ (là số).");
            }
        }
    }

    private boolean validateInput(String email, String password, String  confirmpassword)
    {
        if (email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty())
        {
            Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            if (!password.equals(confirmpassword)) {
                Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (isCustomerExists(email))
            {
                Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean isCustomerExists(String id)
    {
//        for (Student i : STUDENT)
//            if (Integer.parseInt(i.getId()) == id)
//                return true;
        return false;
    }
}