package com.vedant.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import static java.security.AccessController.getContext;

public class userPost extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

         linearLayout = findViewById(R.id.linearLayout);

        Intent receivedIntentObject = getIntent();
        final String receivedUserName = receivedIntentObject.getStringExtra("username");
        Toast.makeText(this, receivedUserName, Toast.LENGTH_SHORT).show();

        setTitle(receivedUserName + "'s posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username", receivedUserName);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {

                    for (ParseObject post : objects) {

                        final TextView postDescription = new TextView(userPost.this);
                        postDescription.setText(post.get("image_des") + "");
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if (data != null && e == null) {

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView postImageView = new ImageView(userPost.this);
                                    LinearLayout.LayoutParams imageView_params =
                                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5, 5, 5, 5);
                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5, 5, 5, 15);
                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.WHITE);
                                    postDescription.setTextColor(Color.BLACK);
                                    postDescription.setTextSize(30f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);

                                }

                            }
                        });


                    }



                } else {

                    Toast.makeText(userPost.this,  receivedUserName + " doesn't have any posts!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }

                dialog.dismiss();
            }
        });

    }
}
