package com.isport.brandapp.wu.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.R;
import com.isport.brandapp.wu.bean.ExerciseInfo;
import com.isport.brandapp.wu.bean.PractiseRecordInfo;

import java.util.ArrayList;
import java.util.List;

public class PractiseAdapter extends RecyclerView.Adapter<PractiseAdapter.MyViewHolder> {

    private List<PractiseRecordInfo> mDatas;
    private Context mContext;

    public PractiseAdapter(Context context, List<PractiseRecordInfo> data) {
        mContext = context;
        this.mDatas = data;
        if (data == null) {
            mDatas = new ArrayList<>();
        }

    }

    public void replaceData(List<PractiseRecordInfo> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public PractiseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_practise_record, parent, false));
    }

    @Override
    public void onBindViewHolder(PractiseAdapter.MyViewHolder holder, int position) {
        holder.tv_date.setText(mDatas.get(position).getKey());
        setItemRecyclerView(holder.recyclerview_itme, mDatas.get(position).getData());


        //实现点击效果

    }

    //私有属性
    private OnPractiseClickListener onItemClickListener = null;

    //setter方法
    public void setItemClickListener(OnPractiseClickListener onItemClickListener) {

        Logger.myLog("PractiseItemAdpter:onItemClickListener3 setItemClickListener"+onItemClickListener);
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnPractiseClickListener {
        void onItemClick(View v, ExerciseInfo note, int position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private void setItemRecyclerView(RecyclerView recyclerView, List<ExerciseInfo> mExerciseInfos) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        PractiseItemAdapter practiseItemAdapter = new PractiseItemAdapter(mContext, mExerciseInfos);
        practiseItemAdapter.setOnItemClickListener(new PractiseItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, ExerciseInfo note, int position) {
                Logger.myLog("PractiseItemAdpter:onItemClickListener2" + onItemClickListener);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, note, position);
                }
            }
        });
        recyclerView.setAdapter(practiseItemAdapter);

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerview_itme;
        private TextView tv_date;
        LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerview_itme = itemView.findViewById(R.id.recyclerview_itme);
            tv_date = itemView.findViewById(R.id.tv_date);
            layout = itemView.findViewById(R.id.layout);

        }
    }
}

