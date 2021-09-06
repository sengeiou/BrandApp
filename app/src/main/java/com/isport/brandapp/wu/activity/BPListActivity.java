package com.isport.brandapp.wu.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.isport.brandapp.R;
import com.isport.brandapp.wu.adapter.BpAdapter;
import com.isport.brandapp.wu.bean.BPInfo;
import com.isport.brandapp.wu.mvp.BpHistoryView;
import com.isport.brandapp.wu.mvp.presenter.BpHistoryPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;

public class BPListActivity extends BaseMVPTitleActivity<BpHistoryView, BpHistoryPresenter> implements BpHistoryView {

    private RecyclerView recyclerview_bp;
    private BpAdapter mAdapter;
    private ArrayList<BPInfo> mDataList = new ArrayList<>();
    private SmartRefreshLayout refresh_layout;
    private int mCurrentPage = 0;
    private boolean isPullRefresh = true;

    private LinearLayout ll_nodata;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bplist;
    }

    @Override
    protected void initView(View view) {

        ll_nodata = findViewById(R.id.ll_nodata);
        recyclerview_bp = findViewById(R.id.recyclerview_bp);
        recyclerview_bp.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new BpAdapter(this, mDataList);
        recyclerview_bp.setAdapter(mAdapter);

        //下拉刷新，到底自动加载更多
        refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setEnableFooterFollowWhenNoMoreData(true);
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isPullRefresh = false;
                mActPresenter.getBpHistoryData(++mCurrentPage);

            }
        });
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isPullRefresh = true;
                mCurrentPage = 0;
                mActPresenter.getBpHistoryData(mCurrentPage);
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initHeader() {
        titleBarView.setTitle(getResources().getString(R.string.bp));
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
    protected BpHistoryPresenter createPresenter() {
        return new BpHistoryPresenter();
    }

    @Override
    protected void initData() {
        mActPresenter.getBpHistoryData(mCurrentPage);
    }


    @Override
    public void getBpHistorySuccess(List<BPInfo> info) {
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
