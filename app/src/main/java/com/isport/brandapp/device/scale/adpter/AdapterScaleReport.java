package com.isport.brandapp.device.scale.adpter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.device.scale.bean.ScaleBean;

import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;
import brandapp.isport.com.basicres.commonutil.StringUtil;

/**
 * Created by huashao on 2017/11/13.
 */
public class AdapterScaleReport extends BaseCommonRefreshRecyclerAdapter<ScaleBean, AdapterScaleReport.ViewHolder> {
    public AdapterScaleReport(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_scale_report;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }




    @Override
    protected void initData(ViewHolder viewHolder, int position, ScaleBean item) {
        if (position == listSource.size() - 1) {
            viewHolder.line.setVisibility(View.GONE);
        } else {
            viewHolder.line.setVisibility(View.VISIBLE);
        }
        if (!StringUtil.isBlank(item.standard)) {
            GradientDrawable drawable = (GradientDrawable) viewHolder.tvStande.getBackground();
            drawable.setColor(Color.parseColor(item.color));
            viewHolder.tvStande.setText(item.standard);
            viewHolder.tvStande.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvStande.setVisibility(View.INVISIBLE);
        }
        String value = "", valuseUnit = "";
        if (item.value.contains("_")) {
            String[] strs = item.value.split("_");
            value = strs[0];
            if (strs.length == 2) {
                valuseUnit = strs[1];
            }
        } else {
            value = item.value;
        }
        viewHolder.tvValue.setText(value);
        viewHolder.tvName.setText(item.title);
        if (TextUtils.isEmpty(valuseUnit)) {
            viewHolder.tvUnitl.setVisibility(View.GONE);
        } else {
            viewHolder.tvUnitl.setText(valuseUnit);
            viewHolder.tvUnitl.setVisibility(View.VISIBLE);
        }

        if (App.appType()==App.httpType) {
            //不论是网络还是单机都用本地图片
//            LoadImageUtil.getInstance().load(context, item.imgUrl, viewHolder.ivLog);
            viewHolder.ivLog.setBackgroundResource(item.imgInt);
        } else {
            viewHolder.ivLog.setBackgroundResource(item.imgInt);
        }
    }

    @Override
    protected void initEvent(ViewHolder viewHolder, int position, ScaleBean item) {


    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        private ImageView ivLog;
        private TextView tvName;
        private TextView tvValue;
        private TextView tvUnitl;
        private TextView tvStande;
        private View line;

        public ViewHolder(View itemView) {
            super(itemView);
            ivLog = itemView.findViewById(R.id.iv_res);
            line = itemView.findViewById(R.id.line);
            tvName = itemView.findViewById(R.id.tv_name);
            tvValue = itemView.findViewById(R.id.tv_value);
            tvUnitl = itemView.findViewById(R.id.tv_unitl);
            tvStande = itemView.findViewById(R.id.tv_stande);
        }
    }
}
