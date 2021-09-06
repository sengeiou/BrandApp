package phone.gym.jkcq.com.socialmodule.report.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ReportBean implements Parcelable {
    /**
     * "dynamicCommentId": "1264840612580732929",
     * "dynamicId": "1264760002516353026",
     * "parentId": "0",
     * "rootId": "0",
     * "fromUserId": 563,
     * "content": "啊可口可乐了",
     * "toUserId": 317,
     * "praiseNums": 1,
     * "contentType": 0,
     * "status": 1,
     * "createTime": 1590396476000,
     * "deleteTime": null,
     * "managerId": null,
     * "managerName": null,
     * "fromNickName": "186****8928",
     * "toNickName": "定了",
     * "whetherPraise": false,
     * "fromHeadUrlTiny": "https://manager.fitalent.com.cn/static/2018/9/19/9-54-56-835426.png",
     * "toHeadUrlTiny": "https://manager.fitalent.com.cn/static/2020/4/14/16-54-30-4976477.jpg",
     * "authorType": 5,
     * "replyNums": 0
     */

    String dynamicCommentId;
    String dynamicId;
    String parentId;
    String rootId;
    String fromUserId;
    String content;
    String toUserId;
    int praiseNums;
    int contentType;
    int status;
    String fromNickName;
    String toNickName;
    boolean whetherPraise;
    String fromHeadUrlTiny;
    String toHeadUrlTiny;
    int authorType;
    int replyNums;
    long createTime;

    public String getDynamicCommentId() {
        return dynamicCommentId;
    }

    public void setDynamicCommentId(String dynamicCommentId) {
        this.dynamicCommentId = dynamicCommentId;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public int getPraiseNums() {
        return praiseNums;
    }

    public void setPraiseNums(int praiseNums) {
        this.praiseNums = praiseNums;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public boolean isWhetherPraise() {
        return whetherPraise;
    }

    public void setWhetherPraise(boolean whetherPraise) {
        this.whetherPraise = whetherPraise;
    }

    public String getFromHeadUrlTiny() {
        return fromHeadUrlTiny;
    }

    public void setFromHeadUrlTiny(String fromHeadUrlTiny) {
        this.fromHeadUrlTiny = fromHeadUrlTiny;
    }

    public String getToHeadUrlTiny() {
        return toHeadUrlTiny;
    }

    public void setToHeadUrlTiny(String toHeadUrlTiny) {
        this.toHeadUrlTiny = toHeadUrlTiny;
    }

    public int getAuthorType() {
        return authorType;
    }

    public void setAuthorType(int authorType) {
        this.authorType = authorType;
    }

    public int getReplyNums() {
        return replyNums;
    }

    public void setReplyNums(int replyNums) {
        this.replyNums = replyNums;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ReportBean{" +
                "dynamicCommentId='" + dynamicCommentId + '\'' +
                ", dynamicId='" + dynamicId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", rootId='" + rootId + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", content='" + content + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", praiseNums=" + praiseNums +
                ", contentType=" + contentType +
                ", status=" + status +
                ", fromNickName='" + fromNickName + '\'' +
                ", toNickName='" + toNickName + '\'' +
                ", whetherPraise=" + whetherPraise +
                ", fromHeadUrlTiny='" + fromHeadUrlTiny + '\'' +
                ", toHeadUrlTiny='" + toHeadUrlTiny + '\'' +
                ", authorType=" + authorType +
                ", replyNums=" + replyNums +
                ", createTime=" + createTime +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dynamicCommentId);
        dest.writeString(this.dynamicId);
        dest.writeString(this.parentId);
        dest.writeString(this.rootId);
        dest.writeString(this.fromUserId);
        dest.writeString(this.content);
        dest.writeString(this.toUserId);
        dest.writeInt(this.praiseNums);
        dest.writeInt(this.contentType);
        dest.writeInt(this.status);
        dest.writeString(this.fromNickName);
        dest.writeString(this.toNickName);
        dest.writeByte(this.whetherPraise ? (byte) 1 : (byte) 0);
        dest.writeString(this.fromHeadUrlTiny);
        dest.writeString(this.toHeadUrlTiny);
        dest.writeInt(this.authorType);
        dest.writeInt(this.replyNums);
        dest.writeLong(this.createTime);
    }

    public ReportBean() {
    }

    protected ReportBean(Parcel in) {
        this.dynamicCommentId = in.readString();
        this.dynamicId = in.readString();
        this.parentId = in.readString();
        this.rootId = in.readString();
        this.fromUserId = in.readString();
        this.content = in.readString();
        this.toUserId = in.readString();
        this.praiseNums = in.readInt();
        this.contentType = in.readInt();
        this.status = in.readInt();
        this.fromNickName = in.readString();
        this.toNickName = in.readString();
        this.whetherPraise = in.readByte() != 0;
        this.fromHeadUrlTiny = in.readString();
        this.toHeadUrlTiny = in.readString();
        this.authorType = in.readInt();
        this.replyNums = in.readInt();
        this.createTime = in.readLong();
    }

    public static final Parcelable.Creator<ReportBean> CREATOR = new Parcelable.Creator<ReportBean>() {
        @Override
        public ReportBean createFromParcel(Parcel source) {
            return new ReportBean(source);
        }

        @Override
        public ReportBean[] newArray(int size) {
            return new ReportBean[size];
        }
    };
}

