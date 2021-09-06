package phone.gym.jkcq.com.socialmodule.bean.requst;

public class RequestDynamicHomeBean {
    /**
     * {
     * "dataNums": 2,
     * "dynamicInfoType": 0,
     * "meUserId": 200
     * }
     */
    private int dataNums;
    private int dynamicInfoType;
    private String meUserId;

    @Override
    public String toString() {
        return "RequsetDynamicHomeBean{" +
                "dataNumbs=" + dataNums +
                ", dynamicInfoType=" + dynamicInfoType +
                ", meUserId='" + meUserId + '\'' +
                '}';
    }

    public int getDataNumbs() {
        return dataNums;
    }

    public void setDataNumbs(int dataNums) {
        this.dataNums = dataNums;
    }

    public int getDynamicInfoType() {
        return dynamicInfoType;
    }

    public void setDynamicInfoType(int dynamicInfoType) {
        this.dynamicInfoType = dynamicInfoType;
    }

    public String getMeUserId() {
        return meUserId;
    }

    public void setMeUserId(String meUserId) {
        this.meUserId = meUserId;
    }
}
