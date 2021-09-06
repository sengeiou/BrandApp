package brandapp.isport.com.basicres.entry.bean;


import android.os.Parcel;

import brandapp.isport.com.basicres.commonbean.BaseBean;

public class UpdatePhotoBean extends BaseBean {

    public String headUrl;
    public String headUrlTiny;

    @Override
    public String toString() {
        return "UpdatePhotoBean{" +
                "headUrl='" + headUrl + '\'' +
                ", headUrlTiny='" + headUrlTiny + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.headUrl);
        dest.writeString(this.headUrlTiny);
    }

    public UpdatePhotoBean() {
    }

    protected UpdatePhotoBean(Parcel in) {
        super(in);
        this.headUrl = in.readString();
        this.headUrlTiny = in.readString();
    }

    public static final Creator<UpdatePhotoBean> CREATOR = new Creator<UpdatePhotoBean>() {
        @Override
        public UpdatePhotoBean createFromParcel(Parcel source) {
            return new UpdatePhotoBean(source);
        }

        @Override
        public UpdatePhotoBean[] newArray(int size) {
            return new UpdatePhotoBean[size];
        }
    };
}
