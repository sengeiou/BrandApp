package com.isport.brandapp.bind;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.action.DeviceInformationTableAction;
import com.isport.blelibrary.db.table.DeviceInformationTable;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.sleep.SleepHistoryDataResult;
import com.isport.blelibrary.result.impl.sleep.SleepHistoryDataResultList;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.Utils;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.bind.presenter.DeviceSettingPresenter;
import com.isport.brandapp.bind.view.DeviceSettingView;
import com.isport.brandapp.device.sleep.ActivityTimerSleep;
import com.isport.brandapp.dialog.UnBindDeviceDialog;
import com.isport.brandapp.dialog.UnbindStateCallBack;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import bike.gymproject.viewlibray.ItemView;
import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class ActivityDeviceSetting extends BaseMVPTitleActivity<DeviceSettingView,
        DeviceSettingPresenter> implements DeviceSettingView, View.OnClickListener {
    ItemView itemViewBattry;
    ItemView itemViewSleepRemind;
    ItemView itemViewDeviceId;
    ItemView itemFirmwareVersion;
    ItemView itemViewStepGoal;
    ItemView itemViewScreenUp;
    ItemView itemViewScreenTime;

    TextView tvUnBind;

    DeviceBean deviceBean;

    String mScreenTime;
    String mTarget;
    String mEnableHandScreen;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_device_setting;
    }

    @Override
    protected void initView(View view) {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        itemViewBattry = view.findViewById(R.id.itemview_battry);
        itemViewSleepRemind = view.findViewById(R.id.itemview_sleep_remind);
        itemViewDeviceId = view.findViewById(R.id.itemview_device_id);
        itemFirmwareVersion = view.findViewById(R.id.itemview_firmware_upgrade);
        itemViewStepGoal = view.findViewById(R.id.item_step_goal);
        itemViewScreenUp = view.findViewById(R.id.item_screen_up);
        itemViewScreenTime = view.findViewById(R.id.item_screen_time);
        tvUnBind = view.findViewById(R.id.btn_unbind);

        itemViewDeviceId.setEnabled(false);
        itemViewBattry.setEnabled(false);
        itemFirmwareVersion.setEnabled(false);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    itemViewSleepRemind.setContentText((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.UPDATE_CLOCKTIME_SUCCESS:
                mActPresenter.findClockTime(deviceBean.deviceName, deviceBean.deviceID);
                break;
            case MessageEvent.UNBIND_DEVICE_SUCCESS_EVENT:
                int deviceType = (int) messageEvent.getObj();
                if (deviceType == JkConfiguration.DeviceType.SLEEP) {
                    brandapp.isport.com.basicres.commonutil.Logger.e("睡眠带解绑成功");
                    TokenUtil.getInstance().saveSleepTime(BaseApp.getApp(), "");
                }
                EventBus.getDefault().post(new MessageEvent(MessageEvent.UNBIND_DEVICE_SUCCESS));
                finish();
                break;
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

    @Override
    protected void initData() {
        getIntentData();
        titleBarView.setLeftIconEnable(true);
        if (deviceBean == null) {
            return;
        }
        String deviceName = "SHJN";
        String strBattry = "30%";
        String strRemindTime = "22:00";
        String strDeviceId = "Z2-1651382764";
        String strFirmwareVersion = "V 1.0";
        DeviceInformationTable deviceInfoByDeviceId;
        switch (deviceBean.deviceType) {
            case JkConfiguration.DeviceType.BODYFAT:
                isShowItem(false);
                deviceName = String.format(getResources().getString(R.string.app_device_body_fat), deviceBean
                        .deviceName);
                strDeviceId = Utils.replaceDeviceMacUpperCase(deviceBean.mac);
                deviceInfoByDeviceId = DeviceInformationTableAction.findDeviceInfoByDeviceId
                        (Utils.resetDeviceMac(strDeviceId));
                if (deviceInfoByDeviceId != null) {
                    if (TextUtils.isEmpty(deviceInfoByDeviceId.getVersion())) {
                        strFirmwareVersion = UIUtils.getString(R.string.un_get);
                    } else {
                        strFirmwareVersion = String.format(getResources().getString(R.string.app_device_version),
                                deviceInfoByDeviceId == null ? "" : deviceInfoByDeviceId
                                        .getVersion() == null ? "" : deviceInfoByDeviceId
                                        .getVersion());
                    }
                } else {
                    strFirmwareVersion = UIUtils.getString(R.string.un_get);
                }
                break;
            case JkConfiguration.DeviceType.SLEEP:
                break;

            case JkConfiguration.DeviceType.WATCH_W516:

                break;
        }
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(deviceName);
        titleBarView.setRightText("");
        itemViewBattry.setContentText(strBattry);
        itemViewSleepRemind.setContentText(strRemindTime);
        itemViewDeviceId.setContentText(strDeviceId);
        itemFirmwareVersion.setContentText(strFirmwareVersion);
    }

    private void isShowItem(boolean isShow) {
        itemViewBattry.setVisibility(isShow ? View.VISIBLE : View.GONE);
        itemViewSleepRemind.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void isShowWatchItem(boolean isShow) {
        itemViewSleepRemind.setVisibility(isShow ? View.GONE : View.VISIBLE);
        itemViewScreenTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
        itemViewStepGoal.setVisibility(isShow ? View.VISIBLE : View.GONE);
        itemViewScreenUp.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void getIntentData() {
        deviceBean = (DeviceBean) getIntent().getSerializableExtra(JkConfiguration.DEVICE);
    }


    @Override
    protected void initEvent() {
        ISportAgent.getInstance().registerListener(mBleReciveListener);
        tvUnBind.setOnClickListener(this);
        itemViewSleepRemind.setOnClickListener(this);
        itemViewScreenTime.setOnClickListener(this);
        itemViewScreenUp.setOnClickListener(this);
        itemViewStepGoal.setOnClickListener(this);
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {

            }
        });
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
                    case IResult.SLEEP_HISTORYDATA:
                        SleepHistoryDataResultList sleepHistoryDataResultList = (SleepHistoryDataResultList) mResult;
                        //存储到本地，到历史页面,上传数据
                        ArrayList<SleepHistoryDataResult> sleepHistoryDataResults = sleepHistoryDataResultList
                                .getSleepHistoryDataResults();
                        if (sleepHistoryDataResults == null) {
                            //没有数据要上传,直接解绑
                            unBindDevice(deviceBean);
                        } else {
                            //有数据要上传
                            // TODO: 2019/2/23 网络状态判断
                            mActPresenter.updateSleepHistoryData(deviceBean);
                        }
                        break;
                    case IResult.SLEEP_SETAUTOCOLLECTION:
                        mActPresenter.findClockTime(deviceBean.deviceName, deviceBean.deviceID);
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
            case R.id.btn_unbind:
                boolean show = false;
                switch (deviceBean.deviceType) {
                    case JkConfiguration.DeviceType.SLEEP:
                        show = true;
                        break;
                    case JkConfiguration.DeviceType.WATCH_W516:
                        show = true;
                        break;
                }
                if (deviceBean.deviceType == JkConfiguration.DeviceType.BODYFAT) {
                    new PublicAlertDialog().showDialog(UIUtils.getString(R.string.notice), UIUtils.getString(R.string.unpair_notice), context, UIUtils.getString(R.string.common_dialog_cancel), UIUtils.getString(R.string.unpair), new AlertDialogStateCallBack() {


                        @Override
                        public void determine() {
                            unBindDevice(deviceBean);
                        }

                        @Override
                        public void cancel() {

                        }
                    }, false);
                } else {

                    new UnBindDeviceDialog(this, JkConfiguration.DeviceType.WATCH_W516, show, new UnbindStateCallBack() {
                        @Override
                        public void synUnbind() {
                            //睡眠带的先同步后绑定
                            if (AppConfiguration.isConnected) {
                                switch (deviceBean.currentType) {
                                    case JkConfiguration.DeviceType.WATCH_W516:
//                                        if (FragmentData.mWristbandstep != null) {
//                                            mActPresenter.updateSportData(FragmentData.mWristbandstep, deviceBean);
//                                        }
                                        break;
                                    case JkConfiguration.DeviceType.SLEEP:
                                        // TODO: 2018/11/8 上传睡眠数据
                                        //long creatTime = 0;
                                        UserInfoBean userInfo = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance()
                                                .getPeopleIdStr
                                                        (context));
                                        ISportAgent.getInstance().requestBle(BleRequest
                                                        .Sleep_Sleepace_historyDownload,
                                                (int) (App.getSleepBindTime() / 1000),
                                                (int)
                                                        DateUtils
                                                                .getCurrentTimeUnixLong
                                                                        (), userInfo
                                                        .getGender().equals
                                                                ("Male") ? 1 : 0);
                                        NetProgressObservable.getInstance().show(UIUtils.getString(R.string.common_please_wait),
                                                false);
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                            }
                        }

                        @Override
                        public void dirctUnbind() {
                            unBindDevice(deviceBean);
                        }

                        @Override
                        public void cancel() {

                        }
                    }, deviceBean.deviceType);
                }
                break;
            case R.id.itemview_sleep_remind: {
                Intent intent = new Intent(context, ActivityTimerSleep.class);
                intent.putExtra("deviceBean", deviceBean);
                if (!itemViewSleepRemind.getContentText().equals(UIUtils.getString(R.string.app_no_setting))) {
                    intent.putExtra("clockTime", itemViewSleepRemind.getContentText());
                }
                startActivity(intent);
                break;
            }
            case R.id.item_step_goal: {
                mActPresenter.popWindowSelect(context, itemViewStepGoal, JkConfiguration.Band.STEP_GOAL, mTarget,
                        false);
                break;
            }
            case R.id.item_screen_time: {
                mActPresenter.popWindowSelect(context, itemViewScreenTime, JkConfiguration.Band.STEP_SCREEN_TIME,
                        mScreenTime,
                        false);
                break;
            }
            case R.id.item_screen_up: {
                mActPresenter.popWindowSelect(context, itemViewScreenUp, JkConfiguration.Band.STEP_SCREEN_UP,
                        mEnableHandScreen,
                        false);
                break;
            }
        }
    }

    private void unBindDevice(DeviceBean deviceBean) {
        //解绑前断连设备
        if (AppConfiguration.isConnected) {
            BaseDevice currnetDevice = ISportAgent.getInstance().getCurrnetDevice();
            if (currnetDevice != null && currnetDevice.deviceType == deviceBean.deviceType) {
                Logger.myLog("currnetDevice == " + deviceBean.deviceType);
                //解绑设备不用重连
                ISportAgent.getInstance().unbind(false);
            }
        }
        mActPresenter.unBind(deviceBean);
    }

    @Override
    public void dataSetSuccess(String select, String data) {
        Logger.myLog("data == " + data);
        switch (select) {
            case JkConfiguration.Band.STEP_GOAL:
                mTarget = data;
                itemViewStepGoal.setContentText(data);
                int target = Integer.valueOf(data.split(" 步")[0]);
                JkConfiguration.WATCH_GOAL = target;
                //TODO 发送蓝牙指令
                ISportAgent.getInstance().requestBle(BleRequest.Watch_SmartBand_sendTarget, target);
                break;
            case JkConfiguration.Band.STEP_SCREEN_TIME:
                mScreenTime = data;
                itemViewScreenTime.setContentText(data);
                int screenTime = Integer.valueOf(data.split(" 秒")[0]);
                //TODO 发送蓝牙指令
                ISportAgent.getInstance().requestBle(BleRequest.Watch_SmartBand_sendScreenTime, screenTime);
                break;
            case JkConfiguration.Band.STEP_SCREEN_UP:
                mEnableHandScreen = data;
                itemViewScreenUp.setContentText(data);
                boolean enable = UIUtils.getString(R.string.app_enable).equals(data);
                //TODO 发送蓝牙指令
                ISportAgent.getInstance().requestBle(BleRequest.Watch_SmartBand_sendHandScreen, enable);
                break;
        }

    }

    @Override
    public void getClockTimeSuccess(String clockTime) {
        Logger.myLog("getClockTimeSuccess" + clockTime);
        Message message = new Message();
        message.what = 0;
        if (TextUtils.isEmpty(clockTime)) {
            itemViewSleepRemind.setContentText(UIUtils.getString(R.string.app_no_setting));
            message.obj = UIUtils.getString(R.string.app_no_setting);
        } else {
            itemViewSleepRemind.setContentText(clockTime);
            message.obj = clockTime;
        }
        mHandler.sendMessage(message);
    }

    @Override
    public void setClockTimeSuccess() {

    }

    @Override
    public void updateWatchDataSuccess(DeviceBean deviceBean) {
        unBindDevice(deviceBean);
    }

    @Override
    public void updateSleepDataSuccess(DeviceBean deviceBean) {
        unBindDevice(deviceBean);
    }


    @Override
    protected DeviceSettingPresenter createPresenter() {
        return new DeviceSettingPresenter(this);
    }

    @Override
    public void onUnBindSuccess() {
        //解绑的是当前连接的设备,需要断连设备
        EventBus.getDefault().post(new MessageEvent(MessageEvent.UNBIND_DEVICE_SUCCESS));
        Logger.myLog("解绑成功");
        finish();
    }
}
