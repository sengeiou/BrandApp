package com.isport.brandapp.gaodelibrary;

import android.app.Notification;

import com.amap.api.location.AMapLocation;

import androidx.annotation.Keep;

/**
 * Created by BLiYing on 2018/6/3.
 */
@Keep
public interface OnGaodeLibraryListen {
    @Keep
    interface LocationListen{
        @Keep
        void getCurrentGaodeLocation(AMapLocation aMapLocation);

    }

    @Keep
    interface DistanceListen{
        void getDistance(double distance);

    }

    @Keep
    interface NotificationListen{
        void getNotificationListen(Notification notification);
    }

    interface DrawTraceListen{
        void drawTrace();
    }

}
