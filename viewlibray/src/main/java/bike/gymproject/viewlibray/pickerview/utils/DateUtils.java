package bike.gymproject.viewlibray.pickerview.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import phone.gym.jkcq.com.commonres.commonutil.CommonDateUtil;

/**
 * 日期工具类
 *
 * @author ck
 */
public class DateUtils {
    //yy/MM/dd HH:mm:ss
    //yy/MM/dd HH:mm:ss
    //

    /**
     * 获取当前的时间（UNIX时间戳形式）
     *
     * @return long
     */
    public static long getCurrentTimeUnixLong() {
        return System.currentTimeMillis() / 1000;
    }

    public static String getCurrentTimeUnixString() {
        return System.currentTimeMillis() / 1000 + "";
    }

    // 获取前面或后面的日期
    public static String getBeforeOrAfterDay(int day) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;

    }

    // 获取前面或后面的日期
    public static String getBeforeOrAfterDay(int day, String format) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;

    }

    /**
     * 毫秒转时间  00:00:00
     *
     * @return
     */
    public static String getTimeByMs(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            if (hour < 10) {
                sb.append("0" + hour + ":");
            } else {
                sb.append(hour + ":");
            }
        }
        if (minute > 0) {
            if (minute < 10) {
                sb.append("0" + minute + ":");
            } else {
                sb.append(minute + ":");
            }

        }
        if (second > 0) {
            if (second < 10) {
                sb.append("0" + second);
            } else {
                sb.append(second);
            }
        }
        return sb.toString();
    }


    /**
     * @param day 想要前多少天
     * @return
     */
    public static ArrayList<String> getBeforeOrAfterDayList(int day) {
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = day; i < 0; i++) {
            String data = getBeforeOrAfterDay(i);
            dateList.add(data);
        }
        return dateList;


    }

    // 获取从今开始的七天日期
    public static ArrayList<String> getAfterSevenDay() {
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String data = getBeforeOrAfterDay(i);
            dateList.add(data);
        }
        return dateList;
    }

    public static String getYMD(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(time);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(curDate);
        //获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getHM(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(time);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(curDate);
        //获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getMD(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        Date curDate = new Date(time);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(curDate);
        //获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getAMTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(time);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(curDate);
        //获取当前时间
        String str = formatter.format(curDate);

        return str;
       /* String ampmValues;
        if (mCalendar.get(Calendar.AM_PM) == 0) {
            ampmValues = "AM";
        } else {
            ampmValues = "PM";
        }
        return str + " " + ampmValues;*/
    }

    /**
     * 判断是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(String date) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = new Date();
        try {
            date2 = sdFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return android.text.format.DateUtils.isToday(date2.getTime());
    }

    public static String getMDHMAMTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        Date curDate = new Date(time);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(curDate);
        //获取当前时间
        String str = formatter.format(curDate);

        return str;
       /* String ampmValues;
        if (mCalendar.get(Calendar.AM_PM) == 0) {
            ampmValues = "AM";
        } else {
            ampmValues = "PM";
        }
        return str + " " + ampmValues;*/
    }

    // 获取从自定义开始天的后几天
    public static ArrayList<String> getAfterCustomDay(int startDayNum, int ContinuedDay) {
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = startDayNum; i < ContinuedDay + startDayNum; i++) {
            String data = getBeforeOrAfterDay(i);
            dateList.add(data);
        }
        return dateList;
    }

    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(time));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "周日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "周一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "周二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "周三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "周四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "周五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "周六";
        }
        return Week;
    }

    /**
     * 获取七天之内的时间以及周几
     *
     * @return
     */
    public static ArrayList<String> getSevevDayWithWeekName() {
        ArrayList<String> dataStrWithWeekName = new ArrayList<>();
        ArrayList<String> dataStrSeven = getAfterSevenDay();
        for (int i = 0; i < dataStrSeven.size(); i++) {
            String dataWeekName = getWeek(dataStrSeven.get(i));
            if (i == 0) {
                dataWeekName = "今天";
            }
            String lastData = dataStrSeven.get(i) + " " + dataWeekName;
            dataStrWithWeekName.add(lastData);
        }
        return dataStrWithWeekName;
    }

    /**
     * 通过年份和月份 得到当月的日子
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 返回当前月份1号位于周几
     *
     * @param year  年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1 一：2 二：3 三：4 四：5 五：6 六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        // Log.d("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取系统时间
     */
    public static String getSystemTime(String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String systemTime = formatter.format(curDate);
        return systemTime;
    }

    /**
     * 时间转换为时间戳
     *
     * @param timeStr 时间 例如: 2016-03-09
     * @param format  时间对应格式 例如: yyyy-MM-dd  HH:mm:ss
     * @return
     */
    public static long getTimeStamp(String timeStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param timeStr
     * @return
     */
    public static long getTimeStamp(String timeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 比较两个日期相差的天数
     *
     * @param s1
     * @param s2
     * @return
     * @throws ParseException
     */

    public static int DateCompare(String s1, String s2) throws ParseException {
        // 设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 得到指定模范的时间
        Date d1 = sdf.parse(s1);
        Date d2 = sdf.parse(s2);
        int i = (int) Math.abs(((d1.getTime() - d2.getTime()) / (24 * 3600 * 1000)));
        // System.out.println(i);
        return i;
    }

    /**
     * 比较两个日期谁大谁小 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
     *
     * @param t1
     * @param t2
     * @return
     */
    public static Boolean timeCompare(String t1, String t2, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(t1));
            c2.setTime(formatter.parse(t2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        if (result == 1) {
            return true;
        }
        return false;
    }

    // 将时间戳转为字符串
    public static String getStrTime(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    // 将时间戳转为字符串
    public static String getStrTime(long cc_time, String format) {
        format = "yyyy-MM-dd HH:mm:ss";
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    // 将时间戳转为字符串
    public static String getDateStringByTime(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        // long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    // 将时间戳转为字符串
    public static String getDateYMDByTime(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    // 将时间戳转为字符串
    public static String getStringByTime(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        // long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    // 将时间戳转为字符串
    public static String getMDByTime(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        // long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    public static String getTimeStringByTime(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    /**
     * 获取两个日期的时间差
     *
     * @return
     */
    public static int[] getTimeDifference(String startTime, String endTime) {
        int data[] = new int[2];
        String lastData = null;
        Date datass = null;
        Date datasss = null;
        try {
            DateFormat df_parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            datass = (Date) df_parseDate.parse(startTime);
            datasss = (Date) df_parseDate.parse(endTime);
            long hour = (datasss.getTime() - datass.getTime()) / (1000 * 60) / 60;
            long minute = (datasss.getTime() - datass.getTime()) / (1000 * 60) % 60;
            lastData = hour + "小时" + minute + "分钟";
            data[0] = (int) hour;
            data[1] = (int) minute;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * @param duration
     * @param
     * @return
     */
    public static CharSequence getHourTime(Long duration, boolean hasSecond) {
        if (duration <= 0) {
            return "00:00";
        } else {
            int secondnd = (int) ((duration / 1000) % 60);
            int min = (int) ((duration / 1000) / 60 % 60);
            int hour = (int) ((duration / 1000) / (60 * 60));
            String s = secondnd >= 10 ? String.valueOf(secondnd) : "0" + String.valueOf(secondnd);
            String m = min >= 10 ? String.valueOf(min) : "0" + String.valueOf(min);
            String h = hour >= 10 ? String.valueOf(hour) : "0" + String.valueOf(hour);
            if (hasSecond) {
                return h + ":" + m + ":" + s;
            }
            s = s.substring(0, 2);
            return h + ":" + m;
        }

    }

    /**
     * 获取任意时间的下一个月 描述:<描述函数实现的功能>.
     *
     * @param repeatDate
     * @return
     */
    public static String getPreMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(5, 7);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 获取任意时间的上一个月 描述:<描述函数实现的功能>.
     *
     * @param repeatDate
     * @return
     */
    public static String getLastMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(5, 7);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month - 2, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 转成日期
     *
     * @param dateString
     * @return
     */
    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    /**
     * 倒计时转换使用
     */
    public static String countDown(long timeStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(timeStr);
        return hms;
    }

    /**
     * 获取当前年月日集合
     */
    public static HashMap<String, Integer> getLocalTime() {
        HashMap<String, Integer> localTime = new HashMap<String, Integer>();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int sencond = c.get(Calendar.SECOND);
        localTime.put(DateUtilString.Year, year);
        localTime.put(DateUtilString.Month, month);
        localTime.put(DateUtilString.Day, day);
        localTime.put(DateUtilString.Hour, hour);
        localTime.put(DateUtilString.Min, minute);
        localTime.put(DateUtilString.Sec, sencond);
        return localTime;
    }

// public void test() {
// Calendar c = Calendar.getInstance();
// int year = c.get(Calendar.YEAR);
// int month = c.get(Calendar.MONTH) + 1;
// int day = c.get(Calendar.DAY_OF_MONTH);
// int hour = c.get(Calendar.HOUR_OF_DAY);
// int minute = c.get(Calendar.MINUTE);
// int sencond = c.get(Calendar.SECOND);
// }

    public interface DateUtilString {
        String Year = "year";
        String Month = "Month";
        String Day = "Day";
        String Hour = "hour";
        String Min = "min";
        String Sec = "sec";
    }

    /**
     * 得到一个格式化的时间
     *
     * @param time
     * @return 时：分：秒：毫秒
     */
    public static String getFormatTime(long time) {
        time = time / 1000;
        long second = time % 60;
        long minute = (time % 3600) / 60;
        long hour = time / 3600;
        // 毫秒秒显示两位
        //String strMillisecond = "" + (millisecond / 10);
        // 秒显示两位
        String strSecond = ("00" + second).substring(("00" + second).length() - 2);
        // 分显示两位
        String strMinute = ("00" + minute).substring(("00" + minute).length() - 2);
        // 时显示两位
        String strHour = ("00" + hour).substring(("00" + hour).length() - 2);
        return strHour + ":" + strMinute + ":" + strSecond;
    }

    /**
     * 得到一个格式化的时间
     *
     * @param time
     * @return 时：分：秒：毫秒
     */
    public static String getTimeStr(long time) {
        time = time / 1000;
        long second = time % 60;
        long minute = (time % 3600) / 60;
        long hour = time / 3600;
        // 毫秒秒显示两位
        // String strMillisecond = "" + (millisecond / 10);
        // 秒显示两位
        // String strSecond = ("00" + second)
        // .substring(("00" + second).length() - 2);
        // // 分显示两位
        // String strMinute = ("00" + minute)
        // .substring(("00" + minute).length() - 2);
        // // 时显示两位
        // String strHour = ("00" + hour).substring(("00" + hour).length() - 2);
        StringBuilder sb = new StringBuilder();
        if (hour > 0) {
            sb.append(hour + "小时");
            if (minute <= 0) {
                sb.append("零");
            }
        }

        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒。");
        }

        if (sb.length() == 0) {
            sb.append("0秒。");
        }

        return sb.toString();
    }

    /**
     * 得到一个格式化的时间
     *
     * @param time
     * @return 时：分：秒：毫秒
     */
    public static ArrayList<String> getLongTimeStr(long time) {
        ArrayList<String> strs = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));


        strs.add(CommonDateUtil.formatTwoStr(calendar.get(Calendar.MONTH) + 1) + "/" + CommonDateUtil.formatTwoStr
                (calendar.get(Calendar.DAY_OF_MONTH)));
        strs.add(CommonDateUtil.formatTwoStr(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + CommonDateUtil.formatTwoStr
                (calendar.get(Calendar.MINUTE)));


        return strs;
    }

    /**
     * @param brithday 传如来的值 2001-03-22
     * @return
     */
    public static ArrayList<Integer> getUserYearMonthDay(String brithday) {
        ArrayList<Integer> integers = new ArrayList<>();
        int year = Integer.parseInt(brithday.substring(0, 4));
        int month = Integer.parseInt(brithday.substring(5, 7));
        int day = Integer.parseInt(brithday.substring(8, 10));
        int hour = Integer.parseInt(brithday.substring(11, 13));
        int min = Integer.parseInt(brithday.substring(14, 15));
        integers.add(year);
        integers.add(month);
        integers.add(day);
        return integers;
    }

    public static ArrayList<String> getMonthAndDay(String brithday) {
        ArrayList strs = new ArrayList();

        int year = Integer.parseInt(brithday.substring(0, 4));
        int month = Integer.parseInt(brithday.substring(5, 7));
        int day = Integer.parseInt(brithday.substring(8, 10));
        int hour = Integer.parseInt(brithday.substring(11, 13));
        int min = Integer.parseInt(brithday.substring(14, 16));
        strs.add(month + "/" + day);
        strs.add(hour + ":" + min);
        return strs;
    }

    public static String getHourAndMin(String brithday) {
        int year = Integer.parseInt(brithday.substring(0, 4));
        int month = Integer.parseInt(brithday.substring(5, 7));
        int day = Integer.parseInt(brithday.substring(8, 10));
        int hour = Integer.parseInt(brithday.substring(11, 13));
        int min = Integer.parseInt(brithday.substring(14, 15));

        return hour + ":" + min;
    }

    public static int getAge(String brithday) {
        int year = Integer.parseInt(brithday.substring(0, 4));
        int month = Integer.parseInt(brithday.substring(5, 7));
        int day = Integer.parseInt(brithday.substring(8, 10));
        HashMap<String, Integer> dateMap = getLocalTime();
        int nowYear = dateMap.get(DateUtilString.Year);
        int nowMonth = dateMap.get(DateUtilString.Month);
        int nowDay = dateMap.get(DateUtilString.Day);
        int age = nowYear - year;
        if (nowMonth <= month) {
            // 如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (nowMonth == month) {
                if (nowDay < day)
                    age--;
            } else {
                age--;
            }
        }
        return age;

    }

    /**
     * 根据传入的时间格式 返回当前日期时间
     *
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    public static Date getDateFromString(String dateStr, String pattern) {
        Date resDate = null;
        try {
            resDate = new SimpleDateFormat(pattern).parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resDate;
    }

    /**
     * 格式化秒数
     *
     * @param timeTemp
     * @return
     */
    public static String formatTime(Object timeTemp) {
        int timeParam = 0;
        if (timeTemp instanceof Integer) {
            timeParam = (Integer) timeTemp;
        }
        if (timeTemp instanceof String) {
            timeParam = Integer.valueOf((String) timeTemp);
        }

        int second = timeParam % 60;
        int minuteTemp = timeParam / 60;
        if (minuteTemp > 0) {
            int minute = minuteTemp % 60;
            int hour = minuteTemp / 60;
            if (hour > 0) {
                return (hour > 10 ? (hour + "") : ("0" + hour)) + ":" + (minute > 10 ? (minute + "") : ("0" + minute))
                        + ":" + (second > 10 ? (second + "") : ("0" + second));
            } else {
                return "00:" + (minute > 10 ? (minute + "") : ("0" + minute)) + ":"
                        + (second > 10 ? (second + "") : ("0" + second));
            }
        } else {
            return "00:00:" + (second > 10 ? (second + "") : ("0" + second));
        }
    }

    public static String getAddress(String address) {
        if (!TextUtils.isEmpty(address) && address.length() > 1) {
            return address.substring(1, address.length());
        } else {
            return "192.168.1.166";
        }
    }

    public static String intToIp(long iIPInt) {
        String strIp;
        long iIPTmp = iIPInt;
        long[] intpoint = {0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            intpoint[3 - i] = (iIPTmp & 0xFF);
            ;
            iIPTmp = iIPTmp >> 8;
            if (0 == iIPTmp) {
                break;
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            buffer.append(intpoint[i] + ".");
        }
        buffer = buffer.delete(buffer.length() - 1, buffer.length());
        return buffer.toString();
    }

    public static long ipToLong(String ipAddress) {
        //将目标IP地址字符串strIPAddress转换为数字
        ipAddress = DateUtils.getAddress(ipAddress);
        String[] arrayIP = ipAddress.split("\\.");
        long sip1 = Long.parseLong(arrayIP[0]);
        long sip2 = Long.parseLong(arrayIP[1]);
        long sip3 = Long.parseLong(arrayIP[2]);
        long sip4 = Long.parseLong(arrayIP[3]);

        long r1 = sip1 * 256 * 256 * 256;
        long r2 = sip2 * 256 * 256;
        long r3 = sip3 * 256;
        long r4 = sip4;

        long result = r1 + r2 + r3 + r4;
        return result;
    }

    public static boolean isDrops(long time) {

        if ((System.currentTimeMillis() - time) / 1000 / 60 >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNewData(long time) {
        if ((System.currentTimeMillis() - time) / 1000 >= 5) {
            return false;
        } else {
            return true;
        }
    }
}
