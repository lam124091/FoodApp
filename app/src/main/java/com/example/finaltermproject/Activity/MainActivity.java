package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.R;

public class MainActivity extends AppCompatActivity
{
    private Button btnDangNhap;
    private Button btnDangKy;
    public Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFormWidgets();
        addEventsForWidgets();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();
    }

    private void getFormWidgets()
    {
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        btnSkip = (Button) findViewById(R.id.btnSkip);
    }

    private void addEventsForWidgets()
    {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten =new Intent(MainActivity.this, LoginActivity.class);
                startActivity(inten);
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten =new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(inten);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}