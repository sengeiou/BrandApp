package com.isport.blelibrary.utils;

/**
 * @Author
 * @Date 2018/12/20
 * @Fuction
 */

public interface BleRequest {

    /**
     * 通用请求
     */
    int Common_GetVersion = 1000;
    int Common_GetBattery = 1001;

    /**
     * 手诺嘉源手表
     */
    int Watch_SmartBand_sendDateAndTime = 2000;
    int Watch_SmartBand_sendUserInfo = 2001;
    int Watch_SmartBand_sendDeviceInfo = 2002;
    int Watch_SmartBand_sendScreenTime = 2003;
    int Watch_SmartBand_sendHandScreen = 2004;
    int Watch_SmartBand_sendTarget = 2005;
    int Watch_SmartBand_getSportData = 2006;

    /**
     * W516双指针手表
     */
    int Watch_W516_ASK_PAIR = 2010;//发送配对请求  1

    int Watch_W516_READ_STATUS = 2011;//获取设备状态，包括版本号，存储状态  1
    int Watch_W516_GENERAL = 2012;//通用设置  1
    int Watch_W516_GET_GENERAL = 2013;//获取通用设置  1
    int Watch_W516_SET_GENERAL_CALL = 2047;//获取通用设置  1
    int Watch_W516_SET_GENERAL_MEAAGE = 2048;//获取通用设置  1
    int Watch_W516_SET_USER = 2014;//用户资料设置    1
    int Watch_W516_GET_USER = 2015;//获取用户资料    1
    int Watch_W516_SET_CALENDER = 2016;//设置实时时间  1
    int Watch_W516_GET_CALENDER = 2017;//获取实时时间  1
    int Watch_W516_SET_ALARM = 2018;//设置闹钟  1
    int Watch_W516_GET_ALARM = 2019;//获取闹钟设置  1
    int Watch_W516_SET_SLEEP_SETTING = 2020;//睡眠检测设置  1
    int Watch_W516_GET_SLEEP_SETTING = 2021;//获取睡眠检测设置  1
    int Watch_W516_SET_SEDENTARY_TIME = 2022;//设置久坐提醒  1
    int Watch_W516_GET_SEDENTARY_TIME = 2023;//获取久坐提醒设置  1
    int Watch_W516_SEND_NOTIFICATION = 2024;//发送通知（andriod）电话和短信  1
    int Watch_W516_SET_HANDLE = 2025;//发送指针校准  1
    int Watch_W516_GET_DAILY_RECORD = 2026;//提取任意一天 24 小时数据  1  Todo 有跨分钟问题
    int Watch_W516_CLEAR_DAILY_RECORD = 2027;//清除所有 24 小时数据  1  Todo 会自动清除最前面一天的数据吗？如果只是过了半天呢，是不是只清除半天的数据
    int Watch_W516_GET_EXERCISE_DATA = 2028;//提取锻炼数据     1
    int Watch_W516_CLEAR_EXERCISE_DATA = 2029;//清除所有锻炼数据   1
    int Watch_W516_SET_DEFAULT = 2030;//设备恢复出厂设备   1
    //生产指令
    int Watch_W516_SET_SN_FACTORY = 2031;//生产阶段设置序列号   0不成功 返回01
    int Watch_W516_SET_SN_NORMALMODE = 2032;//产品生产完成后，更改序列号   1
    int Watch_W516_GET_SN_DATA = 2033;//读取序列号和生产日期   1
    int Watch_W516_SET_BELTNAME = 2034;//设置序列号   1
    int Watch_W516_TEST_RESET = 2035;//设备复位   1
    int Watch_W516_TEST_MOTORLED = 2036;//测试马达和 LED   1
    int Watch_W516_TEST_HANDLE = 2038;//测试指针   todo超超说固定值有误  0
    int Watch_W516_TEST_DISPLAY = 2039;//测试 OLED 显示   0不成功 返回01
    int Watch_W516_TEST_OHR = 2040;//测试 OHR   1
    int Watch_W516_DEVICE_TO_PHONE = 2041;//设备发送命令到 APP
    int Watch_W516_SEND_NOTIFICATION_N = 2042;//发送通知（andriod）通知  1
    int WATCH_W516_TESTDATA = 2043;//产生 7 天历史数据   1
    int WATCH_W516_STOP_TEST_MOTORLED = 2044;//停止测试马达和 LED  1
    //校准
    int WATCH_W516_SWITCH_MODE = 2045;//切换模式
    int WATCH_W516_ADJUST = 2046;//校准时针 分针
    int Watch_W560_SET_ALARM = 2049;//修改W560闹钟  1
    int Watch_W560_ADD_ALARM = 2050;//新增W560闹钟  1
    int Watch_W560_DELETE_ALARM = 2051;//删除W560闹钟  1


