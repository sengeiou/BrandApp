package phone.gym.jkcq.com.socialmodule.bean.result;

import phone.gym.jkcq.com.socialmodule.bean.ListDataCommend;

public class ResultCommentBean<T> {
    int commentTotal;
    int dynamicCommentNums;
    int replyCommentNums;
    ListDataCommend pageData;

    public int getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(int commentTotal) {
        this.commentTotal = commentTotal;
    }

    public ListDataCommend getPageData() {
        return pageData;
    }

    public void setPageData(ListDataCommend pageData) {
        this.pageData = pageData;
    }

    public int getDynamicCommentNums() {
        return dynamicCommentNums;
    }

    public void setDynamicCommentNums(int dynamicCommentNums) {
        this.dynamicCommentNums = dynamicCommentNums;
    }

    public int getReplyCommentNums() {
        return replyCommentNums;
    }

    public void setReplyCommentNums(int replyCommentNums) {
        this.replyCommentNums = replyCommentNums;
    }
}
