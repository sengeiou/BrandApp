package com.isport.brandapp.banner.recycleView.inter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.isport.brandapp.banner.recycleView.holder.CustomHolder;
import com.isport.brandapp.banner.recycleView.holder.CustomPeakHolder;

import java.util.List;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/8 16:10
 */
public class DefaultAdapterViewLisenter<T> implements CustomAdapterListener<T> {
    @Override
    public void initView(T data, View itemView) {

    }

    @Override
    public int getItemTypeByPosition() {
        return 0;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * 获取头布局holder
     *
     * @param context  上下文
     * @param position 位置
     * @return holder
     */
    public CustomPeakHolder getHeardHolder(Context context, int position) {
        return null;
    }

    /**
     * 获取普通布局holder
     *
     * @param context 上下文
     * @param lists   数据
     * @param itemID  布局ID
     * @return holder
     */
    public CustomHolder getBodyHolder(Context context, List<T> lists, int itemID) {
        return null;
    }

    /**
     * 获取脚局holder
     *
     * @param context  上下文
     * @param position 脚布局位置,从0开始
     * @return holder
     */

    public CustomHolder getFootdHolder(Context context, int position) {
        return null;
    }


    public CustomHolder getHeader(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getFooter(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getConect(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getConect1(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getConect2(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getConectScale(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getOxyGenItem(Context context, List<T> lists, int itemID) {
        return null;
    }
    public CustomHolder getTempItem(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getBloodPressureItem(Context context, List<T> lists, int itemID) {
        return null;
    }
    public CustomHolder getOnceHrItem(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getExecericeItem(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getConectHeartRate(Context context, List<T> lists, int itemID) {
        return null;
    }

    public CustomHolder getConectSleep(Context context, List<T> lists, int itemID) {
        return null;
    }

    @Override
    public CustomHolder getHolderByViewType(Context context, List lists, int itemID) {
        return null;
    }
}
