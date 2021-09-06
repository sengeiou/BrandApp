package phone.gym.jkcq.com.socialmodule.bean;

public class PraiseInfo {
    /**
     * 点赞数
     */
    private int praiseNums = 0;
    /**
     * 是否点赞
     */
    private boolean whetherPraise;

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
}
