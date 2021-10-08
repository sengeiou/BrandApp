package com.isport.brandapp.device.history.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.brandapp.R;
import com.isport.brandapp.device.band.bean.BandDayBean;
import com.isport.brandapp.device.history.HistoryTilteBean;
import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.device.sleep.bean.SleepDayBean;
import com.isport.brandapp.device.sleep.bean.SleepHistoryList;
import com.isport.brandapp.sport.bean.SportSumData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.commonrecyclerview.adapter.ViewHolder;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class HistoryAdapter extends BaseAdapter {


    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<HistoryBeanList> mDatas;
    protected int mItemLayoutId;
    protected int mTitleLayoutId;
    protected int mMonthLayoutId;
    protected int mDividerLayoutId;
    private int CurrentType;




    public HistoryAdapter(Context context, int itemLayoutId, int mTitleLayoutId, int mMonthLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = new ArrayList<HistoryBeanList>();
        this.mItemLayoutId = itemLayoutId;
        this.mTitleLayoutId = mTitleLayoutId;
        this.mMonthLayoutId = mMonthLayoutId;
        this.mDividerLayoutId = R.layout.item_history_divier;

    }

    public HistoryAdapter(Context context, List<HistoryBeanList> mDatas, int itemLayoutId, int mTitleLayoutId, int mMonthLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
        this.mTitleLayoutId = mTitleLayoutId;
        this.mMonthLayoutId = mMonthLayoutId;
        this.mDividerLayoutId = R.layout.item_history_divier;
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public HistoryBeanList getItem(int position) {
        if (position >= 0 && position < mDatas.size()) {
            return mDatas.get(position);
        } else {
            return mDatas.get(0);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        int type = JkConfiguration.HistoryType.TYPE_TITLE;
        switch (position) {
            case 0:
                type = JkConfiguration.HistoryType.TYPE_TITLE;
                break;
            case 1:
                type = JkConfiguration.HistoryType.TYPE_MONTH;
                break;
            default:
                type = JkConfiguration.HistoryType.TYPE_CONTENT;
                break;
        }
        return type;

    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);

        switch (mDatas.get(position).viewType) {
            case JkConfiguration.HistoryType.TYPE_TITLE: {
                convertScaleMonthTitle(viewHolder, mDatas.get(position).tilteBean, mDatas.get(position).DeviceTpye);
                break;
            }
            case JkConfiguration.HistoryType.TYPE_CONTENT: {
                switch (mDatas.get(position).DeviceTpye) {
                    case JkConfiguration.DeviceType.BODYFAT:
                        convertScaleMonthItem(viewHolder, mDatas.get(position), position);
                        break;
                    case JkConfiguration.DeviceType.SLEEP:
                        convertSleepItem(viewHolder, mDatas.get(position).sleepDayBean, position);
                        break;
                    case JkConfiguration.DeviceType.WATCH_W516:
                        convertBandItem(viewHolder, mDatas.get(position).bandDayBean, position);
                        break;
                    case JkConfiguration.DeviceType.SPORT:
                        convertSportItem(viewHolder, mDatas.get(position).sportDetailData, position);
                        break;
                }
            }
            break;
            case JkConfiguration.HistoryType.TYPE_DIVIDER: {

                break;
            }
            case JkConfiguration.HistoryType.TYPE_MONTH: {
                switch (mDatas.get(position).DeviceTpye) {
                    case JkConfiguration.DeviceType.BODYFAT:
                        convertScaleMonthTitle(viewHolder, mDatas.get(position));
                        break;
                    case JkConfiguration.DeviceType.SLEEP:
                        convertSleepMonthTitle(viewHolder, mDatas.get(position).sleepHistoryBean);
                        break;
                    case JkConfiguration.DeviceType.SPORT:
                        convertSportMonthTitle(viewHolder, mDatas.get(position).moth);
                        break;
                    case JkConfiguration.DeviceType.WATCH_W516:
                        break;
                }
            }
            break;
        }
        return viewHolder.getConvertView();

    }


    public void convertScaleMonthTitle(ViewHolder viewHolder, HistoryTilteBean item, int type) {


        if (type == JkConfiguration.DeviceType.WATCH_W516) {
            RelativeLayout layout = viewHolder.getView(R.id.layout);
            layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.common_item_bg_color));
        }

        if ((item.one >>> 24) < 2) {
            viewHolder.setText(R.id.tv_one, false);
        } else {
            viewHolder.setText(R.id.tv_one, item.one, true);
        }
        if ((item.two >>> 24) < 2) {

            viewHolder.setText(R.id.tv_two, false);
        } else {
            viewHolder.setText(R.id.tv_two, item.two, true);
        }
        if ((item.three >>> 24) < 2) {

            viewHolder.setText(R.id.tv_three, false);
        } else {
            viewHolder.setText(R.id.tv_three, item.three, true);
        }

        if (item.currentType != JkConfiguration.DeviceType.BODYFAT) {

            //体脂称的布局不一样
            if ((item.four >>> 24) < 2) {

                viewHolder.setText(R.id.tv_four, false);
            } else {
                viewHolder.setText(R.id.tv_four, item.four, true);
            }
            if ((item.five >>> 24) < 2) {

                viewHolder.setText(R.id.tv_five, false);
            } else {
                viewHolder.setText(R.id.tv_five, item.five, true);
            }

        }

    }

    public void convertScaleMonthTitle(ViewHolder holder, HistoryBeanList item) {

        //编写这个字段
        //2018年10月：共减重3.0公斤，减脂

        String strweight = "";
        if (item.scaleHistoryBean.isUpWeight) {
            strweight = String.format(mContext.getResources().getString(R.string.app_scale_up_weight), item.scaleHistoryBean.strWeight);
        } else {
            strweight = String.format(mContext.getResources().getString(R.string.app_scale_down_weight), item.scaleHistoryBean.strWeight);
        }

        String strFat = "";
        if (item.scaleHistoryBean.isUpFatPresent) {
            strFat = String.format(mContext.getResources().getString(R.string.app_scale_up_body), item.scaleHistoryBean.strFat);

        } else {
            strFat = String.format(mContext.getResources().getString(R.string.app_scale_down_body), item.scaleHistoryBean.strFat);
        }
        String strDetail = item.scaleHistoryBean.month + mContext.getResources().getString(R.string.app_scale_sum) + strweight + strFat;

        holder.setText(R.id.tv_detail_value, strDetail);
    }

    public void convertSleepMonthTitle(ViewHolder holder, SleepHistoryList item) {


        holder.setText(R.id.tv_detail_value, item.getMonth());
    }

    public void convertSportMonthTitle(ViewHolder holder, String month) {


        holder.setText(R.id.tv_detail_value, month);
    }

    public void convertSleepItem(ViewHolder viewHolder, SleepDayBean item, int position) {

        try {
            isShowLine(viewHolder, position);

            viewHolder.setText(R.id.tv_one, DateUtils.getDateStringByTime(item.getCreatTime()) + "\n" + DateUtils.getTimeStringByTime(item.getCreatTime()));
            int deepSleepAllTime = Integer.parseInt(item.getDeepSleepAllTime());
            String deepTime = deepSleepAllTime / 60 + "H" + deepSleepAllTime % 60 + "M";
            int duration = Integer.parseInt(item.getDuration());
            String durationT = duration / 60 + "H" + duration % 60 + "M";
            viewHolder.setText(R.id.tv_two, deepTime + "/\n" + durationT);
            viewHolder.setText(R.id.tv_three, item.getAverageHeartBeatRate());
            viewHolder.setText(R.id.tv_four, item.getAverageBreathRate());
            viewHolder.setText(R.id.tv_five, item.getTrunOverTimes());
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void convertSportItem(ViewHolder viewHolder, SportSumData item, int position) {
        try {
            viewHolder.setImageResource(R.id.iv_sport_type, item.getDrawableRes());
            //viewHolder.setText(R.id.tv_sport_name, item.getSportTypeName());
            viewHolder.setText(R.id.tv_sport_recode_time, item.getStrTime());
            viewHolder.setText(R.id.tv_sport_recode_speed, item.getType() == 2 ? String.format("%.2f",Float.valueOf(item.getAvgSpeed()))+mContext.getResources().getString(R.string.unit_speed):item.getAvgPace()+"");
            viewHolder.setText(R.id.tv_sport_recode_cal, item.getCalories());
            viewHolder.setText(R.id.tv_sport_end_time, item.getStrEndTime());
            viewHolder.setText(R.id.tv_sport_dis, item.getDistance());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void convertBandItem(ViewHolder viewHolder, BandDayBean item, int position) {
        try {
            isShowLine(viewHolder, position);

            viewHolder.setText(R.id.tv_one, DateUtils.getMD(item.buildTime));
            RelativeLayout layout = viewHolder.getView(R.id.layout);
            layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.common_item_bg_color));

            viewHolder.setText(R.id.tv_two, item.stepNum + "", mContext.getResources().getDimension(R.dimen.sp22), true);
            viewHolder.setText(R.id.tv_three, CommonDateUtil.formatTwoPoint(Float.valueOf(item.stepKm)), mContext.getResources().getDimension(R.dimen.sp22), true);
            viewHolder.setText(R.id.tv_four, item.calorie, mContext.getResources().getDimension(R.dimen.sp22), true);
            viewHolder.setText(R.id.tv_five, "", false);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void isShowLine(ViewHolder viewHolder, int position) {
        if (mDatas.size() - 1 == position) {
            viewHolder.getView(R.id.view_line).setVisibility(View.GONE);

        } else {
            if (mDatas.get(position).viewType == mDatas.get(position + 1).viewType) {
                viewHolder.getView(R.id.view_line).setVisibility(View.VISIBLE);
            } else {
                viewHolder.getView(R.id.view_line).setVisibility(View.GONE);
            }
        }
    }

    public void convertScaleMonthItem(ViewHolder viewHolder, HistoryBeanList item, int position) {
        try {
            isShowLine(viewHolder, position);
            viewHolder.setText(R.id.tv_date, item.scaleDayBean.date);
            viewHolder.setText(R.id.tv_time, item.scaleDayBean.time);
            viewHolder.setText(R.id.tv_weight, item.scaleDayBean.strWeight);
            TextView tvWeightRate = viewHolder.getView(R.id.tv_weight_rate);
            if (TextUtils.isEmpty(item.scaleDayBean.strWeightRate)) {
                tvWeightRate.setTextColor(ContextCompat.getColor(mContext, R.color.common_title_button_text_enabled));
                tvWeightRate.setText(UIUtils.getString(R.string.no_data));
            } else {
                if (item.scaleDayBean.isWeightUp) {
                    tvWeightRate.setTextColor(ContextCompat.getColor(mContext, R.color.common_stande_red));
                    tvWeightRate.setText(String.format(mContext.getString(R.string.app_rate_up), item.scaleDayBean.strWeightRate));
                } else {
                    tvWeightRate.setTextColor(ContextCompat.getColor(mContext, R.color.common_stande_green));
                    tvWeightRate.setText(String.format(mContext.getString(R.string.app_rate_down), item.scaleDayBean.strWeightRate));

                }
            }

            TextView tvBodyRate = viewHolder.getView(R.id.tv_body_rate);
            viewHolder.setText(R.id.tv_body_prenter, item.scaleDayBean.strBodyPresent);
            if (TextUtils.isEmpty(item.scaleDayBean.strBodyRate)) {
                tvBodyRate.setTextColor(ContextCompat.getColor(mContext, R.color.common_title_button_text_enabled));
                tvBodyRate.setText(UIUtils.getString(R.string.no_data));
            } else {
                if (item.scaleDayBean.isBodyRateUp) {
                    tvBodyRate.setTextColor(ContextCompat.getColor(mContext, R.color.common_stande_red));
                    tvBodyRate.setText(String.format(mContext.getString(R.string.app_rate_up), item.scaleDayBean.strBodyRate));
                } else {
                    tvBodyRate.setTextColor(ContextCompat.getColor(mContext, R.color.common_stande_green));
                    tvBodyRate.setText(String.format(mContext.getString(R.string.app_rate_down), item.scaleDayBean.strBodyRate));

                }
            }

            //编写这个字段
            //2018年10月：共减重3.0公斤，减脂
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // public  void convert(ViewHolder helper, ScaleHistoryBean item, int positon);

    public ViewHolder getViewHolder(int position, View convertView,
                                    ViewGroup parent) {

        switch (mDatas.get(position).viewType) {
            case JkConfiguration.HistoryType.TYPE_CONTENT:
                return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                        position);
            case JkConfiguration.HistoryType.TYPE_DIVIDER:
                return ViewHolder.get(mContext, convertView, parent, mDividerLayoutId,
                        position);
            case JkConfiguration.HistoryType.TYPE_MONTH:
                return ViewHolder.get(mContext, convertView, parent, mMonthLayoutId,
                        position);
            case JkConfiguration.HistoryType.TYPE_TITLE:
                return ViewHolder.get(mContext, convertView, parent, mTitleLayoutId,
                        position);

        }

        return null;

    }

}
