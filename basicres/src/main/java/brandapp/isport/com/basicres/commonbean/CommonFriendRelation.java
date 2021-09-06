package brandapp.isport.com.basicres.commonbean;

import android.os.Parcel;
import android.os.Parcelable;

public class CommonFriendRelation implements Parcelable {
    /**
     * followStatus = 0,
     * friendNums = 1,
     * followNums = 2,
     * fansNums = 1,
     * trendsNums =0动态数据
     */

    private int followStatus;
    private int friendNums;
    private int followNums;
    private int trendsNums;
    private int fansNums;



    private int likeNums;

    @Override
    public String toString() {
        return "CommonFriendRelation{" +
                "followStatus=" + followStatus +
                ", friendNums=" + friendNums +
                ", followNums=" + followNums +
                ", trendsNums=" + trendsNums +
                ", fansNums=" + fansNums +
                ", likeNums=" + likeNums +
                '}';
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    public int getFriendNums() {
        return friendNums;
    }

    public void setFriendNums(int friendNums) {
        this.friendNums = friendNums;
    }

    public int getFollowNums() {
        return followNums;
    }

    public void setFollowNums(int followNums) {
        this.followNums = followNums;
    }

    public int getTrendsNums() {
        return trendsNums;
    }

    public void setTrendsNums(int trendsNums) {
        this.trendsNums = trendsNums;
    }

    public int getFansNums() {
        return fansNums;
    }

    public void setFansNums(int fansNums) {
        this.fansNums = fansNums;
    }

    public int getLikeNums() {
        return likeNums;
    }

    public void setLikeNums(int likeNums) {
        this.likeNums = likeNums;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.followStatus);
        dest.writeInt(this.friendNums);
        dest.writeInt(this.followNums);
        dest.writeInt(this.trendsNums);
        dest.writeInt(this.fansNums);
        dest.writeInt(this.likeNums);
    }

    public CommonFriendRelation() {
    }

    protected CommonFriendRelation(Parcel in) {
        this.followStatus = in.readInt();
        this.friendNums = in.readInt();
        this.followNums = in.readInt();
        this.trendsNums = in.readInt();
        this.fansNums = in.readInt();
        this.likeNums=in.readInt();
    }

    public static final Creator<CommonFriendRelation> CREATOR = new Creator<CommonFriendRelation>() {
        @Override
        public CommonFriendRelation createFromParcel(Parcel source) {
            return new CommonFriendRelation(source);
        }

        @Override
        public CommonFriendRelation[] newArray(int size) {
            return new CommonFriendRelation[size];
        }
    };
}
