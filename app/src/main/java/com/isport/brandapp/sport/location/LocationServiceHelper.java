package com.isport.brandapp.sport.location;

import android.content.Context;
import android.location.Address;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.example.websocket.WsManager;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.DateUtil;
import com.isport.brandapp.sport.location.bean.SBLocation;
import com.isport.brandapp.sport.location.impl.GaoDeLocationImpl;
import com.isport.brandapp.sport.location.interfaces.ILocation;

import org.greenrobot.eventbus.EventBus;

import java.io.Closeable;
import java.lang.ref.WeakReference;
import java.util.Date;

import brandapp.isport.com.basicres.commonutil.MessageEvent;

/**
 * 功能:位置定位服务
 */

public class LocationServiceHelper implements ILocation.LocationListener, Closeable {

    private static final String TAG = "LocationServiceHelper";
    
    private ILocation location;
    private WeakReference<Context> context;


    public void startLocation() {
        if (location != null) {
            location.start();
        }
    }

    public void stopLocation() {
        if (location != null) {
            location.stop();
        }
    }

    public LocationServiceHelper(Context context) {
        this.context = new WeakReference<Context>(context.getApplicationContext());
        switchLocationType();
    }

    public void switchLocationType() {

        Context context = getContext();

        if (context == null) return;
        location = new GaoDeLocationImpl(context, 5, 1);
        /*switch (MapType.getSmartMapType()) {
            case UNKNOWN:
                SBLog.i("天气服务:地图类型:未知,暂时选用高德地图作为默认定位");
            case A_MAP:
                SBLog.i("天气服务:地图类型:高德");
                location = new GaoDeLocationImpl(context, 1, 1);
                break;
            case GOOGLE_MAP:
                if (MapType.isGooglePlayServicesAvailable(context)) {
                    SBLog.i("天气服务:地图类型:谷歌");
                    location = new GoogleLocationImpl(context, 1000, 1000);
                }else{
                    SBLog.i("天气服务:地图类型:谷歌,但无法使用!用户未安装谷歌服务");
                }
                break;
        }*/
        //  location = new GaoDeLocationImpl(context, 1, 1);
       // Log.e("GaoDeLocationImpl", "switchLocationType" + "context=" + context + ",location" + location);
        if (location != null) {
            location.setLocationListener(this);
        }
    }

    @Override
    public void onLocationChanged(SBLocation location) {


        Log.e("onLocationChanged", "--------------------------");
        double latitude = 0;
        double longitude = 0;
        String city = null;
        if (location != null) {
            if (location.getLocation() instanceof Address) {
                Address addr = location.getLocation();
                if (addr != null && addr.hasLatitude() && addr.hasLongitude()) {
                    city = addr.getLocality();
                    latitude = addr.getLatitude();
                    longitude = addr.getLongitude();
                }
            }
            if (location.getLocation() instanceof AMapLocation) {

                AMapLocation addr = location.getLocation();
                if (addr != null && addr.getLatitude() > 0 && addr.getLongitude() > 0) {
                    city = addr.getCity();
                    latitude = addr.getLatitude();
                    longitude = addr.getLongitude();
                }
            }
        }


        //可在其中解析amapLocation获取相应内容。
   /*     mLocationLatitude = (float) location.getLatitude();
        mLocationLongitude = (float) location.getLongitude();
        gpsstate = location.getGpsAccuracyStatus();
        accuracy = location.getAccuracy();
        speed = location.getSpeed();*/
        int type = 0;
        if (location.getAccuracy() == 0) {
            type = 0;
        } else if (location.getAccuracy() > 0 && location.getAccuracy() <= 65) {
            type = 3;
        } else if (location.getAccuracy() > 65 && location.getAccuracy() <= 200) {
            type = 2;

        } else {
            type = 1;
        }
        if (TextUtils.isEmpty(city)) {
            return;
        }
        Constants.speed = location.getSpeed();
        Constants.accuracy = location.getAccuracy();
        Constants.gpstype = location.getGpsAccuracyStatus();
        Constants.locationType = type;
        Constants.mLocationLongitude = (float) location.getLongitude();
        Constants.mLocationLatitude = (float) location.getLatitude();
        Constants.cityName = city;
        Constants.currentCountry = location.getCounty();

                        Log.e(TAG, " Constants.cityName:"
                                + Constants.cityName + ",   速度" + location.getSpeed()+" Constants.mLocationLongitude"
                                + Constants.mLocationLongitude + "Constants.mLocationLatitude :" + Constants.mLocationLatitude + "type:" + type );

        EventBus.getDefault().post(new MessageEvent(MessageEvent.update_location));
        if (WsManager.logBuilder == null) {
            WsManager.logBuilder = new StringBuilder();
        }
        WsManager.logBuilder.append(new StringBuilder(DateUtil.dataToString(new Date(), "HH:mm:ss") + latitude + "longitude=" + longitude + "getCity=" + city).append("\r\n"));


        if (onLocationListener != null) {
            onLocationListener.onLocationChanged(city, latitude, longitude);
        }
    }

    @Override
    public void onSignalChanged(int level) {

    }

    public void refreshLocationType() {
        switchLocationType();
    }

    public void pause() {
        if (location != null) {
            location.stop();
        }
    }

    @Override
    public void close() {
        pause();
        if (this.context != null) {
            this.context.clear();
        }
    }

    private OnLocationListener onLocationListener;

    public void setOnLocationListener(OnLocationListener onLocationListener) {
        this.onLocationListener = onLocationListener;
    }

    public Context getContext() {
        return this.context.get();
    }

    public interface OnLocationListener {
        void onLocationChanged(String city, double latitude, double longitude);
    }
}
