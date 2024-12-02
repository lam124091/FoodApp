package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltermproject.DAO.CartDAO;
import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.DAO.InvoiceDAO;
import com.example.finaltermproject.DAO.InvoiceDetailDAO;
import com.example.finaltermproject.DAO.StatusDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Cart;
import com.example.finaltermproject.model.Customer;
import com.example.finaltermproject.model.Invoice;
import com.example.finaltermproject.model.InvoiceDetail;
import com.example.finaltermproject.model.LineItem;
import com.example.finaltermproject.model.Status;

import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity
{
    private EditText edtCartID, edtName, edtAddress, edtCode;
    private CalendarView dtpDate;
    private Button btnCancel, btnContinue;
    private TextView txtTempToTal, txtTotal, txtCK;

    CustomerDAO customerDAO;
    StatusDAO statusDAO;
    CartDAO cartDAO;
    InvoiceDetailDAO invoiceDetailDAO;
    InvoiceDAO invoiceDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getFormWidgets();
        getInfo();
        addEventsForWidgets();
        Intent intent = getIntent();
        if (intent.hasExtra("paymentmethod") && intent.hasExtra("total") && intent.hasExtra("discount") && intent.hasExtra("totalAmount")) {
            String paymentMethod = intent.getStringExtra("paymentmethod");
            double total = intent.getDoubleExtra("total", 0.0);
            double ck = intent.getDoubleExtra("discount", 0.0);
            double totalAmount = intent.getDoubleExtra("totalAmount", 0.0);

            String tempTotalFormatted = String.format("Tạm tính: %.0fđ", total);
            String discountAmountFormatted = String.format("Chiết khấu (5%%): %.0fđ", ck);
            String totalAmountFormatted = String.format("Tổng số tiền thanh toán: %.0fđ", totalAmount);

            txtTempToTal.setText(tempTotalFormatted);
            txtCK.setText(discountAmountFormatted);
            txtTotal.setText(totalAmountFormatted);
        }
    }

    private void getFormWidgets()
    {
        edtCartID = findViewById(R.id.edtCartID);
        dtpDate = findViewById(R.id.dtpDate);
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtCode = findViewById(R.id.edtCode);
        txtTempToTal = findViewById(R.id.txtTempTotal);
        txtTotal = findViewById(R.id.txtTotal);
        txtCK = findViewById(R.id.txtCK);
        btnCancel = findViewById(R.id.btnCancel);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void getInfo()
    {
        String email = getEmailFromSharedPreferences();

        customerDAO = new CustomerDAO(PaymentActivity.this);
        Customer customer = customerDAO.layCustomer(email);
    }

    private void addEventsForWidgets()
    {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processContinue();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private String getEmailFromSharedPreferences() {
        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        return sharedpreferences.getString("email", "");
    }

    private void processContinue()
    {
        String email = getEmailFromSharedPreferences();

        customerDAO = new CustomerDAO(PaymentActivity.this);
        Customer customer = customerDAO.layCustomer(email);

        cartDAO = new CartDAO(PaymentActivity.this);
        Cart cart = cartDAO.getCartByCustomer2(customer);

        statusDAO = new StatusDAO(this);
        Status status = statusDAO.layStatusTheoID(2);

        Date currentDate = Calendar.getInstance().getTime();

        double total = cart.calculateTotalAmount();
        double ck = total * 5/100;
        double totalAmount = total - ck;

        Intent intent = getIntent();
        String paymentMethod = intent.getStringExtra("paymentmethod");


        Invoice invoice = new Invoice(customer, currentDate, totalAmount, paymentMethod, status);
        invoiceDAO = new InvoiceDAO(PaymentActivity.this);
        int invoiceId = (int) invoiceDAO.themInvoice(invoice);

        int lastInsertedInvoiceId = invoiceDAO.getLastInsertedInvoiceId();

        invoiceDetailDAO = new InvoiceDetailDAO(PaymentActivity.this);
        for (LineItem lineItem : cart.getLINEITEM()) {
            InvoiceDetail invoiceDetail = new InvoiceDetail(invoice, lineItem.getFood(), lineItem.getQuantity());
            invoiceDetailDAO.themInvoiceDetail(lastInsertedInvoiceId, invoiceDetail);
        }

        CartDAO cartDAO = new CartDAO(this);
        cartDAO.deleteCart(cart);

        // Lưu thông tin Invoice và LineItem vào SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("invoice_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("customer_id", customer.getUserid());
        editor.putString("customer_name", customer.getcustomername());
        editor.putString("payment_method", paymentMethod);
        editor.putLong("total_amount", (long) totalAmount);
        editor.putString("invoice_date", currentDate.toString());
        editor.putString("invoice_status", status.getStatusname());
        editor.putInt("invoice_id", lastInsertedInvoiceId);
        editor.apply();

        Toast.makeText(PaymentActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
        Intent intentmove = new Intent(PaymentActivity.this, ThankyouActivity.class);
        startActivity(intentmove);
    }
}