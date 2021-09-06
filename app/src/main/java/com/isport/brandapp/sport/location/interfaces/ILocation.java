package com.isport.brandapp.sport.location.interfaces;


import com.isport.brandapp.sport.location.bean.SBLocation;

/**
 * 功能:定位基类
 */
public interface ILocation {
    void start();

    void stop();

    SBLocation getLastLocation();

    void setLocationListener(LocationListener listener);


    interface LocationListener {
        void onLocationChanged(SBLocation location);

        void onSignalChanged(int level);
    }
}
