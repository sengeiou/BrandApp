package com.isport.brandapp.device.sleep.adpter;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.isport.brandapp.R;
import com.isport.brandapp.device.sleep.bean.SleepHistoryList;

import brandapp.isport.com.basicres.commonrecyclerview.FullyLinearLayoutManager;
import brandapp.isport.com.basicres.commonrecyclerview.RefreshRecyclerView;
import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;

/**
 * Created by huashao on 2017/11/13.
 */
public class AdapterSleepHistory extends BaseCommonRefreshRecyclerAdapter<SleepHistoryList, AdapterSleepHistory.ViewHolder> {


    public AdapterSleepHistory(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_history_month;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }

    @Override
    protected void initData(ViewHolder viewHolder, int position, SleepHistoryList item) {
        AdapterSleepHistroyDay adapterScaleHistroyDay = new AdapterSleepHistroyDay(context);


        FullyLinearLayoutManager mClubFullyLinearLayoutManager = new FullyLinearLayoutManager(context);
        mClubFullyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.refrushRecycleView.setLayoutManager(mClubFullyLinearLayoutManager);
        viewHolder.refrushRecycleView.setAdapter(adapterScaleHistroyDay);
        adapterScaleHistroyDay.setData(item.getDatalist());
        viewHolder.tvDetail.setText(item.getMonth());


    }


    @Override
    protected void initEvent(ViewHolder viewHolder, int position, SleepHistoryList item) {


    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        private TextView tvDetail;
        private RefreshRecyclerView refrushRecycleView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDetail = itemView.findViewById(R.id.tv_detail_value);
            refrushRecycleView = itemView.findViewById(R.id.recycler);
        }
    }
}
