package com.example.finaltermproject.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public DatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase openDatabase()
    {
        return this.getWritableDatabase();
    }

    // Tên cơ sở dữ liệu và version
    public static final String DATABASE_NAME = "finaltermproject";
    public static final int DATABASE_VERSION = 1;

    // Tên các bảng trong cơ sở dữ liệu
    public static final String TABLE_CUSTOMER = "customer";
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_FOOD = "food";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_SIZE = "size";
    public static final String TABLE_FOOD_SIZE = "food_size";
    public static final String TABLE_CART = "cart";
    public static final String TABLE_LINEITEM = "lineitem";
    public static final String TABLE_CART_LINEITEM = "cart_lineitem";
    public static final String TABLE_INVOICE = "invoice";
    public static final String TABLE_STATUS = "status";
    public static final String TABLE_INVOICEDETAIL = "invoicedetail";
    public static final String TABLE_WISHLIST = "wishlist";
    public static final String TABLE_WISHLIST_FOOD = "wishlist_food";

    // Tên các cột trong mỗi bảng
    public static final String KEY_CUSTOMER_ID = "customerid";
    public static final String KEY_CUSTOMER_EMAIL = "customeremail";
    public static final String KEY_CUSTOMER_PASSWORD = "customerpassword";
    public static final String KEY_CUSTOMER_NAME = "customername";
    public static final String KEY_CUSTOMER_ADDRESS = "address";
    public static final String KEY_CUSTOMER_PHONENUMBER = "phonenumber";

    public static final String KEY_ADMIN_ID = "adminid";
    public static final String KEY_ADMIN_EMAIL = "adminemail";
    public static final String KEY_ADMIN_PASSWORD = "adminpassword";
    public static final String KEY_ADMIN_NAME = "adminname";

    public static final String KEY_FOOD_ID = "foodid";
    public static final String KEY_FOOD_NAME = "foodname";
    public static final String KEY_FOOD_PRICE = "price";
    public static final String KEY_FOOD_DESCRIPTION = "description";
    public static final String KEY_FOOD_LIKES = "likes";
    public static final String KEY_FOOD_IMAGE = "image";
    public static final String KEY_FOOD_CATEGORY = "foodcategory";

    public static final String KEY_CATEGORY_ID = "categoryid";
    public static final String KEY_CATEGORY_NAME = "categoryname";

    public static final String KEY_SIZE_ID = "sizeid";
    public static final String KEY_SIZE_NAME = "sizename";

    public static final String KEY_FOOD_FOODID = "food_foodid";
    public static final String KEY_SIZE_SIZEID =  "size_sizeid";

    public static final String KEY_CART_ID = "cartid";
    public static final String KEY_CART_CUSTOMER = "cartcustomer";

    public static final String KEY_LINEITEM_ID = "lineitemid";
    public static final String KEY_LINEITEM_FOOD = "lineitemfood";
    public static final String KEY_LINEITEM_QUANTITY = "quantity";

    public static final String KEY_CART_CARTID = "cart_cartid";
    public static final String KEY_LINEITEM_LINEITEMID = "lineitem_lineitemid";

    public static final String KEY_INVOICE_ID = "invoiceid";
    public static final String KEY_INVOICE_CUSTOMERID = "customerid";
    public static final String KEY_INVOICE_DATE = "date";
    public static final String KEY_INVOICE_TOTALMONEY = "totalmoney";
    public static final String KEY_INVOICE_PAYMENTMETHOD = "paymentmethod";
    public static final String KEY_INVOICE_STATUSID = "statusid";

    public static final String KEY_STATUS_ID = "statusid";
    public static final String KEY_STATUS_NAME = "statusname";

    public static final String KEY_INVOICEDETAIL_ID = "invoicedetailid";
    public static final String KEY_INVOICEDETAIL_INVOICEID = "invoiceid";
    public static final String KEY_INVOICEDETAIL_FOODID = "foodid";
    public static final String KEY_INVOICEDETAIL_QUANTITY = "quantity";

    public static final String KEY_WISHLIST_ID = "wishlistid";
    public static final String KEY_WISHLIST_CUSTOMERID = "customerid";

    public static final String KEY_WISHLIST_WISHLISTID = "wishlist_wishlistid";
    public static final String KEY_FOOD_FOOD_ID = "food_foodid";

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query_to_create_customer_table = "CREATE TABLE " + TABLE_CUSTOMER + "("
                + KEY_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CUSTOMER_EMAIL + " TEXT,"
                + KEY_CUSTOMER_PASSWORD + " TEXT,"
                + KEY_CUSTOMER_NAME + " TEXT,"
                + KEY_CUSTOMER_ADDRESS + " TEXT,"
                + KEY_CUSTOMER_PHONENUMBER + " TEXT" + ")";
        db.execSQL(query_to_create_customer_table);

        String query_to_create_admin_table = "CREATE TABLE " + TABLE_ADMIN + "("
                + KEY_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ADMIN_EMAIL + " TEXT,"
                + KEY_ADMIN_PASSWORD + " TEXT,"
                + KEY_ADMIN_NAME + " TEXT" + ")";
        db.execSQL(query_to_create_admin_table);

        String query_to_create_food_table = "CREATE TABLE " + TABLE_FOOD + "("
                + KEY_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FOOD_NAME + " TEXT,"
                + KEY_FOOD_PRICE + " REAL,"
                + KEY_FOOD_DESCRIPTION + " TEXT,"
                + KEY_FOOD_LIKES + " INTEGER,"
                + KEY_FOOD_CATEGORY + " INTEGER,"
                + KEY_FOOD_IMAGE + " BLOB,"
                + "FOREIGN KEY(" + KEY_FOOD_CATEGORY + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_CATEGORY_ID + ") ON DELETE CASCADE" + ")";
        db.execSQL(query_to_create_food_table);

        String query_to_create_category_table = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CATEGORY_NAME + " TEXT" + ")";
        db.execSQL(query_to_create_category_table);

        String query_to_create_size_table = "CREATE TABLE " + TABLE_SIZE + "("
                + KEY_SIZE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_SIZE_NAME + " TEXT" + ")";
        db.execSQL(query_to_create_size_table);

        String query_to_create_food_size_table = "CREATE TABLE " + TABLE_FOOD_SIZE + "("
                + KEY_FOOD_FOODID + " INTEGER,"
                + KEY_SIZE_SIZEID + " INTEGER,"
                + "PRIMARY KEY (" + KEY_FOOD_FOODID + ", " + KEY_SIZE_SIZEID + "),"
                + "FOREIGN KEY(" + KEY_FOOD_FOODID + ") REFERENCES " + TABLE_FOOD + "(" + KEY_FOOD_ID + ") ON DELETE CASCADE,"
                + "FOREIGN KEY(" + KEY_SIZE_SIZEID + ") REFERENCES " + TABLE_SIZE + "(" + KEY_SIZE_ID + ") ON DELETE NO ACTION"
                + ")";
        db.execSQL(query_to_create_food_size_table);

        String query_to_create_cart_table = "CREATE TABLE " + TABLE_CART + "("
                + KEY_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CART_CUSTOMER + " INTEGER,"
                + "FOREIGN KEY(" + KEY_CART_CUSTOMER + ") REFERENCES " + TABLE_CUSTOMER + "(" + KEY_CUSTOMER_ID + ")"
                + ")";
        db.execSQL(query_to_create_cart_table);

        String query_to_create_lineitem_table = "CREATE TABLE " + TABLE_LINEITEM + "("
                + KEY_LINEITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LINEITEM_FOOD + " INTEGER,"
                + KEY_LINEITEM_QUANTITY + " INTEGER,"
                + "FOREIGN KEY(" + KEY_LINEITEM_FOOD + ") REFERENCES " + TABLE_FOOD + "(" + KEY_FOOD_ID + ")"
                + ")";
        db.execSQL(query_to_create_lineitem_table);

        String query_to_create_cart_lineitem_table = "CREATE TABLE " + TABLE_CART_LINEITEM + "("
                + KEY_CART_CARTID + " INTEGER,"
                + KEY_LINEITEM_LINEITEMID + " INTEGER,"
                + "PRIMARY KEY (" + KEY_CART_CARTID + ", " + KEY_LINEITEM_LINEITEMID + "),"
                + "FOREIGN KEY(" + KEY_CART_CARTID + ") REFERENCES " + TABLE_CART + "(" + KEY_CART_ID + "),"
                + "FOREIGN KEY(" + KEY_LINEITEM_LINEITEMID + ") REFERENCES " + TABLE_LINEITEM + "(" + KEY_LINEITEM_ID + ")"
                + ")";
        db.execSQL(query_to_create_cart_lineitem_table);

        String query_to_create_invoice_table = "CREATE TABLE " + TABLE_INVOICE + "("
                + KEY_INVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_INVOICE_CUSTOMERID + " INTEGER,"
                + KEY_INVOICE_DATE + " TEXT,"
                + KEY_INVOICE_TOTALMONEY + " REAL,"
                + KEY_INVOICE_PAYMENTMETHOD + " TEXT,"
                + KEY_INVOICE_STATUSID + " INTEGER,"
                + "FOREIGN KEY(" + KEY_INVOICE_CUSTOMERID + ") REFERENCES " + TABLE_CUSTOMER + "(" + KEY_CUSTOMER_ID + "),"
                + "FOREIGN KEY(" + KEY_INVOICE_STATUSID + ") REFERENCES " + TABLE_STATUS + "(" + KEY_STATUS_ID + ")"
                + ")";
        db.execSQL(query_to_create_invoice_table);

        String query_to_create_status_table = "CREATE TABLE " + TABLE_STATUS + "("
                + KEY_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_STATUS_NAME + " TEXT" + ")";
        db.execSQL(query_to_create_status_table);

        String query_to_create_invoicedetail_table = "CREATE TABLE " + TABLE_INVOICEDETAIL + "("
                + KEY_INVOICEDETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_INVOICEDETAIL_INVOICEID + " INTEGER,"
                + KEY_INVOICEDETAIL_FOODID + " INTEGER,"
                + KEY_INVOICEDETAIL_QUANTITY + " INTEGER,"
                + "FOREIGN KEY(" + KEY_INVOICEDETAIL_INVOICEID + ") REFERENCES " + TABLE_INVOICE + "(" + KEY_INVOICE_ID + "),"
                + "FOREIGN KEY(" + KEY_INVOICEDETAIL_FOODID + ") REFERENCES " + TABLE_FOOD + "(" + KEY_FOOD_ID + ")"
                + ")";
        db.execSQL(query_to_create_invoicedetail_table);

        String query_to_create_wishlist_table = "CREATE TABLE " + TABLE_WISHLIST + "("
                + KEY_WISHLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_WISHLIST_CUSTOMERID + " INTEGER,"
                + "FOREIGN KEY(" + KEY_WISHLIST_CUSTOMERID + ") REFERENCES " + TABLE_CUSTOMER + "(" + KEY_CUSTOMER_ID + ")"
                + ")";
        db.execSQL(query_to_create_wishlist_table);

        String query_to_create_wishlist_food_table = "CREATE TABLE " + TABLE_WISHLIST_FOOD + "("
                + KEY_WISHLIST_WISHLISTID + " INTEGER,"
                + KEY_FOOD_FOOD_ID + " INTEGER,"
                + "PRIMARY KEY (" + KEY_WISHLIST_WISHLISTID + ", " + KEY_FOOD_FOOD_ID + "),"
                + "FOREIGN KEY(" + KEY_WISHLIST_WISHLISTID + ") REFERENCES " + TABLE_WISHLIST + "(" + KEY_WISHLIST_ID + "),"
                + "FOREIGN KEY(" + KEY_FOOD_FOOD_ID + ") REFERENCES " + TABLE_FOOD + "(" + KEY_FOOD_ID + ")"
                + ")";
        db.execSQL(query_to_create_wishlist_food_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIZE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_SIZE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINEITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_LINEITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICEDETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST_FOOD);

        onCreate(db);
    }

    public boolean isFoodInInvoiceDetail(int foodId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_INVOICEDETAIL + " WHERE " + KEY_INVOICEDETAIL_FOODID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(foodId)});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
