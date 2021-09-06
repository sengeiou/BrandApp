package com.isport.brandapp.bind.presenter;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.bind.bean.BindInsertOrUpdateBean;
import com.isport.brandapp.bind.bean.DeviceState;
import com.isport.brandapp.bind.model.DeviceOptionImple;
import com.isport.brandapp.bind.model.DeviceResultCallBack;
import com.isport.brandapp.bind.view.ScaleScanView;
import com.isport.brandapp.bind.view.ScanBaseView;
import com.isport.brandapp.device.history.util.HistoryParmUtil;
import com.isport.brandapp.parm.db.DeviceDbParms;
import com.isport.brandapp.repository.MainResposition;

import java.util.ArrayList;
import java.util.Map;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.entry.bean.BaseParms;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * @Author
 * @Date 2018/10/15
 * @Fuction
 */

public class ScaleScanPresenter extends BasePresenter<ScaleScanView> {

    private static final String TAG = ScaleScanPresenter.class.getSimpleName();
    private DeviceOptionImple deviceOptionImple;
    private ScaleScanView view;

    public ScaleScanPresenter(ScaleScanView view) {
        this.view = view;
        deviceOptionImple = new DeviceOptionImple();
    }

    public void cancelScan() {
        if (deviceOptionImple != null) {
            deviceOptionImple.cancelScan();
        }
    }

    public void connect(BaseDevice baseDevice, boolean show, boolean isConnectByUser) {
        if (deviceOptionImple != null) {
            deviceOptionImple.connect(baseDevice, show, isConnectByUser);
        }
    }

    public void onRespondError(String message) {
        ToastUtils.showToast(context, message);
    }


//    public void disconnect() {
//        if (deviceOptionImple != null) {
//            deviceOptionImple.disconnect();
//        }
//    }

    public void disconnect(boolean reConnect) {
        if (deviceOptionImple != null) {
            deviceOptionImple.disconnect(reConnect);
        }
    }

    public void scan(int type) {
        deviceOptionImple.scan(type, false, new DeviceResultCallBack() {
            @Override
            public void onScanResult(ArrayList<BaseDevice> mBleDevices) {
                // Logger.myLog("onScanResultArrayList");
                if (view != null) {
                    Logger.myLog("onScanResult1ArrayList");
                    view.onScan(mBleDevices);
                }
            }

            @Override
            public void onScanResult(Map<String, BaseDevice> listDevicesMap) {
                //Logger.myLog("onScanResultmap");
                if (view != null) {
                    // Logger.myLog("onScanResult1map");
                    view.onScan(listDevicesMap);
                }
            }


            @Override
            public void onScanFinish() {

            }
        });
        //scanModel.scan(type);
    }

    /**
     * 网络版查询设备状态
     *
     * @param baseDevice
     */
    public void select_device_state(BaseDevice baseDevice) {
        MainResposition<DeviceState, BaseParms, BaseUrl, DeviceDbParms> mainResposition = new MainResposition<>();
        mainResposition.requst(HistoryParmUtil.selectDevice(baseDevice))
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<DeviceState>(context) {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        Logger.myLog("ResponeThrowable == " + e.toString());
                        if (e.message.startsWith("操作成功")) {
                            if (isViewAttached()) {
                                mActView.get().canBind(ScanBaseView.STATE_NOBIND);
                            }
                        }
                    }

                    @Override
                    public void onNext(DeviceState deviceState) {
                        NetProgressObservable.getInstance().hide();
                        if (deviceState == null) {
                            //说明是新设备,需要调用插入接口
                            Logger.myLog("deviceState == null");
                            if (isViewAttached()) {
                                mActView.get().canBind(ScanBaseView.STATE_NOBIND);
                            }
                        } else {
                            int userid = deviceState.getUserId();
                            Logger.myLog("deviceState != null userid = " + userid);
                            if (userid == 0) {
                                //说明是使用过的，解绑后的设备,可以绑定，调用更新接口
                                if (isViewAttached()) {
                                    mActView.get().canBind(ScanBaseView.STATE_HASBIND_CANBIND);
                                }
                            } else {
                                int state;
                                if (userid == -1) {
                                    //本地绑定成功
                                    state = ScanBaseView.STATE_BINDED;
                                } else {
                                    //已经被绑定的设备，说明不能绑定了，提示用户
                                    state = ScanBaseView.STATE_HASBIND_CONTBIND;
                                }
                                if (isViewAttached()) {
                                    mActView.get().canBind(state);
                                }
                            }
                        }
                    }
                });
    }

    public void bindDevice(BaseDevice baseDevice) {
        MainResposition<Integer, BindInsertOrUpdateBean, BaseUrl, DeviceDbParms> mainResposition = new
                MainResposition<>();
        mainResposition.requst(HistoryParmUtil.setDevice(baseDevice))
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<Integer>(context, true) {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        NetProgressObservable.getInstance().hide();
                        Logger.myLog(e.toString());
                        ToastUtils.showToast(context, e.message);
                    }

                    @Override
                    public void onNext(Integer bindBean) {

                        if (bindBean != null) {
                            //1绑定成功
                            //0为绑定失败
                            if (bindBean != 1) {
                            } else {
                                String deviceId;
                                if (baseDevice.deviceType == JkConfiguration.DeviceType.BODYFAT) {
                                    deviceId = baseDevice.address;
                                } else {
                                    deviceId = baseDevice.deviceName;
                                }
                                ISportAgent.getInstance().bindDevice(baseDevice.deviceType, baseDevice.address, deviceId,
                                        TokenUtil.getInstance().getPeopleIdStr
                                                (BaseApp.getApp()), baseDevice.deviceName);
                                if (isViewAttached()) {
                                    mActView.get().bindSuccess(bindBean);
                                }
                            }
                        }

                    }
                });
    }

    public void bindDevice(BaseDevice baseDevice, int stateNobind) {
        MainResposition<Integer, BindInsertOrUpdateBean, BaseUrl, DeviceDbParms> mainResposition = new
                MainResposition<>();
        mainResposition.requst(HistoryParmUtil.setDevice(baseDevice))
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<Integer>(context) {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        NetProgressObservable.getInstance().hide();
                        ToastUtils.showToast(context, e.message);
                    }

                    @Override
                    public void onNext(Integer bindBean) {
                        NetProgressObservable.getInstance().hide();
                        Logger.myLog("绑定成功，需要存储到本地== " + baseDevice.deviceName + ",baseDevicetype:" + baseDevice.deviceType);
                        //暂不存储本地，先走通网络版本的逻辑，再考虑存本地无网络的情况
                        String deviceId;
                        if (baseDevice.deviceType == JkConfiguration.DeviceType.BODYFAT) {
                            deviceId = baseDevice.address;
                        } else {
                            deviceId = baseDevice.deviceName;
                        }
                        ISportAgent.getInstance().bindDevice(baseDevice.deviceType, baseDevice.address, deviceId,
                                TokenUtil.getInstance().getPeopleIdStr
                                        (BaseApp.getApp()), baseDevice.deviceName);
                        if (isViewAttached()) {
                            mActView.get().bindSuccess(bindBean);
                        }
                    }
                });

    }


}
