package com.isport.brandapp.home.presenter;

import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.ThreadPoolUtils;
import com.isport.brandapp.home.view.FragmentDataView;
import com.isport.brandapp.device.W81Device.IW81DeviceDataModel;
import com.isport.brandapp.device.W81Device.W81DeviceDataModelImp;
import com.isport.brandapp.repository.BPRepository;
import com.isport.brandapp.repository.ExerciseRepository;
import com.isport.brandapp.repository.OnceHrRepository;
import com.isport.brandapp.repository.OxygenRepository;
import com.isport.brandapp.repository.TempRepository;
import com.isport.brandapp.wu.bean.OnceHrInfo;
import com.isport.brandapp.wu.bean.OxyInfo;
import com.isport.brandapp.wu.mvp.OnceHrHistoryView;
import com.isport.brandapp.wu.mvp.OxyHistoryView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.MessageEvent;

public class UpgradeDataPresenter {


    FragmentDataView view;
    OxyHistoryView mOxyView;
    OnceHrHistoryView mOnceHrHistoryView;

    public UpgradeDataPresenter(OxyHistoryView oxyView) {
        this.mOxyView = oxyView;

    }

    public UpgradeDataPresenter(OnceHrHistoryView mOnceHrHistoryView) {
        this.mOnceHrHistoryView = mOnceHrHistoryView;

    }

    public UpgradeDataPresenter(FragmentDataView view) {
        this.view = view;
    }


    public synchronized void getOxyenNumData(String deviceId, String userId, int number) {


        OxygenRepository.requstNumOxygenData(deviceId, userId, number).as(view.bindAutoDispose()).subscribe(new BaseObserver<List<OxyInfo>>(BaseApp.getApp(), false) {


            @Override
            public void onNext(List<OxyInfo> infos) {
                Logger.myLog("getOxyenNumData:" + infos);
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }
        });

    }

    public synchronized void getOxygenNumPageData(String deviceId, String userId, int offset) {

        OxygenRepository.requstPageNumOxygenData(deviceId, userId, offset).as(mOxyView.bindAutoDispose()).subscribe(new BaseObserver<List<OxyInfo>>(BaseApp.getApp(), false) {


            @Override
            public void onNext(List<OxyInfo> infos) {
                if (mOxyView != null) {
                    mOxyView.getOxyHistoryDataSuccess(infos);
                    Logger.myLog("getOxyenNumData:" + infos);
                }

            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }
        });

    }

    public synchronized void getOnceHrNumPageData(String deviceId, String userId, int offset) {

        OnceHrRepository.requstPageNumOnceHrData(deviceId, userId, offset).as(mOnceHrHistoryView.bindAutoDispose()).subscribe(new BaseObserver<List<OnceHrInfo>>(BaseApp.getApp(), false) {


            @Override
            public void onNext(List<OnceHrInfo> infos) {
                if (mOnceHrHistoryView != null) {
                    mOnceHrHistoryView.getOnceHrHistoryDataSuccess(infos);
                    Logger.myLog("getOnceHrNumPageData:" + infos);
                }

            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }
        });

    }


    public synchronized void upgradeOxyenData(String deviceId, String userId) {


        OxygenRepository.requstUpgradeData(deviceId, userId).as(view.bindAutoDispose()).subscribe(new BaseObserver<Integer>(BaseApp.getApp(), false) {


            @Override
            public void onNext(Integer updateSuccessBean) {
                if (updateSuccessBean != -1) {
                    ThreadPoolUtils.getInstance().addTask(new Runnable() {
                        @Override
                        public void run() {
                            IW81DeviceDataModel iw81DeviceDataModel = new W81DeviceDataModelImp();
                            iw81DeviceDataModel.updateWriId(deviceId, userId, 1);
                        }
                    });

                }
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                Logger.myLog(e.message);

            }
        });

    }

    //上传一次心率数据
    public synchronized void upgradeOnceHrData(String deviceId, String userId) {

        OnceHrRepository.requstUpgradeData(deviceId, userId).as(view.bindAutoDispose()).subscribe(new BaseObserver<Integer>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Integer updateSuccessBean) {
                if (updateSuccessBean != -1) {
                    ThreadPoolUtils.getInstance().addTask(new Runnable() {
                        @Override
                        public void run() {
                            IW81DeviceDataModel iw81DeviceDataModel = new W81DeviceDataModelImp();
                            iw81DeviceDataModel.updateWriId(deviceId, userId, 2);
                        }
                    });

                }
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                Logger.myLog("upgradeOnceHrData:" + e.toString());

            }
        });

    }

    //上传体温数据
    public synchronized void updateTempData(String deviceId, String userId) {

        Logger.myLog("updateTempData success:no deviceId" + deviceId + "userId：" + userId);

        TempRepository.requstUpgradeTempData(deviceId, userId).as(view.bindAutoDispose()).subscribe(new BaseObserver<Integer>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Integer integer) {
                if (integer != -1) {
                    ThreadPoolUtils.getInstance().addTask(new Runnable() {
                        @Override
                        public void run() {
                            IW81DeviceDataModel iw81DeviceDataModel = new W81DeviceDataModelImp();
                            iw81DeviceDataModel.updateWriId(deviceId, userId, 3);
                        }
                    });

                }
            }
        });
    }

    //上传血压数据
    public synchronized void upgradeBPData(String deviceId, String userId) {


        BPRepository.requstUpgradeData(deviceId, userId).as(view.bindAutoDispose()).subscribe(new BaseObserver<Integer>(BaseApp.getApp(), false) {


            @Override
            public void onNext(Integer updateSuccessBean) {
                Logger.myLog("updateBloodPressureWridId upgradeBPData" + updateSuccessBean);
                if (updateSuccessBean != -1) {
                    ThreadPoolUtils.getInstance().addTask(new Runnable() {
                        @Override
                        public void run() {
                            IW81DeviceDataModel iw81DeviceDataModel = new W81DeviceDataModelImp();
                            iw81DeviceDataModel.updateWriId(deviceId, userId, 0);
                        }
                    });

                }
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Logger.myLog("upgradeBPData:" + e.toString());
            }
        });

    }


    public void getExeciseDataSum() {

    }

    //上传锻炼数据
    public synchronized void upgradeExeciseData(int devicetype, String deviceId, String userId) {


        ExerciseRepository.requstUpgradeExerciseData(devicetype, deviceId, userId).as(view.bindAutoDispose()).subscribe(new BaseObserver<Integer>(BaseApp.getApp(), false) {


            @Override
            public void onNext(Integer updateSuccessBean) {

                //如果成功需要去更新所有的


                if (updateSuccessBean != -1) {

                    ThreadPoolUtils.getInstance().addTask(new Runnable() {
                        @Override
                        public void run() {
                            IW81DeviceDataModel iw81DeviceDataModel = new W81DeviceDataModelImp();
                            iw81DeviceDataModel.updateWriId(deviceId, userId, 2);
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.update_exercise));
                        }
                    });

                }

            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }
        });


    }
}

