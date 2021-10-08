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


    //计步目标
    public static String KEY_STEP_GOAL = "key_step_goal";
    //久坐提醒时间段
    public static String KEY_LONG_SIT = "key_long_sit";
    //时间格式 12或24小时制
    public static String KEY_TIME_FORMAT = "key_time_format";
    //来电提醒
    public static String KEY_CALL_STATUS = "key_call_status";
    //信息提醒
    public static String KEY_MSG_STATUS = "key_msg_status";
    //app消息提醒
    public static String KEY_APP_MSG_STATUS = "key_app_msg_status";
    //勿扰模式时间段
    public static String KEY_DNT_STATUS = "key_dnt_format";
    //24小时心率
    public static String KEY_HEART_STATUS = "key_heart_status";
    //转腕亮屏
    public static String KEY_TURN_WRIST = "key_turn_wrist";





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
