package brandapp.isport.com.basicres.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author
 * @Date 2018/11/6
 * @Fuction
 */
@Entity
public class WatchSportDataBean {
    @Id
    private Long id;//解决数据重叠bug
    private int stepNum;
    private float stepKm;
    private int cal;
    private String dateStr;
    private int sportTime;
    private int maxHR;
    private int minHR;
    private String mac;
    public String getMac() {
        return this.mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public int getMinHR() {
        return this.minHR;
    }
    public void setMinHR(int minHR) {
        this.minHR = minHR;
    }
    public int getMaxHR() {
        return this.maxHR;
    }
    public void setMaxHR(int maxHR) {
        this.maxHR = maxHR;
    }
    public int getSportTime() {
        return this.sportTime;
    }
    public void setSportTime(int sportTime) {
        this.sportTime = sportTime;
    }
    public String getDateStr() {
        return this.dateStr;
    }
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
    public int getCal() {
        return this.cal;
    }
    public void setCal(int cal) {
        this.cal = cal;
    }
    public float getStepKm() {
        return this.stepKm;
    }
    public void setStepKm(float stepKm) {
        this.stepKm = stepKm;
    }
    public int getStepNum() {
        return this.stepNum;
    }
    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1499971005)
    public WatchSportDataBean(Long id, int stepNum, float stepKm, int cal,
            String dateStr, int sportTime, int maxHR, int minHR, String mac) {
        this.id = id;
        this.stepNum = stepNum;
        this.stepKm = stepKm;
        this.cal = cal;
        this.dateStr = dateStr;
        this.sportTime = sportTime;
        this.maxHR = maxHR;
        this.minHR = minHR;
        this.mac = mac;
    }
    @Generated(hash = 1828070717)
    public WatchSportDataBean() {
    }


}
