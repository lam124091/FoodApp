package com.example.finaltermproject.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Customer;
import com.example.finaltermproject.model.Size;

import java.util.ArrayList;
import java.util.List;

public class SizeDAO
{
    SQLiteDatabase db;

    public SizeDAO(Context context)
    {
        DatabaseHelper databasehelper = new DatabaseHelper(context);
        db = databasehelper.openDatabase();
    }

    public List<Size> layTatCaSize()
    {
        List<Size> SIZE = new ArrayList<>();

        String query_select_all_size = "SELECT * FROM " + DatabaseHelper.TABLE_SIZE;
        Cursor cursor = db.rawQuery(query_select_all_size, null);

        if (cursor.moveToFirst())
        {
            do
            {
                int sizeid = Integer.parseInt(cursor.getString(0));
                String sizename = cursor.getString(1);

                Size size = new Size(sizeid, sizename);
                SIZE.add(size);
            }while (cursor.moveToNext());
        }

        return SIZE;
    }
}
