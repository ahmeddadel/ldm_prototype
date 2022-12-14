package com.dolla.e_commerce.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.dolla.e_commerce.data.LDMDatabaseContract.AuthenticationEntry;
import com.dolla.e_commerce.data.LDMDatabaseContract.ProductEntry;
import com.dolla.e_commerce.model.Product;
import com.dolla.e_commerce.model.User;
import com.dolla.e_commerce.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LDMOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LDM.db";
    public static final int DATABASE_VERSION = 1;
    private final Context context;

    // instate database for singleton pattern
    private static LDMOpenHelper instance;

    // singleton pattern to prevent multiple instances of database opening at the same time
    public static synchronized LDMOpenHelper getInstance(Context context) {
        if (instance == null)
            instance = new LDMOpenHelper(context.getApplicationContext());
        return instance;
    }

    // private constructor to prevent instantiation
    private LDMOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the authentication table
        db.execSQL(AuthenticationEntry.SQL_CREATE_TABLE);
        db.execSQL(AuthenticationEntry.SQL_CREATE_INDEX);

        // Create the product table
        db.execSQL(ProductEntry.SQL_CREATE_TABLE);
        db.execSQL(ProductEntry.SQL_CREATE_INDEX);

        // Initialize the database with some product data
        initializeDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the tables and re-create it
        db.execSQL("DROP TABLE IF EXISTS " + AuthenticationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);

        onCreate(db);
    }

    private void initializeDatabase(SQLiteDatabase db) {
        List<Product> products = null;
        if (context != null) {
            // Read the product data from the JSON file
            products = new Gson().fromJson(Utilities.getJSONFromAssets(context, "products_data.json"), new TypeToken<List<Product>>() {
            }.getType());
        }
        if (products != null) {
            // Insert the products data into the database
            insertProducts(db, products);
        }
    }

    // Insert a list of products into the database
    public void insertProducts(SQLiteDatabase db, List<Product> products) {
        for (Product product : products) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(ProductEntry.COLUMN_TITLE, product.getProductTitle());
            values.put(ProductEntry.COLUMN_SUB_TITLE, product.getProductSubTitle());
            values.put(ProductEntry.COLUMN_SIZE, String.join(", ", product.getProductSize()));
            values.put(ProductEntry.COLUMN_DESCRIPTION, product.getProductDescription());
            values.put(ProductEntry.COLUMN_PRICE, product.getProductPrice());
            values.put(ProductEntry.COLUMN_IMAGES, String.join(", ", product.getProductImages()));

            // Insert the new row
            db.insert(ProductEntry.TABLE_NAME, null, values);
        }
    }

    // methods not used for now
    /*
        // Insert a new product into the database
        public void insertProduct(SQLiteDatabase db, Product product) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(ProductEntry.COLUMN_TITLE, product.getProductTitle());
            values.put(ProductEntry.COLUMN_SUB_TITLE, product.getProductSubTitle());
            values.put(ProductEntry.COLUMN_SIZE, String.join(", ", product.getProductSize()));
            values.put(ProductEntry.COLUMN_DESCRIPTION, product.getProductDescription());
            values.put(ProductEntry.COLUMN_PRICE, product.getProductPrice());
            values.put(ProductEntry.COLUMN_IMAGES, String.join(", ", product.getProductImages()));

            // Insert the new row
            db.insert(ProductEntry.TABLE_NAME, null, values);
        }

        // delete a product from the database
        public void deleteProduct(SQLiteDatabase db, Product product) {
            db.delete(ProductEntry.TABLE_NAME, ProductEntry._ID + " = ?",
                    new String[]{String.valueOf(product.getProductId())});
        }

        // get product by id
        public Product getProductById(int id) {
            // Get the database in read mode
            SQLiteDatabase db = this.getReadableDatabase();

            Product product = null;

            String[] selectionArgs = {String.valueOf(id)};
            String rawQuery = "SELECT * FROM " + ProductEntry.TABLE_NAME + " WHERE " + BaseColumns._ID + " = ?";
            Cursor cursor = db.rawQuery(rawQuery, selectionArgs);

            // get column index of each column in the table
            int indexId = cursor.getColumnIndex(BaseColumns._ID);
            int indexTitle = cursor.getColumnIndex(ProductEntry.COLUMN_TITLE);
            int indexSubTitle = cursor.getColumnIndex(ProductEntry.COLUMN_SUB_TITLE);
            int indexSize = cursor.getColumnIndex(ProductEntry.COLUMN_SIZE);
            int indexDescription = cursor.getColumnIndex(ProductEntry.COLUMN_DESCRIPTION);
            int indexPrice = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
            int indexImages = cursor.getColumnIndex(ProductEntry.COLUMN_IMAGES);

            if (cursor.moveToFirst()) {
                // Get the product data from the database
                product = new Product(cursor.getInt(indexId), cursor.getString(indexTitle), cursor.getString(indexSubTitle), Arrays.asList(cursor.getString(indexSize).split(", ")), cursor.getString(indexDescription), cursor.getString(indexPrice), Arrays.asList(cursor.getString(indexImages).split(", ")));
            }

            // close the cursor
            cursor.close();
            // close the database
            db.close();
            return product;
        }
     */
    // get all products from the database
    public List<Product> getAllProducts() {
        // Get the database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        List<Product> productList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + ProductEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // get column index of each column in the table
        int indexId = cursor.getColumnIndex(BaseColumns._ID);
        int indexTitle = cursor.getColumnIndex(ProductEntry.COLUMN_TITLE);
        int indexSubTitle = cursor.getColumnIndex(ProductEntry.COLUMN_SUB_TITLE);
        int indexSize = cursor.getColumnIndex(ProductEntry.COLUMN_SIZE);
        int indexDescription = cursor.getColumnIndex(ProductEntry.COLUMN_DESCRIPTION);
        int indexPrice = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
        int indexImages = cursor.getColumnIndex(ProductEntry.COLUMN_IMAGES);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(cursor.getInt(indexId), cursor.getString(indexTitle),
                        cursor.getString(indexSubTitle), Arrays.asList(cursor.getString(indexSize).split(", ")), cursor.getString(indexDescription),
                        cursor.getString(indexPrice), Arrays.asList(cursor.getString(indexImages).split(", ")));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // close the cursor
        cursor.close();
        // close the database
        db.close();
        return productList;
    }

    // insert a new user into the database
    public void insertUser(User user) {
        // Get the database in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AuthenticationEntry.COLUMN_USER_EMAIL, user.getUserEmail());
        values.put(AuthenticationEntry.COLUMN_USER_NAME, user.getUserName());
        values.put(AuthenticationEntry.COLUMN_USER_PHONE, user.getUserPhone());
        values.put(AuthenticationEntry.COLUMN_USER_PASSWORD, user.getUserPassword());

        // Insert the new row
        db.insert(AuthenticationEntry.TABLE_NAME, null, values);
        // close the database
        db.close();
    }

    // get user by email
    public User getUserByEmail(String email) {
        // Get the database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        User user = null;

        String[] selectionArgs = {email};
        String rawQuery = "SELECT * FROM " + AuthenticationEntry.TABLE_NAME + " WHERE " + AuthenticationEntry.COLUMN_USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(rawQuery, selectionArgs);

        // get column index of each column in the table
        int indexId = cursor.getColumnIndex(BaseColumns._ID);
        int indexName = cursor.getColumnIndex(AuthenticationEntry.COLUMN_USER_NAME);
        int indexEmail = cursor.getColumnIndex(AuthenticationEntry.COLUMN_USER_EMAIL);
        int indexPhone = cursor.getColumnIndex(AuthenticationEntry.COLUMN_USER_PHONE);
        int indexPassword = cursor.getColumnIndex(AuthenticationEntry.COLUMN_USER_PASSWORD);

        if (cursor.moveToFirst()) {
            // Get the user data from the database
            user = new User(cursor.getInt(indexId), cursor.getString(indexName), cursor.getString(indexEmail), cursor.getString(indexPhone), cursor.getString(indexPassword));
        }

        // close the cursor
        cursor.close();
        // close the database
        db.close();
        return user;
    }

    // update user
    public void updateUser(User user) {
        // Get the database in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AuthenticationEntry.COLUMN_USER_EMAIL, user.getUserEmail());
        values.put(AuthenticationEntry.COLUMN_USER_NAME, user.getUserName());
        values.put(AuthenticationEntry.COLUMN_USER_PHONE, user.getUserPhone());
        values.put(AuthenticationEntry.COLUMN_USER_PASSWORD, user.getUserPassword());

        // update the row
        db.update(AuthenticationEntry.TABLE_NAME, values, AuthenticationEntry._ID + " = ?", new String[]{String.valueOf(user.getUserId())});
        // close the database
        db.close();
    }
}