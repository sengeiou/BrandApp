package com.isport.brandapp.device.scale.presenter;

import androidx.annotation.NonNull;

import com.isport.blelibrary.db.action.scale.Scale_FourElectrode_DataModelAction;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.device.history.util.HistoryParmUtil;
import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.device.scale.bean.ScaleDayBean;
import com.isport.brandapp.device.scale.bean.ScaleHistoryBean;
import com.isport.brandapp.device.scale.bean.ScaleHistoryNBean;
import com.isport.brandapp.device.scale.bean.ScaleHistroyList;
import com.isport.brandapp.device.scale.bean.ScaleHistroyNList;
import com.isport.brandapp.device.scale.view.ScaleHistoryView;
import com.isport.brandapp.net.APIService;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.parm.db.DeviceHistoryParms;
import com.isport.brandapp.parm.http.HistoryParm;
import com.isport.brandapp.repository.MainResposition;

import java.util.ArrayList;
import java.util.List;

import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.RxScheduler;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * @Author
 * @Date 2018/10/23
 * @Fuction
 */

public class ScaleHistoryPresenter extends BasePresenter<ScaleHistoryView> {

    private ScaleHistoryView view;

    public ScaleHistoryPresenter(ScaleHistoryView view) {
        this.view = view;
    }

    public void onRespondError(String message) {
        if (view != null) {
            view.onRespondError(message);
        }

    }


