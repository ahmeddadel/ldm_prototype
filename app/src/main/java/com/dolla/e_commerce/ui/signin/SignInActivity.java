package com.dolla.e_commerce.ui.signin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.data.LDMOpenHelper;
import com.dolla.e_commerce.model.User;
import com.dolla.e_commerce.ui.fraghost.HomeActivity;
import com.dolla.e_commerce.ui.resetpassword.ResetPasswordActivity;
import com.dolla.e_commerce.ui.signup.SignUpActivity;
import com.dolla.e_commerce.utils.Constants;
import com.dolla.e_commerce.utils.Utilities;

import es.dmoral.toasty.Toasty;

public class SignInActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btSignIn;
    private ProgressBar progressBar;
    private View layoutAuthentication;
    private LDMOpenHelper ldmOpenHelper;
    private Boolean isDestroying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ImageView ivBack = findViewById(R.id.button_back);
        layoutAuthentication = findViewById(R.id.layout_authentication);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        TextView tvForgotPassword = findViewById(R.id.tv_forgot_password);
        btSignIn = findViewById(R.id.button_sign_in);
        Button btSignUp = findViewById(R.id.button_sign_up);
        progressBar = findViewById(R.id.progress_bar);

        // create a new instance of LDMOpenHelper
        ldmOpenHelper = LDMOpenHelper.getInstance(this);

        // get data from intent if exist and set it to the email field
        getIntentExtras();

        ivBack.setOnClickListener(view -> finish());
        btSignIn.setOnClickListener(view -> onSignInClicked());
        btSignUp.setOnClickListener(view -> navigateToSignUpActivity());
        tvForgotPassword.setOnClickListener(view -> navigateToResetPasswordActivity());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // animate the activity to slide out from left to right
        if (isDestroying)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        isDestroying = true;
    }

    private void getIntentExtras() {
        Intent intent = getIntent();
        String email = intent.getStringExtra(SignUpActivity.EMAIL_INTENT);
        String password = intent.getStringExtra(SignUpActivity.PASSWORD_INTENT);
        if (email != null || password != null) {
            etEmail.setText(email);
            etPassword.setText(password);
        }
        email = intent.getStringExtra(ResetPasswordActivity.EMAIL_INTENT);
        if (email != null) {
            etEmail.setText(email);
        }
    }

    // Sign in button clicked
    private void onSignInClicked() {
        Utilities.closeKeyboard(this, btSignIn);

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.requestFocus();
            etPassword.setText("");
            etEmail.setError("E-mail is required!");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.requestFocus();
            etEmail.setText("");
            etPassword.setText("");
            etEmail.setError("Please provide a valid E-mail");
            return;
        }
        if (password.isEmpty()) {
            etPassword.requestFocus();
            etPassword.setError("Password is required!");
            return;
        }
        if (password.length() < 6) {
            etPassword.requestFocus();
            etPassword.setText("");
            etPassword.setError("Min password length should be 6 characters!");
            return;
        }

        User user = ldmOpenHelper.getUserByEmail(email);
        // Simulate a network access
        layoutAuthentication.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {

            progressBar.setVisibility(View.GONE);
            layoutAuthentication.setVisibility(View.VISIBLE);
            // Check if the user exists
            if (user != null) {
                if (user.getUserEmail().equals(email) && user.getUserPassword().equals(password)) {
                    Toasty.success(this, "Sign-In Successful", Toasty.LENGTH_SHORT, true).show();
                    Constants.USER = user;
                    navigateToHomeActivity();
                } else
                    Toasty.error(this, "Wrong E-mail or Password!", Toasty.LENGTH_SHORT, true).show();

            } else
                Toasty.error(this, "User not Found!", Toasty.LENGTH_SHORT, true).show();

        }, Constants.DELAY_PERIOD);

    }

    // navigate to the home activity
    private void navigateToHomeActivity() {
        isDestroying = false;
        startActivity(new Intent(this, HomeActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    // navigate to the sign up activity
    private void navigateToSignUpActivity() {
        isDestroying = false;
        startActivity(new Intent(this, SignUpActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // navigate to the reset password activity
    private void navigateToResetPasswordActivity() {
        isDestroying = false;
        startActivity(new Intent(this, ResetPasswordActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}