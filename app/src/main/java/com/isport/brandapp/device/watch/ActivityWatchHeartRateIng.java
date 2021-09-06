package com.isport.brandapp.device.watch;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_SettingModelAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SettingModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.watch.WatchHrHeartResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.DateUtil;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.WristbandHrHeart;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.sleep.TimeUtil;
import com.isport.brandapp.device.watch.presenter.WatchHeartRatePresenter;
import com.isport.brandapp.device.watch.view.WatchHeartRateView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceTypeUtil;
import com.isport.brandapp.util.UserAcacheUtil;
import com.isport.brandapp.wu.util.HeartRateConvertUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import bike.gymproject.viewlibray.HeartRateIngView;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import phone.gym.jkcq.com.commonres.commonutil.UserUtils;

/**
 * @Author 李超凡
 * @Date 2019/2/26
 * @Fuction 手动测量心率
 */

public class ActivityWatchHeartRateIng extends BaseMVPTitleActivity<WatchHeartRateView, WatchHeartRatePresenter> implements
        WatchHeartRateView, View.OnClickListener {
    TextView tvCloseDetect, tvRateTime, tvRateBpm;
    HeartRateIngView heartRateIngView;
    private TextView tvEnd;
    private ArrayList<Integer> list;
    private ArrayList<String> times;
    private DeviceBean deviceBean;
    private String deviceName;
    private UserInfoBean userInfoBean;
    private int age;
    private String sex;
    private int currentHearHr;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_watch_heart_rate_ing;
    }


    @Override
    protected WatchHeartRatePresenter createPresenter() {
        return new WatchHeartRatePresenter(this);
    }

    @Override
    protected void initView(View view) {
        tvRateTime = view.findViewById(R.id.tv_watch_heart_rate_time);
        tvRateBpm = view.findViewById(R.id.tv_watch_heart_rate_bpm);
        tvCloseDetect = view.findViewById(R.id.tv_close_detect);
        heartRateIngView = view.findViewById(R.id.heartRateIngView);
    }

    private void getIntentData() {
        deviceName = getIntent().getStringExtra(JkConfiguration.DEVICE);
    }


    private void startTime() {
        if (disposableTimer != null && !disposableTimer.isDisposed()) {
            return;
            //  disposableTimer.dispose();
        }

        disposableTimer = Observable.interval(0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                tvRateTime.setText(UIUtils.getString(R.string.real_hr_time) + TimeUtil.getTimerFormatedStrings(startTime, startTime + (aLong - pauseTime) * 1000));

                if (currentHearHr == -1) {
                    return;
                }

                times.add(DateUtil.dataToString(new Date(), "HH:mm:ss"));
                if (currentHearHr < 30) {
                    currentHearHr = 0;
                    list.add(0);
                } else {
                    list.add(currentHearHr);
                }
                if (list.size() <= 2) {
                    heartRateIngView.invalidate();
                    return;
                }
                setHrValue(tvRateBpm, currentHearHr);

                currentHearHr = -1;
                heartRateIngView.setData(list);
                heartRateIngView.setTimesData(times);
                heartRateIngView.setMaxScale(list.size());
                heartRateIngView.setFirstScale(list.size() - 2);
            }
        });
    }

    private void setHrValue(TextView tvValue, int value) {
        if (value < 30) {
            tvValue.setText(UIUtils.getString(R.string.no_data));
        } else {
            tvValue.setText(value + "");
        }
        HeartRateConvertUtils.hrValueColor(value, HeartRateConvertUtils.getMaxHeartRate(age, sex), tvValue);
    }

    @Override
    protected void initData() {
        userInfoBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));

        getIntentData();
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(getResources().getString(R.string.watch_heart_rate_detecting));
        titleBarView.setRightText("");

        startTime = System.currentTimeMillis();
        tvRateTime.setText(TimeUtil.getTimerFormatedStrings(startTime, startTime));

        setHeartRatingData();

        if (!TextUtils.isEmpty(deviceName)) {

            Watch_W516_SettingModel w516SettingModelByDeviceId = Watch_W516_SettingModelAction.findW516SettingModelByDeviceId(deviceName, TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));

