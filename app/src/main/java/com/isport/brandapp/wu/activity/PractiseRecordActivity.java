package com.isport.brandapp.wu.activity;

import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.device.dialog.BaseDialog;
import com.isport.brandapp.util.DeviceTypeUtil;
import com.isport.brandapp.wu.Constant;
import com.isport.brandapp.wu.bean.PractiseRecordInfo;
import com.isport.brandapp.wu.bean.PractiseTimesInfo;
import com.isport.brandapp.wu.mvp.PractiseRecordView;
import com.isport.brandapp.wu.mvp.presenter.PractiseRecordPresenter;
import com.jkcq.homebike.history.adpter.PractiseAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BaseMVPActivity;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * 手表锻炼记录
 */
public class PractiseRecordActivity extends BaseMVPActivity<PractiseRecordView, PractiseRecordPresenter> implements PractiseRecordView, View.OnClickListener {

    private static final String TAG = "PractiseRecordActivity";

    private LinearLayout ll_nodata;
    private RecyclerView recyclerview_practise;
    private PractiseAdapter mAdapter;
    private RelativeLayout rl_title;

    private TextView tv_sport_time;
    private TextView tv_sport_times;

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_arrow;
    private SmartRefreshLayout refresh_layout;
    private int mSelectedType = Constant.PRACTISE_TYPE_ALL;

    private int mCurrentPage = 0;
    private List<PractiseRecordInfo> mDatalist = new ArrayList<>();

