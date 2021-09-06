package com.isport.brandapp.device.bracelet;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_AlarmModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W560_AlarmModel;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.WatchSleepDayData;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.bracelet.Utils.RepeatUtils;
import com.isport.brandapp.device.bracelet.braceletPresenter.AlarmPresenter;
import com.isport.brandapp.device.bracelet.view.AlarmView;
import com.isport.brandapp.device.watch.presenter.WatchPresenter;
import com.isport.brandapp.device.watch.view.WatchView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceTypeUtil;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import bike.gymproject.viewlibray.ItemView;
import bike.gymproject.viewlibray.pickerview.DatePickerView;
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
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * 闹钟设置
 */
public class DialogBraceletAlarmSetting extends BaseMVPTitleActivity<WatchView, WatchPresenter> implements WatchView, AlarmView, View.OnClickListener, ItemView.OnItemViewCheckedChangeListener {
    private final static String TAG = DialogBraceletAlarmSetting.class.getSimpleName();
    private ItemView settingRepeat;
    private DeviceBean deviceBean;
    private Integer fromDeviceType;
    private String deviceName;
    private int deviceType;
    private boolean isEdit;
    private AlarmPresenter alarmPresenter;
    private Bracelet_W311_AlarmModel alarmModel;
    private boolean isClick;
    private WatchPresenter presenter;

