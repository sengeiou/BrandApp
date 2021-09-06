package com.isport.brandapp.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.R;
import com.isport.brandapp.sport.bean.SportSettingBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonutil.ACache;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import phone.gym.jkcq.com.commonres.common.AllocationApi;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class SportAcacheUtil {
    /**
     * 缓存user
     */
    public static SportSettingBean getSportTypeSetting(int key) {
        ACache aCache = ACache.get(BaseApp.getApp());
        Gson mGson = new Gson();
        SportSettingBean sportSettingBean = mGson.fromJson(aCache.getAsString(AllocationApi.BaseUrl + TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()) + key), SportSettingBean.class);


        if (sportSettingBean == null) {
            sportSettingBean = new SportSettingBean();
            sportSettingBean.isOnScreen = false;
            sportSettingBean.isPlayer = false;
            sportSettingBean.isHrRemind = false;
            sportSettingBean.isPaceRemind = false;
            sportSettingBean.hrMaxValue = 250;
            sportSettingBean.hrMinValue = 30;


            if (key == JkConfiguration.SportType.sportBike) {
                sportSettingBean.paceMaxValue = 40;
                sportSettingBean.paceMinValue = 10;
                sportSettingBean.currentPaceValue = 25;//每分钟走多少公里


            } else if (key == JkConfiguration.SportType.sportWalk) {
                sportSettingBean.paceMaxValue = 30 * 60;
                sportSettingBean.paceMinValue = 5 * 60;
                sportSettingBean.currentPaceValue = 20 * 60;//每分钟走多少公里

            } else if (key == JkConfiguration.SportType.sportOutRuning) {
                //70%-80%
                sportSettingBean.paceMaxValue = 10 * 60;
                sportSettingBean.paceMinValue = 3 * 60;
                sportSettingBean.currentPaceValue = 6 * 60;//每分钟走多少公里

            } else {
                //70%-80%
                sportSettingBean.paceMaxValue = 10 * 60;
                sportSettingBean.paceMinValue = 3 * 60;
                sportSettingBean.currentPaceValue = 6 * 60;//每分钟走多少公里

            }
            sportSettingBean.currentHrValue = 140;

        }


        UserInfoBean userInfoBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
        int maxhr = 198;
        if (userInfoBean != null) {
            int age = parseAge(userInfoBean.getBirthday());
            if (userInfoBean.getGender().equals("Male")) {
                maxhr = 220 - age;
            } else {
                maxhr = 226 - age;
            }
        }
        String minHrValue = "";
        String maxHrValue = "";

        if (key == JkConfiguration.SportType.sportBike) {
            minHrValue = CommonDateUtil.formatInterger(maxhr * 0.6);
            maxHrValue = CommonDateUtil.formatInterger(maxhr * 0.7);
            sportSettingBean.tips = String.format(UIUtils.getString(R.string.remind_hr_bike), minHrValue, maxHrValue);

        } else if (key == JkConfiguration.SportType.sportWalk) {
            minHrValue = CommonDateUtil.formatInterger(maxhr * 0.5);
            maxHrValue = CommonDateUtil.formatInterger(maxhr * 0.6);
            sportSettingBean.tips = String.format(UIUtils.getString(R.string.remind_hr_walk), minHrValue, maxHrValue);
        } else if (key == JkConfiguration.SportType.sportOutRuning) {
            //70%-80%
            minHrValue = CommonDateUtil.formatInterger(maxhr * 0.7);
            maxHrValue = CommonDateUtil.formatInterger(maxhr * 0.8);
            sportSettingBean.tips = String.format(UIUtils.getString(R.string.remind_hr_running), minHrValue, maxHrValue);
        } else {
            //70%-80%
            minHrValue = CommonDateUtil.formatInterger(maxhr * 0.7);
            maxHrValue = CommonDateUtil.formatInterger(maxhr * 0.8);
            sportSettingBean.tips = String.format(UIUtils.getString(R.string.remind_hr_running), minHrValue, maxHrValue);
        }


        return sportSettingBean;
    }


    public static int parseAge(String birthday) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date(System.currentTimeMillis());
        } finally {
            Calendar calendar = Calendar.getInstance();
            Calendar currentcalendar = Calendar.getInstance();
            calendar.setTime(date);
            return currentcalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        }

    }

    public static void SaveSportTypeSetting(SportSettingBean sportSettingBean, int key) {


        ACache aCache = ACache.get(BaseApp.getApp());
        Gson mGson = new Gson();
        aCache.put(AllocationApi.BaseUrl + TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()) + key, mGson.toJson(sportSettingBean));


    }


    public static void saveSportHomeData(String resultHomeSportData) {
        ACache aCache = ACache.get(BaseApp.getApp());
        String todayDate = TimeUtils.getTodayYYYYMMDD();
        // Logger.myLog("getSportHomeData saveSportHomeData:" + (AllocationApi.BaseUrl + TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()) + todayDate));
        aCache.put(AllocationApi.BaseUrl + TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()) + todayDate, resultHomeSportData, 12 * 60 * 60);
    }

    public static String getSportHomeData() {
        ACache aCache = ACache.get(BaseApp.getApp());
        String todayDate = TimeUtils.getTodayYYYYMMDD();
        // Logger.myLog("getSportHomeData" + (AllocationApi.BaseUrl + TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()) + todayDate));
        String sportSettingBean = aCache.getAsString(AllocationApi.BaseUrl + TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()) + todayDate);
        if (TextUtils.isEmpty(sportSettingBean)) {
            sportSettingBean = "0";
        }
        return sportSettingBean;
    }

    public static void removeSettingBean(String key) {

        if (TextUtils.isEmpty(key)) {
            return;
        }
        ACache aCache = ACache.get(BaseApp.getApp());
        aCache.remove(AllocationApi.BaseUrl + TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()) + key);
        aCache.clear();
    }

    public static void clearAll() {


        ACache aCache = ACache.get(BaseApp.getApp());
        aCache.clear();
    }


    public static void saveUsrInfo(String key, UserInfoBean info) {
        if (!TextUtils.isEmpty(key)) {
            ACache aCache = ACache.get(BaseApp.getApp());
            Gson mGson = new Gson();
            aCache.put(AllocationApi.BaseUrl + key, mGson.toJson(info));
        }
    }

}
