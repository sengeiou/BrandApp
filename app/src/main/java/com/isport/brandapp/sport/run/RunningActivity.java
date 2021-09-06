package com.isport.brandapp.sport.run;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.amap.api.maps.MapView;
import com.isport.brandapp.R;
import com.isport.brandapp.sport.location.bean.MapType;
import com.isport.brandapp.sport.location.bean.SBLocation;
import com.isport.brandapp.sport.location.impl.GpsLocationImpl;
import com.isport.brandapp.sport.location.view.SBGaoDeMap;
import com.isport.brandapp.sport.location.view.SBGoogleMap;
import com.isport.brandapp.sport.location.view.SBMapHelper;


/**
 * 功能:运动轨迹界面/功能与动画
 */
public class RunningActivity extends BaseActivity<RunningPresenterImpl, IRunningContract.IView> implements IRunningContract.IView {

    public static final String KEY_MAP_TYPE = "mapType";

    FrameLayout mMapContent;


    private SBMapHelper mapHelper;

    @Override
    protected RunningPresenterImpl initPresenter() {
        return new RunningPresenterImpl(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_running;
    }


    @Override
    public void setContentView(View views) {
        super.setContentView(views);
        mMapContent = views.findViewById(R.id.mMapContent);
    }

    @Override
    protected void onCreateActivity(final Bundle savedInstanceState) {
//        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
//        StatusBarUtil.setStatusBarDarkTheme(this,false);
//        StatusBarUtil.setStatusBarColor(this,0x00000000);
        final MapType.TYPE mapType = (MapType.TYPE) getIntent().getSerializableExtra(KEY_MAP_TYPE);
        if (mapType == null || mapType == MapType.TYPE.UNKNOWN) {
            finish();
            return;
        }
        initTitle();
        initMap(savedInstanceState, mapType);
        getPresenter().initDefaultValue();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //  PermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().requestSettingConfig();
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        // tlTitle.setTitleShow(false);

    }

    @Override
    public void onUpdateSettingConfig(boolean isKeepScreenEnable, boolean isWeatherEnable) {
        if (isFinished()) return;

        Window window = getWindow();
        if (isKeepScreenEnable) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

    }

    @Override
    public void onUpdateWeatherData(int weatherType, String weatherTemperatureRange, String weatherQuality) {
        if (isFinished()) return;


    }

    @Override
    public void onUpdateMapFirstLocation(double mLatitude, double mLongitude) {
        if (isFinished()) return;
        if (mapHelper == null) return;
        //初始化地图时 显示默认的经纬度,如果没有 那就默认北京
        mapHelper.setMarkerAndMoveCamera(new SBLocation(mLatitude, mLongitude));
    }


    /**
     * 初始化地图
     */
    private void initMap(Bundle savedInstanceState, MapType.TYPE mapType) {
        GpsLocationImpl iLocation = new GpsLocationImpl(this, 1000, 10);

        View mMapView;
        switch (mapType) {
            case A_MAP:  //在国内 使用高德
                mMapView = new MapView(this);
                mapHelper = new SBGaoDeMap(this, savedInstanceState, iLocation, (MapView) mMapView);
                break;
            case GOOGLE_MAP: //在国外使用谷歌地图
                mMapView = new com.google.android.gms.maps.MapView(this);
                mapHelper = new SBGoogleMap(this, savedInstanceState, iLocation, (com.google.android.gms.maps.MapView) mMapView);
                break;
            default:
                finish();
                return;
        }
        mMapContent.addView(mMapView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        getPresenter().initMapListener(mapHelper);


    }


    @Override
    public void onUpdateGpsSignal(int signal) {
        if (isFinished()) {
            return;
        }
        /*switch (signal) {
            case RunningPresenterImpl.SIGNAL_WEAK:
                ivRunningGPS.setImageResource(R.mipmap.icon_gps_1);
                llRunningGPS.setBackground(getResources().getDrawable(R.drawable.item_gps_weak_bg));
                break;
            case RunningPresenterImpl.SIGNAL_MIDDLE:
                ivRunningGPS.setImageResource(R.mipmap.icon_gps_2);
                llRunningGPS.setBackground(getResources().getDrawable(R.drawable.item_gps_middle_bg));
                break;
            case RunningPresenterImpl.SIGNAL_STRONG:
                ivRunningGPS.setImageResource(R.mipmap.icon_gps_3);
                llRunningGPS.setBackground(getResources().getDrawable(R.drawable.item_gps_strong_bg));
                break;
            case RunningPresenterImpl.SIGNAL_STRONG_MAX:
                ivRunningGPS.setImageResource(R.mipmap.icon_gps_4);
                llRunningGPS.setBackground(getResources().getDrawable(R.drawable.item_gps_strong_bg));
                break;
            case RunningPresenterImpl.SIGNAL_GPS_OFF:
                gpsPrmAlertDialog();
                break;
        }*/
    }

    @Override
    public void onUpdateSportData(String distances, String calories, String hourSpeed, String pace) {
     /*   tvRunningDistances.setText(distances);
        tvRunningCalories.setText(calories);
        tvRunningHourSpeed.setText(hourSpeed);
        tvRunningPace.setText(pace);*/

    }

    @Override
    public void onUpdateSportData(String spendTime) {
    }

    @Override
    public void onSaveSportDataStatusChange(int code) {
    }


    @Override
    public void onSportStartAnimationEnable(boolean enable) {
    }

    @Override
    public void onBackPressed() {
        //没开始跑步时 用户直接点返回键 直接退出 不提示弹窗 不然很烦人
        if (mapHelper != null && !mapHelper.isStarted()) {
            finish();
        } else {
            if (mapHelper != null) {
                mapHelper.animateCameraToScreenBounds();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapHelper != null) {
            mapHelper.stopSport();
        }
    }

}
