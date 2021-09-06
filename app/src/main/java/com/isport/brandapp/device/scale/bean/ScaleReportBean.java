package com.isport.brandapp.device.scale.bean;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.commonbean.BaseBean;

/**
 * @Author
 * @Date 2018/10/24
 * @Fuction 体脂称报告数据
 */

public class ScaleReportBean extends BaseBean {

    private String weight;
    private String bmi;
    private long creatTime;
    private List<ScaleBean> list;

    @Override
    public String toString() {
        return "ScaleReportBean{" +
                "weight='" + weight + '\'' +
                ", bmi='" + bmi + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", list=" + list.toString() +
                '}';
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public List<ScaleBean> getList() {
        return list;
    }

    public void setList(List<ScaleBean> list) {
        this.list = list;
    }

    public static Creator<ScaleReportBean> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.weight);
        dest.writeString(this.bmi);
        dest.writeLong(this.creatTime);
        dest.writeList(this.list);
    }

    public ScaleReportBean() {
    }

    protected ScaleReportBean(Parcel in) {
        super(in);
        this.weight = in.readString();
        this.bmi = in.readString();
        this.creatTime = in.readLong();
        this.list = new ArrayList<ScaleBean>();
        in.readList(this.list, ScaleBean.class.getClassLoader());
    }

    public static final Creator<ScaleReportBean> CREATOR = new Creator<ScaleReportBean>() {
        @Override
        public ScaleReportBean createFromParcel(Parcel source) {
            return new ScaleReportBean(source);
        }

        @Override
        public ScaleReportBean[] newArray(int size) {
            return new ScaleReportBean[size];
        }
    };
}
