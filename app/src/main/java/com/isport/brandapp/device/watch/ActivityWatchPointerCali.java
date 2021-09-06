package com.isport.brandapp.device.watch;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class ActivityWatchPointerCali extends BaseTitleActivity implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {
    private static final int TIME = 50;

    ImageView hourUp, hourDown, minUp, minDown;
    TextView caliConfirm;

    int ccwHourClick;
    int cwHourClick;
    int ccwHourLongClick;
    int cwHourLongClick;

    int ccwMinClick;
    int cwMinClick;
    int ccwMinLongClick;
    int cwMinLongClick;

    private int longCount;

    private boolean canTunch;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_watch_pointer_cali;
    }

    @Override
    protected void initView(View view) {
        if (view == null) {
            return;
        }

        hourUp = view.findViewById(R.id.iv_hour_up);
        hourDown = view.findViewById(R.id.iv_hour_down);

        minDown = view.findViewById(R.id.iv_min_down);
        minUp = view.findViewById(R.id.iv_min_up);

        caliConfirm = view.findViewById(R.id.tv_cali_confirm);
    }

    @Override
    protected void initData() {
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(getResources().getString(R.string.watch_sleep_pointer_title));
        titleBarView.setRightText("");

        //默认展示

        ccwHourClick = -1;
        cwHourClick = -1;
        ccwHourLongClick = -1;
        cwHourLongClick = -1;

        ccwMinClick = -1;
        cwMinClick = -1;
        ccwMinLongClick = -1;
        cwMinLongClick = -1;

        longCount = 0;// >0 为长按  =0 单击

    }


