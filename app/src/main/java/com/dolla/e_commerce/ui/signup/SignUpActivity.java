package com.dolla.e_commerce.ui.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dolla.e_commerce.R;
import com.dolla.e_commerce.data.LDMOpenHelper;
import com.dolla.e_commerce.model.User;
import com.dolla.e_commerce.ui.signin.SignInActivity;
import com.dolla.e_commerce.utils.Constants;
import com.dolla.e_commerce.utils.Utilities;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {
    public static final String EMAIL_INTENT = "com.dolla.e_commerce.ui.signup.SignUpActivity.EMAIL_INTENT";
    public static final String PASSWORD_INTENT = "com.dolla.e_commerce.ui.signup.SignUpActivity.PASSWORD_INTENT";

    private EditText etName, etEmail, etPhoneNumber, etPassword, etConfirmPassword;
    private CheckBox cbCheckTerms;
    private ProgressBar progressBar;
    private View layoutAuthentication;
    private LDMOpenHelper ldmOpenHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ImageView ivBack = findViewById(R.id.button_back);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_password_confirm);
        cbCheckTerms = findViewById(R.id.cbCheckTerms);
        Button btSignUp = findViewById(R.id.button_sign_up);
        progressBar = findViewById(R.id.progress_bar);
        layoutAuthentication = findViewById(R.id.layout_authentication);

        context = getApplicationContext();

        // create a new instance of LDMOpenHelper
        ldmOpenHelper = LDMOpenHelper.getInstance(this);

        ivBack.setOnClickListener(view -> finish());
        cbCheckTerms.setOnCheckedChangeListener((compoundButton, b) -> Utilities.closeKeyboard(this, cbCheckTerms));
        btSignUp.setOnClickListener(view -> onSignUpClicked());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // to prevent memory leak
        context = null;
        // animate the activity to slide out from left to right
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // Utility method to validate sign up details
    private boolean validateSignUpDetails(String name, String email, String phoneNumber, String password, String confirmPassword) {
        if (name.isEmpty()) {
            etName.requestFocus();
            etName.setError("Name is required!");
            return false;
        }
        if (email.isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError("E-mail is required!");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.requestFocus();
            etEmail.setError("Please provide a valid E-mail");
            return false;
        }
        if (phoneNumber.isEmpty()) {
            etPhoneNumber.requestFocus();
            etPhoneNumber.setError("Phone Number is required!");
            return false;
        }
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            etPhoneNumber.requestFocus();
            etPhoneNumber.setError("Please provide a valid Phone Number");
            return false;
        }
        if (password.isEmpty()) {
            etPassword.requestFocus();
            etPassword.setError("Password is required!");
            return false;
        }
        if (password.length() < 6) {
            etPassword.requestFocus();
            etPassword.setError("Min password length should be 6 characters!");
            return false;
        }
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.requestFocus();
            etConfirmPassword.setError("Password Confirmation is required!");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etPassword.requestFocus();
            etPassword.setError("Passwords does not match!");
            return false;
        }
        if (!cbCheckTerms.isChecked()) {
            Toasty.error(context, "You have to accept our terms & conditions", Toast.LENGTH_LONG, true).show();
            return false;
        }
        return true;
    }

    private void onSignUpClicked() {
        Utilities.closeKeyboard(this, cbCheckTerms);
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim().toLowerCase();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validate sign up details
        if (validateSignUpDetails(name, email, phoneNumber, password, confirmPassword)) {

            // Simulate a network access.
            layoutAuthentication.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                layoutAuthentication.setVisibility(View.VISIBLE);
                // Check if user already exists
                User user = new User(0, name, email, phoneNumber, password);
                if (insertUser(user)) {
                    Toasty.success(context, "Sign-Up Successful", Toast.LENGTH_LONG, true).show();
                    navigateToSignInActivity();
                } else
                    Toasty.error(context, "User Already Exists!", Toasty.LENGTH_LONG, true).show();

            }, Constants.DELAY_PERIOD);
        }
    }

    // Utility method to insert user into database and check if user already exists
    private boolean insertUser(User user) {
        if (ldmOpenHelper.getUserByEmail(user.getUserEmail()) == null) {
            ldmOpenHelper.insertUser(user);
            return true;
        }
        return false;
    }

    // navigate to the sign in activity
    private void navigateToSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.putExtra(EMAIL_INTENT, etEmail.getText().toString().trim());
        intent.putExtra(PASSWORD_INTENT, etPassword.getText().toString().trim());
        startActivity(intent);
        finish();
    }
}