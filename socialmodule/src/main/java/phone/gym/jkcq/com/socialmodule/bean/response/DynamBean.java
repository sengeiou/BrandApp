package phone.gym.jkcq.com.socialmodule.bean.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DynamBean implements Parcelable {
    /**
     * "dynamicId": "1254680976571858945",
     * "userId": 200,
     * "content": "你好呀12",
     * "contentType": 0,
     * "topicType": 0,
     * "videoUrl": "string",
     * "coverUrl": "string",
     * "shareUrl": "string",
     * "jumpUrl": "string",
     * "musicUrl": "string",
     * "status": 1,
     * "isTop": 0,
     * "seq": 100,
     * "commentNums": 0,
     * "accuseNums": 0,
     * "praiseNums": 0,
     * "countryCode": "string",
     * "city": "string",
     * "latitude": 0,
     * "longitude": 0,
     * "deleteTime": null,
     * "dynamicInfoPictureList": null,
     * "createTime": "2020-04-27 15:57:11",
     * "managerId": null,
     * "managerName": null,
     * "nickName": "186****4982",
     * "whetherPraise": false,
     * "whetherComment": false,
     * "headUrlTiny": "https://manager.fitalent.com.cn/static/2018/9/19/9-54-56-835426.png",
     * "accuseTypes": "string",
     * "followStatus": 0
     */

    private String dynamicId;
    private String userId;
    private String content;
    private int contentType;
    private int topicType;
    private String videoUrl;
    private String coverUrl;
    private String shareUrl;
    private String jumpUrl;
    private String musicUrl;
    private int status;
    private int isTop;
    private int seq;
    private int commentNums;
    private int accuseNums;
    private int praiseNums;
    private String countryCode;
    private String city;
    private double latitude;
    private double longitude;
    private long deleteTime;
    private ArrayList<String> dynamicInfoPictureList;
    private String createTime;
    private String managerId;
    private String managerName;
    private String nickName;
    private boolean whetherPraise;
    private boolean whetherComment;
    private String headUrlTiny;
    private String accuseTypes;
    private int followStatus;
    private Long createTimeApp;

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getCommentNums() {
        return commentNums;
    }

    public void setCommentNums(int commentNums) {
        this.commentNums = commentNums;
    }

    public int getAccuseNums() {
        return accuseNums;
    }

    public void setAccuseNums(int accuseNums) {
        this.accuseNums = accuseNums;
    }

    public int getPraiseNums() {
        return praiseNums;
    }

    public void setPraiseNums(int praiseNums) {
        this.praiseNums = praiseNums;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public ArrayList<String> getDynamicInfoPictureList() {
        return dynamicInfoPictureList;
    }

    public void setDynamicInfoPictureList(ArrayList<String> dynamicInfoPictureList) {
        this.dynamicInfoPictureList = dynamicInfoPictureList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isWhetherPraise() {
        return whetherPraise;
    }

    public void setWhetherPraise(boolean whetherPraise) {
        this.whetherPraise = whetherPraise;
    }

    public boolean isWhetherComment() {
        return whetherComment;
    }

    public void setWhetherComment(boolean whetherComment) {
        this.whetherComment = whetherComment;
    }

    public String getHeadUrlTiny() {
        return headUrlTiny;
    }

    public void setHeadUrlTiny(String headUrlTiny) {
        this.headUrlTiny = headUrlTiny;
    }

    public String getAccuseTypes() {
        return accuseTypes;
    }

    public void setAccuseTypes(String accuseTypes) {
        this.accuseTypes = accuseTypes;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    public Long getCreateTimeApp() {
        return createTimeApp;
    }

    public void setCreateTimeApp(Long createTimeApp) {
        this.createTimeApp = createTimeApp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dynamicId);
        dest.writeString(this.userId);
        dest.writeString(this.content);
        dest.writeInt(this.contentType);
        dest.writeInt(this.topicType);
        dest.writeString(this.videoUrl);
        dest.writeString(this.coverUrl);
        dest.writeString(this.shareUrl);
        dest.writeString(this.jumpUrl);
        dest.writeString(this.musicUrl);
        dest.writeInt(this.status);
        dest.writeInt(this.isTop);
        dest.writeInt(this.seq);
        dest.writeInt(this.commentNums);
        dest.writeInt(this.accuseNums);
        dest.writeInt(this.praiseNums);
        dest.writeString(this.countryCode);
        dest.writeString(this.city);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeLong(this.deleteTime);
        dest.writeStringList(this.dynamicInfoPictureList);
        dest.writeString(this.createTime);
        dest.writeString(this.managerId);
        dest.writeString(this.managerName);
        dest.writeString(this.nickName);
        dest.writeByte(this.whetherPraise ? (byte) 1 : (byte) 0);
        dest.writeByte(this.whetherComment ? (byte) 1 : (byte) 0);
        dest.writeString(this.headUrlTiny);
        dest.writeString(this.accuseTypes);
        dest.writeInt(this.followStatus);
        dest.writeValue(this.createTimeApp);
    }

    public DynamBean() {
    }

    protected DynamBean(Parcel in) {
        this.dynamicId = in.readString();
        this.userId = in.readString();
        this.content = in.readString();
        this.contentType = in.readInt();
        this.topicType = in.readInt();
        this.videoUrl = in.readString();
        this.coverUrl = in.readString();
        this.shareUrl = in.readString();
        this.jumpUrl = in.readString();
        this.musicUrl = in.readString();
        this.status = in.readInt();
        this.isTop = in.readInt();
        this.seq = in.readInt();
        this.commentNums = in.readInt();
        this.accuseNums = in.readInt();
        this.praiseNums = in.readInt();
        this.countryCode = in.readString();
        this.city = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.deleteTime = in.readLong();
        this.dynamicInfoPictureList = in.createStringArrayList();
        this.createTime = in.readString();
        this.managerId = in.readString();
        this.managerName = in.readString();
        this.nickName = in.readString();
        this.whetherPraise = in.readByte() != 0;
        this.whetherComment = in.readByte() != 0;
        this.headUrlTiny = in.readString();
        this.accuseTypes = in.readString();
        this.followStatus = in.readInt();
        this.createTimeApp = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<DynamBean> CREATOR = new Creator<DynamBean>() {
        @Override
        public DynamBean createFromParcel(Parcel source) {
            return new DynamBean(source);
        }

        @Override
        public DynamBean[] newArray(int size) {
            return new DynamBean[size];
        }
    };

    @Override
    public String toString() {
        return "DynamBean{" +
                "dynamicId='" + dynamicId + '\'' +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", contentType=" + contentType +
                ", topicType=" + topicType +
                ", videoUrl='" + videoUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", status=" + status +
                ", isTop=" + isTop +
                ", seq=" + seq +
                ", commentNums=" + commentNums +
                ", accuseNums=" + accuseNums +
                ", praiseNums=" + praiseNums +
                ", countryCode='" + countryCode + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", deleteTime=" + deleteTime +
                ", dynamicInfoPictureList=" + dynamicInfoPictureList +
                ", createTime='" + createTime + '\'' +
                ", managerId='" + managerId + '\'' +
                ", managerName='" + managerName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", whetherPraise=" + whetherPraise +
                ", whetherComment=" + whetherComment +
                ", headUrlTiny='" + headUrlTiny + '\'' +
                ", accuseTypes='" + accuseTypes + '\'' +
                ", followStatus=" + followStatus +
                ", createTimeApp=" + createTimeApp +
                '}';
    }
}