    private int mRepeat;
    DatePickerView datePicker;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_bracelet_alarm_setting;
    }

    @Override
    protected void initView(View view) {
        settingRepeat = view.findViewById(R.id.iv_watch_alarm_setting_repeat);
        datePicker = findViewById(R.id.datePicker);

    }

    @Override
    protected void initData() {
        getIntentData();
        titleBarView.setLeftIconEnable(true);
        if (isEdit) {
            titleBarView.setTitle(context.getResources().getString(R.string.watch_alarm_setting_title_edit));
        } else {
            titleBarView.setTitle(context.getResources().getString(R.string.watch_alarm_setting_title_add));
        }
        titleBarView.setRightText(UIUtils.getString(R.string.confirm), UIUtils.getColor(R.color.common_view_color));
        frameBodyLine.setVisibility(View.VISIBLE);
        settingRepeat.setContentText(RepeatUtils.setRepeat(deviceType, mRepeat));
        // setRepeatStr(mRepeat);
        datePicker.setType(3);
        if (isEdit) {
            datePicker.setDefaultItemAdapter(alarmModel.getTimeString());
        } else {
            datePicker.setDefaultItemAdapter(Constants.defStartTime);
            settingRepeat.setContentText(UIUtils.getString(R.string.only_once));
        }
        alarmPresenter.getAllAralm(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceName);
        datePicker.setCyclic(false);

    }

    ArrayList<Bracelet_W311_AlarmModel> alarmModels = new ArrayList<>();


    private void getIntentData() {
        deviceBean = (DeviceBean) getIntent().getSerializableExtra("deviceBean");
        if (deviceBean != null) {
            deviceName = deviceBean.deviceName;
            deviceType = deviceBean.deviceType;
        } else {
            deviceName = "";
            deviceType = JkConfiguration.DeviceType.WATCH_W516;
        }
        fromDeviceType = getIntent().getIntExtra("type", JkConfiguration.DeviceType.WATCH_W516);
        isEdit = getIntent().getBooleanExtra("isEdit", false);


        if (isEdit) {
            //是编辑
            alarmModel = new Bracelet_W311_AlarmModel();
            alarmModel.setDeviceId(getIntent().getStringExtra("itemDeviceId"));
            alarmModel.setId(getIntent().getLongExtra("itemId", 0l));
            alarmModel.setAlarmId(getIntent().getIntExtra("alarmId", 0));
            alarmModel.setIsOpen(getIntent().getBooleanExtra("itemOpen", true));
            alarmModel.setRepeatCount(getIntent().getIntExtra("itemRepeatCount", 0));
            alarmModel.setTimeString(getIntent().getStringExtra("itemTimeString"));
            alarmModel.setUserId(getIntent().getStringExtra("itemUserId"));
            mRepeat = alarmModel.getRepeatCount();
        } else {
            alarmModel = null;
        }

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
                if (!AppConfiguration.isConnected) {
                    ToastUtil.showTextToast(context, UIUtils.getString(R.string.app_disconnect_device));
                    return;
                }
                if (!isClick) {
                    isClick = true;
                    String selecttime = datePicker.getTime();
                    String alarmTime = "";
                    if (isEdit) {
                        if (alarmModel != null) {
                            alarmTime = alarmModel.getTimeString();
                            if (TextUtils.isEmpty(alarmTime)) {
                                alarmTime = "";
                            }
                        }
                    }

                    if (DeviceTypeUtil.isContainW55X(deviceType)) {
                        if (!AppConfiguration.isConnected) {
                            ToastUtil.showTextToast(context, UIUtils.getString(R.string.app_disconnect_device));
                            return;
                        }
                        if (!AppConfiguration.hasSynced) {
                            ToastUtil.showTextToast(context, UIUtils.getString(R.string.sync_data));
                            return;
                        }
                        Logger.myLog("alarmodle:" + alarmModel + "datePicker.getTime()" + datePicker.getTime() + "mRepeat:" + mRepeat + "alarmTime:" + alarmTime + "selecttime:" + selecttime);

                        if (isEdit) {
                            if (alarmTime.equals(selecttime) && alarmModel.getRepeatCount() == mRepeat) {
                                finish();
                                return;
                            }
                        }

                        alarmModel.setDeviceId(deviceName);
                        alarmModel.setUserId(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));
                        alarmModel.setRepeatCount(mRepeat);
                        alarmModel.setTimeString(selecttime);
                        alarmModel.setIsOpen(true);
                        alarmPresenter.updateAlarmItem(alarmModel, true);

                        String strtime = alarmModel.getTimeString();
                        String[] hourMin = strtime.split(":");
                        int repeat = 0;
                        if (alarmModel.getIsOpen()) {
                            if (alarmModel.getRepeatCount() == 0) {
                                repeat = 128;
                            } else {
                                repeat = mRepeat;
                            }
                        } else {
                            repeat = 0;
                        }
                        ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM, true, repeat, Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), alarmModel.getAlarmId());

                    } else {


                        isClick = true;
                        //保存
                        // getHourAndMin();

                        if (isEdit) {
                            if (alarmTime.equals(selecttime) && alarmModel.getRepeatCount() == mRepeat) {
                                finish();
                                return;
                            }
                        }


                        if (alarmModels.size() > 0) {
                            for (int i = 0; i < alarmModels.size(); i++) {
                                String time = alarmModels.get(i).getTimeString();
                                //  String selecttime = datePicker.getTime();
                                Logger.myLog("time:" + time + "selecttime:" + selecttime + "alarmModels.get(i).getRepeatCount():" + alarmModels.get(i).getRepeatCount());

                                if (time.equals(selecttime) && alarmModels.get(i).getRepeatCount() == mRepeat) {
                                    ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.alarm_tips_reapte));
                                    isClick = false;
                                    return;
                                }
                            }
                        }
                        NetProgressObservable.getInstance().show();
                        if (isEdit) {
                            Logger.myLog("alarmodle:" + alarmModel + "datePicker.getTime()" + datePicker.getTime() + "mRepeat:" + mRepeat);
                            alarmModel.setDeviceId(deviceName);
                            alarmModel.setUserId(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));
                            alarmModel.setRepeatCount(mRepeat);
                            alarmModel.setTimeString(selecttime);
                            alarmModel.setIsOpen(true);
                            alarmPresenter.updateAlarmItem(alarmModel, true);
                        } else {
                            //这里需要去比较下，如果有相同的就保存
                            alarmPresenter.saveAlarmItem(deviceName, TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), mRepeat, datePicker.getTime());
                        }
                    }
                    // mRepeat
                }
            }
        });

        settingRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActPresenter.setRepeatPopupWindow(context, settingRepeat, "4", mRepeat);
            }
        });
    }

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
        }
    }

    @Override
    protected WatchPresenter createPresenter() {
        alarmPresenter = new AlarmPresenter(this);
        return new WatchPresenter(this);
    }


    @Override
    public void dataSetSuccess(View view, String select, String data) {
        if (view instanceof ItemView) {
            //settingTime.setContentText(data);
            //  if (AppConfiguration.isConnected) {
            if ("4".equals(select)) {
                //设置了星期
                if (!TextUtils.isEmpty(data)) {
                    String[] split = data.split("-");
                    if (split.length <= 1) {
                        mRepeat = 0;
                        // settingRepeat.setContentText(RepeatUtils.setRepeat(deviceBean.deviceType,mRepeat));
                        settingRepeat.setContentText(UIUtils.getString(R.string.only_once));
                    }
                    if (split.length >= 1) {

                        mRepeat = Integer.parseInt(split[0]);


                    }
                    if (split.length >= 2) {
                        Logger.myLog("设置mRepeat == " + split[1]);
                        Logger.myLog(split[1]);
                        //settingRepeat.setContentText(split[1]);
                    }

                    int[] hourAndMin = getHourAndMin();
                    Logger.myLog("设置mRepeat == " + mRepeat);
                    // RepeatUtils.setRepeat(deviceBean.deviceType,mRepeat)
                    settingRepeat.setContentText(RepeatUtils.setRepeat(deviceType, mRepeat));
                    //setRepeatStr(mRepeat);
                } else {
                    mRepeat = 0;
                    settingRepeat.setContentText(UIUtils.getString(R.string.only_once));
                }
            } else {
                ((ItemView) view).setContentText(data);
                String[] split = data.split(":");
                int hour = Integer.parseInt(split[0]);
                int min = Integer.parseInt(split[1]);
            }
            //  } else {
            //ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
            //  }
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

    private int[] getHourAndMin() {
        String[] split1 = datePicker.getTime().split(":");
        int hour = Integer.parseInt(split1[0]);
        int min = Integer.parseInt(split1[1]);
        int[] result = new int[2];
        result[0] = hour;
        result[1] = min;
        return result;
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

    @Override
    public void successAllAlarmItem(ArrayList<Bracelet_W311_AlarmModel> bracelet_w311_displayModel) {
        alarmModels.clear();
        if (bracelet_w311_displayModel != null && bracelet_w311_displayModel.size() > 0) {
            alarmModels.addAll(bracelet_w311_displayModel);
        }

    }

    @Override
    public void successW560AllAlarmItem(ArrayList<Watch_W560_AlarmModel> watch_w560_alarmModels) {
        // 不作处理
    }

    @Override
    public void successSaveAlarmItem() {

        //ToastUtil.showTextToast(BaseApp.getApp(), "保存成功");
        // MessageEvent.isChange
        EventBus.getDefault().post(new MessageEvent(MessageEvent.isChange));

        // 去发送蓝牙
        isClick = false;
        NetProgressObservable.getInstance().hide();
        finish();

    }

    @Override
    public void successDelectAlarmItem() {

    }
}
