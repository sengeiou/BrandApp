package com.isport.brandapp;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.iflytek.cloud.SpeechUtility;
import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.SyncCacheUtils;
import com.isport.brandapp.blue.CallListener;
import com.isport.brandapp.blue.NotificationService;
import com.isport.brandapp.blue.SmsBroadcastReceiver;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.net.APIService;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.sport.bean.SportDetailData;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.sport.modle.SportDataModle;
import com.isport.brandapp.sport.response.SportRepository;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceTypeUtil;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.RxScheduler;
import brandapp.isport.com.basicres.commonutil.FileUtil;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.SystemUtils;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import iknow.android.utils.BaseUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class App extends BaseApp {
    private static long mScaleBindTime;
    private static long mSleepBindTime;
    private static long mWatchBindTime;
    private static long deviceBindTime;
    private static long mBraceletBindTime;

    // UMENG 各个平台的配置，建议放在全局Application或者程序入口M


    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }



    private void init(){

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);

        SyncCacheUtils.clearSetting(this);

        ISportAgent.getInstance().init(this);

        FileUtil.initFile(this);
        ARouter.init(this);

        //CrashHandler.getInstance().init(this);
        initAppState();
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
//        CrashReport.initCrashReport(getApplicationContext(), "a1832b72-84a7-480d-b26c-41f6ebfba73d", false);
        ISportAgent.getInstance().setIsWeight(false);

        if (NotificationService.isEnabled(this)) {
            NotificationService.ensureCollectorRunning(this);
        }

        // WbSdk.install(this,new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
        // AccessibilityUtil.checkSetting(this, NotifService.class);
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(new SmsBroadcastReceiver(), filter);

        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        CallListener customPhoneListener = new CallListener(this);
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        CrashReport.initCrashReport(getApplicationContext(), "93283676c4", false);

        char[] target = String.format("%04x", 10000001).toCharArray();
        Logger.LOGE("char ==", target.length + " target == " + target.toString());

        BaseUtils.init(this);
    }





    public static void initAppState() {
        AppConfiguration.isConnected = false;//当前是否已经连接
        AppConfiguration.isFirst = true;//当前是否已经连接
        AppConfiguration.isFirstRealTime = true;
        AppConfiguration.isSleepRealTime = false;
        AppConfiguration.isSleepBind = false;
        AppConfiguration.isWatchMain = false;
        AppConfiguration.isBindList = false;
        AppConfiguration.isScaleScan = false;
        AppConfiguration.isScaleRealTime = false;
        AppConfiguration.isScaleConnectting = false;
        AppConfiguration.hasSynced = false;
        JkConfiguration.AppType = BuildConfig.PRODUCT.equals("db") ? dbType : httpType;
    }

    public static boolean isUserGoogleMap() {
        return false;
    }

    /*
     * 初始化友盟。
     */

    public static boolean isHttp() {
        // BuildConfig.PRODUCT.equals("http")?1:0;
        return BuildConfig.PRODUCT.equals("http");
        //return BuildConfig.PRODUCT.equals("http");
    }

    public static int httpType = 0;
    public static int dbType = 1;


   /* public static boolean isDb(){
        return BuildConfig.PRODUCT.equals("http");
    }*/


    public static int appType() {
        //0是单机版，1是网络版
        return BuildConfig.PRODUCT.equals("db") ? dbType : httpType;

        // return BuildConfig.PRODUCT.equals("http") ? httpType : dbType;
    }

    public static boolean isPerforatedPanel() {
        boolean result;
        String deviceModel = SystemUtils.getDeviceModel();
        Logger.myLog("deviceModel == " + deviceModel);
        if (deviceModel.contains("SM-G8870") || deviceModel.contains("SM-G9730") || deviceModel.contains("SM-G9750") || deviceModel.contains("SM-G9700") || deviceModel.contains("VCE-AL00") || deviceModel.contains("PCT-AL10")) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        Logger.myLog("language=" + language);
        if (language.contains("zh"))
            return true;
        else
            return false;
    }

    public static void setScaleBindTime(long bindTime) {
        mScaleBindTime = bindTime;
    }

    public static long getScaleBindTime() {
        return mScaleBindTime;
    }

    public static void setSleepBindTime(long bindTime) {
        mSleepBindTime = bindTime;
    }

    public static long getSleepBindTime() {
        return mSleepBindTime;
    }

    public static void setWatchBindTime(long bindTime) {
        mWatchBindTime = bindTime;
    }

    public static void setDeviceBindTime(long bindTime) {
        deviceBindTime = bindTime;
    }

    public static long getDeviceBindTime() {
        return deviceBindTime;
    }

    public static void setBraceletBindTime(long bindTime) {
        mBraceletBindTime = bindTime;
    }

    public static long getBraceletBindTime() {
        return mBraceletBindTime;
    }

    public static long getWatchBindTime() {
        return mWatchBindTime;
    }

    public static void saveSportSummar(long id, SportSumData sumData) {
        Observable.create(new ObservableOnSubscribe<SportSumData>() {
            @Override
            public void subscribe(ObservableEmitter<SportSumData> emitter) throws Exception {
                // SportDataModle modle = new SportDataModle();
                //  SportSumData sumData = modle.getSummerData(id);
                emitter.onNext(sumData);
            }
        }).flatMap(new Function<SportSumData, ObservableSource<UpdateSuccessBean>>() {
            @Override
            public ObservableSource<UpdateSuccessBean> apply(SportSumData sumData) throws Exception {
                return SportRepository.addSportSummarrequst(sumData);
            }
        }).flatMap(new Function<UpdateSuccessBean, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(UpdateSuccessBean updateSuccessBean) throws Exception {
                //刷新主页的数据和运动模块的数据
                EventBus.getDefault().post(MessageEvent.SPORT_UPDATE_SUCESS);

                SportDataModle modle = new SportDataModle();
                SportDetailData detailData = modle.getSportDetailDataById(id, updateSuccessBean.getPublicId());
                return SportRepository.addSportDetail(detailData);
            }
        }).subscribe(new BaseObserver<String>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(String s) {
                NetProgressObservable.getInstance().hide();
                if (s.equals("操作成功！")) {
                    SportDataModle modle = new SportDataModle();
                    modle.delectSport(id);
                }
            }
        });
    }

    /**
     * 上传运动
     *
     * @param id
     * @param publicId
     */
    public static void saveSportDtail(long id, String publicId) {
        Observable.create(new ObservableOnSubscribe<SportDetailData>() {
            @Override
            public void subscribe(ObservableEmitter<SportDetailData> emitter) throws Exception {
                SportDataModle modle = new SportDataModle();
                SportDetailData detailData = modle.getSportDetailDataById(id, publicId);
                emitter.onNext(detailData);
            }
        }).flatMap(new Function<SportDetailData, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(SportDetailData sumData) throws Exception {
                //这里有可能会报错

                return SportRepository.addSportDetail(sumData);
            }
        }).subscribe(new BaseObserver<String>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(String s) {
                NetProgressObservable.getInstance().hide();
                if (s.equals("操作成功！")) {
                    SportDataModle modle = new SportDataModle();
                    modle.delectSport(id);
                }
            }
        });
    }


    public static boolean isScale() {
        int currentDeviceType = AppSP.getInt(App.getApp(), AppSP.DEVICE_CURRENTDEVICETYPE, JkConfiguration
                .DeviceType.WATCH_W516);
        boolean result = false;
        if (currentDeviceType == JkConfiguration.DeviceType.BODYFAT)
            result = true;
        return result;
    }

    public static boolean isWatch() {
        int currentDeviceType = AppSP.getInt(App.getApp(), AppSP.DEVICE_CURRENTDEVICETYPE, JkConfiguration
                .DeviceType.WATCH_W516);
        boolean result = false;
        if (DeviceTypeUtil.isContainWatch(currentDeviceType))
            result = true;
        return result;
    }

    public static boolean isBracelet() {
        int currentDeviceType = AppSP.getInt(App.getApp(), AppSP.DEVICE_CURRENTDEVICETYPE, JkConfiguration
                .DeviceType.WATCH_W516);
        boolean result = false;
        if (DeviceTypeUtil.isContainWrishBrand(currentDeviceType))
            result = true;
        return result;
    }

    public static boolean isSleepace() {
        int currentDeviceType = AppSP.getInt(App.getApp(), AppSP.DEVICE_CURRENTDEVICETYPE, JkConfiguration
                .DeviceType.WATCH_W516);
        boolean result = false;
        if (currentDeviceType == JkConfiguration.DeviceType.SLEEP)
            result = true;
        return result;
    }

    /**
     * 当前Acitity个数
     */
    private int activityAount = 0;
    private boolean isForeground = false;
    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (activityAount == 0) {
                //app回到前台
                isForeground = true;
                checkTime();
                //去调用接口

            }
            activityAount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityAount--;
            if (activityAount == 0) {
                isForeground = false;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };

    private void checkTime() {

        new NetworkBoundResource<Long>() {
            @Override
            public io.reactivex.Observable<Long> getFromDb() {
                return null;
            }

            @Override
            public io.reactivex.Observable<Long> getNoCacheData() {
                return null;
            }

            @Override
            public boolean shouldFetchRemoteSource() {
                return false;
            }

            @Override
            public boolean shouldStandAlone() {
                return false;
            }

            @Override
            public io.reactivex.Observable<Long> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).getNowTime().compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);
            }

            @Override
            public void saveRemoteSource(Long bean) {

            }
        }.getAsObservable().subscribe(new BaseObserver<Long>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Long time) {

                // Logger.myLog("gettime=" + time + "System.currentTimeMillis()=" + System.currentTimeMillis() + "------" + Math.abs(time.longValue() - System.currentTimeMillis()));
                if (Math.abs(time.longValue() - System.currentTimeMillis()) > (60 * 1000)) {
                    ToastUtils.showToast(BaseApp.getApp(), UIUtils.getString(R.string.time_error));

                }

            }
        });
    }


}
