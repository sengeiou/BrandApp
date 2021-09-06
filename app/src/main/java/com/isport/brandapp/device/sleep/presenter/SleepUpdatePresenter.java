package com.isport.brandapp.device.sleep.presenter;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.action.sleep.Sleep_Sleepace_DataModelAction;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_DataModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.gen.Sleep_Sleepace_DataModelDao;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.bind.model.DeviceOptionImple;
import com.isport.brandapp.bind.model.DeviceResultCallBack;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.sleep.view.SleepUpdateView;
import com.isport.brandapp.repository.SleepRepository;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class SleepUpdatePresenter extends BasePresenter<SleepUpdateView> {
    private final DeviceOptionImple deviceOptionImple;
    SleepUpdateView sleepUpdateView;
    private List<Sleep_Sleepace_DataModel> mSleep_Sleepace_DataModel;
    private int mCurrentSleepIndex;

    public SleepUpdatePresenter(SleepUpdateView sleepUpdateView) {
        this.sleepUpdateView = sleepUpdateView;
        deviceOptionImple = new DeviceOptionImple();

    }

    public void updateSleepHistoryData() {
        if (!(App.appType() == App.httpType)) {
            if (isViewAttached()) {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_SLEEP_DATA_SUCCESS));
                mActView.get().updateSuccess(null);
            }
        } else {
            List<Sleep_Sleepace_DataModel> sleep_sleepace_dataModels =
                    Sleep_Sleepace_DataModelAction
                            .findSleep_Sleepace_DataModelByDeviceIdAndTimeTamp1
                                    (TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()),
                                            App.getSleepBindTime());
            Logger.myLog("getSleepBindTime == " + App.getSleepBindTime());
            if (sleep_sleepace_dataModels != null) {
                mSleep_Sleepace_DataModel = sleep_sleepace_dataModels;
                mCurrentSleepIndex = 0;
                updateSleepHistory();
            } else {
                if (isViewAttached()) {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_SLEEP_DATA_SUCCESS));
                    mActView.get().updateSuccess(null);
                }
            }
        }
        //model.updateSleepHistoryData(sleepHistoryDataResult, name);
    }

    private void updateSleepHistory() {
        Sleep_Sleepace_DataModel sleep_sleepace_dataModel = mSleep_Sleepace_DataModel.get(mCurrentSleepIndex);
        SleepRepository.requst(sleep_sleepace_dataModel)
                .as(sleepUpdateView.bindAutoDispose())
                .subscribe(new BaseObserver<UpdateSuccessBean>(context) {
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
                    public void onNext(UpdateSuccessBean updateSleepReportBean) {
                        NetProgressObservable.getInstance().hide();
                        if (isViewAttached()) {
                            Sleep_Sleepace_DataModelDao sleep_sleepace_dataModelDao = BleAction
                                    .getSleep_Sleepace_DataModelDao();
                            sleep_sleepace_dataModel.setReportId(updateSleepReportBean.getPublicId());
                            sleep_sleepace_dataModelDao.update(sleep_sleepace_dataModel);
                            mCurrentSleepIndex++;
                            if (mCurrentSleepIndex > mSleep_Sleepace_DataModel.size() - 1) {
                                EventBus.getDefault().post(new MessageEvent(MessageEvent
                                        .UPDATE_SLEEP_DATA_SUCCESS));
                                mActView.get().updateSuccess(updateSleepReportBean);
                            } else {
                                updateSleepHistory();
                            }
                        }
                    }
                });
    }

    public void scan(int type) {

        deviceOptionImple.scan(type, false, new DeviceResultCallBack() {
            @Override
            public void onScanResult(ArrayList<BaseDevice> mBleDevices) {

            }

            @Override
            public void onScanResult(Map<String, BaseDevice> listDevicesMap) {

            }


            @Override
            public void onScanFinish() {

            }
        });
    }

    public void cancelScan() {
        if (deviceOptionImple != null) {
            deviceOptionImple.cancelScan();
        }
    }


}
