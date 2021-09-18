package com.isport.brandapp.home.presenter;

import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.deviceEntry.impl.S002BDevice;
import com.isport.blelibrary.deviceEntry.impl.W307JDevice;
import com.isport.blelibrary.deviceEntry.impl.W311Device;
import com.isport.blelibrary.deviceEntry.impl.W520Device;
import com.isport.blelibrary.deviceEntry.impl.W526Device;
import com.isport.blelibrary.deviceEntry.impl.W557Device;
import com.isport.blelibrary.deviceEntry.impl.W560BDevice;
import com.isport.blelibrary.deviceEntry.impl.W560Device;
import com.isport.blelibrary.deviceEntry.impl.W812BDevice;
import com.isport.blelibrary.deviceEntry.impl.W812Device;
import com.isport.blelibrary.deviceEntry.impl.W813Device;
import com.isport.blelibrary.deviceEntry.impl.W814Device;
import com.isport.blelibrary.deviceEntry.impl.W817Device;
import com.isport.blelibrary.deviceEntry.impl.W819Device;
import com.isport.blelibrary.deviceEntry.impl.W910Device;
import com.isport.blelibrary.deviceEntry.impl.Watch516Device;
import com.isport.brandapp.home.view.DeviceListView;
import com.isport.brandapp.bind.model.DeviceOptionImple;
import com.isport.brandapp.bind.model.DeviceResultCallBack;

import java.util.ArrayList;
import java.util.Map;

import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * @Author
 * @Date 2018/10/12
 * @Fuction 主页数据处理
 */

public class DeviceConnPresenter extends BasePresenter<DeviceListView> {

    DeviceOptionImple deviceOptionImple;

    public DeviceConnPresenter() {
        deviceOptionImple = new DeviceOptionImple();
    }


    public void connectDevice(String currentName, String watchMac, int deviceType, boolean show, boolean isConnectByUser) {
        BaseDevice device = null;
        switch (deviceType) {
            case JkConfiguration.DeviceType.BRAND_W311: {
                device = new W311Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Brand_W520: {
                device = new W520Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.BRAND_W307J: {
                device = new W307JDevice(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.WATCH_W516: {
                device = new Watch516Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W812: {
                device = new W812Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Brand_W814: {
                device = new W814Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W813: {
                device = new W813Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W819: {
                device = new W819Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W910: {
                device = new W910Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W556: {
                device = new W526Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W817: {
                device = new W817Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W557: {
                device = new W557Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W812B: {
                device = new W812BDevice(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W560B: {
                device = new W560BDevice(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.Watch_W560: {
                device = new W560Device(currentName, watchMac);
            }
            break;
            case JkConfiguration.DeviceType.ROPE_SKIPPING: {
                device = new S002BDevice(currentName, watchMac);
            }
            break;
        }
        if (deviceOptionImple != null) {
            deviceOptionImple.connect(device, show, isConnectByUser);
        }
    }


    public void scan(int type, boolean isScale) {


        if (deviceOptionImple != null) {
            deviceOptionImple.scan(type, isScale, new DeviceResultCallBack() {
                @Override
                public void onScanResult(ArrayList<BaseDevice> mBleDevices) {

                }

                @Override
                public void onScanResult(Map<String, BaseDevice> listDevicesMap) {
                    if (isViewAttached()) {
                        mActView.get().onScan(listDevicesMap);
                    }
                }


                @Override
                public void onScanFinish() {
                    if (isViewAttached()) {
                        mActView.get().onScanFinish();
                    }
                }
            });

        }
    }

    public void cancelScan() {
        if (deviceOptionImple != null) {
            deviceOptionImple.cancelScan();
        }
    }


}
