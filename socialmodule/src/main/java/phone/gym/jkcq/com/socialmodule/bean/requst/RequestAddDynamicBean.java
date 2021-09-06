package phone.gym.jkcq.com.socialmodule.bean.requst;

public class RequestAddDynamicBean {
    /**
     * {
     * "accuseNums": 0,
     * "accuseTypes": "string",
     * "city": "string",
     * "commentNums": 0,
     * "content": "string",
     * "contentType": 0,
     * "countryCode": "string",
     * "coverUrl": "string",
     * "dynamicId": "string",
     * "isTop": 0,
     * "jumpUrl": "string",
     * "latitude": 0,
     * "longitude": 0,
     * "musicUrl": "string",
     * "praiseNums": 0,
     * "seq": 0,
     * "shareUrl": "string",
     * "status": 0,
     * "topicType": 0,
     * "userId": 0,
     * "videoUrl": "string"
     * }
     */

    String userId;
    String videoUrl;
    String content;
    String coverUrl;
    int contentType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
