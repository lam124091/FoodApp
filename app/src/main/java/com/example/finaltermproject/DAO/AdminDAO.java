package com.example.finaltermproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Admin;
import com.example.finaltermproject.model.Customer;

public class AdminDAO
{
    SQLiteDatabase db;

    public AdminDAO(Context context)
    {
        DatabaseHelper databasehelper = new DatabaseHelper(context);
        db = databasehelper.openDatabase();
    }

    public void suaAdmin(Admin admin)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(DatabaseHelper.KEY_ADMIN_EMAIL, admin.getEmai());
        contentvalues.put(DatabaseHelper.KEY_ADMIN_PASSWORD, admin.getPassword());
        contentvalues.put(DatabaseHelper.KEY_ADMIN_NAME, admin.getAdminname());

        db.update(DatabaseHelper.TABLE_ADMIN, contentvalues, DatabaseHelper.KEY_ADMIN_ID + "=?",
                new String[]{String.valueOf(admin.getUserid())});
        db.close();
    }

    public boolean checkTKAdmin(String email, String password)
    {
        String query_to_check_admin_account =
                "SELECT * FROM " + DatabaseHelper.TABLE_ADMIN
                + " WHERE " + DatabaseHelper.KEY_ADMIN_EMAIL
                + " = ? AND "+ DatabaseHelper.KEY_ADMIN_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(query_to_check_admin_account, new String[]{email, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();

        return exists;
    }
}
