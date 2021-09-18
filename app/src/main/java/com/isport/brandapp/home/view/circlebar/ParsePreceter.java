package com.isport.brandapp.home.view.circlebar;

import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import com.isport.brandapp.util.DeviceDataUtil;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonutil.TokenUtil;

public class ParsePreceter {
    public static int parseProgress(int currentValue, float goalValue, int currentType) {

        //以BMI 21 为分界点，如果用户算出来的值为>21这把用户的BMI作为体脂的目标值

        if (currentValue == 0) {
            return 0;
        }
        if (currentType == JkConfiguration.DeviceType.BODYFAT) {
            UserInfoBean userInfoBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
            float value = DeviceDataUtil.calIBMValue(currentValue, Float.valueOf(userInfoBean.getHeight()));
            int precent;

            if (value > goalValue) {
                precent = (int) (goalValue / value * 100.0);
                return precent;
            } else {
                precent = (int) (value / goalValue * 100.0);
                return precent;
            }

        } else {
            int precent = (int) (100.0 / goalValue * currentValue);
            if (precent < 1) {
                return 1;
            }
            if (currentValue > goalValue) {
                return 100;
            } else {
                return precent;
            }
        }

    }

    public static String realProgressValue(float probarValue, float goalValue) {
        return ((int) (probarValue * goalValue / 100.0)) + "";
    }
}
