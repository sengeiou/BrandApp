package phone.gym.jkcq.com.socialmodule.personal.bean;

public class UpdateUserBean {
    /**
     * {
     "birthday": "2020-04-22T06:54:08.309Z",
     "gender": "string",
     "height": 0,
     "interfaceId": 0,
     "mobile": "string",
     "myProfile": "string",
     "nickName": "string",
     "occupation": "string",
     "userId": 0,
     "weight": 0
     }
     */
    String birthday;
    String gender;
    String height;
    int interfaceId;
    String mobile;
    String myProfile;
    String nickName;
    String occupation;
    String userId;
    String weight;


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(int interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMyProfile() {
        return myProfile;
    }

    public void setMyProfile(String myProfile) {
        this.myProfile = myProfile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "UpdateUserBean{" +
                "birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", height=" + height +
                ", interfaceId=" + interfaceId +
                ", mobile='" + mobile + '\'' +
                ", myProfile='" + myProfile + '\'' +
                ", nickName='" + nickName + '\'' +
                ", occupation='" + occupation + '\'' +
                ", userId='" + userId + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
