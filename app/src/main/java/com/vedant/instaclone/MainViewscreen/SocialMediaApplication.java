package com.vedant.instaclone.MainViewscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;
import com.vedant.instaclone.Adapters.TabAdapters;
import com.vedant.instaclone.R;

public class SocialMediaApplication extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapters tabAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media_application);

        setTitle("Social Media App!!!");

        toolbar = findViewById(R.id.mytoolbar);
         setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabAdapters = new TabAdapters(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapters);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, false);
    }
}
