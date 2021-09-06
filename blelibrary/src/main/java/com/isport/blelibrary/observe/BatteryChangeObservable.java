
package com.isport.blelibrary.observe;

import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.DeviceInformationTable;
import com.isport.blelibrary.observe.bean.BatteryChangeBean;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class BatteryChangeObservable extends Observable {

    private static BatteryChangeObservable obser;


    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private BatteryChangeObservable() {
        super();
    }

    public static BatteryChangeObservable getInstance() {
        if (obser == null) {
            synchronized (BatteryChangeObservable.class) {
                if (obser == null) {
                    obser = new BatteryChangeObservable();
                }
            }
        }
        return obser;
    }

    public void getBattery(int battery, int deviceType, String deviceName,boolean isSave) {
        if(isSave) {
            DeviceInformationTable mDeviceInformationTable = new DeviceInformationTable();
            mDeviceInformationTable.setBattery(battery);
            mDeviceInformationTable.setDeviceId(deviceName);
            ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, 0);
        }
        BatteryChangeBean batteryChangeBean = new BatteryChangeBean();
        batteryChangeBean.setBattery(battery);
        batteryChangeBean.setDeviceType(deviceType);
        batteryChangeBean.setDeviceName(deviceName);
        BatteryChangeObservable.getInstance().setChanged();
        BatteryChangeObservable.getInstance().notifyObservers(batteryChangeBean);
    }



}