package com.example.the_frozen_spoon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.the_frozen_spoon.dbdao.Customer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class customerDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "frozen_spoon_db";
    private static final String TABLE_CUSTOMER = "tbl_customer";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstName";
    private static final String KEY_LASTNAME = "lastName";
    private static final String KEY_EMAIL = "emailId";
    private static final String KEY_PHONE = "phoneNo";
    private static final String KEY_PSW = "password";
    private static final String KEY_CNF_PSW = "confirm_password";
    private static final String KEY_CREATED_DATE = "created_date";
    private static final String KEY_IS_ACTIVE = "isActive";



    public customerDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //createCustomerTable() ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CUSTOMER);
        onCreate(db);
    }

    public void addNewCustomer(String firstName, String lastName, String emailId, String phoneNo, String password, String confirm_password) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(KEY_FIRSTNAME, firstName);
            values.put(KEY_LASTNAME, lastName);
            values.put(KEY_EMAIL, emailId);
            values.put(KEY_PHONE, phoneNo);
            values.put(KEY_PSW, password);
            values.put(KEY_CNF_PSW, confirm_password);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdf.format(c.getTime());

            values.put(KEY_CREATED_DATE,strDate);
            values.put(KEY_IS_ACTIVE,1);

            db.insert(TABLE_CUSTOMER, null, values);

            db.close();

        }
        catch (Exception e){
            Log.i("test",e.getMessage());
        }
    }

    public void createCustomerTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        try {


            String CREATE_CUSTOMERS_TABLE =
                    "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMER + "("
                            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                            + KEY_FIRSTNAME + " TEXT,"
                            + KEY_LASTNAME + " TEXT,"
                            + KEY_EMAIL + " TEXT UNIQUE,"
                            + KEY_PHONE + " TEXT,"
                            + KEY_PSW + " TEXT,"
                            + KEY_CNF_PSW + " TEXT,"
                            + KEY_CREATED_DATE + " TEXT,"
                            + KEY_IS_ACTIVE + " INTEGER"
                            + ")";

            db.execSQL(CREATE_CUSTOMERS_TABLE);
        }
        catch (Exception e){
            Log.i("test",e.getMessage());
        }
    }

    public Customer getCustomerByEmail_Psw(String emailId, String password){
        //db readable object
        Customer customer = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Log.i("test",emailId);
            Cursor cursor  = db.query(TABLE_CUSTOMER,  // The table to query
                    new String[] {  }, // The array of columns to return (pass null to get all)
                    KEY_EMAIL + "=? AND " + KEY_PSW + "=?",  // The columns for the WHERE clause
                    new String[] { String.valueOf(emailId),String.valueOf(password) },  // The values for the WHERE clause
                    null,
                    null,
                    null,
                    null);

            if (cursor.getCount() > 0){

                cursor.moveToFirst();
                customer = new Customer(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        Boolean.parseBoolean(cursor.getString(7)));

            }

        }
        catch(Exception e)
        {
            Log.i("test",e.getMessage());
        }

        return customer;
    }

    public Customer getCustomerById(int id){
        //db readable object
        Customer customer = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Log.i("test",String.valueOf(id));

            Cursor cursor  = db.query(TABLE_CUSTOMER,  // The table to query
                    new String[] {  }, // The array of columns to return (pass null to get all)
                    KEY_ID + "=? ",  // The columns for the WHERE clause
                    new String[] { String.valueOf(id) },  // The values for the WHERE clause
                    null,
                    null,
                    null,
                    null);

            if (cursor.getCount() > 0){

                cursor.moveToFirst();
                customer = new Customer(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        Boolean.parseBoolean(cursor.getString(7)));

            }

        }
        catch(Exception e)
        {
            Log.i("test",e.getMessage());
        }

        return customer;
    }

    public void truncateTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String truncateQuery ="DELETE FROM "+ TABLE_CUSTOMER;
        db.execSQL(truncateQuery);
    }
}