package com.yi.download;

import android.util.Log;

import java.util.Locale;

/**
 * Created by Yi on 2017/7/26.
 */

public class Logger {
    private static final boolean DEBUG = true;

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Object... args) {
        if (DEBUG)
            Log.d(tag, String.format(Locale.getDefault(),msg,args));
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG)
            Log.w(tag, msg);
    }
}
