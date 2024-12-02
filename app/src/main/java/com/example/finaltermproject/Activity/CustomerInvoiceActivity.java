package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.finaltermproject.Adapter.ListviewInvoiceAdapter;
import com.example.finaltermproject.DAO.InvoiceDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Invoice;

import java.util.List;

public class CustomerInvoiceActivity extends AppCompatActivity {

    private RecyclerView rvInvoices;
    private ListviewInvoiceAdapter adapter;
    private List<Invoice> invoices;
    private InvoiceDAO invoiceDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_invoice);

        rvInvoices = findViewById(R.id.rvInvoices);
        rvInvoices.setLayoutManager(new LinearLayoutManager(this));

        // Lấy email của khách hàng từ SharedPreferences
        String customerEmail = getEmailFromSharedPreferences();

        // Lấy danh sách hóa đơn của khách hàng
        invoiceDAO = new InvoiceDAO(this);
        invoices = invoiceDAO.getInvoicesByCustomerEmail(customerEmail);

        // Gán adapter cho RecyclerView
        adapter = new ListviewInvoiceAdapter(this, invoices);
        adapter.setOnItemClickListener(new ListviewInvoiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Invoice invoice) {
                Intent intent = new Intent(CustomerInvoiceActivity.this, InvoiceDetailActivity.class);
                intent.putExtra("invoiceId", invoice.getInvoiceid());
                startActivity(intent);
            }
        });
        rvInvoices.setAdapter(adapter);
    }

    private String getEmailFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }
}