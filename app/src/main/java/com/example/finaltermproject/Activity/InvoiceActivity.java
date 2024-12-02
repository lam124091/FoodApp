package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltermproject.Adapter.ListViewCartInInvoiceAdapter;
import com.example.finaltermproject.DAO.CartDAO;
import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.DAO.InvoiceDAO;
import com.example.finaltermproject.DAO.InvoiceDetailDAO;
import com.example.finaltermproject.DAO.StatusDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Cart;
import com.example.finaltermproject.model.Customer;
import com.example.finaltermproject.model.Invoice;
import com.example.finaltermproject.model.LineItem;
import com.example.finaltermproject.model.Status;
import com.example.finaltermproject.model.InvoiceDetail;

import java.util.Calendar;
import java.util.Date;

public class InvoiceActivity extends AppCompatActivity
{
    private ImageButton btnBack;
    private TextView txtCustomerID, txtEmail, txtTotal;
    private EditText edtName, edtPhone, edtAddress;
    private ListView lvInvoice;
    private RadioButton rdbCash, rdbWallet;
    private Button btnConfirm;

    ListViewCartInInvoiceAdapter listViewCartInInvoiceAdapter;

    CustomerDAO customerDAO;
    CartDAO cartDAO;
    InvoiceDAO invoiceDAO;
    InvoiceDetailDAO invoiceDetailDAO;
    StatusDAO statusDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        getFormWidgets();
        getInfo();
        loadCart();
        addEventsForWidgets();
    }

    private void getFormWidgets()
    {
        btnBack = findViewById(R.id.btnBack);
        txtCustomerID = findViewById(R.id.txtCustomerID);
        txtEmail = findViewById(R.id.txtEmail);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        lvInvoice = findViewById(R.id.lvInvoice);
        txtTotal = findViewById(R.id.txtTotal);
        rdbCash = findViewById(R.id.rdbCash);
        rdbWallet = findViewById(R.id.rdbWallet);
        btnConfirm = findViewById(R.id.btnConfirm);
    }

    private void getInfo()
    {
        String email = getEmailFromSharedPreferences();

        customerDAO = new CustomerDAO(InvoiceActivity.this);
        Customer customer = customerDAO.layCustomer(email);

        txtCustomerID.setText(String.valueOf(customer.getUserid()));
        txtEmail.setText(customer.getEmai());
        edtName.setText(customer.getcustomername());
        edtPhone.setText(customer.getPhonenumber());
        edtAddress.setText(customer.getAddress());
    }

    private String getEmailFromSharedPreferences() {
        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        return sharedpreferences.getString("email", "");
    }

    private void loadCart()
    {
//        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
//        String email = sharedpreferences.getString("email", "");

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("customer_email"))
        {
            String email = intent.getStringExtra("customer_email");
            customerDAO = new CustomerDAO(InvoiceActivity.this);
            Customer customer = customerDAO.layCustomer(email);

            cartDAO = new CartDAO(InvoiceActivity.this);
            Cart cart = cartDAO.getCartByCustomer2(customer);
            //  LINEITEM =  lineitemDAO.getAllLineBycart(cart.getcartid) => (this, LINEITEM);

            if (cart != null)
            {
                listViewCartInInvoiceAdapter = new ListViewCartInInvoiceAdapter(InvoiceActivity.this, cart.getLINEITEM(), cart);
                lvInvoice.setAdapter(listViewCartInInvoiceAdapter);
                double totalAmount = cart.calculateTotalAmount();
                String totalAmountFormatted = String.format("Tổng giá: %.0fđ", totalAmount);
                txtTotal.setText(totalAmountFormatted);
            }
            else
                txtTotal.setText("Tổng giá: 0");
        }
    }

    private void addEventsForWidgets()
    {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processConfirm();
            }
        });
    }

    private void processConfirm()
    {
        Customer customer = customerDAO.layCustomer(txtEmail.getText().toString());

        String paymentMethod = "";
        if (rdbCash.isChecked())
        {
            paymentMethod = rdbCash.getText().toString().trim();
            statusDAO = new StatusDAO(this);
            Status status = statusDAO.layStatusTheoID(1);

            cartDAO = new CartDAO(InvoiceActivity.this);
            Cart cart = cartDAO.getCartByCustomer2(customer);

            double totalAmount = cart.calculateTotalAmount();

            invoiceDAO = new InvoiceDAO(this);

            Date currentDate = Calendar.getInstance().getTime();
            Invoice invoice = new Invoice(customer, currentDate, totalAmount, paymentMethod, status);

            int invoiceId = (int) invoiceDAO.themInvoice(invoice);

            int lastInsertedInvoiceId = invoiceDAO.getLastInsertedInvoiceId();

            InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO(this);
            for (LineItem lineItem : cart.getLINEITEM()) {
                InvoiceDetail invoiceDetail = new InvoiceDetail(invoice, lineItem.getFood(), lineItem.getQuantity());
                invoiceDetailDAO.themInvoiceDetail(lastInsertedInvoiceId, invoiceDetail);
            }

            CartDAO cartDAO = new CartDAO(this);
            cartDAO.deleteCart(cart);
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

            Toast.makeText(InvoiceActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(InvoiceActivity.this, ThankyouActivity.class);
            startActivity(intent);
        }
        else
            if (rdbWallet.isChecked())
            {
                paymentMethod = rdbWallet.getText().toString().trim();

                cartDAO = new CartDAO(InvoiceActivity.this);
                Cart cart = cartDAO.getCartByCustomer2(customer);
                double total = cart.calculateTotalAmount();
                double ck = total * 5 / 100;
                double totalAmount = total - ck;

                Intent intent = new Intent(InvoiceActivity.this, PaymentActivity.class);
                intent.putExtra("paymentmethod", paymentMethod);
                intent.putExtra("total", total);
                intent.putExtra("discount", ck);
                intent.putExtra("totalAmount", totalAmount);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(InvoiceActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }
    }
}