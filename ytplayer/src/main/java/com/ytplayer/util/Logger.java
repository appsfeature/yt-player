package com.ytplayer.util;

import android.util.Log;

/**
 * @author Created by Abhijit on 2/6/2018.
 */
public class Logger {

    private static final String TAG = "@Logger";

    public static void log(String message) {
        if(YTConstant.isLogEnabled) {
            Log.d(TAG, message);
        }
    }
}
