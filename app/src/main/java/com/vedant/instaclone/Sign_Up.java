package com.vedant.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Sign_Up extends AppCompatActivity {

private EditText musername1,mpass1,musername2,mpass2;
private Button signup,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        musername1 = findViewById(R.id.musername1);
        musername2 = findViewById(R.id.musername2);
        mpass1 = findViewById(R.id.mpass1);
        mpass2 = findViewById(R.id.mpass2);
        signup = findViewById(R.id.msignup);
        login = findViewById(R.id.login);

         signup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 ParseUser appUser = new ParseUser();
                 appUser.setUsername(musername1.getText().toString());
                 appUser.setPassword(mpass1.getText().toString());

                 appUser.signUpInBackground(new SignUpCallback() {
                     @Override
                     public void done(ParseException e) {
                         if(e==null)
                         {
                             Toast.makeText(Sign_Up.this, "User Created Successfully",
                                     Toast.LENGTH_SHORT).show();
                         }

                         else
                         {
                             Toast.makeText(Sign_Up.this, "error found",
                                     Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }
         });

         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 ParseUser.logInInBackground(musername2.getText().toString(),
                         mpass2.getText().toString(), new LogInCallback() {
                             @Override
                             public void done(ParseUser user, ParseException e) {
                                 if(e==null && user!=null)

                                 {
                                     Toast.makeText(Sign_Up.this, "login successfully",
                                             Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(Sign_Up.this,MainActivity.class);
                                     startActivity(intent);
                                 }

                                 else
                                 {
                                     Toast.makeText(Sign_Up.this, "error found",
                                             Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
             }
         });
    }
}