    /**
     * W311 设置
     */
    int BRACELET_W311_SET_USERINFO = 4001;
    int BRACELET_W311_SET_WEAR = 4002;
    int Bracelet_w311_find_bracelect = 4003;
    int bracelet_w311_set_alarm = 4004;
    int bracelet_w311_raise_hand = 4005;

    //setHeartTimingTest
    int bracelet_w311_set_automatic_HeartRate_And_Time = 4006;
    int bracelet_w311_set_display = 4007;
    int bracelet_w311_get_display = 4008;
    int BRACELET_W311_SET_SEDENTARY_TIME = 4022;//设置久坐提醒  1
    int bracelet_lost_remind = 4010;
    int bracelet_w311_is_open_raise_hand = 4011;
    int bracelet_w311_GET_SEDENTARY_TIME = 4023;//获取久坐提醒设置  1
    int bracelet_is_open_heartRate = 4024;//设置心率定点开关
    int bracelet_heartRate = 4025;//设置心率定点开关
    int bracelet_sync_data = 4030;
    int bracelet_send_phone = 4031;
    int bracelet_send_msg = 4032;
    int bracelet_send_disturb = 4033;
    int device_target_step = 4034;

    int w81_send_message = 4035;
    int w526_send_message = 4036;
    int MEASURE_TEMP = 4037;

    int W307J_SLEEP_SET = 4038;
    int W307J_SLEEP_GET = 4039;
    int bracelet_w311_is_open_raise_hand_307j = 4040;
    int rope_start_or_end = 4041;
    int rope_set_state = 4042;
    int rope_set_state_nopar = 4044;
    int rope_get_state = 4043;
    int rope_set_maxhr = 4045;
    int device_target_distance = 4046;
    int device_target_calorie = 4047;

    /**
     * w520设置
     */
    int watch_faces_setting = 5001;


    /**
     * sleep 享睡
     */
    int Sleep_Sleepace_setAutoCollection = 3000;
    int Sleep_Sleepace_setCollectionEnable = 3001;
    int Sleep_Sleepace_getCollectionStatus = 3002;
    int Sleep_Sleepace_setRealTimeEnable = 3003;
    int Sleep_Sleepace_setOriginalEnable = 3004;
    int Sleep_Sleepace_historyDownload = 3005;
    int Sleep_Sleepace_getEnvironmentalData = 3006;


    /**
     * 设备设置
     */
    int DEVICE_TIME_FORMAT = 6000;
    int DEVICE_SITCH_CAMERAVIEW = 6001;
    int DEVICE_OHTER_MESSAGE = 6002;
    int DEVICE_MEASURE_OXYGEN = 6003;
    int DEVICE_MEASURE_BLOODPRE = 6004;
    int DEVICE_MEASURE_ONECE_HR_DATA = 6009;
    int QUERY_ALAMR_LIST = 6005;
    int SET_WHEATHER = 6008;
    int QUERY_WATCH_FACE = 6010;
    int QUERY_TEMP_SUB = 6011;
    int SET_TEMP_SUB = 6012;
    int GET_VERSION = 6013;

    //W560读取目标设置
    int READ_DEVICE_GOAL = 0x00;

    //W526
    int DEVICE_BACKLIGHT_SCREEN_TIME = 7000;

    //所有设备通用
    int DEVICE_SYNC_TODAY_DATA = 8000;

    int W556_HR_SWITCH = 8001;
    int real_hr_switch = 8002;
}
