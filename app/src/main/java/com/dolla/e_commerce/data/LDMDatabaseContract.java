package com.dolla.e_commerce.data;

import android.provider.BaseColumns;

public class LDMDatabaseContract {

    // To prevent someone from accidentally instantiating the contract class
    private LDMDatabaseContract() {
    }

    // Authentication TABLE
    public static final class AuthenticationEntry implements BaseColumns {
        public static final String TABLE_NAME = "user_authentication";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_EMAIL = "user_email";
        public static final String COLUMN_USER_PHONE = "user_phone";
        public static final String COLUMN_USER_PASSWORD = "user_password";

        // CREATE INDEX user_authentication_index ON user_authentication (user_email, user_password)
        public static final String INDEX = TABLE_NAME + "_index";
        public static final String SQL_CREATE_INDEX = "CREATE INDEX " + INDEX +
                " ON " + TABLE_NAME +
                " (" + COLUMN_USER_EMAIL + ", " + COLUMN_USER_PASSWORD + ")";

        // CREATE TABLE user_authentication (_id, user_email, user_name, user_phone, user_password)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USER_NAME + " TEXT NOT NULL, " +
                        COLUMN_USER_EMAIL + " TEXT NOT NULL, " +
                        COLUMN_USER_PHONE + " TEXT NOT NULL, " +
                        COLUMN_USER_PASSWORD + " TEXT NOT NULL)";
    }

    // ProductEntry TABLE
    public static final class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SUB_TITLE = "sub_title";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_IMAGES = "images";

        // CREATE INDEX products_index ON products (_id)
        public static final String INDEX = TABLE_NAME + "_index";
        public static final String SQL_CREATE_INDEX = "CREATE INDEX " + INDEX +
                " ON " + TABLE_NAME +
                " (" + _ID + ")";

        // CREATE TABLE products (_id, title, sub_title, size, description, price)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_SUB_TITLE + " TEXT NOT NULL, " +
                        COLUMN_SIZE + " TEXT NOT NULL, " +
                        COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                        COLUMN_PRICE + " TEXT NOT NULL, " +
                        COLUMN_IMAGES + " TEXT NOT NULL)";
    }

}