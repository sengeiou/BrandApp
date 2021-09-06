package com.isport.brandapp.sport.location.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.os.Process;

import java.util.List;

public class PermissionUtils {
    /**
     * 判断服务是否运行
     *
     * @param context
     * @param cls
     * @return
     */
    public static boolean isServiceRunning(Context context, Class<? extends Service> cls) {
        boolean isServiceRunning = false;
        ComponentName collectorComponent = new ComponentName(context, cls);
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (manager != null) {
            List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);
            if (runningServices != null) {
                for (ActivityManager.RunningServiceInfo service : runningServices) {
                    if (service.service.equals(collectorComponent)) {
                        if (service.pid == Process.myPid()) {
                            isServiceRunning = true;
                        }
                    }
                }
            }
        }
        return isServiceRunning;
    }

}
