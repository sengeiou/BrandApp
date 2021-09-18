package com.isport.brandapp.device.bracelet;

import android.content.Intent;
import android.view.View;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_24H_hr_SettingModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_w311_hrModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.brandapp.App;
import com.isport.brandapp.home.bean.http.WatchSleepDayData;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.bracelet.braceletPresenter.HrSettingPresenter;
import com.isport.brandapp.device.bracelet.view.HrSettingView;
import com.isport.brandapp.device.watch.presenter.WatchPresenter;
import com.isport.brandapp.device.watch.view.WatchView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceTypeUtil;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.ActivityManager;
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
 * 心率间隔设置
 */
public class ActivityBraceletHrSetting extends BaseMVPTitleActivity<WatchView, WatchPresenter> implements WatchView, HrSettingView, View.OnClickListener, ItemView.OnItemViewCheckedChangeListener {
    private final static String TAG = ActivityBraceletHrSetting.class.getSimpleName();
    private ItemView itemSwitch, itemTimes, switch_24h_open;
    private DeviceBean deviceBean;
    private Integer fromDeviceType;
    private String lastTime;
    private int currentType;
    private HrSettingPresenter hrSettingPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_hr_setting;
    }

    @Override
    protected void initView(View view) {
        itemSwitch = view.findViewById(R.id.switch_open);
        itemTimes = view.findViewById(R.id.hr_times);
        switch_24h_open = view.findViewById(R.id.switch_24h_open);
    }

    int min = 0;

    @Override
    protected void initData() {
        getIntentData();
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(context.getResources().getString(R.string.hr_setting));
        //titleBarView.setRightText(UIUtils.getString(R.string.confirm), UIUtils.getColor(R.color.common_view_color));
        frameBodyLine.setVisibility(View.VISIBLE);
        lastTime = "30";
        min = 30;
        hrSettingPresenter.getHrItem(TokenUtil.getInstance().getPeopleIdInt(this), deviceBean.deviceName);

        if (DeviceTypeUtil.isContaintW81(currentType)) {
            switch_24h_open.setVisibility(View.VISIBLE);
        } else {
            switch_24h_open.setVisibility(View.GONE);
        }
    }


    private void getIntentData() {
        deviceBean = (DeviceBean) getIntent().getSerializableExtra(JkConfiguration.DEVICE);
        if (deviceBean != null) {
            currentType = deviceBean.currentType;
        } else {
            currentType = JkConfiguration.DeviceType.BRAND_W311;
        }
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
        itemTimes.setOnClickListener(this);

        itemTimes.setOnContentClickListener(new ItemView.OnContentClickListener() {
            @Override
            public void onContentClick() {
                if (itemSwitch.isChecked()) {
                    mActPresenter.popWindowSelect(context, itemTimes, JkConfiguration.GymUserInfo.HR_TIMES, lastTime, false);
                }
            }
        });
        itemSwitch.setOnCheckedChangeListener(new ItemView.OnItemViewCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int id, boolean isChecked) {
                if (isChecked) {
                    lastTime = min + "";
                    model.setIsOpen(isChecked);
                    ISportAgent.getInstance().requestBle(BleRequest.bracelet_heartRate, true, min);
                    hrSettingPresenter.saveHrSetting(model);
                    itemTimes.setVisibility(View.VISIBLE);
                } else {
                    lastTime = min + "";
                    model.setIsOpen(isChecked);
                    ISportAgent.getInstance().requestBle(BleRequest.bracelet_heartRate, false, min);
                    hrSettingPresenter.saveHrSetting(model);
                    itemTimes.setVisibility(View.GONE);
                }
            }
        });
        switch_24h_open.setOnCheckedChangeListener(new ItemView.OnItemViewCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int id, boolean isChecked) {
                if (isChecked) {

                } else {

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
            case R.id.hr_times:
                if (itemSwitch.isChecked()) {
                    mActPresenter.popWindowSelect(context, itemTimes, JkConfiguration.GymUserInfo.HR_TIMES, lastTime, false);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(int id, boolean isChecked) {
        switch (id) {
        }
    }

    @Override
    protected WatchPresenter createPresenter() {
        hrSettingPresenter = new HrSettingPresenter(this);
        return new WatchPresenter(this);
    }

    @Override
    public void dataSetSuccess(View view, String select, String data) {
        itemTimes.setContentText(data);
        String[] strs = data.split(" ");
        if (view instanceof ItemView) {
            try {
                min = Integer.parseInt(strs[0]);
            } catch (Exception e) {
                min = 30;
            } finally {
                lastTime = min + "";
                model.setIsOpen(true);
                model.setTimes(min);
                ISportAgent.getInstance().requestBle(BleRequest.bracelet_heartRate, true, min);
                hrSettingPresenter.saveHrSetting(model);
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

    Bracelet_w311_hrModel model;

    @Override
    public void successHrSettingItem(Bracelet_w311_hrModel bracelet_w311_wearModel) {
        model = bracelet_w311_wearModel;
        itemSwitch.setChecked(model.getIsOpen());
        if (model.getIsOpen()) {
            itemTimes.setVisibility(View.VISIBLE);
        } else {
            itemTimes.setVisibility(View.GONE);
        }
        lastTime = model.getTimes() + "";
        min = model.getTimes();
        itemTimes.setContentText(model.getTimes() + " " + UIUtils.getString(R.string.unit_minute));
    }

    @Override
    public void successSave(boolean isSave) {
        // ToastUtil.showTextToast(BaseApp.getApp(), "保存成功");
    }

    @Override
    public void success24HrSettingState(Bracelet_W311_24H_hr_SettingModel bracelet_w311_24H_hr_settingModel) {

    }
}
