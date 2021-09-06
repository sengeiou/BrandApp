package com.isport.blelibrary.managers;

import java.util.HashMap;

/**
 * @author Created by Marcos Cheng on 2017/4/6.
 */

public class Constants {
    /**
     * if you want to show log, let it be true ,or false
     * before you release the app, set it false first!
     */
    public static boolean IS_DEBUG = true;
    /**
     * if ues the default music control that designed in sdk ,set {@link #MUSIC_DEFAULT} to true, or set to false
     */
    public static boolean MUSIC_DEFAULT = true;

    /**
     * PRODUCT
     */
    public static final String PRODUCT_UFIT = "ufit";
    public static final String PRODUCT_OTHERS = "others";


    public static String defUserId = "0";

    /**
     * W337B only support whatsapp and skype
     */
    public final static String KEY_MESSAGE = "com.android.mms";
    public final static String KEY_13_PACKAGE = "com.tencent.mobileqq";///qq
    public final static String KEY_14_PACKAGE = "com.tencent.mm";///wechat
    public final static String KEY_14B_PACKAGE = "com.tencent.mm";///wechat
    public final static String KEY_15_PACKAGE = "com.skype.raider";///skype
    public final static String KEY_15_PACKAGE_1 = "com.skype.polaris";///skype
    public final static String KEY_15_PACKAGE_2 = "com.skype.rover";///skype
    public final static String KEY_16_PACKAGE = "com.facebook.katana";//facebook
    public final static String KEY_17_PACKAGE = "com.twitter.android";//twitter
    public final static String KEY_18_PACKAGE = "com.linkedin.android";//linkin
    public final static String KEY_19_PACKAGE = "com.instagram.android";//instagram
    public final static String KEY_1A_PACKAGE = "life.inovatyon.ds";
    public final static String KEY_1B_PACKAGE = "com.whatsapp";
    public final static String KEY_KAOKAO_TALK = "com.kakao.talk";
    public final static String MESSGE_OHTERS = "message_ohters";
    public final static String MESSGE_LINE = "jp.naver.line.android";
    /**
     * add in fireware that version is 89 or up
     */
    public final static String KEY_1C_PACKAGE = "com.facebook.orca";

    public static HashMap<String,Integer> msgTypeMap = new HashMap<>();

    //SMS
    public static final String SMS_PACKAGE_NAME = "com.apple.mobilesms";
    public static final String REDBUS_PACKAGE_NAME = "in.redbus.redBus";
    public static final String WHATS_APP_PACKAGE_NAME = "net.whatsapp.whatsapp";
    public static final String FACEBOOK_APP_PACK_NAME = "com.facebook.facebook";
    public static final String TWITTER_APP_PACK_NAME = "com.atebits.tweetie2";
    public static final String MESSENGER_APP_PACK_NAME = "com.facebook.messenger";
    public static final String INSTAGRAM_APP_PACK_NAME = "com.burbn.instagram";
    public static final String LINKED_APP_PACK_NAME = "com.linkedin.linkedin";
    public static final String CALENDAR_APP_PACK_NAME = "com.apple.mobilecal";
    public static final String YOUTUBE_APP_PACK_NAME = "com.google.ios.youtube";
    public static final String TELEGRAM_APP_PACK_NAME = "ph.telegra.telegraph";
    public static final String GMAIL_APP_PACK_NAME = "com.google.gmail";
    public static final String DAILYHUNT_APP_PACK_NAME = "com.eternoinfotech.newshunt";
    public static final String SNAPCHAT_APP_PACK_NAME = "com.toyopagroup.picaboo";
    public static final String HOSTAR_APP_PACK_NAME = "in.startv.hotstar";
    public static final String INSORTS_APP_PACK_NAME = "com.nis.app";
    public static final String PATYM_APP_PACK_NAME = "com.one97.paytm";
    public static final String AMAZOM_APP_PACK_NAME = "com.amazon.Amazon";
    public static final String FLIPKART_APP_NAME = "com.appflipkart.flipkart";
    public static final String PRIME_APP_NAME = "com.amazon.aiv.aivapp";
    public static final String NETFLIX_APP_NAME = "com.netflix.netflix";
    public static final String GPAY_APP_NAME = "com.google.paisa";
    public static final String PHONEPE_APP_NAME = "com.phonepe.phonepeapp";
    public static final String SWIGGY_APP_NAME = "bundl.swiggy";
    public static final String ZOMATO_APP_NAME = "com.zomato.zomato";
    public static final String MAKE_MY_TRIP_APP_NAME = "com.iphone.mmt";
    public static final String JIOTV_APP_NAME_APP_NAME = "com.jio.jioplay";
    public static final String CALL_PHONE_APP_NAME = "com.apple.mobilephone";
    public static final String OTHER_APP_NAME = "";

