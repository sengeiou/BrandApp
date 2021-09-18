package com.isport.brandapp;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.home.bean.ScacleBean;
import com.isport.brandapp.home.bean.SportLastDataBean;
import com.isport.brandapp.bean.DeviceBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;

/**
 * @Author
 * @Date 2018/10/18
 * @Fuction
 */

public class AppConfiguration {


    public static String hasSportDate;
    public static HashMap<Integer, DeviceBean> deviceBeanList = new HashMap<>();//更为map好查询
    public static HashMap<Integer, DeviceBean> deviceMainBeanList = new HashMap<>();//更为map好查询
    public static boolean isConnected = false;//当前是否已经连接
    public static BaseDevice currentConnectDevice = null;//当前是否已经连接
    public static boolean isFirst = true;//第一次进入app
    public static boolean isFirstRealTime = true;//连接后实时第一次返回要去发送指令
    public static int userId = 249;//当前是否已经连接
    public static String challegeurl = "";//当前是否已经连接
    public static String ropedetailLighturl = "";//当前是否已经连接
    public static String ropedetailDarkurl = "";//当前是否已经连接
    public static String ropeCourseUrl = "";//当前是否已经连接
    public static boolean isSleepRealTime = false;//是否在睡眠监测页面
    public static boolean isSleepBind = false;//是否在绑定设备页面
    public static boolean isBindList = false;//是否在绑定设备列表页面
    public static boolean isWatchMain = false;//是否在Watch设置页面
    public static boolean hasSynced = false;
    public static ArrayList<ScacleBean> scacleBeansList = new ArrayList<>();


    public static ConcurrentHashMap<String, Boolean> thridMessageList;


    public static boolean isScaleScan;//是否在绑定体脂秤页面
    public static boolean isScaleRealTime;//是否在体脂秤称重页面
    public static boolean isScaleConnectting;//是否在体脂秤上称页面

    // public static String watchID;
    public static String braceletID;
    public static String ropeID;
    public static UserInfoBean loginBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));

    public static HashMap<Integer, SportLastDataBean> sportLastDataBeanHashMap = new HashMap<>();


    public static void saveUserInfo(UserInfoBean bean) {
        if (bean != null) {
            TokenUtil.getInstance().updatePeopleId(BaseApp.getApp(), String.valueOf(bean.getUserId()));
            CommonUserAcacheUtil.saveUsrInfo(String.valueOf(bean.getUserId()), bean);
            //设置给SDK
            String birthday = bean.getBirthday();
            String[] split = birthday.split("-");
            String weight = bean.getWeight().contains(".") ? bean.getWeight().split("\\.")[0] : bean.getWeight();
            ISportAgent.getInstance().setUserInfo(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(weight), Float.parseFloat(bean.getHeight()),
                    bean.getGender().equals("Male") ? 1 : 0,
                    TimeUtils.getAge(bean.getBirthday()), bean.getUserId());
        }
    }

}
