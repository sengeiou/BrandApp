package com.isport.brandapp.upgrade.adapter;

import android.content.Context;
import android.view.View;

import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;

import bike.gymproject.viewlibray.ItemView;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;

/**
 * @Author
 * @Date 2018/10/15
 * @Fuction
 */

public class AdapterUpgradeDeviceList extends BaseCommonRefreshRecyclerAdapter<DeviceBean, AdapterUpgradeDeviceList.ViewHolder> {
    public AdapterUpgradeDeviceList(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_upgrade_device_list;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }

    @Override
    protected void initData(ViewHolder viewHolder, int position, DeviceBean item) {

        viewHolder.itemViewDevice.setTitleText(item.deviceName);
    }


    @Override
    protected void initEvent(ViewHolder viewHolder, int position, DeviceBean item) {

    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        ItemView itemViewDevice;

        public ViewHolder(View itemView) {
            super(itemView);
            itemViewDevice = itemView.findViewById(R.id.itemview_device);
        }
    }
}
