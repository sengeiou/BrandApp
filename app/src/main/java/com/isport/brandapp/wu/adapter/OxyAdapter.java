package com.isport.brandapp.wu.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.R;
import com.isport.brandapp.wu.bean.OxyInfo;

import java.util.ArrayList;
import java.util.List;

public class OxyAdapter extends RecyclerView.Adapter<OxyAdapter.MyViewHolder> {

    private List<OxyInfo> mDatas;
    private Context mContext;

    public OxyAdapter(Context context, List<OxyInfo> data) {
        this.mDatas = data;
        mContext = context;
        if (data == null) {
            mDatas = new ArrayList<>();
        }
    }

    public void replaceData(List<OxyInfo> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public OxyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_oxy, parent, false));
    }

    @Override
    public void onBindViewHolder(OxyAdapter.MyViewHolder holder, int position) {
        OxyInfo info = mDatas.get(position);
        holder.tv_value.setText("" + info.getBoValue());
        holder.tv_time.setText(TimeUtils.getTimeByyyyyMMddhhmmss(info.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_value;
        TextView tv_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_value = itemView.findViewById(R.id.tv_value);
            tv_time = itemView.findViewById(R.id.tv_time);

        }
    }
}
