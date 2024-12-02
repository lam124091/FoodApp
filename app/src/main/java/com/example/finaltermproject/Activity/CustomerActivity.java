package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltermproject.Adapter.GridViewFoodAdapter;
import com.example.finaltermproject.Adapter.SpinnerCategoryAdapter;
import com.example.finaltermproject.DAO.CategoryDAO;
import com.example.finaltermproject.DAO.CustomerDAO;
import com.example.finaltermproject.DAO.FoodDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Category;
import com.example.finaltermproject.model.Customer;
import com.example.finaltermproject.model.Food;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity
{
    SearchView searchView;
    GridViewFoodAdapter grdfoodadapter;
    private List<Food> FOOD = new ArrayList<>();
    private List<Category> CATEGORY = new ArrayList<>();
    FoodDAO foodDAO;
    CategoryDAO categoryDAO;
    SpinnerCategoryAdapter spncategoryadapter;
    Spinner spnCategory;
    CustomerDAO customerDAO;
    EditText edtSearchFood;
    TextView txtCustomerName;
    Button btnOptions, btnCart, btnTimKiem, btnSearch, btnHome;
    GridView grdFood;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        getFormWidgets();
        loadCATEGORY();
        getInfo();
        loadFood();
        addEventsForWidgets();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFoodByName();
            }
        });

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String selectedCategory = ((Category) parent.getItemAtPosition(position)).getCategoryname();
                        System.out.println(selectedCategory);

                        foodDAO = new FoodDAO(CustomerActivity.this);
                        FOOD = foodDAO.layFoodTheoCategory(selectedCategory);

                        grdfoodadapter = new GridViewFoodAdapter(CustomerActivity.this, FOOD);
                        grdFood.setAdapter(grdfoodadapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Xử lý khi không có mục nào được chọn
                        loadFood();
                    }
                });
            }
        });
    }

    public void loadCATEGORY()
    {
        categoryDAO = new CategoryDAO(this);
        CATEGORY = categoryDAO.layTatCaCategory();

        spncategoryadapter = new SpinnerCategoryAdapter(this, android.R.layout.simple_spinner_item, CATEGORY);
        spncategoryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(spncategoryadapter);
    }

    private void searchFoodByName() {
        // Lấy tên sản phẩm từ EditText
        String foodName = edtSearchFood.getText().toString().trim();

        // Kiểm tra xem người dùng đã nhập tên sản phẩm hay chưa
        if (foodName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi hàm tìm kiếm sản phẩm theo tên từ FoodDAO
        List<Food> searchedFoodList = foodDAO.searchFoodByName(foodName);

        // Kiểm tra kết quả tìm kiếm
        if (searchedFoodList.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị danh sách sản phẩm tìm được trong GridView
        grdfoodadapter = new GridViewFoodAdapter(this, searchedFoodList);
        grdFood.setAdapter(grdfoodadapter);
    }

    private void getFormWidgets()
    {
        txtCustomerName = (TextView) findViewById(R.id.txtCustomerName);
        grdFood = (GridView) findViewById(R.id.grdFood);
        btnOptions = (Button) findViewById(R.id.btnOptions);
        btnCart = (Button) findViewById(R.id.btnCart);
        edtSearchFood = findViewById(R.id.edtSearchFood);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnTimKiem = (Button) findViewById(R.id.btnTimKiem);
        spnCategory = findViewById(R.id.spnCategory);
        btnHome = (Button) findViewById(R.id.btnHome);
    }

    private void getInfo()
    {
        String email = getEmailFromSharedPreferences();

        customerDAO = new CustomerDAO(CustomerActivity.this);
        Customer customer = customerDAO.layCustomer(email);

        txtCustomerName.setText(customer.getcustomername());
    }

    private void loadFood()
    {
        foodDAO = new FoodDAO(CustomerActivity.this);
        FOOD = foodDAO.layTatCaFood();

        grdfoodadapter = new GridViewFoodAdapter(CustomerActivity.this, FOOD);
        grdFood.setAdapter(grdfoodadapter);
    }

    private String getEmailFromSharedPreferences() {
        SharedPreferences sharedpreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        return sharedpreferences.getString("email", "");
    }

    private void addEventsForWidgets()
    {
        final String email = getEmailFromSharedPreferences();
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });
        grdFood.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                processViewFoodDetail(position);
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartIntent = new Intent(CustomerActivity.this, CartActivity.class);
                cartIntent.putExtra("customer_email", email);
                startActivity(cartIntent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFood();
            }
        });
    }

    public  void processViewFoodDetail(int position)
    {
        Food selectedfood = FOOD.get(position);

        Intent intent = new Intent(CustomerActivity.this, FoodDetailActivity.class);
        intent.putExtra("foodid", selectedfood.getFoodid());
        intent.putExtra("foodname", selectedfood.getFoodname());
        intent.putExtra("price", selectedfood.getPrice());
        intent.putExtra("description", selectedfood.getDescription());
        intent.putExtra("likes", selectedfood.getLikes());
        intent.putExtra("foodcategory", (Serializable) selectedfood.getFoodcategory());
        intent.putExtra("SIZE", (Serializable) selectedfood.getFoodsize());
        intent.putExtra("image", selectedfood.getImage());
        startActivity(intent);
    }

    public void showOptions()
    {
        PopupMenu popupmenu = new PopupMenu(CustomerActivity.this, btnOptions);
        popupmenu.getMenuInflater().inflate(R.menu.customer_options_menu, popupmenu.getMenu());

        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int itemId = item.getItemId();
                if (itemId == R.id.mnuViewProfile)
                {
                    Intent profileIntent = new Intent(CustomerActivity.this, CustomerProfileActivity.class);
                    startActivity(profileIntent);
                    return true;
                }
                else
                    if (itemId == R.id.mnuLogout)
                    {
                        logout();
                        return true;
                    }
                else
                    if (itemId == R.id.mnuHistory)
                    {
                        Intent historyIntent = new Intent(CustomerActivity.this, CustomerInvoiceActivity.class);
                        startActivity(historyIntent);
                        return true;
                    }
                return false;
            }
        });
        popupmenu.show();
    }

    private void logout()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("customerinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent loginIntent = new Intent(CustomerActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}