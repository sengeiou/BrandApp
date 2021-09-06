package brandapp.isport.com.basicres.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author
 * @Date 2019/1/8
 * @Fuction
 */

@Entity
public class UserInformationBean {

    @Id
    private Long id;
    private String userId;
    private String nickName;
    private long creatTime;//创建时间
    private String gender;//	性别	string
    private int bodyHeight;//	身高	int	cm
    private float bodyWeight;//	体重	float	kg
    private String birthday;//出生日期	string
    private int age;//	年龄	int
    private int stepTarget;//	目标步数	int	步数	以人为准同步给设备
    private String stepTargetDate;//	设置目标步数日期	string		后期确认是否需要
    private int bodyWeightTarget;//	目标体重	int	kg
    private String bodyWeightTargetDate;//设置目标体重日期	string		后期确认是否需要
    private long sleepTarget;//	睡眠目标时长	long	分钟
    private String sleepTargetDate;//	设置目标体重日期	string
    private String headImage;//	用户头像	string
    private String headImage_s;//	用户头像缩略图	string
    private boolean useNetwork;//	是否是服务器用户	bool
//    private List bindingDeviceArray;	//用户绑定的设备群	array		包含mac，设备类型
    private String lastConnectDevice;//	最后一次连接的设备	string		包含mac，设备类型
    public String getLastConnectDevice() {
        return this.lastConnectDevice;
    }
    public void setLastConnectDevice(String lastConnectDevice) {
        this.lastConnectDevice = lastConnectDevice;
    }
    public boolean getUseNetwork() {
        return this.useNetwork;
    }
    public void setUseNetwork(boolean useNetwork) {
        this.useNetwork = useNetwork;
    }
    public String getHeadImage_s() {
        return this.headImage_s;
    }
    public void setHeadImage_s(String headImage_s) {
        this.headImage_s = headImage_s;
    }
    public String getHeadImage() {
        return this.headImage;
    }
    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
    public String getSleepTargetDate() {
        return this.sleepTargetDate;
    }
    public void setSleepTargetDate(String sleepTargetDate) {
        this.sleepTargetDate = sleepTargetDate;
    }
    public long getSleepTarget() {
        return this.sleepTarget;
    }
    public void setSleepTarget(long sleepTarget) {
        this.sleepTarget = sleepTarget;
    }
    public String getBodyWeightTargetDate() {
        return this.bodyWeightTargetDate;
    }
    public void setBodyWeightTargetDate(String bodyWeightTargetDate) {
        this.bodyWeightTargetDate = bodyWeightTargetDate;
    }
    public int getBodyWeightTarget() {
        return this.bodyWeightTarget;
    }
    public void setBodyWeightTarget(int bodyWeightTarget) {
        this.bodyWeightTarget = bodyWeightTarget;
    }
    public String getStepTargetDate() {
        return this.stepTargetDate;
    }
    public void setStepTargetDate(String stepTargetDate) {
        this.stepTargetDate = stepTargetDate;
    }
    public int getStepTarget() {
        return this.stepTarget;
    }
    public void setStepTarget(int stepTarget) {
        this.stepTarget = stepTarget;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public float getBodyWeight() {
        return this.bodyWeight;
    }
    public void setBodyWeight(float bodyWeight) {
        this.bodyWeight = bodyWeight;
    }
    public int getBodyHeight() {
        return this.bodyHeight;
    }
    public void setBodyHeight(int bodyHeight) {
        this.bodyHeight = bodyHeight;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public long getCreatTime() {
        return this.creatTime;
    }
    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2063106111)
    public UserInformationBean(Long id, String userId, String nickName,
            long creatTime, String gender, int bodyHeight, float bodyWeight,
            String birthday, int age, int stepTarget, String stepTargetDate,
            int bodyWeightTarget, String bodyWeightTargetDate, long sleepTarget,
            String sleepTargetDate, String headImage, String headImage_s,
            boolean useNetwork, String lastConnectDevice) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.creatTime = creatTime;
        this.gender = gender;
        this.bodyHeight = bodyHeight;
        this.bodyWeight = bodyWeight;
        this.birthday = birthday;
        this.age = age;
        this.stepTarget = stepTarget;
        this.stepTargetDate = stepTargetDate;
        this.bodyWeightTarget = bodyWeightTarget;
        this.bodyWeightTargetDate = bodyWeightTargetDate;
        this.sleepTarget = sleepTarget;
        this.sleepTargetDate = sleepTargetDate;
        this.headImage = headImage;
        this.headImage_s = headImage_s;
        this.useNetwork = useNetwork;
        this.lastConnectDevice = lastConnectDevice;
    }
    @Generated(hash = 92208477)
    public UserInformationBean() {
    }

}
