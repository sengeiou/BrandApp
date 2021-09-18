package com.isport.brandapp.device.watch;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.brandapp.App;
import com.isport.brandapp.home.bean.http.WatchSleepDayData;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.watch.presenter.WatchPresenter;
import com.isport.brandapp.device.watch.view.WatchView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;
import com.isport.brandapp.wu.adapter.SleepItemAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import bike.gymproject.viewlibray.BebasNeueTextView;
import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class ActivityWatchLitterSleep extends BaseMVPTitleActivity<WatchView, WatchPresenter> implements WatchView {
    private final static String TAG = ActivityWatchLitterSleep.class.getSimpleName();
    private BebasNeueTextView tvPointer;
    private TextView sleepState;
    private WatchSleepDayData mWatchSleepDayData;

    private RecyclerView recyclerview_sleep;
    SleepItemAdapter adapter;
    ArrayList<String> mDataList;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_watch_litter_sleep;
    }

    @Override
    protected void initView(View view) {

        tvPointer = view.findViewById(R.id.tv_pointer);
        sleepState = view.findViewById(R.id.sleep_state);

        mDataList = new ArrayList<>();
        recyclerview_sleep = findViewById(R.id.recyclerview_sleep);
        recyclerview_sleep.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    @Override
    protected void initData() {
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(context.getResources().getString(R.string.watch_litter_sleep_title));
        titleBarView.setRightText("");
        frameBodyLine.setVisibility(View.VISIBLE);

        mWatchSleepDayData = (WatchSleepDayData) getIntent().getSerializableExtra("mWatchSleepDayData");
        if (mWatchSleepDayData == null) {
            finish();

        }
        tvPointer.setText(mWatchSleepDayData.getSporadicNapSleepTime() + "");

        int sleepTime = mWatchSleepDayData.getSporadicNapSleepTime();

        if (mWatchSleepDayData.getSporadicNapSleepTime() > 45) {
            sleepState.setText(UIUtils.getString(R.string.sleep_tolong));
        } else {
            sleepState.setText(UIUtils.getString(R.string.sleep_normal));
        }

        int sporadicNapSleepTimes = mWatchSleepDayData.getSporadicNapSleepTimes();
        //Logger.myLog("sleepTime:" + sleepTime + "sporadicNapSleepTimes:" + sporadicNapSleepTimes + ",mWatchSleepDayData.getSporadicNapSleepTimeStr():" + mWatchSleepDayData.getSporadicNapSleepTimeStr());
        if (sporadicNapSleepTimes == 1 || sleepTime == 0) {
            //sporadicNapsOne.setText(mWatchSleepDayData.getSporadicNapSleepTimeStr());
            if (sleepTime != 0 && !TextUtils.isEmpty(mWatchSleepDayData.getSporadicNapSleepTimeStr())) {
                mDataList.add(mWatchSleepDayData.getSporadicNapSleepTimeStr());
            }

        } else {
            String[] split = mWatchSleepDayData.getSporadicNapSleepTimeStr().split(",");

            for (int i = 0; i < split.length; i++) {
                mDataList.add(split[i]);
            }
        }
        adapter = new SleepItemAdapter(this, mDataList);
        recyclerview_sleep.setAdapter(adapter);

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

    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected WatchPresenter createPresenter() {
        return new WatchPresenter(this);
    }

    @Override
    public void dataSetSuccess(View view, String select, String data) {
        if (view instanceof ItemView) {
            //settingTime.setContentText(data);
            ((ItemView) view).setContentText(data);
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
}
