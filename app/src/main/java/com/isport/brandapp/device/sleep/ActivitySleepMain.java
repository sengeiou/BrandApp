package com.isport.brandapp.device.sleep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.isport.blelibrary.db.action.sleep.Sleep_Sleepace_DataModelAction;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_DataModel;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.App;
import com.isport.brandapp.Home.bean.http.SleepBel;
import com.isport.brandapp.Home.view.circlebar.SleepCircleLayout;
import com.isport.brandapp.R;
import com.isport.brandapp.device.history.util.HistoryParmUtil;
import com.isport.brandapp.device.share.ShareActivity;
import com.isport.brandapp.device.share.ShareBean;
import com.isport.brandapp.device.sleep.bean.SleepHistoryNBean;
import com.isport.brandapp.device.sleep.bean.SleepHistoryNList;
import com.isport.brandapp.device.sleep.bean.SleepUpdateBean;
import com.isport.brandapp.device.sleep.bean.SleepUpdateData;
import com.isport.brandapp.device.sleep.calendar.Cell;
import com.isport.brandapp.device.sleep.calendar.PopCalendarView;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.parm.http.SleepHistoryParms;
import com.isport.brandapp.repository.CustomRepository;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceDataUtil;
import com.isport.brandapp.util.StringFomateUtil;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bike.gymproject.viewlibray.SleepItemView;
import bike.gymproject.viewlibray.chart.HeartChar;
import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class ActivitySleepMain extends BaseTitleActivity {
    SleepItemView itemAvgBmp, itemAvgBreath, itemInfant;
    TextView tvTime;
    TextView tv_sleep_time;
    TextView tv_sleep_item;
    //TextView tvSleepAnalyze;
    String strDeepSleepTime;
    float precent;
    private SleepBel mSleepBel;
    private SleepUpdateBean mSleepUpdateBean;

    SleepCircleLayout layout;
    String strAvgBmp, strAvgBreath, strSleepTime, strInfant, strSleepHour, strSleepMin, strSleepRate;

    HeartChar heartChart, breathChart, infantChart;
    private String time;
    private int deepTime;
    private int sleepTime;
    private String fallAsleepTime;
    private Sleep_Sleepace_DataModel mSleep_Sleepace_DataModel;
    private boolean isFirst;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_sleep_main;
    }

    @Override
    protected void initView(View view) {
        itemAvgBmp = view.findViewById(R.id.avg_bmp);
        itemAvgBreath = view.findViewById(R.id.avg_breath);
        heartChart = view.findViewById(R.id.heart_chart);
        breathChart = view.findViewById(R.id.breath_chart);
        infantChart = view.findViewById(R.id.infant_chart);
        //itemSleepTime = view.findViewById(R.id.sleep_time);
        //tvSleepAnalyze = view.findViewById(R.id.tv_sleep_analyze);
        itemInfant = view.findViewById(R.id.infant);
        tvTime = view.findViewById(R.id.tv_update_time);
        layout = view.findViewById(R.id.layout_data_head);
        heartChart = view.findViewById(R.id.heart_chart);
        breathChart = view.findViewById(R.id.breath_chart);
        infantChart = view.findViewById(R.id.infant_chart);
        tv_sleep_time = view.findViewById(R.id.tv_sleep_time);
        tv_sleep_item = view.findViewById(R.id.tv_sleep_item);


    }

    @SuppressLint("StringFormatMatches")
    @Override
    protected void initData() {
        mSleepBel = (SleepBel) getIntent().getSerializableExtra("mSleepBel");
        mSleep_Sleepace_DataModel = (Sleep_Sleepace_DataModel) getIntent().getSerializableExtra
                ("mSleep_Sleepace_DataModel");
        if (mSleepBel != null) {
            setItemValue();
        }
        if (mSleep_Sleepace_DataModel != null) {
            setItemBySleepaceDataModel();
        }
        mSleepUpdateBean = (SleepUpdateBean) getIntent().getSerializableExtra("sleepUpdateBean");
        long lastTime = getIntent().getLongExtra("lastTime", 0);
        if (mSleepUpdateBean != null) {
            setItemValueBySleepUpdateBean(lastTime);
        }
        titleBarView.setLeftIconEnable(true);
        // titleBarView.setTitle(getResources().getString(R.string.last_sleep));
        // titleBarView.setTitle(mSleepUpdateBean.getTime());
        titleBarView.setRightText("");
        titleBarView.setRightIcon(R.drawable.icon_device_share);
        titleBarView.setHistrotyIcon(R.drawable.icon_sleep_history);
        // createDayPop();


      /*  List<Sleep_Sleepace_DataModel> all = Sleep_Sleepace_DataModelAction
                .getAll();*/
    }

    private void setDefultSleepaceDataModel(String str) {
        titleBarView.setTitle(str);
        tv_sleep_item.setText(getString(R.string.deep_sleep_percent, "0"));
        StringFomateUtil.formatText(StringFomateUtil.FormatType.Time, this, tv_sleep_time, ContextCompat.getColor
                (this, R.color.common_view_color), R.string.app_time_util, "0", "0");
        // tvTime.setText(mSleep_Sleepace_DataModel.getDateStr());
        itemAvgBmp.setValueText("0");
        itemAvgBreath.setValueText("0");
        // itemSleepTime.setValueText(mSleepBel.getFallAlseepAllTime());
        itemInfant.setValueText("0");
        ArrayList<Integer> lineHeartRate = new ArrayList<>();
        ArrayList<Integer> lineBreathRate = new ArrayList<>();
        ArrayList<Integer> lineTurnOverStatus = new ArrayList<>();
        com.isport.blelibrary.utils.Logger.myLog("");
        heartChart.setPointInfo(lineHeartRate);
        breathChart.setPointInfo(lineBreathRate);
        infantChart.setPointInfo(lineTurnOverStatus);
        heartChart.postInvalidate();
        breathChart.postInvalidate();
        infantChart.postInvalidate();
        /**
         * 设置view
         */
        layout.setData(0, 0, false);
    }

    private void setItemBySleepaceDataModel() {
        fallAsleepTime = mSleep_Sleepace_DataModel.getFallAlseepAllTime() + "";
        deepTime = mSleep_Sleepace_DataModel.getDeepSleepAllTime();
        sleepTime = mSleep_Sleepace_DataModel.getSleepDuration();
        String pre = "0";
        if (deepTime != 0) {
            pre = CommonDateUtil.formatOnePoint(1.0 * deepTime / sleepTime * 100);
        }
        tv_sleep_item.setText(getString(R.string.deep_sleep_percent, pre));
        String hour = CommonDateUtil.formatTwoStr(deepTime / 60);
        String minute = CommonDateUtil.formatTwoStr(deepTime % 60);
        StringFomateUtil.formatText(StringFomateUtil.FormatType.Time, this, tv_sleep_time, ContextCompat.getColor
                (this, R.color.common_view_color), R.string.app_time_util, hour, minute);

        //tvTime.setText(DateUtils.getAMTime(mSleep_Sleepace_DataModel.getTimestamp()));

        // titleBarView.setTitle(mSleep_Sleepace_DataModel.getDateStr());
        titleBarView.setTitle(mSleep_Sleepace_DataModel.getDateStr());
        // tvTime.setText(mSleep_Sleepace_DataModel.getDateStr());
        itemAvgBmp.setValueText(mSleep_Sleepace_DataModel.getAverageHeartBeatRate() + "");
        itemAvgBreath.setValueText(mSleep_Sleepace_DataModel.getAverageBreathRate() + "");
        // itemSleepTime.setValueText(mSleepBel.getFallAlseepAllTime());
        itemInfant.setValueText(mSleep_Sleepace_DataModel.getTurnOverTimes() + "");
        String heartRateAry = mSleep_Sleepace_DataModel.getHeartRateAry();
        String breathRateAry = mSleep_Sleepace_DataModel.getBreathRateAry();
        String turnOverStatusAry = mSleep_Sleepace_DataModel.getTrunOverStatusAry();
        int[] intHeartRateAry = new Gson().fromJson(heartRateAry, int[].class);
        int[] intBreathRateAry = new Gson().fromJson(breathRateAry, int[].class);
        int[] intTurnOverStatusAry = new Gson().fromJson(turnOverStatusAry, int[].class);
        ArrayList<Integer> lineHeartRate = new ArrayList<>();
        ArrayList<Integer> lineBreathRate = new ArrayList<>();
        ArrayList<Integer> lineTurnOverStatus = new ArrayList<>();
        com.isport.blelibrary.utils.Logger.myLog("");
        for (int i = 0; i < intHeartRateAry.length; i++) {
            lineHeartRate.add(intHeartRateAry[i]);
        }
        for (int i = 0; i < intBreathRateAry.length; i++) {
            lineBreathRate.add(intBreathRateAry[i]);
        }
        for (int i = 0; i < intTurnOverStatusAry.length; i++) {
            lineTurnOverStatus.add(intTurnOverStatusAry[i]);
        }
        heartChart.setPointInfo(lineHeartRate);
        breathChart.setPointInfo(lineBreathRate);
        infantChart.setPointInfo(lineTurnOverStatus);
        heartChart.postInvalidate();
        breathChart.postInvalidate();
        infantChart.postInvalidate();
        /**
         * 设置view
         */
        if (mSleep_Sleepace_DataModel.getSleepDuration() >= 12 * 60) {
            layout.setData(mSleep_Sleepace_DataModel.getDeepSleepAllTime(), mSleep_Sleepace_DataModel
                    .getSleepDuration(), false);
        } else {
            layout.setData(mSleep_Sleepace_DataModel.getDeepSleepAllTime(), mSleep_Sleepace_DataModel
                    .getSleepDuration(), true);
        }
    }


    private void setItemValue() {
        //tvTime.setText(DateUtils.getAMTime(mSleepBel.getCreatTime()));
        titleBarView.setTitle(DateUtils.getYMD(mSleepBel.getCreatTime()));
        itemAvgBmp.setValueText(mSleepBel.getAverageHeartBeatRate());
        itemAvgBreath.setValueText(mSleepBel.getAverageBreathRate());
        // itemSleepTime.setValueText(mSleepBel.getFallAlseepAllTime());
        itemInfant.setValueText(mSleepBel.getTrunOverTimes());

        /**
         * 设置view
         */

        if (Integer.parseInt(mSleepBel.getDuration()) >= 12 * 60) {
            layout.setData(Integer.parseInt(mSleepBel.getDeepSleepAllTime()), Integer.parseInt(mSleepBel.getDuration
                    ()), false);
        } else {
            layout.setData(Integer.parseInt(mSleepBel.getDeepSleepAllTime()), Integer.parseInt(mSleepBel.getDuration
                    ()), true);
        }

        int deepSleepAllTime = Integer.parseInt(mSleepBel.getDeepSleepAllTime());
        time = DateUtils.getAMTime(mSleepBel.getCreatTime());
        deepTime = deepSleepAllTime;
        sleepTime = Integer.parseInt(mSleepBel.getDuration());
        fallAsleepTime = mSleepBel.getFallAlseepAllTime();
        String hour = CommonDateUtil.formatTwoStr(deepSleepAllTime / 60);
        String minute = CommonDateUtil.formatTwoStr(deepSleepAllTime % 60);
        /**
         * 睡眠带上取的数据取的短数据百分比有误
         */
        String pre = "0";
        if (deepTime != 0) {
            pre = CommonDateUtil.formatOnePoint(1.0 * deepTime / sleepTime * 100);
        }
        // StringFomateUtil.formatText(StringFomateUtil.FormatType.SleepTime, context, tvSleepAnalyze, UIUtils
        // .getColor(R.color.common_white), R.string.app_sleep_time_per, hour, minute, pre);
        //tvSleepAnalyze.setText(String.format(UIUtils.getString(R.string.app_sleep_time_per), hour + "", minute +
        // "",mSleepBel.getDeepSleepPerc()));

    }

    private void setItemValueBySleepUpdateBean(long lastTime) {
        List<SleepUpdateData> data1 = mSleepUpdateBean.getData();
        SleepUpdateData data = data1.get(data1.size() - 1);
        time = DateUtils.getAMTime(lastTime);
        deepTime = Integer.parseInt(data.getDeepSleepAllTime());
        sleepTime = Integer.parseInt(data.getDuration());
        fallAsleepTime = data.getFallAlseepAllTime();
        titleBarView.setTitle(DateUtils.getYMD(lastTime));
        //tvTime.setText(DateUtils.getAMTime(lastTime));
        itemAvgBmp.setValueText(data.getAverageHeartBeatRate());
        itemAvgBreath.setValueText(data.getAverageBreathRate());
        //itemSleepTime.setValueText(data.getFallAlseepAllTime());
        itemInfant.setValueText(data.getTrunOverTimes());
        layout.setData(Integer.parseInt(data.getDeepSleepAllTime()), Integer.parseInt(data.getDuration()), false);
        int deepSleepAllTime = Integer.parseInt(data.getDeepSleepAllTime());
        String hour = CommonDateUtil.formatTwoStr(deepSleepAllTime / 60);
        String minute = CommonDateUtil.formatTwoStr(deepSleepAllTime % 60);
      /*  tvSleepAnalyze.setText(String.format(UIUtils.getString(R.string.app_sleep_time_per), hour + "", minute + "",
                                             data.getDeepSleepPerc())+"%");*/
        //  StringFomateUtil.formatText(StringFomateUtil.FormatType.SleepTime, context, tvSleepAnalyze, UIUtils
        // .getColor(R.color.common_white), R.string.app_sleep_time_per, hour, minute, data.getDeepSleepPerc());
    }

    @Override
    protected void initEvent() {
        isFirst = true;
        titleBarView.setOnHistoryClickListener(new TitleBarView.OnHistoryClickListener() {
            @Override
            public void onHistoryClicked(View view) {

                // createDayPop();
                setPopupWindow(ActivitySleepMain.this, view);

                int time = (int) (System.currentTimeMillis() / 1000);
       /* if (dayAnalysis != null) {
            time = dayAnalysis.getStartTime() - SleepConfig.SLEEP_DIFF * 3600;
        }*/


                initDatePopMonthTitle();
                calendarview.setActiveC(TimeUtil.second2Millis(time));
                calendarview.setTimeInMillis(TimeUtil.second2Millis(time));

                //createDayPop();

                /*int[] outLocation = new int[2];
                view.getLocationOnScreen(outLocation);
                int y;
                if (isFirst) {
                    isFirst = false;
                    y = outLocation[1] + view.getHeight() + WindowManagerUtils.getNavigationBarHeight(context);
                } else {
                    y = outLocation[1] + view.getHeight() + (Build.VERSION.SDK_INT >= Build.VERSION_CODES
                            .LOLLIPOP_MR1 ? 0 : WindowManagerUtils.getNavigationBarHeight(context));
                }
                showAsDropDown(datePop, view, 0, WindowManagerUtils.getNavigationBarHeight(context));*/
//                datePop.showAtLocation(view, Gravity.BOTTOM, 0, y);

              /*  Intent intent = new Intent(ActivitySleepMain.this, ActivitySleepHistory.class);
                startActivity(intent);*/
            }
        });
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();

            }

            @Override
            public void onRightClicked(View view) {

                Intent intent = new Intent(context, ShareActivity.class);
                //  currentShareDeviceType = getIntent().getIntExtra(JkConfiguration.FROM_TYPE, JkConfiguration
                // .DeviceType.WATCH_W516);
                //        shareBean = getIntent().getParcelableExtra(JkConfiguration.FROM_BEAN);
                intent.putExtra(JkConfiguration.FROM_TYPE, JkConfiguration.DeviceType.SLEEP);
                ShareBean shareBean = new ShareBean();
                //睡眠时长

                String sleepTimehour = CommonDateUtil.formatTwoStr(sleepTime / 60);
                String sleepTimeminute = CommonDateUtil.formatTwoStr(sleepTime % 60);
                String deepTimehour = CommonDateUtil.formatTwoStr(deepTime / 60);
                String deepTimeminute = CommonDateUtil.formatTwoStr(deepTime % 60);

                //new
                shareBean.one=""+deepTime;
                shareBean.two=""+fallAsleepTime;
                shareBean.three=""+0;
                shareBean.centerValue=""+sleepTime;

//                shareBean.one = String.format(context.getString(R.string.app_sleep_time_hour_min), sleepTimehour,
//                        sleepTimeminute);
//                //深睡时长
//                shareBean.two = String.format(context.getString(R.string.app_sleep_time_hour_min), deepTimehour,
//                        deepTimeminute);
//                //入睡时长
//                shareBean.three = String.format(context.getString(R.string.app_sleep_time_min), fallAsleepTime + "");
//                //睡眠时长
//                shareBean.centerValue = String.format(context.getString(R.string.app_sleep_time_hour_min),
//                        sleepTimehour, sleepTimeminute);
                //日期
                shareBean.time = DateUtils.getYMD(mSleep_Sleepace_DataModel.getTimestamp() * 1000);
                intent.putExtra(JkConfiguration.FROM_BEAN, shareBean);

                startActivity(intent);
                // ToastUtil.showTextToast(context, "点击了分享页面");
            }
        });
    }

    @Override
    protected void initHeader() {

    }


    private PopCalendarView calendarview;
    private ImageView ivPreDate, ivNextDate;
    private TextView tvDatePopTitle, tvBackToay;


    View mMenuViewBirth;
    private PopupWindow popupWindowBirth;

    public void setPopupWindow(Context context, View view) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuViewBirth = inflater.inflate(R.layout.app_activity_chart_sleepace_dem, null);
        calendarview = (PopCalendarView) mMenuViewBirth.findViewById(R.id.calendar);
        ivPreDate = (ImageView) mMenuViewBirth.findViewById(R.id.iv_pre);
        ivNextDate = (ImageView) mMenuViewBirth.findViewById(R.id.iv_next);
        tvDatePopTitle = (TextView) mMenuViewBirth.findViewById(R.id.tv_date);
        tvBackToay = (TextView) mMenuViewBirth.findViewById(R.id.tv_back_today);
        popupWindowBirth = new PopupWindow(context);
        popupWindowBirth.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindowBirth.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //popupWindowBirth.setHeight(DisplayUtils.dip2px(context, 150));
        popupWindowBirth.setContentView(mMenuViewBirth);
        popupWindowBirth.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindowBirth.setOutsideTouchable(false);
        popupWindowBirth.setFocusable(true);
        popupWindowBirth.setAnimationStyle(R.style.popwin_anim_style);
        popupWindowBirth.showAtLocation(view, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
      /*  mMenuViewBirth.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuViewBirth.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        popupWindowBirth.dismiss();
                    }
                }
                return true;
            }
        });*/

        calendarview.setOnCellTouchListener(new PopCalendarView.OnCellTouchListener() {
            @Override
            public void onTouch(Cell cell) {
                /*if (cell.getStartTime() <= 0) {
                    Toast.makeText(BaseApp.getApp(),"没有数据", Toast.LENGTH_SHORT).show();
                    return;
                }
               */
                int stime = cell.getStartTime();
                String dateStr = cell.getDateStr();
                Date date = TimeUtils.stringToDate(dateStr);
                if (!TextUtils.isEmpty(dateStr)) {
                    return;
                }
                Date mCurrentDate = TimeUtils.getCurrentDate();
                //未来的日期提示用户不可选
                if (!date.before(mCurrentDate)) {
                    ToastUtils.showToast(UIUtils.getContext(), UIUtils.getString(R.string.select_date_error));
                    return;
                }
                popupWindowBirth.dismiss();

                Sleep_Sleepace_DataModel sleep_sleepace_dataModelByDeviceIdAndDateStr =
                        Sleep_Sleepace_DataModelAction.findSleep_Sleepace_DataModelByDeviceIdAndDateStr
                                (TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()),
                                        dateStr);
                if (sleep_sleepace_dataModelByDeviceIdAndDateStr != null) {
                    mSleep_Sleepace_DataModel = sleep_sleepace_dataModelByDeviceIdAndDateStr;
                    setItemBySleepaceDataModel();
                    Logger.myLog("sleep_sleepace_dataModelByDeviceIdAndDateStr == " +
                            sleep_sleepace_dataModelByDeviceIdAndDateStr.toString());
                } else {
                    setDefultSleepaceDataModel(cell.getDateStr());
                }
                Logger.myLog("dateStr == " + dateStr);
                /*dayAnalysis = analysisDao.getAnalysis(device.getSn(), stime);
                LogUtil.log(TAG+" touch cell analysis:" + dayAnalysis);
                initDayReport();*/
            }
        });


        tvBackToay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarview.goCurrentMonth();
                getLastMonthData();
                //向前移动一个月
                initDatePopMonthTitle();
            }
        });

        ivPreDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                calendarview.previousMonth();
                getLastMonthData();
                initDatePopMonthTitle();
            }
        });

        ivNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                calendarview.nextMonth();
                getLastMonthData();
                initDatePopMonthTitle();
            }
        });


        //获取当月月初0点时间戳,毫秒值
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.DAY_OF_MONTH, 1);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        //向前移动一个月
        instance.add(Calendar.MONTH, -1);
        //首次进入获取上一个月的数据,
        getMonthData(instance);
    }


    private void getLastMonthData() {
        Calendar calendar = calendarview.getDate();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        //向前移动一个月
        calendar.add(Calendar.MONTH, -1);
        getMonthData(calendar);
        calendar.add(Calendar.MONTH, 1);
    }

    private void getMonthData(Calendar instance) {
        if (!(App.appType() == App.httpType)) {
            return;
        }
        /**
         * 获取睡眠带，当月的数据，同步到本地
         */
        CustomRepository<SleepHistoryNList, SleepHistoryParms, BaseUrl, BaseDbPar> sleepHistory = new
                CustomRepository<>();
        PostBody<SleepHistoryParms, BaseUrl, BaseDbPar> sleepHistoryDataList = HistoryParmUtil
                .getSleepHistoryByTimeTampData(instance.getTimeInMillis());
        sleepHistory.requst(sleepHistoryDataList)
                .as(this.bindAutoDispose()).subscribe(new BaseObserver<SleepHistoryNList>(context) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(SleepHistoryNList mSleepHistoryData) {
                NetProgressObservable.getInstance().hide();
                Logger.myLog("获取主页SleepHistoryNList成功 == " + mSleepHistoryData.toString());
                if (mSleepHistoryData.getList() == null || (mSleepHistoryData.getList() != null && mSleepHistoryData
                        .getList().size() == 0)) {
                    //无数据的情况
                } else {
                    //有数据的情况
                    List<SleepHistoryNBean> list = mSleepHistoryData.getList();
                    for (int i = 0; i < list.size(); i++) {
                        SleepHistoryNBean sleepHistoryNBean = list.get(i);
                        Sleep_Sleepace_DataModel sleep_sleepace_dataModel1 = DeviceDataUtil.getSleep_Sleepace_DataModel1
                                (sleepHistoryNBean);
                        Sleep_Sleepace_DataModel sleep_sleepace_dataModelByDeviceIdAndTimeTamp =
                                Sleep_Sleepace_DataModelAction
                                        .findSleep_Sleepace_DataModelByDeviceIdAndTimeTamp
                                                (TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp
                                                                ()),
                                                        sleep_sleepace_dataModel1.getTimestamp());
                        //查询本地数据库，如果没有，则新增
                        if (sleep_sleepace_dataModelByDeviceIdAndTimeTamp == null) {
                            ParseData.saveSleep_Sleepace_DataModel(sleep_sleepace_dataModel1);
                        }
                    }
                }
            }
        });
        NetProgressObservable.getInstance().hide();
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

    private void initDatePopMonthTitle() {


        Calendar calendar = calendarview.getDate();
        // LogUtil.log(TAG + " initDatePopMonthTitle:" + dateFormat.format(calendar.getTime()));
        tvDatePopTitle.setText(dateFormat.format(calendar.getTime()));
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
}
