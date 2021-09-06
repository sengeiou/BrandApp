package com.isport.brandapp.wu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.R;
import com.isport.brandapp.wu.Constant;
import com.isport.brandapp.wu.bean.ExerciseInfo;

import java.util.ArrayList;
import java.util.List;

public class PractiseItemAdapter extends RecyclerView.Adapter<PractiseItemAdapter.MyViewHolder> {

    private List<ExerciseInfo> mDatas;
    private Context mContext;
    private boolean isDetail;

    public PractiseItemAdapter(Context context, List<ExerciseInfo> data, boolean isDetail) {
        this.mDatas = data;
        mContext = context;
        this.isDetail = isDetail;
        if (data == null) {
            mDatas = new ArrayList<>();
        }

    }

    public PractiseItemAdapter(Context context, List<ExerciseInfo> data) {
        this.mDatas = data;
        mContext = context;
        this.isDetail = false;
        if (data == null) {
            mDatas = new ArrayList<>();
        }

    }

    public void replaceData(List<ExerciseInfo> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public PractiseItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_item_practise_record, parent, false));
    }

    //私有属性
    private OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, ExerciseInfo note, int position);
    }

    @Override
    public void onBindViewHolder(PractiseItemAdapter.MyViewHolder holder, int position) {
//        holder.iv_run.setImageResource();
        ExerciseInfo info = mDatas.get(position);
        if (info == null) {
            return;
        }
        if (isDetail) {
            holder.tv_run_time.setText(TimeUtils.getTimeByyyyyMMdd(info.getStartTimestamp()) + " " + TimeUtils.getTimeByHHmmssWithOutSpace(info.getStartTimestamp()) + "~" + (TimeUtils.getTimeByHHmmssWithOutSpace(info.getEndTimestamp())));
        } else {
            holder.tv_run_time.setText(TimeUtils.getTimeByHHmmssWithOutSpace(info.getStartTimestamp()) + "~" + (TimeUtils.getTimeByHHmmssWithOutSpace(info.getEndTimestamp())));
        }
        holder.tv_sport_time.setText(TimeUtils.getFormatTimeHHMMSS(Long.valueOf(info.getVaildTimeLength())));
        String aveRate = "0";
        if (!TextUtils.isEmpty(info.getAveRate())) {
            aveRate = info.getAveRate();
        }
        if (Integer.valueOf(aveRate) != 0) {
            holder.tv_average_heart.setText(mContext.getString(R.string.average_heart_value, aveRate));
        } else {
            holder.tv_average_heart.setText("--");
        }

        holder.tv_consume.setText(mContext.getString(R.string.consume_value, info.getTotalCalories()));
        holder.tv_step.setText(mContext.getString(R.string.step_value, "" + info.getTotalSteps()));
        float distance = 0f;
        String hour_speed = "0";
        String average_speed = "0";
        if (!TextUtils.isEmpty(info.getTotalDistance()) && !TextUtils.isEmpty(info.getVaildTimeLength())) {
            int tempDistance = (Integer.valueOf(info.getTotalDistance()) / 10);
            distance = (float) (tempDistance) / 100;
            if (distance > 0) {
                hour_speed = String.format("%.2f", distance * 60 * 60 / Float.valueOf(info.getVaildTimeLength()));
                int average_speed_second = (int) (Float.valueOf(info.getVaildTimeLength()) / distance);
                average_speed = average_speed_second / 60 + "'" + CommonDateUtil.formatTwoStr(average_speed_second % 60) + "''";
            }
        }
        holder.tv_distance.setText(mContext.getString(R.string.distance_value, String.format("%.2f", distance)));

        holder.tv_hour_speed.setText(mContext.getString(R.string.hour_speed_value, hour_speed));
        holder.tv_average_speed.setText(average_speed);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.myLog("PractiseItemAdpter:onItemClickListener1 ");
                if (onItemClickListener != null) {

                    onItemClickListener.onItemClick(v, info, position);
                }
            }
        });

        switch (Integer.valueOf(info.getExerciseType())) {
            case Constant.PRACTISE_TYPE_ALL:
            case Constant.PRACTISE_TYPE_WALK:
                holder.tv_run.setText(mContext.getString(R.string.walk));
                holder.iv_type.setImageResource(R.drawable.icon_walk);
                showSet(holder, 0);
                break;
            case Constant.PRACTISE_TYPE_RUN:
                holder.tv_run.setText(mContext.getString(R.string.run));
                holder.iv_type.setImageResource(R.drawable.icon_run);
                showSet(holder, 0);
                break;
            case Constant.PRACTISE_TYPE_ROPE_SKIP:
                holder.tv_run.setText(mContext.getString(R.string.rope_skip));
                holder.iv_type.setImageResource(R.drawable.icon_skip);
                showSet(holder, 1);
                break;
            case Constant.PRACTISE_TYPE_RIDE:
                holder.tv_run.setText(mContext.getString(R.string.ride));
                holder.iv_type.setImageResource(R.drawable.icon_bike);
                showSet(holder, 1);
                break;
            case Constant.PRACTISE_TYPE_CLIMBING:
                holder.tv_run.setText(mContext.getString(R.string.climbing));
                holder.iv_type.setImageResource(R.drawable.icon_climbing);
                showSet(holder, 1);
                break;
            case Constant.PRACTISE_TYPE_BADMINTON:
                holder.tv_run.setText(mContext.getString(R.string.badminton));
                holder.iv_type.setImageResource(R.drawable.icon_badminton);
                showSet(holder, 2);
                break;
            case Constant.PRACTISE_TYPE_FOOTBALL:
                holder.tv_run.setText(mContext.getString(R.string.football));
                holder.iv_type.setImageResource(R.drawable.icon_football);
                showSet(holder, 2);
                break;
            case Constant.PRACTISE_TYPE_BASKETBALL:
                holder.tv_run.setText(mContext.getString(R.string.basketball));
                holder.iv_type.setImageResource(R.drawable.icon_basketball);
                showSet(holder, 2);
                break;
            case Constant.PRACTISE_TYPE_PINGPANG:
                holder.tv_run.setText(mContext.getString(R.string.pingpang));
                holder.iv_type.setImageResource(R.drawable.icon_pingpang);
                showSet(holder, 2);
                break;

        }

        if (isDetail) {
            holder.iv_right.setVisibility(View.GONE);
            holder.iv_type.setVisibility(View.GONE);
            holder.tv_run.setText(mContext.getString(R.string.practise_sum_time));
        } else {
            holder.iv_right.setVisibility(View.VISIBLE);
            holder.iv_type.setVisibility(View.VISIBLE);
        }

    }

    private void showSet(MyViewHolder holder, int type) {

        switch (type) {
            case 0:
                holder.cardview_step.setVisibility(View.VISIBLE);
                holder.ll_bottom.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.cardview_step.setVisibility(View.INVISIBLE);
                holder.ll_bottom.setVisibility(View.GONE);
                break;
            case 2:
                holder.cardview_step.setVisibility(View.VISIBLE);
                holder.ll_bottom.setVisibility(View.GONE);
                break;
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        View view_line;
        ImageView iv_type;
        ImageView iv_right;
        TextView tv_run;
        TextView tv_run_time;
        TextView tv_sport_time;
        TextView tv_average_heart;
        TextView tv_consume;
        TextView tv_step;
        TextView tv_distance;
        TextView tv_hour_speed;
        TextView tv_average_speed;
        LinearLayout cardview_average_speed;
        LinearLayout cardview_hour_speed;
        LinearLayout cardview_distance;
        LinearLayout cardview_step;
        LinearLayout ll_bottom;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view_line = itemView.findViewById(R.id.view_line);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_type = itemView.findViewById(R.id.iv_type);
            tv_run = itemView.findViewById(R.id.tv_run);
            tv_run_time = itemView.findViewById(R.id.tv_run_time);
            tv_sport_time = itemView.findViewById(R.id.tv_sport_time);
            tv_average_heart = itemView.findViewById(R.id.tv_average_heart);
            tv_consume = itemView.findViewById(R.id.tv_consume);
            tv_step = itemView.findViewById(R.id.tv_step);
            tv_distance = itemView.findViewById(R.id.tv_distance);
            tv_hour_speed = itemView.findViewById(R.id.tv_hour_speed);
            tv_average_speed = itemView.findViewById(R.id.tv_average_speed);

            cardview_step = itemView.findViewById(R.id.cardview_step);
            cardview_average_speed = itemView.findViewById(R.id.cardview_average_speed);
            cardview_hour_speed = itemView.findViewById(R.id.cardview_hour_speed);
            cardview_distance = itemView.findViewById(R.id.cardview_distance);
            ll_bottom = itemView.findViewById(R.id.ll_bottom);
        }
    }
}
