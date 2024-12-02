package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finaltermproject.Adapter.ListViewInvoiceDetailAdapter;
import com.example.finaltermproject.DAO.InvoiceDAO;
import com.example.finaltermproject.DAO.InvoiceDetailDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Invoice;
import com.example.finaltermproject.model.InvoiceDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class InvoiceDetailActivity extends AppCompatActivity
{
    private ImageButton btnBack;
    private TextView  txtInvoiceId, txtCustomer, txtDate, txtPaymentMethod, txtStatus, txtTotalInvoice;
    private ListView lvInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);

        btnBack = findViewById(R.id.btnBack);
        txtInvoiceId = findViewById(R.id.txtInvoiceId);
        txtCustomer = findViewById(R.id.txtCustomer);
        txtDate = findViewById(R.id.txtDate);
        txtPaymentMethod = findViewById(R.id.txtPaymentMethod);
        txtStatus = findViewById(R.id.txtStatus);
        txtTotalInvoice = findViewById(R.id.txtTotalInvoice);
        lvInvoice = findViewById(R.id.lvInvoice);

        int invoiceId = getIntent().getIntExtra("invoiceId", -1);

        // Nếu không có ID hóa đơn, kết thúc Activity
        if (invoiceId == -1) {
            finish();
            return;
        }

        String customerEmail = getEmailFromSharedPreferences();

        // Lấy thông tin hóa đơn từ cơ sở dữ liệu
        InvoiceDAO invoiceDAO = new InvoiceDAO(this);
        Invoice invoice = invoiceDAO.layInvoiceTheoID(invoiceId);

        // Hiển thị thông tin hóa đơn
        txtInvoiceId.setText(String.valueOf(invoice.getInvoiceid()));
        txtCustomer.setText(invoice.getCustomer().getcustomername());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(invoice.getDate());
        txtDate.setText(dateString);
        txtPaymentMethod.setText(invoice.getPaymentmethod());
        txtStatus.setText(invoice.getStatus().getStatusname());
        txtTotalInvoice.setText(invoice.getTotalmoney() + "VNĐ");

        // Lấy danh sách chi tiết hóa đơn từ cơ sở dữ liệu
        InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO(this);
        ArrayList<InvoiceDetail> invoiceDetails = invoiceDetailDAO.layInvoiceDetailTheoInvoiceID(invoiceId);

        // Hiển thị danh sách chi tiết hóa đơn bằng ListView
        ListViewInvoiceDetailAdapter adapter = new ListViewInvoiceDetailAdapter(this, invoiceDetails);
        lvInvoice.setAdapter(adapter);
    }

    private String getEmailFromSharedPreferences()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }
}