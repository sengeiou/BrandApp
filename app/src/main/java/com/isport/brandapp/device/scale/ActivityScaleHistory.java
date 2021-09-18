package com.isport.brandapp.device.scale;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.App;
import com.isport.brandapp.home.bean.http.Fatsteelyard;
import com.isport.brandapp.R;
import com.isport.brandapp.device.history.HistoryTilteBean;
import com.isport.brandapp.device.history.adapter.HistoryAdapter;
import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.device.scale.bean.ScaleHistoryBean;
import com.isport.brandapp.device.scale.bean.ScaleHistroyList;
import com.isport.brandapp.device.scale.bean.ScaleHistroyNList;
import com.isport.brandapp.device.scale.presenter.ScaleHistoryPresenter;
import com.isport.brandapp.device.scale.view.ScaleHistoryView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceDataUtil;
import com.isport.brandapp.util.RequestCode;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import brandapp.isport.com.basicres.ActivityManager;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonrecyclerview.Xlist.XListView;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class ActivityScaleHistory extends BaseMVPTitleActivity<ScaleHistoryView, ScaleHistoryPresenter> implements
        ScaleHistoryView, XListView.IXListViewListener {
    XListView xListview;
    ArrayList<ScaleHistoryBean> lists;
    ArrayList<HistoryBeanList> scaleHistoryBeanLists;
    HistoryAdapter mAdapter;

    int pageSize = 0;
    String currentMonth;
    long mNextTimeTamp = 0;

    boolean isRfrsh = false;
    private Scale_FourElectrode_DataModel mScale_fourElectrode_dataModel;
    private String mDeviceId;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_history;
    }

    @Override
    protected void initView(View view) {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        xListview = view.findViewById(R.id.recycler_result);
        lists = new ArrayList<>();
        xListview.setCacheColorHint(0);
        xListview.setPullLoadEnable(true);
        xListview.setPullRefreshEnable(true);
        xListview.setHeaderDividersEnabled(false);
        xListview.setDividerHeight(0);
        xListview.setFooterDividersEnabled(false);
    }

    @Override
    protected void initData() {
        mDeviceId = getIntent().getStringExtra("mDeviceId");
               /* ("mScale_fourElectrode_dataModel");
        if (mScale_fourElectrode_dataModel != null) {
            mDeviceId = mScale_fourElectrode_dataModel.getDeviceId();
        }*/
        if (TextUtils.isEmpty(mDeviceId)) {
            finish();
        }
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(getResources().getString(R.string.weight_record));
        titleBarView.setRightText("");

        lists = new ArrayList<>();

        pageSize = 0;
        //当前的月
        currentMonth = TimeUtils.getTimeByyyyyMMdd(Calendar.getInstance().getTimeInMillis());
        isRfrsh = true;
        mNextTimeTamp = 1;
        //第一次请求,单机版根据pageSize来单月查询
        if (App.appType() == App.httpType) {
            if (!checkNet())
                return;
        }
        mActPresenter.getFirstHistoryModel(mNextTimeTamp, RequestCode.Request_getScaleHistoryData, Calendar
                .getInstance().getTimeInMillis(), mDeviceId, currentMonth, "1", "", "", pageSize + "", "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.SCALE_HISTORYDATA:
                BaseResponse<ScaleHistroyList> deviceHomeData = (BaseResponse<ScaleHistroyList>) messageEvent.getObj();
                ScaleHistroyList data = deviceHomeData.getData();
                ArrayList<ScaleHistoryBean> scaleHistoryBeans = (ArrayList<ScaleHistoryBean>) ScaleHistoryPresenter
                        .parseData
                                (deviceHomeData);
                if (scaleHistoryBeans != null) {

                    Logger.myLog("Request_getScaleHistoryData 请求完成 == " + data
                            .mNextMonth);
                    if (pageSize == 0) {
                        successRefresh(scaleHistoryBeans, deviceHomeData.isIslastdata(), data
                                .mNextMonth, data.mNextTimeTamp);
                    } else {
                        successLoadMone(scaleHistoryBeans, deviceHomeData.isIslastdata(), data
                                .mNextMonth, data.mNextTimeTamp);
                    }
                }
                break;
            case MessageEvent.NEED_LOGIN:
                ToastUtils.showToast(context, UIUtils.getString(com.isport.brandapp.basicres.R.string.login_again));
                NetProgressObservable.getInstance().hide();
                BleProgressObservable.getInstance().hide();
                TokenUtil.getInstance().clear(context);
                AppSP.putBoolean(context, AppSP.CAN_RECONNECT, false);
                UserAcacheUtil.clearAll();
                App.initAppState();
                Intent intent = new Intent(context, ActivityLogin.class);
                context.startActivity(intent);
                ActivityManager.getInstance().finishAllActivity(ActivityLogin.class.getSimpleName());
                break;
            default:
                break;
        }
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
        xListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryBeanList beanList = mAdapter.getItem(position - 1);
                if (beanList.scaleDayBean != null) {
                    Fatsteelyard fatsteelyard = new Fatsteelyard();
//                    App.isHttp() ? Long.parseLong(beanList.scaleDayBean.fatSteelyardId) :
                    long mMFatsteelyard = beanList.scaleDayBean.creatTime;
                    Intent intent = new Intent(ActivityScaleHistory.this, ActivityScaleReport.class);
                    intent.putExtra("fatSteelyardId", mMFatsteelyard);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected ScaleHistoryPresenter createPresenter() {
        return new ScaleHistoryPresenter(this);
    }


    @Override
    public void successRefresh(ArrayList<ScaleHistoryBean> historyBean, boolean isLastData, String lastMonthStr, long
            nextTimeTamp) {
        if (scaleHistoryBeanLists == null) {
            scaleHistoryBeanLists = new ArrayList();
        }

        scaleHistoryBeanLists.clear();
        lists.clear();
        if (historyBean != null) {
            lists.addAll(historyBean);
        }

        HistoryBeanList title = new HistoryBeanList();
        title.tilteBean = new HistoryTilteBean();
        title.tilteBean.currentType = JkConfiguration.DeviceType.BODYFAT;
        title.tilteBean.one = R.string.time;
        title.tilteBean.two = R.string.weight_KG;
        title.tilteBean.three = R.string.bfr;
        title.DeviceTpye = JkConfiguration.DeviceType.BODYFAT;
        scaleHistoryBeanLists.add(title);//固定header

        //列表数据,首次进入获取最近两月的数据
        for (int i = 0; i < lists.size(); i++) {
            scaleHistoryBeanLists.addAll(DeviceDataUtil.parseScaleData(lists.get(i)));
           /* HistoryBeanList diviver = new HistoryBeanList();
            if (i < lists.size() - 1) {
                diviver.viewType = JkConfiguration.HistoryType.TYPE_DIVIDER;
                scaleHistoryBeanLists.add(diviver);
            }*/
        }

        if (mAdapter == null) {
            mAdapter = new HistoryAdapter(this, scaleHistoryBeanLists, R.layout.item_scale_history_detail_day, R
                    .layout.item_scale_history_head, R.layout.item_history_month);
            xListview.setAdapter(mAdapter);
            //recyclerDetail.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        pageSize = 0;
        successDataSetXlist(isLastData, lastMonthStr, nextTimeTamp);
        //adapterScaleHistory.setData(lists);
        //adapterScaleHistory.notifyDataSetChanged();
    }

    @Override
    public void successMainScale(ScaleHistroyNList historyBean) {

    }

    @Override
    public void successMothList(List<Long> list) {

    }

    public void stopXlist() {
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
    public void successLoadMone(ArrayList<ScaleHistoryBean> historyBean, boolean isLastData, String lastMonthStr,
                                long nextTimeTamp) {
        lists.clear();
        if (historyBean != null) {
            lists.addAll(historyBean);
        }
        //获取更多，以两个月为单位
        for (int i = 0; i < lists.size(); i++) {
            scaleHistoryBeanLists.addAll(DeviceDataUtil.parseScaleData(lists.get(i)));
        }
        if (historyBean != null) {
            lists.addAll(historyBean);
        }
        mAdapter.notifyDataSetChanged();
        successDataSetXlist(isLastData, lastMonthStr, nextTimeTamp);
    }

    public void successDataSetXlist(boolean isLastData, String lastMonthStr, long nextTimeTamp) {
        isRfrsh = false;
        pageSize = pageSize + 1;

        /**
         * 把当前的月份减掉1
         */


       /* Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.add(Calendar.MONTH, -pageSize);
        currentMonth = TimeUtils.getTimeByyyyyMMdd(currentCalendar);*/
        /*currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
        currentCalendar.set(Calendar.HOUR, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);*/

        if (!StringUtil.isBlank(lastMonthStr))
            currentMonth = lastMonthStr;
        mNextTimeTamp = nextTimeTamp;
        stopXlist();
        isShowLoad(isLastData);
    }

    Handler mHandler = new Handler();

    @Override
    public void onRefresh() {
        pageSize = 0;
        currentMonth = TimeUtils.getTimeByyyyyMM(Calendar.getInstance().getTimeInMillis());
        if (isRfrsh) {
            xListview.stopRefresh();
            return;
        }
        if (!checkNet() && App.appType() == App.httpType) {
            return;
        }
        mHandler.postDelayed(() -> {
            isRfrsh = true;
            mActPresenter.getFirstHistoryModel(1, RequestCode.Request_getScaleHistoryData, Calendar.getInstance()
                    .getTimeInMillis(), mDeviceId, currentMonth, "1", "", "", pageSize + "", "");
        }, 500);
    }

    @Override
    public void onLoadMore() {
        if (isRfrsh || mNextTimeTamp == 0) {
            xListview.stopLoadMore();
            return;
        }
        if (!checkNet() && App.appType() == App.httpType) {
            return;
        }
        mHandler.postDelayed(() -> {
            isRfrsh = true;
            mActPresenter.getScaleHistoryModel(mNextTimeTamp, RequestCode.Request_getScaleHistoryData, Calendar.getInstance()
                    .getTimeInMillis(), mDeviceId, currentMonth, "1", "", "", pageSize + "", "");

        }, 500);
    }

    @Override
    public void onRespondError(String message) {
        super.onRespondError(message);
        stopXlist();
        isRfrsh = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
