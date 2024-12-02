package com.example.finaltermproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO
{
    SQLiteDatabase db;

    public CategoryDAO(Context context)
    {
        DatabaseHelper databasehelper = new DatabaseHelper(context);
        db = databasehelper.openDatabase();
    }

    public void themCategory(Category category)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(DatabaseHelper.KEY_CATEGORY_NAME, category.getCategoryname());

        db.insert(DatabaseHelper.TABLE_CATEGORY, null, contentvalues);
        //db.close();
    }

    public void xoaCategory(int categoryId)
    {
        db.delete(DatabaseHelper.TABLE_CATEGORY, DatabaseHelper.KEY_CATEGORY_ID + " = ?",
                new String[]{String.valueOf(categoryId)});
        //db.close();
    }

    public void suaCategory(Category category)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_CATEGORY_NAME, category.getCategoryname());

        db.update(DatabaseHelper.TABLE_CATEGORY, contentValues, DatabaseHelper.KEY_CATEGORY_ID + " = ?",
                new String[]{String.valueOf(category.getCategoryid())});
        //db.close();
    }

    public List<String> layTatCaCategories()
    {
        List<String> CATEGORY = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                Category category = new Category();
                category.setCategoryid(Integer.parseInt(cursor.getString(0)));
                category.setCategoryname(cursor.getString(1));

                String chuoi = category.getCategoryid()+ " - " + category.getCategoryname();
                CATEGORY.add(chuoi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return CATEGORY;
    }

    public List<Category> layTatCaCategory()
    {
        List<Category> CATEGORY = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                int categoryid = Integer.parseInt(cursor.getString(0));
                String categoryName = cursor.getString(1);

                Category category = new Category(categoryid, categoryName);
                CATEGORY.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return CATEGORY;
    }

    public boolean hasFoodsInInvoiceDetail(int categoryId)
    {
        String query = "SELECT COUNT(*) " +
                "FROM InvoiceDetail " +
                "INNER JOIN Food ON InvoiceDetail.FoodID = Food.FoodID " +
                "INNER JOIN Category ON Food.FoodCategory = Category.CategoryID " +
                "WHERE Category.CategoryID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count > 0;
    }

    public Category layCategoryTheoTen(String categoryName) {
        Category category = null;

        // Truy vấn để lấy thông tin của danh mục có tên tương ứng
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY +
                " WHERE " + DatabaseHelper.KEY_CATEGORY_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{categoryName});

        // Kiểm tra xem có dữ liệu không
        if (cursor.moveToFirst()) {
            int categoryid = Integer.parseInt(cursor.getString(0));
            String categoryNamee = cursor.getString(1);

            category = new Category(categoryid, categoryNamee);
        }

        cursor.close();

        return category;
    }


    public int laySoLuongCategory()
    {
        String countQuery = "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public Category layCategoryTheoID(int categoryId) {
        Category category = null;

        // Truy vấn để lấy thông tin của danh mục có categoryId tương ứng
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY +
                " WHERE " + DatabaseHelper.KEY_CATEGORY_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});

        // Kiểm tra xem có dữ liệu không
        if (cursor.moveToFirst()) {
            int categoryid = Integer.parseInt(cursor.getString(0));
            String categoryName = cursor.getString(1);

            category = new Category(categoryid, categoryName);
        }

        cursor.close();

        return category;
    }

}
