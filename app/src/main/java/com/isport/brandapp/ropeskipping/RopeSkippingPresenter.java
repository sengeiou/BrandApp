package com.isport.brandapp.ropeskipping;

import com.isport.blelibrary.db.table.s002.DailyBrief;
import com.isport.blelibrary.db.table.s002.DailySummaries;
import com.isport.blelibrary.db.table.s002.Summary;
import com.isport.blelibrary.utils.AppLanguageUtil;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.DateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.home.bean.RopeDetailBean;
import com.isport.brandapp.repository.S002DeviceDataRepository;
import com.isport.brandapp.ropeskipping.history.bean.HistoryDateBean;
import com.isport.brandapp.ropeskipping.response.ResponseDailySummaries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RopeSkippingPresenter extends BasePresenter<RopeSkippingView> {

    private RopeSkippingView mMessageView;

    public RopeSkippingPresenter(RopeSkippingView messageView) {
        this.mMessageView = messageView;
    }


    public void getdailySummaries(String strdate, int date, String summaryType, String userId) {

        S002DeviceDataRepository.requestDailySummaries(userId, date, strdate, summaryType).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<List<DailySummaries>>(BaseApp.getApp(), false) {
            @Override
            public void onNext(List<DailySummaries> summary) {
                List<ResponseDailySummaries> summaries = new ArrayList<>();

                ResponseDailySummaries responseDailySummaries;
                DailySummaries summaries1;
                for (int i = 0; i < summary.size(); i++) {
                    summaries1 = summary.get(i);
                    responseDailySummaries = new ResponseDailySummaries();
                    responseDailySummaries.setDay(summaries1.getDay());
                    responseDailySummaries.setTotalCalories(summaries1.getTotalCalories());
                    responseDailySummaries.setTotalDuration(summaries1.getTotalDuration());
                    responseDailySummaries.setTotalSkippingNum(summaries1.getTotalSkippingNum());
                    summaries.add(responseDailySummaries);
                }

                mMessageView.sucessSummaries(summaries);

            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Constants.isSyncData = false;
            }

        });
    }

    public void getDailyBrief(String date, String userId) {

        S002DeviceDataRepository.requestDailyBrief(userId, date).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<List<DailyBrief>>(BaseApp.getApp(), false) {
            @Override
            public void onNext(List<DailyBrief> summary) {

                if (summary != null) {
                    for (int i = 0; i < summary.size(); i++) {
                        summary.get(i).setHhandMin(DateUtil.strFormatHHmm(summary.get(i).getStartTime()));
                    }
                }
                mMessageView.sucessDailyBrief(summary);

            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Constants.isSyncData = false;
            }

        });
    }

    public void getsportDaysInMonth(String userId, String month) {

        S002DeviceDataRepository.requestsportDaysInMonth(userId, month).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<List<String>>(BaseApp.getApp(), false) {
            @Override
            public void onNext(List<String> summary) {

                mMessageView.sucessDaysInMonth(summary);

            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Constants.isSyncData = false;
            }

        });
    }

    public void getSummary(String userId, String day, String summaryType) {

        S002DeviceDataRepository.requsetSummary(userId, day, summaryType).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<Summary>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Summary summary) {


                if (summary.getTotalDuration() != 0) {
                    summary.setHour("" + summary.getTotalDuration() / 60);
                    summary.setMin("" + summary.getTotalDuration() % 60);
                    summary.setStrTime(DateUtil.getRopeFormatTimehhmmss(summary.getTotalDuration()));
                } else {
                    summary.setHour("0");
                    summary.setMin("0");
                    summary.setStrTime("00:00:00");
                }

                mMessageView.successgetSummaryData(summary);

            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
            }

        });
    }


    public void getRopeUrl(String userId) {
        S002DeviceDataRepository.requsetRopeUrl(userId).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<RopeDetailBean>(BaseApp.instance) {
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
            public void onNext(RopeDetailBean s) {
                if (mMessageView != null) {
                    AppConfiguration.ropedetailLighturl = s.getLight();
                    AppConfiguration.ropedetailDarkurl = s.getDark();
                }
            }
        });
    }

    public void getCourseUrl(String userId) {
        S002DeviceDataRepository.requCourseUrl(userId).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<String>(BaseApp.instance) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Logger.myLog("getCourseUrl=========== ExceptionHandle"+e.toString());
            }

            @Override
            public void onNext(String s) {
                Logger.myLog("getCourseUrl===========" + s);
                if (mMessageView != null) {
                    AppConfiguration.ropeCourseUrl = s + "&language=" + AppLanguageUtil.getCurrentLanguageStr();
                }
            }
        });
    }

    public void getRopeUrlChanlleg(String userId) {
        S002DeviceDataRepository.requsetRopeChallegUrl(userId).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<String>(BaseApp.instance) {
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
            public void onNext(String s) {
                if (mMessageView != null) {
                    AppConfiguration.challegeurl = s + "&language=" + AppLanguageUtil.getCurrentLanguageStr();
                }
            }
        });
    }

    public void getBraceletWeekData(int data) {
        Observable.create(new ObservableOnSubscribe<List<HistoryDateBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HistoryDateBean>> emitter) throws Exception {
                //AppConfiguration.watchID = "1";

                Logger.myLog("getBraceletWeekData1");
                ArrayList<HistoryDateBean> wristbandsteps = new ArrayList<>();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(data * 1000l));
                String strDate = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd");
                HistoryDateBean wristbandstep1 = null;
                for (int i = 0; i < 7; i++) {
                    wristbandstep1 = new HistoryDateBean();
                    if (i != 0) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    strDate = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd");
                    Logger.myLog(strDate);
                    wristbandstep1.setMdDate(DateUtil.dataToString(calendar.getTime(), "M/d"));
                    wristbandstep1.setDate(strDate);
                    wristbandsteps.add(wristbandstep1);
                }
                Collections.reverse(wristbandsteps);
                emitter.onNext(wristbandsteps);
                Logger.myLog("getBraceletWeekData2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<List<HistoryDateBean>>(BaseApp.getApp(), false) {
            @Override
            public void onNext(List<HistoryDateBean> wristbandstep) {
                if (mMessageView != null) {
                    Logger.myLog("HistoryDateBean" + wristbandstep);
                    mMessageView.sucessWeekStr(wristbandstep);
                }
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                Logger.myLog(e.toString());
            }

        });

    }


    public void getRopeMonthData(int data) {
        Observable.create(new ObservableOnSubscribe<List<HistoryDateBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HistoryDateBean>> emitter) throws Exception {
                // AppConfiguration.watchID = "1";
                ArrayList<HistoryDateBean> historyDateBeans = new ArrayList<>();
                Calendar calendar = Calendar.getInstance();
                //需要判断是不是当前的月
                calendar.setTime(new Date(data * 1000l));
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                int monthCount = DateUtil.getMonthOfDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
                String strDate = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd");

                HistoryDateBean historyDateBean;
                for (int i = 0; i < monthCount; i++) {
                    historyDateBean = new HistoryDateBean();
                    if (i != 0) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    strDate = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd");
                    Logger.myLog(strDate);
                    historyDateBean.setDate(strDate);
                    historyDateBean.setMdDate(DateUtil.dataToString(calendar.getTime(), "MM-dd"));
                    historyDateBeans.add(historyDateBean);
                }
                emitter.onNext(historyDateBeans);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<List<HistoryDateBean>>(BaseApp.getApp()) {
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
            public void onNext(List<HistoryDateBean> historyDateBeans) {

                if (mMessageView != null) {
                    mMessageView.sucessMonthStr(historyDateBeans);
                }
            }
        });
    }


}