    private int currentType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_practise_record;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.rl_title).init();
    }

    @Override
    protected void initView(View view) {

        ll_nodata = findViewById(R.id.ll_nodata);
        tv_sport_time = findViewById(R.id.tv_sport_time);
        tv_sport_times = findViewById(R.id.tv_sport_times);
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        iv_arrow = findViewById(R.id.iv_arrow);
        rl_title = findViewById(R.id.rl_title);
        refresh_layout = findViewById(R.id.refresh_layout);
        iv_back.setOnClickListener(this);
        iv_arrow.setOnClickListener(this);
        recyclerview_practise = findViewById(R.id.recyclerview_practise);
        recyclerview_practise.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new PractiseAdapter(mDatalist);
        recyclerview_practise.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Logger.myLog("onItemChildClick=" + position);
                if (mDatalist.get(position).isSelect()) {
                    for (int i = 0; i < mDatalist.size(); i++) {
                        mDatalist.get(i).setSelect(false);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < mDatalist.size(); i++) {
                        mDatalist.get(i).setSelect(false);
                    }
                    mDatalist.get(position).setSelect(true);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        refresh_layout.setEnableRefresh(false);
        refresh_layout.setEnableFooterFollowWhenNoMoreData(true);
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mActPresenter.getPractiseRecordData(currentType, mSelectedType, mCurrentPage++);
            }
        });
    }

    @Override
    protected void initData() {
        tv_title.setText(R.string.practise_record);
//        setNodata();
        if (DeviceTypeUtil.isContainW55X()) {
            if(AppConfiguration.deviceMainBeanList.containsKey(JkConfiguration.DeviceType.Watch_W560)){
                currentType = JkConfiguration.DeviceType.Watch_W560;
            }else{
                currentType = JkConfiguration.DeviceType.Watch_W556;
            }

        } else {
            currentType = JkConfiguration.DeviceType.Watch_W812;
        }

       // currentType = JkConfiguration.DeviceType.Watch_W560;

        Log.e(TAG,"----currentType="+currentType);

        mActPresenter.getPractiseRecordData(currentType, Constant.PRACTISE_TYPE_ALL, mCurrentPage);
        mActPresenter.getTotalPractiseTimes(currentType, Constant.PRACTISE_TYPE_ALL);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initHeader() {

    }

    private void getData(int type) {
        isShowPopup = !isShowPopup;
        if (mMenuViewBirth != null && mMenuViewBirth.isShowing()) {
            mMenuViewBirth.dismiss();
        }
        mSelectedType = type;
        mCurrentPage = 0;
        refresh_layout.setEnableLoadMore(true);
        mActPresenter.getPractiseRecordData(currentType, mSelectedType, mCurrentPage);
        mActPresenter.getTotalPractiseTimes(currentType, mSelectedType);
    }

    /**
     * 暂无数据
     */
    private void setNodata() {
        recyclerview_practise.setVisibility(View.GONE);
        ll_nodata.setVisibility(View.VISIBLE);
        tv_sport_time.setText("0");
        tv_sport_times.setText(getString(R.string.total_practise_times, "0"));
    }

    @Override
    protected PractiseRecordPresenter createPresenter() {
        return new PractiseRecordPresenter();
    }

    @Override
    public void getPractiseRecordSuccess(List<PractiseRecordInfo> infos) {

        Logger.myLog(TAG,"------getPractiseRecordSuccess="+new Gson().toJson(infos));

        if (mCurrentPage > 0) {
            if (infos != null && infos.size() > 0) {
                mDatalist.addAll(infos);
                mAdapter.replaceData(mDatalist);
                if (infos.size() < 10) {
                    refresh_layout.finishLoadMoreWithNoMoreData();
                } else {
                    refresh_layout.finishLoadMore();
                }
            } else {
                refresh_layout.finishLoadMoreWithNoMoreData();
            }
        } else {


            if (infos != null && infos.size() > 0) {
                infos.get(0).setSelect(true);
                Log.e(TAG, "size=" + infos.size());
                mDatalist.clear();
                recyclerview_practise.setVisibility(View.VISIBLE);
                ll_nodata.setVisibility(View.GONE);
                recyclerview_practise.scrollToPosition(0);
                mAdapter.replaceData(infos);
//                mAdapter = new PractiseAdapter(this, infos);
//                recyclerview_practise.setAdapter(mAdapter);
                if (infos.size() < 10) {
                    refresh_layout.setEnableLoadMore(false);
//                    refresh_layout.finishLoadMoreWithNoMoreData();
                }
            } else {
                setNodata();
            }
        }
    }

    @Override
    public void getTotalPractiseTimesSuccess(PractiseTimesInfo data) {
        if (data != null) {
            tv_sport_time.setText("" + data.getTotalTime());
            tv_sport_times.setText(getString(R.string.total_practise_times, "" + data.getTotalNum()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_arrow:
                if (DeviceTypeUtil.isContainWatch()) {
                    showSelectPopupWindowW5xx();
                } else {
                    showSelectPopupWindow();
                }
                break;
            case R.id.tv_all:
                tv_title.setText(getString(R.string.practise_record));
                getData(Constant.PRACTISE_TYPE_ALL);
                break;
            case R.id.tv_run:
                tv_title.setText(getString(R.string.run));
                getData(Constant.PRACTISE_TYPE_RUN);
                break;
            case R.id.tv_walk:
                tv_title.setText(getString(R.string.walk));
                getData(Constant.PRACTISE_TYPE_WALK);
                break;
            case R.id.tv_ride:
                tv_title.setText(getString(R.string.ride));
                getData(Constant.PRACTISE_TYPE_RIDE);
                break;
            case R.id.tv_rope_skip:
                tv_title.setText(getString(R.string.rope_skip));
                getData(Constant.PRACTISE_TYPE_ROPE_SKIP);
                break;
            case R.id.tv_badminton:
                tv_title.setText(getString(R.string.badminton));
                getData(Constant.PRACTISE_TYPE_BADMINTON);
                break;
            case R.id.tv_football:
                tv_title.setText(getString(R.string.football));
                getData(Constant.PRACTISE_TYPE_FOOTBALL);
                break;
            case R.id.tv_basketball:
                tv_title.setText(getString(R.string.basketball));
                getData(Constant.PRACTISE_TYPE_BASKETBALL);
                break;
            case R.id.tv_climbing:
                tv_title.setText(getString(R.string.climbing));
                getData(Constant.PRACTISE_TYPE_CLIMBING);
                break;
            case R.id.tv_pingpang:
                tv_title.setText(getString(R.string.pingpang));
                getData(Constant.PRACTISE_TYPE_PINGPANG);
                break;
            case R.id.iv_select_arrow: {
                if (mMenuViewBirth != null && mMenuViewBirth.isShowing()) {
                    mMenuViewBirth.dismiss();
                }
            }
            break;
            case R.id.tempview_view:
                if (mMenuViewBirth != null && mMenuViewBirth.isShowing()) {
                    mMenuViewBirth.dismiss();
                }
                break;

        }
    }

    private boolean isShowPopup = false;


    private BaseDialog mMenuViewBirth;

    private void showSelectPopupWindowW5xx() {


        if (mMenuViewBirth != null && mMenuViewBirth.isShowing()) {
            mMenuViewBirth.dismiss();
        }


        mMenuViewBirth = new BaseDialog.Builder(context)
                .setContentView(R.layout.include_practise_record_select_5xx)
                .fullWidth()
                .setCanceledOnTouchOutside(false)
                .fromBottom(false)
                .show();
        ImageView iv_arrow = mMenuViewBirth.findViewById(R.id.iv_select_arrow);
        TextView tv_all = mMenuViewBirth.findViewById(R.id.tv_all);
        TextView tv_walk = mMenuViewBirth.findViewById(R.id.tv_walk);
        TextView tv_run = mMenuViewBirth.findViewById(R.id.tv_run);
        TextView tv_ride = mMenuViewBirth.findViewById(R.id.tv_ride);
        TextView tv_badminton = mMenuViewBirth.findViewById(R.id.tv_badminton);
        TextView tv_football = mMenuViewBirth.findViewById(R.id.tv_football);
        TextView tv_basketball = mMenuViewBirth.findViewById(R.id.tv_basketball);
        TextView tv_climbing = mMenuViewBirth.findViewById(R.id.tv_climbing);
        TextView tv_pingpang = mMenuViewBirth.findViewById(R.id.tv_pingpang);
        View tempView = mMenuViewBirth.findViewById(R.id.tempview_view);

        tv_all.setOnClickListener(this);
        tv_walk.setOnClickListener(this);
        tv_run.setOnClickListener(this);
        tv_ride.setOnClickListener(PractiseRecordActivity.this);
        tv_badminton.setOnClickListener(PractiseRecordActivity.this);
        tv_football.setOnClickListener(PractiseRecordActivity.this);
        tv_pingpang.setOnClickListener(PractiseRecordActivity.this);
        tv_basketball.setOnClickListener(PractiseRecordActivity.this);
        tv_climbing.setOnClickListener(PractiseRecordActivity.this);
        iv_arrow.setOnClickListener(this);
        tempView.setOnClickListener(PractiseRecordActivity.this);
        switch (mSelectedType) {
            case Constant.PRACTISE_TYPE_ALL:
                tv_all.setTypeface(Typeface.DEFAULT_BOLD);
                tv_all.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_WALK:
                tv_walk.setTypeface(Typeface.DEFAULT_BOLD);
                tv_walk.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_RUN:
                tv_run.setTypeface(Typeface.DEFAULT_BOLD);
                tv_run.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_RIDE:
                tv_ride.setTypeface(Typeface.DEFAULT_BOLD);
                tv_ride.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_BADMINTON:
                tv_badminton.setTypeface(Typeface.DEFAULT_BOLD);
                tv_badminton.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_FOOTBALL:
                tv_football.setTypeface(Typeface.DEFAULT_BOLD);
                tv_football.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_BASKETBALL:
                tv_basketball.setTypeface(Typeface.DEFAULT_BOLD);
                tv_basketball.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_CLIMBING:
                tv_climbing.setTypeface(Typeface.DEFAULT_BOLD);
                tv_climbing.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_PINGPANG:
                tv_pingpang.setTypeface(Typeface.DEFAULT_BOLD);
                tv_pingpang.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
        }

    }


    private void showSelectPopupWindow() {
        if (mMenuViewBirth != null && mMenuViewBirth.isShowing()) {
            mMenuViewBirth.dismiss();
        }


        mMenuViewBirth = new BaseDialog.Builder(context)
                .setContentView(R.layout.include_practise_record_select)
                .fullWidth()
                .setCanceledOnTouchOutside(false)
                .fromBottom(false)
                .show();
        LinearLayout layoutbottom = mMenuViewBirth.findViewById(R.id.layout_bottom);
        ImageView iv_arrow = mMenuViewBirth.findViewById(R.id.iv_select_arrow);
        TextView tv_all = mMenuViewBirth.findViewById(R.id.tv_all);
        TextView tv_walk = mMenuViewBirth.findViewById(R.id.tv_walk);
        TextView tv_run = mMenuViewBirth.findViewById(R.id.tv_run);
        TextView tv_ride = mMenuViewBirth.findViewById(R.id.tv_ride);
        TextView tv_rope_skip = mMenuViewBirth.findViewById(R.id.tv_rope_skip);
        TextView tv_badminton = mMenuViewBirth.findViewById(R.id.tv_badminton);
        TextView tv_football = mMenuViewBirth.findViewById(R.id.tv_football);
        TextView tv_basketball = mMenuViewBirth.findViewById(R.id.tv_basketball);
        TextView tv_climbing = mMenuViewBirth.findViewById(R.id.tv_climbing);
        TextView tv_pingpang = mMenuViewBirth.findViewById(R.id.tv_pingpang);
        View tempView = mMenuViewBirth.findViewById(R.id.tempview_view);

        iv_arrow.setOnClickListener(this);
        tv_all.setOnClickListener(PractiseRecordActivity.this);
        tv_walk.setOnClickListener(PractiseRecordActivity.this);
        tv_run.setOnClickListener(PractiseRecordActivity.this);
        tv_ride.setOnClickListener(PractiseRecordActivity.this);
        tv_rope_skip.setOnClickListener(PractiseRecordActivity.this);
        tv_badminton.setOnClickListener(PractiseRecordActivity.this);
        tv_football.setOnClickListener(PractiseRecordActivity.this);
        tv_pingpang.setOnClickListener(PractiseRecordActivity.this);
        tv_basketball.setOnClickListener(PractiseRecordActivity.this);
        tv_climbing.setOnClickListener(PractiseRecordActivity.this);
        tempView.setOnClickListener(PractiseRecordActivity.this);
        if (currentType == JkConfiguration.DeviceType.Watch_W556) {
            tv_climbing.setVisibility(View.VISIBLE);
            layoutbottom.setVisibility(View.VISIBLE);
        } else {
            layoutbottom.setVisibility(View.GONE);
            tv_climbing.setVisibility(View.INVISIBLE);
        }
        switch (mSelectedType) {
            case Constant.PRACTISE_TYPE_ALL:

                tv_all.setTypeface(Typeface.DEFAULT_BOLD);
                tv_all.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_WALK:
                tv_walk.setTypeface(Typeface.DEFAULT_BOLD);
                tv_walk.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_RUN:
                tv_run.setTypeface(Typeface.DEFAULT_BOLD);
                tv_run.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_RIDE:
                tv_ride.setTypeface(Typeface.DEFAULT_BOLD);
                tv_ride.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_ROPE_SKIP:
                tv_rope_skip.setTypeface(Typeface.DEFAULT_BOLD);
                tv_rope_skip.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_BADMINTON:
                tv_badminton.setTypeface(Typeface.DEFAULT_BOLD);
                tv_badminton.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_FOOTBALL:
                tv_football.setTypeface(Typeface.DEFAULT_BOLD);
                tv_football.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_BASKETBALL:
                tv_basketball.setTypeface(Typeface.DEFAULT_BOLD);
                tv_basketball.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_CLIMBING:
                tv_climbing.setTypeface(Typeface.DEFAULT_BOLD);
                tv_climbing.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
            case Constant.PRACTISE_TYPE_PINGPANG:
                tv_pingpang.setTypeface(Typeface.DEFAULT_BOLD);
                tv_pingpang.setTextColor(UIUtils.getColor(R.color.common_view_color));
                break;
        }
        isShowPopup = true;

    }


}
