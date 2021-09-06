package com.isport.brandapp.device.publicpage;

import android.content.Context;
import android.content.Intent;

import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.bracelet.playW311.PlayW311Activity;
import com.isport.brandapp.util.DeviceTypeUtil;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class GoActivityUtil {

    public static void goActivityUnbindDevice(int deviceType, Context context) {

        if (!DeviceTypeUtil.isContainW81(deviceType)) {
            Intent intent = new Intent(context, ActivityDeviceUnbindGuide.class);
            context.startActivity(intent);
        }
    }

    public static void goActivityPlayerDevice(int deviceType, DeviceBean bean, Context context) {
        // Logger.myLog("goActivityPlayerDevice getPlayBanImage deviceType" + deviceType + "bean " + bean.deviceType);
        Intent guideintent = new Intent(context, PlayW311Activity.class);
        guideintent.putExtra(JkConfiguration.DEVICE, deviceType);
        context.startActivity(guideintent);
    }
}
