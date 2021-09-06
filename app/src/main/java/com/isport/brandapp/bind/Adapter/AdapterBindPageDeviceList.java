package com.isport.brandapp.bind.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport.blelibrary.utils.Utils;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;

/**
 * @Author
 * @Date 2018/10/15
 * @Fuction
 */

public class AdapterBindPageDeviceList extends BaseCommonRefreshRecyclerAdapter<DeviceBean, AdapterBindPageDeviceList.ViewHolder> {
    public AdapterBindPageDeviceList(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_bind_device_list;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }

    @Override
    protected void initData(ViewHolder viewHolder, int position, DeviceBean item) {
        viewHolder.ivDevice.setBackgroundResource(item.resId);
        viewHolder.tvDeviceType.setText(item.scanName);
        /*switch (item.currentType) {



            case JkConfiguration.DeviceType.Watch_W812:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_scan_w812);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_watch), Constants.WATCH_812_FILTER));
                break;
            case JkConfiguration.DeviceType.Watch_W813:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_scan_w813);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_watch), Constants.WATCH_813_FILTER));
                break;
            case JkConfiguration.DeviceType.Watch_W556:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_scan_w556);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_watch), Constants.WATCH_556_FILTER));
                break;
            case JkConfiguration.DeviceType.Watch_W910:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_scan_w910);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_watch), Constants.WATCH_910_FILTER));
                break;
            case JkConfiguration.DeviceType.Watch_W819:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_scan_w819);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_watch), Constants.WATCH_819_FILTER));
                break;
            case JkConfiguration.DeviceType.Brand_W814:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_scan_w814);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_wristband), Constants.BRAND_814_FILTER));
                break;
            case JkConfiguration.DeviceType.Brand_W811:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_scan_w811);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_wristband), Constants.BRAND_811_FILTER));
                break;
            case JkConfiguration.DeviceType.WATCH_W516:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_w516);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_watch), Constants.WATCH_FILTER));
                break;
            case JkConfiguration.DeviceType.BODYFAT:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_device_scale);
                viewHolder.tvDeviceType.setText(context.getString(R.string.body_fat_scale));
                break;
            case JkConfiguration.DeviceType.BRAND_W311:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_w311n);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_wristband), Constants.BRAND_W311N_FILTER));
                break;
            case JkConfiguration.DeviceType.Brand_W520:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_w520);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_wristband), Constants.BRAND_520_FILTER));
                break;
            case JkConfiguration.DeviceType.Watch_W817:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_scan_w817);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_watch), Constants.WATCH_817_FILTER));
                break;
            case JkConfiguration.DeviceType.Watch_W557:
                viewHolder.ivDevice.setBackgroundResource(R.drawable.icon_scan_w557);
                viewHolder.tvDeviceType.setText(String.format(UIUtils.getString(R.string.detail_watch), Constants.WATCH_557_FILTER));
                break;
            default:
                break;
        }*/
        viewHolder.tvDeviceBind.setText(context.getString(R.string.paired, Utils.isEmpty(item.deviceName) ? "" : item.deviceName));
        viewHolder.tvDeviceBind.setVisibility(Utils.isEmpty(item.deviceName) ? View.GONE : View.VISIBLE);
    }


    @Override
    protected void initEvent(ViewHolder viewHolder, int position, DeviceBean item) {

    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        private ImageView ivDevice;
        private TextView tvDeviceType;
        private TextView tvDeviceBind;
        private RelativeLayout rlBind;

        public ViewHolder(View itemView) {
            super(itemView);
            ivDevice = (ImageView) itemView.findViewById(R.id.iv_device);
            rlBind = (RelativeLayout) itemView.findViewById(R.id.rl_bind);
            tvDeviceType = (TextView) itemView.findViewById(R.id.tv_device_type);
            tvDeviceBind = (TextView) itemView.findViewById(R.id.tv_device_bind);
        }
    }
}
