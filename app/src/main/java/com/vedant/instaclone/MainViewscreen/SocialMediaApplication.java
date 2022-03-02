package com.vedant.instaclone.MainViewscreen;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.vedant.instaclone.Adapters.TabAdapters;
import com.vedant.instaclone.R;
import com.vedant.instaclone.databinding.ActivitySocialMediaApplicationBinding;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SocialMediaApplication extends AppCompatActivity {

    ActivitySocialMediaApplicationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media_application);
        binding = ActivitySocialMediaApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Social Media App!!!");

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        TabAdapters tabAdapters = new TabAdapters(getSupportFragmentManager());

        binding.viewPager.setAdapter(tabAdapters);

        binding.tabLayout.setupWithViewPager(binding.viewPager, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.postImageItem) {

            if (android.os.Build.VERSION.SDK_INT >= 23 &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {


                requestPermissions(new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE},
                        3000);

            } else {

                captureImage();
            }

        } else if (item.getItemId() == R.id.logoutUserItem) {

            ParseUser.getCurrentUser();
            ParseUser.logOut();
            finish();
            Intent intent = new Intent(SocialMediaApplication.this, LoginActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 3000) {

            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                captureImage();
            }

        }

    }

    private void captureImage() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4000);

    }
/**Getting path of captured image in Android using camera intent*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4000 && resultCode == RESULT_OK && data != null) {

            try {
                Uri capturedImage = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.
                        getBitmap(this.getContentResolver(),
                                capturedImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();

                ParseFile parseFile = new ParseFile("img.png", bytes);
                ParseObject parseObject = new ParseObject("Photo");
                parseObject.put("picture", parseFile);
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Loading...");
                dialog.show();
                parseObject.saveInBackground(e -> {
                    if (e == null) {
                        Toast.makeText(SocialMediaApplication.this, "Pictured Uploaded!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SocialMediaApplication.this, "Unknown error:" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    dialog.dismiss();
                });


            } catch (Exception e) {

                e.printStackTrace();
            }

        }
    }
}
