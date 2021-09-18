package com.isport.brandapp.sport.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.isport.brandapp.home.fragment.LatLongData;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class IndoorRunDatas implements Parcelable {

    public long timer; // 运动时长毫秒值
    public String strTime; // 运动时常

    public double currentDis;//这个是需要报的米数
    public boolean isDisPlayer;

    //    public Double distance;// 运动距离
    public Double distance = 0.0;// 运动距离(初始设置为0.0)
    public int totalStep;// 总步数
    public int disToStep;//距离转换成步数

    // 热量：卡路里
    public Double calories = 0.0;
    // 速度：米每秒
    public Double velocity = 0.0;

    public double averageVelocity;// 平均速度。          公里/小时
    public float instantVelocity;// 即时速度，配速    分钟/公里
    public String strinstantVelocity;// 即时速度，配速    分钟/公里

    public String heartRate;

    public String sportType;
    public static final String SPORT_INDOOR = "2";
    public static final String SPORT_OUTDOOR = "1";

    //此次跑步的发起方式。
    public String runType;

    public static final String FREE_RUN = "freeRun";
    public static final String PLAN_RUN = "planRun";
    public static final String PK_RUN = "pkRun";

    //目前用户使用的设备。
    public String kitType;
    public static final String RING_WITH_HEART_RATE = "ring_with_heart_rate";
    public static final String RING_WITHOUT_HEART_RATE = "ring_without_heart_rate";
    public static final String HEART_RATE_WITH_STEP = "heart_rate_with_step";
    public static final String HEART_RATE_WITHOUT_STEP = "heart_rate_without_step";
    public static final String JUST_PHONE = "just_phone";

    /**
     * 跑步的历史数据。k——Long 跑步的时刻点。 v_Integer 跑步的公里数，1 2 3 ...
     */
    public LinkedHashMap<Long, Double> runDatasMap = new LinkedHashMap<>();
    /**
     * 记录运动过程中心率的数组。 k:时间 , v:心率值 .
     */
    public LinkedHashMap<Long, HrBean> heartRateMap = new LinkedHashMap<>();


    public LinkedHashMap<Long, PaceBean> paceBean = new LinkedHashMap<>();

    //保存经纬度的值 每暂停一次保存一次经纬度的值
    public ArrayList Latlists = new ArrayList<>();


    public LinkedHashMap<Integer, ArrayList<LatLongData>> latMap = new LinkedHashMap<>();


    //public LinkedHashMap<Long,String> h=


    public void clearData() {
        currentDis = 0;
        isDisPlayer = false;
        timer = 0;
        strTime = "";
        latMap.clear();
        Latlists.clear();
        latMap.clear();
        heartRateMap.clear();
        runDatasMap.clear();
        paceBean.clear();
        runType = "";
        averageVelocity = 0;// 平均速度。          公里/小时
        instantVelocity = 0;// 即时速度，配速    分钟/公里
        strinstantVelocity = "00'00''";// 即时速度，配速    分钟/公里
        calories = 0.0;
        totalStep = 0;
        disToStep = 0;
        velocity = 0.0;

        distance = 0.0;

    }

    public IndoorRunDatas() {
        super();
    }

    public IndoorRunDatas setCalories(Double calories) {
        this.calories = calories;
        return this;
    }

    public IndoorRunDatas setVelocity(Double velocity) {
        this.velocity = velocity;
        return this;
    }

    public IndoorRunDatas setTimer(long timer) {
        this.timer = timer;
        return this;
    }

    public IndoorRunDatas setTime(String time) {
        this.strTime = time;
        return this;
    }

    public IndoorRunDatas setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    public IndoorRunDatas setTotalStep(int totalStep) {
        this.totalStep = totalStep;
        return this;
    }

    public IndoorRunDatas setAverageVelocity(double averageVelocity) {
        this.averageVelocity = averageVelocity;
        return this;
    }

    public IndoorRunDatas setInstantVelocity(float instantVelocity) {
        this.instantVelocity = instantVelocity;
        return this;
    }

    public IndoorRunDatas setHeartRate(String heartRate) {
        this.heartRate = heartRate;
        return this;
    }

    public IndoorRunDatas setKitType(String kitType) {
        this.kitType = kitType;
        return this;
    }

    @Override
    public String toString() {
        return "IndoorRunDatas [timer=" + timer + ", time=" + strTime + ", distance=" + distance
                + ", totalStep=" + totalStep + ", calories=" + calories + ", velocity=" + velocity
                + ", averageVelocity=" + averageVelocity + ", instantVelocity=" + instantVelocity
                + ", heartRate=" + heartRate + ", runType=" + runType + ", kitType=" + kitType
                + ", runDatasMap=" + runDatasMap + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.timer);
        dest.writeString(this.strTime);
        dest.writeValue(this.distance);
        dest.writeInt(this.totalStep);
        dest.writeValue(this.calories);
        dest.writeValue(this.velocity);
        dest.writeDouble(this.averageVelocity);
        dest.writeDouble(this.instantVelocity);
        dest.writeString(this.heartRate);
        dest.writeString(this.sportType);
        dest.writeString(this.runType);
        dest.writeString(this.kitType);
        dest.writeSerializable(this.runDatasMap);
        dest.writeSerializable(this.heartRateMap);
    }

    protected IndoorRunDatas(Parcel in) {
        this.timer = in.readLong();
        this.strTime = in.readString();
        this.distance = (Double) in.readValue(Double.class.getClassLoader());
        this.totalStep = in.readInt();
        this.calories = (Double) in.readValue(Double.class.getClassLoader());
        this.velocity = (Double) in.readValue(Double.class.getClassLoader());
        this.averageVelocity = in.readDouble();
        this.instantVelocity = in.readFloat();
        this.heartRate = in.readString();
        this.sportType = in.readString();
        this.runType = in.readString();
        this.kitType = in.readString();
        this.runDatasMap = (LinkedHashMap<Long, Double>) in.readSerializable();
        this.heartRateMap = (LinkedHashMap<Long, HrBean>) in.readSerializable();
    }

    public static final Creator<IndoorRunDatas> CREATOR = new Creator<IndoorRunDatas>() {
        @Override
        public IndoorRunDatas createFromParcel(Parcel source) {
            return new IndoorRunDatas(source);
        }

        @Override
        public IndoorRunDatas[] newArray(int size) {
            return new IndoorRunDatas[size];
        }
    };
}