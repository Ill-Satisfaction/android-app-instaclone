package com.example.myapplication;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // register parse models
        ParseObject.registerSubclass(Post.class);

        // set applicationid and server based on values in heroku settings
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("WJC0nR0XgvqxjGqIUJbpZPGvq6gU2F5zuRrBjgDO")
                .clientKey("7e5I6Fenmq7eHrQhbLkc4MGeETusA3G1nZT9tsXY")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
