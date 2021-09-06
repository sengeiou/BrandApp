package phone.gym.jkcq.com.socialmodule.bean;

public class UpdateFollowStateBean {
    String userId;
    int followStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "userId='" + userId + '\'' +
                ", followStatus=" + followStatus +
                '}';
    }
}
