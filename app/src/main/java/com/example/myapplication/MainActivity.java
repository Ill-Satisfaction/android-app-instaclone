package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context root = this;

    public static final String TAG = "MainActivity";

    private BottomNavigationView botNav;
    private FrameLayout fragmentHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        botNav = findViewById(R.id.bottom_navigation);
        fragmentHolder = findViewById(R.id.layout_fragment_container);

        openFragment(FeedFragment.newInstance("",""));
        BottomNavigationView.OnNavigationItemSelectedListener navSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case(R.id.item_post) :
                        openFragment(CreatePostFragment.newInstance("",""));
                        break;
                    case(R.id.item_feed) :
                        openFragment(FeedFragment.newInstance("",""));
                        break;
                    case(R.id.item_profile) :
                        openFragment(ProfileFragment.newInstance("",""));
                }
                return false;
            }
        };
        botNav.setOnNavigationItemSelectedListener(navSelected);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentHolder.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}