package com.isport.brandapp.device.bracelet;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DisplayModel;
import com.isport.blelibrary.entry.DisplaySet;
import com.isport.blelibrary.managers.HandlerContans;
import com.isport.blelibrary.observe.BleGetStateObservable;
import com.isport.blelibrary.observe.BleSettingObservable;
import com.isport.blelibrary.observe.bean.ResultBean;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.bracelet.braceletPresenter.DisplayPresenter;
import com.isport.brandapp.device.bracelet.view.DisplayView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Observable;

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
 *
 */
public class ActivityBraceletDisplaySetting extends BaseMVPTitleActivity<DisplayView, DisplayPresenter> implements DisplayView, View.OnClickListener, ItemView.OnItemViewCheckedChangeListener {
    private final static String TAG = ActivityBraceletDisplaySetting.class.getSimpleName();
    private ItemView iv_display_cal, iv_display_dis, iv_display_sport_time, iv_display_present, iv_display_complete, iv_display_alarm;
    private DeviceBean deviceBean;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_wristband_display_setting;
    }

    @Override
    protected void initView(View view) {
        iv_display_cal = view.findViewById(R.id.iv_display_cal);
        iv_display_dis = view.findViewById(R.id.iv_display_dis);
        iv_display_sport_time = view.findViewById(R.id.iv_display_sport_time);
        iv_display_present = view.findViewById(R.id.iv_display_present);
        iv_display_complete = view.findViewById(R.id.iv_display_complete);
        iv_display_alarm = view.findViewById(R.id.iv_display_alarm);
    }

    @Override
    protected void initData() {

        if (deviceBean == null) {
            finish();
        }
        getIntentData();
        titleBarView.setLeftIconEnable(true);
        BleGetStateObservable.getInstance().addObserver(this);
        titleBarView.setTitle(context.getResources().getString(R.string.display_setting));
        titleBarView.setRightText("");
        frameBodyLine.setVisibility(View.VISIBLE);
        BleSettingObservable.getInstance().addObserver(this);
        /*if (AppConfiguration.isConnected) {
            //去获取当前的
            ISportAgent.getInstance().requestBle(BleRequest.bracelet_w311_get_display);
        } else {
            mActPresenter.getAllDisplayState(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceBean.deviceID);
        }*/
        mActPresenter.getAllDisplayState(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceBean.deviceID);
        if (deviceBean.currentType == JkConfiguration.DeviceType.BRAND_W311) {
            iv_display_cal.setVisibility(View.VISIBLE);
            iv_display_dis.setVisibility(View.VISIBLE);
            iv_display_sport_time.setVisibility(View.VISIBLE);
            iv_display_present.setVisibility(View.VISIBLE);
            iv_display_complete.setVisibility(View.VISIBLE);
            iv_display_alarm.setVisibility(View.VISIBLE);
        } else if (deviceBean.currentType == JkConfiguration.DeviceType.Brand_W520) {
            iv_display_cal.setVisibility(View.VISIBLE);
            iv_display_dis.setVisibility(View.VISIBLE);
            iv_display_sport_time.setVisibility(View.GONE);
            iv_display_present.setVisibility(View.GONE);
            iv_display_complete.setVisibility(View.GONE);
            iv_display_alarm.setVisibility(View.GONE);
        } else {
            iv_display_cal.setVisibility(View.VISIBLE);
            iv_display_dis.setVisibility(View.VISIBLE);
            iv_display_sport_time.setVisibility(View.GONE);
            iv_display_present.setVisibility(View.GONE);
            iv_display_complete.setVisibility(View.VISIBLE);
            iv_display_alarm.setVisibility(View.GONE);
        }


    }

    DisplaySet displaySet;

    Bracelet_W311_DisplayModel model;

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        if (o instanceof BleGetStateObservable) {
            if (arg instanceof DisplaySet) {
                displaySet = (DisplaySet) arg;
            }
        } else if (o instanceof BleSettingObservable) {

            ResultBean result = (ResultBean) arg;
            Logger.myLog("BleSettingObservable result.dataType=" + result.dataType + "result.isSuccess=" + result.isSuccess);
            if (result.dataType == HandlerContans.mSenderGetDisplay && result.isSuccess == 0) {
                handler.removeMessages(0x01);
                handler.sendEmptyMessage(0x01);
            }
        }
    }

    private void getIntentData() {
        deviceBean = (DeviceBean) getIntent().getSerializableExtra("deviceBean");
    }


    @Override
    protected void initEvent() {
        iv_display_alarm.setOnCheckedChangeListener(this);
        iv_display_cal.setOnCheckedChangeListener(this);
        iv_display_dis.setOnCheckedChangeListener(this);
        iv_display_sport_time.setOnCheckedChangeListener(this);
        iv_display_present.setOnCheckedChangeListener(this);
        iv_display_complete.setOnCheckedChangeListener(this);
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
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    Logger.myLog("handler 0x01");
                    BleProgressObservable.getInstance().hide();
                    break;
                case 0x02:
                    BleProgressObservable.getInstance().show();
                    break;
            }
        }
    };

    @Override
    public void onCheckedChanged(int id, boolean isChecked) {
        if (displaySet == null) {
            return;
        }
        switch (id) {
            case R.id.iv_display_cal:
                Log.e("iv_display_cal", "开启" + isChecked);
                if (model != null) {
                    model.setIsShowCal(isChecked);
                    mActPresenter.saveDisplayItem(model);
                }
                displaySet.setShowCala(isChecked);
                if (AppConfiguration.isConnected) {
                    //如果关闭则设置为0
                    starthandler();

                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                }

                break;
            case R.id.iv_display_dis:
                Log.e("iv_display_cal", "开启" + isChecked);
                if (model != null) {
                    model.setIsShowDis(isChecked);
                    mActPresenter.saveDisplayItem(model);
                }
                displaySet.setShowDist(isChecked);
                if (AppConfiguration.isConnected) {
                    //如果关闭则设置为0
                    if (AppConfiguration.isConnected) {
                        //如果关闭则设置为0
                        starthandler();
                    } else {
                        ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                    }
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                }
                break;
            case R.id.iv_display_alarm:
                if (model != null) {
                    model.setIsShowAlarm(isChecked);
                    mActPresenter.saveDisplayItem(model);
                }
                displaySet.setShowAlarm(isChecked);
                Log.e("iv_display_cal", "开启" + isChecked);
                if (AppConfiguration.isConnected) {
                    //如果关闭则设置为0
                    starthandler();
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                }
                break;
            case R.id.iv_display_complete:
                if (model != null) {
                    model.setIsShowComplete(isChecked);
                    mActPresenter.saveDisplayItem(model);
                }
                displaySet.setShowEmotion(isChecked);
                Log.e("iv_display_cal", "开启" + isChecked);
                if (AppConfiguration.isConnected) {
                    //如果关闭则设置为0
                    starthandler();
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                }
                break;
            case R.id.iv_display_present:
                if (model != null) {
                    model.setIsShowPresent(isChecked);
                    mActPresenter.saveDisplayItem(model);
                }
                Log.e("iv_display_cal", "开启" + isChecked);
                displaySet.setShowProgress(isChecked);
                if (AppConfiguration.isConnected) {
                    starthandler();
                    //如果关闭则设置为0
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                }
                break;
            case R.id.iv_display_sport_time:
                if (model != null) {
                    model.setIsShowSportTime(isChecked);
                    mActPresenter.saveDisplayItem(model);
                }
                Log.e("iv_display_cal", "开启" + isChecked);
                displaySet.setShowSportTime(isChecked);
                if (AppConfiguration.isConnected) {
                    //如果关闭则设置为0
                    starthandler();
                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                }
                break;
        }
    }

    private void starthandler() {
        ISportAgent.getInstance().requestBle(BleRequest.bracelet_w311_set_display, displaySet);
        handler.sendEmptyMessageDelayed(0x01, 1000);
        handler.sendEmptyMessage(0x02);
    }

    @Override
    protected DisplayPresenter createPresenter() {
        return new DisplayPresenter(this);
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
    protected void onDestroy() {
        super.onDestroy();
        BleGetStateObservable.getInstance().deleteObserver(this);
        BleSettingObservable.getInstance().deleteObserver(this);
    }

    @Override
    public void successDisplayItem(Bracelet_W311_DisplayModel bracelet_w311_displayModel, boolean isMessage, boolean isCall) {
        model = bracelet_w311_displayModel;

        displaySet = new DisplaySet();
        displaySet.setShowAlarm(model.getIsShowAlarm());
        displaySet.setShowCala(model.getIsShowCal());
        displaySet.setShowDist(model.getIsShowDis());
        displaySet.setShowProgress(model.getIsShowPresent());
        displaySet.setShowEmotion(model.getIsShowComplete());
        displaySet.setShowSportTime(model.getIsShowSportTime());
        displaySet.setShowIncomingReminder(isCall);
        displaySet.setShowMsgContentPush(isMessage);

        iv_display_alarm.setChecked(bracelet_w311_displayModel.getIsShowAlarm());
        iv_display_cal.setCheckBox(bracelet_w311_displayModel.getIsShowCal());
        iv_display_dis.setCheckBox(bracelet_w311_displayModel.getIsShowDis());
        iv_display_present.setCheckBox(bracelet_w311_displayModel.getIsShowPresent());
        iv_display_complete.setCheckBox(bracelet_w311_displayModel.getIsShowComplete());
        iv_display_sport_time.setCheckBox(bracelet_w311_displayModel.getIsShowSportTime());

    }

    @Override
    public void successSaveDisplayItem() {
        // ToastUtil.showTextToast(BaseApp.getApp(), "修改成功");
    }
}
