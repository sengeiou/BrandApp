package com.isport.blelibrary.interfaces;

import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.result.IResult;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction  接返回和设置数据返回的的监听。
 */

public interface BleReciveListener {

    void onConnResult(boolean isConn,boolean isConnectByUser, BaseDevice baseDevice);//连接的结果，true为连接成功,  0 体脂称 1 睡眠带 2 手表

    void setDataSuccess(String s);//设置数据成功。

    void receiveData(IResult mResult);//接收到数据。

    void onConnecting(BaseDevice baseDevice);

    void onBattreyOrVersion(BaseDevice baseDevice);
}
