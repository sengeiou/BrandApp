package com.isport.brandapp.device.bracelet.braceletModel;

import com.isport.blelibrary.db.table.DeviceTempUnitlTable;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_24H_hr_SettingModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_AlarmModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DeviceInfoModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DisplayModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_LiftWristToViewInfoModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_ThridMessageModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_WearModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_w311_hrModel;
import com.isport.blelibrary.db.table.w811w814.DeviceTimeFormat;
import com.isport.blelibrary.db.table.w811w814.FaceWatchMode;
import com.isport.blelibrary.db.table.watch_w516.Watch_W560_AlarmModel;

import java.util.ArrayList;

public interface IW311SettingModel {

    /**
     * 获取显示的项目
     *
     * @param userId
     * @param deviceId
     * @return
     */
    Bracelet_W311_DisplayModel getDispalyItem(String userId, String deviceId);


    boolean saveDisplayItem(Bracelet_W311_DisplayModel model);

    /**
     * 抬手亮屏设置
     *
     * @param userId
     * @param deviceId
     * @return
     */
    Bracelet_W311_LiftWristToViewInfoModel getLifWristToViewInfo(String userId, String deviceId);

    /**
     * a保存或修改抬手亮屏设置
     *
     * @param model
     * @return
     */

    boolean saveLifWristToViewInfo(Bracelet_W311_LiftWristToViewInfoModel model);


    /**
     * 获取佩戴的方式
     */
    Bracelet_W311_WearModel getWearModel(String userId, String deviceId);

    /**
     * 保存佩戴方式
     */
    boolean saveWearModel(Bracelet_W311_WearModel model);

    /**
     * 获取需要推送的第三方消息
     */
    Bracelet_W311_ThridMessageModel getW311ThridMessageItem(String userId, String deviceId);

    /**
     * 保存第三方推送的消息Item
     */
    boolean saveW311ThridItem(Bracelet_W311_ThridMessageModel model);


    Bracelet_w311_hrModel getHrSetting(String userId, String deviceId);

    boolean saveHrSetting(Bracelet_w311_hrModel model);

    /**
     * 获取闹钟信息
     */
    ArrayList<Bracelet_W311_AlarmModel> getAllAlarm(String deviceId, String userId);
    /**
     * 获取闹钟信息
     */
    ArrayList<Bracelet_W311_AlarmModel> getAllAlarmW526(String deviceId, String userId);

    /**
     * 获取W560闹钟信息
     */
    ArrayList<Watch_W560_AlarmModel> getAllAlarmW560(String deviceId, String userId);

    /**
     * 更新闹钟条目
     */
    boolean updateAlarmModel(Bracelet_W311_AlarmModel model);

    /**
     * 更新W560闹钟条目
     */
    boolean updateW560AlarmModel(Watch_W560_AlarmModel model);

    /**
     * 更新所有的条目
     */
    boolean updateAllAlarmModel(ArrayList<Bracelet_W311_AlarmModel> lists, String deviceId, String userId);


    /**
     * 删除条目
     */
    boolean deletArarmItem(Bracelet_W311_AlarmModel model);

    /**
     * 删除W560条目
     */
    boolean deletW560ArarmItem(Watch_W560_AlarmModel model);

    /**
     * 删除本地所有W560条目
     */
    boolean deletW560ArarmItems(String userId, String deviceId);

    /**
     * 获取w311设备信息
     */
    Bracelet_W311_DeviceInfoModel getDevieVersion(String userId, String deviceId);

    /**
     * 保存w311s设备信息
     */
    boolean saveDevieVersion(Bracelet_W311_DeviceInfoModel model);

    /**
     * 获取w311 是否开启24小时开关
     */
    Bracelet_W311_24H_hr_SettingModel get24HSwitchState(String userId, String devcieId);

    /**
     * 保存311 24小时心率开关的状态
     */
    boolean save24HhrSwitchState(Bracelet_W311_24H_hr_SettingModel model);


    /**
     * 保存设备的表盘设置
     */

    boolean saveWatchFacesSetting(String devcieId, String userId, int faceMode);


    /**
     * 获取表盘信息
     *
     * @param userId
     * @param devcieId
     * @return
     */
    FaceWatchMode getWatchModeSetting(String userId, String devcieId);


    public boolean saveTimeFormat(String deviceId, String userId, int format);


    public DeviceTimeFormat getTimeFormate(String userId, String deviceId);


    public DeviceTempUnitlTable getTempUtil(String userId,String deviceId);

    public boolean saveTempUtil(String userId,String deviceId,String unitl);


}
