package com.isport.brandapp.Home.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;

import java.util.List;

import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;

/**
 * @创建者 bear
 * @创建时间 2019/3/28 19:50
 * @描述
 */
public class DataSleepHolder extends CustomHolder<String> {

    TextView tvUpdateTime;
    ImageView iv_current_type;
    TextView tvOption, tv_current_type_name;
    TextView tvValueH, tvValueM;
    TextView tvUnitH, tvUnitlM;

    public DataSleepHolder(View itemView) {
        super(itemView);
    }

    public DataSleepHolder(List<String> datas, View itemView) {
        super(datas, itemView);
    }

    public DataSleepHolder(Context context, final List<String> lists, int itemID) {
        super(context, lists, itemID);
        tvUpdateTime = itemView.findViewById(R.id.tv_update_time);
        iv_current_type = itemView.findViewById(R.id.iv_current_type);
        tv_current_type_name = itemView.findViewById(R.id.tv_current_type_name);
        tvOption = itemView.findViewById(R.id.tv_option);
        tvValueH = itemView.findViewById(R.id.tv_value_h);
        tvValueM = itemView.findViewById(R.id.tv_value_m);
        tvUnitH = itemView.findViewById(R.id.tv_unitl_h);
        tvUnitlM = itemView.findViewById(R.id.tv_unitl_m);
        setDefValues();

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                if (onSleepItemClickListener != null) {
                    onSleepItemClickListener.onSleepItemClick();
                }
            }
        });

    }

    public void setDefValues() {
        if (iv_current_type != null) {
            iv_current_type.setImageResource(R.drawable.icon_main_sleep);
        }
        if (tv_current_type_name != null) {
            tv_current_type_name.setText(UIUtils.getString(R.string.fragment_data_sleep));
        }
        if (tvValueM != null) {
            tvValueM.setVisibility(View.GONE);
        }
        if (tvUnitlM != null) {
            tvUnitlM.setVisibility(View.GONE);
        }
        if (tvUnitH != null) {
            tvUnitH.setVisibility(View.VISIBLE);
            tvUnitH.setText(UIUtils.getString(R.string.fragment_data_no_data));
        }
        if (tvValueH != null) {
            tvValueH.setVisibility(View.GONE);
        }
        if (tvOption != null) {
            tvOption.setVisibility(View.GONE);
            tvOption.setText(R.string.detection);
            tvOption.setEnabled(false);
        }
        if (tvUpdateTime != null) {
            tvUpdateTime.setVisibility(View.GONE);
        }
    }


    public void updateUI(String strDate, int min) {
        if (TextUtils.isEmpty(strDate)) {
            setDefValues();
            return;
        }
//        && currentDeviceType == JkConfiguration.DeviceType.SLEEP


        int compareSleepTime = (int) Math.abs(min);
        if (compareSleepTime >= 60) {
            String comparHour = CommonDateUtil.formatTwoStr(compareSleepTime / 60);
            String comparMin = CommonDateUtil.formatTwoStr(compareSleepTime % 60);
            tvValueH.setVisibility(View.VISIBLE);
            tvValueH.setText(comparHour);
            tvValueM.setVisibility(View.VISIBLE);
            tvValueM.setText(comparMin);
            tvUnitH.setVisibility(View.VISIBLE);
            tvUnitH.setText(UIUtils.getString(R.string.unit_hour));
            tvUnitlM.setVisibility(View.VISIBLE);
            tvUnitlM.setText(UIUtils.getString(R.string.unit_min));
        } else {
            String comparMin = CommonDateUtil.formatTwoStr(compareSleepTime % 60);
            tvValueH.setVisibility(View.GONE);
            tvValueM.setVisibility(View.VISIBLE);
            tvValueM.setText(comparMin);
            tvUnitH.setVisibility(View.GONE);
            tvUnitlM.setVisibility(View.VISIBLE);
            tvUnitlM.setText(UIUtils.getString(R.string.sport_min));
        }
        tvUpdateTime.setVisibility(View.VISIBLE);
        tvUpdateTime.setText(strDate);

    }


    OnSleepItemClickListener onSleepItemClickListener;
    OnSleepOnclickListenter onSleepOnclickListenter;

    public void setSleepItemClickListener(OnSleepItemClickListener onSportItemClickListener, OnSleepOnclickListenter onSportOnclickListenter) {
        this.onSleepItemClickListener = onSportItemClickListener;
        this.onSleepOnclickListenter = onSportOnclickListenter;
    }


    public interface OnSleepItemClickListener {
        void onSleepItemClick();
    }


    public interface OnSleepOnclickListenter {
        void onSleepStateListenter();
    }
}
