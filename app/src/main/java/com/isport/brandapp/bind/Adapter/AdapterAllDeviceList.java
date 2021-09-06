package com.isport.brandapp.bind.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;

/**
 * @Author
 * @Date 2018/10/15
 * @Fuction
 */

public class AdapterAllDeviceList extends BaseCommonRefreshRecyclerAdapter<DeviceBean, AdapterAllDeviceList.ViewHolder> {
    public AdapterAllDeviceList(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_all_device_list;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }

    @Override
    protected void initData(ViewHolder viewHolder, int position, DeviceBean item) {
        viewHolder.tvDeviceType.setText(item.scanName);
        viewHolder.tvDeviceType.setCompoundDrawablesWithIntrinsicBounds(0,item.resId, 0, 0);
        viewHolder.viewLine.setVisibility(View.VISIBLE);
        if(item.currentType ==JkConfiguration.DeviceType.BODYFAT){
            viewHolder.viewLine.setVisibility(View.GONE);
        }else{
            viewHolder.viewLine.setVisibility(View.VISIBLE);
        }
       /* switch (item.currentType) {
            case JkConfiguration.DeviceType.WATCH_W516:
                viewHolder.tvDeviceType.setText(context.getString(R.string.watch));
                viewHolder.tvDeviceType.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_scan_watch, 0, 0);
                viewHolder.viewLine.setVisibility(View.VISIBLE);
                break;
            case JkConfiguration.DeviceType.BODYFAT:
                viewHolder.tvDeviceType.setText(context.getString(R.string.body_fat_scale));
                viewHolder.tvDeviceType.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_scan_scale, 0, 0);
                viewHolder.viewLine.setVisibility(View.GONE);
                break;
            case JkConfiguration.DeviceType.BRAND_W311:
                viewHolder.tvDeviceType.setText(context.getString(R.string.wristband));
                viewHolder.tvDeviceType.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_scan_brand, 0, 0);
                viewHolder.viewLine.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }*/
    }


    @Override
    protected void initEvent(ViewHolder viewHolder, int position, DeviceBean item) {

    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        private TextView tvDeviceType;
        private View viewLine;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDeviceType = (TextView) itemView.findViewById(R.id.tv_device_type);
            viewLine = itemView.findViewById(R.id.view_line);
        }
    }
}
