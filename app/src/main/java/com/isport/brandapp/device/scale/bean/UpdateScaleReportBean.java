package com.isport.brandapp.device.scale.bean;

import android.os.Parcel;

import brandapp.isport.com.basicres.commonbean.BaseBean;

/**
 * @Author
 * @Date 2018/10/23
 * @Fuction 上传体脂称测量数据返回报告id
 */

public class UpdateScaleReportBean extends BaseBean {
    private long fatSteelyardId;

    @Override
    public String toString() {
        return "UpdateScaleReportBean{" +
                "fatSteelyardId=" + fatSteelyardId +
                '}';
    }

    public long getFatSteelyardId() {
        return fatSteelyardId;
    }

    public void setFatSteelyardId(long fatSteelyardId) {
        this.fatSteelyardId = fatSteelyardId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.fatSteelyardId);
    }

    public UpdateScaleReportBean() {
    }

    protected UpdateScaleReportBean(Parcel in) {
        super(in);
        this.fatSteelyardId = in.readLong();
    }

    public static final Creator<UpdateScaleReportBean> CREATOR = new Creator<UpdateScaleReportBean>() {
        public UpdateScaleReportBean createFromParcel(Parcel source) {
            return new UpdateScaleReportBean(source);
        }

        public UpdateScaleReportBean[] newArray(int size) {
            return new UpdateScaleReportBean[size];
        }
    };
}
