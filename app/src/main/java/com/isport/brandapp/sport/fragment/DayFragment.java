package com.isport.brandapp.sport.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.isport.brandapp.R;
import com.isport.brandapp.device.bracelet.FragmentList;
import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.sport.adapter.BaseRecyclerAdapter;
import com.isport.brandapp.sport.adapter.SmartViewHolder;
import com.isport.brandapp.sport.bean.MoreTypeBean;
import com.isport.brandapp.sport.bean.ResultHistorySportSummarizingData;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.sport.present.SportHistoryPresent;
import com.isport.brandapp.sport.view.SportHistoryView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import bike.gymproject.viewlibray.WeekBarChartView;
import bike.gymproject.viewlibray.chart.BarChartEntity;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BaseMVPFragment;
import brandapp.isport.com.basicres.service.observe.TodayObservable;

public class DayFragment extends BaseMVPFragment<SportHistoryView, SportHistoryPresent> implements SportHistoryView, View.OnClickListener, AdapterView.OnItemClickListener {

    private SmartRefreshLayout mRefreshLayout;


    private RecyclerView mRecyclerView;
    private View reView;
    private ClassicsHeader mClassicsHeader;
    private Drawable mDrawableProgress;
    private static boolean isFirstEnter = true;
    private ResultHistorySportSummarizingData summarizingData;

    @Override
    public void onClick(View view) {

    }

    @Override
    protected SportHistoryPresent createPersenter() {
        return null;
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
        ivBack = itemView.findViewById(R.id.iv_back);
        tvSportType = itemView.findViewById(R.id.tv_sport_type);
        tvTime = itemView.findViewById(R.id.tv_time);
        tvCount = itemView.findViewById(R.id.tv_count);

    }

    BaseRecyclerAdapter mAdpater;
    RadioGroup rgTab;
    ImageView ivBack;
    TextView tvSportType;
    TextView tvTime;
    TextView tvCount;

