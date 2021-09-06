package com.isport.brandapp.message;

/**
 * Created by BeyondWorlds
 * on 2020/6/30
 */
public class MessageCount {

    /**
     * commentNums : 0
     * praiseNums : 0
     * fansNums : 0
     */

    private int commentNums;
    private int praiseNums;
    private int fansNums;

    public int getCommentNums() {
        return commentNums;
    }

    public void setCommentNums(int commentNums) {
        this.commentNums = commentNums;
    }

    public int getPraiseNums() {
        return praiseNums;
    }

    public void setPraiseNums(int praiseNums) {
        this.praiseNums = praiseNums;
    }

    public int getFansNums() {
        return fansNums;
    }

    public void setFansNums(int fansNums) {
        this.fansNums = fansNums;
    }

    @Override
    public String toString() {
        return "MessageCount{" +
                "commentNums=" + commentNums +
                ", praiseNums=" + praiseNums +
                ", fansNums=" + fansNums +
                '}';
    }
}
