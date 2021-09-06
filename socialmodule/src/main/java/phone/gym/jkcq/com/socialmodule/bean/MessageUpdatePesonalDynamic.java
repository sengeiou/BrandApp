package phone.gym.jkcq.com.socialmodule.bean;

import java.util.List;

import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;

public class MessageUpdatePesonalDynamic {
    int videoType;
    int currentPage;
    boolean isLast;
    List<DynamBean> list;
    String userId;

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public List<DynamBean> getList() {
        return list;
    }

    public void setList(List<DynamBean> list) {
        this.list = list;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MessageUpdatePesonalDynamic{" +
                "videoType=" + videoType +
                ", currentPage=" + currentPage +
                ", isLast=" + isLast +
                ", list=" + list +
                ", userId='" + userId + '\'' +
                '}';
    }
}
