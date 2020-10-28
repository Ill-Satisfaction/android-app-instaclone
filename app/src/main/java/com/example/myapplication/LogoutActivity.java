package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.parse.Parse;
import com.parse.ParseUser;

public class LogoutActivity extends AppCompatActivity {

    private Context root = this;
    private Button btnLogout;
    private ImageButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        getSupportActionBar().hide();

        btnLogout = findViewById(R.id.btn_logout);
        btnHome = findViewById(R.id.btn_home);

        btnHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(root, MainActivity.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        // log out
        ParseUser.logOutInBackground();
        // move to login screen
        Intent i = new Intent(root, LoginActivity.class);
        startActivity(i);
    }
}