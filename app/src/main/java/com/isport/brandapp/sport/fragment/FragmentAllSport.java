package com.isport.brandapp.sport.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.device.bracelet.FragmentList;
import com.isport.brandapp.device.history.adapter.HistoryAdapter;
import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.login.ActivityWebView;
import com.isport.brandapp.sport.bean.ResultHistorySportSummarizingData;
import com.isport.brandapp.sport.present.SportHistoryPresent;
import com.isport.brandapp.sport.view.SportHistoryView;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonrecyclerview.Xlist.XListView;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BaseMVPFragment;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import brandapp.isport.com.basicres.service.observe.TodayObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

/**
 * 地图运动记录页面
 */
public class FragmentAllSport extends BaseMVPFragment<SportHistoryView, SportHistoryPresent> implements SportHistoryView, XListView.IXListViewListener, View.OnClickListener {

    int offset = 0;

    TextView tvSumTime, tvSumCont;
    XListView xListview;
    ArrayList<HistoryBeanList> lists;
    HistoryAdapter mAdapter;
    boolean isRfrsh = false;
    Handler mHandler = new Handler();
    String userId;
    RadioGroup rgTab;

    LinearLayout no_data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sport_history_layout;
    }

    @Override
    protected void initView(View view) {

        // View view1 = view.findViewById(R.id.view_head);
        //对于打孔屏设置的marginTop更低
        LinearLayout.LayoutParams ivSsettingLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DisplayUtils.dip2px(context, 40));
       /* if (App.isPerforatedPanel()) {
            view1.setLayoutParams(ivSsettingLP);
        }*/

        tvSumTime = view.findViewById(R.id.tv_time);
        tvSumCont = view.findViewById(R.id.tv_count);

        xListview = view.findViewById(R.id.recycler_result);
        xListview.setPullLoadEnable(false);
        rgTab = view.findViewById(R.id.tab);
        no_data = view.findViewById(R.id.rl_nodata);

        rgTab.check(R.id.rb_day);
        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId) {
                    case R.id.rb_day:
                        TodayObservable.getInstance().cheackType(FragmentList.TYPE_DAY);
                        break;
                    case R.id.rb_week:
                        TodayObservable.getInstance().cheackType(FragmentList.TYPE_WEEK);
                        break;
                    case R.id.rb_month:
                        TodayObservable.getInstance().cheackType(FragmentList.TYPE_MONTH);
                        break;
                }
            }
        });

    }

    @Override
    protected void initData() {
        offset = 0;
        lists = new ArrayList<>();
        showNOdata(true);
        tvSumTime.setText(CommonDateUtil.formatTwoPoint(0));
      /*  StringFomateUtil.formatText(StringFomateUtil.FormatType.Alone, context, tvSumTime, ContextCompat.getColor
                (context, R.color.white), R.string.sport_unite_distance, CommonDateUtil.formatTwoPoint(0) + "", "0.25");*/
        tvSumCont.setText(String.format(UIUtils.getString(R.string.total_sport_times), CommonDateUtil.formatInterger(0)));
        userId = TokenUtil.getInstance().getPeopleIdStr(getActivity());
        xListview.setCacheColorHint(0);
        xListview.setPullLoadEnable(false);
        xListview.setPullRefreshEnable(false);
        xListview.setHeaderDividersEnabled(false);
        xListview.setDividerHeight(0);
        xListview.setFooterDividersEnabled(false);

        mFragPresenter.getSportSummerData();
        mFragPresenter.getFisrtSportHistory(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), offset + "");

      /*  StringFomateUtil.formatText(StringFomateUtil.FormatType.Alone, context, tvSumTime, ContextCompat.getColor
                (context, R.color.white), R.string.sport_unite_distance, "0.00", "0.25");
        tvSumCont.setText(String.format(UIUtils.getString(R.string.total_sport_times), 0 + ""));*/

    }

    @Override
    protected void initEvent() {
        xListview.setXListViewListener(this);
        xListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lists.size() < position) {
                    return;
                }
                if (lists.get(position - 1).viewType == JkConfiguration.HistoryType.TYPE_CONTENT) {
                    Intent intent = new Intent(context, ActivityWebView.class);
                    intent.putExtra("title", R.string.sport_dtail);//UIUtils.getString(R.string.sport_dtail)
                    intent.putExtra("share_url", lists.get(position - 1).sportDetailData.getShareUrl());
                    intent.putExtra("url", lists.get(position - 1).sportDetailData.getDataUrl());
                    intent.putExtra("sumData", lists.get(position - 1).sportDetailData);
                    startActivity(intent);
                }

            }
        });
    }


    @Override
    public void successLoadSummarData(ResultHistorySportSummarizingData resultHistorySportSummarizingData) {

        tvSumTime.setText(CommonDateUtil.formatTwoPoint(resultHistorySportSummarizingData.getAllDistance()));
        /*StringFomateUtil.formatText(StringFomateUtil.FormatType.Alone, context, tvSumTime, ContextCompat.getColor
                (context, R.color.white), R.string.sport_unite_distance, CommonDateUtil.formatTwoPoint(resultHistorySportSummarizingData.getAllDistance()) + "", "0.25");*/
        tvSumCont.setText(String.format(UIUtils.getString(R.string.total_sport_times), CommonDateUtil.formatInterger(resultHistorySportSummarizingData.getTimes())));
    }

    @Override
    public void successFisrtHistory(ArrayList<HistoryBeanList> scaleHistoryBeanLists) {

        if (scaleHistoryBeanLists != null) {
            lists.clear();
            lists.addAll(scaleHistoryBeanLists);
            if (lists.size() < 10) {
                xListview.setPullLoadEnable(false);
            } else {
                xListview.setPullLoadEnable(true);
            }
            if (lists.size() == 0) {
                showNOdata(true);
            } else {
                showNOdata(false);
            }
        } else {
            showNOdata(true);
        }

        if (mAdapter == null) {
            mAdapter = new HistoryAdapter(getActivity(), lists, R.layout.item_sport_history_layout, R
                    .layout.item_scale_history_head, R.layout.item_sport_tilte);
            xListview.setAdapter(mAdapter);
            //recyclerDetail.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        offset = offset + 1;
        stopXlist();

    }


    private void showNOdata(boolean isShow) {
        no_data.setVisibility(isShow ? View.VISIBLE : View.GONE);
        xListview.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void successLoadMoreHistory(ArrayList<HistoryBeanList> scaleHistoryBeanLists) {


        if (scaleHistoryBeanLists != null && scaleHistoryBeanLists.size() > 0) {
            lists.addAll(scaleHistoryBeanLists);
            mAdapter.notifyDataSetChanged();
            offset = offset + 1;
            stopXlist();
        } else {
            stopXlist();
            xListview.setPullLoadEnable(false);
        }


    }

    @Override
    public void onRefresh() {
      /*  offset = 0;
        if (isRfrsh) {
            //xListview.stopRefresh();
            return;
        }
        mHandler.postDelayed(() -> {
            isRfrsh = true;
           // mFragPresenter.getFisrtSportHistory(userId, offset + "");
        }, 500);
*/
    }

    @Override
    public void onLoadMore() {
        if (isRfrsh) {
            //  xListview.stopLoadMore();
            return;
        }
        mHandler.postDelayed(() -> {
            isRfrsh = true;
            mFragPresenter.getLoadMoreHistory(userId, offset + "");

        }, 500);
    }

    public void stopXlist() {
        xListview.stopLoadMore();
        xListview.stopRefresh();
        isRfrsh = false;
    }

    public void isShowLoad(boolean islastdata) {
        if (islastdata) {
            xListview.setPullLoadEnable(false);
        } else {
            xListview.setPullLoadEnable(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
        }
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
                Intent intent = new Intent(context, ActivityLogin.class);
                context.startActivity(intent);
                ActivityManager.getInstance().finishAllActivity(ActivityLogin.class.getSimpleName());
                break;
            default:
                break;
        }
    }

    @Override
    protected SportHistoryPresent createPersenter() {
        return new SportHistoryPresent(this);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true)
                .init();
    }
}
