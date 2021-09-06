package com.isport.brandapp.bind.model;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.ScanBackListener;

import java.util.ArrayList;
import java.util.Map;

public class DeviceOptionImple {

    DeviceResultCallBack callBack;
    private static final String TAG = "DeviceOptionImple";

    public void scan(int type, boolean isScale, DeviceResultCallBack callBack) {
        this.callBack = callBack;
        ISportAgent.getInstance().scanDevice(mScanBackListener, type, isScale);

    }


    ScanBackListener mScanBackListener = new ScanBackListener() {
        @Override
        public void onScanResult(ArrayList<BaseDevice> mBleDevices) {
            if (callBack != null) {
                callBack.onScanResult(mBleDevices);
            }
        }

        @Override
        public void onScanResult(Map<String, BaseDevice> listDevicesMap) {
            if (callBack != null) {
                callBack.onScanResult(listDevicesMap);
            }
        }


        @Override
        public void onScanFinish() {
            if (callBack != null) {
                callBack.onScanFinish();
            }
        }
    };


    public void cancelScan() {
        ISportAgent.getInstance().cancelLeScan();
    }

    /**
     * @param baseDevice
     * @param show       是否显示弹窗
     */
    public void connect(BaseDevice baseDevice, boolean show, boolean isConnectByUser) {
        if (show && isConnectByUser) {
            //com.isport.blelibrary.utils.Logger.myLog("connect,show:" + show + ",isConnectByUser:" + isConnectByUser);
            //BleProgressObservable.getInstance().show(UIUtils.getString(R.string.app_isconnecting), false);
        }
        ISportAgent.getInstance().connect(baseDevice, true, isConnectByUser);
    }

//    public void disconnect() {
//        ISportAgent.getInstance().disconnect();
//    }

    public void disconnect(boolean reConnect) {
        ISportAgent.getInstance().disConDevice(reConnect);
    }


}
