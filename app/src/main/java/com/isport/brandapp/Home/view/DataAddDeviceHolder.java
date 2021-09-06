package com.isport.brandapp.Home.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;

import java.util.List;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;

public class DataAddDeviceHolder extends CustomHolder<String> {
    ItemView view;
    TextView tvAddDevice;

    public DataAddDeviceHolder(View itemView) {
        super(itemView);
    }

    public DataAddDeviceHolder(List<String> datas, View itemView) {
        super(datas, itemView);
    }

    public DataAddDeviceHolder(Context context, final List<String> lists, int itemID) {
        super(context, lists, itemID);
        tvAddDevice = (TextView) itemView.findViewById(R.id.tv_adddevice);
        tvAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lister != null) {
                    if (ViewMultiClickUtil.onMultiClick(view)) {
                        return;
                    }
                    lister.onAddOnclick();
                }
            }
        });
    }


    OnAddOnclickLister lister;

    public void setOnCourseOnclickLister(OnAddOnclickLister lister) {
        this.lister = lister;
    }

    public interface OnAddOnclickLister {

        public void onAddOnclick();

    }


}
