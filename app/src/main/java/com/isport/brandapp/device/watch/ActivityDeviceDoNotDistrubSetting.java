package com.isport.brandapp.device.watch;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SleepAndNoDisturbModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.ThreadPoolUtils;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.WatchSleepDayData;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.bracelet.braceletPresenter.NoDisturbPresenter;
import com.isport.brandapp.device.bracelet.view.NoDisturbView;
import com.isport.brandapp.device.watch.presenter.WatchPresenter;
import com.isport.brandapp.device.watch.view.WatchView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceDataUtil;
import com.isport.brandapp.util.DeviceTypeUtil;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

/**
 * 勿扰设置
 */
public class ActivityDeviceDoNotDistrubSetting extends BaseMVPTitleActivity<WatchView, WatchPresenter> implements WatchView, View.OnClickListener, ItemView.OnItemViewCheckedChangeListener, NoDisturbView {
    private final static String TAG = ActivityDeviceDoNotDistrubSetting.class.getSimpleName();
    private ItemView settingOpen, settingStart, settingEnd;
    private DeviceBean deviceBean;
    private String deviceName, userId;
    private NoDisturbPresenter noDisturbPresenter;

    int currentType;

    String defStartTime = Constants.defStartTime;
    String defEndTime = Constants.defEndTime;
    int defstarHour = Constants.defStarHour, defstarMin = Constants.defStartMin, defendHour = Constants.defEndHour, defendMin = Constants.defEndMin;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_watch_disturb_setting;
    }

    @Override
    protected void initView(View view) {
        layout = view.findViewById(R.id.layout);
        settingOpen = view.findViewById(R.id.iv_watch_disturb_setting_open);
        settingStart = view.findViewById(R.id.iv_watch_disturb_setting_start);
        settingEnd = view.findViewById(R.id.iv_watch_disturb_setting_end);
        frameBodyLine.setVisibility(View.VISIBLE);
    }


    @Override
    protected void initData() {
        getIntentData();
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(context.getResources().getString(R.string.watch_disturb_setting_title));
        titleBarView.setRightText("");
        if (deviceBean != null) {
            currentType = deviceBean.currentType;
            deviceName = deviceBean.deviceName;
        } else {
            currentType = JkConfiguration.DeviceType.WATCH_W516;
            deviceName = AppConfiguration.braceletID;
        }
        userId = TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp());

        noDisturbPresenter.getNodisturbBean(userId, deviceName);

    }


    public void setNext() {

        if (!DeviceDataUtil.comHour(this.startHour, this.startMin, this.endHour, this.endMin)) {
            settingEnd.showNext(UIUtils.getString(R.string.next_day));
        } else {
            settingEnd.hideNext();
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

        settingOpen.setOnCheckedChangeListener(this);

        settingStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActPresenter.setPopupWindow(context, settingStart, "3", settingStart.getContentText());
            }
        });
        settingEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActPresenter.setPopupWindow(context, settingEnd, "3", settingEnd.getContentText());
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

    int startHour, startMin, endHour, endMin;
    int sendStartHour, sendStartMin, sendEndHour, sendEndMin;

    @Override
    public void onCheckedChanged(int id, boolean isChecked) {
        switch (id) {
            case R.id.iv_watch_disturb_setting_open:
                Log.e("StableRemind", "开启" + isChecked);
                if (AppConfiguration.isConnected) {
                    showTimeItem(isChecked);
                    String contentTextEnd = settingEnd.getContentText();
                    String[] split3 = contentTextEnd.split(":");
                    //暂时以24小时制来判断,开始时间应该要小于结束时间
                    String contentTextStart = settingStart.getContentText();
                    String[] split5 = contentTextStart.split(":");
                    startHour = Integer.parseInt(split5[0]);
                    startMin = Integer.parseInt(split5[1]);
                    endHour = Integer.parseInt(split3[0]);
                    endMin = Integer.parseInt(split3[1]);
                    if (!isChecked) {
                        if (DeviceTypeUtil.isContainW81(currentType)) {
                            endHour = Constants.defStarHour;
                            endMin = Constants.defStartMin;
                            startHour = Constants.defEndHour;
                            endMin = Constants.defEndMin;
                            settingEnd.setContentText(Constants.defStartTime);
                            settingStart.setContentText(Constants.defEndTime);
                        }
                    }
                    //暂时以24小时制来判断,开始时间应该要小于结束时间
                    setNext();
                    sendCmd(contentTextStart, contentTextEnd);
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                    settingOpen.setChecked(!isChecked);
                    showTimeItem(!isChecked);
                }
                break;
        }
    }


    @Override
    protected WatchPresenter createPresenter() {
        noDisturbPresenter = new NoDisturbPresenter(this);
        return new WatchPresenter(this);
    }

    @Override
    public void dataSetSuccess(View view, String select, String data) {
        int tempHour = 0, tempMin = 0;
        if (view instanceof ItemView) {
            if (AppConfiguration.isConnected) {
                String[] splitSelectData = data.split(":");
                String[] splitStartTime;
                String[] splitEndTime;
                String contentText;
                switch (view.getId()) {
                    case R.id.iv_watch_disturb_setting_start:
                        contentText = settingEnd.getContentText();
                        splitEndTime = contentText.split(":");
                        tempHour = Integer.parseInt(splitSelectData[0]);
                        tempMin = Integer.parseInt(splitSelectData[1]);
                        endHour = Integer.parseInt(splitEndTime[0]);
                        endMin = Integer.parseInt(splitEndTime[1]);
                        //暂时以24小时制来判断,开始时间应该要小于结束时间
                        if (DeviceDataUtil.isSame(tempHour, tempMin, endHour, endMin)) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.time_setting_same));
                            return;
                        }
                        startHour = tempHour;
                        startMin = tempMin;
                        ((ItemView) view).setContentText(data);
                        sendCmd(data, contentText);
                        break;
                    case R.id.iv_watch_disturb_setting_end:
                        //暂时以24小时制来判断,结束时间应该要大于开始时间
                        contentText = settingStart.getContentText();
                        splitStartTime = contentText.split(":");
                        startHour = Integer.parseInt(splitStartTime[0]);
                        startMin = Integer.parseInt(splitStartTime[1]);
                        tempHour = Integer.parseInt(splitSelectData[0]);
                        tempMin = Integer.parseInt(splitSelectData[1]);
                        if (DeviceDataUtil.isSame(startHour, startMin, tempHour, tempMin)) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.time_setting_same));
                            return;
                        }
                        endHour = tempHour;
                        endMin = tempMin;
                        ((ItemView) view).setContentText(data);
                        sendCmd(contentText, data);
                        //暂时以24小时制来判断,开始时间应该要小于结束时间

                        break;
                }

            } else {
                ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
            }
        }
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

    public void saveDb(String deviceName, String userId, String startTime, String endTime, boolean enable) {
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModel = new Watch_W516_SleepAndNoDisturbModel();
                watch_w516_sleepAndNoDisturbModel.setDeviceId(deviceName);
                watch_w516_sleepAndNoDisturbModel.setUserId(userId);
                watch_w516_sleepAndNoDisturbModel.setOpenNoDisturb(enable);
                watch_w516_sleepAndNoDisturbModel.setNoDisturbStartTime(startTime);
                watch_w516_sleepAndNoDisturbModel.setNoDisturbEndTime(endTime);
                ParseData.saveOrUpdateWatchW516SleepAndNoDisturb(watch_w516_sleepAndNoDisturbModel);
            }
        });

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
    private LinearLayout layout;
    public void showTimeItem(boolean isShow) {
        if (!isShow) {
            // settingStart.setContentText(Constants.defEndHour);
            // settingEnd.setContentText(Constants.defStartTime);
        }
        if (isShow) {
            layout.setBackgroundResource(R.drawable.shape_btn_white_20_bg);
        } else {
            layout.setBackgroundResource(R.color.transparent);
        }
        settingStart.setVisibility(isShow ? View.VISIBLE : View.GONE);
        settingEnd.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void successDisturb(boolean isOpen) {

    }

    @Override
    public void successDisturb(Watch_W516_SleepAndNoDisturbModel model) {
        Logger.myLog("==" + model.toString());
        settingOpen.setChecked(model.getOpenNoDisturb());
        showTimeItem(model.getOpenNoDisturb());
        settingStart.setContentText(model.getNoDisturbStartTime());
        settingEnd.setContentText(model.getNoDisturbEndTime());
        String contentTextEnd = settingEnd.getContentText();
        String[] split3 = contentTextEnd.split(":");
        //暂时以24小时制来判断,开始时间应该要小于结束时间
        String contentTextStart = settingStart.getContentText();
        String[] split5 = contentTextStart.split(":");
        this.startHour = Integer.parseInt(split5[0]);
        this.startMin = Integer.parseInt(split5[1]);
        this.endHour = Integer.parseInt(split3[0]);
        this.endMin = Integer.parseInt(split3[1]);

        setNext();
    }


    public void sendCmd(String strStartTime, String strEndTime) {
        if (AppConfiguration.isConnected) {
            setNext();
            if (deviceBean.currentType == JkConfiguration.DeviceType.WATCH_W516) {
                //ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SLEEP_SETTING, setting.getAutomaticSleep(), setting.getSleepRemind(), settingOpen.isChecked(),
                //        Integer.parseInt(split1[0]), Integer.parseInt(split1[1]), Integer.parseInt(split2[0]), Integer.parseInt(split2[1]), Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split3[0]), Integer.parseInt(split3[1]));
            } else {
                saveDb(deviceName, userId, strStartTime, strEndTime, settingOpen.isChecked());
                ISportAgent.getInstance().requestBle(BleRequest.bracelet_send_disturb, settingOpen.isChecked(), startHour, startMin, endHour, endMin);

            }
        } else {
            ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
        }
    }
}