//    private void checkInMode(){
//        if (!canTunch){
//            showToast(UIUtils.getString(R.string.unit_steps));
//        }
//    }

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
        hourUp.setOnClickListener(this);
        hourDown.setOnClickListener(this);

        hourUp.setOnLongClickListener(this);
        hourDown.setOnLongClickListener(this);
        hourUp.setOnTouchListener(this);
        hourDown.setOnTouchListener(this);

        minUp.setOnClickListener(this);
        minDown.setOnClickListener(this);

        minUp.setOnLongClickListener(this);
        minDown.setOnLongClickListener(this);

        minUp.setOnTouchListener(this);
        minDown.setOnTouchListener(this);

        caliConfirm.setOnClickListener(this);

        ISportAgent.getInstance().registerListener(mBleReciveListener);

        ISportAgent.getInstance().requestBle(BleRequest.WATCH_W516_SWITCH_MODE, true);
        NetProgressObservable.getInstance().show(UIUtils.getString(R.string.common_please_wait), false);
        handler.sendEmptyMessageDelayed(0x01, 5000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    NetProgressObservable.getInstance().hide();
                    break;

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ISportAgent.getInstance().requestBle(BleRequest.WATCH_W516_SWITCH_MODE, false);
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }


    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {
            if (isConn) {
            } else {
                NetProgressObservable.getInstance().hide();
            }
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            if (mResult != null)
                switch (mResult.getType()) {
                    case IResult.WATCH_W516_IN_ADJUSTMODE:
                        NetProgressObservable.getInstance().hide();
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

    private void checkConnectState() {
        if (AppConfiguration.isConnected) {
        } else {
            bleHandler.removeCallbacksAndMessages(null);
            ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
            return;
        }
    }

    @Override
    public void onClick(View v) {
        checkConnectState();
        switch (v.getId()) {
            case R.id.iv_hour_up:
                //顺时针
                Log.e("PointerCali", "iv_hour_up");
                // TODO: 2018/5/3 发送单击顺时针调针指令
                if (cwHourClick == -1) {
                    if (longCount > 0) {
                        longCount = 0;
                    } else {
                        Log.e(TAG, "btn_cw单击");
                        clickHour(1);
                        cwHourClick = 0;
                    }
                    // TODO: 2018/5/3 发送单击顺时针调针指令
                }
                break;
            case R.id.iv_hour_down:
                Log.e("PointerCali", "iv_hour_down");
                // TODO: 2018/5/3 发送单击逆时针调针指令
                if (ccwHourClick == -1) {
                    if (longCount > 0) {
                        longCount = 0;
                    } else {
                        Log.e(TAG, "btn_ccw单击");
                        clickHour(-1);
                        ccwHourClick = 0;
                    }
                    // TODO: 2018/5/3 发送单击逆时针调针指令
                }
                break;
            case R.id.iv_min_up:
                Log.e("PointerCali", "iv_min_up");
                // TODO: 2018/5/3 发送单击顺时针调针指令

                if (cwMinClick == -1) {
                    if (longCount > 0) {
                        longCount = 0;
                    } else {
                        Log.e(TAG, "btn_cw单击");
                        clickMin(1);
                        cwMinClick = 0;
                    }
                    // TODO: 2018/5/3 发送单击顺时针调针指令
                }
                break;
            case R.id.iv_min_down:
                Log.e("PointerCali", "iv_min_down");
                // TODO: 2018/5/3 发送单击逆时针调针指令
                clickMin(-1);
                if (ccwHourClick == -1) {
                    if (longCount > 0) {
                        longCount = 0;
                    } else {
                        Log.e(TAG, "btn_ccw单击");
                        clickHour(-1);
                        ccwHourClick = 0;
                    }
                    // TODO: 2018/5/3 发送单击逆时针调针指令
                }
                break;
            case R.id.tv_cali_confirm:
                Log.e("PointerCali", "tv_cali_confirm");
                whenBack();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        checkConnectState();

        switch (view.getId()) {
            case R.id.iv_hour_down:
                if (ccwHourLongClick == -1) {
                    Log.e(TAG, "btn_ccw长按");
                    ccwHourLongClick = 0;
                    longCount++;
                    bleHandler.post(mHourCcwRunnable);
                }
                // TODO: 2018/5/3 发送长按逆时针调针指令
                break;
            case R.id.iv_hour_up:
                if (cwHourLongClick == -1) {
                    Log.e(TAG, "btn_cw长按");
                    cwHourLongClick = 0;
                    longCount++;
                    bleHandler.post(mHourCwRunnable);
                }
                // TODO: 2018/5/3 发送长按顺时针调针指令
                break;
            case R.id.iv_min_down:
                if (ccwMinLongClick == -1) {
                    Log.e(TAG, "btn_ccw长按");
                    ccwMinLongClick = 0;
                    longCount++;
                    bleHandler.post(mMinCcwRunnable);
                }
                // TODO: 2018/5/3 发送长按逆时针调针指令
                break;
            case R.id.iv_min_up:
                if (cwMinLongClick == -1) {
                    Log.e(TAG, "btn_cw长按");
                    cwMinLongClick = 0;
                    longCount++;
                    bleHandler.post(mMinCwRunnable);
                }
                // TODO: 2018/5/3 发送长按顺时针调针指令
                break;
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            whenBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void clickHour(int step) {
        ISportAgent.getInstance().requestBle(BleRequest.WATCH_W516_ADJUST, 0, step);
    }


    private void clickMin(int step) {
        ISportAgent.getInstance().requestBle(BleRequest.WATCH_W516_ADJUST, step, 0);
    }


    private void whenBack() {
        if (!AppConfiguration.isConnected) {
            showToast(R.string.unconnected_device);
            finish();
        } else {
            //未动过针的，直接发时间模式-低速模式
            ISportAgent.getInstance().requestBle(BleRequest.WATCH_W516_SWITCH_MODE, false);
            finish();
        }
    }

    private Handler bleHandler = new Handler();

    Runnable mHourCcwRunnable = new Runnable() {
        @Override
        public void run() {
            clickHour(-2);
            bleHandler.postDelayed(this, TIME);
        }
    };

    Runnable mHourCwRunnable = new Runnable() {
        @Override
        public void run() {
            clickHour(2);
            bleHandler.postDelayed(this, TIME);
        }
    };

    Runnable mMinCcwRunnable = new Runnable() {
        @Override
        public void run() {
            clickMin(-2);
            bleHandler.postDelayed(this, TIME);
        }
    };

    Runnable mMinCwRunnable = new Runnable() {
        @Override
        public void run() {
            clickMin(2);
            bleHandler.postDelayed(this, TIME);
        }
    };

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        checkConnectState();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                break;
            }
            case MotionEvent.ACTION_UP: {
                bleHandler.removeCallbacksAndMessages(null);
                switch (view.getId()) {
                    case R.id.iv_hour_down:
//                        if (canTunch) {
                        if (ccwHourLongClick == 0) {
                            // TODO: 2018/5/3 停止逆时针调针
                            ccwHourLongClick = -1;
                            Log.e(TAG, "btn_ccw长按抬起");
                            bleHandler.removeCallbacksAndMessages(null);
//                                bleHandler.removeCallbacks(mHourCcwRunnable);
                            clickHour(-1);
                        }
                        if (ccwHourClick == 0) {
                            Log.e(TAG, "btn_ccw单击抬起");
                            ccwHourClick = -1;
                        }
//                        }
                        break;
                    case R.id.iv_hour_up:
//                        if (canTunch) {
                        if (cwHourLongClick == 0) {
                            // TODO: 2018/5/3 停止顺时针调针
                            cwHourLongClick = -1;
                            Log.e(TAG, "btn_cw长按抬起");
                            bleHandler.removeCallbacksAndMessages(null);
//                                bleHandler.removeCallbacks(mHourCwRunnable);
                            clickHour(1);
                        }
                        if (cwHourClick == 0) {
                            Log.e(TAG, "btn_cw单击抬起");
                            cwHourClick = -1;
                        }
//                        }
                        break;
                    case R.id.iv_min_down:
//                        if (canTunch) {
                        if (ccwMinLongClick == 0) {
                            // TODO: 2018/5/3 停止逆时针调针
                            ccwMinLongClick = -1;
                            Log.e(TAG, "btn_ccw长按抬起");
                            bleHandler.removeCallbacksAndMessages(null);
//                                bleHandler.removeCallbacks(mMinCcwRunnable);
                            clickMin(-1);
                        }
                        if (ccwMinClick == 0) {
                            Log.e(TAG, "btn_ccw单击抬起");
                            ccwMinClick = -1;
                        }
//                        }
                        break;
                    case R.id.iv_min_up:
//                        if (canTunch) {
                        if (cwMinLongClick == 0) {
                            // TODO: 2018/5/3 停止顺时针调针
                            cwMinLongClick = -1;
                            Log.e(TAG, "btn_cw长按抬起");
                            bleHandler.removeCallbacksAndMessages(null);
//                                bleHandler.removeCallbacks(mMinCwRunnable);
                            clickMin(1);
                        }
                        if (cwMinClick == 0) {
                            Log.e(TAG, "btn_cw单击抬起");
                            cwMinClick = -1;
                        }
//                        }
                        break;
                }
                break;
            }
        }
        return false;
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
