package com.dolla.e_commerce.utils;

import com.dolla.e_commerce.model.Product;
import com.dolla.e_commerce.model.User;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    // private constructor to prevent instantiation
    private Constants() {
    }

    // constants for delay in splash screen and animation
    public static final int DELAY_PERIOD_SPLASH_SCREEN = 1500; // 1.5 seconds
    public static final int DELAY_PERIOD = 1000; // 1 seconds

    // Constants for the social media links
    public static final String INSTAGRAM_URL = "https://www.instagram.com/ldmfam";
    public static final String FACEBOOK_URL = "https://www.facebook.com/LOINDEMOYENNE";
    public static final String GMAIL_EMAIL = "loindemoyenne@gmail.com";

    // product list
    public static List<Product> PRODUCTS_LIST;
    public static List<Product> PRODUCTS_CART_LIST = new ArrayList<>();
    public static List<Integer> PRODUCT_CART_AMOUNT = new ArrayList<>();

    // user
    public static User USER;
}