package com.isport.brandapp.bind;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.observe.RopeNoDataObservable;
import com.isport.blelibrary.observe.RopeSyncDataObservable;
import com.isport.blelibrary.observe.SyncProgressObservable;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.BleSPUtils;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.SyncCacheUtils;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.bind.Adapter.AdapterBindPageDeviceList;
import com.isport.brandapp.bind.presenter.BindPresenter;
import com.isport.brandapp.bind.view.BindBaseView;
import com.isport.brandapp.device.publicpage.GoActivityUtil;
import com.isport.brandapp.dialog.UnBindDeviceDialog;
import com.isport.brandapp.dialog.UnbindStateCallBack;
import com.isport.brandapp.util.ActivitySwitcher;
import com.isport.brandapp.util.DeviceTypeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Observable;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonrecyclerview.FullyLinearLayoutManager;
import brandapp.isport.com.basicres.commonrecyclerview.RefreshRecyclerView;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.NetUtils;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * @Author
 * @Date 2018/10/15
 * @Fuction
 */

public class ActivityBindRope extends BaseMVPTitleActivity<BindBaseView, BindPresenter> implements BindBaseView {
    private RefreshRecyclerView refreshRecyclerView;
    private AdapterBindPageDeviceList adapterBindPageDeviceList;
    private boolean hasWatch;
    ArrayList<DeviceBean> list = new ArrayList<>();
    public static ActivityBindRope mActivityBind;
    private int currentType;
    boolean isDerictUnBind;
    private DeviceBean mDeviceBean;
    private boolean canUnBind;

