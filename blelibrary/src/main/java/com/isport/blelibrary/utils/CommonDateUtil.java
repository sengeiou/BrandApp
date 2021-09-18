package com.isport.blelibrary.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class CommonDateUtil {
    public static String formatOnePoint(double value) {
        String strNumber = String.format("%.1f", Math.round(value * 10) / 10.0f);
        return strNumber;
    }



    public static String formatPaceForSpeed(double currSpeed){
        try {
            if(String.valueOf(currSpeed).equals("0.0")){
                return "--";
            }
            //将速度转换为千米/小时
            double kmSpeed = div(currSpeed,3.6d,2);
            //再用60/速度得到配速
            double tempPace = div(60d,kmSpeed,2);

            //转换成0'0''格式
            //小数点后的
            String afterStr = StringUtils.substringAfter(tempPace+"",".");
            //小数点前的
            String beforeStr = StringUtils.substringBefore(tempPace+"",".");
            if(afterStr.equals("00") || afterStr.equals("0"))
                return beforeStr+"'"+"00''";
            return beforeStr+"'"+afterStr+"''";
        }catch (Exception e){
            e.printStackTrace();
            return "--";
        }

    }


    /**
     * 两个double相除，保留小数
     * @param v1 被除数
     * @param v2 除数
     * @param scale 保留小数的点位
     * @return
     */
    public static Double div(Double v1, Double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    " The scale must be a positive integer or zero ");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }




    public static double formatOnePointDouble(double value) {
        String strNumber = String.format("%.1f", Math.round(value * 10) / 10.0f);
        return Double.parseDouble(strNumber);
    }

    //华氏度与摄氏度转换
    public static float ctof(float temp) {

        float fTemp = (temp * 1.8f) + 32;
        fTemp = (float) (Math.floor(fTemp * 100 / 10) / 10.0f);
        // Math.floor(temp *100) / 10f
        return fTemp;
    }

    public static String formatTwoStr(int number) {
        String strNumber = String.format("%02d", number);
        return strNumber;
    }

    public static String formatTwoPoint(double value) {


        String result = String.format("%.2f", Math.round(value * 100) / 100f);
        // String result2 = String.format("%.2f", 3.145);

    /*    DecimalFormat format = new DecimalFormat("0.00");
        String numberStr = format.format(value);*/
        return result;
    }

    //距离向下取整
    public static float formatFloor(float dis, boolean isfloor) {
        if (isfloor) {
            return (float) (Math.floor(dis / 10) * 10f) / 1000;

        } else {
            return dis / 1000;
        }

    }

    public static String formatTwoPoint(float value) {

        String result = String.format("%.2f", Math.round(value * 100) / 100.0f);
        // String result2 = String.format("%.2f", 3.145);

    /*    DecimalFormat format = new DecimalFormat("0.00");
        String numberStr = format.format(value);*/
        return result;
    }

    public static String formatTwoPointThree(double value) {
        String result = String.format("%.3f", Math.round(value * 1000) / 1000f);
     /*   DecimalFormat format = new DecimalFormat("0.000");
        String numberStr = format.format(value);*/
        return result;
    }

    public static String formatInterger(double value) {
        long data = Math.round(value);
       /* DecimalFormat format = new DecimalFormat("0");
        String numberStr = format.format(value);*/
        return data + "";
    }

    public static String getW81DeviceName(String name, String mac) {
        return name + "-" + Utils.replaceDeviceMacUpperCase(mac);
    }
}
