package com.isport.brandapp.sport.location;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.isport.brandapp.R;

/**
 * 功能：主服务
 * 常驻，负责处理app中各种耗时杂事
 * 功能:位置定位服务,事件接收器
 */

public class MainService extends Service implements CountTimer.OnCountTimerListener, LocationServiceHelper.OnLocationListener {
    private LocationServiceHelper mLSHelper;
    private CountTimer countTimer = new CountTimer(60 * 1000L, this);

    //private Call<WeatherListBean> weatherListBeanCall;
    //  private UploadGoogleFitHelper mGLFitHelper;
    private long minutes;

    private String date;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLSHelper = new LocationServiceHelper(this);
        // mGLFitHelper = new UploadGoogleFitHelper(this);
        mLSHelper.setOnLocationListener(this);

        // SBEventBus.register(this);
        countTimer.start();
        //刚开始定位一次并拿到天气信息的原因是:
        //1.等会自动连接设备后能第一时间给设备发送天气数据,而不用重新等待天气请求成功
        //2.防止用户一打开app马上进去地图轨迹界面,无法即时拿到天气的尴尬
        startLocation();

        //8.0以上要求在5秒内运行这个
        AppDataNotifyUtil.updateNotificationTitle(this, getString(R.string.app_name), "--");



        minutes = 0;
    }


    /**
     * 一分钟回调一次
     *
     * @param millisecond
     */
    @Override
    public void onCountTimerChanged(long millisecond) {

       /* date = DateUtil.getDate(DateUtil.HH_MM, System.currentTimeMillis());

        long lastDeviceSyncTime = UserStorage.getLastDeviceSyncTime();
        long nextDeviceSyncTime = lastDeviceSyncTime + SyncDataHelper.SYNC_TIME_INTERVAL * 60 * 1000;
        long nextMinutes = SyncDataHelper.SYNC_TIME_INTERVAL - (nextDeviceSyncTime - System.currentTimeMillis()) / 1000 / 60;
        if (System.currentTimeMillis() > nextDeviceSyncTime) {

        } else if (this.minutes == 0) {
            //minutes修正
            this.minutes = nextMinutes;
            SBLog.i("定时器:上次同步时间:%s,30分钟后将会同步", DateUtil.getDate(DateUtil.YYYY_MM_DD_HH_MM_SS, lastDeviceSyncTime));
        }

        //每半小时设备数据同步
        if (this.minutes == 0 || this.minutes % SyncDataHelper.SYNC_TIME_INTERVAL == 0) {
            SBLog.i("定时器:设备数据同步");
            SBEventBus.sendEvent(AppEvent.EVENT_USER_REQUEST_SYNC_DEVICE_DATA, true);

        }

        //每6小时 同步一次天气
        if (this.minutes == 0 || this.minutes % 360 == 0) {
            SBLog.i("定时器:定位同步天气");
            //重置上次请求时间 防止这个值导致无法即时准点更新天气
            SBEventBus.sendEvent(AppEvent.EVENT_USER_REQUEST_SYNC_WEATHER_DATA);
        }


        //每小时刷新一次 好友消息
        if (this.minutes == 0 || this.minutes % 60 == 0) {
            SBLog.i("定时器:好友消息");
            SBEventBus.sendEvent(AppEvent.EVENT_USER_UNREAD_MESSAGE_NUMBER, true);
        }

        //每4小时定时GoogleFit同步
        switch (date) {
            case "00:00":
            case "04:00":
            case "08:00":
            case "12:00":
            case "16:00":
            case "20:00":
                final boolean hasPermission = GoogleFitTool.hasPermission(this);
                SBLog.i("定时器:GoogleFit同步");
                if (hasPermission && mGLFitHelper != null) {
                    mGLFitHelper.startAutoSync();
                }
                break;
        }
*/

        this.minutes++;
    }

    private void startLocation() {
        //重复定位 大于1小时的,重新定位
      /*  if (System.currentTimeMillis() - WeatherStorage.getLastWeatherTime() > 60 * 60 * 1000) {
            if (mLSHelper != null) {
                mLSHelper.startLocation();
            }
        } else {
            //小于1小时的 不请求天气,直接使用上一次的天气 减少天气API压力
            WeatherListBean weatherListBean = WeatherStorage.getWeatherListBean();
            if (weatherListBean != null) {
                sendWeatherToDevices(weatherListBean);
                return;
            }
            //无天气数据则重新定位并获取
            if (mLSHelper != null) {
                mLSHelper.startLocation();
            }
        }*/
    }


    @Override
    public void onLocationChanged(final String city, double latitude, double longitude) {
        if (mLSHelper != null) {
            mLSHelper.pause();
        }
        if (city == null) return;


    }


    /**
     * 上传数据给我的朋友看
     */
    private void uploadDataToMyFriendsSee() {
    }

    /**
     * 系统配置改变监听 这里监听的是系统语言变化
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mLSHelper != null) {
            mLSHelper.refreshLocationType();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLSHelper != null) {
            mLSHelper.pause();
            mLSHelper.close();
        }

        countTimer.stop();
    }


}
