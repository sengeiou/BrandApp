package com.isport.brandapp.upgrade;

import android.view.View;
import android.widget.AdapterView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.utils.Constants;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.upgrade.adapter.AdapterUpgradeDeviceList;
import com.isport.brandapp.util.ActivitySwitcher;
import com.isport.brandapp.util.AppSP;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonrecyclerview.FullyLinearLayoutManager;
import brandapp.isport.com.basicres.commonrecyclerview.RefreshRecyclerView;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class DFUDeviceSelectActivity extends BaseTitleActivity {
    private RefreshRecyclerView refreshRecyclerView;
    private AdapterUpgradeDeviceList adapterBindPageDeviceList;
    ArrayList<DeviceBean> list = new ArrayList<>();

    String deviceName, deviceMac;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_upgrade;
    }

    @Override
    protected void initView(View view) {
        getIntenValue();
        AppSP.putString(context, AppSP.WATCH_MAC, "");
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        refreshRecyclerView = (RefreshRecyclerView) view.findViewById(R.id.recycler_device);
        adapterBindPageDeviceList = new AdapterUpgradeDeviceList(this);
        //TODO 俱乐部名称 recycler_club_content
        FullyLinearLayoutManager mClubFullyLinearLayoutManager = new FullyLinearLayoutManager(context);
        mClubFullyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshRecyclerView.setLayoutManager(mClubFullyLinearLayoutManager);
        refreshRecyclerView.setAdapter(adapterBindPageDeviceList);
        getDeviceList();
        refreshRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClickAction(position);
            }
        });

    }


    private void getIntenValue() {
        deviceName = getIntent().getStringExtra(JkConfiguration.DEVICE_NAME);
        deviceMac = getIntent().getStringExtra(JkConfiguration.DEVICE_MAC);
    }

    /**
     * 暂隐藏sleep
     */
    private void getDeviceList() {
        list.clear();
        if (deviceName.toUpperCase().contains("DFU")) {
            list.add(new DeviceBean(JkConfiguration.DeviceType.WATCH_W516, Constants.WATCH_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.BRAND_W311, Constants.BRAND_W311N_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Brand_W520, Constants.BRAND_520_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.BRAND_W307J, Constants.BRAND_W307J_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Watch_W812, Constants.WATCH_812_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Watch_W819, Constants.WATCH_910_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Watch_W556, Constants.WATCH_556_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Watch_W557, Constants.WATCH_557_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Watch_W812B, Constants.WATCH_812B_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Watch_W560, Constants.WATCH_560_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Watch_W560B, Constants.WATCH_560B_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.ROPE_SKIPPING, Constants.ROPE_S002_FILTER));
        } else {
            list.add(new DeviceBean(JkConfiguration.DeviceType.Watch_W813, Constants.WATCH_813_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Brand_W814, Constants.BRAND_814_FILTER));
            list.add(new DeviceBean(JkConfiguration.DeviceType.Watch_W819, Constants.WATCH_819_FILTER));

        }
        adapterBindPageDeviceList.setData(list);
        adapterBindPageDeviceList.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        titleBarView.setTitle(UIUtils.getString(R.string.select_device_type_title));
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


    @Override
    protected void initHeader() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        //  ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //  ISportAgent.getInstance().registerListener(mBleReciveListener);
    }

    private void onItemClickAction(int position) {
        if (position < list.size()) {
            DeviceBean bean = list.get(position);
            //需要升级的类型，名字，mac

            ActivitySwitcher.goDFUAct(context, bean.currentType, deviceName, deviceMac, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ISportAgent.getInstance().disConDevice(false);
        Constants.isDFU = false;
        AppSP.putString(context, AppSP.WATCH_MAC, "");
    }
}
