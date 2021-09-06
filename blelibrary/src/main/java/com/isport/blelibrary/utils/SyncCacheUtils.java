package com.isport.blelibrary.utils;

import android.content.Context;
import android.text.TextUtils;

public class SyncCacheUtils {

    //不是解绑操作的时候才弹解绑的对话框
    public static boolean isUnbind = false;
    //保存设置的时间间隔
    //保存同步的时间间隔
    //保存下拉的时间间隔


    public static void clearStartSync(Context context) {
        ACache aCache = ACache.get(context);
        aCache.remove("sync_start");
    }

    public static void saveStartSync(Context context) {
        ACache aCache = ACache.get(context);
        aCache.put("sync_start", "true", 60);
    }

    public static boolean getStartSync(Context context) {
        try {
            ACache aCache = ACache.get(context);
            String str = aCache.getAsString("sync_start");
            if (TextUtils.isEmpty(str)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void setUnBindState(boolean isUnbind) {
        SyncCacheUtils.isUnbind = isUnbind;
    }

    public static boolean getUnbindState() {
        return isUnbind;
    }

    //是否是第一次绑定w311
    public static void saveFirstBindW311(Context context) {
        ACache aCache = ACache.get(context);
        aCache.put("firstBindW311", "true");
    }

    public static void clearFirstBindW311(Context context) {
        ACache aCache = ACache.get(context);
        aCache.remove("firstBindW311");
    }

    public static boolean getFirstBindW311State(Context context) {
        try {
            ACache aCache = ACache.get(context);
            String str = aCache.getAsString("firstBindW311");
            if (TextUtils.isEmpty(str)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void saveSetting(Context context) {
        ACache aCache = ACache.get(context);
        aCache.put("setting_sync", "true");
    }

    public static boolean getSetting(Context context) {
        try {
            ACache aCache = ACache.get(context);
            String str = aCache.getAsString("setting_sync");
            if (TextUtils.isEmpty(str)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean getSyncDataTime(Context context) {
        try {
            ACache aCache = ACache.get(context);
            String str = aCache.getAsString("sync_data");
            if (TextUtils.isEmpty(str)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void saveSyncDataTime(Context context) {
        ACache aCache = ACache.get(context);
        aCache.put("sync_data", "true", 30 * 60);
    }

    public static void clearSetting(Context context) {
        ACache aCache = ACache.get(context);
        aCache.remove("setting_sync");
    }

    public static void clearSysData(Context context) {
        ACache aCache = ACache.get(context);
        aCache.remove("sync_data");
    }
    //是否进入到升级模式的

    public static void clearDFUMode(Context context, String mac) {
        ACache aCache = ACache.get(context);
        aCache.remove(mac);
    }

    public static boolean getDFUMode(Context context, String mac) {
        try {
            ACache aCache = ACache.get(context);
            String str = aCache.getAsString(mac);
            if (TextUtils.isEmpty(str)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void isDFUMode(Context context, String mac, boolean isEnter) {
        ACache aCache = ACache.get(context);
        if (isEnter) {
            aCache.put(mac, "true");
        } else {
            aCache.put(mac, "");
        }

    }
}
