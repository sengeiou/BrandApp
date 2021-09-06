package com.jkcq.platform.umeng.analysis;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

public class UmengEventManager {

    public static final String LAUNCHER_TIMES="0001";
    public static final String USER_LOGIN="0002";
    public static final String BIND_PHONE="0003";
    public static final String QUERY_PHSICAL_RECORD="0004";
    public static final String QUERY_MYGROUP_COURSE="0005";
    public static final String QUERY_SPORT_RECORD="0006";
    public static final String CONNECT_BLE="0007";
    public static final String QUERY_MESSAGE="0008";
    public static final String CLICK_AD="0009";
    public static final String WECHAT_LOGIN="0010";
    public static final String QQ_LOGIN="0011";
    public static final String QUREY_MY_TRAIN="0012";
    public static final String QUERY_MY_PERSONAL="0013";
    public static final String QUERY_RECOVERY="0014";
    public static final String WECHAT_PAY_SUCCESS="0015";
    public static final String ZFB_PAY_SUCCESS="0016";
    public static final String STORED_PAY_SUCCESS="0017";
    public static final String QUERY_ORDER_LIST="0018";
    public static final String QUERY_ORDER_DETAIL="0019";
    public static final String QUERY_COURSE_DETAIL_FROM_ORDER_LIST="0020";
    public static final String INVITE_FRIEND_FROM_ORDER_LIST="0021";
    public static void uploadEvent( Context context,String eventId){
        HashMap<String ,Object> map=new HashMap<>();
        map.put("jkcq",eventId);
        MobclickAgent.onEventObject(context, eventId, map);
    }
}
