package com.example.finaltermproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Customer;
import com.example.finaltermproject.model.Invoice;
import com.example.finaltermproject.model.Status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvoiceDAO {
    private SQLiteDatabase db;
    private Context context;

    public InvoiceDAO(Context context) {
        this.context = context;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.openDatabase();
    }

    public long themInvoice(Invoice invoice) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_INVOICE_CUSTOMERID, invoice.getCustomer().getUserid());
        values.put(DatabaseHelper.KEY_INVOICE_DATE,  getCurrentDate());
        values.put(DatabaseHelper.KEY_INVOICE_TOTALMONEY, invoice.getTotalmoney());
        values.put(DatabaseHelper.KEY_INVOICE_PAYMENTMETHOD, invoice.getPaymentmethod());
        values.put(DatabaseHelper.KEY_INVOICE_STATUSID, invoice.getStatus().getStatusid());

        return db.insert(DatabaseHelper.TABLE_INVOICE, null, values);
    }

    public List<Invoice> layTatCaInvoice() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INVOICE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int invoiceId = cursor.getInt(0);
                int customerId = cursor.getInt(1);
                String date = cursor.getString(2);
                double totalMoney = cursor.getDouble(3);
                String paymentMethod = cursor.getString(4);
                int statusId = cursor.getInt(5);

                CustomerDAO customerDAO = new CustomerDAO(context);
                Customer customer = customerDAO.layCustomerByID(customerId);

                StatusDAO statusDAO = new StatusDAO(context);
                Status status = statusDAO.layStatusTheoID(statusId);

                Invoice invoice = new Invoice(invoiceId, customer, parseDate(date), totalMoney, paymentMethod, status);
                invoices.add(invoice);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return invoices;
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private Date parseDate(String dateString) {
        // Chuyển đổi chuỗi ngày giờ thành đối tượng Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public int getLastInsertedInvoiceId() {
        int lastInsertedId = -1;
        String query = "SELECT last_insert_rowid()";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastInsertedId = cursor.getInt(0);
            cursor.close();
        }
        return lastInsertedId;
    }

    public List<Invoice> getInvoicesByCustomerEmail(String customerEmail) { //lay tat ca hoa don cua customer do
        List<Invoice> invoices = new ArrayList<>();
        // Truy vấn SQL để lấy các hóa đơn của khách hàng dựa trên địa chỉ email
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INVOICE + " i " +
                "JOIN " + DatabaseHelper.TABLE_CUSTOMER + " c ON i." + DatabaseHelper.KEY_INVOICE_CUSTOMERID + " = c." + DatabaseHelper.KEY_CUSTOMER_ID +
                " WHERE c." + DatabaseHelper.KEY_CUSTOMER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{customerEmail});

        if (cursor.moveToFirst()) {
            do {
                int invoiceId = cursor.getInt(0);
                int customerId = cursor.getInt(1);
                String date = cursor.getString(2);
                double totalMoney = cursor.getDouble(3);
                String paymentMethod = cursor.getString(4);
                int statusId = cursor.getInt(5);

                // Lấy thông tin khách hàng từ cơ sở dữ liệu
                CustomerDAO customerDAO = new CustomerDAO(context);
                Customer customer = customerDAO.layCustomerByID(customerId);

                // Lấy trạng thái của hóa đơn từ cơ sở dữ liệu
                StatusDAO statusDAO = new StatusDAO(context);
                Status status = statusDAO.layStatusTheoID(statusId);

                // Tạo đối tượng hóa đơn và thêm vào danh sách
                Invoice invoice = new Invoice(invoiceId, customer, parseDate(date), totalMoney, paymentMethod, status);
                invoices.add(invoice);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return invoices;
    }

    public Invoice layInvoiceTheoID(int invoiceId) {
        Invoice invoice = null;
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INVOICE +
                " WHERE " + DatabaseHelper.KEY_INVOICE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(invoiceId)});

        if (cursor.moveToFirst()) {
            int invoiceid = cursor.getInt(0);
            int customerId = cursor.getInt(1);
            String date = cursor.getString(2);
            double totalMoney = cursor.getDouble(3);
            String paymentMethod = cursor.getString(4);
            int statusId = cursor.getInt(5);

            // Lấy thông tin khách hàng từ cơ sở dữ liệu
            CustomerDAO customerDAO = new CustomerDAO(context);
            Customer customer = customerDAO.layCustomerByID(customerId);

            // Lấy trạng thái của hóa đơn từ cơ sở dữ liệu
            StatusDAO statusDAO = new StatusDAO(context);
            Status status = statusDAO.layStatusTheoID(statusId);

            // Tạo đối tượng hóa đơn
            invoice = new Invoice(invoiceid, customer, parseDate(date), totalMoney, paymentMethod, status);
        }

        cursor.close();
        return invoice;
    }
}