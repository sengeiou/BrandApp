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
import com.isport.brandapp.wu.bean.BPInfo;

import java.util.ArrayList;
import java.util.List;

public class BpAdapter extends RecyclerView.Adapter<BpAdapter.MyViewHolder> {

    private List<BPInfo> mDatas;
    private Context mContext;

    public BpAdapter(Context context, List<BPInfo> data) {
        this.mDatas = data;
        mContext = context;
        if (data == null) {
            mDatas = new ArrayList<>();
        }

    }

    public void replaceData(List<BPInfo> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public BpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bp, parent, false));
    }

    @Override
    public void onBindViewHolder(BpAdapter.MyViewHolder holder, int position) {
        BPInfo info = mDatas.get(position);
        holder.tv_value.setText("" + info.getSpValue()  + "/" +info.getDpValue());
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
