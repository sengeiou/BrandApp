package com.isport.brandapp.sport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.isport.brandapp.home.FloatWindowParamManager;
import com.isport.brandapp.home.PowerUtil;
import com.isport.brandapp.home.RomUtils;
import com.isport.brandapp.R;

import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;

public class SportPremiSettingActivity extends BaseTitleActivity {

    TextView tv_back_setting, tv_battery_setting, tv_float_setting;

    /**
     * 获取layout Id
     *
     * @return layout Id
     */
    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_sport_premi_setting;
    }

    /**
     * find控件
     *
     * @param view
     */
    @Override
    protected void initView(View view) {

        tv_back_setting = findViewById(R.id.tv_back_setting);
        tv_battery_setting = findViewById(R.id.tv_battery_setting);
        tv_float_setting = findViewById(R.id.tv_float_setting);
        boolean permission = FloatWindowParamManager.checkPermission(this);

        tv_back_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PowerUtil powerUtil = new PowerUtil();
                powerUtil.goSetting(SportPremiSettingActivity.this);

            }
        });
        tv_battery_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestChangeBatteryOptimizations();
            }
        });
        tv_float_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permission && !RomUtils.isVivoRom()) {
                    //  Toast.makeText(MainActivity.this, R.string.has_float_permission, Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(getApplicationContext(), FloatWindowService.class);
                intent.setAction(FloatWindowService.ACTION_CHECK_PERMISSION_AND_TRY_ADD);
                startService(intent);*/
                } else {
                    // Toast.makeText(MainActivity.this, R.string.no_float_permission, Toast.LENGTH_SHORT).show();
                    showOpenPermissionDialog();
                }
            }
        });

    }

    private void showOpenPermissionDialog() {
        PublicAlertDialog.getInstance().showDialog(context.getResources().getString(R.string.no_float_permission), context.getResources().getString(R.string.go_t0_open_float_ask), context, getResources().getString(R.string.common_dialog_cancel), getResources().getString(R.string.app_bluetoothadapter_turnon), new AlertDialogStateCallBack() {
            @Override
            public void determine() {
//                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivity(intent);
                FloatWindowParamManager.tryJumpToPermissionPage(SportPremiSettingActivity.this);
            }

            @Override
            public void cancel() {
                // showCountDownPopWindow();
            }
        }, false);
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent, REQCODE_OPEN_BT);


    }

    private void requestChangeBatteryOptimizations() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isIgnoringBatteryOptimizations();
            if (isIgnoringBatteryOptimizations()) {

            } else {
                try {
                    Intent intent = new Intent();
                    String packageName = this.getPackageName();
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    this.startActivity(intent);
                } catch (Exception e) {

                }
            }

            //
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isIgnoringBatteryOptimizations() {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }
        return isIgnoring;
    }

    /**
     * 数据处理
     */
    @Override
    protected void initData() {

    }

    /**
     * 设置监听
     */
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initHeader() {

    }
}
