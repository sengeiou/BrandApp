package com.isport.blelibrary.bluetooth;

/**
 * 错误码
 */
public interface ISportResCode {

    int ERR_BLE_SERVICE_NOT_REG=-2001;//bind service为空或者address为空

    int ERR_UNKNOWN = 0;
    int ERR_APP_KEY_MISS = -1000; //APP ID缺失

    /*****************
     * 开门相关
     ***************/
    int ERR_DEVICE_ADDRESS_EMPTY = -2001;// 设备地址错误
    int ERR_BLUETOOTH_DISABLE = -2002;   // 蓝牙未开启
    int ERR_DEVICE_INVALID = -2003;   // 设备失效
    int ERR_DEVICE_CONNECT_FAIL = -2004;    // 无法建立连接
    int ERR_DEVICE_OPEN_FAIL = -2005;  // 开门失败
    int ERR_DEVICE_DISCONNECT = -2006;    // 与断开连接
    int ERR_DEVICE_PARSE_RESPONSE_FAIL = -2007;  // 解析数据失败
    int ERR_APP_ID_MISMATCH = -2008;    // App Id 与应用不匹配
    int ERR_NO_AVAILABLE_DEVICES = -2009;    // 附近没有可用设备
    int ERR_DEVICE_SCAN_TIMEOUT = -2010 ;	//设备扫描超时
    int ERR_DEVICE_CONNECT_TIMEOUT = -2011; //设备连接超时
    int ERR_KEY_STRING_PARSE_FAIL = -2012;    //钥匙信息解析失败
    int ERR_SHAKE_KEY = -2013 ;              //摇一摇钥匙参数信息有误
    int ERR_OPEN_PARAMETER_WRONG = -2014;    //开门参数中存在nil或空字符串


    /**
     * 蓝牙相关
     */
    int ERR_BLE_CHARACTER_FOUND_FAILURE = -2016;    //蓝牙特征值发现失败
    int ERR_BLE_SERVICE_FOUND_FAILURE = -2015;      //蓝牙服务发现失败
    int ERR_BLE_UPDATE_VALUE_FAILURE = -2017;       //获取蓝牙订阅值错误

    int ERR_KEY_TIMEOUT = -2018;                 //钥匙有效期失效


    int ERR_NO_BLUETOOTH    = -2019;      //没有蓝牙设备
    int ERR_BLUETOOTH_WRONG = - 2020;
    int ERR_REQUST_OPEN_DATA_FAILURE = -2021;   //网络请求开门数据失败
    int ERR_DEVICE_CALLBACK_TIMEOUT = -2022;        //开门超时无返回
    int ERR_BLE_NOTWORK = - 2024;                   //不支持BLE
    int ERR_BLE_WRITEFAILED = -2027;                //写入失败
    int ERR_ANOPEN_THREAD_ALLREADYRUN = -2028; //开门已经在运行了


    int ERR_OPEN_KEY_WRONG = -2029;                //秘钥对比不正确
    int ERR_OPNE_INDEX_WRONG= -2030;                //本次序号小于等于上次的序号
    int ERR_OPEN_FAIURE = -2031;                //秘钥和序号匹配正确，但开门失败





    /***************
     * 授权相关
     ***************/
    int ERR_NETWORK_ERROR = -3001;
    // 用户授权失效
    int ERR_AUTHORIZE_INVALID = -3002;
    int ERR_USER_NON_EXIST = -3003;

    /*************
     * 获取钥匙
     *****************/
    int ERR_NO_NEW_KEY = -3004;
    int ERR_NO_USEFUL_KEY = -3005;

    /*************
     * APP设置相关
     *****************/
    int ERR_JAR_PROGUARDED = -4001;
}
