package com.example.finaltermproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaltermproject.Database.DatabaseHelper;
import com.example.finaltermproject.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAO
{
    SQLiteDatabase db;

    public CustomerDAO(Context context)
    {
        DatabaseHelper databasehelper = new DatabaseHelper(context);
        db = databasehelper.openDatabase();
    }

    public void themCustomer(Customer customer)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_EMAIL, customer.getEmai());
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_PASSWORD, customer.getPassword());
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_NAME, customer.getcustomername());
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_ADDRESS, customer.getAddress());
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_PHONENUMBER, customer.getPhonenumber());

        db.insert(DatabaseHelper.TABLE_CUSTOMER, null, contentvalues);
        db.close();
    }

    public void xoaCustomer(Customer customer)
    {
        db.delete(DatabaseHelper.TABLE_CUSTOMER, DatabaseHelper.KEY_CUSTOMER_ID + "=?",
                new String[]{String.valueOf(customer.getUserid())});
        db.close();
    }

    public void suaCustomer(Customer customer)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_EMAIL, customer.getEmai());
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_PASSWORD, customer.getPassword());
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_NAME, customer.getcustomername());
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_ADDRESS, customer.getAddress());
        contentvalues.put(DatabaseHelper.KEY_CUSTOMER_PHONENUMBER, customer.getPhonenumber());

        db.update(DatabaseHelper.TABLE_CUSTOMER, contentvalues, DatabaseHelper.KEY_CUSTOMER_EMAIL + "=?",
                new String[]{String.valueOf(customer.getEmai())});
        //db.close();
    }

    public List<Customer> layTatCaCustomer()
    {
        List<Customer> CUSTOMER = new ArrayList<>();

        String query_select_all_congviec = "SELECT * FROM " + DatabaseHelper.TABLE_CUSTOMER;
        Cursor cursor = db.rawQuery(query_select_all_congviec, null);

        if (cursor.moveToFirst())
        {
            do
            {
                int customerid = Integer.parseInt(cursor.getString(0));
                String email = cursor.getString(1);
                String password = cursor.getString(2);
                String customername = cursor.getString(3);
                String address = cursor.getString(4);
                String phonenumber = cursor.getString(5);

                Customer customer = new Customer(customerid, email, password, customername, address, phonenumber);
                CUSTOMER.add(customer);
            }while (cursor.moveToNext());
        }

        return CUSTOMER;
    }

    public List<String> layTatCaCustomers()
    {
        List<String> CUSTOMER = new ArrayList<>();

        String query_select_all_congviec = "SELECT * FROM " + DatabaseHelper.TABLE_CUSTOMER;
        Cursor cursor = db.rawQuery(query_select_all_congviec, null);

        if (cursor.moveToFirst())
        {
            do
            {   Customer customer = new Customer();

                customer.setUserid(Integer.parseInt(cursor.getString(0)));
                customer.setEmai(cursor.getString(1));//đọc dữ liệu trường masp và đưa vào đối tượng
                customer.setPassword(cursor.getString(2));//đọc dữ liệu trường tensp và đưa vào đối tượng
                customer.setcustomername(cursor.getString(3));//đọc dữ liệu trường tensp và đưa vào đối tượng
                customer.setAddress(cursor.getString(4));//đọc dữ liệu trường tensp và đưa vào đối tượng
                customer.setPhonenumber(cursor.getString(5));//đọc dữ liệu trường soluongSP và đưa vào đối tượng
                //CHuyển đối tượng thành chuỗi
                String chuoi = customer.getEmai()+ " - " + customer.getcustomername() + " - " + customer.getPassword()+ " - " + customer.getAddress() + " - " + customer.getPhonenumber();
                CUSTOMER.add(chuoi);
            }while (cursor.moveToNext());
        }
        cursor.close();//đóng con trỏ
        return CUSTOMER;
    }

    public int soLuongCustomer()
    {
        return 0;
    }

    public boolean checkTKCustomer(String email, String password)
    {
        String query_to_check_admin_account =
                "SELECT * FROM " + DatabaseHelper.TABLE_CUSTOMER
                +" WHERE " + DatabaseHelper.KEY_CUSTOMER_EMAIL
                + " = ? AND " + DatabaseHelper.KEY_CUSTOMER_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query_to_check_admin_account, new String[]{email, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();

        return exists;
    }

    public Customer layCustomer(String email)
    {
        Cursor cursor = db.query(DatabaseHelper.TABLE_CUSTOMER, new String[]
                {
                DatabaseHelper.KEY_CUSTOMER_ID,
                DatabaseHelper.KEY_CUSTOMER_EMAIL,
                DatabaseHelper.KEY_CUSTOMER_PASSWORD,
                DatabaseHelper.KEY_CUSTOMER_NAME,
                DatabaseHelper.KEY_CUSTOMER_ADDRESS,
                DatabaseHelper.KEY_CUSTOMER_PHONENUMBER
        }, DatabaseHelper.KEY_CUSTOMER_EMAIL + "=?", new String[]{email}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Customer customer = new Customer(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );
        cursor.close();

        //db.close();
        return customer;
    }

    public Customer layCustomerByID(int customerId) {
        Cursor cursor = db.query(DatabaseHelper.TABLE_CUSTOMER, new String[] {
                DatabaseHelper.KEY_CUSTOMER_ID,
                DatabaseHelper.KEY_CUSTOMER_EMAIL,
                DatabaseHelper.KEY_CUSTOMER_PASSWORD,
                DatabaseHelper.KEY_CUSTOMER_NAME,
                DatabaseHelper.KEY_CUSTOMER_ADDRESS,
                DatabaseHelper.KEY_CUSTOMER_PHONENUMBER
        }, DatabaseHelper.KEY_CUSTOMER_ID + "=?", new String[] {String.valueOf(customerId)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int customerid = cursor.getInt(0);
            String email = cursor.getString(1);
            String password = cursor.getString(2);
            String name = cursor.getString(3);
            String address = cursor.getString(4);
            String phone = cursor.getString(5);

            Customer customer = new Customer(customerid, email, password, name, address, phone);
            cursor.close();
            return customer;
        } else {
            cursor.close();
            return null;
        }
    }

}
