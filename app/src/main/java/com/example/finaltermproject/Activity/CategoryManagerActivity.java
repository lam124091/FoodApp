package com.example.finaltermproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltermproject.Adapter.ListviewCategoryAdapter;
import com.example.finaltermproject.DAO.CategoryDAO;
import com.example.finaltermproject.DAO.FoodDAO;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Category;
import com.example.finaltermproject.model.Food;

import java.util.ArrayList;
import java.util.List;

public class CategoryManagerActivity extends AppCompatActivity
{
    private Category selectedCategory;

    private ListviewCategoryAdapter listviewcategoryadapter;
    private List<Category> CATEGORY = new ArrayList<>();
    private CategoryDAO categoryDAO;
    private List<String> list = new ArrayList<>();
    ImageButton btnBack;
    ArrayAdapter<String> arrayAdapter;
    private ListView lvCategory;
    private EditText edtCategory;
    private TextView txtsoLuong;
    private Button btnThem, btnXoa, btnSua;
    private String getId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_manager);

        getFormWidgets();
        loadCategory();
        addEventsForWidgets();
    }

    public void getFormWidgets()
    {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        lvCategory = findViewById(R.id.lvCategory);
        edtCategory = findViewById(R.id.edtCategory);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        txtsoLuong = findViewById(R.id.txtSoLuong);
    }

    private void loadCategory()
    {
//        categoryDAO = new CategoryDAO(CategoryManagerActivity.this);
//        CATEGORY = categoryDAO.layTatCaCategory();
//
//        listviewcategoryadapter = new ListviewCategoryAdapter(CategoryManagerActivity.this, R.layout.category_item_layout, CATEGORY);
//        lvCategory.setAdapter(listviewcategoryadapter);
        list.clear();
        categoryDAO = new CategoryDAO(CategoryManagerActivity.this);
        list = categoryDAO.layTatCaCategories();
        arrayAdapter = new ArrayAdapter<>(CategoryManagerActivity.this, android.R.layout.simple_list_item_1,list);
        lvCategory.setAdapter(arrayAdapter);

        txtsoLuong.setText("Số lượng category: " + categoryDAO.laySoLuongCategory());

    }

    public void addEventsForWidgets()
    {
        btnThem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                processInput();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                processDelete();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                processUpdate();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryManagerActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedUser = (String) parent.getItemAtPosition(position);

                String[] userData = selectedUser.split(" - ");
                getId = userData[0];
                edtCategory.setText(userData[1]);            }
        });

    }

    public void processInput()
    {
        String categoryname = edtCategory.getText().toString().trim();
        if (!categoryname.isEmpty())
        {
            try
            {
                Category newCategory = new Category(categoryname);
                categoryDAO = new CategoryDAO(CategoryManagerActivity.this);
                categoryDAO.themCategory(newCategory);
//                CATEGORY.clear();
//                CATEGORY.addAll(categoryDAO.layTatCaCategory());
//                listviewcategoryadapter.notifyDataSetChanged();
                updateListView();
                // Gửi danh sách category mới đến ActivityThemFood
                Intent intent = new Intent(CategoryManagerActivity.this, ThemFoodActivity.class);
//                intent.putExtra("CATEGORY_LIST", new ArrayList<>(CATEGORY));
                startActivity(intent);

                Toast.makeText(CategoryManagerActivity.this, "Thêm category thành công", Toast.LENGTH_SHORT).show();
                edtCategory.setText("");
            }
            catch (Exception e)
            {
                Toast.makeText(CategoryManagerActivity.this, "Lỗi khi thêm category: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(CategoryManagerActivity.this, "Vui lòng nhập tên category", Toast.LENGTH_SHORT).show();
    }

    public void processDelete() {
        if (selectedCategory != null)
        {
            CategoryDAO categoryDAO = new CategoryDAO(CategoryManagerActivity.this);
            if (categoryDAO.hasFoodsInInvoiceDetail(selectedCategory.getCategoryid())) {
                Toast.makeText(CategoryManagerActivity.this, "Không thể xóa danh mục này vì có món ăn tồn tại trong hóa đơn", Toast.LENGTH_SHORT).show();
                return;
            }
            // Lấy danh sách các món ăn thuộc về danh mục cần xóa
            FoodDAO foodDAO = new FoodDAO(CategoryManagerActivity.this);
            List<Food> foodsInCategory = foodDAO.getFoodsByCategory(selectedCategory.getCategoryid());

            // Xóa các món ăn trong danh sách
            for (Food food : foodsInCategory) {
                foodDAO.xoaFood(food.getFoodid());
            }

            // Xóa danh mục khỏi bảng category
            categoryDAO.xoaCategory(selectedCategory.getCategoryid());
            CATEGORY.remove(selectedCategory);
            listviewcategoryadapter.notifyDataSetChanged();

            Toast.makeText(CategoryManagerActivity.this, "Xóa danh mục và các món ăn liên quan thành công", Toast.LENGTH_SHORT).show();

            // Xóa giá trị của edtCategory và txtsoLuong
            edtCategory.setText("");
        } else {
            Toast.makeText(CategoryManagerActivity.this, "Vui lòng chọn danh mục cần xóa", Toast.LENGTH_SHORT).show();
        }
    }

    public void processUpdate()
    {
        String categoryName = edtCategory.getText().toString();
        Category category = new Category();
        category.setCategoryid(Integer.parseInt(getId));
        category.setCategoryname(categoryName);
        categoryDAO.suaCategory(category);
        Toast.makeText(CategoryManagerActivity.this, "Sửa Category thành công", Toast.LENGTH_SHORT).show();
        updateListView();
    }
    private void updateListView() {
        list.clear();
        categoryDAO = new CategoryDAO(CategoryManagerActivity.this);
        list = categoryDAO.layTatCaCategories();
        arrayAdapter = new ArrayAdapter<>(CategoryManagerActivity.this, android.R.layout.simple_list_item_1,list);
        lvCategory.setAdapter(arrayAdapter);
    }
}