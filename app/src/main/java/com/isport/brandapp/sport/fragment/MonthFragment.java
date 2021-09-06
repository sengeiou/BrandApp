package com.isport.brandapp.sport.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.R;
import com.isport.brandapp.device.bracelet.FragmentList;
import com.isport.brandapp.login.ActivityWebView;
import com.isport.brandapp.sport.adapter.BaseRecyclerAdapter;
import com.isport.brandapp.sport.adapter.SmartViewHolder;
import com.isport.brandapp.sport.bean.MoreTypeBean;
import com.isport.brandapp.sport.bean.ResultHistorySportSummarizingData;
import com.isport.brandapp.sport.present.SportHistoryWeekOrMonthPresent;
import com.isport.brandapp.sport.view.SportHistoryWeekOrMonthView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import bike.gymproject.viewlibray.RopeBarChartView;
import bike.gymproject.viewlibray.chart.BarChartEntity;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BaseMVPFragment;
import brandapp.isport.com.basicres.service.observe.TodayObservable;

public class MonthFragment extends BaseMVPFragment<SportHistoryWeekOrMonthView, SportHistoryWeekOrMonthPresent> implements SportHistoryWeekOrMonthView, View.OnClickListener, AdapterView.OnItemClickListener {

    private SmartRefreshLayout mRefreshLayout;

    private long currenttimes;

    List<MoreTypeBean> items = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private View reView;
    private ClassicsHeader mClassicsHeader;
    private Drawable mDrawableProgress;
    private static boolean isFirstEnter = true;
    LinearLayout no_data, top_date;
    private String month;
    private TextView tv_update_time;
    private ImageView iv_data_left, iv_data_right;
    private boolean isLast = false;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currenttimes = getArguments().getInt("date");
        month = getArguments().getString("month");
        isLast = getArguments().getBoolean("isLast");
        currenttimes = currenttimes * 1000;

        //strDate = dateFormat.format(new Date(date * 1000l));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport_month_or_week_layout;
    }

    @Override
    protected void initView(View itemView) {
        mRefreshLayout = itemView.findViewById(R.id.smartRefreshLayout);
        reView = itemView.findViewById(R.id.recyclerView);
        rgTab = itemView.findViewById(R.id.tab);
        tvTime = itemView.findViewById(R.id.tv_time);
        tvCount = itemView.findViewById(R.id.tv_count);
        no_data = itemView.findViewById(R.id.rl_nodata);
        top_date = itemView.findViewById(R.id.layout_date);
        iv_data_left = itemView.findViewById(R.id.iv_data_left);
        iv_data_right = itemView.findViewById(R.id.iv_data_right);
        tv_update_time = itemView.findViewById(R.id.tv_update_time);
        top_date.setVisibility(View.VISIBLE);
        tv_update_time.setText(month);
        if (isLast) {
            iv_data_right.setVisibility(View.GONE);
        } else {
            iv_data_right.setVisibility(View.VISIBLE);
        }

    }

    private void showNOdata(boolean isShow) {
        no_data.setVisibility(isShow ? View.VISIBLE : View.GONE);
        //mRefreshLayout.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    BaseRecyclerAdapter mAdpater;
    RadioGroup rgTab;
    TextView tvTime;
    TextView tvCount;
    private ResultHistorySportSummarizingData summarizingData;


    @Override
    protected void initData() {

      /*  StringFomateUtil.formatText(StringFomateUtil.FormatType.Alone, context, tvTime, ContextCompat.getColor
                (context, R.color.white), R.string.sport_unite_distance, "0.00", "0.25");
        tvCount.setText(String.format(UIUtils.getString(R.string.total_sport_times), 0 + ""));*/


        mFragPresenter.getSportSummerData(true, currenttimes);

        Logger.myLog(" mFragPresenter.getSportSummerData：" + TimeUtils.getTimeByyyyyMMdd(currenttimes));

        rgTab.check(R.id.rb_month);
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

        int delta = new Random().nextInt(7 * 24 * 60 * 60 * 1000);
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis() - delta));
        mClassicsHeader.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        // mClassicsHeader.setTimeFormat(new DynamicTimeFormat("更新于 %s"));

