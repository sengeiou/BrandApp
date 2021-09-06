package phone.gym.jkcq.com.socialmodule.bean.requst;

public class RequestNextHomeBean {
    /**
     * {
     * "dataNums": 2,
     * "dynamicInfoType": 0,
     * "meUserId": 200
     * direction 7 上 8 下
     * dynamicInfoId 动态id
     * }
     */
    private int dataNums;
    private int dynamicInfoType;
    private String meUserId;
    private int direction;
    private String dynamicInfoId;

    @Override
    public String toString() {
        return "RequsetNextHomeBean{" +
                "dataNums=" + dataNums +
                ", dynamicInfoType=" + dynamicInfoType +
                ", meUserId='" + meUserId + '\'' +
                ", direction=" + direction +
                ", dynamicInfoId='" + dynamicInfoId + '\'' +
                '}';
    }

    public int getDataNums() {
        return dataNums;
    }

    public void setDataNums(int dataNums) {
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getDynamicInfoId() {
        return dynamicInfoId;
    }

    public void setDynamicInfoId(String dynamicInfoId) {
        this.dynamicInfoId = dynamicInfoId;
    }
}
