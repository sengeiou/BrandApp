package com.isport.brandapp.wu.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.isport.brandapp.R;
import com.isport.brandapp.wu.adapter.OxyAdapter;
import com.isport.brandapp.wu.bean.OxyInfo;
import com.isport.brandapp.wu.mvp.OxyHistoryView;
import com.isport.brandapp.wu.mvp.presenter.OxyHistoryPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;

public class OxyListActivity extends BaseMVPTitleActivity<OxyHistoryView, OxyHistoryPresenter> implements OxyHistoryView {

    LinearLayout ll_nodata;
    private RecyclerView recyclerview_oxy;
    private OxyAdapter mAdapter;
    private ArrayList<OxyInfo> mDataList = new ArrayList<>();

    private SmartRefreshLayout refresh_layout;
    private int mCurrentPage = 0;
    private boolean isPullRefresh = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oxylist;
    }

    @Override
    protected void initView(View view) {
        ll_nodata = findViewById(R.id.ll_nodata);
        recyclerview_oxy = findViewById(R.id.recyclerview_oxy);
        recyclerview_oxy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new OxyAdapter(this, mDataList);
        recyclerview_oxy.setAdapter(mAdapter);

        //下拉刷新，到底自动加载更多
        refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setEnableFooterFollowWhenNoMoreData(true);
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isPullRefresh = false;
                mActPresenter.getOxyHistoryData(++mCurrentPage);

            }
        });
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isPullRefresh = true;
                mCurrentPage = 0;
                mActPresenter.getOxyHistoryData(mCurrentPage);
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initHeader() {
        titleBarView.setTitle(getResources().getString(R.string.oxy_saturation));
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {

            }
        });
    }

    @Override
    protected OxyHistoryPresenter createPresenter() {
        return new OxyHistoryPresenter();
    }

    protected void initData() {
        mActPresenter.getOxyHistoryData(0);
    }


    @Override
    public void getOxyHistoryDataSuccess(List<OxyInfo> info) {
        if (refresh_layout != null) {
            if (info != null && info.size() > 0) {
                ll_nodata.setVisibility(View.GONE);
                if (isPullRefresh) {
                    mDataList.clear();
                    refresh_layout.finishRefresh();
                    refresh_layout.setEnableLoadMore(true);
                } else {
                    refresh_layout.finishLoadMore();
                }
                if (info.size() < 10) {
                    refresh_layout.finishLoadMoreWithNoMoreData();
                }
                if (mAdapter != null) {
                    mDataList.addAll(info);
                    mAdapter.replaceData(mDataList);
                }
            } else {
                if (isPullRefresh) {
                    ll_nodata.setVisibility(View.VISIBLE);
                }
                else{
                    refresh_layout.finishLoadMoreWithNoMoreData();
                }

            }
        }
    }
}
