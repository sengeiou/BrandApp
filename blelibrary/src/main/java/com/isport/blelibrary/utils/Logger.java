package com.isport.blelibrary.utils;

import android.util.Log;

import java.util.Calendar;

/**
 * @Author
 * @Date 2018/9/30
 * @Fuction Log类
 */

public class Logger {

    private static String LOG_PREFIX = "ISport_";
    private static final int MAX_LOG_TAG_LENGTH = 23;

    // 因 Android Studio 中规定，Library Model 的 BuildConfig.DEBUG 在 debug/release 模式下均为 true，所以， DEBUG 设置为成员变量，自主控制
    // use setDebuggable()
    private static boolean DEBUG = true;
    private static boolean INTENALDEBUG = false;

    public static String makeLogTag(String str) {
        int prefixLen = LOG_PREFIX.length();
        if (str.length() > MAX_LOG_TAG_LENGTH - prefixLen) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - prefixLen - 1);
        }
        return LOG_PREFIX + str;
    }

    private static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void myLog(String msg) {
        if (DEBUG) {
            Log.e("MyLog", msg);
        }
    }


    public static void myLog(String tag,String msg){
        if(DEBUG)
            Log.e(tag,msg);
    }

    public static void LOGD(String tag, String msg) {
        if (DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, msg);
        }
    }

    public static void LOGD(String tag, String msg, Throwable cause) {
        if (DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, msg, cause);
        }
    }

    public static void LOGSP(String tag, String msg) {
        Log.i(makeLogTag(tag), msg);
    }

    public static void LOGV(String tag, String msg) {
        if (DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(makeLogTag(tag), msg);
        }
    }

    public static void LOGV(String tag, String msg, Throwable cause) {
        if(null == tag || msg == null)
            return;
        if (DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(makeLogTag(tag), msg, cause);
        }
    }

    public static void LOGI(String tag, String msg) {
        if(null == tag || msg == null)
            return;
        if (DEBUG)
            Log.i(makeLogTag(tag), msg);
    }

    public static void LOGII(String tag, String msg) {
        if(null == tag || msg == null)
            return;
        if (INTENALDEBUG)
            Log.i(makeLogTag(tag), msg);
    }


    public static void LOGI(String tag, String msg, Throwable cause) {
        Log.i(tag, msg, cause);
    }

    public static void LOGW(String tag, String msg) {
        Log.w(makeLogTag(tag), msg);
    }

    public static void LOGW(String tag, String msg, Throwable cause) {
        Log.w(tag, msg, cause);
    }


    public static void LOGE(String tag, String msg) {
        Log.e(makeLogTag(tag), msg);
    }

    public static void LOGE(String tag, String msg, Throwable cause) {
        Log.e(tag, msg, cause);
    }


    public static void setLogPrefix(String prefix) {
        LOG_PREFIX = prefix;
    }

    // 仅供测试时开启
    private static void setDebuggable(boolean debuggable) {
        DEBUG = debuggable;
    }

    public static void setDebugMode(int mode){
        if(mode == ( Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                + (Calendar.getInstance().get(Calendar.MONTH)+1) * 2
                + Calendar.getInstance().get(Calendar.YEAR)))
            setDebuggable(true);
        else
            setDebuggable(false);
    }


    public static boolean getDebugMode(){
        return DEBUG;
    }

    private Logger() {
    }

}
