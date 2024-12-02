package com.example.finaltermproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerManagerActivity extends AppCompatActivity {

    ImageButton btnBack;
    EditText edtName, edtPassword, edtAddress, edtPhoneNumber;
    Button btnSua, btnXoa, btnHienThi;
    TextView edtEmail;
    ListView lvCustomer;
    ArrayAdapter<String> arrayAdapter;
    CustomerDAO customerDAO;
    List<String> list = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_manager);

        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        btnHienThi = findViewById(R.id.btnHienThi);
        lvCustomer = findViewById(R.id.lvCustomer);
        btnBack = (ImageButton) findViewById(R.id.btnBack);

        // Khoi tao
        context = this;
        // hien thi
        list.clear();
        customerDAO = new CustomerDAO(context);
        list = customerDAO.layTatCaCustomers();
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,list);
        lvCustomer.setAdapter(arrayAdapter);


        lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy user được chọn từ danh sách
                String selectedUser = (String) parent.getItemAtPosition(position);

                // Tách dữ liệu user từ chuỗi hiển thị
                String[] userData = selectedUser.split(" - ");

                // Hiển thị dữ liệu user lên các EditText tương ứng
                edtEmail.setText(userData[0]);
                edtName.setText(userData[1]);
                edtPassword.setText(userData[2]);
                edtAddress.setText(userData[3]);
                edtPhoneNumber.setText(userData[4]);
            }
        });
        // ----
        btnHienThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                customerDAO = new CustomerDAO(context);
                list = customerDAO.layTatCaCustomers();
                arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,list);
                lvCustomer.setAdapter(arrayAdapter);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy email của user đang được hiển thị
                String email = edtEmail.getText().toString();

                // Xóa user từ cơ sở dữ liệu
                Customer userToDelete = customerDAO.layCustomer(email);
                customerDAO.xoaCustomer(userToDelete);
                updateListView();
                Toast.makeText(context, "Xóa tài khoản khách hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                String address = edtAddress.getText().toString();
                String phoneNumber = edtPhoneNumber.getText().toString();

                // Tạo đối tượng user mới
                Customer updatedUser = new Customer();
                updatedUser.setEmai(email);
                updatedUser.setcustomername(name);
                updatedUser.setPassword(password);
                updatedUser.setAddress(address);
                updatedUser.setPhonenumber(phoneNumber);
                customerDAO.suaCustomer(updatedUser);
                updateListView();
                Toast.makeText(context, "Sửa tài khoản khách hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerManagerActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateListView() {
        list.clear();
        customerDAO = new CustomerDAO(context);
        list = customerDAO.layTatCaCustomers();
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,list);
        lvCustomer.setAdapter(arrayAdapter);
    }
}