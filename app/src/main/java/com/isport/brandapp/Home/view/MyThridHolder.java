package com.isport.brandapp.Home.view;

import android.content.Context;
import android.view.View;

import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;

import java.util.List;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;

public class MyThridHolder extends CustomHolder<String> {
    //List<FirstLevelsBean> listBeans;
    ItemView itemViewMythrid;
    ItemView itemViewDeviceUpgrade;
    ItemView itemViewSetting;

    public MyThridHolder(View itemView) {
        super(itemView);
    }

    public MyThridHolder(List<String> datas, View itemView) {
        super(datas, itemView);
    }

    public MyThridHolder(final Context context, final List<String> lists, int itemID) {
        super(context, lists, itemID);
        itemViewMythrid = itemView.findViewById(R.id.itemview_mythrid);
        itemViewDeviceUpgrade = itemView.findViewById(R.id.itemview_fireware_upgrade);
        itemViewSetting = itemView.findViewById(R.id.itemview_setting);
        itemViewMythrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ViewMultiClickUtil.onMultiClick(view)) {
                    return;
                }
                if (listener != null) {
                    listener.onThirdDeviceItemListener();
                }
            }
        });
        itemViewDeviceUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                if (listener != null) {
                    listener.onDevcieUpgrade();
                }
            }
        });
        itemViewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                if (listener != null) {
                    listener.onDevcieSetting();
                }
            }
        });

    }


    OnThridItemClickListener listener;

    public void setOnItemClickListener(OnThridItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnThridItemClickListener {
        void onThirdDeviceItemListener();

        void onDevcieUpgrade();

        void onDevcieSetting();

    }


}
