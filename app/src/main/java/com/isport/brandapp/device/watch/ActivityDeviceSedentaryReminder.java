package com.isport.brandapp.device.watch;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.WatchSleepDayData;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.watch.presenter.WatchPresenter;
import com.isport.brandapp.device.watch.view.WatchView;
import com.isport.brandapp.util.DeviceDataUtil;
import com.isport.brandapp.util.DeviceTypeUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * 久坐提醒
 */
public class ActivityDeviceSedentaryReminder extends BaseMVPTitleActivity<WatchView, WatchPresenter> implements WatchView, View.OnClickListener, ItemView.OnItemViewCheckedChangeListener {
    private final static String TAG = ActivityDeviceSedentaryReminder.class.getSimpleName();
    private ItemView remindOpen, remindTime, startTime, endTime;
    private String deviceId;
    private Integer deviceType;

    private String defStartTime, defEndTime, sendStarTime, sendEndTime;
    private String defStrPerid = "60 ";

    private int defStartHour = Constants.defStarHour, defStartMin = Constants.defStartMin, defEndHour = Constants.defEndHour, defEndMin = Constants.defEndMin;
    private int sendStartHour = 0, sendStartMin = 0, sendEndHour = 0, sendEndMin = 0;
    private int sendPerid = 0;

    private int defperid = 60;
    private LinearLayout layout;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_watch_stable_remind;
    }

    @Override
    protected void initView(View view) {
        layout = view.findViewById(R.id.layout);
        remindOpen = view.findViewById(R.id.iv_watch_stable_remind_open);
        remindTime = view.findViewById(R.id.iv_watch_stable_remind_time);
        startTime = view.findViewById(R.id.iv_watch_stable_remind_start_time);
        endTime = view.findViewById(R.id.iv_watch_stable_remind_end_time);
    }

    @Override
    protected void initData() {
        getIntentData();
        defEndTime = Constants.defEndTime;
        defStartTime = Constants.defStartTime;
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(context.getResources().getString(R.string.watch_stable_remind_title));
        titleBarView.setRightText("");
        frameBodyLine.setVisibility(View.VISIBLE);
        if (JkConfiguration.DeviceType.WATCH_W516 == deviceType) {
            defStrPerid = "30 ";
            defperid = 30;
            sendPerid = 30;
        } else {
            defperid = 60;
            defStrPerid = "60 ";
            sendPerid = 60;
        }


        mActPresenter.getDeviceSedentaryReminder(deviceId, TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));


    }

    private void getIntentData() {
        deviceId = getIntent().getStringExtra("deviceId");
        deviceType = getIntent().getIntExtra("deviceType", JkConfiguration.DeviceType.WATCH_W516);
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

        remindOpen.setOnCheckedChangeListener(this);
        remindTime.setOnClickListener(this);
        remindTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //w516最低开始是30
                //311 是15 30 45 60
                if (deviceType == JkConfiguration.DeviceType.WATCH_W516) {
                    mActPresenter.popWindowSelect(ActivityDeviceSedentaryReminder.this, remindTime, JkConfiguration.GymUserInfo
                            .REMIND_TIME, remindTime.getContentText(), false);
                } else if (DeviceTypeUtil.isContainWrishBrand(deviceType) || DeviceTypeUtil.isContainW55X(deviceType) || DeviceTypeUtil.isContainW81(deviceType)) {
                    mActPresenter.popWindowSelect(ActivityDeviceSedentaryReminder.this, remindTime, JkConfiguration.GymUserInfo
                            .w311_long_time_reminder, remindTime.getContentText(), false);
                }
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeviceTypeUtil.isContaintW81(deviceType)) {
                    mActPresenter.setPopupWindow(context, startTime, "4", startTime.getContentText());
                } else {
                    mActPresenter.setPopupWindow(context, startTime, "3", startTime.getContentText());
                }

            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeviceTypeUtil.isContaintW81(deviceType)) {
                    mActPresenter.setPopupWindow(context, endTime, "4", endTime.getContentText());
                } else {
                    mActPresenter.setPopupWindow(context, endTime, "3", endTime.getContentText());
                }

            }
        });
    }

    @Override
    protected void initHeader() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_watch_stable_remind_time:

                break;
        }
    }

    @Override
    public void onCheckedChanged(int id, boolean isChecked) {
        switch (id) {
            case R.id.iv_watch_stable_remind_open:
                Log.e("StableRemind", "开启" + isChecked);
                if (AppConfiguration.isConnected) {
                    showTimeItem(isChecked);
                    String startText = startTime.getContentText();
                    String endText = endTime.getContentText();
                    String[] start;
                    if (startText == null) {
                        start = new String[]{defStartHour + "", defStartMin + ""};
                    } else {
                        start = startText.split(":");
                    }
                    String[] end;
                    if (endText == null) {
                        end = new String[]{defEndHour + "", defEndMin + ""};
                    } else {
                        end = endText.split(":");
                    }
                    sendStartHour = Integer.parseInt(start[0]);
                    sendStartMin = Integer.parseInt(start[1]);
                    sendEndHour = Integer.parseInt(end[0]);
                    sendEndMin = Integer.parseInt(end[1]);
                    if (isChecked) {
                        //打开默认是5分钟 W516 默认是30分钟
                        showTimeItem(true);
                        remindTime.setContentText(sendPerid + " " + UIUtils.getString(R.string.unit_minute));
                        if (JkConfiguration.DeviceType.WATCH_W516 == deviceType || DeviceTypeUtil.isContainW55X(deviceType)) {
                            ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SEDENTARY_TIME, sendPerid, sendStartHour, sendStartMin, sendEndHour, sendEndMin);
                        } else {
                            //TODO
                            saveDb(sendPerid, startText, endText, true);
                            ISportAgent.getInstance().requestBle(BleRequest.BRACELET_W311_SET_SEDENTARY_TIME, sendPerid, sendStartHour, sendStartMin, sendEndHour, sendEndMin);
                        }
                    } else {
                        //关闭默认是1分钟
                        remindOpen.setChecked(false);
                        //需要区分是81系列还是 w516 w556
                        if (DeviceTypeUtil.isContainW81(deviceType) || JkConfiguration.DeviceType.WATCH_W516 == deviceType || DeviceTypeUtil.isContainW55X(deviceType)) {
                            remindTime.setContentText(defStrPerid + UIUtils.getString(R.string.unit_minute));
                            startTime.setContentText(defStartTime);
                            endTime.setContentText(defEndTime);
                            sendStartHour = defStartHour;
                            sendStartMin = defStartMin;
                            sendEndHour = defEndHour;
                            sendEndMin = defEndMin;
                            sendPerid = defperid;
                        }
                        // w520 w311
                        if (JkConfiguration.DeviceType.WATCH_W516 == deviceType || DeviceTypeUtil.isContainW55X(deviceType)) {
                            ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SEDENTARY_TIME, 0, 0, 0, 23, 59);
                        } else {
                            saveDb(0, startText, endText, false);
                            ISportAgent.getInstance().requestBle(BleRequest.BRACELET_W311_SET_SEDENTARY_TIME, 0, sendStartHour, sendStartMin, sendEndHour, sendEndMin);
                        }
                    }
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                    remindOpen.setChecked(!isChecked);
                    showTimeItem(!isChecked);
                }
                break;
        }
    }

    public void showTimeItem(boolean isShow) {

        if (isShow) {
            layout.setBackgroundResource(R.drawable.shape_btn_white_20_bg);
        } else {
            layout.setBackgroundResource(R.color.transparent);
        }
        remindTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
        startTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
        endTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    protected WatchPresenter createPresenter() {
        return new WatchPresenter(this);
    }

    @Override
    public void dataSetSuccess(View view, String select, String data) {

        int tempHour = 0, temMin = 0;
        int tmepPerid = 0;

        if (view instanceof ItemView) {
            //settingTime.setContentText(data);
            if (AppConfiguration.isConnected) {

                switch (view.getId()) {
                    case R.id.iv_watch_stable_remind_time:
                        String startText = startTime.getContentText();
                        String endText = endTime.getContentText();
                        String[] start = startText.split(":");
                        String[] end = endText.split(":");
                        //要对这儿做限制，因为5分钟以下是关闭
                        // sendPerid = Integer.parseInt(data.split(" " + UIUtils.getString(R.string.unit_minute))[0]);
                        sendStartHour = Integer.parseInt(start[0]);
                        sendStartMin = Integer.parseInt(start[1]);
                        sendEndHour = Integer.parseInt(end[0]);
                        sendEndMin = Integer.parseInt(end[1]);
                        tmepPerid = Integer.parseInt(data.split(" " + UIUtils.getString(R.string.unit_minute))[0]);
                        Logger.myLog("dataSetSuccess,sendStartHour:" + sendStartHour + "sendStartMin:" + sendStartMin + "sendEndHour:" + sendEndHour + "sendEndMin:" + sendEndMin + "tmepPerid:" + tmepPerid);
                        if (DeviceDataUtil.comHourSame(tempHour, temMin, sendEndHour, sendEndMin)) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.stableRemind_same_tips));
                            return;
                        }
                        if (!DeviceDataUtil.settingAbleTime(sendStartHour, sendStartMin, sendEndHour, sendEndMin, sendPerid)) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.stableRemind_Perid_tips));
                            return;
                        }

                        ((ItemView) view).setContentText(data);
                        sendPerid = tmepPerid;
                        sendCmd(sendPerid, sendStartHour, sendStartMin, sendEndHour, sendEndMin, startText, endText);
                        break;
                    case R.id.iv_watch_stable_remind_start_time:
                        //开始时间不能大于结束时间
                        String remindText1 = remindTime.getContentText();
                        String endText1 = endTime.getContentText();
                        String[] end1 = endText1.split(":");
                        String[] start1 = data.split(":");
                        tempHour = Integer.parseInt(start1[0]);
                        temMin = Integer.parseInt(start1[1]);
                        sendEndHour = Integer.parseInt(end1[0]);
                        sendEndMin = Integer.parseInt(end1[1]);
                        sendPerid = Integer.parseInt(remindText1.split(" " + UIUtils.getString(R.string.unit_minute))[0]);
                        if (DeviceDataUtil.comHourSame(tempHour, temMin, sendEndHour, sendEndMin)) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.stableRemind_same_tips));
                            return;
                        }
                        if (!DeviceDataUtil.comHour(tempHour, temMin, sendEndHour, sendEndMin)) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.stableRemind_tips));
                            return;
                        }
                        if (DeviceDataUtil.settingAbleTime(tempHour, temMin, sendEndHour, sendEndMin, sendPerid)) {
                            sendStartHour = tempHour;
                            sendStartMin = temMin;
                            sendCmd(sendPerid, sendStartHour, sendStartMin, sendEndHour, sendEndMin, data, endText1);
                            startTime.setContentText(data);
                        } else {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.stableRemind_Perid_tips));
                        }
                        break;
                    case R.id.iv_watch_stable_remind_end_time:
                        //开始时间不能大于结束时间
                        String remindText2 = remindTime.getContentText();
                        String startText1 = startTime.getContentText();
                        String[] start2 = startText1.split(":");
                        String[] end2 = data.split(":");
                        sendStartHour = Integer.parseInt(start2[0]);
                        sendStartMin = Integer.parseInt(start2[1]);
                        sendPerid = Integer.parseInt(remindText2.split(" " + UIUtils.getString(R.string.unit_minute))[0]);
                        tempHour = Integer.parseInt(end2[0]);
                        temMin = Integer.parseInt(end2[1]);
                        if (!DeviceDataUtil.comHour(sendStartHour, sendStartMin, tempHour, temMin)) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.stableRemind_tips));
                            return;
                        }
                        if (DeviceDataUtil.settingAbleTime(sendStartHour, sendStartMin, tempHour, temMin, sendPerid)) {
                            sendEndHour = tempHour;
                            sendEndMin = temMin;
                            endTime.setContentText(data);
                            sendCmd(sendPerid, sendStartHour, sendStartMin, sendEndHour, sendEndMin, startText1, endTime.getContentText());

                        } else {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.stableRemind_Perid_tips));
                        }
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
    public void seccessGetDeviceSedentaryReminder(Watch_W516_SedentaryModel model) {

        Logger.myLog("findWatch_W516_Watch_W516_SedentaryModelyDeviceId" + model.toString());


        if (model.getIsEnable() && model.getLongSitTimeLong() > 5) {
            showTimeItem(true);
            remindOpen.setChecked(true);
            remindTime.setContentText(model.getLongSitTimeLong() + " " + UIUtils.getString(R.string.unit_minute));
            startTime.setContentText(StringUtil.isBlank(model.getLongSitStartTime()) ? defStartTime : model.getLongSitStartTime());
            endTime.setContentText(StringUtil.isBlank(model.getLongSitEndTime()) ? defEndTime : model.getLongSitEndTime());
            sendStarTime = startTime.getContentText();
            sendEndTime = endTime.getContentText();
            String startText = startTime.getContentText();
            String endText = endTime.getContentText();
            String[] start = startText.split(":");
            String[] end = endText.split(":");
            //要对这儿做限制，因为5分钟以下是关闭
            // sendPerid = Integer.parseInt(data.split(" " + UIUtils.getString(R.string.unit_minute))[0]);
            sendStartHour = Integer.parseInt(start[0]);
            sendStartMin = Integer.parseInt(start[1]);
            sendEndHour = Integer.parseInt(end[0]);
            sendEndMin = Integer.parseInt(end[1]);
            sendPerid = Integer.parseInt(remindTime.getContentText().split(" " + UIUtils.getString(R.string.unit_minute))[0]);
        } else {
            showTimeItem(false);
            remindOpen.setChecked(false);
            remindTime.setContentText(defStrPerid + " " + UIUtils.getString(R.string.unit_minute));
            startTime.setContentText(defStartTime);
            endTime.setContentText(defEndTime);
        }
        //showTimeItem(model.getIsEnable());


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            default:
                break;
        }
    }

    public void saveDb(int times, String starTime, String endTime, boolean enable) {

        mActPresenter.saveDevcieSedentaryReminder(deviceId, TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), times, starTime, endTime, enable);

    }


    public void sendCmd(int perid, int starthour, int startMin, int endHour, int endMin, String strStartTime, String strEndTime) {
        if (JkConfiguration.DeviceType.WATCH_W516 == deviceType || DeviceTypeUtil.isContainW55X(deviceType)) {
            ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SEDENTARY_TIME, perid, starthour, startMin, endHour, endMin);
        } else {
            saveDb(perid, strStartTime, strEndTime, true);
            ISportAgent.getInstance().requestBle(BleRequest.BRACELET_W311_SET_SEDENTARY_TIME, perid, starthour, startMin, endHour, endMin);
        }
    }
}
