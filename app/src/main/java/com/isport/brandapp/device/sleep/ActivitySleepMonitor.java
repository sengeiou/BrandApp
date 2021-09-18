package com.isport.brandapp.device.sleep;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.action.sleep.Sleep_Sleepace_DataModelAction;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_DataModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.sleep.SleepCollectionStatusResult;
import com.isport.blelibrary.result.impl.sleep.SleepHistoryDataResult;
import com.isport.blelibrary.result.impl.sleep.SleepHistoryDataResultList;
import com.isport.blelibrary.result.impl.sleep.SleepRealTimeDataResult;
import com.isport.blelibrary.result.impl.sleep.SleepStartCollectionResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.home.bean.http.SleepBel;
import com.isport.brandapp.R;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.sleep.bean.SleepUpdateBean;
import com.isport.brandapp.device.sleep.bean.UpdateDataSleep;
import com.isport.brandapp.device.sleep.presenter.SleepUpdatePresenter;
import com.isport.brandapp.device.sleep.view.SleepUpdateView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.StringFomateUtil;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import bike.gymproject.viewlibray.RoundProgressBar;
import bike.gymproject.viewlibray.SleepMonitorView;
import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.action.UserInformationBeanAction;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

import com.isport.blelibrary.utils.CommonDateUtil;

