package com.isport.brandapp.device.watch;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_AlarmModelAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_AlarmModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.entry.DeviceType;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.WatchSleepDayData;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.bracelet.Utils.RepeatUtils;
import com.isport.brandapp.device.watch.presenter.WatchPresenter;
import com.isport.brandapp.device.watch.view.WatchView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

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
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * 闹钟设置
 */
public class ActivityWatchAlarmSetting extends BaseMVPTitleActivity<WatchView, WatchPresenter> implements WatchView, View.OnClickListener, ItemView.OnItemViewCheckedChangeListener {
    private final static String TAG = ActivityWatchAlarmSetting.class.getSimpleName();
    private ItemView settingOpen, settingTime, settingRepeat;
    private DeviceBean deviceBean;
    private Integer fromDeviceType;
    private LinearLayout layout;

    private int mRepeat;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_watch_alarm_setting;
    }

    @Override
    protected void initView(View view) {

        settingOpen = view.findViewById(R.id.iv_watch_alarm_setting_open);
        settingTime = view.findViewById(R.id.iv_watch_alarm_setting_time);
        settingRepeat = view.findViewById(R.id.iv_watch_alarm_setting_repeat);
        layout = view.findViewById(R.id.layout);
    }


    public int getReapter(int week) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0");
        //从星期日开始是第一天

        if (week == 7) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }
        if (week == 6) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }
        if (week == 5) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }
        if (week == 4) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }
        if (week == 3) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }
        if (week == 2) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }
        if (week == 1) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }

        int value = Integer.valueOf(stringBuilder.toString(), 2);
        return value;
    }

    @Override
    protected void initData() {
        getIntentData();
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(context.getResources().getString(R.string.watch_alarm_setting_title));
        titleBarView.setRightText("");
        frameBodyLine.setVisibility(View.VISIBLE);

        if (deviceBean == null) {
            finish();
        }
        Watch_W516_AlarmModel watch_w516_alarmModelByDeviceId = Watch_W516_AlarmModelAction.findWatch_W516_AlarmModelByDeviceId(deviceBean.deviceName, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
        if (watch_w516_alarmModelByDeviceId == null) {
            //没有闹钟默认是0，信息"起床"，时间为8:00
            mRepeat = 0;
            settingOpen.setChecked(false);
            settingTime.setContentText(Constants.defStartTime);
            Watch_W516_AlarmModel watch_w516_alarmModel = new Watch_W516_AlarmModel();
            watch_w516_alarmModel.setDeviceId(deviceBean.deviceName);
            watch_w516_alarmModel.setUserId(TokenUtil.getInstance().getPeopleIdInt(this));
            watch_w516_alarmModel.setMessageString("123");
            watch_w516_alarmModel.setRepeatCount(mRepeat);
            watch_w516_alarmModel.setTimeString(Constants.defStartTime);
            BleAction.getWatch_W516_AlarmModelDao().insertOrReplace(watch_w516_alarmModel);
            ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM, true, 0, Constants.defStarHour, 0, 0);

            settingTime.setVisibility(View.GONE);
            settingRepeat.setVisibility(View.GONE);
            layout.setBackgroundResource(R.drawable.shape_btn_white_20_bg);
        } else {
            mRepeat = watch_w516_alarmModelByDeviceId.getRepeatCount();
            Logger.myLog("watch_w516_alarmModelByDeviceId " + watch_w516_alarmModelByDeviceId.toString());
            if (mRepeat == 0) {
                layout.setBackgroundResource(R.color.transparent);
                settingOpen.setChecked(false);
                settingTime.setVisibility(View.GONE);
                settingRepeat.setVisibility(View.GONE);
            } else {
                settingOpen.setChecked(true);
                layout.setBackgroundResource(R.drawable.shape_btn_white_20_bg);
                settingTime.setVisibility(View.VISIBLE);
                settingRepeat.setVisibility(View.VISIBLE);
            }
            settingTime.setContentText(watch_w516_alarmModelByDeviceId.getTimeString());
        }

        setRepeatStr(mRepeat);
    }

    private void setRepeatStr(int repeat) {
/*        byte[] booleanArrayG = Utils.getBooleanArray((byte) repeat);

        StringBuilder stringBuilderWeek = new StringBuilder();
        if (Utils.byte2Int(booleanArrayG[6]) == 1) {
            stringBuilderWeek.append(UIUtils.getString(R.string.mon) + ",");
        }
        if (Utils.byte2Int(booleanArrayG[5]) == 1) {
            stringBuilderWeek.append(UIUtils.getString(R.string.tue) + ",");
        }

        if (Utils.byte2Int(booleanArrayG[4]) == 1) {
            stringBuilderWeek.append(UIUtils.getString(R.string.wed) + ",");
        }

        if (Utils.byte2Int(booleanArrayG[3]) == 1) {
            stringBuilderWeek.append(UIUtils.getString(R.string.thu) + ",");
        }

        if (Utils.byte2Int(booleanArrayG[2]) == 1) {
            stringBuilderWeek.append(UIUtils.getString(R.string.fri) + ",");
        }

        if (Utils.byte2Int(booleanArrayG[1]) == 1) {
            stringBuilderWeek.append(UIUtils.getString(R.string.sat) + ",");
        }

        if (Utils.byte2Int(booleanArrayG[7]) == 1) {
            stringBuilderWeek.append(UIUtils.getString(R.string.sun) + ",");
        }

        String weekStr = stringBuilderWeek.toString();

        if (weekStr.endsWith(",")) {
            weekStr = weekStr.substring(0, weekStr.length() - 1);
        }*/
        settingRepeat.setContentText(RepeatUtils.setRepeat(DeviceType.WATCH_W516, repeat));

    }

    private void getIntentData() {
        deviceBean = (DeviceBean) getIntent().getSerializableExtra("deviceBean");
        fromDeviceType = getIntent().getIntExtra("type", JkConfiguration.DeviceType.WATCH_W516);
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
        settingTime.setOnContentClickListener(new ItemView.OnContentClickListener() {
            @Override
            public void onContentClick() {
                mActPresenter.setPopupWindow(context, settingTime, "3", settingTime.getContentText());
            }
        });
        settingRepeat.setOnContentClickListener(new ItemView.OnContentClickListener() {
            @Override
            public void onContentClick() {
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
            case R.id.iv_watch_alarm_setting_open:
                Log.e("StableRemind", "开启" + isChecked);
                if (AppConfiguration.isConnected) {
                    //如果关闭则设置为0
                    int[] hourAndMin = getHourAndMin();


                    int alarmHour = hourAndMin[0];
                    int alarmMin = hourAndMin[1];

                    if (isChecked) {
                        //默认给周日 如果当前时间大于闹钟时间就显示今天，如果当前时间小于闹钟时间就是第二天
                        Calendar calendar = Calendar.getInstance();
                        int weekday = calendar.get(Calendar.DAY_OF_WEEK);

                        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                        int currentMin = calendar.get(Calendar.MINUTE);

                        if (currentHour * 60 + currentMin > alarmHour * 60 + alarmMin) {
                            mRepeat = weekday;
                        } else {
                            mRepeat = weekday + 1;
                        }

                        Logger.myLog("mRepeat:" + mRepeat + " getReapter(mRepeat):" + getReapter(mRepeat));

                        setRepeatStr(getReapter(mRepeat));
                        settingTime.setVisibility(View.VISIBLE);
                        settingRepeat.setVisibility(View.VISIBLE);
                        layout.setBackgroundResource(R.drawable.shape_btn_white_20_bg);
                    } else {
                        mRepeat = 0;
                        settingRepeat.setContentText("");
                        settingTime.setVisibility(View.GONE);
                        settingRepeat.setVisibility(View.GONE);
                        layout.setBackgroundResource(R.color.transparent);
                    }

                    ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM, true, settingOpen.isChecked() ? mRepeat : 0, alarmHour, alarmMin, 0);
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
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
                if ("4".equals(select)) {
                    //设置了星期
                    int[] hourAndMin = getHourAndMin();
                    String[] split = data.split("-");
                    if (split.length >= 2) {
                        mRepeat = Integer.parseInt(split[0]);
                        settingRepeat.setContentText(split[1]);
                        setRepeatStr(mRepeat);
                        Logger.myLog("设置mRepeat == " + mRepeat);
                        ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM, true, settingOpen.isChecked() ? mRepeat : 0, hourAndMin[0], hourAndMin[1], 0);
                    } else {
                        settingOpen.setChecked(false);
                        mRepeat = 0;
                        settingTime.setVisibility(View.GONE);
                        settingRepeat.setVisibility(View.GONE);
                        layout.setBackgroundResource(R.color.transparent);
                        settingRepeat.setContentText("");
                        ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM, true, settingOpen.isChecked() ? mRepeat : 0, hourAndMin[0], hourAndMin[1], 0);

                    }

                } else {
                    ((ItemView) view).setContentText(data);
                    String[] split = data.split(":");
                    int hour = Integer.parseInt(split[0]);
                    int min = Integer.parseInt(split[1]);
                    ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM, true, settingOpen.isChecked() ? mRepeat : 0, hour, min, 0);
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

    private int[] getHourAndMin() {
        String[] split1 = settingTime.getContentText().split(":");
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
}
