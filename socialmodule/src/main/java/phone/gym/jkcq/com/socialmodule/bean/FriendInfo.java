package phone.gym.jkcq.com.socialmodule.bean;

public class FriendInfo {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 头像
     */
    private String headUrl;


    /**
     * 昵称
     */
    private String nickName;


    /**
     * 类型 包括：1：好友，2：粉丝，3：关注，4：黑名单(拉黑对方),0
     */
    private int type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FriendInfo{" +
                "userId='" + userId + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", type=" + type +
                '}';
    }
}
