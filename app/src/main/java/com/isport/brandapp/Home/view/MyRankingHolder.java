package com.isport.brandapp.Home.view;

import android.content.Context;
import android.view.View;

import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;

import java.util.List;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;

public class MyRankingHolder extends CustomHolder<String> {
    //List<FirstLevelsBean> listBeans;
    ItemView itemViewRanking;

    public MyRankingHolder(View itemView) {
        super(itemView);
    }

    public MyRankingHolder(List<String> datas, View itemView) {
        super(datas, itemView);
    }

    public MyRankingHolder(final Context context, final List<String> lists, int itemID) {
        super(context, lists, itemID);
        itemViewRanking = itemView.findViewById(R.id.itemview_ranking);
        itemViewRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ViewMultiClickUtil.onMultiClick(view)) {
                    return;
                }
                if (listener != null) {
                    listener.onRankingItem();
                }
            }
        });

    }


    onRankingLister listener;

    public void setOnItemClickListener(onRankingLister listener) {
        this.listener = listener;
    }

    public interface onRankingLister {
        void onRankingItem();

    }


}
