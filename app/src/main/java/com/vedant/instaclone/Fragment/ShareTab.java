package com.vedant.instaclone.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vedant.instaclone.R;

import java.io.ByteArrayOutputStream;
import java.util.Objects;


public class ShareTab extends Fragment implements View.OnClickListener {

    Bitmap receivedImageBitmap;
    ImageView imageView;
    EditText editText;
    public ShareTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share_tab, container, false);

        imageView = view.findViewById(R.id.imgshare);
         editText = view.findViewById(R.id.desc);
        Button shareButton = view.findViewById(R.id.button);

        imageView.setOnClickListener(ShareTab.this);
        shareButton.setOnClickListener(ShareTab.this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgshare:


                if (android.os.Build.VERSION.SDK_INT >= 23 &&
                        ActivityCompat.checkSelfPermission(requireContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]
                                    {Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);

                } else {

                    getChosenImage();

                }

                break;
            case R.id.button:
                if (receivedImageBitmap != null) {

                    if (editText.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Error: You must specify a description.", Toast.LENGTH_SHORT).show();


                    } else {

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("img.png", bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture", parseFile);
                        parseObject.put("image_des", editText.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Loading...");
                        dialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getContext(), "Done!!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Unknown error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        });


                    }

                } else {

                    Toast.makeText(getContext(), "Error: You must select an image.", Toast.LENGTH_SHORT).show();

                }


                break;

        }
    }

    private void getChosenImage() {
        Toast.makeText(getContext(), "Now we can access the images", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == 2000) {

            if (resultCode == Activity.RESULT_OK) {

                //Do something with your captured image.
                try {
                    assert data != null;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = requireActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);

                    imageView.setImageBitmap(receivedImageBitmap);

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }
    }
}
