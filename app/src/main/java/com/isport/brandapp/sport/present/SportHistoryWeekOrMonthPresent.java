package com.isport.brandapp.sport.present;

import android.text.TextUtils;

import com.isport.blelibrary.utils.DateUtil;
import com.isport.brandapp.R;
import com.isport.brandapp.device.sleep.TimeUtil;
import com.isport.brandapp.sport.adapter.BaseRecyclerAdapter;
import com.isport.brandapp.sport.bean.IphoneSportListVo;
import com.isport.brandapp.sport.bean.IphoneSportWeekVo;
import com.isport.brandapp.sport.bean.MoreTypeBean;
import com.isport.brandapp.sport.bean.ResultSportHistoryListBean;
import com.isport.brandapp.sport.bean.ResultSportHistroyList;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.sport.response.SportRepository;
import com.isport.brandapp.sport.view.SportHistoryWeekOrMonthView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;

import com.isport.blelibrary.utils.CommonDateUtil;

import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class SportHistoryWeekOrMonthPresent extends BasePresenter<SportHistoryWeekOrMonthView> implements ISportHistoryWeekOrMonthPresent {
    SportHistoryWeekOrMonthView view;
    SportRepository sportRepository;

    public SportHistoryWeekOrMonthPresent(SportHistoryWeekOrMonthView view) {
        this.view = view;
        this.sportRepository = new SportRepository();
    }

    @Override
    public void getSportSummerData(boolean isMonth, long times) {

        if (isMonth) {
            sportRepository.getMonthTotal(times).as(view.bindAutoDispose()).subscribe(new BaseObserver<IphoneSportListVo>(BaseApp.getApp(), false) {
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
                public void onNext(IphoneSportListVo resultHistorySportSummarizingData) {
                    NetProgressObservable.getInstance().hide();

                    if (view != null) {


                        List<IphoneSportWeekVo> list = parseMothData(resultHistorySportSummarizingData.getList());
                        ArrayList<String> date = new ArrayList<>();
                        ArrayList<Float> disList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            date.add(list.get(i).getDate());
                            disList.add((float) list.get(i).getDistance());
                        }

                        view.successSummarData(resultHistorySportSummarizingData.getTimes(), resultHistorySportSummarizingData.getAllDistance(), date, disList);

                    } else {

                    }

                }
            });
        } else {
            sportRepository.getWeekTotal(times).as(view.bindAutoDispose()).subscribe(new BaseObserver<IphoneSportListVo>(BaseApp.getApp(), false) {
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
                public void onNext(IphoneSportListVo resultHistorySportSummarizingData) {
                    NetProgressObservable.getInstance().hide();

                    if (view != null) {
                        List<IphoneSportWeekVo> list = parseWeekData(resultHistorySportSummarizingData.getList());
                        ArrayList<String> date = new ArrayList<>();
                        ArrayList<Float> disList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            date.add(list.get(i).getDate());
                            disList.add((float) list.get(i).getDistance());
                        }

                        view.successSummarData(resultHistorySportSummarizingData.getTimes(), resultHistorySportSummarizingData.getAllDistance(), date, disList);

                    } else {

                    }

                }
            });
        }


    }

    @Override
    public void getWeekOrMonthDetailData(boolean isMonth, long times, String userid) {
        if (isMonth) {
            sportRepository.getHistoryMonth(userid, times).as(view.bindAutoDispose()).subscribe(new BaseObserver<ResultSportHistroyList>(BaseApp.getApp(), false) {
                @Override
                protected void hideDialog() {

                }

                @Override
                protected void showDialog() {

                }

                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    if (view != null) {
                        ArrayList<MoreTypeBean> historyBeanLists = new ArrayList<>();
                        view.successDetailData(historyBeanLists);
                    }

                }

                @Override
                public void onNext(ResultSportHistroyList lastDataBeanList) {
                    NetProgressObservable.getInstance().hide();

                    ResultSportHistroyList result = lastDataBeanList;
                    ArrayList<MoreTypeBean> historyBeanLists = new ArrayList<>();
                    if (result == null && result.getDatalist() == null && result.getDatalist().size() == 0) {
                        if (view != null) {
                            view.successDetailData(historyBeanLists);
                           // return;
                        }
                    }

                    for (int i = 0; i < result.getDatalist().size(); i++) {
                        ResultSportHistoryListBean bean = lastDataBeanList.getDatalist().get(i);
                        ArrayList<SportSumData> sportSumDataArrayList = bean.getList();

                        MoreTypeBean moreTypeBean = new MoreTypeBean(BaseRecyclerAdapter.type_month);
                        moreTypeBean.moth = bean.getDateStr();
                        historyBeanLists.add(moreTypeBean);
                        for (int j = 0; j < sportSumDataArrayList.size(); j++) {
                            MoreTypeBean moreTypeBean1 = new MoreTypeBean(BaseRecyclerAdapter.type_item);
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
                            moreTypeBean1.sportSumData = sumData;
                            historyBeanLists.add(moreTypeBean1);
                        }
                    }
                    if (view != null) {
                        view.successDetailData(historyBeanLists);
                    }
                }
            });
        } else {
            sportRepository.getHistoryWeek(userid, times).as(view.bindAutoDispose()).subscribe(new BaseObserver<ResultSportHistroyList>(BaseApp.getApp(), false) {
                @Override
                protected void hideDialog() {

                }

                @Override
                protected void showDialog() {

                }

                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    if (view != null) {
                        ArrayList<MoreTypeBean> historyBeanLists = new ArrayList<>();
                        view.successDetailData(historyBeanLists);
                    }
                }

                @Override
                public void onNext(ResultSportHistroyList lastDataBeanList) {
                    NetProgressObservable.getInstance().hide();

                    ResultSportHistroyList result = lastDataBeanList;
                    ArrayList<MoreTypeBean> historyBeanLists = new ArrayList<>();
                    if (result == null && result.getDatalist() == null && result.getDatalist().size() == 0) {
                        if (view != null) {
                            view.successDetailData(historyBeanLists);
                        }
                        return;
                    }

                    for (int i = 0; i < result.getDatalist().size(); i++) {
                        ResultSportHistoryListBean bean = lastDataBeanList.getDatalist().get(i);
                        ArrayList<SportSumData> sportSumDataArrayList = bean.getList();

                        MoreTypeBean moreTypeBean = new MoreTypeBean(BaseRecyclerAdapter.type_month);
                        moreTypeBean.moth = bean.getDateStr();
                        historyBeanLists.add(moreTypeBean);
                        for (int j = 0; j < sportSumDataArrayList.size(); j++) {
                            MoreTypeBean moreTypeBean1 = new MoreTypeBean(BaseRecyclerAdapter.type_item);
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
                            moreTypeBean1.sportSumData = sumData;
                            historyBeanLists.add(moreTypeBean1);
                        }
                    }
                    if (view != null) {
                        view.successDetailData(historyBeanLists);
                    }
                }
            });
        }

    }


    public List<IphoneSportWeekVo> parseWeekData(List<IphoneSportWeekVo> list) {
        List<IphoneSportWeekVo> result = new ArrayList<>();
        HashMap<String, Double> strList = getMap(list);
        Calendar calendar = getStartCanlendar(list.get(0).getDate());
        String strDate;
        for (int i = 0; i < 7; i++) {
            IphoneSportWeekVo iphoneSportWeekVo1 = new IphoneSportWeekVo();
            if (i != 0) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            strDate = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd");
            if (strList.containsKey(strDate)) {
                iphoneSportWeekVo1.setDistance(strList.get(strDate));
            } else {
                iphoneSportWeekVo1.setDistance(0);
            }
            iphoneSportWeekVo1.setDate(DateUtil.dataToString(calendar.getTime(), "M/d"));

            result.add(iphoneSportWeekVo1);
        }
        return result;
    }


    public Calendar getStartCanlendar(String strStart) {
        String[] strings = strStart.split("-");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(strings[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(strings[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strings[2]));
        return calendar;
    }

    public HashMap<String, Double> getMap(List<IphoneSportWeekVo> list) {
        HashMap<String, Double> strList = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            strList.put(list.get(i).getDate(), list.get(i).getDistance());
        }
        return strList;
    }


    public List<IphoneSportWeekVo> parseMothData(List<IphoneSportWeekVo> list) {
        HashMap<String, Double> strList = getMap(list);
        List<IphoneSportWeekVo> result = new ArrayList<>();
        IphoneSportWeekVo iphoneSportWeekVo = list.get(0);
        String startDate = iphoneSportWeekVo.getDate();
        Calendar calendar = getStartCanlendar(startDate);
        String strDate;
        int monthCount = DateUtil.getMonthOfDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        for (int i = 0; i < monthCount; i++) {
            IphoneSportWeekVo iphoneSportWeekVo1 = new IphoneSportWeekVo();
            if (i != 0) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            strDate = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd");
            if (strList.containsKey(strDate)) {
                iphoneSportWeekVo1.setDistance(strList.get(strDate));
            } else {
                iphoneSportWeekVo1.setDistance(0);
            }
            iphoneSportWeekVo1.setDate(DateUtil.dataToString(calendar.getTime(), "M/d"));

            result.add(iphoneSportWeekVo1);
        }
        return result;
    }
}
