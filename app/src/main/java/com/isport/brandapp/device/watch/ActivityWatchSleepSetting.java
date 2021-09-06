package com.isport.brandapp.device.watch;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_SleepAndNoDisturbModelAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SleepAndNoDisturbModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.WatchSleepDayData;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.watch.presenter.WatchPresenter;
import com.isport.brandapp.device.watch.view.WatchView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

/**
 * 睡眠设置
 */
public class ActivityWatchSleepSetting extends BaseMVPTitleActivity<WatchView, WatchPresenter> implements WatchView, View.OnClickListener, ItemView.OnItemViewCheckedChangeListener {
    private final static String TAG = ActivityWatchSleepSetting.class.getSimpleName();
    private ItemView sleepSettingChange, sleepSettingOpen, sleepSettingSleepTime, sleepSettingWakeupTime;
    private DeviceBean deviceBean;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_watch_sleep_setting;
    }

    @Override
    protected void initView(View view) {

        sleepSettingChange = view.findViewById(R.id.iv_watch_sleep_setting_change);

        sleepSettingOpen = view.findViewById(R.id.iv_watch_sleep_setting_open);
        sleepSettingSleepTime = view.findViewById(R.id.iv_watch_disturb_setting_sleep_time);
        sleepSettingWakeupTime = view.findViewById(R.id.iv_watch_disturb_setting_wakeup_time);
    }

    @Override
    protected void initData() {
        getIntentData();
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(context.getResources().getString(R.string.watch_sleep_setting_title));
        titleBarView.setRightText("");
        frameBodyLine.setVisibility(View.VISIBLE);
        if (deviceBean == null) {
            finish();
        }

        Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModelyDeviceId = Watch_W516_SleepAndNoDisturbModelAction.findWatch_W516_SleepAndNoDisturbModelyDeviceId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), deviceBean.deviceName);
        if (watch_w516_sleepAndNoDisturbModelyDeviceId == null) {
            //如果是没有设置过，全部设置为关闭
            Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModel = new Watch_W516_SleepAndNoDisturbModel();
            watch_w516_sleepAndNoDisturbModel.setDeviceId(deviceBean.deviceName);
            watch_w516_sleepAndNoDisturbModel.setUserId(TokenUtil.getInstance().getPeopleIdInt(this));
            watch_w516_sleepAndNoDisturbModel.setAutomaticSleep(true);
            watch_w516_sleepAndNoDisturbModel.setSleepRemind(false);
            // TODO: 2019/3/2 要查看是12小时还是24小时制
            watch_w516_sleepAndNoDisturbModel.setSleepStartTime("00:00");
            watch_w516_sleepAndNoDisturbModel.setSleepEndTime("23:59");
            watch_w516_sleepAndNoDisturbModel.setOpenNoDisturb(false);
            // TODO: 2019/3/2 要查看是12小时还是24小时制
            watch_w516_sleepAndNoDisturbModel.setNoDisturbStartTime("20:00");
            watch_w516_sleepAndNoDisturbModel.setNoDisturbEndTime("8:00");
            sleepSettingChange.setRadioChange(true);
            sleepSettingOpen.setChecked(false);
            sleepSettingSleepTime.setContentText("00:00");
            sleepSettingWakeupTime.setContentText("23:59");

            //默认是关闭状态
            sleepSettingOpen.setVisibility(View.GONE);
            sleepSettingSleepTime.setVisibility(View.GONE);
            sleepSettingWakeupTime.setVisibility(View.GONE);
            if (AppConfiguration.isConnected) {
                ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SLEEP_SETTING, true, false, false,
                        0, 0, 23, 59, 20, 0, 8, 0);
                BleAction.getWatch_W516_SleepAndNoDisturbModelDao().insertOrReplace(watch_w516_sleepAndNoDisturbModel);
            } else {
                ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
            }
        } else {
            sleepSettingChange.setRadioChange(!watch_w516_sleepAndNoDisturbModelyDeviceId.getAutomaticSleep());
            sleepSettingOpen.setChecked(watch_w516_sleepAndNoDisturbModelyDeviceId.getSleepRemind());
            sleepSettingSleepTime.setContentText(watch_w516_sleepAndNoDisturbModelyDeviceId.getSleepStartTime());
            sleepSettingWakeupTime.setContentText(watch_w516_sleepAndNoDisturbModelyDeviceId.getSleepEndTime());

            sleepSettingOpen.setVisibility(!watch_w516_sleepAndNoDisturbModelyDeviceId.getAutomaticSleep() ? View.VISIBLE : View.GONE);
            sleepSettingSleepTime.setVisibility(!watch_w516_sleepAndNoDisturbModelyDeviceId.getAutomaticSleep() ? View.VISIBLE : View.GONE);
            sleepSettingWakeupTime.setVisibility(!watch_w516_sleepAndNoDisturbModelyDeviceId.getAutomaticSleep() ? View.VISIBLE : View.GONE);
        }
    }

    private void getIntentData() {
        deviceBean = (DeviceBean) getIntent().getSerializableExtra("deviceBean");
    }


    @Override
    protected void initEvent() {
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {
            }
        });


        sleepSettingChange.setOnRadioCheckedChangeListener(new ItemView.OnItemViewRadioCheckedChangeListener() {
            @Override
            public void onRadioCheckedChanged(int id, boolean isChecked) {
                Log.e("onModeCheckedChanged", isChecked ? "主动" : "被动");
                sleepSettingOpen.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                sleepSettingSleepTime.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                sleepSettingWakeupTime.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                if (AppConfiguration.isConnected) {
                    //切到自动00:00-23:59   主动 默认是20:00-8:00
                    Watch_W516_SleepAndNoDisturbModel setting = Watch_W516_SleepAndNoDisturbModelAction.findWatch_W516_SleepAndNoDisturbModelyDeviceId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), deviceBean.deviceName);
                    String noDisturbStartTime = setting.getNoDisturbStartTime();
                    String[] split1 = noDisturbStartTime.split(":");
                    String noDisturbEndTime = setting.getNoDisturbEndTime();
                    String[] split2 = noDisturbEndTime.split(":");

                    String contentTextEnd = sleepSettingWakeupTime.getContentText();
                    String[] split3 = contentTextEnd.split(":");
                    //暂时以24小时制来判断,开始时间应该要小于结束时间
                    String contentTextStart = sleepSettingSleepTime.getContentText();
                    String[] split5 = contentTextStart.split(":");
                    int sleepStartHour;
                    int sleepStartMin;
                    int sleepEndHour;
                    int sleepEndMin;
                    if (isChecked) {
                        //打开，说明从关闭到打开
                        sleepSettingOpen.setChecked(false);
                        sleepSettingSleepTime.setContentText("20:00");
                        sleepSettingWakeupTime.setContentText("8:00");
                        sleepStartHour = 20;
                        sleepStartMin = 0;
                        sleepEndHour = 8;
                        sleepEndMin = 0;
                    } else {
                        //关闭，说明从打开到关闭
                        sleepSettingOpen.setChecked(false);
                        sleepSettingSleepTime.setContentText("00:00");
                        sleepSettingWakeupTime.setContentText("23:59");
                        sleepStartHour = 0;
                        sleepStartMin = 0;
                        sleepEndHour = 23;
                        sleepEndMin = 59;
                    }
                    sleepSettingOpen.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                    sleepSettingSleepTime.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                    sleepSettingWakeupTime.setVisibility(isChecked ? View.VISIBLE : View.GONE);

                    //暂时以24小时制来判断,开始时间应该要小于结束时间
                    ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SLEEP_SETTING, !isChecked, sleepSettingOpen.isChecked(), setting.getOpenNoDisturb(),
                            sleepStartHour, sleepStartMin, sleepEndHour, sleepEndMin, Integer.parseInt(split1[0]), Integer.parseInt(split1[1]), Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                    sleepSettingChange.setRadioChange(!isChecked);
                }
            }
        });


        sleepSettingOpen.setOnCheckedChangeListener(this);

        sleepSettingSleepTime.setOnContentClickListener(new ItemView.OnContentClickListener() {
            @Override
            public void onContentClick() {
                mActPresenter.setPopupWindow(context, sleepSettingSleepTime, "3", sleepSettingSleepTime.getContentText());
            }
        });
        sleepSettingWakeupTime.setOnContentClickListener(new ItemView.OnContentClickListener() {
            @Override
            public void onContentClick() {
                mActPresenter.setPopupWindow(context, sleepSettingWakeupTime, "3", sleepSettingWakeupTime.getContentText());
            }
        });
        ISportAgent.getInstance().registerListener(mBleReciveListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }


    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {
            if (isConn) {
            } else {
            }
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            if (mResult != null)
                switch (mResult.getType()) {
                    case IResult.WATCH_REALTIME:
                        break;

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
    protected void initHeader() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onCheckedChanged(int id, boolean isChecked) {
        switch (id) {
            case R.id.iv_watch_sleep_setting_open:
                Log.e("StableRemind", "开启" + isChecked);
                if (AppConfiguration.isConnected) {
                    Watch_W516_SleepAndNoDisturbModel setting = Watch_W516_SleepAndNoDisturbModelAction.findWatch_W516_SleepAndNoDisturbModelyDeviceId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), deviceBean.deviceName);
                    String noDisturbStartTime = setting.getNoDisturbStartTime();
                    String[] split1 = noDisturbStartTime.split(":");
                    String noDisturbEndTime = setting.getNoDisturbEndTime();
                    String[] split2 = noDisturbEndTime.split(":");

                    String contentTextEnd = sleepSettingWakeupTime.getContentText();
                    String[] split3 = contentTextEnd.split(":");
                    //暂时以24小时制来判断,开始时间应该要小于结束时间
                    String contentTextStart = sleepSettingSleepTime.getContentText();
                    String[] split5 = contentTextStart.split(":");
                    //暂时以24小时制来判断,开始时间应该要小于结束时间
                    ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SLEEP_SETTING, !sleepSettingChange.isRadioChange(), isChecked, setting.getOpenNoDisturb(),
                            Integer.parseInt(split5[0]), Integer.parseInt(split5[1]), Integer.parseInt(split3[0]), Integer.parseInt(split3[1]), Integer.parseInt(split1[0]), Integer.parseInt(split1[1]), Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                    sleepSettingOpen.setChecked(!isChecked);
                }
                break;
        }
    }


    @Override
    protected WatchPresenter createPresenter() {
        return new WatchPresenter(this);
    }

    @Override
    public void dataSetSuccess(View view, String select, String data) {
        if (view instanceof ItemView) {
            //settingTime.setContentText(data);
            if (AppConfiguration.isConnected) {
                //settingTime.setContentText(data);
                ((ItemView) view).setContentText(data);
                Watch_W516_SleepAndNoDisturbModel setting = Watch_W516_SleepAndNoDisturbModelAction.findWatch_W516_SleepAndNoDisturbModelyDeviceId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), deviceBean.deviceName);
                String[] split = data.split(":");
                String noDisturbStartTime = setting.getNoDisturbStartTime();
                String[] split1 = noDisturbStartTime.split(":");
                String noDisturbEndTime = setting.getNoDisturbEndTime();
                String[] split2 = noDisturbEndTime.split(":");
                switch (view.getId()) {
                    case R.id.iv_watch_disturb_setting_sleep_time:
                        String contentText = sleepSettingWakeupTime.getContentText();
                        String[] split3 = contentText.split(":");
                        //暂时以24小时制来判断,开始时间应该要小于结束时间
                        ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SLEEP_SETTING, !sleepSettingChange.isRadioChange(), sleepSettingOpen.isChecked(), setting.getOpenNoDisturb(),
                                Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split3[0]), Integer.parseInt(split3[1]), Integer.parseInt(split1[0]), Integer.parseInt(split1[1]), Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
                        break;
                    case R.id.iv_watch_disturb_setting_wakeup_time:
                        //暂时以24小时制来判断,结束时间应该要大于开始时间
                        String contentText1 = sleepSettingSleepTime.getContentText();
                        String[] split5 = contentText1.split(":");
                        //暂时以24小时制来判断,开始时间应该要小于结束时间
                        ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SLEEP_SETTING, !sleepSettingChange.isRadioChange(), sleepSettingOpen.isChecked(), setting.getOpenNoDisturb(),
                                Integer.parseInt(split5[0]), Integer.parseInt(split5[1]), Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split1[0]), Integer.parseInt(split1[1]), Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
                        break;
                }

            } else {
                ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
            }
        }
    }

    public static String formatTwoStr(int number) {
        String strNumber = String.format("%02d", number);
        return strNumber;
    }

    @Override
    public void onUnBindSuccess() {

    }

    @Override
    public void successDayDate(WatchSleepDayData watchSleepDayData) {

    }

    @Override
    public void updateWatchHistoryDataSuccess(DeviceBean deviceBean) {

    }

    @Override
    public void updateFail() {

    }

    @Override
    public void seccessGetDeviceSedentaryReminder(Watch_W516_SedentaryModel watch_w516_sedentaryModel) {

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
