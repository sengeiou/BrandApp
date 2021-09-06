package com.isport.brandapp.sport.location.interfaces;

/**
 * 功能:获取地址回调
 */
public interface OnMapLocationAddressListener {
    void onLocationAddress(String address);
    void onLocationAddressFailed(int code);
}
