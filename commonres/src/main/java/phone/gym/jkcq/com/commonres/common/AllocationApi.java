package phone.gym.jkcq.com.commonres.common;


/**
 * @author ck
 * @version 创建时间：2016年10月11日 上午8:58:18 说明: 公共配置
 */
public class AllocationApi {

    //内网
    //public static final String BaseUrl = "http://192.168.10.15:8038";
    //外网
//    public static final String BaseUrl = "https://api.mini-banana.com";
    public static final String BaseUrl = "https://test.api.mini-banana.com";
    public static final String BaseScaleUrl = "http://192.168.10.203:8110";
    public static final String EXTRA_WEBVIEW_LOAD_URL = "extra_webview_load_url";
    public static final String EXTRA_NOTICATION_BUNDLE = "extra_notication_bundle";
    public static final String EXTRA_NOTICATION_DATA = "extra_notication_data";
    public static final String EXTRA_FRIEND_USERID = "extra_friend_userid";


    // 判断是否有网络
    public static boolean isNetwork = true;
    public static boolean isShowHint = false;

    // SharedPerferences的String类。
    public interface SpStringTag {
        // 是否提示需要开启高耗电模式的提示！
        String IS_SHOULD_SHOW_HIGH_POWER_TIPS = "is_should_show_high_power_tips";
        String USER_SETTING = "user_setting";

    }


    public static final int INTERFACEID = 0;

    /**
     * customer 用户数据
     *
     * @param mobile
     * @param type
     * @return
     */

    // Post /verify/{mobile}/{type} 用户提现校验获取校验码
    public static String getVerify(String mobile, int type) {
        return BaseUrl + "/verify/" + mobile + "/" + type;
    }


    //Post /customer/LoginByMobile  手机号码登陆 /customer/loginByMobile


    public static String postLoginMobileInfo() {
        return "loginByMobile";
        //  return BaseUrl + "/customer/loginByMobile";
    }

    //Post /customer/authorizedLanding/{platformType}  第三方授权登陆  platformType:平台 1.微信登陆 2.QQ登陆
    public static String postAuthorizedLanding() {
        return BaseUrl + "/customer/authorizedLanding";
    }

    //Post /customer/BindingMobile   手机号码绑定
    public static String postBindingMobile() {


        return BaseUrl + "/customer/BindingMobile";
    }

    //Post /customer/CustomerBasicInfo 通过userid查询用户信息
    public static String postCustomerRelationInfo() {
        //return BaseUrl + "/customer/CustomerBasicInfo";
        return "CustomerBasicInfo";
    }

    //Post /customer/editCustomerBasicInfo 编辑用户个人资料
    public static String postEditCustomerBasicInfo() {
        return "editCustomerBasicInfo";
    }


    //Post /customer/CustomerBasicInfo  查询用户个人资料
    public static String postCustomerBasicInfo() {
        return BaseUrl + "/customer/CustomerBasicInfo";
    }


    //Post /customer/CustomerBasicInfo/updateCustomerMobile  变更手机号码

    public static String postUpdateCustomerMobile() {
        return BaseUrl + "/customer/CustomerBasicInfo/updateCustomerMobile";
    }

    //POST /customers/{peopleId}/device/deviceData 上传设备数据
   /* public static String sendDeviceData() {
        return BaseUrl + "/customers/" + TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()) + "/device/deviceData";
    }*/

    /**
     * 上传图片
     *
     * @param userId
     * @return /customer/customerBasicInfo/editCustomerImmage/{userId}/{interfaceId}
     */
    public static String postUserPhoto(String userId) {
        return BaseUrl + "/customer/customerBasicInfo/editCustomerImmage/" + userId + "/" + INTERFACEID;
    }

    /**
     * 获取体脂称的历史列表接口
     * POST /fatsteelyard/historyData
     */
    public static String postScaleHistoryData() {
        return BaseUrl + "/fatsteelyard/historyData";
    }

    /**
     * 删除图片
     *
     * @return
     */
    public static String postDeletePhoto() {
        return BaseUrl + "/customer/CustomerBasicInfo/deleteCustomerImmage";
    }

    public static String getPhoto() {
        return BaseUrl + "/customer/customerHeadAuthentication";

    }

