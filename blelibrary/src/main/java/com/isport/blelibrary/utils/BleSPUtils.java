package com.isport.blelibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author
 * @Date 2018/10/17
 * @Fuction
 */

public class BleSPUtils {

    public static String APP_CONFIG          = "BLE_CONFIG";
    public static String WATCH_LAST_SYNCTIME = "WATCH_LAST_SYNCTIME";
    public static String Bracelet_LAST_SYNCTIME = "bracelet_LAST_SYNCTIME";
    public static String Bracelet_LAST_HR_SYNCTIME = "bracelet_LAST_Hr_SYNCTIME";

    private static SharedPreferences        sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public static void putString(Context context, String key, String defValue) {
        init(context);
        editor.putString(key, defValue).commit();
    }

    public static void putInt(Context context, String key, int defValue) {
        init(context);
        editor.putInt(key, defValue).commit();
    }

    public static void putBoolean(Context context, String key, boolean defValue) {
        init(context);
        editor.putBoolean(key, defValue).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        init(context);
        return sharedPreferences.getInt(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        init(context);
        return sharedPreferences.getString(key, defValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        init(context);
        return sharedPreferences.getBoolean(key, defValue);
    }
}
