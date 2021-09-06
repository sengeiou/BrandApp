package com.isport.brandapp.sport.location.view;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;

import com.isport.brandapp.sport.location.bean.SBLocation;
import com.isport.brandapp.sport.location.interfaces.ILocation;
import com.isport.brandapp.sport.location.interfaces.OnMapLocationAddressListener;
import com.isport.brandapp.sport.location.interfaces.OnMapScreenShotListener;
import com.isport.brandapp.sport.location.interfaces.OnSportMessageListener;
import com.isport.brandapp.sport.location.utils.LinearSmooth;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * 功能:地图助手                                                                                                                         助手(父类)
 */
public abstract class SBMapHelper implements Application.ActivityLifecycleCallbacks {

    private static WeakReference<Application> app;
    private WeakReference<Activity> mLastActivity;
    protected ILocation iLocation;
    private View mMapView;

    private LinkedList<SBLocation> mLocations = new LinkedList<>();
    private LinkedList<SBLocation> mLastLocations = new LinkedList<>();
    private int count = 0;
    private boolean isStarted;

    public View getMapView() {
        return mMapView;
    }

    public SBMapHelper(Activity activity, Bundle savedInstanceState, ILocation iLocation, View mMapView) {
        mLastActivity = new WeakReference<>(activity);
        this.iLocation = iLocation;

        ILocation.LocationListener listener = new ILocation.LocationListener() {
            @Override
            public void onLocationChanged(SBLocation location) {
                location = convertLocation(location);
                drawLineAndMarker(location, true);
                SBMapHelper.this.onLocationChanged(location);
            }

            @Override
            public void onSignalChanged(int level) {
                SBMapHelper.this.onSignalChanged(level);
            }
        };
        iLocation.setLocationListener(listener);


        if (activity != null) {
            if (app == null) {
                app = new WeakReference<>(activity.getApplication());
                final Application application = app.get();
                if (application != null) {
                    application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                        @Override
                        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                            if (mLastActivity.get() == activity) {
                                SBMapHelper.this.onActivityCreated(activity, savedInstanceState);
                            }
                        }

                        @Override
                        public void onActivityStarted(Activity activity) {
                            if (mLastActivity.get() == activity) {
                                SBMapHelper.this.onActivityStarted(activity);
                            }
                        }

                        @Override
                        public void onActivityResumed(Activity activity) {
                            if (mLastActivity.get() == activity) {
                                SBMapHelper.this.onActivityResumed(activity);
                            }
                        }

                        @Override
                        public void onActivityPaused(Activity activity) {
                            if (mLastActivity.get() == activity) {
                                SBMapHelper.this.onActivityPaused(activity);
                            }
                        }

                        @Override
                        public void onActivityStopped(Activity activity) {
                            if (mLastActivity.get() == activity) {
                                SBMapHelper.this.onActivityStopped(activity);
                            }
                        }

                        @Override
                        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                            if (mLastActivity.get() == activity) {
                                SBMapHelper.this.onActivitySaveInstanceState(activity, outState);
                            }
                        }

                        @Override
                        public void onActivityDestroyed(Activity activity) {
                            if (mLastActivity.get() == activity) {
                                SBMapHelper.this.onActivityDestroyed(activity);
                                application.unregisterActivityLifecycleCallbacks(this);
                                app.clear();
                                app = null;
                            }
                        }
                    });
                }
            }
        }
    }

    public LinkedList<SBLocation> getLocations() {
        return mLocations;
    }


    public void requestLastLocation() {
        SBLocation location = getLastLocation();
        if (location == null) return;
        setMarkerAndMoveCamera(location);
    }

    public void setMarkerAndMoveCamera(SBLocation location){
        drawEndMarker(location);
        moveCamera(location, true);
    }

    public SBLocation getLastLocation() {
        if (iLocation == null) return null;
        SBLocation lastLocation = iLocation.getLastLocation();
        if (lastLocation == null) return null;
        return convertLocation(lastLocation);
    }

    /**
     * 开始运动 GO GO!
     */
    public void startSport() {

        iLocation.stop();
        iLocation.start();
        isStarted = true;
    }

    public boolean isStarted() {
        return isStarted;
    }

    /**
     * 停止运动
     */
    public void stopSport() {
        isStarted = false;
        iLocation.stop();
    }

    /**
     * AB两点的经纬度
     *
     * @param locationA
     * @param locationB
     * @return
     */
    public abstract float calculateLineDistance(SBLocation locationA, SBLocation locationB);

    /**
     * 截图
     *
     * @param isCaptureCenter
     * @param onMapScreenShotListener
     */
    public abstract void screenCapture(boolean isCaptureCenter, OnMapScreenShotListener onMapScreenShotListener);

    /**
     * 获取地址名称
     *
     * @param onMapLocationAddressListener
     */
    public abstract void requestGetLocationAddress(SBLocation location, OnMapLocationAddressListener onMapLocationAddressListener);

    /**
     * 运动信息回调
     *
     * @param onSportMessageListener
     */
    public abstract void setOnSportMessageListener(OnSportMessageListener onSportMessageListener);

    /**
     * 缩放/移动 画面到屏幕可视的全部marker的可见矩形范围
     */
    public abstract void animateCameraToScreenBounds();

    protected abstract void drawEndMarker(SBLocation location);

    protected abstract void drawBeginMarker(SBLocation location);

    protected abstract void drawLine(SBLocation location);

    /**
     * 信号值
     *
     * @param level
     */
    protected abstract void onSignalChanged(int level);

    /**
     * 位置改变
     *
     * @param location
     */
    protected abstract void onLocationChanged(SBLocation location);

    /**
     * @param location 预览的位置
     * @param anim
     */
    public abstract void moveCamera(SBLocation location, boolean anim);


    protected void drawLineAndMarker(SBLocation location, boolean moveToCenter) {
        //是 新位置
        boolean hasNewLocation = !contains(mLocations, location);

        if (hasNewLocation) {
            //添加新位置

            mLocations.add(location);
            count++;

            if (mLocations.size() >= 2 && count % 10 == 0) {
                mLocations = new LinearSmooth(this, mLocations, 5d).compress(mLastLocations);
                mLastLocations.clear();
                mLastLocations.addAll(mLocations);
            }
        }
        //有新位置再画 没有新位置 说明用户一直在同一个位置
        if (hasNewLocation) {
            drawLine(location);
            if (mLocations.size() >= 2) {
                drawBeginMarker(mLocations.getFirst());
            }
            drawEndMarker(location);
        }

        if (moveToCenter) {
            moveCamera(location, true);
        }
    }

    private boolean contains(LinkedList<SBLocation> mLocations, SBLocation location) {
        if (mLocations == null || mLocations.isEmpty()) return false;
        for (SBLocation mLocation : mLocations) {
            if (mLocation.getAccuracy() == location.getAccuracy() &&
                    mLocation.getLatitude() == location.getLatitude() &&
                    mLocation.getLongitude() == location.getLongitude()) {
                return true;
            }
        }
        return false;
    }


    private SBLocation convertLocation(SBLocation location) {
        double[] latlng = onLocationConverter(location.getLatitude(), location.getLongitude());
        //更新转换后的经纬度
        location.setLatitude(latlng[0]);
        location.setLongitude(latlng[1]);
        return location;
    }

    protected abstract double[] onLocationConverter(double latitude, double longitude);

}
