package com.example.the_frozen_spoon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class orderDetailDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "frozen_spoon_db";
    private static final String TABLE_ORDER = "order_detail";
    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_CUSTOMER_NAME = "customer_name";
    private static final String KEY_CARD_NUMBER = "card_number";
    private static final String KEY_CVV = "cvv";
    private static final String KEY_CARD_TYPE = "card_type";
    private static final String KEY_DATE_TIME = "date_time";
    private static final String KEY_ORDER_ADDRESS = "order_address";
    private static final String KEY_CUSTOMER_ID = "customer_id";

    public orderDetailDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
        Log.i("test","Product Constructor");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //createOrderTable();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ORDER);
        onCreate(db);
    }

    public void createOrderTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("test","Order Table");
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ORDER);
            String CREATE_ORDERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + KEY_PRODUCT_NAME + " TEXT,"
                    + KEY_PRICE + " TEXT,"
                    + KEY_CUSTOMER_NAME + " TEXT,"
                    + KEY_CARD_NUMBER + " TEXT,"
                    + KEY_CVV + " TEXT,"
                    + KEY_CARD_TYPE + " TEXT,"
                    + KEY_DATE_TIME + " DATETIME,"
                    + KEY_ORDER_ADDRESS + " TEXT, "
                    + KEY_CUSTOMER_ID + " INTERGER "
                    + ")";

            db.execSQL(CREATE_ORDERS_TABLE);
        }
        catch (Exception e){
            Log.i("test",e.getMessage());
        }
    }


    public void addNewOrder(Integer customer_id, String product_name, String price, String customer_name, String card_number,String cvv, String card_type, String address)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();


            values.put(KEY_CUSTOMER_ID, customer_id);
            values.put(KEY_PRODUCT_NAME, product_name);
            values.put(KEY_PRICE, price);
            values.put(KEY_CUSTOMER_NAME, customer_name);
            values.put(KEY_CARD_NUMBER, card_number);
            values.put(KEY_CARD_TYPE, card_type);
            values.put(KEY_ORDER_ADDRESS, address);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdf.format(c.getTime());

            values.put(KEY_DATE_TIME, strDate);
            values.put(KEY_CVV, cvv);


            db.insert(TABLE_ORDER, null, values);
            db.close();

        }
        catch (Exception e){
            Log.i("test",e.getMessage());
        }
    }
}
