package com.isport.blelibrary.db.table.bracelet_w311;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */
@Entity
public class Bracelet_W311_LiftWristToViewInfoModel {

//    界面显示的数据库
//    userId	用户ID	string
//    deviceId	设备ID	string
//    swichType	 抬腕亮屏的类型	int 0,1,2 0: 全天开,1特定时间段开 2关闭
//    startHour	开始时间时 hh:mm	int
//    startMin	开始时间分	int
//    endHour	结束时间时	int
//    endMin	结束时间分	int
//    isNextDay	是否跨天	boolean

    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private int swichType;//0,1,2
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;
    private boolean isNextDay;
    public boolean getIsNextDay() {
        return this.isNextDay;
    }
    public void setIsNextDay(boolean isNextDay) {
        this.isNextDay = isNextDay;
    }
    public int getEndMin() {
        return this.endMin;
    }
    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }
    public int getEndHour() {
        return this.endHour;
    }
    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }
    public int getStartMin() {
        return this.startMin;
    }
    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }
    public int getStartHour() {
        return this.startHour;
    }
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }
    public int getSwichType() {
        return this.swichType;
    }
    public void setSwichType(int swichType) {
        this.swichType = swichType;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1182781032)
    public Bracelet_W311_LiftWristToViewInfoModel(Long id, String userId,
            String deviceId, int swichType, int startHour, int startMin,
            int endHour, int endMin, boolean isNextDay) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.swichType = swichType;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.isNextDay = isNextDay;
    }
    @Generated(hash = 474147507)
    public Bracelet_W311_LiftWristToViewInfoModel() {
    }

}
