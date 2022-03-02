package com.vedant.instaclone.MainViewscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.parse.ParseUser;
import com.vedant.instaclone.R;
import com.vedant.instaclone.databinding.ActivitySignUpBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Sign_Up extends AppCompatActivity {

    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Sign Up");

        binding.signpass.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                onClick(binding.msignup1);
            }
            return false;
        });
        binding.login1.setOnClickListener(this::onClick);
        binding.msignup1.setOnClickListener(this::onClick);


        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(Sign_Up.this, SocialMediaApplication.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.login1:
                Toast.makeText(Sign_Up.this, "login clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Sign_Up.this, LoginActivity.class));
                break;
            case R.id.msignup1:

                if (binding.mEmail1.getText().toString().equals("") || binding.SignUsername.getText().toString().equals("") || binding.signpass.getText().toString().equals("")) {
                    Toast.makeText(Sign_Up.this, "complete the fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(binding.mEmail1.getText().toString());
                    appUser.setUsername(binding.SignUsername.getText().toString());
                    appUser.setPassword(binding.signpass.getText().toString());

                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up " + binding.SignUsername.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(e -> {
                        if (e == null) {
                            Toast.makeText(Sign_Up.this, "User created successfully",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, SocialMediaApplication.class));
                            finish();
                        } else {
                            Toast.makeText(Sign_Up.this, "invalid fields filled or network issue " +
                                            "found",
                                    Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    });
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    public void closeKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