    /**
     * 预约
     *
     * @return
     */

    //POST   /club/mySubscribe  查询我的预约信息
    public static String postMySubscribe() {
        return BaseUrl + "/club/mySubscribe";
    }

    //  POST /club/clubInfo/coordinate    查询附近的会所
    public static String postCoordinate() {
        return BaseUrl + "/club/clubInfo/coordinate";
    }

    //  POST /club/subscribe/cancel    取消预约
    public static String postCancel() {

        return BaseUrl + "/club/subscribe/cancel";
    }

    //  POST /club/SubscribeSchedule/{clubId}/{subscribeDate} 根据会所编号查询可预约信息
    public static String postSubscribeSchedule(String clubId, String subscribeDate) {

        return BaseUrl + "/club/SubscribeSchedule/" + clubId + "/" + subscribeDate;
    }

    //POST /club/clubInfo/{clubId} 1.3查询场馆详情（可预约信息）
    public static String postClubInfo(String clubId) {
        return BaseUrl + "/club/clubInfo/" + clubId;
    }

    //POST /club/sportStatus 用户是否正在运动
    public static String postSportStatus() {

        return BaseUrl + "/club/sportStatus";

    }

    //POST /club/subscribe   预约场馆
    public static String postSubscribe() {

        return BaseUrl + "/club/subscribe";
    }
    //POST /club/ScanQrcode 二维码扫描

    public static String postScanQrcode() {

        return BaseUrl + "/club/ScanQrcode";
    }


    //POST /club/goSportNow 马上运动
    public static String goSportNow() {
        return BaseUrl + "/club/goSportNow";
    }

    /**
     * Message模块接口
     */

    public static String postMessages() {
        return BaseUrl + "/message/message";
    }

    public static String postIsHasUnReadMessage() {
        return BaseUrl + "/message/message/isHasUnreadMessage";
    }

    public static String postDeleteMessage(String MessageId) {
        return BaseUrl + "/message/message/deleteMessage";
    }

    public static String postDeleteAllMessage() {
        return BaseUrl + "/message/message/deleteAllMessage";
    }

    public static String postReadMessage(String messageId) {
        return BaseUrl + "/message/message/read";
    }


    /**
     * 蓝牙模块接口
     */
    //POST /myPersonalDevice/myPersonalDeviceList 我的设备列表查询
    public static String postMyPersonalDeviceList() {
        return BaseUrl + "/myPersonalDevice/myPersonalDeviceList";
    }


    /**
     * 广告
     */
    // GET  /advertisement/frontPageAdvertisements
    public static String getFrontPageAdvertisements() {
        return BaseUrl + "/advertisement/frontPageAdvertisements?clientType=APP&location=STANDBY";
    }

    /**
     * 系统配置接口
     */
    //POST /systems/appConfig/allFunctions  查询app所有功能
    public static String postAllFunctions() {
        return BaseUrl + "/systems/appConfig/allFunctions";
    }


    //POST /systems/appConfig/Myfunctions 显示在“我的”界面功能
    public static String postMyfunctions() {
        return BaseUrl + "/systems/appConfig/Myfunctions";
    }

    /**
     * 查询天气接口
     */

    //POST /weather/cityWeather/{cityName}  查询城市天气情况
    public static String postCityWeather(String cityName) {
        return BaseUrl + "/weather/cityWeather/" + cityName;
    }

    /**
     * 修改目标步数
     */

    //POST  post /customer/updateTargetSteps  修改目标步数
    public static String postUpdateTargetSteps() {
        return BaseUrl + "/customer/updateTargetSteps";
    }

    /**
     * 设备
     */

    //POST  post /fatsteelyard/synchronizeData/  上传体脂秤测量结果
    public static String postSynchronizeScaleData() {
        return BaseUrl + "/fatsteelyard/synchronizeData";
    }

    //POST 上传体脂称测试结果
    public static String postSynchronizeScaleDataN() {
        return BaseScaleUrl + "/fatsteelyard/fatsteelyardTarget/insertSelective";
    }

    //POST  post /wristbandstep/synchronizeData  上传手表历史数据
    public static String postSynchronizeWatchData() {
        return BaseUrl + "/wristbandstep/synchronizeData";
    }

