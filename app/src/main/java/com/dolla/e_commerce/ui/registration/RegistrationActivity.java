package com.dolla.e_commerce.ui.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.ui.signin.SignInActivity;
import com.dolla.e_commerce.ui.signup.SignUpActivity;
import com.dolla.e_commerce.utils.Constants;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize the variables
        Button btSignIn = findViewById(R.id.button_sign_in);
        Button btSignup = findViewById(R.id.button_sign_up);
        ImageView ivInstagram = findViewById(R.id.iv_instagram_button);
        ImageView ivFacebook = findViewById(R.id.iv_facebook_button);
        ImageView ivGmail = findViewById(R.id.iv_gmail_button);

        btSignIn.setOnClickListener(view -> navigateToSignInActivity());
        btSignup.setOnClickListener(view -> navigateToSignUpActivity());

        ivInstagram.setOnClickListener(view -> social(Constants.INSTAGRAM_URL));
        ivFacebook.setOnClickListener(view -> social(Constants.FACEBOOK_URL));
        ivGmail.setOnClickListener(view -> social("mailto:" + Constants.GMAIL_EMAIL));
    }

    // navigate to required activity
    private void social(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    // navigate to the login activity
    private void navigateToSignInActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // navigate to the signup activity
    private void navigateToSignUpActivity() {
        startActivity(new Intent(this, SignUpActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}