package com.isport.blelibrary.interfaces;

import java.io.IOException;

public interface W311BluetoothListener extends BluetoothListener {
    void onSyncCompte(); //同步完成
    void onDeviceSuccess(int type);

    void onSysnSportDate(int year, int month, int day);
    void onSysnHrDate(int year,int month,int day);

    void onSyncHrDataComptelety();

    void onOpenReal();

    void onStartSync();

    void onW311RealTimeData(int sumStep, float sumDis, int sumCal, String mac);

    void onSuccessSendPhone();

    void onSuccessSendMesg(int mesgType,int dataIndex);

    void onSendUserInfo();
    void findPhoen();
    void onGetWatchFaceMode(int mode);
}