//            if (!w516SettingModelByDeviceId.getHeartRateSwitch()) {
//                if (AppConfiguration.isConnected) {
//                    if (AppConfiguration.hasSynced) {
//                        //需要发指令去打开心率监测,正在重连同步中,是不会打开心率24小时的
//                        ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_GENERAL, true);
//                    }
//                }
//            }
        }

        if (userInfoBean != null) {
            String birthday = userInfoBean.getBirthday();
            age = UserUtils.getAge(birthday);
            sex = userInfoBean.getGender();
        }

    }


    private void setHeartRatingData() {
        list = new ArrayList<>();
        times = new ArrayList<>();
//        list.add(78);

        heartRateIngView.setMaxScale(list.size());
        final Random random = new Random();

        heartRateIngView.setOnChooseResulterListener(new HeartRateIngView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {
//                tvRateBpm.setText(result);
            }

            @Override
            public void onScrollResult(String result) {
//                tvRateBpm.setText(result);
            }
        });


    }

    Disposable disposableTimer;
    long startTime, pauseTime;

    @Override
    protected void initEvent() {
        ISportAgent.getInstance().registerListener(mBleReciveListener);
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {

                finnishBack();
            }

            @Override
            public void onRightClicked(View view) {
            }
        });
        tvCloseDetect.setOnClickListener(this);
    }

    @Override
    protected void initHeader() {

    }

    Handler handler = new Handler();

    public void finnishBack() {
        BaseDevice baseDevice = ISportAgent.getInstance().getCurrnetDevice();

        //需要去同步数据
        if (baseDevice != null && (DeviceTypeUtil.isContainWrishBrand(baseDevice.deviceType) || DeviceTypeUtil.isContainW55X(baseDevice.deviceType))) {
            if (AppConfiguration.isConnected) {
                ISportAgent.getInstance().requestBle(BleRequest.bracelet_w311_set_automatic_HeartRate_And_Time, false);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.endHr));
            }
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.syncTodayData));
                finish();
            }
        }, 500);

    }


    @Override
    public void onBackPressed() {
        finnishBack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close_detect:
                finnishBack();
                break;
        }
    }

    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {
            if (isConn) {
            } else {
            }
          /*  if (mineItemHolder != null)
                mineItemHolder.notifyList(getDeviceList(baseDevice.deviceType, isConn));*/
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            if (mResult != null)
                switch (mResult.getType()) {
                    case IResult.WATCH_REALTIME_HR:
                        WatchHrHeartResult mResult2 = (WatchHrHeartResult) mResult;

                        // tvRateTime.setText(TimeUtil.getTimerFormatedStrings(startTime, startTime + (aLong - pauseTime) * 1000));
                        currentHearHr = mResult2.getHeartRate();
                        // currentHearHr=150;
                        if (times == null) {
                            times = new ArrayList<>();
                        }
                        if (currentHearHr >= 30 || times.size() > 0) {
                            startTime();
                        } else {
                            heartRateIngView.invalidate();
                        }


                    default:
                        break;
                }
        }

        @Override
        public void onConnecting(BaseDevice baseDevice) {

        }

        @Override
        public void onBattreyOrVersion(BaseDevice baseDevice) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposableTimer != null && !disposableTimer.isDisposed()) {
            disposableTimer.dispose();
        }
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }


    @Override
    public void successMonthStrDate(ArrayList<String> strDate) {

    }

    @Override
    public void successDayHrDate(WristbandHrHeart wristbandHrHeart) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.NEED_LOGIN:
                ToastUtils.showToast(context, UIUtils.getString(com.isport.brandapp.basicres.R.string.login_again));
                NetProgressObservable.getInstance().hide();
                BleProgressObservable.getInstance().hide();
                TokenUtil.getInstance().clear(context);
                UserAcacheUtil.clearAll();
                AppSP.putBoolean(context, AppSP.CAN_RECONNECT, false);
                App.initAppState();
                Intent intent = new Intent(context, ActivityLogin.class);
                context.startActivity(intent);
                ActivityManager.getInstance().finishAllActivity(ActivityLogin.class.getSimpleName());
                break;
            default:
                break;
        }
    }
}

