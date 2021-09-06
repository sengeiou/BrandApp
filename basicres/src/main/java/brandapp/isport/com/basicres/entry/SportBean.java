package brandapp.isport.com.basicres.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SportBean {
    @Id
    private Long id;
    String userId;
    String publicId;
    int sporttype;
    String avgPace;
    String avgSpeed;
    String calories;
    String distance;
    long endTimestamp;
    long startTimestamp;
    String maxHeartRate;
    String avgHeartRate;
    String maxPace;
    String minHeartRate;
    String minPace;
    String step;
    int timeLong;
    private String paceArr;
    private String heartRateArr;
    private String coorArr;
    public String getCoorArr() {
        return this.coorArr;
    }
    public void setCoorArr(String coorArr) {
        this.coorArr = coorArr;
    }
    public String getHeartRateArr() {
        return this.heartRateArr;
    }
    public void setHeartRateArr(String heartRateArr) {
        this.heartRateArr = heartRateArr;
    }
    public String getPaceArr() {
        return this.paceArr;
    }
    public void setPaceArr(String paceArr) {
        this.paceArr = paceArr;
    }
    public int getTimeLong() {
        return this.timeLong;
    }
    public void setTimeLong(int timeLong) {
        this.timeLong = timeLong;
    }
    public String getStep() {
        return this.step;
    }
    public void setStep(String step) {
        this.step = step;
    }
    public String getMinPace() {
        return this.minPace;
    }
    public void setMinPace(String minPace) {
        this.minPace = minPace;
    }
    public String getMinHeartRate() {
        return this.minHeartRate;
    }
    public void setMinHeartRate(String minHeartRate) {
        this.minHeartRate = minHeartRate;
    }
    public String getMaxPace() {
        return this.maxPace;
    }
    public void setMaxPace(String maxPace) {
        this.maxPace = maxPace;
    }
    public String getAvgHeartRate() {
        return this.avgHeartRate;
    }
    public void setAvgHeartRate(String avgHeartRate) {
        this.avgHeartRate = avgHeartRate;
    }
    public String getMaxHeartRate() {
        return this.maxHeartRate;
    }
    public void setMaxHeartRate(String maxHeartRate) {
        this.maxHeartRate = maxHeartRate;
    }
    public long getStartTimestamp() {
        return this.startTimestamp;
    }
    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
    public long getEndTimestamp() {
        return this.endTimestamp;
    }
    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
    public String getDistance() {
        return this.distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getCalories() {
        return this.calories;
    }
    public void setCalories(String calories) {
        this.calories = calories;
    }
    public String getAvgSpeed() {
        return this.avgSpeed;
    }
    public void setAvgSpeed(String avgSpeed) {
        this.avgSpeed = avgSpeed;
    }
    public String getAvgPace() {
        return this.avgPace;
    }
    public void setAvgPace(String avgPace) {
        this.avgPace = avgPace;
    }
    public int getSporttype() {
        return this.sporttype;
    }
    public void setSporttype(int sporttype) {
        this.sporttype = sporttype;
    }
    public String getPublicId() {
        return this.publicId;
    }
    public void setPublicId(String publicId) {
        this.publicId = publicId;
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
    @Generated(hash = 754892589)
    public SportBean(Long id, String userId, String publicId, int sporttype,
            String avgPace, String avgSpeed, String calories, String distance,
            long endTimestamp, long startTimestamp, String maxHeartRate,
            String avgHeartRate, String maxPace, String minHeartRate,
            String minPace, String step, int timeLong, String paceArr,
            String heartRateArr, String coorArr) {
        this.id = id;
        this.userId = userId;
        this.publicId = publicId;
        this.sporttype = sporttype;
        this.avgPace = avgPace;
        this.avgSpeed = avgSpeed;
        this.calories = calories;
        this.distance = distance;
        this.endTimestamp = endTimestamp;
        this.startTimestamp = startTimestamp;
        this.maxHeartRate = maxHeartRate;
        this.avgHeartRate = avgHeartRate;
        this.maxPace = maxPace;
        this.minHeartRate = minHeartRate;
        this.minPace = minPace;
        this.step = step;
        this.timeLong = timeLong;
        this.paceArr = paceArr;
        this.heartRateArr = heartRateArr;
        this.coorArr = coorArr;
    }
    @Generated(hash = 84405280)
    public SportBean() {
    }


}
