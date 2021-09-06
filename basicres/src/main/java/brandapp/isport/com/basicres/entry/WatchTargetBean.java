package brandapp.isport.com.basicres.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @创建者 bear
 * @创建时间 2019/3/1 17:56
 * @描述
 */
@Entity
public class WatchTargetBean {

    @Id
    private Long id;//解决数据重叠bug
    private String userId;
    private String deviceId;
    private int targetStep;
    private int targetDistance;
    private int targetCalorie;
    public int getTargetStep() {
        return this.targetStep;
    }
    public void setTargetStep(int targetStep) {
        this.targetStep = targetStep;
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
    public int getTargetCalorie() {
        return this.targetCalorie;
    }
    public void setTargetCalorie(int targetCalorie) {
        this.targetCalorie = targetCalorie;
    }
    public int getTargetDistance() {
        return this.targetDistance;
    }
    public void setTargetDistance(int targetDistance) {
        this.targetDistance = targetDistance;
    }
    @Generated(hash = 584927215)
    public WatchTargetBean(Long id, String userId, String deviceId, int targetStep,
            int targetDistance, int targetCalorie) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.targetStep = targetStep;
        this.targetDistance = targetDistance;
        this.targetCalorie = targetCalorie;
    }
    @Generated(hash = 220158101)
    public WatchTargetBean() {
    }

}
