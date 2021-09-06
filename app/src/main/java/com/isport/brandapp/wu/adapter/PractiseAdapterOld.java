package com.isport.brandapp.wu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.R;
import com.isport.brandapp.wu.bean.ExerciseInfo;
import com.isport.brandapp.wu.bean.PractiseRecordInfo;

import java.util.ArrayList;
import java.util.List;

public class PractiseAdapterOld extends RecyclerView.Adapter<PractiseAdapterOld.MyViewHolder> {

    private List<PractiseRecordInfo> mDatas;
    private Context mContext;

    public PractiseAdapterOld(Context context, List<PractiseRecordInfo> data) {
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
    public PractiseAdapterOld.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_practise_record, parent, false));
    }

    @Override
    public void onBindViewHolder(PractiseAdapterOld.MyViewHolder holder, int position) {



        holder.tv_date.setText(mDatas.get(position).getKey());
        setItemRecyclerView(holder.recyclerview_itme, mDatas.get(position).getData());

        holder.layout_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收起来还是放开
            }
        });
        //实现点击效果

    }

    //私有属性
    private OnPractiseClickListener onItemClickListener = null;

    //setter方法
    public void setItemClickListener(OnPractiseClickListener onItemClickListener) {

        Logger.myLog("PractiseItemAdpter:onItemClickListener3 setItemClickListener" + onItemClickListener);
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
        RelativeLayout layout_head;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerview_itme = itemView.findViewById(R.id.recyclerview_itme);
            tv_date = itemView.findViewById(R.id.tv_date);
            layout = itemView.findViewById(R.id.layout);
            layout_head = itemView.findViewById(R.id.layout_head);

        }
    }
}

