package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finaltermproject.Adapter.ListViewCartInInvoiceAdapter;
import com.example.finaltermproject.Adapter.ListViewInvoiceDetailAdapter;
import com.example.finaltermproject.DAO.InvoiceDetailDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.InvoiceDetail;

import java.util.ArrayList;

public class ThankyouActivity extends AppCompatActivity
{
    ListViewCartInInvoiceAdapter listViewCartInInvoiceAdapter;

    private TextView  txtCustomerID, txtName, txtStatus,  txtCode, txtDate,  txtPayment,  txtTotal;
    private ListView lvInvoice;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

        getFormWidgets();

        SharedPreferences sharedPreferences = getSharedPreferences("invoice_info", MODE_PRIVATE);
        int customerId = sharedPreferences.getInt("customer_id", 0);
        String customerName = sharedPreferences.getString("customer_name", "");
        String paymentMethod = sharedPreferences.getString("payment_method", "");
        long totalAmountLong = sharedPreferences.getLong("total_amount", 0L);
        double totalAmount = totalAmountLong;
        String invoiceDate = sharedPreferences.getString("invoice_date", "");
        String invoiceStatus = sharedPreferences.getString("invoice_status", "");
        int invoiceId = sharedPreferences.getInt("invoice_id", 0);

        txtCustomerID.setText(String.valueOf(customerId));
        txtName.setText(customerName);
        txtStatus.setText(invoiceStatus);
        txtCode.setText(String.valueOf(invoiceId));
        txtDate.setText(invoiceDate);
        txtPayment.setText(paymentMethod);
        txtTotal.setText(String.format("Tổng Giá: %.0fđ", totalAmount));

        InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO(this);
        ArrayList<InvoiceDetail> invoiceDetails = invoiceDetailDAO.layInvoiceDetailTheoInvoiceID(invoiceId);
        ListViewInvoiceDetailAdapter adapter = new ListViewInvoiceDetailAdapter(this, invoiceDetails);
        lvInvoice.setAdapter(adapter);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThankyouActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getFormWidgets()
    {
        txtCustomerID = findViewById(R.id.txtCustomerID);
        txtName = findViewById(R.id.txtName);
        txtStatus = findViewById(R.id.txtStatus);
        txtCode = findViewById(R.id.txtCode);
        txtDate = findViewById(R.id.txtDate);
        txtPayment = findViewById(R.id.txtPayment);
        lvInvoice = findViewById(R.id.lvInvoice);
        txtTotal = findViewById(R.id.txtTotal);
        btnConfirm = findViewById(R.id.btnConfirm);
    }
}