import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.NetUtils;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.entry.UserInformationBean;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class ActivitySleepMonitor extends BaseMVPTitleActivity<SleepUpdateView, SleepUpdatePresenter>
        implements SleepUpdateView, View.OnClickListener {
    private static final int CONNECT_TIMEOUT = 15000;
    TextView tvSleepTime, tvSleepRate, tvAwake, tvStatus;
    private SleepMonitorView mAvgBmp;
    private SleepMonitorView mAvgBreath;
    private String name;
    private SleepBel mSleepBel;
    private BaseDevice mCurrentDevice;
    private boolean isWakeUping;
    private boolean isFrist = true;
    private long reConnectTime;
    private long mStartConnectTime;
    private Sleep_Sleepace_DataModel mSleep_Sleepace_DataModel;
    private RoundProgressBar rundprogressbar;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_sleep_monitor;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mActPresenter.scan(JkConfiguration.DeviceType.SLEEP);
                    ISportAgent.getInstance().connect(mCurrentDevice, false, true);
                    break;
                case 1:
                    wakeUp();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initView(View view) {
        mAvgBmp = view.findViewById(R.id.avg_bmp);
        mAvgBreath = view.findViewById(R.id.avg_breath);
        tvAwake = view.findViewById(R.id.tv_awake);
        tvSleepTime = view.findViewById(R.id.tv_sleep_time);
        tvSleepRate = view.findViewById(R.id.tv_sleep_rate);
        rundprogressbar = view.findViewById(R.id.rundprogressbar);
        tvStatus = view.findViewById(R.id.tv_status);
    }

    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        AppConfiguration.isSleepRealTime = true;
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle("");
        tvStatus.setText(getResources().getString(R.string.sleep_measuring));
        titleBarView.setRightText("");
        name = getIntent().getStringExtra("name");
        mCurrentDevice = (BaseDevice) getIntent().getSerializableExtra("mCurrentDevice");
        mSleepBel = (SleepBel) getIntent().getSerializableExtra("mSleepBel");
        mSleep_Sleepace_DataModel = (Sleep_Sleepace_DataModel) getIntent().getSerializableExtra
                ("mSleep_Sleepace_DataModel");

//        Logger.myLog(mSleep_Sleepace_DataModel.toString());


        if (mSleep_Sleepace_DataModel != null) {
            int sleepDuration = mSleep_Sleepace_DataModel.getSleepDuration();
            int deepSleepAllTime = mSleep_Sleepace_DataModel.getDeepSleepAllTime();

            StringFomateUtil.formatText(StringFomateUtil.FormatType.Time, this, tvSleepTime, ContextCompat.getColor
                    (this, R.color.common_white), R.string.app_time_util, sleepDuration / 60 + "", sleepDuration % 60 + "");


            //tvSleepTime.setText(deepSleepAllTime / 60 + "H" + deepSleepAllTime % 60 + "M");
            String pre = "0";
            if (deepSleepAllTime != 0 && sleepDuration != 0) {
                pre = CommonDateUtil.formatOnePoint(1.0 * deepSleepAllTime / sleepDuration * 100);
                rundprogressbar.setProgress((int) (1.0 * deepSleepAllTime / sleepDuration * 100));
            }

            tvSleepRate.setText(pre + "%");
        }
        if (mSleepBel != null) {
            String duration = mSleepBel.getDuration();
            if (!"--".equals(duration)) {
                int sleepTime = Integer.parseInt(duration);
                //tvSleepTime.setText(sleepTime / 60 + "H" + sleepTime % 60 + "M");


                StringFomateUtil.formatText(StringFomateUtil.FormatType.Time, this, tvSleepTime, ContextCompat.getColor
                        (this, R.color.common_white), R.string.app_time_util, sleepTime / 60 + "", sleepTime % 60 + "");

                /**
                 * 睡眠带上取的数据取的短数据百分比有误
                 */
                int deepTime = 0;
                try {
                    deepTime = Integer.parseInt(mSleepBel.getDeepSleepAllTime());
                } catch (Exception e) {
                    deepTime = 0;
                } finally {
                    String pre = "0";
                    if (deepTime != 0 && sleepTime != 0) {
                        pre = CommonDateUtil.formatOnePoint(1.0 * deepTime / sleepTime * 100);
                        rundprogressbar.setProgress((int) (1.0 * deepTime / sleepTime * 100));
                    }
                    tvSleepRate.setText(pre + "%");

                }
            }
        }
    }


    @Override
    protected void initEvent() {
        tvAwake.setOnClickListener(this);
        ISportAgent.getInstance().registerListener(mBleReciveListener);
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
                /*if (TextUtils.isEmpty(from)) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("isLogout", true);
                    startActivity(intent);
                    ActivityManager.getInstance().finishAllActivity(MainActivity.class.getSimpleName());

                } else {
                    finish();
                }
*/
            }

            @Override
            public void onRightClicked(View view) {

            }
        });
        ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_getCollectionStatus);
    }

    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {
            if (isConn) {
                Logger.myLog("睡眠监测连接成功,移除监听");
//                if (mHandler.hasMessages(0x01)) {
//                    mHandler.removeMessages(0x01);
//                }
                if (mHandler.hasMessages(0x00)) {
                    mHandler.removeMessages(0x00);
                }
                //接着监测
                Logger.myLog("睡眠监测重连上，继续监测");
                ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_getCollectionStatus);
                reConnectTime = System.currentTimeMillis();
                //titleBarView.setTitle(getResources().getString(R.string.sleep_measuring));
                tvStatus.setText(getResources().getString(R.string.sleep_measuring));
                isFrist = true;
                reConnectTime = 0;
            } else {
                Logger.myLog("睡眠监测断开连接去重连");
                // TODO: 2018/11/12  在监听页面，如果设备断连，则一直去重连，且取消离床15分钟逻辑
                if (isWakeUping) {
                    //正在上传数据中，断连的情况
                    Logger.myLog("正在上传数据端口连接");
                    return;
                }
//                if (mHandler.hasMessages(0x01)) {
//                    mHandler.removeMessages(0x01);
//                }
                //titleBarView.setTitle(UIUtils.getString(R.string.app_device_disconnected));
                tvStatus.setText(getResources().getString(R.string.app_device_disconnected));
                if (isFrist) {
                    Logger.myLog("第一次断连");
                    isFrist = false;
                    reConnectTime = 0;
                    mStartConnectTime = System.currentTimeMillis();
                    mHandler.sendEmptyMessageDelayed(0x00, 10000);
//                    mHandler.sendEmptyMessageDelayed(0x02,CONNECT_TIMEOUT);
                } else {
                    Logger.myLog("第N次断连");
                    reConnectTime = System.currentTimeMillis() - mStartConnectTime;
                }
                if (reConnectTime > 15 * 60 * 1000) {
                    //  if (reConnectTime > 20 * 1000) {
                    //重连时间大于10分钟，直接结束页面
                    mActPresenter.cancelScan();
                    Logger.myLog("重连大于10分钟,显示已断开 " + reConnectTime);
                    ToastUtils.showToast(context, R.string.app_disconnected);
                } else {
                    Logger.myLog("重连小于10分钟,继续重连 " + reConnectTime);
                    mActPresenter.cancelScan();
                    mHandler.sendEmptyMessageDelayed(0x00, 10000);
                }
            }
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            if (mResult != null)
                switch (mResult.getType()) {
                    case IResult.SLEEP_COLLECTIONSTATUS:
                        SleepCollectionStatusResult sleepCollectionStatusResult = (SleepCollectionStatusResult) mResult;
                        int aByte = (int) sleepCollectionStatusResult.getByte();
                        if (aByte == 0) {
                            //空闲状态去发送开始监听指令
                            ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_setCollectionEnable, true);
                        } else if (aByte == 1) {
                            //不是空闲状态直接监听就行
                            ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_setRealTimeEnable, true);
                        }
                        break;
                    case IResult.SLEEP_STARTCOLLECTION:
                        SleepStartCollectionResult startCollectionResult = (SleepStartCollectionResult) mResult;
                        if (startCollectionResult.isEnable()) {
                            //重新去记录开始的睡眠时间
                            //打开成功，去发送开启实时指令
                            ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_setRealTimeEnable, true);
                        }
                        break;
                    case IResult.SLEEP_REALTIME:
                        SleepRealTimeDataResult sleepRealTimeDataResult = (SleepRealTimeDataResult) mResult;
                        byte status = sleepRealTimeDataResult.getStatus();
                        String statusStr = null;
                        if ((int) status == 5) {
                            if (!mHandler.hasMessages(0x01)) {
                                //15分钟
                                Logger.myLog("离床状态下，10s去同步数据或者离开页面");
                                mHandler.sendEmptyMessageDelayed(0x01, 900000);
//                                mHandler.sendEmptyMessageDelayed(0x01, 10000);
                            }
                        } else {
                            if (mHandler.hasMessages(0x01)) {
                                mHandler.removeMessages(0x01);
                            }
                        }
                        switch ((int) status) {
                            case 0:
                                statusStr = UIUtils.getString(R.string.normal);
                                break;
                            case 1:
                                statusStr = UIUtils.getString(R.string.heart_rate);
                                break;
                            case 2:
                                statusStr = UIUtils.getString(R.string.apnea);
                                break;
                            case 3:
                                statusStr = UIUtils.getString(R.string.heart_pause);
                                break;
                            case 4:
                                statusStr = UIUtils.getString(R.string.body_moving);
                                break;
                            case 5:
                                statusStr = UIUtils.getString(R.string.leave_bed);
                                break;
                            case 6:
                                statusStr = UIUtils.getString(R.string.turn_over);
                                break;
                            case 7:
                                statusStr = UIUtils.getString(R.string.app_bodymove_temp);
                                break;
                            case -1:
                                statusStr = UIUtils.getString(R.string.app_invalid);
                                break;
                            default:
                                break;
                        }
                        //titleBarView.setTitle(statusStr);
                        tvStatus.setText(statusStr);
                        mAvgBmp.setValueText(sleepRealTimeDataResult.getHeartRate() + "");
                        mAvgBreath.setValueText(sleepRealTimeDataResult.getBreathRate() + "");
                        break;
                    case IResult.SLEEP_HISTORYDATA:
                        SleepHistoryDataResultList sleepHistoryDataResultList = (SleepHistoryDataResultList) mResult;
                        //存储到本地，到历史页面,上传数据
                        ArrayList<SleepHistoryDataResult> sleepHistoryDataResults = sleepHistoryDataResultList
                                .getSleepHistoryDataResults();

                        if (sleepHistoryDataResults == null || !(App.appType() == App.httpType)) {
                            Logger.myLog("历史数据回调，无数据,或者单机版本");
//                            updateSleepHistoryDataSuccess(null, null);
                            List<Sleep_Sleepace_DataModel> all = Sleep_Sleepace_DataModelAction.getAll();
                            if (all != null)
                                for (int i = 0; i < all.size(); i++) {
                                    Logger.myLog("sleep 历史数据 == " + all.get(i).toString());
                                }
                            updateSuccess(null);
                            Sleep_Sleepace_DataModel sleep_sleepace_dataModelByDeviceId =
                                    Sleep_Sleepace_DataModelAction.findSleep_Sleepace_DataModelByDeviceId
                                            (TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                            if (sleep_sleepace_dataModelByDeviceId != null) {
                                Logger.myLog("历史数据回调，有数据去上传 == " + sleep_sleepace_dataModelByDeviceId.toString());
                                EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_SLEEP_DATA_SUCCESS));
                            }
                        } else {
                            //网络版，如果没有网络的情况也是不会上传的，直接展示本地
                            if (NetUtils.hasNetwork()) {
                                mActPresenter.updateSleepHistoryData();
                            } else {
                                updateSuccess(null);
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
    protected SleepUpdatePresenter createPresenter() {
        return new SleepUpdatePresenter(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AppConfiguration.isSleepRealTime = false;
        mHandler.removeCallbacksAndMessages(null);
        ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_setRealTimeEnable, false);
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }

    @Override
    protected void initHeader() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_awake:
                wakeUp();
                break;
            default:
                break;
        }
    }

    long creatTime;

    private void wakeUp() {
        Logger.myLog("起床去同步数据，离开页面");
        if (!AppConfiguration.isConnected) {
            finish();
            return;
        }
        isWakeUping = true;
        ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_setCollectionEnable, false);


        //如果不足十分钟就直接退出。否则就是需要去同步历史数据


        //同步历史睡眠历史数据，然后上传到服务器
        creatTime = App.getSleepBindTime();
        if (mSleep_Sleepace_DataModel != null) {
            creatTime = mSleep_Sleepace_DataModel.getTimestamp() * 1000;
        }
        //同步最后时间点的数据到当前数据
