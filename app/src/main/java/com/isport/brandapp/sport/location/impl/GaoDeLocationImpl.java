package com.isport.brandapp.sport.location.impl;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.isport.brandapp.sport.location.bean.SBLocation;
import com.isport.brandapp.sport.location.interfaces.ILocation;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import brandapp.isport.com.basicres.commonutil.AppUtil;

/**
 * 功能:高德地图定位
 */
public class GaoDeLocationImpl implements ILocation, AMapLocationListener {


    private WeakReference<Context> context;
    private final int minTime;
    private final int minDistance;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private LocationListener listener;

    public GaoDeLocationImpl(Context context, int minTime, int minDistance) {
        this.context = new WeakReference<>(context.getApplicationContext());
        this.minTime = minTime;
        this.minDistance = minDistance;
    }

    @Override
    public void start() {
        stop();
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(context.get());

            mLocationClient.setLocationListener(this);
        }
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        if (AppUtil.isZh(context.get())) {
            mLocationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.ZH);
        } else {
            mLocationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.EN);
        }
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒 1次
        mLocationOption.setInterval(5000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public void stop() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位
            mLocationClient.onDestroy();//销毁定位客户端。
            mLocationClient = null;
        }
    }

    @Override
    public SBLocation getLastLocation() {
        AMapLocation lastLocation = mLocationClient.getLastKnownLocation();
        if (lastLocation == null) return null;
        return getLocation(lastLocation);
    }

    @Override
    public void setLocationListener(LocationListener listener) {
        // Log.e("JWebSocketClientService", "listener=" + listener + "----");
        this.listener = listener;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

       // Log.e("JWebSocketClientService", "listener=" + listener + "----" + aMapLocation);
       /* getLocation(aMapLocation);
        if (aMapLocation != null) {
            BaseGattCallback.logBuilder.append(new StringBuilder(DateUtil.dataToString(new Date(), "HH:mm:ss") + aMapLocation.getLatitude() + "longitude=" + aMapLocation.getLongitude() + "getCity=" + aMapLocation.getCity()).append("\r\n"));
        }*/
        if (listener != null) {
            if (aMapLocation == null) {
                listener.onLocationChanged(null);
            } else {
                listener.onLocationChanged(getLocation(aMapLocation));
            }
        }
    }

    @NonNull
    private SBLocation getLocation(AMapLocation aMapLocation) {
        return new SBLocation(aMapLocation, aMapLocation.getTime(), aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation.getAltitude(), aMapLocation.getAccuracy(), aMapLocation.getSpeed(), aMapLocation.getGpsAccuracyStatus(), aMapLocation.getCountry());
    }
}
