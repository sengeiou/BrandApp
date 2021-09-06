package com.isport.brandapp.banner.recycleView.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.isport.brandapp.banner.recycleView.holder.CustomHolder;
import com.isport.brandapp.banner.recycleView.holder.CustomPeakHolder;
import com.isport.brandapp.banner.recycleView.inter.DefaultAdapterViewLisenter;

import java.util.Vector;


/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/7 16:43
 */
public class RefrushAdapter<T> extends DefaultAdapter<T> {

    private int refrushPosition = 0;

    public RefrushAdapter(Context context, Vector lists, int itemID, DefaultAdapterViewLisenter lisenter) {
        super(context, lists, itemID, lisenter);
    }

    public void setRefrushPosition(int position) {
        this.refrushPosition = position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == refrushPosition && tops.size() > 0) {
            return TOP;
        } else if (position < heards.size() + tops.size()) {
            if (position < refrushPosition) {
                return position;
            } else if (position > refrushPosition && tops.size() > 0) {
                return position;
            } else {
                return position;
            }
        } else if (position < heards.size() + lists.size() + tops.size()) {
            int id = position - heards.size() - tops.size();
            return (int) lists.get(id);
        } else if (position < heards.size() + lists.size() + tops.size() + foots.size()) {
            return position;
        } else {
            return FOOT;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int id;
        if (position == refrushPosition && tops.size() > 0) {
            id = position;
            ((CustomPeakHolder) holder).initView(id, context);
        } else if (position < heards.size() + tops.size()) {
            id = position - tops.size();
            if (position < refrushPosition) {
                id = position;
            } else if (position > refrushPosition && tops.size() > 0) {
                id = position - 1;
            }
            ((CustomPeakHolder) holder).initView(id, context);
        } else if (position < heards.size() + lists.size() + tops.size()) {
            id = position - heards.size() - tops.size();
            ((CustomHolder) holder).initView(id, lists, context);
        } else if (position < heards.size() + lists.size() + tops.size() + foots.size()) {
            id = position - heards.size() - tops.size() - lists.size();
            ((CustomPeakHolder) holder).initView(id, context);
        } else {
            id = position - (heards.size() + lists.size() + tops.size() + foots.size());
            ((CustomPeakHolder) holder).initView(id, context);
        }


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        if (viewType == TOP) {
            holder = (RecyclerView.ViewHolder) tops.get(0);
        } else if (viewType < heards.size() + tops.size()) {
            if (viewType > refrushPosition && tops.size() > 0) {
                holder = (RecyclerView.ViewHolder) heards.get(viewType - tops.size());
            } else {
                holder = (RecyclerView.ViewHolder) heards.get(viewType);
            }
        } else if (viewType == BODY) {
            holder = ((DefaultAdapterViewLisenter) listener).getBodyHolder(context, lists, itemID);
        } else if (viewType == BODY_HEADER) {
            holder = ((DefaultAdapterViewLisenter) listener).getHeader(context, lists, itemID);
        } else if (viewType == BODY_END) {
            holder = ((DefaultAdapterViewLisenter) listener).getFooter(context, lists, itemID);
        } else if (viewType == BODY_DEVICE) {
            holder = ((DefaultAdapterViewLisenter) listener).getConect(context, lists, itemID);
        } else if (viewType == BODY_DEVICE1) {
            holder = ((DefaultAdapterViewLisenter) listener).getConect1(context, lists, itemID);
        } else if (viewType == BODY_DEVICE2) {
            holder = ((DefaultAdapterViewLisenter) listener).getConect2(context, lists, itemID);
        } else if (viewType == BODY_SCALE) {
            holder = ((DefaultAdapterViewLisenter) listener).getConectScale(context, lists, itemID);
        } else if (viewType == BODY_HEARTRATE) {
            holder = ((DefaultAdapterViewLisenter) listener).getConectHeartRate(context, lists, itemID);
        } else if (viewType == BODY_OXYGEN) {
            holder = ((DefaultAdapterViewLisenter) listener).getOxyGenItem(context, lists, itemID);
        } else if (viewType == BODY_BLOODPRESSURE) {
            holder = ((DefaultAdapterViewLisenter) listener).getBloodPressureItem(context, lists, itemID);
        } else if (viewType == BODY_EXCERICE) {
            holder = ((DefaultAdapterViewLisenter) listener).getExecericeItem(context, lists, itemID);
        } else if (viewType == BODY_ONECE_HR) {
            holder = ((DefaultAdapterViewLisenter) listener).getOnceHrItem(context, lists, itemID);
        } else if (viewType == BODY_SLEEP) {
            holder = ((DefaultAdapterViewLisenter) listener).getConectSleep(context, lists, itemID);
        } else if (viewType == BODY_TEMP) {
            holder = ((DefaultAdapterViewLisenter) listener).getTempItem(context, lists, itemID);
        } else if (viewType < heards.size() + lists.size() + tops.size() + foots.size()) {
            holder = (RecyclerView.ViewHolder) foots.get(viewType - heards.size() - lists.size() - tops.size());
        } else if (viewType == FOOT) {
            holder = (RecyclerView.ViewHolder) booms.get(0);
        }
        return holder;
    }
}
