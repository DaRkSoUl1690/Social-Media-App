package com.vedant.instaclone.MainViewscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.vedant.instaclone.R;

public class Sign_Up extends AppCompatActivity {


    private EditText mEmail1, SignUsername, signpass;
    private Button msignup1, login1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");
        mEmail1 = findViewById(R.id.mEmail1);
        SignUsername = findViewById(R.id.SignUsername);
        signpass = findViewById(R.id.signpass);

        msignup1 = findViewById(R.id.msignup1);
        login1 = findViewById(R.id.login1);
        signpass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN)
                {
                    onClick(msignup1);
                }
                return false;
            }
        });
        login1.setOnClickListener(this::onClick);
        msignup1.setOnClickListener(this::onClick);


        if (ParseUser.getCurrentUser() != null) {
//            ParseUser.getCurrentUser();
//            ParseUser.logOut();
            Intent intent = new Intent(Sign_Up.this, SocialMediaApplication.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login1:
                Toast.makeText(Sign_Up.this, "login clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.msignup1:

                if (mEmail1.getText().toString().equals("") || SignUsername.getText().toString().equals("") || signpass.getText().toString().equals(""))
                {
                    Toast.makeText(Sign_Up.this, "complete the fields",
                            Toast.LENGTH_SHORT).show();



                }else {
                    final ParseUser appuser = new ParseUser();
                    appuser.setEmail(mEmail1.getText().toString());
                    appuser.setUsername(SignUsername.getText().toString());
                    appuser.setPassword(signpass.getText().toString());
                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up" + SignUsername.getText().toString());
                    progressDialog.show();
                    appuser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(Sign_Up.this, "User created successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(Sign_Up.this,SocialMediaApplication.class);
                                startActivity(intent1);
                                finish();
                            }
                            else {
                                Toast.makeText(Sign_Up.this, "error found", Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
                }
                break;
        }
    }

     public void closeKeyboard(View view)
     {
         try {
             InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
             inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
         }catch (Exception e)
         {
             e.printStackTrace();
         }
     }

}
