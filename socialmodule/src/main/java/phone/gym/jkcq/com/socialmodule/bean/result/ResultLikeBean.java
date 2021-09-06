package phone.gym.jkcq.com.socialmodule.bean.result;


public class ResultLikeBean {
    /**
     * "praiseNums": 0,
     * "whetherPraise": false
     */
    private int praiseNums;
    private boolean whetherPraise;
    private String userId;
    private String dynamicInfoId;

    public int getPraiseNums() {
        return praiseNums;
    }

    public void setPraiseNums(int praiseNums) {
        this.praiseNums = praiseNums;
    }

    public boolean isWhetherPraise() {
        return whetherPraise;
    }

    public void setWhetherPraise(boolean whetherPraise) {
        this.whetherPraise = whetherPraise;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDynamicInfoId() {
        return dynamicInfoId;
    }

    public void setDynamicInfoId(String dynamicInfoId) {
        this.dynamicInfoId = dynamicInfoId;
    }

    @Override
    public String toString() {
        return "ResultLikeBean{" +
                "praiseNums=" + praiseNums +
                ", whetherPraise=" + whetherPraise +
                ", userId='" + userId + '\'' +
                '}';
    }
}
