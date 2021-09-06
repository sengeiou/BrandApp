package phone.gym.jkcq.com.socialmodule.bean.requst;

public class RequsetDynamicPraise {
    /**
     *   {
     *   "dynamicInfoId": "1254680838566674433",
     *   "meUserId": 100,
     *   "toUserId": 100
     * }
     * }
     */
    private String dynamicInfoId;
    private String meUserId;
    private String toUserId;

    @Override
    public String toString() {
        return "RequsetDynamicPraise{" +
                "dynamicInfoId='" + dynamicInfoId + '\'' +
                ", meUserId='" + meUserId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                '}';
    }

    public String getDynamicInfoId() {
        return dynamicInfoId;
    }

    public void setDynamicInfoId(String dynamicInfoId) {
        this.dynamicInfoId = dynamicInfoId;
    }

    public String getMeUserId() {
        return meUserId;
    }

    public void setMeUserId(String meUserId) {
        this.meUserId = meUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}
