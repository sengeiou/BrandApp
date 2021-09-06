package com.isport.blelibrary.db.table.scale;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * @Author
 * @Date 2019/1/7
 * @Fuction  四电极测量最终数据
 */

@Entity
public class Scale_FourElectrode_DataModel implements Serializable {

    @Id(autoincrement = true)
    private Long id;//准备让它自增长
    private String deviceId;
    private String userId;
    private String dateStr;//日期
    private long timestamp;//时间戳
    private int sex;//男 0  女 1
    private int hight;
    private int age;
    private int r;
    private String reportId;
    private float weight;
    private double BFP;//脂肪率%
    private double SLM;//肌肉重kg
    private double BWP;//水含量%
    private double BMC;//骨盐量
    private double VFR;//内脏脂肪等级
    private double PP;//蛋白质%
    private double SMM;//骨骼肌kg
    private double BMR;//基础代谢
    private double BMI;//身体质量指数
    private double SBW;//标准体重kg
    private double MC;//肌肉控制
    private double WC;//体重控制
    private double FC;//脂肪控制
    private int MA;//身体年龄
    private int SBC;//评分
    public int getSBC() {
        return this.SBC;
    }
    public void setSBC(int SBC) {
        this.SBC = SBC;
    }
    public int getMA() {
        return this.MA;
    }
    public void setMA(int MA) {
        this.MA = MA;
    }
    public double getFC() {
        return this.FC;
    }
    public void setFC(double FC) {
        this.FC = FC;
    }
    public double getWC() {
        return this.WC;
    }
    public void setWC(double WC) {
        this.WC = WC;
    }
    public double getMC() {
        return this.MC;
    }
    public void setMC(double MC) {
        this.MC = MC;
    }
    public double getSBW() {
        return this.SBW;
    }
    public void setSBW(double SBW) {
        this.SBW = SBW;
    }
    public double getBMI() {
        return this.BMI;
    }
    public void setBMI(double BMI) {
        this.BMI = BMI;
    }
    public double getBMR() {
        return this.BMR;
    }
    public void setBMR(double BMR) {
        this.BMR = BMR;
    }
    public double getSMM() {
        return this.SMM;
    }
    public void setSMM(double SMM) {
        this.SMM = SMM;
    }
    public double getPP() {
        return this.PP;
    }
    public void setPP(double PP) {
        this.PP = PP;
    }
    public double getVFR() {
        return this.VFR;
    }
    public void setVFR(double VFR) {
        this.VFR = VFR;
    }
    public double getBMC() {
        return this.BMC;
    }
    public void setBMC(double BMC) {
        this.BMC = BMC;
    }
    public double getBWP() {
        return this.BWP;
    }
    public void setBWP(double BWP) {
        this.BWP = BWP;
    }
    public double getSLM() {
        return this.SLM;
    }
    public void setSLM(double SLM) {
        this.SLM = SLM;
    }
    public double getBFP() {
        return this.BFP;
    }
    public void setBFP(double BFP) {
        this.BFP = BFP;
    }
    public float getWeight() {
        return this.weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public String getReportId() {
        return this.reportId;
    }
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    public int getR() {
        return this.r;
    }
    public void setR(int r) {
        this.r = r;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getHight() {
        return this.hight;
    }
    public void setHight(int hight) {
        this.hight = hight;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getDateStr() {
        return this.dateStr;
    }
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
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
    @Generated(hash = 690208564)
    public Scale_FourElectrode_DataModel(Long id, String deviceId, String userId,
            String dateStr, long timestamp, int sex, int hight, int age, int r,
            String reportId, float weight, double BFP, double SLM, double BWP,
            double BMC, double VFR, double PP, double SMM, double BMR, double BMI,
            double SBW, double MC, double WC, double FC, int MA, int SBC) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.dateStr = dateStr;
        this.timestamp = timestamp;
        this.sex = sex;
        this.hight = hight;
        this.age = age;
        this.r = r;
        this.reportId = reportId;
        this.weight = weight;
        this.BFP = BFP;
        this.SLM = SLM;
        this.BWP = BWP;
        this.BMC = BMC;
        this.VFR = VFR;
        this.PP = PP;
        this.SMM = SMM;
        this.BMR = BMR;
        this.BMI = BMI;
        this.SBW = SBW;
        this.MC = MC;
        this.WC = WC;
        this.FC = FC;
        this.MA = MA;
        this.SBC = SBC;
    }
    @Generated(hash = 986530246)
    public Scale_FourElectrode_DataModel() {
    }

}
