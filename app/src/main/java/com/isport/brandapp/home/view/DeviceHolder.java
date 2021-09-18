package com.isport.brandapp.home.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isport.brandapp.home.adpter.AdapterMineDeviceList;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;
import com.isport.brandapp.bean.DeviceBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import brandapp.isport.com.basicres.commonrecyclerview.FullyLinearLayoutManager;
import brandapp.isport.com.basicres.commonrecyclerview.RefreshRecyclerView;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;

public class DeviceHolder extends CustomHolder<String> {
    RefreshRecyclerView refreshRecyclerView;
    TextView tvAdDevice;
    TextView tvLog;
    LinearLayout layout_empty_view;
    AdapterMineDeviceList adapterFragmentMineMid;
    //List<FirstLevelsBean> listBeans;

    public DeviceHolder(View itemView) {
        super(itemView);
    }

    public DeviceHolder(List<String> datas, View itemView) {
        super(datas, itemView);
    }

    public DeviceHolder(final Context context, final List<String> lists, int itemID) {
        super(context, lists, itemID);
        refreshRecyclerView = (RefreshRecyclerView) itemView.findViewById(R.id.recycler_device);
        tvAdDevice = (TextView) itemView.findViewById(R.id.tv_ad_device);
        tvLog = (TextView) itemView.findViewById(R.id.tv_log);
        adapterFragmentMineMid = new AdapterMineDeviceList(context);
        layout_empty_view = itemView.findViewById(R.id.layout_empty_view);
        layout_empty_view.setVisibility(View.VISIBLE);
        refreshRecyclerView.setVisibility(View.GONE);
        //TODO 俱乐部名称 recycler_club_content
        FullyLinearLayoutManager mClubFullyLinearLayoutManager = new FullyLinearLayoutManager(context);
        mClubFullyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshRecyclerView.setLayoutManager(mClubFullyLinearLayoutManager);
        refreshRecyclerView.setAdapter(adapterFragmentMineMid);
        refreshRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (ViewMultiClickUtil.onMultiClick(view)) {
                    return;
                }
                final DeviceBean bean = adapterFragmentMineMid.getItem(position);
                if (listener != null) {
                    listener.onDeviceItemListener(position, bean);
                }
            }
        });


        tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lister != null) {
                    lister.onLog();
                }
            }
        });

        tvAdDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lister != null) {
                    if (ViewMultiClickUtil.onMultiClick(view)) {
                        return;
                    }
                    lister.onAddDeviceOnclick();
                }
            }
        });

    }

    public void setData(ArrayList<DeviceBean> deviceList) {
        if (deviceList.size() > 0) {
            layout_empty_view.setVisibility(View.GONE);
            refreshRecyclerView.setVisibility(View.VISIBLE);
        } else {
            layout_empty_view.setVisibility(View.VISIBLE);
            refreshRecyclerView.setVisibility(View.GONE);
        }
        adapterFragmentMineMid.setData(deviceList);
       /* this.listBeans.clear();
        this.listBeans = listBeans;
        adapterFragmentMineMid.setData(this.listBeans);
        adapterFragmentMineMid.notifyDataSetChanged();*/
    }

    public void notifyList(ArrayList<DeviceBean> deviceList) {
        setData(deviceList);
        adapterFragmentMineMid.notifyDataSetChanged();
    }

    OnDeviceItemClickListener listener;

    public void setOnItemClickListener(OnDeviceItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnDeviceItemClickListener {
        void onDeviceItemListener(int position, DeviceBean bean);
    }

    DeviceHolder.OnDeviceOnclickListenter lister;

    public void setOnCourseOnclickLister(DeviceHolder.OnDeviceOnclickListenter lister) {
        this.lister = lister;
    }


    public interface OnDeviceOnclickListenter {
        public void onAddDeviceOnclick();

        public void onLog();

    }
}
