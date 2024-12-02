package com.example.finaltermproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Cart;
import com.example.finaltermproject.model.Category;
import com.example.finaltermproject.model.Customer;
import com.example.finaltermproject.model.Food;
import com.example.finaltermproject.model.LineItem;
import com.example.finaltermproject.model.Size;

import java.util.ArrayList;

public class CartDAO
{
    SQLiteDatabase db;
    private Context context;

    public CartDAO(Context context)
    {
        this.context = context;
        DatabaseHelper databasehelper = new DatabaseHelper(context);
        db = databasehelper.openDatabase();
    }

    public void themCartChoCustomer(Customer customer)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_CART_CUSTOMER, customer.getUserid());

        db.insert(DatabaseHelper.TABLE_CART, null, values);
    }

    public boolean isCartExitsts(Customer customer)
    {
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CART +
                " WHERE " + DatabaseHelper.KEY_CART_CUSTOMER + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(customer.getUserid())});
        boolean hasCart = cursor.getCount() > 0;
        cursor.close();
        return hasCart;
    }

    public int getCartIdByCustomer(Customer customer)
    {
        int cartId = -1;
        String query = "SELECT " + DatabaseHelper.KEY_CART_ID +
                " FROM " + DatabaseHelper.TABLE_CART +
                " WHERE " + DatabaseHelper.KEY_CART_CUSTOMER + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(customer.getUserid())});

        if (cursor.moveToFirst())
        {
            cartId = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        return cartId;
    }

    public Cart getCartByCustomer2(Customer customer)
    {
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CART +
                " WHERE " + DatabaseHelper.KEY_CART_CUSTOMER + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(customer.getUserid())});

        Cart cart = null;
        if (cursor.moveToFirst()) {
            int cartId = cursor.getInt(0);
            cart = new Cart(cartId, customer, new ArrayList <LineItem>());
            // Fetch line items for this cart
            LineItemDAO lineItemDAO = new LineItemDAO(context);
            ArrayList<LineItem> lineItems = lineItemDAO.layTatCaLineItem(cartId);
            cart.setLINEITEM(lineItems);
        }cursor.close();
        return cart;
    }

    public void deleteCart(Cart cart) {
        // Xóa các mục trong giỏ hàng
        ArrayList<LineItem> lineItems = cart.getLINEITEM();
        LineItemDAO lineItemDAO = new LineItemDAO(context);
        for (LineItem item : lineItems) {
            lineItemDAO.deleteLineItem(item);
        }

        // Xóa giỏ hàng
        db.delete(DatabaseHelper.TABLE_CART,
                DatabaseHelper.KEY_CART_ID + " = ?",
                new String[]{String.valueOf(cart.getCartid())});
    }

}
