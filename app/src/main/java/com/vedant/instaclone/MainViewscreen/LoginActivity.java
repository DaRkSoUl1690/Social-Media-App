package com.vedant.instaclone.MainViewscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.parse.ParseUser;
import com.vedant.instaclone.R;
import com.vedant.instaclone.databinding.ActivityLoginBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Login");

        binding.loginpass.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                onClick(binding.login2);
            }
            return false;
        });

        binding.msignup2.setOnClickListener(this::onClick);
        binding.login2.setOnClickListener(this::onClick);

        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, SocialMediaApplication.class);
            startActivity(intent);
            finish();
        }

    }

    public void onClick(@NonNull View view) {
        if(view.getId()== R.id.msignup2) {
            Toast.makeText(LoginActivity.this, "login clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Sign_Up.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.login2) {
            if (binding.loginusername.getText().toString().equals("") || binding.loginpass.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, "complete the fields",
                        Toast.LENGTH_SHORT).show();

            } else {
                ParseUser.logInInBackground(
                        binding.loginusername.getText().toString(),
                        binding.loginpass.getText().toString(), (user, e) -> {
                            if (user != null && e == null) {
                                Toast.makeText(LoginActivity.this, user.getUsername() + "is " +
                                                "logged in " +
                                                "successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(LoginActivity.this,
                                        SocialMediaApplication.class);
                                startActivity(intent1);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "error found",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

        }


    public void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
