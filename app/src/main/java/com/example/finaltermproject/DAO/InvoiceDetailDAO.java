package com.example.finaltermproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Food;
import com.example.finaltermproject.model.InvoiceDetail;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO {
    private SQLiteDatabase db;
    private Context context;

    public InvoiceDetailDAO(Context context) {
        this.context = context;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.openDatabase();
    }

    public void themInvoiceDetail(int invoiceId, InvoiceDetail invoiceDetail) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_INVOICEDETAIL_INVOICEID, invoiceId); // Use the provided invoiceId
        values.put(DatabaseHelper.KEY_INVOICEDETAIL_FOODID, invoiceDetail.getFood().getFoodid());
        values.put(DatabaseHelper.KEY_INVOICEDETAIL_QUANTITY, invoiceDetail.getQuantity());

        db.insert(DatabaseHelper.TABLE_INVOICEDETAIL, null, values);
    }


    public ArrayList<InvoiceDetail> layInvoiceDetailTheoInvoiceID(int invoiceId) {
        ArrayList<InvoiceDetail> invoiceDetails = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INVOICEDETAIL +
                " WHERE " + DatabaseHelper.KEY_INVOICEDETAIL_INVOICEID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(invoiceId)});

        if (cursor.moveToFirst()) {
            do {
                int invoiceDetailId = cursor.getInt(0);
                int foodId = cursor.getInt(2);
                int quantity = cursor.getInt(3);

                FoodDAO foodDAO = new FoodDAO(context);
                Food food = foodDAO.layFoodByID(foodId);

                InvoiceDetail invoiceDetail = new InvoiceDetail(invoiceDetailId, null, food, quantity);
                invoiceDetails.add(invoiceDetail);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return invoiceDetails;
    }
}
