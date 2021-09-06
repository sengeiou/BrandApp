package com.isport.brandapp.wu.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.R;
import com.isport.brandapp.wu.bean.TempInfo;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.commonutil.UIUtils;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.MyViewHolder> {

    private List<TempInfo> mDatas;
    private Context mContext;
    private String tempUnitl;

    public TempAdapter(Context context, List<TempInfo> data, String tempUnitl) {
        this.mDatas = data;
        mContext = context;
        if (data == null) {
            mDatas = new ArrayList<>();
        }
    }

    public void replaceData(List<TempInfo> data,String tempUnitl) {
        this.tempUnitl=tempUnitl;
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public TempAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_oxy, parent, false));
    }

    @Override
    public void onBindViewHolder(TempAdapter.MyViewHolder holder, int position) {
        TempInfo info = mDatas.get(position);
        if (TextUtils.isEmpty(tempUnitl)) {
            tempUnitl = "0";
        }
       // com.isport.blelibrary.utils.Logger.myLog("getTempUtil："+tempUnitl);
        if (tempUnitl.equals("0")) {
            holder.tv_unit.setText(UIUtils.getString(R.string.temperature_degree_centigrade));
            holder.tv_value.setText(info.getCentigrade());
        } else {
            holder.tv_unit.setText(UIUtils.getString(R.string.temperature_fahrenheit));
            holder.tv_value.setText(info.getFahrenheit());
        }

        holder.tv_time.setText(TimeUtils.getTimeByyyyyMMddhhmmss(info.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_value;
        TextView tv_time;
        TextView tv_unit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_value = itemView.findViewById(R.id.tv_value);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_unit = itemView.findViewById(R.id.tv_unit);

        }
    }
}
