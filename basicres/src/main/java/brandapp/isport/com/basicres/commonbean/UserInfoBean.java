package brandapp.isport.com.basicres.commonbean;

import android.os.Parcel;


/**
 * Created by Administrator on 2017/10/16.
 */
public class UserInfoBean extends BaseBean {

    /**
     * "userId": 177,
     * "mobile": "15675870845",
     * "nickName": "156****0845",
     * "gender": "Male",
     * "height": 170,
     * "weight": 60,
     * "birthday": "2018-10-18",
     * "headUrl": "https://manager.fitalent.com.cn//resources/people//isportdefault.png",
     * "headUrlTiny": "https://manager.fitalent.com.cn//resources/people//isportdefault.png",
     * "isRegidit": true,
     * "targetSteps": 10000
     * followStatus = 0,
     * friendNums = 1,
     * followNums = 2,
     * fansNums = 1,
     */

    private String userId;
    private boolean isRegidit;
    private String nickName;
    private String gender;
    private String height;
    private String weight;
    private String birthday;
    private String headUrl;
    private String headUrlTiny;
    private String targetSteps;
    private String mobile;
    private String backgroundUrl;
    private String myProfile;
    private String qrString;

    private boolean enableRanking;
    private boolean enableRopeRanking;

    public boolean isEnableRanking() {
        return enableRanking;
    }

    public boolean isEnableRopeRanking() {
        return enableRopeRanking;
    }

    public void setEnableRopeRanking(boolean enableRopeRanking) {
        this.enableRopeRanking = enableRopeRanking;
    }

    public void setEnableRanking(boolean enableRanking) {
        this.enableRanking = enableRanking;
    }

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    public boolean isRegidit() {
        return isRegidit;
    }

    public void setRegidit(boolean regidit) {
        isRegidit = regidit;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getHeadUrlTiny() {
        return headUrlTiny;
    }

    public void setHeadUrlTiny(String headUrlTiny) {
        this.headUrlTiny = headUrlTiny;
    }

    public String getTargetSteps() {
        return targetSteps;
    }

    public void setTargetSteps(String targetSteps) {
        this.targetSteps = targetSteps;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isIsRegidit() {
        return isRegidit;
    }

    public void setIsRegidit(boolean isRegidit) {
        this.isRegidit = isRegidit;
    }

    public String showMes() {
        return "userId" + userId + "isRegidit" + isRegidit;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getMyProfile() {
        return myProfile;
    }

    public void setMyProfile(String myProfile) {
        this.myProfile = myProfile;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "userId='" + userId + '\'' +
                ", isRegidit=" + isRegidit +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", birthday='" + birthday + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", headUrlTiny='" + headUrlTiny + '\'' +
                ", targetSteps='" + targetSteps + '\'' +
                ", mobile='" + mobile + '\'' +
                ", backgroundUrl='" + backgroundUrl + '\'' +
                ", myProfile='" + myProfile + '\'' +
                ", qrString='" + qrString + '\'' +
                ", enableRanking=" + enableRanking +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.userId);
        dest.writeByte(this.isRegidit ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enableRanking ? (byte) 1 : (byte) 0);
        dest.writeString(this.nickName);
        dest.writeString(this.gender);
        dest.writeString(this.height);
        dest.writeString(this.weight);
        dest.writeString(this.birthday);
        dest.writeString(this.headUrl);
        dest.writeString(this.headUrlTiny);
        dest.writeString(this.targetSteps);
        dest.writeString(this.mobile);
        dest.writeString(this.backgroundUrl);
        dest.writeString(this.myProfile);
        dest.writeString(this.qrString);
    }

    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        super(in);
        this.userId = in.readString();
        this.isRegidit = in.readByte() != 0;
        this.enableRanking = in.readByte() != 0;
        this.nickName = in.readString();
        this.gender = in.readString();
        this.height = in.readString();
        this.weight = in.readString();
        this.birthday = in.readString();
        this.headUrl = in.readString();
        this.headUrlTiny = in.readString();
        this.targetSteps = in.readString();
        this.mobile = in.readString();
        this.backgroundUrl = in.readString();
        this.myProfile = in.readString();
        this.qrString = in.readString();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}