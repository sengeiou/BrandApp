package com.isport.blelibrary.db.table.watch;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2019/1/8
 * @Fuction
 */

@Entity
public class Watch_SmartBand_StepTargetModel {

    @Id
    private Long id;
    private String deviceId;
    private String userId;
    private int target;
    public int getTarget() {
        return this.target;
    }
    public void setTarget(int target) {
        this.target = target;
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 140852768)
    public Watch_SmartBand_StepTargetModel(Long id, String deviceId, String userId,
            int target) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.target = target;
    }
    @Generated(hash = 869676618)
    public Watch_SmartBand_StepTargetModel() {
    }


}
