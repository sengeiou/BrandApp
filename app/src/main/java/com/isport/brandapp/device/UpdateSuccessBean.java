package com.isport.brandapp.device;

import android.os.Parcel;

import brandapp.isport.com.basicres.commonbean.BaseBean;

/**
 * @Author
 * @Date 2019/1/18
 * @Fuction
 */

public class UpdateSuccessBean extends BaseBean {

    private String publicId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.publicId);
    }

    public UpdateSuccessBean() {
    }

    protected UpdateSuccessBean(Parcel in) {
        super(in);
        this.publicId = in.readString();
    }

    public static final Creator<UpdateSuccessBean> CREATOR = new Creator<UpdateSuccessBean>() {
        @Override
        public UpdateSuccessBean createFromParcel(Parcel source) {
            return new UpdateSuccessBean(source);
        }

        @Override
        public UpdateSuccessBean[] newArray(int size) {
            return new UpdateSuccessBean[size];
        }
    };

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    @Override
    public String toString() {
        return "UpdateSuccessBean{" +
                "publicId=" + publicId +
                '}';
    }
}
