package com.isport.brandapp.device.scale;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.scale.ScaleCalculateResult;
import com.isport.blelibrary.result.impl.scale.ScaleLockDataResult;
import com.isport.blelibrary.result.impl.scale.ScaleUnLockDataResult;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.bind.presenter.ScaleScanPresenter;
import com.isport.brandapp.bind.view.ScaleScanView;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.scale.presenter.UpdateReportPresenter;
import com.isport.brandapp.device.scale.view.UpdateReportView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import bike.gymproject.viewlibray.WaveView;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.action.BaseAction;
import brandapp.isport.com.basicres.action.UserInformationBeanAction;
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionManageUtil;
import brandapp.isport.com.basicres.commonutil.AppUtil;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.MapUtils;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.RoundImageView;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.entry.UserInformationBean;
import brandapp.isport.com.basicres.gen.UserInformationBeanDao;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class ActivityScaleRealTimeData extends BaseMVPTitleActivity<UpdateReportView,
        UpdateReportPresenter>
        implements UpdateReportView, ScaleScanView {
    RoundImageView ivHead;
    UserInfoBean infoBean;
    private LinearLayout rlOnscale;
    private LinearLayout rlOnscaleStart;
    private TextView tvData;
    private WaveView mWaveView;
    // private RippleBackground rippleBackground;
    private ScaleScanPresenter scaleScanPresenter;
    private BaseDevice baseDevice;

    private boolean isShowScaleData;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_scale_real_time_data;
    }

    @Override
    protected void initView(View view) {
        AppConfiguration.isScaleRealTime = true;
        ivHead = view.findViewById(R.id.iv_head);
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(UIUtils.getString(R.string.body_fat_scale));
        titleBarView.setRightText("");

        rlOnscale = view.findViewById(R.id.rl_onscale);
        rlOnscaleStart = view.findViewById(R.id.rl_onscale_start);
        tvData = view.findViewById(R.id.tv_data);
        //rippleBackground = view.findViewById(R.id.content);
        //mWaveView = view.findViewById(R.id.wave_view);
        //initWaveView();
        ISportAgent.getInstance().setIsWeight(true);

        //是否直接跳转到显示体重值页面
        isShowScaleData = getIntent().getBooleanExtra(Constants.SHOWSCALEDATA, false);
        if (isShowScaleData) {
            isFrist = false;
            showScaleData(true);
        } else {
            isFrist = true;
            showScaleData(false);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ISportAgent.getInstance().getCurrnetDevice() != null && ISportAgent.getInstance().getCurrnetDevice().deviceType == JkConfiguration.DeviceType.BODYFAT && AppConfiguration.isConnected) {
            //ISportAgent.getInstance().disConDevice(false);
        } else {
            ISportAgent.getInstance().disConDevice(false);
            mHandler.sendEmptyMessageDelayed(0x00, 100);
            AppConfiguration.isScaleConnectting = true;
            AppConfiguration.isScaleScan = true;
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x00:
                    startScan();
                    break;
                case 0x01:
                    requestPermission();
                    break;
            }
        }
    };


    private void requestPermission() {
        PermissionManageUtil permissionManage = new PermissionManageUtil(this);
        RxPermissions mRxPermission = new RxPermissions(this);
        if (!mRxPermission.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
//        if (!permissionManage.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            permissionManage.requestPermissionsGroup(new RxPermissions(this),
//                                                     PermissionGroup.LOCATION, new PermissionManageUtil
//                            .OnGetPermissionListener() {
//                        @Override
//                        public void onGetPermissionYes() {
//                            startScan();
//                        }
//
//                        @Override
//                        public void onGetPermissionNo() {
//                            ToastUtils.showToastLong(ActivityScaleScan.this, UIUtils.getString(R.string.location_permissions));
//                        }
//
//                    });
            permissionManage.requestPermissions(new RxPermissions(this), Manifest.permission.ACCESS_FINE_LOCATION, UIUtils.getString(R.string.permission_location0), new PermissionManageUtil.OnGetPermissionListener() {


                @Override
                public void onGetPermissionYes() {
                    startScan();
                }

                @Override
                public void onGetPermissionNo() {
                    ToastUtils.showToastLong(ActivityScaleRealTimeData.this, UIUtils.getString(R.string.location_permissions));
                }
            });
        } else {
            startScan();
        }
    }

    private void startScan() {
        if (AppUtil.isOpenBle()) {
            scaleScanPresenter.scan(JkConfiguration.DeviceType.BODYFAT);
        } else {
            PublicAlertDialog.getInstance().showDialog("", context.getResources().getString(R.string.bluetooth_is_not_enabled), context, getResources().getString(R.string.common_dialog_cancel), getResources().getString(R.string.app_bluetoothadapter_turnon), new AlertDialogStateCallBack() {
                @Override
                public void determine() {
//                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivity(intent);
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (bluetoothAdapter != null) {
                        bluetoothAdapter.enable();
                    }
                }

                @Override
                public void cancel() {
                    finish();
                }
            }, false);
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent, REQCODE_OPEN_BT);
        }
    }


    private void showScaleData(boolean isShow) {
        rlOnscale.setVisibility(isShow ? View.GONE : View.VISIBLE);
        rlOnscaleStart.setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (isShow) {
            //rippleBackground.startRippleAnimation();
        }
    }

    @Override
    protected UpdateReportPresenter createPresenter() {
        scaleScanPresenter = new ScaleScanPresenter(this);
        return new UpdateReportPresenter(this);
    }

    public void goback() {
        ISportAgent.getInstance().setIsWeight(false);
        //  rippleBackground.stopRippleAnimation();
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
        AppConfiguration.isScaleConnectting = false;
        //如果是沒有连接上就去重连手环或者手表
        AppConfiguration.isScaleScan = false;
        AppConfiguration.isScaleRealTime = false;
        AppConfiguration.isScaleConnectting = false;
        //可能还在少
        scaleScanPresenter.cancelScan();
        if (ISportAgent.getInstance().getCurrnetDevice() != null && ISportAgent.getInstance().getCurrnetDevice().deviceType == JkConfiguration.DeviceType.BODYFAT && Constants.tempConnected) {
            //  ISportAgent.getInstance().disConDevice(false);
            //scaleScanPresenter.cancelScan();
            //设备
            EventBus.getDefault().post(new MessageEvent(MessageEvent.scale_device_success));
        } else {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.reconnect_device));
        }

        EventBus.getDefault().post(new MessageEvent(MessageEvent.EXIT_SCALEREALTIME));
    }

    @Override
    protected void onDestroy() {
        goback();
        super.onDestroy();


    }

    private boolean isFrist = true;
    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {
            if (isConn) {
                Logger.myLog("连接成功,去同步数据,可以先去主页");
            } else {
                // finish();
               /* if (isFrist) {
                    //进入时，已经测试完了，那么需要用户重新上称,还可以测试的状态
                    //进入时，已经测试完了，但是这时要息屏了，会断开连接，此时用户也是要重新上称，但要经过搜索、连接的过程
                }*/
                // TODO: 2018/10/23 已经测完的设备断开不用管，正在测试的设备断开，1.还没有确切的值情况，这种情况应该不会出现，因为返回数据会一直保持连接，2.已经出现了确切值
            }
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            //TODO
            if (mResult != null)
                switch (mResult.getType()) {
                    case IResult.SCALE_UN_LOCK_DATA:
                        if (isFrist) {
                            isFrist = false;
                            showScaleData(true);
                        }
                        ScaleUnLockDataResult mResult1 = (ScaleUnLockDataResult) mResult;
                        tvData.setText(mResult1.getWeight() + "");
                        break;
                    case IResult.SCALE_LOCK_DATA:
                        if (isFrist) {
                            isFrist = false;
                            showScaleData(true);
                        }
                        ScaleLockDataResult mResult2 = (ScaleLockDataResult) mResult;
                        if (mResult2.getWeight() < 20) {
                            ToastUtils.showToast(ActivityScaleRealTimeData.this, UIUtils.getString(R.string.cont_20kg));
                        }
                        tvData.setText(mResult2.getWeight() + "");
                        break;
                    case IResult.SCALE_ALCULATE_DATA:
                        Logger.myLog("去存储");
                        ScaleCalculateResult mResult3 = (ScaleCalculateResult) mResult;
                        Scale_FourElectrode_DataModel scale_fourElectrode_dataModel =
                                mResult3.getScale_FourElectrode_DataModel();
                        // TODO: 2018/10/23  获取完最新的数据，存储或更新到本地,上传到服务器返回记录id，然后进入报告页面查询报告详情
                        if (!checkNet() && App.appType() == App.httpType) {
                            return;
                        }
                        mActPresenter.updateReport(scale_fourElectrode_dataModel);
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
    protected void initData() {

        infoBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(this));
        if (infoBean != null) {
            if (App.appType() == App.httpType) {
                LoadImageUtil.getInstance().loadCirc(context, infoBean.getHeadUrl(), ivHead);
            } else {
                /**
                 * 如果是本地的图片就读取本地的路径
                 */
                if (!TextUtils.isEmpty(infoBean.getHeadUrl())) {
                    ivHead.setImageBitmap(BitmapFactory.decodeFile(infoBean.getHeadUrl()));
                }
            }
        }
    }

    @Override
    protected void initEvent() {
        ISportAgent.getInstance().registerListener(mBleReciveListener);
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
    public void updateSuccess(UpdateSuccessBean bean, Scale_FourElectrode_DataModel scale_fourElectrode_dataModel) {
        // TODO: 2018/10/23 上传报告成功，去报告页，传报告id
        // TODO: 2019/1/12 单机版暂还未做报告页面，直接返回主页去

        UserInfoBean userInfo = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(this));
        userInfo.setWeight((int) scale_fourElectrode_dataModel.getWeight() + "");
        UserInformationBean userInfoByUserId = UserInformationBeanAction.findUserInfoByUserId(TokenUtil.getInstance().getPeopleIdInt(this));
        UserInformationBeanDao userInformationBeanDao = BaseAction.getUserInformationBeanDao();
        if (userInfoByUserId != null) {
            userInfoByUserId.setBodyWeight(scale_fourElectrode_dataModel.getWeight());
            userInformationBeanDao.update(userInfoByUserId);
        }
        Logger.myLog("itemHeight.getContentText() == " + userInfo.getHeight());
        if (App.isHttp()) {
            mActPresenter.saveUserBaseicInfo(userInfo.getGender(), userInfo.getNickName(), userInfo.getHeight() + " cm", userInfo.getWeight() + " kg"
                    , userInfo.getBirthday());
            CommonUserAcacheUtil.saveUsrInfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), userInfo);
        }
        AppConfiguration.saveUserInfo(userInfo);
        if (App.appType() == App.httpType) {
            Logger.myLog("上传体脂检测记录成功" + bean.toString());
            Intent intent = new Intent(this, ActivityScaleReport.class);
//            intent.putExtra("fatSteelyardId", bean.getPublicId());
            intent.putExtra("mScale_fourElectrode_dataModel", scale_fourElectrode_dataModel);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ActivityScaleReport.class);
            intent.putExtra("mScale_fourElectrode_dataModel", scale_fourElectrode_dataModel);
            startActivity(intent);
        }
        //去通知刷新首页体脂称item数据
        EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_SCALE_DATA_SUCCESS));
        finish();
    }

    @Override
    public void saveUserBaseInfoSuccess() {

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
    public void onBackPressed() {
        // EventBus.getDefault().post(new MessageEvent(MessageEvent.reconnect_device));
    }

    boolean hasConnected = false;

    @Override
    public void onScan(List<BaseDevice> baseViewList) {
        if (!hasConnected && baseViewList.size() > 0) {
            this.baseDevice = baseViewList.get(0);
            hasConnected = true;
            scaleScanPresenter.cancelScan();
            ISportAgent.getInstance().disConDevice(false);
            scaleScanPresenter.connect(baseViewList.get(0), false, true);
        }
    }

    @Override
    public void onScan(Map<String, BaseDevice> listDevicesMap) {
        if (!hasConnected && listDevicesMap.size() > 0) {
            this.baseDevice = MapUtils.getFirstOrNull(listDevicesMap);
            Logger.myLog("baseDevice == " + baseDevice.toString());
            hasConnected = true;
            scaleScanPresenter.cancelScan();
            scaleScanPresenter.connect(baseDevice, false, true);
        }
        //Logger.myLog("onScan");

    }

    @Override
    public void onScan(String key, BaseDevice baseDevice) {
        if (!hasConnected && baseDevice != null) {
            this.baseDevice = baseDevice;
            Logger.myLog("baseDevice == " + baseDevice.toString());
            hasConnected = true;
            scaleScanPresenter.cancelScan();
            scaleScanPresenter.connect(baseDevice, false, true);
        }
    }

    @Override
    public void bindSuccess(int deviceId) {

    }

    @Override
    public void canBind(int state) {

    }
}
