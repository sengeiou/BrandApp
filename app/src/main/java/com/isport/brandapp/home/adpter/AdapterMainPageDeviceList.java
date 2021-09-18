package com.isport.brandapp.home.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.brandapp.home.bean.BaseMainData;
import com.isport.brandapp.home.bean.http.Fatsteelyard;
import com.isport.brandapp.home.bean.http.SleepBel;
import com.isport.brandapp.home.bean.http.Wristbandstep;
import com.isport.brandapp.R;
import com.isport.brandapp.util.StringFomateUtil;

import bike.gymproject.viewlibray.BebasNeueTextView;
import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;
import com.isport.blelibrary.utils.CommonDateUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;

/**
 * Created by huashao on 2017/11/13.
 */
public class AdapterMainPageDeviceList extends BaseCommonRefreshRecyclerAdapter<BaseMainData, AdapterMainPageDeviceList.ViewHolder> {
    public AdapterMainPageDeviceList(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_main_device_list;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }

    @Override
    protected void initData(ViewHolder viewHolder, int position, BaseMainData item) {

    }


    @Override
    protected void initEvent(ViewHolder viewHolder, int position, BaseMainData item) {

        if (item instanceof Fatsteelyard) {
            Fatsteelyard item1 = (Fatsteelyard) item;
            viewHolder.tvTypeUnit.setVisibility(View.VISIBLE);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_scale);
            viewHolder.tvTypeName.setText(context.getString(R.string.body_weight));
            viewHolder.tvTypeUnit.setText(context.getString(R.string.app_weight_util_single));
            viewHolder.tvTypeValue.setText(item1.getWeight().split("_")[0]);
            viewHolder.tvLastUpdateTime.setText(DateUtils.getStringByTime(item1.getNearestTime()));
        }

        if (item instanceof SleepBel) {
            SleepBel item1 = (SleepBel) item;
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_sleep);
            viewHolder.tvTypeName.setText(context.getString(R.string.sleep));
            viewHolder.tvTypeUnit.setText(context.getString(R.string.app_sleep_util_min));
            viewHolder.tvTypeUnit.setVisibility(View.GONE);
//            viewHolder.tvTypeValue.setText(item1.getDuration());
            String hour = "";
            String min = "";
            try {
                int sleepTime = Integer.valueOf(item1.getDuration());
                hour = CommonDateUtil.formatTwoStr(sleepTime / 60);
                min = CommonDateUtil.formatTwoStr(sleepTime % 60);
            } catch (Exception e) {
                hour = "--";
                min = "--";
            } finally {

                //  Logger.myLog("hour = " + hour + " min = " + min);
                StringFomateUtil.formatText(StringFomateUtil.FormatType.Time, context, viewHolder.tvTypeValue, UIUtils.getColor(R.color.common_text_color), R.string.app_time_util, hour, min);
                viewHolder.tvLastUpdateTime.setText(DateUtils.getStringByTime(item1.getCreatTime()));
            }

        }

        if (item instanceof Wristbandstep) {
            Wristbandstep item1 = (Wristbandstep) item;
            viewHolder.tvTypeUnit.setVisibility(View.VISIBLE);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_watch);
            viewHolder.tvTypeName.setText(context.getString(R.string.watch));
            viewHolder.tvTypeUnit.setText(context.getString(R.string.unit_steps));
            viewHolder.tvTypeValue.setText(item1.getStepNum());
            viewHolder.tvLastUpdateTime.setText(DateUtils.getStringByTime(item1.getLastServerTime()));
        }
    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        private TextView tvTypeName;
        private TextView tvTypeUnit;
        private BebasNeueTextView tvTypeValue;
        private TextView tvLastUpdateTime;
        private ImageView ivTypeLog;

        public ViewHolder(View itemView) {
            super(itemView);
            ivTypeLog = (ImageView) itemView.findViewById(R.id.iv_type_log);
            tvTypeValue = itemView.findViewById(R.id.tv_type_value);
            tvTypeUnit = itemView.findViewById(R.id.tv_type_unit);
            tvTypeName = itemView.findViewById(R.id.tv_type_name);
            tvLastUpdateTime = itemView.findViewById(R.id.tv_time);

        }
    }
}
