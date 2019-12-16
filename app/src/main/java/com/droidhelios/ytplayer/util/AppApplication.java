package com.droidhelios.ytplayer.util;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

public class AppApplication extends Application {

    private static volatile AppApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }


    @Override
    protected void attachBaseContext(Context base) {
        initTheme(base);
        super.attachBaseContext(base);
    }

    public void initTheme(Context context) {
        if (!AppPreferences.isDayMode(context)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}