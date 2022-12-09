package com.dolla.e_commerce.ui.fraghost;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.adapters.ProductListAdapter;
import com.dolla.e_commerce.data.LDMOpenHelper;
import com.dolla.e_commerce.ui.aboutus.AboutUsFragment;
import com.dolla.e_commerce.ui.account.AccountFragment;
import com.dolla.e_commerce.ui.cart.CartFragment;
import com.dolla.e_commerce.ui.homelist.HomeFragment;
import com.dolla.e_commerce.ui.productdetails.ProductDetailsActivity;
import com.dolla.e_commerce.utils.Constants;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity implements ProductListAdapter.ItemClicked {

    public static final String PRODUCT_POSITION_INTENT = "com.dolla.e_commerce.home.HomeActivity.PRODUCT_POSITION_INTENT";

    public static Boolean isDestroying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ChipNavigationBar bottomNavigation = findViewById(R.id.bottomNavigation);

        // create a new instance of LDMOpenHelper
        LDMOpenHelper ldmOpenHelper = LDMOpenHelper.getInstance(this);
        // set data into product list
        Constants.PRODUCTS_LIST = ldmOpenHelper.getAllProducts();

        // Set Home Fragment as default
        bottomNavigation.setItemSelected(R.id.navHome, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container, new HomeFragment()).commit();

        bottomNavigation.setOnItemSelectedListener(this::swapFragment);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // animate the activity to slide out from left to right
        if (isDestroying)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        isDestroying = true;
    }

    @Override
    public void onBackPressed() {
        // cancel back button function to prevent user from going back to login screen using back button
    }

    // Swap Fragment
    @SuppressLint("NonConstantResourceId")
    private void swapFragment(int item) {
        Fragment targetFragment;
        switch (item) {
            case R.id.navCart:
                targetFragment = new CartFragment();
                break;
            case R.id.navAccount:
                targetFragment = new AccountFragment();
                break;
            case R.id.navAboutUs:
                targetFragment = new AboutUsFragment();
                break;
            default:
                targetFragment = new HomeFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container, targetFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void onItemClicked(int index) {
        isDestroying = false;
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra(PRODUCT_POSITION_INTENT, index);
        startActivity(intent);
    }
}