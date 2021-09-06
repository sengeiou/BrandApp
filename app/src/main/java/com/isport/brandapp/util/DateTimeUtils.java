package com.isport.brandapp.util;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Desc 日期操作工具
 * Created by Admin
 * Date 2021/8/25
 */
public class DateTimeUtils {


    public static int formatLongDateToInt(Long timeStr){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
            String timeFormat = sdf.format(new Date(timeStr));

            int hour = Integer.parseInt(StringUtils.substringBefore(timeFormat,":"));
            int minute = Integer.parseInt(StringUtils.substringAfter(timeFormat,":"));

            return hour * 60 + minute;

        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }
}