    @Override
    protected void initData() {


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        tvSportType.setText(UIUtils.getString(R.string.sport_history_memory));
       /* summarizingData=new ResultHistorySportSummarizingData();
        summarizingData.setAllTimeLong(112);
        summarizingData.setAlltimes(5);*/
      /*  StringFomateUtil.formatText(StringFomateUtil.FormatType.Alone, context, tvTime, ContextCompat.getColor
                (context, R.color.white), R.string.unit_minute_number, summarizingData.getAllTimeLong() + "", "0.25");
        tvCount.setText(String.format(UIUtils.getString(R.string.total_sport_times), summarizingData.getAlltimes() + ""));*/

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
            List<MoreTypeBean> items = new ArrayList<>();
           /* MoreTypeBean moreTypeBean = new MoreTypeBean(BaseRecyclerAdapter.type_head);
            ResultHistorySportSummarizingData resultHistorySportSummarizingData = new ResultHistorySportSummarizingData();
            resultHistorySportSummarizingData.setAllTimeLong(112);
            resultHistorySportSummarizingData.setAlltimes(5);
            moreTypeBean.resultHistorySportSummarizingData = resultHistorySportSummarizingData;

            items.add(moreTypeBean);*/
            MoreTypeBean moreTypeBeanMonth = new MoreTypeBean(BaseRecyclerAdapter.type_month);
            moreTypeBeanMonth.moth = "5月";
            items.add(moreTypeBeanMonth);

            MoreTypeBean moreTypeBeanitem = new MoreTypeBean(BaseRecyclerAdapter.type_item);
            SportSumData sportSumData = new SportSumData();
            sportSumData.setEndTimestamp(System.currentTimeMillis());
            sportSumData.setDrawableRes(R.drawable.icon_sport);
            moreTypeBeanitem.sportSumData = sportSumData;
            items.add(moreTypeBeanitem);
            MoreTypeBean moreTypeBeanitem1 = new MoreTypeBean(BaseRecyclerAdapter.type_item);
            SportSumData sportSumData1 = new SportSumData();
            sportSumData1.setEndTimestamp(System.currentTimeMillis());
            sportSumData1.setDrawableRes(R.drawable.icon_sport);
            moreTypeBeanitem1.sportSumData = sportSumData;
            items.add(moreTypeBeanitem1);
            MoreTypeBean moreTypeBeanitem2 = new MoreTypeBean(BaseRecyclerAdapter.type_item);
            SportSumData sportSumData2 = new SportSumData();
            sportSumData2.setEndTimestamp(System.currentTimeMillis());
            sportSumData2.setDrawableRes(R.drawable.icon_sport);
            moreTypeBeanitem2.sportSumData = sportSumData;
            items.add(moreTypeBeanitem2);
            // mAdapter = new HistoryAdapter(this, lists, R.layout.item_sport_history_layout, R
            //                    .layout.item_scale_history_head, R.layout.item_history_month);
            recyclerView.setAdapter(mAdpater = new BaseRecyclerAdapter(items, R.layout.item_sport_history_head_layout, R.layout.item_sport_history_chart_layout, R.layout.item_history_month, R.layout.item_sport_history_layout,R.layout.include_nodata, this) {
                @Override
                protected void onBindViewHolder(SmartViewHolder holder, final MoreTypeBean model, int position) {
                    // holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
                    if (model.type == BaseRecyclerAdapter.type_head) {
                        /*TextView tv_course_time = (TextView) holder.itemView.findViewById(R.id.tv_course_state);
                        tv_course_time.setText("125452");*/


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

                        WeekBarChartView weekBarChartView = holder.itemView.findViewById(R.id.barChartView);
                        ArrayList<String> dateList = new ArrayList<>();
                        dateList.add("5/16");
                        dateList.add("5/16");
                        dateList.add("5/16");
                        dateList.add("5/16");
                        dateList.add("5/16");
                        dateList.add("5/16");
                        dateList.add("5/16");
                        ArrayList<Integer> steps = new ArrayList<>();
                        steps.add(15);
                        steps.add(15);
                        steps.add(15);
                        steps.add(15);
                        steps.add(15);
                        steps.add(15);
                        steps.add(15);
                        setWeekBarChartData(steps, dateList, weekBarChartView);

                    }
                    // TODO: 2019/4/10 通过传入的类型来判断展示


                }
            });
            mRecyclerView = recyclerView;

            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                }
            });
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

    }

    @Override
    public void successLoadSummarData(ResultHistorySportSummarizingData resultHistorySportSummarizingData) {

    }

    @Override
    public void successFisrtHistory(ArrayList<HistoryBeanList> scaleHistoryBeanLists) {

    }

    @Override
    public void successLoadMoreHistory(ArrayList<HistoryBeanList> scaleHistoryBeanLists) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    private void setWeekBarChartData(ArrayList<Integer> stepList, ArrayList<String> date, WeekBarChartView chartView) {
        List<BarChartEntity> datas = new ArrayList<>();
        if (stepList == null) {
            for (int i = 0; i <= 7; i++) {
                datas.add(new BarChartEntity(String.valueOf(i), new Float[]{Float.valueOf(0), Float.valueOf(0), Float.valueOf(0)}));

            }
        } else {
            for (int i = 0; i < stepList.size(); i++) {
                if (i < stepList.size()) {
                    datas.add(new BarChartEntity(String.valueOf(i), new Float[]{Float.valueOf(stepList.get(i)), 0f, 0f}));
                } else {
                    datas.add(new BarChartEntity(String.valueOf(i), new Float[]{Float.valueOf(0), Float.valueOf(0), Float.valueOf(0)}));
                }

            }
        }
       /* chartView.setOnItemBarClickListener(new BarChartView.OnItemBarClickListener() {
            @Override
            public void onClick(int position) {
                Log.e("TAG", "点击了：" + position);
            }
        });*/
        chartView.setData(datas, new int[]{Color.parseColor("#6FC5F4")}, "分组", "数量");
        chartView.setWeekDateList(date);
        chartView.startAnimation();
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .init();
    }
}