    //POST  post /sleepbelt/synchronizeData  上传睡眠带的历史数据
    public static String postSynchronizeSleepData() {
        return BaseUrl + "/sleepbelt/synchronizeData";
    }

    //POST /wristbandstep/historyData  获取体脂称报告列表
    public static String postWristbandHistoryData() {
        return BaseUrl + "/wristbandstep/historyData";
    }

    //POST post /fatsteelyard/reportData/{fatSteelyardId}  获取手表历史记录
    public static String postReportData() {
        return BaseUrl + "/fatsteelyard/reportData/";
    }

    //POST post /sleepbelt/historyData  获取睡眠历史
    public static String postSleepHistoryData() {
        return BaseUrl + "/sleepbelt/historyData";
    }

    //POST  post /myPersonalDevice/myPersonalDeviceBinding 设备绑定
    public static String postMyPersonalDeviceBinding() {
        return BaseUrl + "/myPersonalDevice/myPersonalDeviceBinding";
    }

    //POST post /myPersonalDevice/delMyPersonalDevice 设备解绑
    public static String postDelMyPersonalDevice() {
        return BaseUrl + "/myPersonalDevice/delMyPersonalDevice";
    }

    //POST post /sleepbelt/findClockTime/{mac} 查询睡眠提醒时间
    public static String postFindClockTime() {
        return BaseUrl + "/sleepbelt/findClockTime/";
    }

    //POST post /sleepbelt/uploadClockTime  上传睡眠提醒时间
    public static String postUploadClockTime() {
        return BaseUrl + "/sleepbelt/uploadClockTime";
    }

    //POST /agreement/agreementUrl 获取用户协议URL
    public static String postAgreementUrl() {
        return BaseUrl + "/agreement/agreementUrl";
    }


    /**
     * post
     * myPersonalDevice/myPersonalDeviceList
     * 获取用户绑定的列表数据
     */

    public static String postMyPersonalDevice() {
        return BaseUrl + "/myPersonalDevice/myPersonalDeviceList";
    }

    /**
     * 获取主页数据
     * <p>
     * post /devicedatahome/findDeviceDataHome/{userId}
     *
     * @return
     */
    public static String postFindDeviceDataHome() {
        return BaseUrl + "/devicedatahome/findDeviceDataHome/";
    }


    /**
     * /**
     * ——————————String Tag———————————————
     */
//    public interface StringTag {
//        String ALARM_ACTION_TYPE = "ALARM_ACTION_TYPE";//闹钟操作类型
//        String INDEX_POSTION_BACK = "INDEX_POSTION_BACK";//修改闹钟下标返回
//        String INDEX_POSTION = "INDEX_POSTION";//修改闹钟下标
//        String SENDCLOCKFATHERBEAN_BACK = "SENDCLOCKFATHERBEAN_BACK";//添加闹钟界面返回给闹钟主页
//        String SENDCLOCKFATHERBEAN = "SENDCLOCKFATHERBEAN";//闹钟主页传给添加闹钟界面标记
//        String SENDCLOCKBEAN_BACK = "SENDCLOCKBEAN_BACK";//修改闹钟界面返回给主界面
//        String SENDCLOCKBEAN = "SENDCLOCKBEAN";//闹钟主页传给修改闹钟界面
//        String REPEAT_RESULT = "repeat_result";
//        String REPEAT_DATA = "repeat_data";
//    }
//
//
//    /**
//     * ——--————Integer Tag——————————
//     */
//    public interface IntegerTag {
//        int REPEAT_RESULT = 16;
//        int ADD_CLOCK = 43;//添加闹钟
//        int MODIFYCLOCK = 44;//修改闹钟
//        int ADD_CLOCK_BACK = 46;//添加闹钟返回
//        int MODIFY_BACK = 47;//修改闹钟返回
//    }

    public interface SendPhoneMessageType {

        String MESSAGE = "message";
        String QQ = "qq";
        String WECHAT = "wechat";
        String SKYPE = "skype";
        String FACEBOOK = "facebook";
        String TWITTER = "twitter";
        String LINKEDIN = "linkedin";
        String INSTAGRAM = "instagram";
        String LIFEINOVATYON = "lifeinovatyon";

    }
}
