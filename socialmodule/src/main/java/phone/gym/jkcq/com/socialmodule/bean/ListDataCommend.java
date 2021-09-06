package phone.gym.jkcq.com.socialmodule.bean;

import java.util.List;

import phone.gym.jkcq.com.socialmodule.report.bean.ReportBean;

public class ListDataCommend {

    /**
     * pageNum : 1
     * pageSize : 10
     * total : 0
     * pages : 0
     * list : []
     * isFirstPage : true
     * isLastPage : true
     */

    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private boolean isFirstPage;
    private boolean isLastPage;
    private List<ReportBean> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public List<ReportBean> getList() {
        return list;
    }

    public void setList(List<ReportBean> list) {
        this.list = list;
    }
}
