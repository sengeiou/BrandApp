package com.isport.brandapp.wu.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isport.brandapp.R;

import java.util.ArrayList;
import java.util.List;

public class SleepItemAdapter extends RecyclerView.Adapter<SleepItemAdapter.MyViewHolder> {

    private List<String> mDatas;
    private Context mContext;

    public SleepItemAdapter(Context context, List<String> data) {
        this.mDatas = data;
        mContext = context;
        if (data == null) {
            mDatas = new ArrayList<>();
        }
    }

    public void replaceData(List<String> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public SleepItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sleep, parent, false));
    }

    @Override
    public void onBindViewHolder(SleepItemAdapter.MyViewHolder holder, int position) {
        holder.tv_value.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_value = itemView.findViewById(R.id.sporadic_naps_one);

        }
    }
}