//                (int) (creatTime / 1000)
        UserInfoBean userInfo = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(this));
        if (App.appType() == App.httpType) {
            ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_historyDownload, (int) (creatTime / 1000),
                    (int) DateUtils
                            .getCurrentTimeUnixLong(), userInfo.getGender().equals
                            ("Male") ?
                            1 : 0);
        } else {
            UserInformationBean userInfoByUserId = UserInformationBeanAction.findUserInfoByUserId
                    ("0");
            Logger.myLog("userInfoByUserId = " + userInfoByUserId.toString());
            ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_historyDownload, (int) (creatTime / 1000),
                    (int) DateUtils
                            .getCurrentTimeUnixLong(), userInfoByUserId
                            .getGender().equals("Male") ? 0 : 1);
        }
//                                 ISportAgent.getInstance().historyDownload((int) 0, (int) DateUtils
// .getCurrentTimeUnixLong(), 1);
        NetProgressObservable.getInstance().show(UIUtils.getString(R.string.app_syncing),
                false);
    }

    @Override
    public void updateSuccess(UpdateSuccessBean bean) {
        // TODO: 2018/11/8 不足十分钟或者没有数据的逻辑
        NetProgressObservable.getInstance().hide();
        // TODO: 2019/1/16 单机版本查询最近一条记录，和当前记录对比
        isWakeUping = false;
        if (!(App.appType() == App.httpType)) {
            Sleep_Sleepace_DataModel sleep_sleepace_dataModelByDeviceId = Sleep_Sleepace_DataModelAction
                    .findSleep_Sleepace_DataModelByDeviceId(TokenUtil.getInstance().getPeopleIdStr
                                    (BaseApp.getApp()));
            if (sleep_sleepace_dataModelByDeviceId == null) {
                ToastUtils.showToastLong(context, UIUtils.getString(R.string.app_not_history_data));
            } else {
                if (mSleep_Sleepace_DataModel == null) {
                    //主页没有数据的情况
                    Intent intentSleep = new Intent(context, ActivitySleepMain.class);
                    intentSleep.putExtra("mSleep_Sleepace_DataModel", sleep_sleepace_dataModelByDeviceId);
                    intentSleep.putExtra("lastTime", sleep_sleepace_dataModelByDeviceId.getTimestamp());
                    startActivity(intentSleep);
                } else {
                    if (!sleep_sleepace_dataModelByDeviceId.getDateStr().equals(mSleep_Sleepace_DataModel.getDateStr())) {
                        //有新数据的情况
                        Intent intentSleep = new Intent(context, ActivitySleepMain.class);
                        intentSleep.putExtra("mSleep_Sleepace_DataModel", sleep_sleepace_dataModelByDeviceId);
                        intentSleep.putExtra("lastTime", sleep_sleepace_dataModelByDeviceId.getTimestamp());
                        startActivity(intentSleep);
                    } else {
                        ToastUtils.showToastLong(context, UIUtils.getString(R.string.app_nolong_history_data));
                    }
                }
            }
        } else {
            if (bean == null) {
                ToastUtils.showToastLong(context, UIUtils.getString(R.string.app_not_history_data));
            } else {
                Sleep_Sleepace_DataModel sleep_sleepace_dataModelByDeviceId = Sleep_Sleepace_DataModelAction
                        .findSleep_Sleepace_DataModelByDeviceId(TokenUtil.getInstance().getPeopleIdStr
                                (BaseApp.getApp()));
                if (sleep_sleepace_dataModelByDeviceId == null) {
                    ToastUtils.showToastLong(context, UIUtils.getString(R.string.app_not_history_data));
                } else {
                    if (mSleep_Sleepace_DataModel == null) {
                        //主页没有数据的情况
                        Intent intentSleep = new Intent(context, ActivitySleepMain.class);
                        intentSleep.putExtra("mSleep_Sleepace_DataModel", sleep_sleepace_dataModelByDeviceId);
                        intentSleep.putExtra("lastTime", sleep_sleepace_dataModelByDeviceId.getTimestamp());
                        startActivity(intentSleep);
                    } else {
                        if (!sleep_sleepace_dataModelByDeviceId.getDateStr().equals(mSleep_Sleepace_DataModel
                                .getDateStr())) {
                            //有新数据的情况
                            Intent intentSleep = new Intent(context, ActivitySleepMain.class);
                            intentSleep.putExtra("mSleep_Sleepace_DataModel", sleep_sleepace_dataModelByDeviceId);
                            intentSleep.putExtra("lastTime", sleep_sleepace_dataModelByDeviceId.getTimestamp());
                            startActivity(intentSleep);
                        } else {
                            ToastUtils.showToastLong(context, UIUtils.getString(R.string.app_nolong_history_data));
                        }
                    }
                }
            }
        }
        finish();
    }


    private SleepUpdateBean sleepUpdateBean;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UpdateDataSleep messageEvent) {
        sleepUpdateBean = messageEvent.getSleepUpdateBean();
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
