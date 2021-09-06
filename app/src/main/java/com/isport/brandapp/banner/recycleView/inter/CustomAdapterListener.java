package com.isport.brandapp.banner.recycleView.inter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.isport.brandapp.banner.recycleView.holder.CustomHolder;

import java.util.List;


public interface CustomAdapterListener<T> extends BodyInitListener<T> {

    void initView(T data, View itemView);

    /**
     * 根据position获取itemType
     *
     * @return itemType
     */
    int getItemTypeByPosition();

    /**
     * 根据类型获取相应的ViewHolder
     *
     * @param context 上下文
     * @param lists   数据集合
     * @param itemID  布局ID
     * @return
     */
    CustomHolder getHolderByViewType(Context context, List<T> lists, int itemID);

    /**
     * 绑定holder
     *
     * @param holder
     * @param position
     */
    void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
}
