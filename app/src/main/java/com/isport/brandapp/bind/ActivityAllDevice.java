package com.isport.brandapp.bind;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.bind.Adapter.DeviceListAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class ActivityAllDevice extends BaseTitleActivity {
    private RecyclerView refreshRecyclerView;
    private DeviceListAdapter adapterAllDeviceList;
    private RelativeLayout layout;
    ArrayList<DeviceBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_all_device_bind;
    }

    @Override
    protected void initView(View view) {
        titleBarView.setTitle(UIUtils.getString(R.string.select_device_type));
        refreshRecyclerView = view.findViewById(R.id.recycler_device);
        getDeviceList();
        adapterAllDeviceList = new DeviceListAdapter(list);
        layout = view.findViewById(R.id.layout);
        layout.setBackgroundColor(getResources().getColor(R.color.white));


        //TODO 俱乐部名称 recycler_club_content
        LinearLayoutManager mClubFullyLinearLayoutManager = new LinearLayoutManager(context);
        mClubFullyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshRecyclerView.setLayoutManager(mClubFullyLinearLayoutManager);
        refreshRecyclerView.setAdapter(adapterAllDeviceList);


    }

    private void onItemClickAction(int position) {
        ViewMultiClickUtil.clearTimeNoView();
        if (null != list && position < list.size()) {
            Intent mIntent = null;
            DeviceBean deviceBean = list.get(position);
            switch (deviceBean.currentType) {
                case JkConfiguration.DeviceType.WATCH_W516:
                    // mIntent = new Intent(ActivityAllDevice.this, ActivityScan.class);
                    // mIntent.putExtra("device_type", JkConfiguration.DeviceType.WATCH_W516);
                    mIntent = new Intent(ActivityAllDevice.this, ActivityBindWatch.class);
                    break;
                case JkConfiguration.DeviceType.BRAND_W311:
                    //mIntent = new Intent(ActivityAllDevice.this, ActivityScan.class);
                    // mIntent.putExtra("device_type", JkConfiguration.DeviceType.BRAND_W311);
                    mIntent = new Intent(ActivityAllDevice.this, ActivityBindWrishBrand.class);
                    break;
                case JkConfiguration.DeviceType.BODYFAT:
                    // mIntent = new Intent(ActivityAllDevice.this, ActivityScaleScan.class);
                    //mIntent.putExtra("isConnect", false);
                    mIntent = new Intent(ActivityAllDevice.this, ActivityBindScale.class);
                    break;
                case JkConfiguration.DeviceType.ROPE_SKIPPING:
                    mIntent = new Intent(ActivityAllDevice.this, ActivityBindRope.class);
                    break;
            }
            if (mIntent != null) {
                startActivity(mIntent);
            }
        }
    }

    @Override
    protected void initData() {

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
        adapterAllDeviceList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (ViewMultiClickUtil.onMultiClick()) {
                    return;
                }
                onItemClickAction(position);
            }
        });
    }

    @Override
    protected void initHeader() {

    }


    private void getDeviceList() {
        list.clear();
        list.add(new DeviceBean(JkConfiguration.DeviceType.WATCH_W516, R.drawable.shape_scan_bg_watch, UIUtils.getString(R.string.watch), R.drawable.icon_scan_watch));
        list.add(new DeviceBean(JkConfiguration.DeviceType.BRAND_W311, R.drawable.shape_scan_bg_band, context.getString(R.string.wristband), R.drawable.icon_scan_brand));
        list.add(new DeviceBean(JkConfiguration.DeviceType.BODYFAT, R.drawable.shape_scan_bg_scale, UIUtils.getString(R.string.body_fat_scale), R.drawable.icon_scan_scale));
        list.add(new DeviceBean(JkConfiguration.DeviceType.ROPE_SKIPPING, R.drawable.shape_scan_rope_skipping, UIUtils.getString(R.string.rope_device), R.drawable.icon_scan_rope_skipping));
    }

}
