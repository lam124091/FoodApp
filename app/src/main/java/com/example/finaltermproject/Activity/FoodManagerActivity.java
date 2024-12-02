package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.finaltermproject.Adapter.ListviewFoodAdapter;
import com.example.finaltermproject.DAO.FoodDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodManagerActivity extends AppCompatActivity
{
    ListviewFoodAdapter lvfoodadapter;
    private List<Food> FOOD = new ArrayList<>();
    FoodDAO foodDAO;
    ImageButton btnBack;
    Button btnThem;
    ListView lvFood;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_manager);

        getFormWidgets();
        loadFood();
        addEventsForWidgets();
    }

    public void getFormWidgets()
    {
        btnThem = (Button) findViewById(R.id.btnThem);
        lvFood = (ListView) findViewById(R.id.lvFood);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    public void loadFood()
    {
        foodDAO = new FoodDAO(FoodManagerActivity.this);
        FOOD = foodDAO.layTatCaFood();

        lvfoodadapter = new ListviewFoodAdapter(FoodManagerActivity.this, FOOD);
        lvFood.setAdapter(lvfoodadapter);
    }

    public void addEventsForWidgets()
    {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodManagerActivity.this, ThemFoodActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodManagerActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }
}