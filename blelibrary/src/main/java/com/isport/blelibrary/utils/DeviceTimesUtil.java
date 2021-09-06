package com.isport.blelibrary.utils;

public class DeviceTimesUtil {

    public static int getTime(int day, int type) {
        // type0 311 12秒
        // type1 w516 7秒
        if (type == 0) {
            return day * 15;
        } else if (type == 1) {
            return day * 7;
        } else if (type == 2) {
            return 30;
        } else {
            return day * 15;
        }
    }
}
