package com.isport.brandapp.device.sleep;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.device.history.adapter.HistoryAdapter;
import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.device.sleep.bean.SleepHistoryList;
import com.isport.brandapp.device.sleep.presenter.SleepHistoryPresenter;
import com.isport.brandapp.device.sleep.view.SleepHistoryView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceDataUtil;
import com.isport.brandapp.util.RequestCode;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;

import brandapp.isport.com.basicres.ActivityManager;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonrecyclerview.Xlist.XListView;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class ActivitySleepHistory extends BaseMVPTitleActivity<SleepHistoryView, SleepHistoryPresenter> implements
        SleepHistoryView, XListView.IXListViewListener {
    XListView xListview;
    ArrayList<SleepHistoryList> lists;
    ArrayList<HistoryBeanList> sleepHistoryBeanLists;
    HistoryAdapter mAdapter;


    int pageSize = 0;

    boolean isRfrsh = false;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_history;
    }

    @Override
    protected void initView(View view) {

        xListview = view.findViewById(R.id.recycler_result);


        xListview.setCacheColorHint(0);
        xListview.setDividerHeight(0);
        xListview.setPullLoadEnable(true);
        xListview.setPullRefreshEnable(true);
        xListview.setHeaderDividersEnabled(false);
        xListview.setFooterDividersEnabled(false);

    }

    @Override
    protected void initData() {

        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(getResources().getString(R.string.sleep_record));
        titleBarView.setRightText("");

        lists = new ArrayList<>();

        pageSize = 0;
        isRfrsh = true;
        // TODO: 2019/1/14 需要传入deviceId
        mActPresenter.getFirstHistoryModel(RequestCode.Request_getSleepHistoryData, Calendar.getInstance().getTimeInMillis(), "", "", JkConfiguration.DeviceType.SLEEP + "", "", "", pageSize + "", "");
    }

    @Override
    protected void initEvent() {
        xListview.setXListViewListener(this);
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
    protected void initHeader() {

    }


    @Override
    public void successRefresh(ArrayList<SleepHistoryList> historyBean, boolean islastdata) {
        pageSize = 0;
        lists.clear();
        if (sleepHistoryBeanLists == null) {
            sleepHistoryBeanLists = new ArrayList<>();
        }
        sleepHistoryBeanLists.clear();

        if (historyBean != null) {
            sleepHistoryBeanLists.addAll(DeviceDataUtil.parseSleepData(historyBean, true));
        }
        if (mAdapter == null) {
            mAdapter = new HistoryAdapter(this, sleepHistoryBeanLists, R.layout.item_history_item, R.layout.item_history_title, R.layout.item_history_month);
            xListview.setAdapter(mAdapter);
            //recyclerDetail.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        successDataSetXlist(islastdata);

    }

    @Override
    public void successLoadMore(ArrayList<SleepHistoryList> historyBean, boolean islastdata) {
        if (historyBean != null && historyBean.size() != 0) {
            sleepHistoryBeanLists.addAll(DeviceDataUtil.parseSleepData(historyBean, false));
        }
        mAdapter.notifyDataSetChanged();
        successDataSetXlist(islastdata);
    }

    @Override
    protected SleepHistoryPresenter createPresenter() {
        return new SleepHistoryPresenter(this);
    }

    @Override
    public void onRefresh() {

        pageSize = 0;
        if (isRfrsh) {
            xListview.stopRefresh();
            return;
        }
        mHandler.postDelayed(() -> {
            isRfrsh = true;
            // TODO: 2019/1/14 需要传入deviceId
            mActPresenter.getFirstHistoryModel(RequestCode.Request_getSleepHistoryData, Calendar.getInstance().getTimeInMillis(),"","",JkConfiguration.DeviceType.SLEEP + "", "", "", pageSize + "", "");


        }, 500);

    }

    Handler mHandler = new Handler();

    @Override
    public void onLoadMore() {
        if (isRfrsh) {
            xListview.stopLoadMore();
            return;
        }
        mHandler.postDelayed(() -> {
            isRfrsh = true;
            // TODO: 2019/1/14 需要传入deviceId
            mActPresenter.getSleepHistoryModel(RequestCode.Request_getSleepHistoryData, Calendar.getInstance().getTimeInMillis(),"","",JkConfiguration.DeviceType.SLEEP + "", "", "", pageSize + "", "");


        }, 500);

    }

    public void successDataSetXlist(boolean isLastData) {
        isRfrsh = false;
        pageSize = pageSize + 1;
        stopXlist();
        isShowLoad(isLastData);

    }

    public void stopXlist() {
        isRfrsh = false;
        xListview.stopLoadMore();
        xListview.stopRefresh();
    }

    public void isShowLoad(boolean islastdata) {
        if (islastdata) {
            xListview.setPullLoadEnable(false);
        } else {
            xListview.setPullLoadEnable(true);
        }
    }

    @Override
    public void onRespondError(String message) {
        super.onRespondError(message);
        stopXlist();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.NEED_LOGIN:
                ToastUtils.showToast(context, UIUtils.getString(com.isport.brandapp.basicres.R.string.login_again));
                NetProgressObservable.getInstance().hide();
                BleProgressObservable.getInstance().hide();
                TokenUtil.getInstance().clear(context);
                UserAcacheUtil.clearAll();
                AppSP.putBoolean(context, AppSP.CAN_RECONNECT, false);
                App.initAppState();
                Intent intent = new Intent(context,ActivityLogin.class);
                context.startActivity(intent);
                ActivityManager.getInstance().finishAllActivity(ActivityLogin.class.getSimpleName());
                break;
            default:
                break;
        }
    }
}
