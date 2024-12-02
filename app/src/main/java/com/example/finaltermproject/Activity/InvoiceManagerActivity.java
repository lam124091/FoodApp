package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finaltermproject.Adapter.ListViewAdminInvoiceAdapter;
import com.example.finaltermproject.DAO.InvoiceDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceManagerActivity extends AppCompatActivity
{
    ListView lvInvoice;
    ListViewAdminInvoiceAdapter listViewAdminInvoiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_manager);

        lvInvoice = findViewById(R.id.lvInvoice);

        listViewAdminInvoiceAdapter = new ListViewAdminInvoiceAdapter(this, new ArrayList<>());
        lvInvoice.setAdapter(listViewAdminInvoiceAdapter);

        loadInvoiceData();
        listViewAdminInvoiceAdapter.setOnItemClickListener(new ListViewAdminInvoiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Invoice invoice, int position) {
                Intent intent = new Intent(InvoiceManagerActivity.this, InvoiceDetailActivity.class);
                intent.putExtra("invoiceId", invoice.getInvoiceid());
                startActivity(intent);
            }
        });

    }

    private void loadInvoiceData()
    {
        InvoiceDAO invoiceDAO = new InvoiceDAO(this);
        List<Invoice> invoices = invoiceDAO.layTatCaInvoice();

        listViewAdminInvoiceAdapter.clear();
        listViewAdminInvoiceAdapter.addAll(invoices);
        listViewAdminInvoiceAdapter.notifyDataSetChanged();
    }
}