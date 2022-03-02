package com.vedant.instaclone.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vedant.instaclone.R;
import com.vedant.instaclone.userPost;

import java.util.ArrayList;
import java.util.List;



public class UserTab extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private   ArrayList<String> arrayList;
    public UserTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_tab, container, false);

        ListView listView = view.findViewById(R.id.listView);
         arrayList = new ArrayList();
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1
                , arrayList);


        listView.setOnItemClickListener(UserTab.this);
        listView.setOnItemLongClickListener(UserTab.this);

        TextView textView = view.findViewById(R.id.loading);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if (e == null) {
                    if (users.size() > 0) {
                        for (ParseUser user : users) {
                            arrayList.add(user.getUsername());
                        }
                    }
                    listView.setAdapter(adapter);
                    textView.animate().alpha(0).setDuration(2000);
                    listView.setVisibility(View.VISIBLE);
                }

            }
        });
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
    Intent intent = new Intent(getContext(), userPost.class);
    intent.putExtra("username",arrayList.get(position));
    startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username", arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (user != null && e == null) {

                    final PrettyDialog prettyDialog =  new PrettyDialog(getContext());

                    prettyDialog.setTitle(user.getUsername() + " 's Info")
                            .setMessage(user.get("profileBio") + "\n"
                                    + user.get("profileProfession") + "\n"
                                    + user.get("profileHobbies") + "\n"
                                    + user.get("profileFavSport"))
                            .setIcon(R.drawable.person)
                            .addButton(
                                    "OK",     // button text
                                    R.color.pdlg_color_white,  // button text color
                                    R.color.pdlg_color_green,  // button background color
                                    new PrettyDialogCallback() {  // button OnClick listener
                                        @Override
                                        public void onClick() {
                                            // Do what you gotta do
                                            prettyDialog.dismiss();
                                        }
                                    }
                            )
                            .show();


                }
            }
        });


        return true;
    }
}
