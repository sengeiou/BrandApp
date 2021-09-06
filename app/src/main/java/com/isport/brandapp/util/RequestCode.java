package com.isport.brandapp.util;

/**
 * @Author
 * @Date 2019/1/9
 * @Fuction
 */

public interface RequestCode {

    int Request_getBindDeviceList = 1001;//获取绑定设备列表
    int Request_bindDevice = 1002;//绑定设备
    int Request_unBindDevice = 1003;//解绑设备
    int Request_getClockTime = 1004;//获取定时睡眠时间
    int Request_setClockTime = 1005;//设置定时睡眠时间
    int Request_getScaleHistoryData = 1007;//查询体重历史记录
    int Request_getScalerReportData = 1008;//查询体重报告
    int Request_getWatchHistoryData = 1009;//查询步数记录
    int Request_getSleepHistoryData = 1010;//查询心率记录
}
