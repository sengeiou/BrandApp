package com.isport.brandapp.device.sleep.adpter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.isport.brandapp.R;
import com.isport.brandapp.device.sleep.bean.SleepDayBean;

import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;

/**
 * Created by huashao on 2017/11/13.
 */
public class AdapterSleepHistroyDay extends BaseCommonRefreshRecyclerAdapter<SleepDayBean, AdapterSleepHistroyDay
        .ViewHolder> {


    public AdapterSleepHistroyDay(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_sleep_history_detail_day;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }

    @Override
    protected void initData(ViewHolder viewHolder, int position, SleepDayBean item) {

        if (listSource.size() - 1 == position) {
            viewHolder.line.setVisibility(View.GONE);
        } else {
            viewHolder.line.setVisibility(View.VISIBLE);
        }

        viewHolder.tvDate.setText(DateUtils.getDateStringByTime(item.getCreatTime()) + "");
        viewHolder.tvTime.setText(DateUtils.getTimeStringByTime(item.getCreatTime()) + "");
        viewHolder.tvAvgBmp.setText(item.getAverageHeartBeatRate());
        viewHolder.tvAvgBreathe.setText(item.getAverageBreathRate());
        viewHolder.tvIfant.setText(item.getTrunOverTimes());
        int deepSleepAllTime = Integer.parseInt(item.getDeepSleepAllTime());
        String deepTime = deepSleepAllTime / 60 + "H" + deepSleepAllTime % 60 + "M";
        int duration = Integer.parseInt(item.getDuration());
        String durationT = duration / 60 + "H" + duration % 60 + "M";
        viewHolder.tvSleepdDeepTime.setText(deepTime + "/");
        viewHolder.tvSleepdSumTime.setText(durationT);
    }


    @Override
    protected void initEvent(ViewHolder viewHolder, int position, SleepDayBean item) {


    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        private TextView tvDate;
        private TextView tvTime;
        private TextView tvAvgBmp;
        private TextView tvIfant;
        private TextView tvAvgBreathe;
        private TextView tvSleepdDeepTime;
        private TextView tvSleepdSumTime;
        private View line;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAvgBmp = itemView.findViewById(R.id.tv_avg_bmp);
            tvIfant = itemView.findViewById(R.id.tv_infant);
            tvAvgBreathe = itemView.findViewById(R.id.tv_avg_breathe);
            tvSleepdDeepTime = itemView.findViewById(R.id.tv_sleep_deep_time);
            tvSleepdSumTime = itemView.findViewById(R.id.tv_sleep_sum_time);
            line = itemView.findViewById(R.id.view_line);
        }
    }
}
