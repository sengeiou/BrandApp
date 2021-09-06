package com.isport.brandapp.util;

import com.isport.brandapp.bean.DeviceBaseBean;
import com.isport.brandapp.bean.DeviceBean;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class DeviceDataParseUtil {

    public static DeviceBaseBean getDeviceTypeBean(int type, DeviceBean deviceBean) {
        if (type==JkConfiguration.DeviceType.SLEEP) {
            return deviceBean.sleepBean;

        } else if (type==JkConfiguration.DeviceType.WATCH_W516) {
            return deviceBean.sportBean;

        } else if (type==JkConfiguration.DeviceType.BODYFAT) {
            return deviceBean.weightBean;

        }
        return null;
    }
}
