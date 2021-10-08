package com.vedant.instaclone.Adapters;

import com.vedant.instaclone.Fragment.ProfileTab;
import com.vedant.instaclone.Fragment.ShareTab;
import com.vedant.instaclone.Fragment.UserTab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapters extends FragmentPagerAdapter {
    public TabAdapters(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position) {
           case 0:
               return new ProfileTab();
               case 1:
                   return new UserTab();
                   case 2:
                       return new ShareTab();
           default:
               return null;
       }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      switch(position){
          case 0:return "Profile";
          case 1:return "User";
          case 2:return "Share Picture";

          default:
              return null;
      }

    }

}
