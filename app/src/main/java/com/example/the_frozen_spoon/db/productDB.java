package com.example.the_frozen_spoon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.the_frozen_spoon.dbdao.Product;

import java.util.ArrayList;
import java.util.List;

public class productDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "frozen_spoon_db";
    private static final String TABLE_PRODUCT = "product";
    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_IMG = "img";
    private static final String KEY_DESCRIPTION = "description";

    public productDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
        Log.i("test","Product Constructor");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //createProductTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUCT);
        onCreate(db);
    }

    public void createProductTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("test","Product Table");
        try {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUCT);
            String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + KEY_PRODUCT_NAME + " TEXT,"
                    + KEY_PRICE + " TEXT,"
                    + KEY_IMG + " TEXT,"
                    + KEY_DESCRIPTION + " TEXT"
                    + ")";

            db.execSQL(CREATE_PRODUCTS_TABLE);
        }
        catch (Exception e){
            Log.i("test",e.getMessage());
        }
    }

    public void addNewProduct(String product_name, String price, Integer img, String description) {


        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(KEY_PRODUCT_NAME, product_name);
            values.put(KEY_PRICE, price);
            values.put(KEY_IMG, img);
            values.put(KEY_DESCRIPTION, description);

            db.insert(TABLE_PRODUCT, null, values);

            db.close();

        }
        catch (Exception e){
            Log.i("test",e.getMessage());
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setProduct_name(cursor.getString(1));//(Integer.parseInt(cursor.getString(0)));
                product.setPrice(cursor.getString(2));
                product.setImg(cursor.getInt(3));
                product.setDescription(cursor.getString(4));
                product.setId(Integer.parseInt(cursor.getString(0)));
                // Adding products to list
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // return contact list
        return productList;
    }

    public Product getProductRecord(int id){
        //db readable object
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT,  // The table to query
                new String[] { KEY_ID,
                        KEY_PRODUCT_NAME, KEY_PRICE,KEY_IMG, KEY_DESCRIPTION }, // The array of columns to return (pass null to get all)
                KEY_ID + "=?",  // The columns for the WHERE clause
                new String[] { String.valueOf(id) },  // The values for the WHERE clause
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4));

        return product;


    }
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_NAME, product.getProduct_name());
        values.put(KEY_DESCRIPTION, product.getDescription());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_IMG, product.getImg());

        // updating row
        return db.update(TABLE_PRODUCT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
    }


    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PRODUCT, KEY_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
        db.close();
    }

    public void truncateTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String truncateQuery ="DELETE FROM "+ TABLE_PRODUCT;
        db.execSQL(truncateQuery);
    }
}
