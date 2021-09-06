package com.isport.brandapp.sport.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.isport.brandapp.sport.bean.MoreTypeBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by SCWANG on 2017/6/11.
 */

@SuppressWarnings({"UnusedReturnValue", "unused"})
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<SmartViewHolder> implements ListAdapter {
    public static final int type_month = 0;
    public static final int type_item = 1;
    public static final int type_head = 2;
    public static final int type_chart = 3;
    public static final int type_no_data = 4;

    //<editor-fold desc="BaseRecyclerAdapter">

    private int mLayoutIdHead;
    private int mLayoutIdChart;
    private int mLayoutIdMonth;
    private int mLayoutIdSportItem;
    private int mLayoutnoData;
    private List<MoreTypeBean> mList;
    private int mLastPosition = -1;
    private boolean mOpenAnimationEnable = true;
    protected AdapterView.OnItemClickListener mListener;

    public BaseRecyclerAdapter(@LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>();
        this.mLayoutIdHead = layoutId;
    }

    public BaseRecyclerAdapter(Collection collection, @LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>(collection);
        this.mLayoutIdHead = layoutId;
    }

    public BaseRecyclerAdapter(Collection collection, @LayoutRes int layoutId, AdapterView.OnItemClickListener listener) {
        setHasStableIds(false);
        setOnItemClickListener(listener);
        this.mList = new ArrayList<>(collection);
        this.mLayoutIdHead = layoutId;
    }

    public BaseRecyclerAdapter(Collection collection, @LayoutRes int layoutHead, @LayoutRes int layoutChart, @LayoutRes int layoutIdmonth, @LayoutRes int layoutIdsportItem, @LayoutRes int layoutIdNodataItem, AdapterView.OnItemClickListener listener) {
        setHasStableIds(false);
        setOnItemClickListener(listener);
        this.mList = new ArrayList<>(collection);
        this.mLayoutIdHead = layoutHead;
        this.mLayoutIdChart = layoutChart;
        this.mLayoutIdMonth = layoutIdmonth;
        this.mLayoutIdSportItem = layoutIdsportItem;
        this.mLayoutnoData = layoutIdNodataItem;
    }

    public BaseRecyclerAdapter(Collection collection, @LayoutRes int layoutHead, @LayoutRes int layoutChart, @LayoutRes int layoutIdmonth, @LayoutRes int layoutIdsportItem, AdapterView.OnItemClickListener listener) {
        setHasStableIds(false);
        setOnItemClickListener(listener);
        this.mList = new ArrayList<>(collection);
        this.mLayoutIdHead = layoutHead;
        this.mLayoutIdChart = layoutChart;
        this.mLayoutIdMonth = layoutIdmonth;
        this.mLayoutIdSportItem = layoutIdsportItem;
    }

    public void setDataList(Collection collection) {
        this.mList = new ArrayList<>(collection);
    }

    public BaseRecyclerAdapter(Collection collection, @LayoutRes int layoutHead, @LayoutRes int layoutIdmonth, @LayoutRes int layoutIdsportItem, AdapterView.OnItemClickListener listener) {
        setHasStableIds(false);
        setOnItemClickListener(listener);
        this.mList = new ArrayList<>(collection);
        this.mLayoutIdHead = layoutHead;
        this.mLayoutIdMonth = layoutIdmonth;
        this.mLayoutIdSportItem = layoutIdsportItem;
    }
    //</editor-fold>


    private void addAnimate(SmartViewHolder holder, int postion) {
        if (mOpenAnimationEnable && mLastPosition < postion) {
            holder.itemView.setAlpha(0);
            holder.itemView.animate().alpha(1).start();
            mLastPosition = postion;
        }
    }


    //<editor-fold desc="RecyclerAdapter">
    @Override
    public SmartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == type_month) {
            return new SmartViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutIdMonth, parent, false), mListener);
        } else if (viewType == type_chart) {
            return new SmartViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutIdChart, parent, false), mListener);
        } else if (viewType == type_head) {
            return new SmartViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutIdHead, parent, false), mListener);
        } else {
            return new SmartViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutIdSportItem, parent, false), mListener);
        }
    }

    @Override
    public void onBindViewHolder(SmartViewHolder holder, int position) {
        onBindViewHolder(holder, position < mList.size() ? mList.get(position) : null, position);
    }

    protected abstract void onBindViewHolder(SmartViewHolder holder, MoreTypeBean model, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onViewAttachedToWindow(SmartViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        addAnimate(holder, holder.getLayoutPosition());
    }

    public void setOpenAnimationEnable(boolean enabled) {
        this.mOpenAnimationEnable = enabled;
    }

    //</editor-fold>

    //<editor-fold desc="API">

    public MoreTypeBean get(int index) {
        return mList.get(index);
    }

    public BaseRecyclerAdapter setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
        return this;
    }

    public BaseRecyclerAdapter refresh(Collection collection) {
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        mLastPosition = -1;
        return this;
    }

    public BaseRecyclerAdapter loadMore(Collection collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    public BaseRecyclerAdapter insert(Collection collection) {
        mList.addAll(0, collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }


    //</editor-fold>

    //<editor-fold desc="ListAdapter">
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

//    public boolean hasStableIds() {
//        return false;
//    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyListDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmartViewHolder holder;
        if (convertView != null) {
            holder = (SmartViewHolder) convertView.getTag();
        } else {
            holder = onCreateViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        }
        holder.setPosition(position);
        onBindViewHolder(holder, position);
        addAnimate(holder, position);
        return convertView;
    }

    public int getItemViewType(int position) {
        MoreTypeBean moreTypeBean = mList.get(position);
        if (moreTypeBean.type == type_month) {
            return type_month;
        } else if (moreTypeBean.type == type_item) {
            return type_item;
        } else if (moreTypeBean.type == type_chart) {
            return type_chart;
        } else {
            return type_head;
        }
    }


    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    //</editor-fold>
}
