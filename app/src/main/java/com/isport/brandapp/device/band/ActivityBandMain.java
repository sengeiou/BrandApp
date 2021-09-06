package com.isport.brandapp.device.band;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.watch.WatchRealTimeResult;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.FormatUtils;
import com.isport.brandapp.Home.bean.db.WatchSportMainData;
import com.isport.brandapp.Home.bean.http.Wristbandstep;
import com.isport.brandapp.Home.view.circlebar.BandCirclebarAnimatorLayout;
import com.isport.brandapp.R;
import com.isport.brandapp.device.history.ActivityHistory;
import com.isport.brandapp.device.share.NewShareActivity;
import com.isport.brandapp.device.share.ShareBean;
import com.isport.brandapp.util.StringFomateUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.BebasNeueTextView;
import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class ActivityBandMain extends BaseTitleActivity implements View.OnClickListener {

    private TextView tvTime;
    private BebasNeueTextView tvDis, tvCal, tvDisAnalyze, tvCalAnalyze;
    private BandCirclebarAnimatorLayout circlebarAnimatorLayout;
    BebasNeueTextView tvGoal;


    private long time;
    private String dis;
    private String disAnayze;
    private String cal;
    private String calAnayze;
    private int steps;

    private String rate = "0";
    private Wristbandstep mWristbandstep;
    private WatchSportMainData mWatchSportMainData;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_band_main;
    }

    @Override
    protected void initView(View view) {
        tvTime = view.findViewById(R.id.tv_update_time);
        tvDis = view.findViewById(R.id.tv_dis);
        tvCal = view.findViewById(R.id.tv_cal);
        tvDisAnalyze = view.findViewById(R.id.tv_dis_analyze);
        tvCalAnalyze = view.findViewById(R.id.tv_cal_analyze);
        tvGoal = findViewById(R.id.tv_goal);
        circlebarAnimatorLayout = view.findViewById(R.id.layout_data_head);
    }

    @Override
    protected void initData() {
        // TODO: 2018/11/5 查询历史
        String name = getIntent().getStringExtra("name");
        mWristbandstep = (Wristbandstep) getIntent().getSerializableExtra("mWristbandstep");
        mWatchSportMainData = (WatchSportMainData) getIntent().getSerializableExtra("mWatchSportMainData");
        titleBarView.setTitle(getString(R.string.last_step_recor));
        titleBarView.setLeftIconEnable(true);
        titleBarView.setRightIcon(R.drawable.icon_device_share);
        titleBarView.setHistrotyIcon(R.drawable.icon_band_history);
        dis = "0";
        disAnayze = "0";
        cal = "0";
        calAnayze = "0";
        steps = 0;
        time = 0;
        rate = "0";
        if (mWristbandstep != null) {
            time = mWristbandstep.getLastServerTime();
            steps = Integer.parseInt(mWristbandstep.getStepNum());
            cal = mWristbandstep.getCalorie();
            dis = CommonDateUtil.formatTwoPoint(Float.valueOf(mWristbandstep.getStepKm()));
            calAnayze = Math.round(Float.valueOf(mWristbandstep.getCalorie()) * 0.127) + "";
            disAnayze = CommonDateUtil.formatTwoPoint(Float.valueOf(mWristbandstep.getStepKm()) * 0.0826);
            rate = Math.round((steps / JkConfiguration.WATCH_GOAL) * 100) + "";
        }
        if (mWatchSportMainData!=null){
            time = mWatchSportMainData.getLastSyncTime();
            steps = Integer.parseInt(mWatchSportMainData.getStep());
            cal = mWatchSportMainData.getCal();
            dis = CommonDateUtil.formatTwoPoint(Float.valueOf(mWatchSportMainData.getDistance()));
            calAnayze = Math.round(Float.valueOf(mWatchSportMainData.getCal()) * 0.127) + "";
            disAnayze = CommonDateUtil.formatTwoPoint(Float.valueOf(mWatchSportMainData.getDistance()) * 0.0826);
            rate = Math.round((steps / JkConfiguration.WATCH_GOAL) * 100) + "";
        }
        refleshData();
    }

    private void refleshData() {
        if (DateUtils.isToday(DateUtils.getYMD(time))) {
            tvTime.setText(time == 0 ? "" : DateUtils.getMDHMAMTime(time));
        } else {
            tvTime.setText(time == 0 ? "" : DateUtils.getMDHMAMTime(time));
        }
        StringFomateUtil.formatText(StringFomateUtil.FormatType.BandAnayzeSinle, context, tvCal, ContextCompat.getColor
                (context, R.color.common_white), R.string.app_band_cal, cal, "0.7");
        StringFomateUtil.formatText(StringFomateUtil.FormatType.BandAnayze, context, tvCalAnalyze, ContextCompat
                .getColor(context, R.color.common_tips_color), R.string.burned_title, calAnayze, "0.75");
        StringFomateUtil.formatText(StringFomateUtil.FormatType.BandAnayzeSinle, context, tvDis, ContextCompat.getColor
                (context, R.color.common_white), R.string.app_band_dis, dis, "0.7");
        StringFomateUtil.formatText(StringFomateUtil.FormatType.BandAnayze, context, tvDisAnalyze, ContextCompat
                .getColor(context, R.color.common_tips_color), R.string.saved_title, disAnayze, "0.75");
        circlebarAnimatorLayout.setGoalValue(steps, JkConfiguration.DeviceType.WATCH_W516, "");
        StringFomateUtil.formatText(StringFomateUtil.FormatType.BandAnayze, context, tvGoal, ContextCompat.getColor
                (context, R.color.common_white), R.string.app_band_achieve_goal, rate, "0.7");
    }

    @Override
    protected void initEvent() {
        ISportAgent.getInstance().registerListener(mBleReciveListener);
        titleBarView.setOnHistoryClickListener(new TitleBarView.OnHistoryClickListener() {
            @Override
            public void onHistoryClicked(View view) {
                //super.onHistoryClicked(view);
                Intent intent = new Intent(context, ActivityHistory.class);
                intent.putExtra(JkConfiguration.FROM_TYPE, JkConfiguration.DeviceType.WATCH_W516);
                startActivity(intent);
                //ToastUtils.showToast(context, "点击了历史条目");
            }
        });
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {
                Intent intent = new Intent(context, NewShareActivity.class);
//                Intent intent = new Intent(context, ShareActivity.class);
                //  currentShareDeviceType = getIntent().getIntExtra(JkConfiguration.FROM_TYPE, JkConfiguration
                // .IDeviceType.WATCH_W516);
                //        shareBean = getIntent().getParcelableExtra(JkConfiguration.FROM_BEAN);
                intent.putExtra(JkConfiguration.FROM_TYPE, JkConfiguration.DeviceType.WATCH_W516);
                ShareBean shareBean = new ShareBean();
                //目标比例 设置的目标值与当前值的比列
                shareBean.one = Math.round((steps / JkConfiguration.WATCH_GOAL) * 100) + "";
                //里程
                shareBean.two = dis + "";
                //消耗
                shareBean.three = cal + "";
                //今日步数
                shareBean.centerValue = steps + "";
                //时间
                shareBean.time = time == 0 ? DateUtils.getYMD(System.currentTimeMillis()) : DateUtils.getYMD(time);
                intent.putExtra(JkConfiguration.FROM_BEAN, shareBean);
                startActivity(intent);
                // ToastUtil.showTextToast(context, "点击了分享页面");
            }
        });
    }

    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {
            if (isConn) {

            } else {
                //titleBarView.setTitle(getString(R.string.app_band_today));
                // TODO: 2018/11/5 提醒用户设备已经断开，去主页连接

            }
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            if (mResult != null)
                switch (mResult.getType()) {
                    case IResult.WATCH_REALTIME:
                        WatchRealTimeResult mResult2 = (WatchRealTimeResult) mResult;
                        // TODO: 2018/11/5 存储实时数据到本地,差一个目标值
                        time = System.currentTimeMillis();
                        steps = mResult2.getStepNum();
                        cal = mResult2.getCal() + "";
                        calAnayze = Math.round(mResult2.getCal() * 0.127) + "";
                        float fdis = 0f, fCol = 0f;
                        try {
                            fdis = Float.valueOf(mResult2.getStepKm());
                        } catch (Exception e) {
                            fdis = 0f;
                        } finally {
                            dis = CommonDateUtil.formatTwoPoint(fdis);
                            try {
                                fCol = Float.valueOf(mResult2.getStepKm());
                            } catch (Exception e) {
                                fCol = 0f;
                            } finally {
                                disAnayze = FormatUtils.formatTwoPointReturnFloat(fCol * 0.0826) + "";
                                rate = Math.round((steps / JkConfiguration.WATCH_GOAL) * 100) + "";
                                refleshData();
                            }
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
        ImmersionBar.with(this).statusBarDarkFont(true)
                .init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            default:
                break;
        }
    }
}
