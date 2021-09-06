package bike.gymproject.viewlibray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @创建者 bear
 * @创建时间 2019/3/11 11:35
 * @描述
 */
public class SleepFormatUtils {


    public static String sleepTimeFormatByIndex(int index) {

        //开始时间


        int minuteR;
        if (index <= 239) {
            //昨晚的数据 20:00-23:59
            minuteR = 1200 + index;
        } else {
            //今天的数据 0:00-20:00
            minuteR = index - 240 ;
        }
        long minute = minuteR % 60;
        long hour = minuteR / 60;
        // 分显示两位
        String strMinute = ("00" + minute).substring(("00" + minute).length() - 2);
        // 时显示两位
        String strHour = ("00" + hour).substring(("00" + hour).length() - 2);
        return strHour + ":" + strMinute;
    }

    public static String sleepTimeFormatByIndex(int index, Calendar calendar) {

        //开始时间

        Date date = new Date(calendar.getTimeInMillis());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.MINUTE, index);
        //calendar.add(Calendar.MINUTE, index);
        return dataToString(calendar1.getTime(), "HH:mm");
    }

    public static String sleepTimeEndFormatByIndex(int index, Calendar calendar) {

        //开始时间

        Date date = new Date(calendar.getTimeInMillis());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.MINUTE, index);
        //calendar.add(Calendar.MINUTE, index);
        return dataToString(calendar1.getTime(), "HH:mm");
    }

    public static String dataToString(Date date, String format) {
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        return format1.format(date);
    }
}
