package com.isport.brandapp.sport.present;

import android.text.TextUtils;

import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.device.sleep.TimeUtil;
import com.isport.brandapp.sport.bean.ResultHistorySportSummarizingData;
import com.isport.brandapp.sport.bean.ResultSportHistoryListBean;
import com.isport.brandapp.sport.bean.ResultSportHistroyList;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.sport.response.SportRepository;
import com.isport.brandapp.sport.view.SportHistoryView;

import java.util.ArrayList;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class SportHistoryPresent extends BasePresenter<SportHistoryView> implements ISportHistoryPresent {
    SportHistoryView view;
    SportRepository sportRepository;

    public SportHistoryPresent(SportHistoryView view) {
        this.view = view;
        this.sportRepository = new SportRepository();
    }

    @Override
    public void getSportSummerData() {

        sportRepository.getTotal().as(view.bindAutoDispose()).subscribe(new BaseObserver<ResultHistorySportSummarizingData>(BaseApp.getApp(), false) {
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
            public void onNext(ResultHistorySportSummarizingData resultHistorySportSummarizingData) {
                NetProgressObservable.getInstance().hide();

                if (view != null) {

                    view.successLoadSummarData(resultHistorySportSummarizingData);

                } else {

                }

            }
        });
    }

    @Override
    public void getFisrtSportHistory(String userid, String offset) {
        sportRepository.getHistory(userid, offset).as(view.bindAutoDispose()).subscribe(new BaseObserver<ResultSportHistroyList>(BaseApp.getApp(), false) {
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
            public void onNext(ResultSportHistroyList lastDataBeanList) {
                NetProgressObservable.getInstance().hide();


                ResultSportHistroyList result = lastDataBeanList;
                ArrayList<HistoryBeanList> historyBeanLists = new ArrayList<>();
                if (result == null && result.getDatalist() == null) {
                    return;
                }

                for (int i = 0; i < result.getDatalist().size(); i++) {

                    ResultSportHistoryListBean bean = lastDataBeanList.getDatalist().get(i);
                    if (i == 0) {
                        AppConfiguration.hasSportDate = bean.getDateStr();
                    }

                    ArrayList<SportSumData> sportSumDataArrayList = bean.getList();

                    HistoryBeanList month = new HistoryBeanList();

                    month.viewType = JkConfiguration.HistoryType.TYPE_MONTH;
                    month.DeviceTpye = JkConfiguration.DeviceType.SPORT;
                    month.moth = bean.getDateStr();
                    historyBeanLists.add(month);
                    for (int j = 0; j < sportSumDataArrayList.size(); j++) {
                        HistoryBeanList historyBeanList = new HistoryBeanList();
                        SportSumData sumData = sportSumDataArrayList.get(j);
                        String strCal = "0";
                        if (!TextUtils.isEmpty(sumData.getCalories())) {
                            strCal = CommonDateUtil.formatInterger(Float.parseFloat(sumData.getCalories()));
                        }
                        sumData.setCalories(strCal);
                        sumData.setStrEndTime(TimeUtil.longformatMinute(sumData.getEndTimestamp()));
                        sumData.setStrTime(TimeUtil.getTimerFormatedStrings(sumData.getTimeLong() * 1000, 0));
                        if (sumData.getType() == JkConfiguration.SportType.sportOutRuning) {
                            sumData.setDrawableRes(R.drawable.icon_sport_recode_out);
                            sumData.setSportTypeName(UIUtils.getString(R.string.outdoor_running));
                            sumData.setStrSpeed(TextUtils.isEmpty(sumData.getAvgPace()) ? "00'00''" : sumData.getAvgPace());
                        } else if (sumData.getType() == JkConfiguration.SportType.sportIndoor) {
                            sumData.setDrawableRes(R.drawable.icon_sport_recode_indoor);
                            sumData.setSportTypeName(UIUtils.getString(R.string.treadmill));
                            sumData.setStrSpeed(TextUtils.isEmpty(sumData.getAvgPace()) ? "00'00''" : sumData.getAvgPace());
                        } else if (sumData.getType() == JkConfiguration.SportType.sportBike) {
                            sumData.setDrawableRes(R.drawable.icon_sport_recode_bike);
                            sumData.setSportTypeName(UIUtils.getString(R.string.tdoor_cycling));
                            sumData.setStrSpeed(sumData.getAvgSpeed() + UIUtils.getString(R.string.unit_speed));
                        } else if (sumData.getType() == JkConfiguration.SportType.sportWalk) {
                            sumData.setDrawableRes(R.drawable.icon_sport_recode_walk);
                            sumData.setSportTypeName(UIUtils.getString(R.string.walking));
                            sumData.setStrSpeed(TextUtils.isEmpty(sumData.getAvgPace()) ? "00'00''" : sumData.getAvgPace());
                        }
                        // public int viewType;
                        //
                        //    public int DeviceTpye;
                        historyBeanList.sportDetailData = sumData;
                        historyBeanList.viewType = JkConfiguration.HistoryType.TYPE_CONTENT;
                        historyBeanList.DeviceTpye = JkConfiguration.DeviceType.SPORT;
                        historyBeanLists.add(historyBeanList);
                    }
                }
                if (view != null) {
                    view.successFisrtHistory(historyBeanLists);
                }
            }
        });

    }

    @Override
    public void getLoadMoreHistory(String userid, String offset) {
        sportRepository.getHistory(userid, offset).as(view.bindAutoDispose()).subscribe(new BaseObserver<ResultSportHistroyList>(BaseApp.getApp(), false) {
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
            public void onNext(ResultSportHistroyList lastDataBeanList) {
                NetProgressObservable.getInstance().hide();

                ResultSportHistroyList result = lastDataBeanList;
                ArrayList<HistoryBeanList> historyBeanLists = new ArrayList<>();
                if (result == null && result.getDatalist() == null) {
                    return;
                }

                for (int i = 0; i < result.getDatalist().size(); i++) {
                    ResultSportHistoryListBean bean = lastDataBeanList.getDatalist().get(i);
                    ArrayList<SportSumData> sportSumDataArrayList = bean.getList();

                    HistoryBeanList month = new HistoryBeanList();
                    month.viewType = JkConfiguration.HistoryType.TYPE_MONTH;
                    month.DeviceTpye = JkConfiguration.DeviceType.SPORT;
                    month.moth = bean.getDateStr();
                    historyBeanLists.add(month);
                    for (int j = 0; j < sportSumDataArrayList.size(); j++) {
                        HistoryBeanList historyBeanList = new HistoryBeanList();
                        SportSumData sumData = sportSumDataArrayList.get(j);
                        sumData.setStrEndTime(TimeUtil.longformatMinute(sumData.getEndTimestamp()));
                        sumData.setStrTime(TimeUtil.getTimerFormatedStrings(sumData.getTimeLong(), 0));
                        if (sumData.getType() == JkConfiguration.SportType.sportOutRuning) {
                            sumData.setDrawableRes(R.drawable.icon_sport_recode_out);
                            sumData.setSportTypeName(UIUtils.getString(R.string.outdoor_running));
                            sumData.setStrSpeed(TextUtils.isEmpty(sumData.getAvgPace()) ? "00'00''" : sumData.getAvgPace());
                        } else if (sumData.getType() == JkConfiguration.SportType.sportIndoor) {
                            sumData.setDrawableRes(R.drawable.icon_sport_recode_indoor);
                            sumData.setSportTypeName(UIUtils.getString(R.string.treadmill));
                            sumData.setStrSpeed(TextUtils.isEmpty(sumData.getAvgPace()) ? "00'00''" : sumData.getAvgPace());
                        } else if (sumData.getType() == JkConfiguration.SportType.sportBike) {
                            sumData.setDrawableRes(R.drawable.icon_sport_recode_bike);
                            sumData.setSportTypeName(UIUtils.getString(R.string.tdoor_cycling));
                            sumData.setStrSpeed(sumData.getAvgSpeed() + UIUtils.getString(R.string.unit_speed));
                        } else if (sumData.getType() == JkConfiguration.SportType.sportWalk) {
                            sumData.setDrawableRes(R.drawable.icon_sport_recode_walk);
                            sumData.setSportTypeName(UIUtils.getString(R.string.walking));
                            sumData.setStrSpeed(TextUtils.isEmpty(sumData.getAvgPace()) ? "00'00''" : sumData.getAvgPace());
                        }
                        // public int viewType;
                        //
                        //    public int DeviceTpye;
                        historyBeanList.sportDetailData = sumData;
                        historyBeanList.viewType = JkConfiguration.HistoryType.TYPE_CONTENT;
                        historyBeanList.DeviceTpye = JkConfiguration.DeviceType.SPORT;
                        historyBeanLists.add(historyBeanList);
                    }
                }
                if (view != null) {
                    view.successLoadMoreHistory(historyBeanLists);
                }
            }
        });
    }
}