//        mDrawableProgress = mClassicsHeader.getProgressView().getDrawable();
        mDrawableProgress = ((ImageView) mClassicsHeader.findViewById(ClassicsHeader.ID_IMAGE_PROGRESS)).getDrawable();
        if (mDrawableProgress instanceof LayerDrawable) {
            mDrawableProgress = ((LayerDrawable) mDrawableProgress).getDrawable(0);
        }


        if (reView instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) reView;
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView = recyclerView;

            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                }
            });
            mRefreshLayout.setEnableLoadMore(false);
            mRecyclerView.setEnabled(false);
            mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshlayout) {
                    refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                }
            });
            mRefreshLayout.setEnableRefresh(false);
        }

        if (isFirstEnter) {
            isFirstEnter = false;
            //触发自动刷新
            mRefreshLayout.autoRefresh();
        }

    }

    @Override
    protected void initEvent() {
        iv_data_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.DayAdd));
            }
        });
        iv_data_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.DaySub));
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (items.size() < position) {
            return;
        }
        if (items.get(position).type == BaseRecyclerAdapter.type_item) {
            Intent intent = new Intent(context, ActivityWebView.class);
            intent.putExtra("title", UIUtils.getString(R.string.sport_dtail));
            intent.putExtra("share_url", items.get(position).sportSumData.getShareUrl());
            intent.putExtra("url", items.get(position).sportSumData.getDataUrl());
            intent.putExtra("sumData", items.get(position).sportSumData);
            startActivity(intent);
        }

    }


    private void setWeekBarChartData(List<Float> stepList, List<String> date, RopeBarChartView chartView) {
        List<BarChartEntity> datas = new ArrayList<>();
        if (stepList == null) {
            for (int i = 0; i <= 30; i++) {
                datas.add(new BarChartEntity(String.valueOf(i), new Float[]{Float.valueOf(0), Float.valueOf(0), Float.valueOf(0)}));

            }
        } else {
            for (int i = 0; i < stepList.size(); i++) {
                if (i < stepList.size()) {
                    datas.add(new BarChartEntity(String.valueOf(i), new Float[]{stepList.get(i), 0f, 0f}));
                    // datas.add(new BarChartEntity(String.valueOf(i), new Float[]{55f, 0f, 0f}));
                } else {
                    datas.add(new BarChartEntity(String.valueOf(i), new Float[]{Float.valueOf(0), Float.valueOf(0), Float.valueOf(0)}));
                }

            }
        }

        int index = 0;
        int clickPostion = 0;
        while (index < stepList.size()) {
            if (stepList.get(index) > 0) {
                clickPostion = index;
            }
            //println("item at $index is ${items[index]}")
            index++;
        }
        chartView.setData(datas, new int[]{Color.parseColor("#6FC5F4")}, "分组", "数量");
        chartView.setWeekDateList(date);
        chartView.setSportMode(true);
        chartView.startAnimation();

        int finalClickPostion = clickPostion;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chartView.setmClickPosition(finalClickPostion);
            }
        }, 10);
    }


    Handler handler = new Handler();

    @Override
    public void successSummarData(Integer times, double dis, ArrayList<String> datalist, ArrayList<Float> disList) {
        tvTime.setText(CommonDateUtil.formatTwoPoint(dis));
       /* StringFomateUtil.formatText(StringFomateUtil.FormatType.Alone, context, tvTime, ContextCompat.getColor
                (context, R.color.white), R.string.sport_unite_distance, CommonDateUtil.formatTwoPoint(dis) + "", "0.25");*/
        tvCount.setText(String.format(UIUtils.getString(R.string.total_sport_times), times + ""));

        MoreTypeBean moreTypeBean = new MoreTypeBean(BaseRecyclerAdapter.type_chart);

        moreTypeBean.countList = new ArrayList<>();
        moreTypeBean.countList.addAll(disList);

        moreTypeBean.dateList = new ArrayList<>();
        moreTypeBean.dateList.addAll(datalist);

        items.add(0, moreTypeBean);
        mFragPresenter.getWeekOrMonthDetailData(true, currenttimes, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));


        //char的数据源
    }


    @Override
    public void successDetailData(ArrayList<MoreTypeBean> detaillists) {


        if (detaillists.size() == 0) {
            showNOdata(true);
            items.clear();
            return;
        } else {
            showNOdata(false);
        }

        items.addAll(detaillists);


        mRecyclerView.setAdapter(mAdpater = new BaseRecyclerAdapter(items, R.layout.item_sport_history_head_layout, R.layout.item_sport_history_chart_layout, R.layout.item_sport_tilte, R.layout.item_sport_history_layout, this) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, final MoreTypeBean model, int position) {
                // holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
                if (model.type == BaseRecyclerAdapter.type_head) {
                        /*TextView tv_course_time = (TextView) holder.itemView.findViewById(R.id.tv_course_state);
                        tv_course_time.setText("125452");*/
                    RadioGroup rgTab = holder.itemView.findViewById(R.id.tab);
                    ImageView ivBack = holder.itemView.findViewById(R.id.iv_back);
                    TextView tvSportType = holder.itemView.findViewById(R.id.tv_sport_type);
                    TextView tvTime = holder.itemView.findViewById(R.id.tv_time);
                    TextView tvCount = holder.itemView.findViewById(R.id.tv_count);

                    ivBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().finish();
                        }
                    });
                       /* tvSportType.setText("运动记录");
                        StringFomateUtil.formatText(StringFomateUtil.FormatType.Alone, context, tvTime, ContextCompat.getColor
                                (context, R.color.white), R.string.unit_minute_number, model.resultHistorySportSummarizingData.getAllTimeLong() + "", "0.25");
                        tvCount.setText(String.format(UIUtils.getString(R.string.total_sport_times), model.resultHistorySportSummarizingData.getAlltimes() + ""));*/

                    rgTab.check(R.id.rb_month);
                    rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                            switch (checkId) {
                                case R.id.rb_day:
                                    TodayObservable.getInstance().cheackType(com.isport.brandapp.device.bracelet.FragmentList.TYPE_DAY);
                                    break;
                                case R.id.rb_week:
                                    TodayObservable.getInstance().cheackType(com.isport.brandapp.device.bracelet.FragmentList.TYPE_WEEK);
                                    break;
                                case R.id.rb_month:
                                    TodayObservable.getInstance().cheackType(FragmentList.TYPE_MONTH);
                                    break;
                            }
                        }
                    });

                } else if (model.type == BaseRecyclerAdapter.type_item) {

                    ImageView ivSportType = holder.itemView.findViewById(R.id.iv_sport_type);
                    //TextView tvSportName = holder.itemView.findViewById(R.id.tv_sport_name);
                    TextView tvSportRecodeTime = holder.itemView.findViewById(R.id.tv_sport_recode_time);
                    TextView tvSportRecodeSpeed = holder.itemView.findViewById(R.id.tv_sport_recode_speed);
                    TextView tvSportRecodeCal = holder.itemView.findViewById(R.id.tv_sport_recode_cal);
                    TextView tvSportEndTime = holder.itemView.findViewById(R.id.tv_sport_end_time);
                    TextView tvSpoartDis = holder.itemView.findViewById(R.id.tv_sport_dis);

                    ivSportType.setImageResource(model.sportSumData.getDrawableRes());
                    // tvSportName.setText(model.sportSumData.getSportTypeName());
                    tvSportRecodeTime.setText(model.sportSumData.getStrTime());
                    tvSportRecodeSpeed.setText(model.sportSumData.getStrSpeed());
                    tvSportRecodeCal.setText(model.sportSumData.getCalories());
                    tvSportEndTime.setText(model.sportSumData.getStrEndTime());
                    tvSpoartDis.setText(model.sportSumData.getDistance());

                } else if (model.type == BaseRecyclerAdapter.type_month) {
                    TextView tv_month = holder.itemView.findViewById(R.id.tv_detail_value);
                    tv_month.setText(model.moth);
                } else if (model.type == BaseRecyclerAdapter.type_chart) {
                    RopeBarChartView barChartView = holder.itemView.findViewById(R.id.barChartView);
                    setWeekBarChartData(model.countList, model.dateList, barChartView);
                    //setWeekBarChartData(steps, dateList, barChartView);

                } else if (model.type == BaseRecyclerAdapter.type_no_data) {

                }
                // TODO: 2019/4/10 通过传入的类型来判断展示


            }
        });


    }

    @Override
    protected SportHistoryWeekOrMonthPresent createPersenter() {
        return new SportHistoryWeekOrMonthPresent(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Logger.myLog("mFragPresenter.getSportSummerData onDestroyView");
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true)
                .init();
    }
}
