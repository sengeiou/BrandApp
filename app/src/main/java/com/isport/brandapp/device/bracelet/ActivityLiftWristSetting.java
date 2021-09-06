package com.isport.brandapp.device.bracelet;

import android.view.View;
import android.widget.LinearLayout;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_LiftWristToViewInfoModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.WatchSleepDayData;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.bracelet.braceletPresenter.LiftWristPresenter;
import com.isport.brandapp.device.bracelet.view.LiftWristView;
import com.isport.brandapp.device.watch.presenter.WatchPresenter;
import com.isport.brandapp.device.watch.view.WatchView;
import com.isport.brandapp.util.DeviceDataUtil;
import com.isport.brandapp.util.DeviceTypeUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * 抬腕亮屏
 */
public class ActivityLiftWristSetting extends BaseMVPTitleActivity<WatchView, WatchPresenter> implements WatchView, LiftWristView, View.OnClickListener {
    private final static String TAG = ActivityLiftWristSetting.class.getSimpleName();
    private ItemView all_day, start_time, end_time;
    private DeviceBean deviceBean;
    private int deviceType;
    private String deviceName;
    private LiftWristPresenter liftWristPresenter;
    private Integer lastListState, startHour, endHour, startMin, endMin;
    private Boolean isNext;
    private LinearLayout layout;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_wristband_lift_to_view_info;
    }

    @Override
    protected void initView(View view) {

        all_day = view.findViewById(R.id.all_day);
        start_time = view.findViewById(R.id.start_time);
        end_time = view.findViewById(R.id.end_time);
        layout = view.findViewById(R.id.layout);
    }

    @Override
    protected void initData() {
        getIntentData();
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(context.getResources().getString(R.string.lift_to_view_info));
        titleBarView.setRightText("");
        liftWristPresenter.getLiftWristBean(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceName);
        //mActPresenter.getAllDisplayState(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceBean.deviceName);

    }


    private void getIntentData() {
        deviceBean = (DeviceBean) getIntent().getSerializableExtra("deviceBean");
        if (deviceBean != null) {
            deviceType = deviceBean.deviceType;
            deviceName = deviceBean.deviceName;
        } else {
            deviceType = JkConfiguration.DeviceType.WATCH_W516;
            deviceName = AppConfiguration.braceletID;
        }
    }


    @Override
    protected void initEvent() {
        all_day.setOnClickListener(this);
        start_time.setOnClickListener(this);
        end_time.setOnClickListener(this);
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

    @Override
    protected void initHeader() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_day:
                mActPresenter.popWindowSelect(context, all_day, JkConfiguration.GymUserInfo.LIFT_BRACELET_TO_VIEW_INFO, strlastListState, false);
                break;
            case R.id.start_time:
                mActPresenter.setPopupWindow(context, start_time, "3", start_time.getContentText());
                break;
            case R.id.end_time:
                mActPresenter.setPopupWindow(context, end_time, "3", end_time.getContentText());
                break;
        }
    }


    @Override
    protected WatchPresenter createPresenter() {
        liftWristPresenter = new LiftWristPresenter(this);
        return new WatchPresenter(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {

            default:
                break;
        }
    }


    @Override
    public void dataSetSuccess(View view, String select, String data) {

        if ((view == all_day)) {
            ((ItemView) view).setContentText(data);
            if (UIUtils.getString(R.string.lift_to_view_info_all_day).equals(data)) {
                stateType = 0;
                startHour = Constants.defStarHour;
                startMin = Constants.defStartMin;
                endHour = Constants.defEndHour;
                endMin = Constants.defEndMin;
                if (model != null) {
                    model.setSwichType(stateType);
                    model.setStartHour(startHour);
                    model.setEndHour(endHour);
                    model.setStartMin(startMin);
                    model.setEndMin(endMin);
                }
                start_time.setContentText(Constants.defStartTime);
                end_time.setContentText(Constants.defEndTime);
                showTimeItem(false);
            } else if (UIUtils.getString(R.string.lift_to_view_info_Timing_open).equals(data)) {
                stateType = 1;
                showTimeItem(true);
            } else {
                stateType = 2;
                if (DeviceTypeUtil.isContainW81(deviceType)) {
                    startHour = Constants.defStarHour;
                    startMin = Constants.defStartMin;
                    endHour = Constants.defEndHour;
                    endMin = Constants.defEndMin;
                    start_time.setContentText(Constants.defStartTime);
                    end_time.setContentText(Constants.defEndTime);
                }

                if (model != null) {
                    model.setSwichType(stateType);
                    model.setStartHour(startHour);
                    model.setEndHour(endHour);
                    model.setStartMin(startMin);
                    model.setEndMin(endMin);
                }

                showTimeItem(false);
            }
            if (model != null) {
                model.setSwichType(stateType);
            }
        } else {
            String[] split = data.split(":");
            int hour = Integer.parseInt(split[0]);
            int min = Integer.parseInt(split[1]);
            if (view == start_time) {
                if (DeviceDataUtil.isSame(hour, min, endHour, endMin)) {
                    ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.time_setting_same));
                    return;
                }
                startHour = hour;
                startMin = min;

            } else if (view == end_time) {


                if (DeviceDataUtil.isSame(startHour, startMin, hour, min)) {
                    ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.time_setting_same));
                    return;
                }
                Logger.myLog(deviceType + "startHour:" + startHour + "startMin:" + startMin + "hour:" + hour + "min:" + min);

                if (DeviceTypeUtil.isContainW55X(deviceType)) {
                    if (startHour == 0 && startMin == 0 && hour == 23 && min == 59) {
                        ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.liftwrist_allday));
                        return;
                    }
                }

                endHour = hour;
                endMin = min;
            }

            ((ItemView) view).setContentText(data);
            if (model != null) {
                model.setSwichType(stateType);
                model.setStartHour(startHour);
                model.setEndHour(endHour);
                model.setStartMin(startMin);
                model.setEndMin(endMin);
            }
        }
        setNext();
        liftWristPresenter.saveLiftWristBean(model);


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

    Bracelet_W311_LiftWristToViewInfoModel model;

    @Override
    public void successLifWristBean(Bracelet_W311_LiftWristToViewInfoModel models) {

        this.model = new Bracelet_W311_LiftWristToViewInfoModel();
        model.setSwichType(models.getSwichType());
        model.setEndMin(models.getEndMin());
        model.setEndHour(models.getEndHour());
        model.setStartMin(models.getStartMin());
        model.setStartHour(models.getStartHour());
        model.setDeviceId(models.getDeviceId());
        model.setId(models.getId());
        model.setIsNextDay(models.getIsNextDay());
        model.setUserId(models.getUserId());


        this.lastListState = model.getSwichType();

        this.startHour = model.getStartHour();
        this.startMin = model.getStartMin();
        this.endHour = model.getEndHour();
        this.endMin = model.getEndMin();
        this.isNext = model.getIsNextDay();


        if (lastListState == 0) {
            this.startHour = Constants.defStarHour;
            this.startMin = Constants.defStartMin;
            this.endHour = Constants.defEndHour;
            this.endMin = Constants.defEndMin;
        }


        updateUI();


    }

    @Override
    public void successSave(boolean isSave) {
        if (AppConfiguration.isConnected) {
            switch (stateType) {
                case 0:
                    strlastListState = UIUtils.getString(R.string.lift_to_view_info_all_day);
                    showTimeItem(false);
                    ISportAgent.getInstance().requestBle(BleRequest.bracelet_w311_is_open_raise_hand, true);
                    break;
                case 1:
                    strlastListState = UIUtils.getString(R.string.lift_to_view_info_Timing_open);
                    showTimeItem(true);
                    if (endHour == null) {
                        endHour = 7;
                    }
                    if (endMin == null) {
                        endMin = 0;
                    }
                    if (startHour == null) {
                        startHour = 22;
                    }
                    if (startMin == null) {
                        startMin = 0;
                    }
                    ISportAgent.getInstance().requestBle(BleRequest.bracelet_w311_raise_hand, stateType, endHour, endMin, startHour, startMin);
                    break;
                case 2:
                    showTimeItem(false);
                    strlastListState = UIUtils.getString(R.string.lift_to_view_info_close);
                    ISportAgent.getInstance().requestBle(BleRequest.bracelet_w311_raise_hand, stateType, 0, 0, 0, 0);
                    break;
            }
        }
    }

    int stateType = 0;

    public void setNext() {
        if (!DeviceDataUtil.comHour(this.startHour, this.startMin, this.endHour, this.endMin)) {
            end_time.showNext(UIUtils.getString(R.string.next_day));
        } else {
            end_time.hideNext();
        }
    }

    public String strlastListState;

    private void updateUI() {
        setNext();
        start_time.setContentText(CommonDateUtil.formatTwoStr(startHour) + ":" + CommonDateUtil.formatTwoStr(startMin));
        end_time.setContentText(CommonDateUtil.formatTwoStr(endHour) + ":" + CommonDateUtil.formatTwoStr(endMin));
        switch (lastListState) {
            case 0:
                stateType = 0;
                showTimeItem(false);
                model.setSwichType(stateType);
                // ISportAgent.getInstance().requestBle(BleRequest.bracelet_w311_is_open_raise_hand, true);
                strlastListState = UIUtils.getString(R.string.lift_to_view_info_all_day);
                all_day.setContentText(strlastListState);
                break;
            case 1:
                showTimeItem(true);
                strlastListState = UIUtils.getString(R.string.lift_to_view_info_Timing_open);
                all_day.setContentText(strlastListState);
                stateType = 1;
                break;
            case 2:
                stateType = 2;
                showTimeItem(false);
                strlastListState = UIUtils.getString(R.string.lift_to_view_info_close);
                all_day.setContentText(strlastListState);
                break;
        }


    }

    public void showTimeItem(boolean isShow) {
        if(isShow) {
            layout.setBackgroundResource(R.drawable.shape_btn_white_20_bg);
        }else{
            layout.setBackgroundResource(R.color.transparent);
        }
        start_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
        end_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
