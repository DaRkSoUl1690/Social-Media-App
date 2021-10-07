package com.vedant.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText loginusername, loginpass;
    private Button msignup2, login2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       setTitle("Login");
        loginusername = findViewById(R.id.loginusername);
        loginpass = findViewById(R.id.loginpass);
        loginpass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN)
                {
                    onClick(loginpass);
                }
                return false;
            }
        });
        msignup2 = findViewById(R.id.msignup2);
        login2 = findViewById(R.id.login2);

        msignup2.setOnClickListener(this::onClick);
        login2.setOnClickListener(this::onClick);

        if(ParseUser.getCurrentUser()!=null)
        {
//            ParseUser.getCurrentUser();
//            ParseUser.logOut();
            Intent intent = new Intent(LoginActivity.this,SocialMediaApplication.class);
            startActivity(intent);
        }

    }

    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.msignup2:
                Toast.makeText(LoginActivity.this, "login clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Sign_Up.class);
                startActivity(intent);
                break;
            case R.id.login2:
                if (loginusername.getText().toString().equals("") || loginpass.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "complete the fields",
                            Toast.LENGTH_SHORT).show();

                }else {
                    ParseUser.logInInBackground(loginusername.getText().toString(),
                            loginpass.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        Toast.makeText(LoginActivity.this, user.getUsername() + "is " +
                                                        "logged in " +
                                                        "successfully",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent1 = new Intent(LoginActivity.this,SocialMediaApplication.class);
                                        startActivity(intent1);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "error found",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
        }
    }

    public void hidekeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
