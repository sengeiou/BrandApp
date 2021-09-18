package com.isport.brandapp.home.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;

import java.util.List;

import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;

public class DataDeviceSportHolder extends CustomHolder<String> {
   /* ItemView view;
    private DeviceItemView deviceItemView;*/

    TextView tv_sport_time;
    TextView tv_day_sum_min;
    TextView tv_start_sport;
    ImageView  iv_change;


    public DataDeviceSportHolder(View itemView) {
        super(itemView);
    }

    public DataDeviceSportHolder(List<String> datas, View itemView) {
        super(datas, itemView);
    }

    public DataDeviceSportHolder(Context context, final List<String> lists, int itemID) {
        super(context, lists, itemID);
       /* view = itemView.findViewById(R.id.itemview);
        deviceItemView = itemView.findViewById(R.id.view_device_item);
        view.setBg(R.drawable.common_main_item_selector);*/
        tv_sport_time = itemView.findViewById(R.id.tv_sport_time);
        tv_day_sum_min = itemView.findViewById(R.id.tv_day_sum_min);
        iv_change = itemView.findViewById(R.id.iv_change);
        tv_start_sport = itemView.findViewById(R.id.tv_start_sport);
        //ivBg = itemView.findViewById(R.id.iv_bg);

      //  LoadImageUtil.getInstance().loadCirc(context, R.drawable.bg_main_sport, ivBg, DisplayUtils.dip2px(context, 8));


        tv_sport_time.setText(TimeUtils.getTimeByyyyyMMdd(System.currentTimeMillis()));

        tv_start_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ViewMultiClickUtil.onMultiClick(view)) {
                    return;
                }
                if (onSportOnclickListenter != null) {
                    onSportOnclickListenter.onSportStateListenter();
                }

                //历史页面
               /* if (onSportItemClickListener != null) {
                    onSportItemClickListener.onSportItemClick();
                }*/
            }
        });

    }


    public void setSportHolder(String min) {

        if (tv_day_sum_min != null) {
            tv_day_sum_min.setText(min);
        }


    }

    public void setData(String sportData) {
        if (sportData != null) {

        }
    }

    OnSportItemClickListener onSportItemClickListener;
    OnSportOnclickListenter onSportOnclickListenter;

    public void setSpoartItemClickListener(OnSportItemClickListener onSportItemClickListener, OnSportOnclickListenter onSportOnclickListenter) {
        this.onSportItemClickListener = onSportItemClickListener;
        this.onSportOnclickListenter = onSportOnclickListenter;
    }


    public interface OnSportItemClickListener {
        void onSportItemClick();
    }

    /**
     * 绑定两个以上的设备可以显示设备切换的按钮
     */



    public interface OnSportOnclickListenter {
        void onSportStateListenter();


    }


}
