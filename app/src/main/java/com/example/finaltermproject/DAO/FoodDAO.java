package com.example.finaltermproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Category;
import com.example.finaltermproject.model.Food;
import com.example.finaltermproject.model.Size;

import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    SQLiteDatabase db;
    Context context;

    public FoodDAO(Context context)
    {
        this.context = context;
        DatabaseHelper databasehelper = new DatabaseHelper(context);
        db = databasehelper.openDatabase();
    }

    public void themFood(Food food)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(DatabaseHelper.KEY_FOOD_NAME, food.getFoodname());
        contentvalues.put(DatabaseHelper.KEY_FOOD_PRICE, food.getPrice());
        contentvalues.put(DatabaseHelper.KEY_FOOD_DESCRIPTION, food.getDescription());
        contentvalues.put(DatabaseHelper.KEY_FOOD_LIKES, food.getLikes());
        contentvalues.put(DatabaseHelper.KEY_FOOD_IMAGE, food.getImage());
        contentvalues.put(DatabaseHelper.KEY_FOOD_CATEGORY, food.getFoodcategory().getCategoryid());

        long foodId = db.insert(DatabaseHelper.TABLE_FOOD, null, contentvalues);

        // Thêm các bản ghi vào bảng food_size
        List<Size> SIZE = food.getFoodsize();
        for (Size i : SIZE)
        {
            ContentValues sizevalues = new ContentValues();
            sizevalues.put(DatabaseHelper.KEY_FOOD_FOODID, foodId); // Sử dụng foodId vừa thêm
            sizevalues.put(DatabaseHelper.KEY_SIZE_SIZEID, i.getSizeid());

            db.insert(DatabaseHelper.TABLE_FOOD_SIZE, null, sizevalues);
        }
            //db.close();
    }

    public List<Food> layFoodTheoCategory(String categoryName) {
        List<Food> foods = new ArrayList<>();

        // Truy vấn để lấy categoryId dựa trên tên của category
        CategoryDAO categoryDAO = new CategoryDAO(context);
        Category category = categoryDAO.layCategoryTheoTen(categoryName);
        if (category == null) {
            // Nếu không tìm thấy category với tên đã cho, trả về danh sách rỗng
            return foods;
        }

        int categoryId = category.getCategoryid();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FOOD +
                " WHERE " + DatabaseHelper.KEY_FOOD_CATEGORY + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            do {
                int foodId = Integer.parseInt(cursor.getString(0));
                String foodName = cursor.getString(1);
                double price = Double.parseDouble(cursor.getString(2));
                String description = cursor.getString(3);
                int likes = Integer.parseInt(cursor.getString(4));
                int categoryIdd = Integer.parseInt(cursor.getString(5));
                byte[] image = cursor.getBlob(6);

                // Lấy danh sách các kích cỡ của món ăn
                List<Size> sizes = laySizeCuaFood(foodId);

                // Tạo đối tượng Food từ dữ liệu lấy được
                Food food = new Food(foodId, foodName, price, description, likes, category, sizes, image);

                // Thêm món ăn vào danh sách
                foods.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return foods;
    }

    public List<Food> layTatCaFood()
    {
        List<Food> foods = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FOOD;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                int foodId = Integer.parseInt(cursor.getString(0));
                String foodName = cursor.getString(1);
                double price = Double.parseDouble(cursor.getString(2));
                String description = cursor.getString(3);
                int likes = Integer.parseInt(cursor.getString(4));
                int categoryId = Integer.parseInt(cursor.getString(5));
                byte[] image = cursor.getBlob(6);

                // Lấy thông tin danh mục của món ăn
                CategoryDAO categoryDAO = new CategoryDAO(context);
                Category category = categoryDAO.layCategoryTheoID(categoryId);

                // Lấy danh sách các kích cỡ của món ăn
                List<Size> sizes = laySizeCuaFood(foodId);

                // Tạo đối tượng Food từ dữ liệu lấy được
                Food food = new Food(foodId, foodName, price, description, likes, category, sizes, image);

                // Thêm món ăn vào danh sách
                foods.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return foods;
    }

    public List<Size> laySizeCuaFood(int foodId)
    {
        List<Size> sizes = new ArrayList<>();

        // Truy vấn để lấy các kích cỡ của món ăn có foodId tương ứng
        String query = "SELECT " + DatabaseHelper.TABLE_SIZE + ".* FROM " + DatabaseHelper.TABLE_SIZE +
                " INNER JOIN " + DatabaseHelper.TABLE_FOOD_SIZE + " ON " +
                DatabaseHelper.TABLE_SIZE + "." + DatabaseHelper.KEY_SIZE_ID + " = " +
                DatabaseHelper.TABLE_FOOD_SIZE + "." + DatabaseHelper.KEY_SIZE_SIZEID +
                " WHERE " + DatabaseHelper.TABLE_FOOD_SIZE + "." + DatabaseHelper.KEY_FOOD_FOODID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(foodId)});

        // Kiểm tra xem có dữ liệu không
        if (cursor.moveToFirst())
        {
            do
            {
                int sizeId = Integer.parseInt(cursor.getString(0));
                String sizeName = cursor.getString(1);

                Size size = new Size(sizeId, sizeName);
                sizes.add(size);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return sizes;
    }

    public Food layFoodByID(int foodId)
    {
        Food food = null;

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FOOD + " WHERE " +
                DatabaseHelper.KEY_FOOD_ID + " = " + foodId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            String foodName = cursor.getString(1); // Lấy dữ liệu từ cột thứ nhất (0-indexed)
            double price = cursor.getDouble(2); // Lấy dữ liệu từ cột thứ hai (0-indexed)
            String description = cursor.getString(3); // Lấy dữ liệu từ cột thứ ba (0-indexed)
            int likes = cursor.getInt(4); // Lấy dữ liệu từ cột thứ tư (0-indexed)
            int categoryId = cursor.getInt(5); // Lấy dữ liệu từ cột thứ năm (0-indexed)
            byte[] image = cursor.getBlob(6);

            // Lấy thông tin danh mục của món ăn
            CategoryDAO categoryDAO = new CategoryDAO(context);
            Category category = categoryDAO.layCategoryTheoID(categoryId);

            // Lấy danh sách các kích cỡ của món ăn
            List<Size> sizes = laySizeCuaFood(foodId);

            // Tạo đối tượng Food từ dữ liệu lấy được
            food = new Food(foodId, foodName, price, description, likes, category, sizes, image);
        }

        cursor.close();

        return food;
    }

    public List<Food> searchFoodByName(String foodName) {
        List<Food> foods = new ArrayList<>();

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_FOOD +
                " WHERE " + DatabaseHelper.KEY_FOOD_NAME + " LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + foodName + "%"});

        if (cursor.moveToFirst()) {
            do {
                int foodId = Integer.parseInt(cursor.getString(0));
                String foodNamee = cursor.getString(1);
                double price = Double.parseDouble(cursor.getString(2));
                String description = cursor.getString(3);
                int likes = Integer.parseInt(cursor.getString(4));
                int categoryId = Integer.parseInt(cursor.getString(5));
                byte[] image = cursor.getBlob(6);

                // Lấy thông tin danh mục của món ăn
                CategoryDAO categoryDAO = new CategoryDAO(context);
                Category category = categoryDAO.layCategoryTheoID(categoryId);

                // Lấy danh sách các kích cỡ của món ăn
                List<Size> sizes = laySizeCuaFood(foodId);

                // Tạo đối tượng Food từ dữ liệu lấy được
                Food food = new Food(foodId, foodNamee, price, description, likes, category, sizes, image);

                // Thêm món ăn vào danh sách
                foods.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        //db.close();
        return foods;
    }

    public boolean xoaFood(int foodId) {
        // Kiểm tra xem món ăn có trong danh sách chi tiết hóa đơn không
        if (isFoodInInvoiceDetail(foodId)) {
            // Nếu món ăn đã có trong danh sách chi tiết hóa đơn, không được phép xóa
            Toast.makeText(context, "Không thể xóa món ăn đang được sử dụng trong hóa đơn", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // Nếu món ăn không có trong danh sách chi tiết hóa đơn, thực hiện xóa
            // Xóa các LineItem chứa món ăn này trong bảng lineitem
            LineItemDAO lineItemDAO = new LineItemDAO(context);
            lineItemDAO.deleteLineItemsByFoodId(foodId);

            // Xóa món ăn khỏi bảng food
            int result = db.delete(DatabaseHelper.TABLE_FOOD,
                    DatabaseHelper.KEY_FOOD_ID + " = ?",
                    new String[]{String.valueOf(foodId)});

            // Xóa các bản ghi liên quan trong bảng food_size
            db.delete(DatabaseHelper.TABLE_FOOD_SIZE,
                    DatabaseHelper.KEY_FOOD_FOODID + " = ?",
                    new String[]{String.valueOf(foodId)});

            //db.close();
            return result > 0;
        }
    }

    public boolean isFoodInInvoiceDetail(int foodId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.isFoodInInvoiceDetail(foodId);
    }

    public List<Food> getFoodsByCategory(int categoryId) {
        List<Food> foods = new ArrayList<>();

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_FOOD +
                " WHERE " + DatabaseHelper.KEY_FOOD_CATEGORY + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            do {
                int foodId = cursor.getInt(0);
                String foodName = cursor.getString(1);
                double price = cursor.getDouble(2);
                String description = cursor.getString(3);
                int likes = cursor.getInt(4);
                int categoryIdFromDB = cursor.getInt(5);
                byte[] image = cursor.getBlob(6);

                CategoryDAO categoryDAO = new CategoryDAO(context);
                Category category = categoryDAO.layCategoryTheoID(categoryId);

                // Lấy danh sách các kích cỡ của món ăn
                List<Size> sizes = laySizeCuaFood(foodId);

                Food food = new Food(foodId, foodName, price, description, likes, category, sizes, image);
                foods.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return foods;
    }
}