package com.isport.brandapp.device.history;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.device.band.bean.BandDayBean;
import com.isport.brandapp.device.history.adapter.HistoryAdapter;
import com.isport.brandapp.device.history.presenter.HistoryPresenter;
import com.isport.brandapp.device.history.view.HistoryView;
import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceDataUtil;
import com.isport.brandapp.util.RequestCode;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

public class ActivityHistory extends BaseMVPTitleActivity<HistoryView, HistoryPresenter> implements HistoryView,
        XListView.IXListViewListener {
    XListView xListview;
    ArrayList<BandDayBean> lists;
    ArrayList<HistoryBeanList> scaleHistoryBeanLists;
    HistoryAdapter mAdapter;

    int pageSize = 0;

    boolean isRfrsh = false;

    int type;
    int titleRes;
    private long mCurrentTimeMillis;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_history;
    }

    @Override
    protected void initView(View view) {

        xListview = view.findViewById(R.id.recycler_result);
        lists = new ArrayList<>();
        xListview.setCacheColorHint(0);
        xListview.setDividerHeight(0);
        xListview.setPullLoadEnable(true);
        xListview.setPullRefreshEnable(true);
        xListview.setHeaderDividersEnabled(false);
        xListview.setFooterDividersEnabled(false);
    }

    private void getIntentValue() {
        type = getIntent().getIntExtra(JkConfiguration.FROM_TYPE, JkConfiguration.DeviceType.WATCH_W516);
        switch (type) {
            case JkConfiguration.DeviceType.BODYFAT:
                titleRes = R.string.weight_record;
                break;
            case JkConfiguration.DeviceType.SLEEP:
                titleRes = R.string.sleep_record;
                break;
            case JkConfiguration.DeviceType.WATCH_W516:
                titleRes = R.string.step_history;
                titleRes = R.string.app_bmp_recode;
                break;
        }
    }

    @Override
    protected void initData() {

        getIntentValue();
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(getResources().getString(titleRes));
        titleBarView.setRightText("");

        lists = new ArrayList<>();
        pageSize = 0;
        isRfrsh = true;
        // TODO: 2019/1/14 需要传入deviceId
        //获取当前时间戳,
        mCurrentTimeMillis = System.currentTimeMillis();
        mActPresenter.getFirstHistoryModel(RequestCode.Request_getWatchHistoryData, mCurrentTimeMillis,
                                           AppConfiguration.deviceBeanList.get(JkConfiguration.DeviceType.WATCH_W516)
                                                   .deviceID, "", "1", "", "", pageSize + "", "");
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

    public void stopXlist() {
        xListview.stopLoadMore();
        xListview.stopRefresh();
    }

    Handler mHandler = new Handler();

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
            mCurrentTimeMillis = System.currentTimeMillis();
            mActPresenter.getFirstHistoryModel(RequestCode.Request_getWatchHistoryData, mCurrentTimeMillis,
                                               AppConfiguration.deviceBeanList.get(JkConfiguration.DeviceType.WATCH_W516)
                                                       .deviceID, "", JkConfiguration.DeviceType.WATCH_W516 + "", "", "",
                                               pageSize + "",
                                               "");


        }, 500);

    }

    @Override
    public void onLoadMore() {
        if (isRfrsh) {
            xListview.stopLoadMore();
            return;
        }
        mHandler.postDelayed(() -> {
            isRfrsh = true;
            // TODO: 2019/1/14 需要传入deviceId
            // TODO: 2019/1/16 需要拿到返回的时间戳，来作为查询的依据
            mActPresenter.getSportHistroyModel(RequestCode.Request_getWatchHistoryData, mCurrentTimeMillis,
                                               AppConfiguration.deviceBeanList.get(JkConfiguration.DeviceType.WATCH_W516)
                                                       .deviceID, "", JkConfiguration.DeviceType.WATCH_W516 + "", "", "",
                                               pageSize + "",
                                               "");


        }, 500);

    }

    @Override
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter(this);
    }

    @Override
    public void onRespondError(String message) {
        stopXlist();
    }


    @Override
    public void successSportRefresh(ArrayList<BandDayBean> historyBean) {
        //recyclerDetail.stopRefreshing();
        stopXlist();
        isRfrsh = false;

        if (scaleHistoryBeanLists == null) {
            scaleHistoryBeanLists = new ArrayList();
        }
        scaleHistoryBeanLists.clear();
        lists.clear();
        if (historyBean != null) {
            if (historyBean.size() > 20) {
                xListview.setPullLoadEnable(true);
            } else {
                xListview.setPullLoadEnable(false);
            }
            lists.addAll(historyBean);
            if (historyBean.size()>0){
                //获取最后一天的时间，移到前一天的23.59
                String timeByyyyyMMdd = TimeUtils.getTimeByyyyyMMdd(historyBean.get(historyBean.size() - 1).buildTime);
                Calendar instance = Calendar.getInstance();
                instance.setTime(new Date(historyBean.get(historyBean.size() - 1).buildTime));
                instance.add(Calendar.DAY_OF_MONTH,-1);//向前移动一天
                instance.set(Calendar.HOUR_OF_DAY,23);
                instance.set(Calendar.MINUTE,59);
                instance.set(Calendar.SECOND,59);
                Logger.myLog("getTimeInMillis == "+instance.getTimeInMillis());
                mCurrentTimeMillis=instance.getTimeInMillis();
            }
        }
        HistoryBeanList title = new HistoryBeanList();
        title.tilteBean = new HistoryTilteBean();
        title.tilteBean.currentType = JkConfiguration.DeviceType.WATCH_W516;
        title.tilteBean.one = R.string.time;
        title.tilteBean.two = R.string.app_band_step_number;
        title.tilteBean.three = R.string.app_band_step_dis;
        title.tilteBean.four = R.string.app_band_step_cal;
        title.viewType = JkConfiguration.HistoryType.TYPE_TITLE;
        title.DeviceTpye = JkConfiguration.DeviceType.WATCH_W516;
        scaleHistoryBeanLists.add(title);


        HistoryBeanList divider = new HistoryBeanList();
        divider.viewType = JkConfiguration.HistoryType.TYPE_DIVIDER;
        scaleHistoryBeanLists.add(divider);


        scaleHistoryBeanLists.addAll(DeviceDataUtil.parseBandData(lists));

        if (mAdapter == null) {
            mAdapter = new HistoryAdapter(this, scaleHistoryBeanLists, R.layout.item_history_item, R.layout
                    .item_history_title, R.layout.item_history_month);
            xListview.setAdapter(mAdapter);
            //recyclerDetail.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }


        //adapterScaleHistory.setData(lists);
        //adapterScaleHistory.notifyDataSetChanged();
        pageSize = 0;
        pageSize = pageSize + 1;
    }

    @Override
    public void successSportLoadMore(ArrayList<BandDayBean> historyBean) {
        isRfrsh = false;
        lists.clear();
        if (historyBean != null) {
            lists.addAll(historyBean);
        }
        scaleHistoryBeanLists.addAll(DeviceDataUtil.parseBandData(lists));
        stopXlist();
        mAdapter.notifyDataSetChanged();
        pageSize = pageSize + 1;
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
                App.initAppState();
                AppSP.putBoolean(context, AppSP.CAN_RECONNECT, false);
                Intent intent = new Intent(context,ActivityLogin.class);
                context.startActivity(intent);
                ActivityManager.getInstance().finishAllActivity(ActivityLogin.class.getSimpleName());
                break;
            default:
                break;
        }
    }
}
