package com.isport.brandapp.Home.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBaseBean;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceDataParseUtil;
import com.isport.brandapp.util.DeviceTypeUtil;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;

/**
 * Created by huashao on 2017/11/13.
 */
public class AdapterChangeDeiceList extends BaseCommonRefreshRecyclerAdapter<DeviceBean, AdapterChangeDeiceList
        .ViewHolder> {
    public AdapterChangeDeiceList(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_select_device_item_layout;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }

    @Override
    protected void initData(ViewHolder viewHolder, int position, DeviceBean item) {

    }


    @Override
    protected void initEvent(ViewHolder viewHolder, int position, DeviceBean item) {
        DeviceBaseBean bean = DeviceDataParseUtil.getDeviceTypeBean(item.currentType, item);
        if (listSource.size() - 1 == position) {
            viewHolder.view.setVisibility(View.GONE);
        } else {
            viewHolder.view.setVisibility(View.VISIBLE);
        }
        int currnetType = AppSP.getInt(context, AppSP.DEVICE_CURRENTDEVICETYPE, JkConfiguration.DeviceType.WATCH_W516);
        int type = item.currentType;
        if (type == JkConfiguration.DeviceType.SLEEP) {
            viewHolder.tvTypeName.setText(context.getResources().getString(R.string.app_device_sleep, item.deviceName));
            viewHolder.ivSelect.setVisibility(currnetType == type && AppConfiguration.isConnected ? View.VISIBLE :
                    View.GONE);
        } else if (DeviceTypeUtil.deviceWatch(type)) {
            viewHolder.tvTypeName.setText(context.getResources().getString(R.string.app_device_watch, item.deviceName));
            viewHolder.ivSelect.setVisibility(currnetType == type && AppConfiguration.isConnected ? View.VISIBLE :
                    View.GONE);
        } else if (type == JkConfiguration.DeviceType.BODYFAT) {
            viewHolder.tvTypeName.setText(context.getResources().getString(R.string.app_device_body_fat, item
                    .deviceName));
            viewHolder.ivSelect.setVisibility(currnetType == type && AppConfiguration.isConnected ? View.VISIBLE :
                    View.GONE);
        } else if (DeviceTypeUtil.deviceBrand(type)) {
            viewHolder.tvTypeName.setText(context.getResources().getString(R.string.app_device_watch, item.deviceName));
            viewHolder.ivSelect.setVisibility(currnetType == type && AppConfiguration.isConnected ? View.VISIBLE :
                    View.GONE);
        }
    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        private TextView tvTypeName;
        private ImageView ivSelect;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ivSelect = itemView.findViewById(R.id.iv_select);
            tvTypeName = itemView.findViewById(R.id.tv_name);
            view = itemView.findViewById(R.id.view_line);

        }
    }
}
