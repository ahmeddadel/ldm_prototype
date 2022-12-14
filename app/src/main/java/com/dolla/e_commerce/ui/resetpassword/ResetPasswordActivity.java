package com.dolla.e_commerce.ui.resetpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.data.LDMOpenHelper;
import com.dolla.e_commerce.model.User;
import com.dolla.e_commerce.ui.signin.SignInActivity;
import com.dolla.e_commerce.utils.Constants;
import com.dolla.e_commerce.utils.Utilities;

import es.dmoral.toasty.Toasty;

public class ResetPasswordActivity extends AppCompatActivity {
    public static final String EMAIL_INTENT = "com.dolla.e_commerce.ui.resetpassword.ResetPasswordActivity.EMAIL_INTENT";

    private EditText etEmail, etPassword;
    private Button btResetPassword;
    private ProgressBar progressBar;
    private View layoutAuthentication;
    private LDMOpenHelper ldmOpenHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ImageView ivBack = findViewById(R.id.button_back);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btResetPassword = findViewById(R.id.button_reset_password);
        progressBar = findViewById(R.id.progress_bar);
        layoutAuthentication = findViewById(R.id.layout_authentication);

        context = getApplicationContext();

        // create a new instance of LDMOpenHelper
        ldmOpenHelper = LDMOpenHelper.getInstance(this);

        ivBack.setOnClickListener(view -> finish());
        btResetPassword.setOnClickListener(view -> onResetPasswordClicked());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // animate the activity to slide out from left to right
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        // to prevent memory leak
        context = null;
    }

    // validate the email and password
    private void onResetPasswordClicked() {
        Utilities.closeKeyboard(this, btResetPassword);

        String email = etEmail.getText().toString().trim().toLowerCase();
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
                user.setUserPassword(password);
                ldmOpenHelper.updateUser(user);
                Toasty.success(context, "Password Successfully Changed", Toasty.LENGTH_SHORT, true).show();
                navigateToSignInActivity();
            } else {
                Toasty.error(context, "User E-mail doesn't exist", Toasty.LENGTH_SHORT, true).show();
            }

        }, Constants.DELAY_PERIOD);

    }

    // navigate to the sign in activity
    private void navigateToSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.putExtra(EMAIL_INTENT, etEmail.getText().toString().trim());
        startActivity(intent);
        finish();
    }
}