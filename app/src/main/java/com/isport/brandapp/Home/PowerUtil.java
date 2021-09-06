package com.isport.brandapp.Home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

public class PowerUtil {
    /**
     * 跳转到指定应用的首页
     */
    public static void showActivity(@NonNull String packageName, @NonNull Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    /**
     * 跳转到指定应用的指定页面
     */
    public static void showActivity(@NonNull String packageName, @NonNull String activityDir, @NonNull Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public void goSetting(Context context) {
        Log.e("goSetting","Build.BRAND.toLowerCase()="+Build.BRAND.toLowerCase());
        if (isHuawei()) {
            goHuaweiSetting(context);
        } else if (isSmartisan()) {
            goSmartisanSetting(context);
        } else if (isLeTV()) {
            goLetvSetting(context);
        } else if (isMeizu()) {
            goMeizuSetting(context);
        } else if (isOPPO()) {
            goOPPOSetting(context);
        } else if (isSamsung()) {
            goSamsungSetting(context);
        } else if (isVIVO()) {
            goVIVOSetting(context);
        } else if (isXiaomi()) {
            goXiaomiSetting(context);
        }

    }


    public boolean isHuawei() {
        if (Build.BRAND == null) {
            return false;
        } else {
            return Build.BRAND.toLowerCase().equals("huawei") || Build.BRAND.toLowerCase().equals("honor");
        }
    }

    private void goHuaweiSetting(@NonNull Context context) {
        try {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", context);
        } catch (Exception e) {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity", context);
        }
    }

    public static boolean isXiaomi() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("xiaomi");
    }

    private void goXiaomiSetting(@NonNull Context context) {
        showActivity("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity", context);
    }

    public static boolean isOPPO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("oppo");
    }

    private void goOPPOSetting(@NonNull Context context) {
        try {
            showActivity("com.coloros.phonemanager", context);
        } catch (Exception e1) {
            try {
                showActivity("com.oppo.safe", context);
            } catch (Exception e2) {
                try {
                    showActivity("com.coloros.oppoguardelf", context);
                } catch (Exception e3) {
                    showActivity("com.coloros.safecenter", context);
                }
            }
        }
    }

    public static boolean isVIVO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("vivo");
    }

    private void goVIVOSetting(@NonNull Context context) {
        showActivity("com.iqoo.secure", context);
    }

    public static boolean isMeizu() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("meizu");
    }

    private void goMeizuSetting(@NonNull Context context) {
        showActivity("com.meizu.safe", context);
    }

    public static boolean isSamsung() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("samsung");
    }

    private void goSamsungSetting(@NonNull Context context) {
        try {
            showActivity("com.samsung.android.sm_cn", context);
        } catch (Exception e) {
            showActivity("com.samsung.android.sm", context);
        }
    }

    public static boolean isLeTV() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("letv");
    }

    private void goLetvSetting(@NonNull Context context) {
        showActivity("com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity", context);
    }

    public static boolean isSmartisan() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("smartisan");
    }

    private void goSmartisanSetting(@NonNull Context context) {
        showActivity("com.smartisanos.security", context);
    }
}
