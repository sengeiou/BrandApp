package com.isport.brandapp.device.bracelet.Utils;

import android.util.Log;

import com.isport.blelibrary.utils.Utils;
import com.isport.brandapp.R;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonutil.UIUtils;

public class RepeatUtils {
    public static String setRepeat(int deviceType, int repeat) {
        if (deviceType == JkConfiguration.DeviceType.Watch_W556) {
            return setW526RepeatStr(repeat);
        } else {
            return setRepeatStr(repeat);
        }

    }

    public static String setRepeatStr(int repeat) {
        int workeDay = 0;
        int everyDay = 0;
        int wenkend = 0;
        StringBuilder stringBuilderWeek = new StringBuilder();

        Log.e("setRepeatStr", "setRepeatStr=" + repeat);
        if (repeat == 0) {
            stringBuilderWeek.append(UIUtils.getString(R.string.once) + ",");
        } else {
            byte[] booleanArrayG = Utils.getBooleanArray((byte) repeat);


            if (Utils.byte2Int(booleanArrayG[6]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.mon) + ",");
                workeDay += 1;
                everyDay += 1;
            }
            if (Utils.byte2Int(booleanArrayG[5]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.tue) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[4]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.wed) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[3]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.thu) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[2]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.fri) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[1]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.sat) + ",");
                everyDay += 1;
                wenkend += 1;
            }

            if (Utils.byte2Int(booleanArrayG[7]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.sun) + ",");
                everyDay += 1;
                wenkend += 1;
            }

        }
        String weekStr = "";
        Log.e("everyDay", "everyDay=" + everyDay);
        if (everyDay == 7) {
            weekStr = UIUtils.getString(R.string.every_day);
        } else {
            weekStr = stringBuilderWeek.toString();
        }
        if (weekStr.endsWith(",")) {
            weekStr = weekStr.substring(0, weekStr.length() - 1);
        }
        return weekStr;

    }

    private static String setW526RepeatStr(int repeat) {
        StringBuilder stringBuilderWeek = new StringBuilder();
        int workeDay = 0;
        int everyDay = 0;
        int wenkend = 0;
        if (repeat == 128 || repeat == 0) {
            stringBuilderWeek.append(UIUtils.getString(R.string.once) + ",");
        } else {
            byte[] booleanArrayG = Utils.getBooleanArray((byte) repeat);


            if (Utils.byte2Int(booleanArrayG[6]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.mon) + ",");
                workeDay += 1;
                everyDay += 1;
            }
            if (Utils.byte2Int(booleanArrayG[5]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.tue) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[4]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.wed) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[3]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.thu) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[2]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.fri) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[1]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.sat) + ",");
                everyDay += 1;
                wenkend += 1;
            }

            if (Utils.byte2Int(booleanArrayG[7]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.sun) + ",");
                everyDay += 1;
                wenkend += 1;
            }
        }
        // String weekStr = stringBuilderWeek.toString();
        String weekStr = stringBuilderWeek.toString();


        if (everyDay == 7) {
            weekStr = UIUtils.getString(R.string.every_day);
        } else {
            weekStr = stringBuilderWeek.toString();
        }

        if (weekStr.endsWith(",")) {
            weekStr = weekStr.substring(0, weekStr.length() - 1);
        }

        return weekStr;
    }
}
