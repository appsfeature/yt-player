package com.droidhelios.ytplayer.util;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AppPreferences {

    public static final String DAY_MODE = "dayMode";

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreferenceObj(Context context){
        if ( sharedPreferences == null )
            sharedPreferences = context.getSharedPreferences(context.getPackageName() , Context.MODE_PRIVATE);

        return sharedPreferences ;
    }



    public static boolean isDayMode(Context context){
        return getSharedPreferenceObj(context).getBoolean( DAY_MODE, true );
    }

    public static void setDayMode(Context context , boolean isDayMode){
        getSharedPreferenceObj(context).edit().putBoolean( DAY_MODE , isDayMode).apply();
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getDelegate().setLocalNightMode( isDayMode
                    ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES );

        }
        if (isDayMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

}
