package com.isport.brandapp.wu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
import com.isport.brandapp.wu.bean.TempInfo;
import com.isport.brandapp.wu.mvp.TempHistoryView;
import com.isport.brandapp.wu.mvp.presenter.TempHistoryPresenter;
import com.isport.brandapp.wu.view.TempTrendView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;

public class TempResultActivity extends BaseMVPTitleActivity<TempHistoryView, TempHistoryPresenter> implements TempHistoryView {

    private TempInfo mCurrentInfo;
    TempTrendView trendview_temp;
    private TextView tv_bp_time;
    private Button btn_measure;
    private TextView tv_value, tv_unitl, tv_state;
    private ImageView iv_measure_way;
    private ImageView iv_state_norm, iv_state_low, iv_state_high;
    private ArrayList<ImageView> stateList = new ArrayList<>();
    private TextView tv_start_temp1, tv_start_temp2, tv_start_temp3, tv_start_temp4;


    private static String currentTempUtil;

    private boolean isMeasure = false;
    private W81DeviceDataModelImp mW81DeviceDataModelImp;

    private EditText etTemp;
    private Button btnAdd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tempresult;
    }

    @Override
    protected void initView(View view) {

//        setTranslucentStatus(getResources().getColor(R.color.common_view_color));
        tv_start_temp1 = findViewById(R.id.tv_start_temp1);
        tv_start_temp2 = findViewById(R.id.tv_start_temp2);
        tv_start_temp3 = findViewById(R.id.tv_start_temp3);
        tv_start_temp4 = findViewById(R.id.tv_start_temp4);
        iv_state_high = findViewById(R.id.iv_state_high);
        iv_state_low = findViewById(R.id.iv_state_low);
        iv_state_norm = findViewById(R.id.iv_state_norm);
        tv_bp_time = findViewById(R.id.tv_bp_time);
        trendview_temp = findViewById(R.id.trendview_temp);
        btn_measure = findViewById(R.id.btn_measure);
        iv_measure_way = findViewById(R.id.iv_measure_way);
        stateList.add(iv_state_norm);
        stateList.add(iv_state_low);
        stateList.add(iv_state_high);


        tv_value = findViewById(R.id.tv_value);
        tv_unitl = findViewById(R.id.tv_unitl);
        tv_state = findViewById(R.id.tv_state);
        setdefvalue();
        btn_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMeasure) {
                    startMeasure();
                } else {
                    // finishMeasure();
                }
            }
        });
    }


    public void setValue() {
        if (mCurrentInfo != null) {
            if (currentTempUtil.equals("0")) {
                tv_value.setText(mCurrentInfo.getCentigrade());
            } else {
                tv_value.setText(mCurrentInfo.getFahrenheit());
            }
            //iv_bg_temp_state.setImageResource(mCurrentInfo.getResId());
            // tv_state.setTextColor(mCurrentInfo.getColor());
            tv_value.setTextColor(mCurrentInfo.getColor());
            tv_unitl.setTextColor(mCurrentInfo.getColor());
            tv_state.setText(mCurrentInfo.getState());

            for (int i = 0; i < stateList.size(); i++) {
                stateList.get(i).setVisibility(View.INVISIBLE);
            }
            stateList.get(mCurrentInfo.getResId() - 1).setVisibility(View.VISIBLE);
        }
    }

    public void setDefTitle() {
        if (currentTempUtil.equals("0")) {
            tv_start_temp1.setText("35.0" + UIUtils.getString(R.string.temperature_degree_centigrade));
            tv_start_temp2.setText("37.2" + UIUtils.getString(R.string.temperature_degree_centigrade));
            tv_start_temp3.setText("38.0" + UIUtils.getString(R.string.temperature_degree_centigrade));
            tv_start_temp4.setText("42.0" + UIUtils.getString(R.string.temperature_degree_centigrade));
            // view_temp_normal.setData(String.format(UIUtils.getString(R.string.temp_norm_temp_title), "35.0" + UIUtils.getString(R.string.temperature_degree_centigrade), "37.2" + UIUtils.getString(R.string.temperature_degree_centigrade)));
            // view_temp_low_fever.setData(String.format(UIUtils.getString(R.string.temp_low_fever_title), "37.3" + UIUtils.getString(R.string.temperature_degree_centigrade), "38.0" + UIUtils.getString(R.string.temperature_degree_centigrade)));
            //  view_temp_high_fever.setData(String.format(UIUtils.getString(R.string.temp_high_fever_title), "38.1" + UIUtils.getString(R.string.temperature_degree_centigrade)));
        } else {
            tv_start_temp1.setText("35.0" + UIUtils.getString(R.string.temperature_fahrenheit));
            tv_start_temp2.setText("37.2" + UIUtils.getString(R.string.temperature_fahrenheit));
            tv_start_temp3.setText("38.0" + UIUtils.getString(R.string.temperature_fahrenheit));
            tv_start_temp4.setText("42.0" + UIUtils.getString(R.string.temperature_fahrenheit));
            //  view_temp_normal.setData(String.format(UIUtils.getString(R.string.temp_norm_temp_title), CommonDateUtil.formatOnePoint(CommonDateUtil.ctof(35.0f)) + UIUtils.getString(R.string.temperature_fahrenheit), CommonDateUtil.formatOnePoint(CommonDateUtil.ctof(37.2f)) + UIUtils.getString(R.string.temperature_fahrenheit)));
            // view_temp_low_fever.setData(String.format(UIUtils.getString(R.string.temp_low_fever_title), CommonDateUtil.formatOnePoint(CommonDateUtil.ctof(37.3f)) + UIUtils.getString(R.string.temperature_fahrenheit), CommonDateUtil.formatOnePoint(CommonDateUtil.ctof(38.0f)) + UIUtils.getString(R.string.temperature_fahrenheit)));
            // view_temp_high_fever.setData(String.format(UIUtils.getString(R.string.temp_high_fever_title), CommonDateUtil.formatOnePoint(CommonDateUtil.ctof(38.1f)) + UIUtils.getString(R.string.temperature_fahrenheit)));
        }
    }

    @Override
    protected void initHeader() {
        // StatusBarCompat.setStatusBarColor(this, getResources().getColor(com.isport.brandapp.basicres.R.color.white));
    }

    @Override
    protected void initData() {
        mW81DeviceDataModelImp = new W81DeviceDataModelImp();
        //mActPresenter.getTempNumData();
        mActPresenter.getTempUnitl(AppConfiguration.braceletID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
        ISportAgent.getInstance().registerListener(mBleReciveListener);

        titleBarView.setTitle(getResources().getString(R.string.temp));
        titleBarView.setRightIcon(R.drawable.icon_sleep_history);
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {
                Intent intent = new Intent(TempResultActivity.this, TempListActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void initEvent() {
        iv_measure_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempWayDialog tempWayDialog = new TempWayDialog(TempResultActivity.this);
                tempWayDialog.showDialog();
              /*  Intent intent = new Intent(TempResultActivity.this, TempMeasureWayActivity.class);
                startActivity(intent);*/
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0x01);
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }

    private void setData() {
        tv_bp_time.setText(TimeUtils.getTimeByyyyyMMddhhmmss(mCurrentInfo.getTimestamp()));
        setValue();


        /*bp_barview.setProgress(mCurrentInfo.getSpValue(), mCurrentInfo.getDpValue());
        tv_bp_sys.setText("" + mCurrentInfo.getSpValue());
        tv_bp_dias.setText("" + mCurrentInfo.getDpValue());*/
    }


    private void setDataFromLocal() {
        mActPresenter.getLastTempData(AppConfiguration.braceletID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    isMeasure = false;
                    btn_measure.setText(R.string.start_measure);
                    break;
            }
        }
    };

    private void startMeasure() {
        if (AppConfiguration.isConnected) {
            handler.sendEmptyMessageDelayed(0x01, 5000);
            isMeasure = true;
            btn_measure.setText(R.string.measureing);
            ISportAgent.getInstance().requestBle(BleRequest.MEASURE_TEMP, true);
            // ActivitySwitcher.goMeasureActivty(this, JkConfiguration.DeviceMeasureType.blood);
        } else {
            Toast.makeText(this, getString(R.string.app_disconnect_device), Toast.LENGTH_LONG).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.measure_end:
                handler.removeMessages(0x01);
                isMeasure = false;
                btn_measure.setText(R.string.start_measure);
                //setDataFromLocal();
                break;
        }
    }


    private void finishMeasure() {
        if (AppConfiguration.isConnected) {
            ISportAgent.getInstance().requsetW81Ble(BleRequest.DEVICE_MEASURE_BLOODPRE, false);
            isMeasure = false;
            btn_measure.setText(R.string.start_measure);
        } else {
            Toast.makeText(this, getString(R.string.app_disconnect_device), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected TempHistoryPresenter createPresenter() {
        return new TempHistoryPresenter();
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
                                    isMeasure = false;
                                    btn_measure.setText(R.string.start_measure);
                                    setDataFromLocal();
                                    Logger.myLog("measure_bloodpre success");
                                    break;
                                case DeviceMessureData.measure_oxygen:
                                    Logger.myLog("measure_oxygen success");
                                    break;
                                case DeviceMessureData.today_temp:
                                    handler.removeMessages(0x01);
                                    isMeasure = false;
                                    btn_measure.setText(R.string.start_measure);
                                    setDataFromLocal();
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
    public void getTempHistorySuccess(List<TempInfo> info) {
        if (info != null && info.size() > 0) {
            trendview_temp.setData(info, currentTempUtil);
            mCurrentInfo = info.get(0);
            setData();
        } else {
            tv_bp_time.setText("--");
            //tv_bp_sys.setText("--");
            //tv_bp_dias.setText("--");
        }
    }

    @Override
    public void getLastTempSuccess(TempInfo info) {
        mCurrentInfo = mW81DeviceDataModelImp.getTempInfoeLastData(AppConfiguration.braceletID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
        setData();
        trendview_temp.setLocalData(mCurrentInfo, currentTempUtil);
    }

    @Override
    public void getTempUtil(String unitl) {
        currentTempUtil = unitl;
        if (unitl.equals("0")) {
            tv_unitl.setText(UIUtils.getString(R.string.temperature_degree_centigrade));
        } else {
            tv_unitl.setText(UIUtils.getString(R.string.temperature_fahrenheit));
        }
        setDefTitle();
        mActPresenter.getTempNumberHistory(AppConfiguration.braceletID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), 7);
    }


    public void setdefvalue() {
        tv_state.setText(UIUtils.getString(R.string.no_data));
        tv_value.setText(UIUtils.getString(R.string.no_data));
        tv_bp_time.setText(UIUtils.getString(R.string.no_data));
        tv_unitl.setText(UIUtils.getString(R.string.no_data));

    }
}
