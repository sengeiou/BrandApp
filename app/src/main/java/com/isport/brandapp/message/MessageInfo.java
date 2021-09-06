package com.isport.brandapp.message;

/**
 * Created by BeyondWorlds
 * on 2020/6/30
 */
public class MessageInfo {

    /**
     * socialNewsId : 1277554624661659649
     * fromUserId : 121
     * toUserId : 170
     * dynamicCommentId : null
     * dynamicId : 1265532884238516225
     * rootId : null
     * parentId : null
     * status : 1
     * newsType : 2
     * contentType : 1
     * createTime : 1593427733000
     * jumpUrl : null
     * coverUrl : null
     * fromNickName : 大石头192
     * toNickName : 186****1608
     * authorNickName : null
     * authorHeadUrlTiny : null
     * replyNickName : null
     * replyHeadUrlTiny : null
     * fromHeadUrlTiny : https://manager.fitalent.com.cn/static/2020/4/19/10-55-16-1283852.jpg
     * toHeadUrlTiny : null
     * newsContent : null
     * replyContent : null
     * dynamicContent : null
     * followStatus : 1
     */

    private String socialNewsId;
    private String fromUserId;
    private String toUserId;
    private String dynamicCommentId;
    private String dynamicId;
    private String rootId;
    private String parentId;
    private int status;
    private int newsType;
    private int contentType;
    private long createTime;
    private String jumpUrl;
    private String coverUrl;
    private String fromNickName;
    private String toNickName;
    private String authorNickName;
    private String authorHeadUrlTiny;
    private String replyNickName;
    private String replyHeadUrlTiny;
    private String fromHeadUrlTiny;
    private String toHeadUrlTiny;
    private String newsContent;
    private String replyContent;
    private String dynamicContent;
    private int followStatus;

    public String getSocialNewsId() {
        return socialNewsId;
    }

    public void setSocialNewsId(String socialNewsId) {
        this.socialNewsId = socialNewsId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

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

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    public String getAuthorNickName() {
        return authorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        this.authorNickName = authorNickName;
    }

    public String getAuthorHeadUrlTiny() {
        return authorHeadUrlTiny;
    }

    public void setAuthorHeadUrlTiny(String authorHeadUrlTiny) {
        this.authorHeadUrlTiny = authorHeadUrlTiny;
    }

    public String getReplyNickName() {
        return replyNickName;
    }

    public void setReplyNickName(String replyNickName) {
        this.replyNickName = replyNickName;
    }

    public String getReplyHeadUrlTiny() {
        return replyHeadUrlTiny;
    }

    public void setReplyHeadUrlTiny(String replyHeadUrlTiny) {
        this.replyHeadUrlTiny = replyHeadUrlTiny;
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

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getDynamicContent() {
        return dynamicContent;
    }

    public void setDynamicContent(String dynamicContent) {
        this.dynamicContent = dynamicContent;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "socialNewsId='" + socialNewsId + '\'' +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", dynamicCommentId='" + dynamicCommentId + '\'' +
                ", dynamicId='" + dynamicId + '\'' +
                ", rootId='" + rootId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", status=" + status +
                ", newsType=" + newsType +
                ", contentType=" + contentType +
                ", createTime=" + createTime +
                ", jumpUrl='" + jumpUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", fromNickName='" + fromNickName + '\'' +
                ", toNickName='" + toNickName + '\'' +
                ", authorNickName='" + authorNickName + '\'' +
                ", authorHeadUrlTiny='" + authorHeadUrlTiny + '\'' +
                ", replyNickName='" + replyNickName + '\'' +
                ", replyHeadUrlTiny='" + replyHeadUrlTiny + '\'' +
                ", fromHeadUrlTiny='" + fromHeadUrlTiny + '\'' +
                ", toHeadUrlTiny='" + toHeadUrlTiny + '\'' +
                ", newsContent='" + newsContent + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", dynamicContent='" + dynamicContent + '\'' +
                ", followStatus=" + followStatus +
                '}';
    }
}
