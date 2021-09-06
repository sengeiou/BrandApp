package com.isport.brandapp.wu.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.CommonInterFace.DeviceMessureData;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.watch.DeviceMessureDataResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.device.W81Device.W81DeviceDataModelImp;
import com.isport.brandapp.util.ActivitySwitcher;
import com.isport.brandapp.wu.bean.DrawRecDataBean;
import com.isport.brandapp.wu.bean.OxyInfo;
import com.isport.brandapp.wu.mvp.OxyHistoryView;
import com.isport.brandapp.wu.mvp.presenter.OxyHistoryPresenter;
import com.isport.brandapp.wu.view.OxyTrendView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonalertdialog.ProgressOxyView;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BaseMVPActivity;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class OxyResultActivity extends BaseMVPActivity<OxyHistoryView, OxyHistoryPresenter> implements OxyHistoryView {

    private ArrayList<OxyInfo> mDataList = new ArrayList<>();
    private long lastTimestamp;
    private OxyInfo mCurrentInfo;

    private OxyTrendView trendview_oxy;
    // private OxyBarView oxy_barview;
    private TextView tv_oxy_value;
    private TextView tv_time;
    private TextView btn_measure;
    private TextView tv_title;
    private TextView tv_percent;
    private ImageView iv_back;
    private ImageView iv_history;
    private ProgressOxyView progressOxyView;

    private boolean isMeasure = false;
    private W81DeviceDataModelImp mW81DeviceDataModelImp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oxy_result;
    }

    @Override
    protected void initView(View view) {
        progressOxyView = view.findViewById(R.id.progressBar);
        tv_title = view.findViewById(R.id.tv_title);
        iv_history = findViewById(R.id.iv_history);
        iv_back = findViewById(R.id.iv_back);
        trendview_oxy = findViewById(R.id.trendview_oxy);
        tv_oxy_value = findViewById(R.id.tv_oxy_value);
        tv_time = findViewById(R.id.tv_time);
        tv_percent = findViewById(R.id.tv_percent);
        btn_measure = findViewById(R.id.btn_measure);
        btn_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMeasure) {
                    startMeasure();
                } else {
                    finishMeasure();
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OxyResultActivity.this, OxyListActivity.class));
            }
        });
    }

    @Override
    protected void initHeader() {
        // StatusBarCompat.setStatusBarColor(this, getResources().getColor(com.isport.brandapp.basicres.R.color.common_view_color));


    }

    @Override
    protected void initData() {
        if (tv_title != null) {
            tv_title.setText(getResources().getString(R.string.mian_title_oxy));
        }
        mW81DeviceDataModelImp = new W81DeviceDataModelImp();
        mActPresenter.getOxyNumData();
        ISportAgent.getInstance().registerListener(mBleReciveListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }

    private void startMeasure() {
        if (AppConfiguration.isConnected) {
            isMeasure = true;
            btn_measure.setText(R.string.stop_measure);
            ISportAgent.getInstance().requsetW81Ble(BleRequest.DEVICE_MEASURE_OXYGEN, true);
            ActivitySwitcher.goMeasureActivty(this, JkConfiguration.DeviceMeasureType.oxygen);

            // ISportAgent.getInstance().requsetW81Ble(BleRequest.DEVICE_MEASURE_ONECE_HR_DATA, true);
        } else {
            Toast.makeText(this, getString(R.string.app_disconnect_device), Toast.LENGTH_LONG).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.measure_end:
                isMeasure = false;
                btn_measure.setText(R.string.start_measure);
                //setDataFromLocal();
                Logger.myLog("measure_oxygen success");
                break;
        }
    }

    private void finishMeasure() {
        if (AppConfiguration.isConnected) {
            ISportAgent.getInstance().requsetW81Ble(BleRequest.DEVICE_MEASURE_OXYGEN, false);
            isMeasure = false;
            btn_measure.setText(R.string.start_measure);
        } else {
            Toast.makeText(this, getString(R.string.app_disconnect_device), Toast.LENGTH_LONG).show();
        }
    }

    private void setData() {
        tv_percent.setVisibility(View.VISIBLE);
        if (mCurrentInfo.getBoValue() > 80) {
            progressOxyView.setProgress(mCurrentInfo.getBoValue());
        }
        tv_oxy_value.setText("" + mCurrentInfo.getBoValue());
        tv_time.setText(TimeUtils.getTimeByyyyyMMddhhmmss(mCurrentInfo.getTimestamp()));
        //oxy_barview.setProgress(mCurrentInfo.getBoValue());
    }

    private void setDataFromLocal() {
        mCurrentInfo = mW81DeviceDataModelImp.getOxygenLastData(AppConfiguration.braceletID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
        if (lastTimestamp == mCurrentInfo.getTimestamp().longValue()) {
            return;
        }
        lastTimestamp = mCurrentInfo.getTimestamp().longValue();
        setData();
        if (mCurrentInfo != null) {

            DrawRecDataBean bean = new DrawRecDataBean();
            bean.setStrdate(TimeUtils.getTimeByyyyyMMddhhmmss(mCurrentInfo.getTimestamp()));
            bean.setValue(mCurrentInfo.getBoValue());

            if (mCurrentInfo.getBoValue() > 95) {
                bean.setColors(UIUtils.getColor(R.color.common_view_color));
            } else {
                bean.setColors(UIUtils.getColor(R.color.oxyen_error));

            }
            trendview_oxy.setLocalData(bean);
        }
    }

    @Override
    protected OxyHistoryPresenter createPresenter() {
        return new OxyHistoryPresenter();
    }

    @Override
    public void getOxyHistoryDataSuccess(List<OxyInfo> info) {
        // ArrayList<Integer> valuse = new ArrayList<>();
        //  ArrayList<String> dates = new ArrayList<>();

        ArrayList<DrawRecDataBean> list = new ArrayList<>();
        DrawRecDataBean recDataBean;

        try {
            if (info != null && info.size() > 0) {
                for (int i = 0; i < info.size(); i++) {
                    Integer value = 0;
                    value = info.get(i).getBoValue();
                    recDataBean = new DrawRecDataBean();
                    recDataBean.setValue(value);
                    recDataBean.setStrdate(TimeUtils.getTimeByyyyyMMddhhmmss(info.get(i).getTimestamp()));
                    if (value > 95) {
                        recDataBean.setColors(UIUtils.getColor(R.color.common_view_color));
                    } else {
                        recDataBean.setColors(UIUtils.getColor(R.color.oxyen_error));

                    }
                    list.add(recDataBean);

                }
                lastTimestamp = info.get(0).getTimestamp();
            }

        } catch (Exception e) {

        } finally {
            if (list.size() > 0) {
                trendview_oxy.setdata(list, JkConfiguration.BODY_OXYGEN);
                mCurrentInfo = info.get(0);
                setData();
            } else {
                trendview_oxy.setDeviceType(JkConfiguration.BODY_OXYGEN);
                tv_percent.setVisibility(View.GONE);
                tv_oxy_value.setText("--");
                tv_time.setText("--");
            }


        }

    }

    private BleReciveListener mBleReciveListener = new BleReciveListener() {

        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {

        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            if (mResult != null)
                switch (mResult.getType()) {
                    //设备测量结果成功
                    case IResult.DEVICE_MESSURE:
                        try {
                            DeviceMessureDataResult deviceMessureDataResult = (DeviceMessureDataResult) mResult;
                            switch (deviceMessureDataResult.getMessureType()) {
                                case DeviceMessureData.measure_bloodpre:

                                    Logger.myLog("measure_bloodpre success");
                                    break;
                                case DeviceMessureData.measure_oxygen:
                                    isMeasure = false;
                                    btn_measure.setText(R.string.start_measure);
                                    setDataFromLocal();
                                    Logger.myLog("measure_oxygen success");
                                    break;
                            }
                        } catch (Exception e) {

                        }

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
    protected void initImmersionBar() {
        // super.initImmersionBar();
        //  ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(UIUtils.getColor(R.color.common_bg)).init();
        ImmersionBar.with(this).statusBarDarkFont(true).init();
    }
}