    public void getMothListData(String userId) {
//https://test.api.mini-banana.com/isport/concumer-fatsteelyard/fatsteelyard/fatsteelyardTarget/monthCounts?userId=317
        new NetworkBoundResource<List<Long>>() {
            @Override
            public io.reactivex.Observable<List<Long>> getFromDb() {
                return null;
            }

            @Override
            public io.reactivex.Observable<List<Long>> getNoCacheData() {
                return null;
            }

            @Override
            public boolean shouldFetchRemoteSource() {
                return false;
            }

            @Override
            public boolean shouldStandAlone() {
                return false;
            }

            @Override
            public io.reactivex.Observable<List<Long>> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).fatsteelyardTarget(userId).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);
            }

            @Override
            public void saveRemoteSource(List<Long> bean) {

            }
        }.getAsObservable().subscribe(new BaseObserver<List<Long>>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                Logger.myLog("messageCount ExceptionHandle");
            }

            @Override
            public void onNext(List<Long> messageCount) {


                Logger.myLog("messageCount" + messageCount);

                if (view != null) {
                    view.successMothList(messageCount);
                }






                /*if (messageCount != null) {
                    if (messageCount.getFansNums() > 0) {
                        mMessageCounts = messageCount.getFansNums();
                        if (fragmentMine != null) {
                            fragmentMine.setmMessageCount(mMessageCounts);
                        }
                        view_message_point.setVisibility(View.VISIBLE);
                    } else {
                        if (fragmentMine != null) {
                            fragmentMine.setmMessageCount(0);
                        }
                        mMessageCounts = 0;
                        view_message_point.setVisibility(View.INVISIBLE);
                    }

                }*/
            }
        });
    }

    public void getFirstHistoryModel(long timesTampServer, int requestCode, long timeTamp, String deviceId, String
            currentMonth, String deviceType, String mac, String
                                             time, String pageNum,
                                     String size) {
        MainResposition<ScaleHistroyNList, HistoryParm, BaseUrl, DeviceHistoryParms> mainResposition = new
                MainResposition<>();
        mainResposition.requst(HistoryParmUtil.setHistory(timesTampServer, JkConfiguration.Url.FAT_STEELYARD,
                JkConfiguration.Url
                        .HISTORY_DATA, requestCode, timeTamp, deviceId,
                currentMonth, deviceType, mac, time, pageNum, size,
                JkConfiguration.RequstType
                        .GETSCALEHISTORYLISTDATA))
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<ScaleHistroyNList>(context) {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        if (e.message.startsWith("操作成功")) {
                            if (isViewAttached()) {
                                //无历史数据的情况
                            }
                        }
                    }

                    @Override
//                    public void onNext(BaseResponse<ScaleHistroyList> deviceHomeData) {
                    public void onNext(ScaleHistroyNList deviceHomeData) {
                        NetProgressObservable.getInstance().hide();
                        Logger.myLog("getFirstHistoryModel == " + deviceHomeData.time + " data == " + deviceHomeData
                                .list.size());
                        if (deviceHomeData == null) {
                            //本地查询
                            if (view != null) {
                                view.successRefresh(null, true, "", 0);
                            }
                        } else {
                            //返回
                            BaseResponse<ScaleHistroyList> scaleHistroyListBaseResponse = new BaseResponse<>();
                            ScaleHistroyList scaleHistroyList = new ScaleHistroyList();
                            List<ScaleHistoryBean> scaleHistoryBeanList = new ArrayList<>();
                            ScaleHistoryBean scaleHistoryBean = new ScaleHistoryBean();
                            ArrayList<ScaleDayBean> datalist = new ArrayList<>();

                            if (deviceHomeData.list == null || (deviceHomeData.list != null && deviceHomeData.list
                                    .size() == 0)) {
                                //返回没有数据的情况
                                if (view != null) {
                                    view.successRefresh(null, true, "", 0);
                                }
                            } else {
                                for (int i = 0; i < deviceHomeData.list.size(); i++) {
                                    Logger.myLog(deviceHomeData.list.get(i).toString());
                                }
                                //有数据的情况
                                List<ScaleHistoryNBean> scaleHistoryNBeanList = deviceHomeData.list;
                                //查询第一条所处的月,并查询出当月数据
                                ScaleHistoryNBean scaleHistoryNBean = scaleHistoryNBeanList.get(0);
                                //有数据的最近的一个月的String
                                String timeByyyyyMM1 = TimeUtils.getTimeByyyyyMMdd
                                        (Long.parseLong(scaleHistoryNBean.getTimestamp()));
                                String[] split = timeByyyyyMM1.split("-");
                                //拿一个月的数据
                                scaleHistroyListBaseResponse.setCode(2000);
                                scaleHistroyListBaseResponse.setIslastdata(deviceHomeData.time == 0 ? true : false);
                                scaleHistoryBean.setMonthTitle(true);
                                scaleHistoryBean.setMonth(split[0] + "-" + split[1]);
                                if (scaleHistoryNBeanList.size() == 1) {
                                    //当月只有一条数据
                                    scaleHistoryBean.setLeftFatPersent("--");
                                    scaleHistoryBean.setLeftWeight("--");
                                } else {
                                    //多条数据，比较第一条和最后一条
                                    ScaleHistoryNBean scaleHistoryNBeanFirst = scaleHistoryNBeanList
                                            .get(0);
                                    ScaleHistoryNBean scaleHistoryNBeanEnd = scaleHistoryNBeanList
                                            .get(scaleHistoryNBeanList.size() - 1);
                                    float weight = (float) scaleHistoryNBeanEnd.getBodyWeight();
                                    float weightLast = (float) scaleHistoryNBeanFirst.getBodyWeight();
                                    float leftWeight = weight - weightLast;
                                    if (leftWeight != 0) {
                                        scaleHistoryBean.setLeftWeight(leftWeight + "");
                                    } else {
                                        scaleHistoryBean.setLeftWeight("--");
                                    }
                                    double bfp = scaleHistoryNBeanEnd.getBfpBodyFatPercent();
                                    double bfpLast = scaleHistoryNBeanFirst.getBfpBodyFatPercent();
                                    double leftNFP = bfp - bfpLast;
                                    if (leftNFP != 0) {
                                        scaleHistoryBean.setLeftFatPersent(leftNFP + "");
                                    } else {
                                        scaleHistoryBean.setLeftFatPersent("--");
                                    }
                                }
                                for (int i = scaleHistoryNBeanList.size() - 1; i >= 0; i--) {
                                    //数据要存储要更新到本地
                                    UserInfoBean bean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance()
                                            .getPeopleIdStr(BaseApp.getApp
                                                    ()));
                                    Scale_FourElectrode_DataModel scale_fourElectrode_dataModel =
                                            getScale_fourElectrode_dataModel(scaleHistoryNBeanList.get(i), bean);
                                    Scale_FourElectrode_DataModel scaleFourElectrodeDataModelByDeviceIdAndTimeTamp =
                                            Scale_FourElectrode_DataModelAction
                                                    .findScaleFourElectrodeDataModelByDeviceIdAndTimeTamp
                                                            (scale_fourElectrode_dataModel.getTimestamp(), TokenUtil
                                                                    .getInstance().getPeopleIdStr
                                                                            (BaseApp.getApp()));
                                    //查询本地数据库，如果没有，则新增
                                    if (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp == null) {
                                        ParseData.saveScaleFourElectrodeData(scale_fourElectrode_dataModel);
                                    }
                                    ScaleHistoryNBean scaleHistoryNBeanFirst = scaleHistoryNBeanList
                                            .get(i);
                                    ScaleDayBean scaleDayBean = new ScaleDayBean();
                                    float weight = (float) scaleHistoryNBeanFirst.getBodyWeight();
                                    double bfp = scaleHistoryNBeanFirst.getBfpBodyFatPercent();
                                    scaleDayBean.weight = weight + "";
                                    scaleDayBean.fatpersent = bfp + "";
                                    //和上次比较的值
                                    if (i - 1 >= 0) {
                                        //存在下一条数据
                                        ScaleHistoryNBean scaleHistoryNBeanLast = scaleHistoryNBeanList
                                                .get(i - 1);
                                        float weightLast = (float) scaleHistoryNBeanLast.getBodyWeight();
                                        float leftWeight = weight - weightLast;
                                        if (leftWeight != 0) {
                                            scaleDayBean.leftweight = leftWeight + "";
                                        } else {
                                            scaleDayBean.leftweight = "--";
                                        }
                                        double bfpLast = scaleHistoryNBeanLast.getBfpBodyFatPercent();
                                        double leftNFP = bfp - bfpLast;
                                        if (leftNFP != 0) {
                                            scaleDayBean.leftfatpersent = leftNFP + "";
                                        } else {
                                            scaleDayBean.leftfatpersent = "--";
                                        }
                                    } else {
                                        scaleDayBean.leftweight = "--";
                                        scaleDayBean.leftfatpersent = "--";
                                    }
                                    scaleDayBean.creatTime = Long.parseLong(scaleHistoryNBeanFirst.getTimestamp());
                                    datalist.add(scaleDayBean);
                                }
                            }
                            scaleHistoryBean.setDatalist(datalist);
                            scaleHistoryBeanList.add(scaleHistoryBean);
                            scaleHistroyList.mNextMonth = "";
                            scaleHistroyList.mNextTimeTamp = deviceHomeData.time;
                            scaleHistroyList.list = scaleHistoryBeanList;
                            Logger.myLog("Request_getScaleHistoryData MONTH == " + scaleHistoryBean.toString());
                            scaleHistroyListBaseResponse.setData(scaleHistroyList);
                            ArrayList<ScaleHistoryBean> scaleHistoryBeans = (ArrayList<ScaleHistoryBean>) parseData
                                    (scaleHistroyListBaseResponse);
                            ScaleHistroyList data = scaleHistroyListBaseResponse.getData();
                            if (scaleHistoryBeans != null) {
                                Logger.myLog("Request_getScaleHistoryData 请求完成 == " + data
                                        .mNextMonth);
                                if (view != null) {
                                    view.successRefresh(scaleHistoryBeans, scaleHistroyListBaseResponse
                                            .isIslastdata(), data
                                            .mNextMonth, data.mNextTimeTamp);
                                }
                            } else {
                                Logger.myLog("Request_getScaleHistoryData 请求完成 == 为null");
                            }
                        }
                    }
                });
    }

    public void getScaleHomeFirstHistoryModel(long timesTampServer, int requestCode, long timeTamp, String deviceId, String
            currentMonth, String deviceType, String mac, String
                                                      time, String pageNum,
                                              String size) {
        MainResposition<ScaleHistroyNList, HistoryParm, BaseUrl, DeviceHistoryParms> mainResposition = new
                MainResposition<>();
        mainResposition.requst(HistoryParmUtil.setHistory(timesTampServer, JkConfiguration.Url.FAT_STEELYARD,
                JkConfiguration.Url
                        .HISTORY_DATA, requestCode, timeTamp, deviceId,
                currentMonth, deviceType, mac, time, pageNum, size,
                JkConfiguration.RequstType
                        .GETSCALEHISTORYLISTDATA))
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<ScaleHistroyNList>(context) {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        if (e.message.startsWith("操作成功")) {
                            if (view != null) {
                                //无历史数据的情况
                            }
                        }
                    }

                    @Override
//                    public void onNext(BaseResponse<ScaleHistroyList> deviceHomeData) {
                    public void onNext(ScaleHistroyNList deviceHomeData) {
                        if (view != null) {
                            view.successMainScale(deviceHomeData);

                            if (deviceHomeData != null && deviceHomeData.list != null) {

                                if (deviceHomeData.list.size() == 0) {
                                    return;
                                }

                                List<ScaleHistoryNBean> scaleHistoryNBeanList = deviceHomeData.list;

                                for (int i = scaleHistoryNBeanList.size() - 1; i >= 0; i--) {
                                    //数据要存储要更新到本地
                                    UserInfoBean bean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance()
                                            .getPeopleIdStr(BaseApp.getApp
                                                    ()));
                                    Scale_FourElectrode_DataModel scale_fourElectrode_dataModel =
                                            getScale_fourElectrode_dataModel(scaleHistoryNBeanList.get(i), bean);
                                    Scale_FourElectrode_DataModel scaleFourElectrodeDataModelByDeviceIdAndTimeTamp =
                                            Scale_FourElectrode_DataModelAction
                                                    .findScaleFourElectrodeDataModelByDeviceIdAndTimeTamp
                                                            (scale_fourElectrode_dataModel.getTimestamp(), TokenUtil
                                                                    .getInstance().getPeopleIdStr
                                                                            (BaseApp.getApp()));
                                    //查询本地数据库，如果没有，则新增
                                    if (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp == null) {
                                        ParseData.saveScaleFourElectrodeData(scale_fourElectrode_dataModel);
                                    }

                                }
                            }

                        }
                    }
                });
    }

    @NonNull
    private Scale_FourElectrode_DataModel getScale_fourElectrode_dataModel(ScaleHistoryNBean scaleHistoryNBean,
                                                                           UserInfoBean bean) {
        Scale_FourElectrode_DataModel scale_fourElectrode_dataModel;
        scale_fourElectrode_dataModel = new Scale_FourElectrode_DataModel();
        scale_fourElectrode_dataModel.setDeviceId(scaleHistoryNBean.getDeviceId());
        scale_fourElectrode_dataModel.setReportId(scaleHistoryNBean.getFatsteelyardTargetId());
        scale_fourElectrode_dataModel.setUserId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()
        ));
        scale_fourElectrode_dataModel.setDateStr(scaleHistoryNBean.getDateStr());
        scale_fourElectrode_dataModel.setTimestamp(Long.parseLong(scaleHistoryNBean.getTimestamp()));
        scale_fourElectrode_dataModel.setSex(bean.getGender().equals("Male") ? 0 : 1);
        scale_fourElectrode_dataModel.setHight((int) Float.parseFloat(bean.getHeight()));
        scale_fourElectrode_dataModel.setAge(TimeUtils.getAge(bean.getBirthday()));
        scale_fourElectrode_dataModel.setR(2000);//暂设置2000
        scale_fourElectrode_dataModel.setWeight((float) scaleHistoryNBean.getBodyWeight());
        scale_fourElectrode_dataModel.setBFP(scaleHistoryNBean.getBfpBodyFatPercent());
        scale_fourElectrode_dataModel.setSLM(scaleHistoryNBean.getSlmMuscleWeight());
        scale_fourElectrode_dataModel.setBWP(scaleHistoryNBean.getBwpBodyWeightPercent());//水含量
        scale_fourElectrode_dataModel.setBMC(scaleHistoryNBean.getBmcBoneMineralContent());
        scale_fourElectrode_dataModel.setVFR(scaleHistoryNBean.getVfrVisceralFatRating());
        scale_fourElectrode_dataModel.setPP(scaleHistoryNBean.getPpProteinPercent());
        scale_fourElectrode_dataModel.setSMM(scaleHistoryNBean.getSmmSkeletalMuscleMass());
        scale_fourElectrode_dataModel.setBMR(scaleHistoryNBean.getBmrBasalMetabolicRate());
        scale_fourElectrode_dataModel.setBMI(scaleHistoryNBean.getBmi());
        scale_fourElectrode_dataModel.setSBW(scaleHistoryNBean.getSlmMuscleWeight());
        scale_fourElectrode_dataModel.setMC(scaleHistoryNBean.getMcMuscleControl());
        scale_fourElectrode_dataModel.setWC(scaleHistoryNBean.getWcWeightControl());
        scale_fourElectrode_dataModel.setFC(scaleHistoryNBean.getFcFatControl());
        scale_fourElectrode_dataModel.setMA((int) scaleHistoryNBean.getMaBodyAge());
        scale_fourElectrode_dataModel.setSBC((int) scaleHistoryNBean.getSbcIndividualScore());
        return scale_fourElectrode_dataModel;
    }


    public static List<ScaleHistoryBean> parseData(BaseResponse<ScaleHistroyList> deviceHomeData) {

        List<ScaleHistoryBean> list = null;

        if (deviceHomeData != null) {
            ScaleHistroyList bean = deviceHomeData.getData();
            if (bean != null) {
                list = bean.list;
                for (int i = 0; i < list.size(); i++) {
                    ScaleHistoryBean historyBean = list.get(i);
                    HistoryBeanList scaleHistroyList = new HistoryBeanList();
                    //historyBean
                    //historyBean.datalist= BaseApp.getApp().getResources().getString(R.string
                    // .app_device_watch);

                    if ("--".equals(historyBean.leftFatPersent)) {
                        historyBean.strFat = "0";
                    } else {
                        if (Float.valueOf(historyBean.leftFatPersent) > 0) {
                            historyBean.isUpFatPresent = true;
                        } else {
                            historyBean.isUpFatPresent = false;
                        }
                        historyBean.strFat = CommonDateUtil.formatOnePoint(Math.abs(Float.valueOf(historyBean
                                .leftFatPersent)));
                    }
                    if ("--".equals(historyBean.leftWeight)) {
                        historyBean.strWeight = "0";
                    } else {
                        if (Float.valueOf(historyBean.leftWeight) > 0) {
                            historyBean.isUpWeight = true;
                        } else {
                            historyBean.isUpWeight = false;
                        }
                        historyBean.strWeight = CommonDateUtil.formatOnePoint(Math.abs(Float.valueOf
                                (historyBean.leftWeight)));
                    }
                    ArrayList<ScaleDayBean> dayList = historyBean.datalist;
                    if (dayList != null) {
                        for (int j = 0; j < dayList.size(); j++) {
                            ScaleDayBean dayBean = (ScaleDayBean) dayList.get(j);
                            //才分时间  日期和时间
                            if ("--".equals(dayBean.leftfatpersent)) {
                                dayBean.strBodyRate = "";
                            } else {
                                if (Float.valueOf(dayBean.leftfatpersent) < 0) {
                                    dayBean.isBodyRateUp = false;
                                    dayBean.strBodyRate = CommonDateUtil.formatOnePoint(Math.abs(Float.valueOf
                                            (dayBean.leftfatpersent)));

                                } else if (Float.valueOf(dayBean.leftfatpersent) > 0) {
                                    dayBean.isBodyRateUp = true;
                                    dayBean.strBodyRate = CommonDateUtil.formatOnePoint(Math.abs(Float.valueOf
                                            (dayBean.leftfatpersent)));
                                } else {
                                    dayBean.strBodyRate = "";
                                }
                            }

                            if ("--".equals(dayBean.leftweight)) {
                                dayBean.strWeightRate = "";
                            } else {
                                if (Float.valueOf(dayBean.leftweight) < 0) {
                                    dayBean.strWeightRate = CommonDateUtil.formatOnePoint(Math.abs(Float.valueOf
                                            (dayBean.leftweight)));
                                    dayBean.isWeightUp = false;

                                } else if (Float.valueOf(dayBean.leftweight) > 0) {
                                    dayBean.strWeightRate = CommonDateUtil.formatOnePoint(Math.abs(Float.valueOf
                                            (dayBean.leftweight)));
                                    dayBean.isWeightUp = true;
                                } else {
                                    dayBean.strWeightRate = "";
                                }
                            }
                            ArrayList<String> strings = DateUtils.getLongTimeStr(dayBean.creatTime);
                            dayBean.date = strings.get(0);
                            dayBean.time = strings.get(1);
                            Logger.myLog("dayBean.weight == " + dayBean.weight + " dayBean.fatpersent == " + dayBean
                                    .fatpersent);
                            dayBean.strWeight = dayBean.weight.contains("_") ? dayBean.weight.substring(0, dayBean
                                    .weight.indexOf("_")) : dayBean.weight;
                            dayBean.strBodyPresent = dayBean.fatpersent.contains("_") ? dayBean.fatpersent.substring
                                    (0, dayBean.fatpersent.indexOf
                                            ("_")) : dayBean.fatpersent;


                        }
                    }

                }
            }
        }

        return list;

          /*  if (deviceHomeData.getData() != null && deviceHomeData.getData().list != null) {
                mActView.get().successRefresh((ArrayList<ScaleHistoryBean>) deviceHomeData.getData().list,
                deviceHomeData.isIslastdata());
            }*/

    }

    public void getScaleHistoryModel(long timesTampServer, int requestCode, long timeTamp, String deviceId, String
            currentMonth, String deviceType, String mac, String
                                             time, String pageNum,
                                     String size) {
        if (timesTampServer == 0) {
            if (view != null) {
                view.successLoadMone(null, true, "", 0);
            }
        } else {
            MainResposition<ScaleHistroyNList, HistoryParm, BaseUrl, DeviceHistoryParms> mainResposition = new
                    MainResposition<>();
            mainResposition.requst(HistoryParmUtil.setHistory(timesTampServer, JkConfiguration.Url.FAT_STEELYARD,
                    JkConfiguration.Url
                            .HISTORY_DATA, requestCode, timeTamp, deviceId,
                    currentMonth, deviceType, mac, time, pageNum, size,
                    JkConfiguration.RequstType
                            .GETSCALEHISTORYLISTDATA))
                    .as(view.bindAutoDispose())
                    .subscribe(new BaseObserver<ScaleHistroyNList>(context) {
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
                        public void onNext(ScaleHistroyNList deviceHomeData) {
                            NetProgressObservable.getInstance().hide();
                            Logger.myLog("getFirstHistoryModel == " + deviceHomeData.time + " data == " + deviceHomeData
                                    .list.size());
                            if (deviceHomeData == null) {
                                //本地查询
                                if (view != null) {
                                    view.successLoadMone(null, true, "", 0);
                                }
                            } else {
                                //需要查询上一个月的第一条数据
                                //返回
                                BaseResponse<ScaleHistroyList> scaleHistroyListBaseResponse = new BaseResponse<>();
                                ScaleHistroyList scaleHistroyList = new ScaleHistroyList();
                                List<ScaleHistoryBean> scaleHistoryBeanList = new ArrayList<>();
                                ScaleHistoryBean scaleHistoryBean = new ScaleHistoryBean();
                                ArrayList<ScaleDayBean> datalist = new ArrayList<>();

                                if (deviceHomeData.list == null || (deviceHomeData.list != null && deviceHomeData.list
                                        .size() == 0)) {
                                    //返回没有数据的情况
                                    if (view != null) {
                                        view.successLoadMone(null, true, "", 0);
                                    }
                                } else {
                                    //有数据的情况
                                    List<ScaleHistoryNBean> scaleHistoryNBeanList = deviceHomeData.list;
                                    //查询第一条所处的月,并查询出当月数据
                                    ScaleHistoryNBean scaleHistoryNBean = scaleHistoryNBeanList.get(0);
                                    //有数据的最近的一个月的String
                                    String timeByyyyyMM1 = TimeUtils.getTimeByyyyyMMdd
                                            (Long.parseLong(scaleHistoryNBean.getTimestamp()));
                                    String[] split = timeByyyyyMM1.split("-");
                                    //拿一个月的数据
                                    scaleHistroyListBaseResponse.setCode(2000);
                                    scaleHistroyListBaseResponse.setIslastdata(deviceHomeData.time == 0 ? true : false);
                                    scaleHistoryBean.setMonthTitle(true);
                                    scaleHistoryBean.setMonth(split[0] + "-" + split[1]);
                                    if (scaleHistoryNBeanList.size() == 1) {
                                        //当月只有一条数据
                                        scaleHistoryBean.setLeftFatPersent("--");
                                        scaleHistoryBean.setLeftWeight("--");
                                    } else {
                                        //多条数据，比较第一条和最后一条
                                        ScaleHistoryNBean scaleHistoryNBeanFirst = scaleHistoryNBeanList
                                                .get(0);
                                        ScaleHistoryNBean scaleHistoryNBeanEnd = scaleHistoryNBeanList
                                                .get(scaleHistoryNBeanList.size() - 1);
                                        float weight = (float) scaleHistoryNBeanEnd.getBodyWeight();
                                        float weightLast = (float) scaleHistoryNBeanFirst.getBodyWeight();
                                        float leftWeight = weight - weightLast;
                                        if (leftWeight != 0) {
                                            scaleHistoryBean.setLeftWeight(leftWeight + "");
                                        } else {
                                            scaleHistoryBean.setLeftWeight("--");
                                        }
                                        double bfp = scaleHistoryNBeanEnd.getBfpBodyFatPercent();
                                        double bfpLast = scaleHistoryNBeanFirst.getBfpBodyFatPercent();
                                        double leftNFP = bfp - bfpLast;
                                        if (leftNFP != 0) {
                                            scaleHistoryBean.setLeftFatPersent(leftNFP + "");
                                        } else {
                                            scaleHistoryBean.setLeftFatPersent("--");
                                        }
                                    }
                                    for (int i = scaleHistoryNBeanList.size() - 1; i >= 0; i--) {
                                        //数据要存储要更新到本地
                                        UserInfoBean bean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance()
                                                .getPeopleIdStr(BaseApp.getApp
                                                        ()));
                                        Scale_FourElectrode_DataModel scale_fourElectrode_dataModel =
                                                getScale_fourElectrode_dataModel(scaleHistoryNBeanList.get(i), bean);
                                        Scale_FourElectrode_DataModel scaleFourElectrodeDataModelByDeviceIdAndTimeTamp =
                                                Scale_FourElectrode_DataModelAction
                                                        .findScaleFourElectrodeDataModelByDeviceIdAndTimeTamp
                                                                (scale_fourElectrode_dataModel.getTimestamp(), TokenUtil
                                                                        .getInstance().getPeopleIdStr
                                                                                (BaseApp.getApp()));
                                        //查询本地数据库，如果没有，则新增
                                        if (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp == null) {
                                            ParseData.saveScaleFourElectrodeData(scale_fourElectrode_dataModel);
                                        }
                                        ScaleHistoryNBean scaleHistoryNBeanFirst = scaleHistoryNBeanList
                                                .get(i);
                                        ScaleDayBean scaleDayBean = new ScaleDayBean();
                                        float weight = (float) scaleHistoryNBeanFirst.getBodyWeight();
                                        double bfp = scaleHistoryNBeanFirst.getBfpBodyFatPercent();
                                        scaleDayBean.weight = weight + "";
                                        scaleDayBean.fatpersent = bfp + "";
                                        //和上次比较的值
                                        if (i - 1 >= 0) {
                                            //存在下一条数据
                                            ScaleHistoryNBean scaleHistoryNBeanLast = scaleHistoryNBeanList
                                                    .get(i - 1);
                                            float weightLast = (float) scaleHistoryNBeanLast.getBodyWeight();
                                            float leftWeight = weight - weightLast;
                                            if (leftWeight != 0) {
                                                scaleDayBean.leftweight = leftWeight + "";
                                            } else {
                                                scaleDayBean.leftweight = "--";
                                            }
                                            double bfpLast = scaleHistoryNBeanLast.getBfpBodyFatPercent();
                                            double leftNFP = bfp - bfpLast;
                                            if (leftNFP != 0) {
                                                scaleDayBean.leftfatpersent = leftNFP + "";
                                            } else {
                                                scaleDayBean.leftfatpersent = "--";
                                            }
                                        } else {
                                            scaleDayBean.leftweight = "--";
                                            scaleDayBean.leftfatpersent = "--";
                                        }
                                        scaleDayBean.creatTime = Long.parseLong(scaleHistoryNBeanFirst.getTimestamp());
                                        datalist.add(scaleDayBean);
                                    }
                                }
                                scaleHistoryBean.setDatalist(datalist);
                                scaleHistoryBeanList.add(scaleHistoryBean);
                                scaleHistroyList.mNextMonth = "";
                                scaleHistroyList.mNextTimeTamp = deviceHomeData.time;
                                scaleHistroyList.list = scaleHistoryBeanList;
                                Logger.myLog("Request_getScaleHistoryData MONTH == " + scaleHistoryBean.toString());
                                scaleHistroyListBaseResponse.setData(scaleHistroyList);
                                ArrayList<ScaleHistoryBean> scaleHistoryBeans = (ArrayList<ScaleHistoryBean>) parseData
                                        (scaleHistroyListBaseResponse);
                                ScaleHistroyList data = scaleHistroyListBaseResponse.getData();
                                if (scaleHistoryBeans != null) {
                                    Logger.myLog("Request_getScaleHistoryData 请求完成 == " + data
                                            .mNextMonth);
                                    if (view != null) {
                                        view.successLoadMone(scaleHistoryBeans, scaleHistroyListBaseResponse
                                                .isIslastdata(), data
                                                .mNextMonth, data.mNextTimeTamp);
                                    }
                                } else {
                                    Logger.myLog("Request_getScaleHistoryData 请求完成 == 为null");
                                }
                            }
                        }
                    });
        }
    }
}
