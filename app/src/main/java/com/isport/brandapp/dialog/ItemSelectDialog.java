package com.isport.brandapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.Utils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.adpter.AdapterChangeDeiceList;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.util.AppSP;

import java.util.ArrayList;

import brandapp.isport.com.basicres.commonrecyclerview.FullyLinearLayoutManager;
import brandapp.isport.com.basicres.commonrecyclerview.RefreshRecyclerView;
import brandapp.isport.com.basicres.commonutil.AppUtil;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class ItemSelectDialog {
    private OnTypeClickListenter onTypeClickListenter;
    private Activity mActivity;
    public AlertDialog dialog;
    RefreshRecyclerView refreshRecyclerView;
    AdapterChangeDeiceList adapter;
    private DeviceBean mBean;
    LinearLayout layout;

    public ItemSelectDialog(Activity activity, final OnTypeClickListenter onTypeClickListenter) {
        this.mActivity = activity;
        this.onTypeClickListenter = onTypeClickListenter;
        dialog = new AlertDialog.Builder(mActivity, R.style.alert_dialog).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_item_select);
        refreshRecyclerView = dialog.getWindow().findViewById(R.id.recycler);
        ISportAgent.getInstance().registerListener(mBleReciveListener);
        layout = dialog.getWindow().findViewById(R.id.layout);

        adapter = new AdapterChangeDeiceList(activity);
        //TODO 俱乐部名称 recycler_club_content
        FullyLinearLayoutManager mClubFullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        mClubFullyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshRecyclerView.setLayoutManager(mClubFullyLinearLayoutManager);
        refreshRecyclerView.setAdapter(adapter);
        ArrayList<DeviceBean> list = new ArrayList<>();
        for (int deviceType : AppConfiguration.deviceBeanList.keySet()) {
            if (deviceType == JkConfiguration.DeviceType.ROPE_SKIPPING) {
                continue;
            }
            list.add(AppConfiguration.deviceBeanList.get(deviceType));
        }
        adapter.setData(list);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog();
            }
        });
        refreshRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBean = adapter.getItem(position);
                Logger.myLog("ItemSelectDialog:" + mBean.toString() + "AppConfiguration.isConnected:" + AppConfiguration.isConnected);
                int currenType;
                if (ISportAgent.getInstance().getCurrnetDevice() != null) {
                    currenType = ISportAgent.getInstance().getCurrnetDevice().deviceType;
                } else {
                    currenType = JkConfiguration.DeviceType.WATCH_W516;
                }

                if (AppConfiguration.isConnected) {
                    //1.如果点击的和连接的是同一个设备，就直接隐藏对话框即可
                    if (mBean.currentType == currenType) {
                        cancelDialog();
                    } else {
                        //connect();
                        //2.如果需要切换设备连接需要断开再去连接
                        ISportAgent.getInstance().disConDevice(false);
                        //如果是体脂称，这样的操作不需要去重新连接
                        if (currenType == JkConfiguration.DeviceType.BODYFAT) {
                            AppConfiguration.isScaleScan = true;
                        }
                    }
                } else {

                    connect();
                }
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ISportAgent.getInstance().unregisterListener(mBleReciveListener);
            }
        });
    }

    Handler handler = new Handler();

    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {
            if (isConn) {
                Logger.myLog("连接成功00000000");
            } else {
                Logger.myLog("连接断开11111111");
                int currentType = AppSP.getInt(mActivity, AppSP.DEVICE_CURRENTDEVICETYPE, JkConfiguration.DeviceType.WATCH_W516);
                if (currentType == baseDevice.deviceType) {
                    Logger.myLog("连接断开222222222222");
                    //重连
                    connect();


                }
            }
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            if (mResult != null)
                switch (mResult.getType()) {
                    default:
                        break;
                }

        }

        @Override
        public void onConnecting(BaseDevice baseDevice) {

        }

        @Override
        public void onBattreyOrVersion(BaseDevice baseDevice) {

        }
    };


    private void connect() {
        if (onTypeClickListenter != null) {
            if (AppUtil.isOpenBle()) {
                if (mBean == null)
                    return;
                switch (mBean.deviceType) {
                    case JkConfiguration.DeviceType.WATCH_W516:
                    case JkConfiguration.DeviceType.BRAND_W311:
                    case JkConfiguration.DeviceType.Brand_W520:
                    case JkConfiguration.DeviceType.Brand_W811:
                    case JkConfiguration.DeviceType.Brand_W814:
                    case JkConfiguration.DeviceType.Watch_W812:
                    case JkConfiguration.DeviceType.Watch_W819:
                    case JkConfiguration.DeviceType.Watch_W813:
                        //updateHeardLayout(R.drawable.icon_watch, 800, JkConfiguration.IDeviceType.WATCH_W516, "3264米",
                        // "56千卡");
                        if (mBean.mac != null)
                            if (!mBean.mac.startsWith("B")) {
                                String watchMac = Utils.resetDeviceMac(mBean.mac);
                                Logger.myLog("Connect to " + watchMac);
                                if (!StringUtil.isBlank(watchMac)) {
                                    // TODO: 2018/10/25 搜索到设备后去
                                    AppSP.putString(mActivity, AppSP.WATCH_MAC, watchMac);
                                }
                            }
                        break;
                    case JkConfiguration.DeviceType.SLEEP:
                        //updateHeardLayout(R.drawable.icon_sleep, 85, JkConfiguration.IDeviceType.SLEEP, "7h25min",
                        // "深睡:20%");
                        if (mBean.mac != null) {
                            if (!mBean.mac.startsWith("Z")) {
                                String sleepMac = Utils.resetDeviceMac(mBean.mac);
                                Logger.myLog("Connect to " + sleepMac);
                                if (!StringUtil.isBlank(sleepMac)) {
                                    // TODO: 2018/10/25 搜索到设备后去
                                    AppSP.putString(mActivity, AppSP.SLEEP_MAC, sleepMac);
                                }
                            }
                        }
                        break;
                    case JkConfiguration.DeviceType.BODYFAT:
                        //updateHeardLayout(R.drawable.icon_body_fat, 40, JkConfiguration.IDeviceType.BODYFAT,
                        // "BMI:20.3", "体脂率:17.3%");
                        //ScaleTipObservable.getInstance().show();
                        String scaleMac = Utils.resetDeviceMac(mBean.mac);
                        Logger.myLog("Connect to " + scaleMac);
                        AppSP.putString(mActivity, AppSP.SCALE_MAC, scaleMac);
                        break;
                }
                AppSP.putInt(mActivity, AppSP.DEVICE_CURRENTDEVICETYPE, mBean.deviceType);
                // TODO: 2018/10/25 连接时刷新最新的值,各个设备的mac存储
                AppSP.putString(mActivity, AppSP.DEVICE_CURRENTNAME, mBean.deviceID);
                cancelDialog();
                onTypeClickListenter.changeDevcieonClick(mBean.deviceType, mBean.mac, mBean.deviceID);
            } else {
                cancelDialog();
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mActivity.startActivityForResult(intent, REQCODE_OPEN_BT);
            }

        }
    }

    public void cancelDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static final int REQCODE_OPEN_BT = 0x100;

    public interface OnTypeClickListenter {
        void changeDevcieonClick(int type, String mac, String deviceID);
    }

}
