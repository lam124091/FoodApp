package com.example.finaltermproject.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusDAO {
    private SQLiteDatabase db;
    private Context context;

    public StatusDAO(Context context) {
        this.context = context;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.openDatabase();
    }

    public Status layStatusTheoID(int statusId) {
        Cursor cursor = db.query(DatabaseHelper.TABLE_STATUS, new String[] {
                DatabaseHelper.KEY_STATUS_ID,
                DatabaseHelper.KEY_STATUS_NAME
        }, DatabaseHelper.KEY_STATUS_ID + "=?", new String[] {String.valueOf(statusId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Status status = new Status(id, name);
            cursor.close();
            return status;
        } else {
            cursor.close();
            return null;
        }
    }

    public List<Status> layTatCaStatus() {
        List<Status> statusList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_STATUS, new String[] {
                DatabaseHelper.KEY_STATUS_ID,
                DatabaseHelper.KEY_STATUS_NAME
        }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Status status = new Status(id, name);
                statusList.add(status);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return statusList;
    }
}