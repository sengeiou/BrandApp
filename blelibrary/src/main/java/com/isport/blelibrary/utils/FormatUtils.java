package com.isport.blelibrary.utils;

import java.text.DecimalFormat;

/**
 * @Author
 * @Date 2018/10/24
 * @Fuction
 */

public class FormatUtils {
    public static float formatOnePointReturnFloat(double value) {
        DecimalFormat format = new DecimalFormat("0.0");
        String numberStr = format.format(value);
        return Float.valueOf(numberStr);
    }

    public static float formatTwoPointReturnFloat(double value) {
        DecimalFormat format = new DecimalFormat("0.00");
        String numberStr = format.format(value);
        return Float.valueOf(numberStr);
    }
}
