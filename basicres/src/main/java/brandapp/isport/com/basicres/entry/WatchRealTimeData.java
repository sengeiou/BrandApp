package brandapp.isport.com.basicres.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */
@Entity
public class WatchRealTimeData {
    @Id
    private Long id;//解决数据重叠bug
    private int stepNum;
    private float stepKm;
    private int cal;
    private String date;
    private String mac;
    public String getMac() {
        return this.mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
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
    @Generated(hash = 1605770869)
    public WatchRealTimeData(Long id, int stepNum, float stepKm, int cal,
            String date, String mac) {
        this.id = id;
        this.stepNum = stepNum;
        this.stepKm = stepKm;
        this.cal = cal;
        this.date = date;
        this.mac = mac;
    }
    @Generated(hash = 414124240)
    public WatchRealTimeData() {
    }

}
