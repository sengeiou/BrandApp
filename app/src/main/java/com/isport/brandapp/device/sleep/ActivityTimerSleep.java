package com.isport.brandapp.device.sleep;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.sleep.SleepSetAutoCollectionResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.bind.presenter.DeviceSettingPresenter;
import com.isport.brandapp.bind.view.DeviceSettingView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.BebasNeueTextView;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class ActivityTimerSleep extends BaseMVPTitleActivity<DeviceSettingView,
        DeviceSettingPresenter> implements DeviceSettingView, View.OnClickListener {
    private BebasNeueTextView tvTime;
    private String data;
    private DeviceBean deviceBean;
    private String clockTime;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_timer_sleep;
    }

    @Override
    protected void initView(View view) {
        tvTime = view.findViewById(R.id.tv_time);
    }

    @Override
    protected void initData() {
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(getResources().getString(R.string.sleep_timer));
        titleBarView.setRightText(getResources().getString(R.string.save));
        deviceBean = (DeviceBean) getIntent().getSerializableExtra("deviceBean");
        clockTime = getIntent().getStringExtra("clockTime");
        if (StringUtil.isBlank(clockTime)) {
            clockTime = UIUtils.getString(R.string.app_def_setting);
            tvTime.setText(UIUtils.getString(R.string.app_def_setting));
        } else {
            tvTime.setText(clockTime);
        }
    }

    @Override
    protected void initEvent() {
        ISportAgent.getInstance().registerListener(mBleReciveListener);
        tvTime.setOnClickListener(this);
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {
                if (tvTime.getText().equals(UIUtils.getString(R.string.app_def_setting))) {
                    finish();
                } else {
                    if (clockTime.equals(tvTime.getText().toString())) {
                        finish();
                    } else {
                        if (App.appType()==App.httpType){
                            if (!checkNet())
                                return;
                        }
                        String[] split = tvTime.getText().toString().split(":");
                        ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_setAutoCollection, true,
                                                             Integer.parseInt(split[0]), Integer.parseInt(split[1]),
                                                             127);
                        Logger.myLog("设置自动睡眠时间");
                        TokenUtil.getInstance().saveSleepTime(ActivityTimerSleep.this, tvTime.getText().toString()
                                .trim());
                    }
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }

    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {
            if (isConn) {

            } else {
                ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnected));
            }
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            if (mResult != null)
                switch (mResult.getType()) {
                    case IResult.SLEEP_SETAUTOCOLLECTION:
                        SleepSetAutoCollectionResult sleepSetAutoCollectionResult = (SleepSetAutoCollectionResult)
                                mResult;
                        if (sleepSetAutoCollectionResult.isEnable()) {
                            //如果是单机的直接退出
                            Logger.myLog("设置自动睡眠时间成功");
                            mActPresenter.setClockTime(data, deviceBean);
                        } else {
                            ToastUtils.showToast(context, UIUtils.getString(R.string.app_setsuccess));
                        }
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
            case R.id.tv_time:
                mActPresenter.setPopupWindow(context, tvTime, "3", StringUtil.isBlank(clockTime) ? null : clockTime);
                break;
        }

    }

    @Override
    protected DeviceSettingPresenter createPresenter() {
        return new DeviceSettingPresenter(this);
    }

    @Override
    public void onUnBindSuccess() {

    }

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    public void dataSetSuccess(String select, String data) {
        String[] split = data.split(":");
        this.data = data;
        tvTime.setText(data);

        // AlarmManagerUtil.setAlarm(context,1,hourOfDay,minute,);

    }


    @Override
    public void getClockTimeSuccess(String clockTime) {

    }

    @Override
    public void setClockTimeSuccess() {
        finish();
    }

    @Override
    public void updateWatchDataSuccess(DeviceBean deviceBean) {

    }

    @Override
    public void updateSleepDataSuccess(DeviceBean deviceBean) {

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
                Intent intent = new Intent(context,ActivityLogin.class);
                context.startActivity(intent);
                ActivityManager.getInstance().finishAllActivity(ActivityLogin.class.getSimpleName());
                break;
            default:
                break;
        }
    }
}
