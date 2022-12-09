package com.dolla.e_commerce.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.ui.registration.RegistrationActivity;
import com.dolla.e_commerce.utils.Constants;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Delay the transition to the next activity by 1.5 seconds
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, RegistrationActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, Constants.DELAY_PERIOD_SPLASH_SCREEN);
    }
}