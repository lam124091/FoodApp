package com.example.finaltermproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Food;
import com.example.finaltermproject.model.LineItem;

import java.util.ArrayList;
import java.util.List;

public class LineItemDAO
{
    SQLiteDatabase db;
    private Context context;
    public LineItemDAO(Context context)
    {this.context = context;
        DatabaseHelper databasehelper = new DatabaseHelper(context);
        db = databasehelper.openDatabase();
    }

    public void insertLineItem(LineItem lineItem)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_LINEITEM_FOOD, lineItem.getFood().getFoodid());
        values.put(DatabaseHelper.KEY_LINEITEM_QUANTITY, lineItem.getQuantity());

        db.insert(DatabaseHelper.TABLE_LINEITEM, null, values);


    }

    public void insertCartLineItem(int cartId, int lineItemId)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_CART_CARTID, cartId);
        values.put(DatabaseHelper.KEY_LINEITEM_LINEITEMID, lineItemId);

        db.insert(DatabaseHelper.TABLE_CART_LINEITEM, null, values);
    }

    public int getLastInsertedLineItemId() {
        int lastInsertedId = -1;
        String query = "SELECT last_insert_rowid()";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastInsertedId = cursor.getInt(0);
            cursor.close();
        }
        return lastInsertedId;
    }

    public void updateLineItemQuantity(LineItem item)
    {
        ContentValues values = new ContentValues();
        //values.put(DatabaseHelper.KEY_LINEITEM_FOOD, item.getFood().getFoodid());
        values.put(DatabaseHelper.KEY_LINEITEM_QUANTITY, item.getQuantity());

        db.update(DatabaseHelper.TABLE_LINEITEM, values, DatabaseHelper.KEY_LINEITEM_ID + " = ?",
                new String[]{String.valueOf(item.getLineitemid())});
    }

    public void deleteLineItem(LineItem item) {
        db.delete(DatabaseHelper.TABLE_LINEITEM, DatabaseHelper.KEY_LINEITEM_ID + " = ?",
                new String[]{String.valueOf(item.getLineitemid())});
    }


    public void themLineItem(LineItem lineItem, int cartId, int lineItemId )
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_LINEITEM_FOOD, lineItem.getFood().getFoodid());
        values.put(DatabaseHelper.KEY_LINEITEM_QUANTITY, lineItem.getQuantity());

        db.insert(DatabaseHelper.TABLE_LINEITEM, null, values);

        ContentValues valuesc = new ContentValues();
        valuesc.put(DatabaseHelper.KEY_CART_CARTID, cartId);
        valuesc.put(DatabaseHelper.KEY_LINEITEM_LINEITEMID, lineItemId);

        db.insert(DatabaseHelper.TABLE_CART_LINEITEM, null, values);
    }

    public ArrayList<LineItem> layTatCaLineItem(int cartId) {
        ArrayList<LineItem> lineItems = new ArrayList<>();
        String query = "SELECT l.* " +
                "FROM " + DatabaseHelper.TABLE_LINEITEM + " l " +
                "INNER JOIN " + DatabaseHelper.TABLE_CART_LINEITEM + " cl " +
                "ON l." + DatabaseHelper.KEY_LINEITEM_ID + " = cl." + DatabaseHelper.KEY_LINEITEM_LINEITEMID + " " +
                "WHERE cl." + DatabaseHelper.KEY_CART_CARTID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cartId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int lineItemId = cursor.getInt(0);
                int foodId = cursor.getInt(1); // Lấy dữ liệu từ cột thứ hai (0-indexed)
                int quantity = cursor.getInt(2);

                // Lấy thông tin về món ăn từ bảng food
                FoodDAO foodDAO = new FoodDAO(this.context);
                Food food = foodDAO.layFoodByID(foodId);

                LineItem lineItem = new LineItem(lineItemId, food, quantity);
                lineItems.add(lineItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lineItems;
    }

    public boolean existsLineItemWithFoodAndSize(int foodId, int sizeId) {
        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_LINEITEM +
                " WHERE " + DatabaseHelper.KEY_LINEITEM_FOOD + " = ? " +
                "AND EXISTS (SELECT 1 FROM " + DatabaseHelper.TABLE_FOOD_SIZE +
                " WHERE " + DatabaseHelper.TABLE_FOOD_SIZE + "." + DatabaseHelper.KEY_FOOD_FOODID + " = ? " +
                "AND " + DatabaseHelper.TABLE_FOOD_SIZE + "." + DatabaseHelper.KEY_SIZE_SIZEID + " = ?)";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(foodId), String.valueOf(foodId), String.valueOf(sizeId)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count > 0;
    }

    public void deleteLineItemsByFoodId(int foodId) {
        // Lấy danh sách các LineItem chứa món ăn có foodId tương ứng
        ArrayList<LineItem> lineItems = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_LINEITEM +
                " WHERE " + DatabaseHelper.KEY_LINEITEM_FOOD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(foodId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int lineItemId = cursor.getInt(0);
                int foodIdFromDB = cursor.getInt(1);
                int quantity = cursor.getInt(2);

                // Lấy thông tin về món ăn từ bảng food
                FoodDAO foodDAO = new FoodDAO(context);
                Food food = foodDAO.layFoodByID(foodIdFromDB);

                LineItem lineItem = new LineItem(lineItemId, food, quantity);
                lineItems.add(lineItem);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Xóa các LineItem tìm được
        for (LineItem item : lineItems) {
            db.delete(DatabaseHelper.TABLE_LINEITEM,
                    DatabaseHelper.KEY_LINEITEM_ID + " = ?",
                    new String[]{String.valueOf(item.getLineitemid())});
        }
    }

}
