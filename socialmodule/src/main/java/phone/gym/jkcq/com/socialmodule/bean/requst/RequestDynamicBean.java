package phone.gym.jkcq.com.socialmodule.bean.requst;

public class RequestDynamicBean {
    /**
     * {
     * "page": 1,
     * "size": 10,
     * "userId": 100,
     * "videoType": 3
     * }
     */

    int page;
    int size;
    String userId;
    int videoType;

    @Override
    public String toString() {
        return "RequestDynamicBean{" +
                "page=" + page +
                ", size=" + size +
                ", userId='" + userId + '\'' +
                ", videoType=" + videoType +
                '}';
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }
}
