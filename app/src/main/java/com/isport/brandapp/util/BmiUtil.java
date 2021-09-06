package com.isport.brandapp.util;

import com.isport.brandapp.R;

import brandapp.isport.com.basicres.commonutil.UIUtils;

public class BmiUtil {
    /**
     * BMI1(-Float.MAX_VALUE,18.5f, "#4BC4FF", "偏瘦"),
     * BMI2(18.5f,24.0f, "#50E3C2", "标准"),
     * BMI3(24.0f,28.0f, "#FFD100", "偏胖"),
     * BMI4(28.0f,30.0f, "#FD944A", "肥胖"),
     * BMI5(30.0f,Float.MAX_VALUE, "#FA5F5F", "重度"),
     */

    Float[][] value = {{0f, 18.5f}, {18.5f, 24.0f}, {24.0f, 28.0f}, {28.0f, 30.0f}, {30.0f, 50.1f}};


    public static Integer getBmiCorrespondingColor(float currentBmi) {

        if (currentBmi >= 0 && currentBmi <= 18.5) {
            return UIUtils.getColor(R.color.common_stande_blue);
        } else if (currentBmi > 18.5 && currentBmi < 24.0f) {
            return UIUtils.getColor(R.color.common_stande_green);

        } else if (currentBmi > 24.0f && currentBmi < 28.0f) {
            return UIUtils.getColor(R.color.common_stande_yello);

        } else if (currentBmi > 28.0f && currentBmi < 30.0f) {
            return UIUtils.getColor(R.color.common_stande_orange);

        } else if (currentBmi > 30.0f && currentBmi < Integer.MAX_VALUE) {
            return UIUtils.getColor(R.color.common_stande_red);

        } else {
            return UIUtils.getColor(R.color.common_stande_blue);
        }


    }
}
