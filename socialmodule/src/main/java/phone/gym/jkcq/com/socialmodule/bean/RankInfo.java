package phone.gym.jkcq.com.socialmodule.bean;

public class RankInfo extends PraiseInfo {
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
     * 排号
     */
    private String index;
    /**
     * 步数
     */
    private int totalStep = 0;
    /**
     * 距离
     */
    private float totalDistance = 0F;
    /**
     * 卡路里
     */
    private float totalCalories = 0F;
    private int motionType;//0:室外跑,1:室内跑,2:骑行,3:行走,4步数,5卡路里,6跳绳个数,7跳绳时长
    private int timeType;//1:天,7:周,30月

    /**
     * totalNum
     *
     * @return
     */
    private int totalNum;

    private String rankingNo;


    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getRankingNo() {
        return rankingNo;
    }

    public void setRankingNo(String rankingNo) {
        this.rankingNo = rankingNo;
    }

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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getTotalStep() {
        return totalStep;
    }

    public void setTotalStep(int totalStep) {
        this.totalStep = totalStep;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public float getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(float totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getMotionType() {
        return motionType;
    }

    public void setMotionType(int motionType) {
        this.motionType = motionType;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }
}