    Handler handler = new Handler();

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (o instanceof RopeSyncDataObservable) {
                    SyncProgressObservable.getInstance().hide();
                    unBindDevice(mDeviceBean, false);

                } else if (o instanceof RopeNoDataObservable) {
                    SyncProgressObservable.getInstance().hide();
                    unBindDevice(mDeviceBean, false);
                }
            }
        }, 10);

    }

    @Override
    protected BindPresenter createPresenter() {
        return new BindPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_bind;
    }

    @Override
    protected void initView(View view) {
        canUnBind = false;
        mActivityBind = this;
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        refreshRecyclerView = (RefreshRecyclerView) view.findViewById(R.id.recycler_device);
        adapterBindPageDeviceList = new AdapterBindPageDeviceList(this);
        //TODO 俱乐部名称 recycler_club_content
        FullyLinearLayoutManager mClubFullyLinearLayoutManager = new FullyLinearLayoutManager(context);
        mClubFullyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshRecyclerView.setLayoutManager(mClubFullyLinearLayoutManager);
        refreshRecyclerView.setAdapter(adapterBindPageDeviceList);
        ISportAgent.getInstance().registerListener(mBleReciveListener);
        refreshRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ViewMultiClickUtil.onMultiClick()) {
                    return;
                }
                onItemClickAction(position);
            }
        });
        adapterBindPageDeviceList.setData(list);
    }

    int count = 0;

    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {
            if (isConn) {
            } else {
                NetProgressObservable.getInstance().hide();
            }
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            /*if (mResult != null) {

            }*/
        }

        @Override
        public void onConnecting(BaseDevice baseDevice) {

        }

        @Override
        public void onBattreyOrVersion(BaseDevice baseDevice) {

        }
    };

    /**
     * 暂隐藏sleep
     */
    private void getDeviceList() {
        list.clear();
        list.add(new DeviceBean(JkConfiguration.DeviceType.ROPE_SKIPPING, String.format(UIUtils.getString(R.string.detail_rope), Constants.ROPE_S002_FILTER), R.drawable.icon_scan_rope));
        hasWatch = false;

        Logger.myLog("getDeviceList=" + AppConfiguration.deviceMainBeanList);

        if (AppConfiguration.deviceMainBeanList != null) {
            for (int deviceType : AppConfiguration.deviceMainBeanList.keySet()) {
                DeviceBean deviceBean = AppConfiguration.deviceMainBeanList.get(deviceType);
                Logger.myLog("getDeviceList=" + deviceBean);
                switch (deviceBean.currentType) {
                    case JkConfiguration.DeviceType.ROPE_SKIPPING:
                        updateList(0, deviceBean);
                        break;
                }
                hasWatch = true;

            }
        }
        adapterBindPageDeviceList.notifyDataSetChanged();
    }


    public void updateList(int index, DeviceBean deviceBean) {
        deviceBean.scanName = list.get(index).scanName;
        deviceBean.resId = list.get(index).resId;
        list.set(index, deviceBean);
    }


    private void onItemClickAction(int position) {


        mDeviceBean = list.get(position);
        if (AppConfiguration.deviceMainBeanList != null && AppConfiguration.deviceMainBeanList.size() > 0) {
            if (AppConfiguration.deviceMainBeanList.containsKey(JkConfiguration.DeviceType.ROPE_SKIPPING)) {
                Logger.myLog("showUnbindDialog");
                showUnbindDialog();
            } else {
                Logger.myLog("去断开连接00000000000");
                ISportAgent.getInstance().disConDevice(false);
                // }
                ActivitySwitcher.goScanActivty(ActivityBindRope.this, mDeviceBean.currentType);
            }

        } else {
            Logger.myLog("去断开连接00000000000");
            ISportAgent.getInstance().disConDevice(false);
            // }
            ActivitySwitcher.goScanActivty(ActivityBindRope.this, mDeviceBean.currentType);
        }

    }

    private void unBindDevice(DeviceBean deviceBean, boolean isDe) {
        isDerictUnBind = true;
        currentType = deviceBean.deviceType;
        Logger.myLog("点击去解绑 == " + currentType);
        mActPresenter.unBind(deviceBean, isDe);
    }

    @Override
    protected void initData() {
        RopeSyncDataObservable.getInstance().addObserver(this);
        RopeNoDataObservable.getInstance().addObserver(this);
        Constants.isDFU = false;
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(R.string.select_device_type_rope);
        titleBarView.setRightText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppConfiguration.isBindList = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppConfiguration.isBindList = true;
        getDeviceList();

    }

    @Override
    protected void initEvent() {
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.GET_BIND_DEVICELIST_SUCCESS:
                getDeviceList();
                adapterBindPageDeviceList.notifyDataSetChanged();
                break;
            case MessageEvent.UNBIND_DEVICE_SUCCESS_EVENT:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int deviceType = (int) messageEvent.getObj();
                        if (deviceType == JkConfiguration.DeviceType.SLEEP) {
                            brandapp.isport.com.basicres.commonutil.Logger.e("睡眠带解绑成功");
                            TokenUtil.getInstance().saveSleepTime(BaseApp.getApp(), "");
                        }
                        Logger.myLog("解绑成功");
                        hasWatch = false;
                        //EventBus.getDefault().post(new MessageEvent(MessageEvent.UNBIND_DEVICE_SUCCESS));
                    }
                }, 200);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RopeSyncDataObservable.getInstance().deleteObserver(this);
        RopeNoDataObservable.getInstance().deleteObserver(this);
        Constants.isSyncUnbind = false;
        EventBus.getDefault().unregister(this);
        SyncCacheUtils.setUnBindState(false);
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }


    @Override
    public void onUnBindSuccess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Logger.myLog("onUnBindSuccess 解绑成功");
                hasWatch = false;
                ToastUtils.showToast(context, UIUtils.getString(R.string.unpair_successfully));
                EventBus.getDefault().post(new MessageEvent(MessageEvent.UNBIND_DEVICE_SUCCESS));
                GoActivityUtil.goActivityUnbindDevice(currentType, ActivityBindRope.this);

                BaseDevice currnetDevice = ISportAgent.getInstance().getCurrnetDevice();
                if (currnetDevice != null && currnetDevice.deviceType == currentType) {
                    Logger.myLog("currnetDevice == " + currentType);
                    //解绑设备不用重连
                    ISportAgent.getInstance().unbind(false);

                }
            }
        }, 200);
    }

    @Override
    public void updateWatchDataSuccess(DeviceBean deviceBean) {
        unBindDevice(deviceBean, false);
    }

    @Override
    public void updateSleepDataSuccess(DeviceBean deviceBean) {
        unBindDevice(deviceBean, false);
    }

    @Override
    public void updateWatchHistoryDataSuccess(DeviceBean deviceBean) {
        unBindDevice(mDeviceBean, false);
    }

    @Override
    public void updateFail() {
        ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.error_Connection_timeout));
    }


    public void showUnbindDialog() {
        //w516 w311 w520只会存在一个
        isDerictUnBind = false;
        isDerictUnBind = false;
        mDeviceBean = DeviceTypeUtil.getRopeDevice();
        if (mDeviceBean == null) {
            return;
        }
        new UnBindDeviceDialog(this, JkConfiguration.DeviceType.ROPE_SKIPPING, true, new UnbindStateCallBack() {
            @Override
            public void synUnbind() {
                if (!NetUtils.hasNetwork(BaseApp.getApp())) {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.common_please_check_that_your_network_is_connected));
                    return;
                }
                if (AppConfiguration.isConnected) {
                    BaseDevice device = ISportAgent.getInstance().getCurrnetDevice();
                    int deviceType = 0;
                    if (device != null) {
                        deviceType = device.deviceType;
                    }
                    if (deviceType != JkConfiguration.DeviceType.ROPE_SKIPPING) {
                        ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                        return;
                    }
                    if (DeviceTypeUtil.isContainWrishBrand(deviceType) || DeviceTypeUtil.isContainW81(deviceType)) {
                        canUnBind = true;
                        //睡眠带连接
                        ISportAgent.getInstance().requestBle(BleRequest.bracelet_sync_data);
                    } else if (DeviceTypeUtil.isContainWatch(deviceType)) {
                        canUnBind = true;
                        //睡眠带连接
                        String string = BleSPUtils.getString(BaseApp.getApp(), BleSPUtils.WATCH_LAST_SYNCTIME, TimeUtils.getTodayYYYYMMDD());
                        ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_GET_DAILY_RECORD, string);
                    } else {
                        SyncCacheUtils.setUnBindState(true);
                        ISportAgent.getInstance().requestBle(BleRequest.bracelet_sync_data);
                    }

                } else {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.app_disconnect_device));
                }
            }

            @Override
            public void dirctUnbind() {
                if (!NetUtils.hasNetwork(BaseApp.getApp())) {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.common_please_check_that_your_network_is_connected));
                    return;
                }
                unBindDevice(mDeviceBean, true);
            }

            @Override
            public void cancel() {

            }
        }, mDeviceBean.currentType);
    }
}