    public static final String YT_MUSIC_APP_NAME = "com.google.ios.youtubemusic";
    public static final String UBER_APP_NAME = "com.ubercab.uberclient";
    public static final String OAL_APP_NAME = "olacabs.olacabs";
    public static final String QQ_APP_NAME = "com.tencent.mqq";
    public static final String WECHAT_APP_NAME = "com.tencent.xin";
    public static final String QQ_APP_NAME2 = "com.tencent.mobileqq";
    public static final String WECHAT_APP_NAME2= "com.tencent.mm";

    static {
        msgTypeMap.put(SMS_PACKAGE_NAME,1); //电话
        msgTypeMap.put(REDBUS_PACKAGE_NAME,2);
        msgTypeMap.put(WHATS_APP_PACKAGE_NAME,3);

        msgTypeMap.put(FACEBOOK_APP_PACK_NAME,4);
        msgTypeMap.put(TWITTER_APP_PACK_NAME,5);
        msgTypeMap.put(MESSENGER_APP_PACK_NAME,6);
        msgTypeMap.put(INSTAGRAM_APP_PACK_NAME,7);
        msgTypeMap.put(LINKED_APP_PACK_NAME,8);
        msgTypeMap.put(CALENDAR_APP_PACK_NAME,9);
        msgTypeMap.put(YOUTUBE_APP_PACK_NAME,10);
        msgTypeMap.put(TELEGRAM_APP_PACK_NAME,11);
        msgTypeMap.put(GMAIL_APP_PACK_NAME,12);
        msgTypeMap.put(DAILYHUNT_APP_PACK_NAME,13);
        msgTypeMap.put(SNAPCHAT_APP_PACK_NAME,14);
        msgTypeMap.put(HOSTAR_APP_PACK_NAME,15);
        msgTypeMap.put(INSORTS_APP_PACK_NAME,16);
        msgTypeMap.put(PATYM_APP_PACK_NAME,17);
        msgTypeMap.put(AMAZOM_APP_PACK_NAME,18);
        msgTypeMap.put(FLIPKART_APP_NAME,19);
        msgTypeMap.put(PRIME_APP_NAME,20);
        msgTypeMap.put(NETFLIX_APP_NAME,21);
        msgTypeMap.put(GPAY_APP_NAME,22);
        msgTypeMap.put(PHONEPE_APP_NAME,23);
        msgTypeMap.put(SWIGGY_APP_NAME,24);
        msgTypeMap.put(ZOMATO_APP_NAME,25);
        msgTypeMap.put(MAKE_MY_TRIP_APP_NAME,26);
        msgTypeMap.put(JIOTV_APP_NAME_APP_NAME,27);
        msgTypeMap.put(CALL_PHONE_APP_NAME,28);
        msgTypeMap.put(OTHER_APP_NAME,29);

        msgTypeMap.put(QQ_APP_NAME2,30);
        msgTypeMap.put(QQ_APP_NAME,30);
        msgTypeMap.put(WECHAT_APP_NAME2,30);

    }








    /**
     * LocalBroadcastManager.getInstance(context).sendBroadcast() to send broadcast,so you
     * must register broadcastReceiver by LocalBroadcastManager if you need get these info of device
     */
    public final static String ACTION_QUERY_SEDENTARY = "com.isport.isportlibrary.Constants.ACTION_QUERY_SEDENTARY";
    /**
     * the extra is a list of sedentary
     */
    public final static String EXTRA_QUERY_SEDENTARY = "com.isport.isportlibrary.Constants.EXTRA_QUERY_SEDENTARY";

    public final static String ACTION_QUERY_ALARM = "com.isport.isportlibrary.Constants.ACTION_QUERY_ALARM";
    /**
     * the extra is a list of alarm
     */
    public final static String EXTRA_QUERY_ALARM = "com.isport.isportlibrary.Constants.EXTRA_QUERY_ALARM";

    public final static String ACTION_QUERY_DISPLAY = "com.isport.isportlibrary.Constants.ACTION_QUERY_DISPLAY";
    /**
     * the extra is the info of display
     */
    public final static String EXTRA_QUERY_DISPLAY = "com.isport.isportlibrary.Constants.EXTRA_QUERY_DISPLAY";

    public final static String ACTION_QUERY_SLEEP = "com.isport.isportlibrary.Constants.ACTION_QUERY_SLEEP";
    /**
     * the extra is the info of sleep
     */
    public final static String EXTRA_QUERY_SLEEP = "com.isport.isportlibrary.Constants.EXTRA_QUERY_SLEEP";

    public final static String ACTION_QUERY_TIMING_HEART_DETECT = "com.isport.isportlibrary.Constants" +
            ".ACTION_QUERY_TIMING_HEART_DETECT";
    /**
     * the extra is the info of timing heart detect
     */
    public final static String EXTRA_QUERY_TIMING_HEART_DETECT = "com.isport.isportlibrary.Constants" +
            ".EXTRA_QUERY_TIMING_HEART_DETECT";





}
