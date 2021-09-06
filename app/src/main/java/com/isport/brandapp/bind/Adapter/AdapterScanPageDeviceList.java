package com.isport.brandapp.bind.Adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.brandapp.R;
import com.isport.brandapp.util.DeviceTypeUtil;

import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;

/**
 * @Author
 * @Date 2018/10/15
 * @Fuction
 */

public class AdapterScanPageDeviceList extends BaseCommonRefreshRecyclerAdapter<BaseDevice, AdapterScanPageDeviceList
        .ViewHolder> {
    boolean isDfu;

    public AdapterScanPageDeviceList(Context context, boolean isDfu) {
        super(context);
        this.isDfu = isDfu;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_scan_device_list;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }

    @Override
    protected void initData(ViewHolder viewHolder, int position, BaseDevice item) {
        if (item != null) {
            viewHolder.tvDeviceBind.setVisibility(View.VISIBLE);
            if (isDfu) {
                viewHolder.tvDeviceBind.setText(UIUtils.getString(R.string.devcie_select));
            } else {
                viewHolder.tvDeviceBind.setText(UIUtils.getString(R.string.pair));
            }
            // viewHolder.tvDeviceName.setText(item.deviceName + item.getAddress());

            if (DeviceTypeUtil.isContaintW81(item.deviceType)) {
                // Utils.resetDeviceMac(bindDevice.getDeviceId()
                viewHolder.tvDeviceName.setText(DeviceTypeUtil.getW81DeviceName(item.deviceName, item.getAddress()));
            } else {
                viewHolder.tvDeviceName.setText(item.deviceName);
            }
        } else {
            viewHolder.tvDeviceBind.setVisibility(View.GONE);
        }
    }


    @Override
    protected void initEvent(ViewHolder viewHolder, int position, final BaseDevice item) {

        viewHolder.tvDeviceBind.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (lister != null) {
                            if (!ViewMultiClickUtil.onMultiClick(v)) {
                                lister.onBindDeviceOnclick(item);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return false;
            }
        });
//        viewHolder.tvDeviceBind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (lister != null) {
//                    if (ViewMultiClickUtil.onMultiClick(v)) {
//                        Logger.myLog("111111111111111111");
//                        return;
//                    }
//                    lister.onBindDeviceOnclick(item);
//                }
//            }
//        });
    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        private TextView tvDeviceBind;
        private TextView tvDeviceName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDeviceName = (TextView) itemView.findViewById(R.id.tv_device_name);
            tvDeviceBind = (TextView) itemView.findViewById(R.id.tv_device_bind);
        }
    }

    OnBindOnclickLister lister;

    public void setOnBindOnclickLister(OnBindOnclickLister lister) {
        this.lister = lister;
    }

    public interface OnBindOnclickLister {
        public void onBindDeviceOnclick(BaseDevice baseDevice);
    }
}
