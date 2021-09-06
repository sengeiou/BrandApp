package com.isport.blelibrary.db.table.watch;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2018/11/6
 * @Fuction 运动数据，当天实时数据也是运动数据，会实时更新
 */
@Entity
public class Watch_SmartBand_SportDataModel {

    @Id(autoincrement = true)
    private Long id;//解决数据重叠bug
    private int totalSteps;
    private float totalDistance;
    private int totalCalories;
    private String dateStr;
    private String deviceId;
    private String userId;
    private long timestamp;
    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getDateStr() {
        return this.dateStr;
    }
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
    public int getTotalCalories() {
        return this.totalCalories;
    }
    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }
    public float getTotalDistance() {
        return this.totalDistance;
    }
    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }
    public int getTotalSteps() {
        return this.totalSteps;
    }
    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1337686721)
    public Watch_SmartBand_SportDataModel(Long id, int totalSteps,
            float totalDistance, int totalCalories, String dateStr,
            String deviceId, String userId, long timestamp) {
        this.id = id;
        this.totalSteps = totalSteps;
        this.totalDistance = totalDistance;
        this.totalCalories = totalCalories;
        this.dateStr = dateStr;
        this.deviceId = deviceId;
        this.userId = userId;
        this.timestamp = timestamp;
    }
    @Generated(hash = 156426402)
    public Watch_SmartBand_SportDataModel() {
    }

}
