package com.isport.brandapp.util;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.isport.blelibrary.db.CommonInterFace.WatchData;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_24HDataModelAction;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_DataModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_24HDataModel;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.ScaleHistoryData;
import com.isport.brandapp.Home.bean.http.SleepHistoryData;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.band.bean.BandDayBean;
import com.isport.brandapp.device.history.HistoryTilteBean;
import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.device.scale.bean.ScaleBMIResultBean;
import com.isport.brandapp.device.scale.bean.ScaleHistoryBean;
import com.isport.brandapp.device.sleep.bean.SleepHistoryList;
import com.isport.brandapp.device.sleep.bean.SleepHistoryNBean;
import com.isport.brandapp.device.watch.bean.WatchHistoryNBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class DeviceDataUtil {

    public static float calIBMValue(float weight, float height) {
        //体质指数（BMI）=体重（kg）÷ 身高^2（m）

        float fIBMValue = weight / (height / 100 * height / 100);
        return fIBMValue;
    }


    public static ScaleBMIResultBean calReslut(float IBMValue) {
        //阶段划分 < 18.5 偏瘦
        //
        //               >= 18.5 且 < 24.0  标准
        //
        //               >= 24.0 且 < 28.0  偏胖
        //
        //               >= 28.0 且 < 30.0  肥胖
        //
        //               >= 30.0 且  重度肥胖


        String result = "";

     /*   switch (result) {
            case JkConfiguration.BodyType.CHUBBY:
                ivResult.setImageResource(R.drawable.icon_body_chubby);
                break;
            case JkConfiguration.BodyType.STANDARD:
                ivResult.setImageResource(R.drawable.icon_body_standards);
                break;
            case JkConfiguration.BodyType.FAT:
                ivResult.setImageResource(R.drawable.icon_body_fat);
                break;
            case JkConfiguration.BodyType.THIN:
                ivResult.setImageResource(R.drawable.icon_body_thin);
                break;
            case JkConfiguration.BodyType.VERY_FAT:
                ivResult.setImageResource(R.drawable.icon_body_very_fat);
                break;
                */
        int res = R.drawable.icon_body_standards;

        if (IBMValue < 18.5) {

            result = UIUtils.getString(R.string.thin);
            res = R.drawable.icon_body_thin;

        } else if (IBMValue >= 18.5 && IBMValue < 24) {
            /**
             * String STANDARD = "标准";
             String FAT = "偏胖";
             String THIN = "偏瘦";
             String CHUBBY = "肥胖";
             String VERY_FAT = "重度";
             */


            result = UIUtils.getString(R.string.standard);
            res = R.drawable.icon_body_standards;

        } else if (IBMValue >= 24.0 && IBMValue < 28.0) {
            result = UIUtils.getString(R.string.chubby);
            res = R.drawable.icon_body_chubby;

        } else if (IBMValue >= 28.0 && IBMValue < 30.0) {
            result = UIUtils.getString(R.string.obesity);
            res = R.drawable.icon_body_fat;


        } else if (IBMValue >= 30.0) {
            result = UIUtils.getString(R.string.severe_obesity);
            res = R.drawable.icon_body_very_fat;
        }

        ScaleBMIResultBean bmiResultBean = new ScaleBMIResultBean();
        bmiResultBean.res = res;
        bmiResultBean.result = result;

        return bmiResultBean;


    }

    public static ArrayList<HistoryBeanList> parseBandData(ArrayList<BandDayBean> list) {

        ArrayList<HistoryBeanList> lists = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                HistoryBeanList historyBeanList = new HistoryBeanList();
                historyBeanList.bandDayBean = list.get(i);
                historyBeanList.viewType = JkConfiguration.HistoryType.TYPE_CONTENT;
                historyBeanList.DeviceTpye = JkConfiguration.DeviceType.WATCH_W516;
                lists.add(historyBeanList);
            }
        }
        return lists;

    }

    public static ArrayList<HistoryBeanList> parseScaleData(ScaleHistoryBean bean) {


        ArrayList<HistoryBeanList> list = new ArrayList<>();


        HistoryBeanList scaleHistory = new HistoryBeanList();
        //月头部
        scaleHistory.viewType = JkConfiguration.HistoryType.TYPE_MONTH;
        scaleHistory.DeviceTpye = JkConfiguration.DeviceType.BODYFAT;
        scaleHistory.scaleHistoryBean = bean;
        list.add(scaleHistory);
        //月数据部分
        if (bean.datalist != null) {
            for (int i = 0; i < bean.datalist.size(); i++) {
                HistoryBeanList scaleHistorySon = new HistoryBeanList();
                scaleHistorySon.viewType = JkConfiguration.HistoryType.TYPE_CONTENT;
                scaleHistorySon.DeviceTpye = JkConfiguration.DeviceType.BODYFAT;
                scaleHistorySon.scaleDayBean = bean.datalist.get(i);
                list.add(scaleHistorySon);
            }
        }
        return list;


    }

    public static ArrayList<HistoryBeanList> parseSleepData(ArrayList<SleepHistoryList> historyBean, boolean isFirst) {


        ArrayList<HistoryBeanList> list = new ArrayList<>();

        if (isFirst) {
            HistoryBeanList title = new HistoryBeanList();
            title.tilteBean = new HistoryTilteBean();
            title.tilteBean.currentType = JkConfiguration.DeviceType.SLEEP;
            title.viewType = JkConfiguration.HistoryType.TYPE_TITLE;
            title.tilteBean.one = R.string.time;
            title.tilteBean.two = R.string.deep_sleep_time_total_time;
            title.tilteBean.three = R.string.app_sleep_histroy_avg_hr;
            title.tilteBean.four = R.string.app_sleep_histroy_avg_breath;
            title.tilteBean.five = R.string.app_sleep_histroy_deep_infant;
            title.DeviceTpye = JkConfiguration.DeviceType.SLEEP;
            list.add(title);
        } else {
           /* HistoryBeanList divider = new HistoryBeanList();
            divider.viewType = JkConfiguration.HistoryType.TYPE_DIVIDER;
            list.add(divider);*/
        }

        for (int j = 0; j < historyBean.size(); j++) {
            SleepHistoryList historyList = historyBean.get(j);
            HistoryBeanList scaleHistory = new HistoryBeanList();
            scaleHistory.viewType = JkConfiguration.HistoryType.TYPE_MONTH;
            scaleHistory.DeviceTpye = JkConfiguration.DeviceType.SLEEP;
            scaleHistory.sleepHistoryBean = historyList;
            //scaleHistory.sleepDayBean = (SleepDayBean) historyList.getDatalist();
            list.add(scaleHistory);
            if (historyList.getDatalist() != null) {
                for (int i = 0; i < historyList.getDatalist().size(); i++) {
                    HistoryBeanList scaleHistorySon = new HistoryBeanList();
                    scaleHistorySon.viewType = JkConfiguration.HistoryType.TYPE_CONTENT;
                    scaleHistorySon.DeviceTpye = JkConfiguration.DeviceType.SLEEP;
                    scaleHistorySon.sleepDayBean = historyList.getDatalist().get(i);
                    list.add(scaleHistorySon);
                }
            }
            if (j < historyBean.size() - 1) {
              /*  HistoryBeanList divider = new HistoryBeanList();
                divider.viewType = JkConfiguration.HistoryType.TYPE_DIVIDER;
                list.add(divider);*/
            }
        }

        return list;


    }


    public static float getDeviceGoalValue(int currentType) {
        float goalValue;
        if (currentType == JkConfiguration.DeviceType.WATCH_W516) {
            goalValue = JkConfiguration.WATCH_GOAL;
        } else if (currentType == JkConfiguration.DeviceType.SLEEP) {
            goalValue = JkConfiguration.SLEEP_GOAL;
        } else {
            goalValue = JkConfiguration.BODYFAT_GOAL;
        }

        return goalValue;
    }

    @NonNull
    public static Scale_FourElectrode_DataModel getScale_fourElectrode_dataModel(ScaleHistoryData.ScaleList scaleList,
                                                                                 UserInfoBean bean) {
        Scale_FourElectrode_DataModel scale_fourElectrode_dataModel;
        scale_fourElectrode_dataModel = new Scale_FourElectrode_DataModel();
        scale_fourElectrode_dataModel.setDeviceId(scaleList.getDeviceId());
        scale_fourElectrode_dataModel.setReportId(scaleList.getFatsteelyardTargetId());
        scale_fourElectrode_dataModel.setUserId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()
        ));
        scale_fourElectrode_dataModel.setDateStr(scaleList.getDateStr());
        scale_fourElectrode_dataModel.setTimestamp(Long.parseLong(scaleList.getTimestamp()));
        scale_fourElectrode_dataModel.setSex(bean.getGender().equals("Male") ? 0 : 1);
        scale_fourElectrode_dataModel.setHight((int) Float.parseFloat(bean.getHeight()));
        scale_fourElectrode_dataModel.setAge(TimeUtils.getAge(bean.getBirthday()));
        scale_fourElectrode_dataModel.setR(2000);//暂设置2000
        scale_fourElectrode_dataModel.setWeight(scaleList.getBodyWeight());
        scale_fourElectrode_dataModel.setBFP(scaleList.getBfpBodyFatPercent());
        scale_fourElectrode_dataModel.setSLM(scaleList.getSlmMuscleWeight());
        scale_fourElectrode_dataModel.setBWP(scaleList.getBwpBodyWeightPercent());//水含量
        scale_fourElectrode_dataModel.setBMC(scaleList.getBmcBoneMineralContent());
        scale_fourElectrode_dataModel.setVFR(scaleList.getVfrVisceralFatRating());
        scale_fourElectrode_dataModel.setPP(scaleList.getPpProteinPercent());
        scale_fourElectrode_dataModel.setSMM(scaleList.getSmmSkeletalMuscleMass());
        scale_fourElectrode_dataModel.setBMR(scaleList.getBmrBasalMetabolicRate());
        scale_fourElectrode_dataModel.setBMI(scaleList.getBmi());
        scale_fourElectrode_dataModel.setSBW(scaleList.getSlmMuscleWeight());
        scale_fourElectrode_dataModel.setMC(scaleList.getMcMuscleControl());
        scale_fourElectrode_dataModel.setWC(scaleList.getWcWeightControl());
        scale_fourElectrode_dataModel.setFC(scaleList.getFcFatControl());
        scale_fourElectrode_dataModel.setMA((int) scaleList.getMaBodyAge());
        scale_fourElectrode_dataModel.setSBC((int) scaleList.getSbcIndividualScore());
        return scale_fourElectrode_dataModel;
    }

    @NonNull
    public static Sleep_Sleepace_DataModel getSleep_Sleepace_DataModel(SleepHistoryData.SleepList sleepList) {
        Sleep_Sleepace_DataModel sleep_sleepace_dataModel;
        sleep_sleepace_dataModel = new Sleep_Sleepace_DataModel();

        sleep_sleepace_dataModel.setDeviceId(sleepList.getDeviceId());
        sleep_sleepace_dataModel.setReportId(sleepList.getSleepbeltTargetId());
        sleep_sleepace_dataModel.setUserId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()
        ));
        sleep_sleepace_dataModel.setDateStr(sleepList.getDateStr());
        sleep_sleepace_dataModel.setTimestamp(Long.parseLong(sleepList.getTimestamp()) / 1000);
        sleep_sleepace_dataModel.setTrunOverStatusAry(sleepList.getTrunOverStatusAry());
        sleep_sleepace_dataModel.setBreathRateAry(sleepList.getBreathRateValueArray());
        sleep_sleepace_dataModel.setHeartRateAry(sleepList.getHeartBeatRateValueArray());
        sleep_sleepace_dataModel.setAverageBreathRate(sleepList.getAverageBreathRate());
        sleep_sleepace_dataModel.setAverageHeartBeatRate(sleepList.getAverageHeartBeatRate());
        sleep_sleepace_dataModel.setLeaveBedTimes(sleepList.getLeaveBedTimes());
        sleep_sleepace_dataModel.setTurnOverTimes(sleepList.getTurnOverTimes());
        sleep_sleepace_dataModel.setBodyMovementTimes(sleepList.getBodyMovementTimes());
        sleep_sleepace_dataModel.setHeartBeatPauseTimes(sleepList.getHeartBeatPauseTimes());
        sleep_sleepace_dataModel.setBreathPauseTimes(sleepList.getBreathPauseTimes());
        sleep_sleepace_dataModel.setHeartBeatPauseAllTime(sleepList.getHeartBeatPauseAllTime());
        sleep_sleepace_dataModel.setBreathPauseAllTime(sleepList.getBreathPauseAllTime());
        sleep_sleepace_dataModel.setLeaveBedAllTime(sleepList.getLeaveBedAllTime());
        sleep_sleepace_dataModel.setMaxHeartBeatRate(sleepList.getMaxHeartBeatRate());
        sleep_sleepace_dataModel.setMinHeartBeatRate(sleepList.getMinHeartBeatRate());
        sleep_sleepace_dataModel.setMaxBreathRate(sleepList.getMaxBreathRate());
        sleep_sleepace_dataModel.setMinBreathRate(sleepList.getMinBreathRate());
        sleep_sleepace_dataModel.setHeartBeatRateFastAllTime(sleepList.getHeartBeatRateFastAllTime());
        sleep_sleepace_dataModel.setHeartBeatRateSlowAllTime(sleepList.getHeartBeatRateSlowAllTime());
        sleep_sleepace_dataModel.setBreathRateFastAllTime(sleepList.getBreathRateFastAllTime());
        sleep_sleepace_dataModel.setBreathRateSlowAllTime(sleepList.getBreathRateSlowAllTime());
        sleep_sleepace_dataModel.setFallAlseepAllTime(sleepList.getFallSleepAllTime());
        sleep_sleepace_dataModel.setSleepDuration(sleepList.getSleepDuration());
        sleep_sleepace_dataModel.setDeepSleepAllTime(sleepList.getDeepSleepDuration());
        sleep_sleepace_dataModel.setDeepSleepPercent(sleepList.getDeepSleepPercent());
        return sleep_sleepace_dataModel;
    }

    @NonNull
    public static Sleep_Sleepace_DataModel getSleep_Sleepace_DataModel1(SleepHistoryNBean sleepHistoryNBean) {
        Sleep_Sleepace_DataModel sleep_sleepace_dataModel;
        sleep_sleepace_dataModel = new Sleep_Sleepace_DataModel();

        sleep_sleepace_dataModel.setDeviceId(sleepHistoryNBean.getDeviceId());
        sleep_sleepace_dataModel.setReportId(sleepHistoryNBean.getSleepbeltTargetId());
        sleep_sleepace_dataModel.setUserId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()
        ));
        sleep_sleepace_dataModel.setDateStr(sleepHistoryNBean.getDateStr());
        sleep_sleepace_dataModel.setTimestamp(Long.parseLong(sleepHistoryNBean.getTimestamp()) / 1000);
        sleep_sleepace_dataModel.setTrunOverStatusAry(sleepHistoryNBean.getTrunOverStatusAry());
        sleep_sleepace_dataModel.setBreathRateAry(sleepHistoryNBean.getBreathRateValueArray());
        sleep_sleepace_dataModel.setHeartRateAry(sleepHistoryNBean.getHeartBeatRateValueArray());
        sleep_sleepace_dataModel.setAverageBreathRate(sleepHistoryNBean.getAverageBreathRate());
        sleep_sleepace_dataModel.setAverageHeartBeatRate(sleepHistoryNBean.getAverageHeartBeatRate());
        sleep_sleepace_dataModel.setLeaveBedTimes(sleepHistoryNBean.getLeaveBedTimes());
        sleep_sleepace_dataModel.setTurnOverTimes(sleepHistoryNBean.getTurnOverTimes());
        sleep_sleepace_dataModel.setBodyMovementTimes(sleepHistoryNBean.getBodyMovementTimes());
        sleep_sleepace_dataModel.setHeartBeatPauseTimes(sleepHistoryNBean.getHeartBeatPauseTimes());
        sleep_sleepace_dataModel.setBreathPauseTimes(sleepHistoryNBean.getBreathPauseTimes());
        sleep_sleepace_dataModel.setBreathPauseAllTime(sleepHistoryNBean.getBreathPauseAllTime());
        sleep_sleepace_dataModel.setHeartBeatPauseAllTime(sleepHistoryNBean.getHeartBeatPauseAllTime());
        sleep_sleepace_dataModel.setLeaveBedAllTime(sleepHistoryNBean.getLeaveBedAllTime());
        sleep_sleepace_dataModel.setMaxHeartBeatRate(sleepHistoryNBean.getMaxHeartBeatRate());
        sleep_sleepace_dataModel.setMinHeartBeatRate(sleepHistoryNBean.getMinHeartBeatRate());
        sleep_sleepace_dataModel.setMaxBreathRate(sleepHistoryNBean.getMaxBreathRate());
        sleep_sleepace_dataModel.setMinBreathRate(sleepHistoryNBean.getMinBreathRate());
        sleep_sleepace_dataModel.setHeartBeatRateFastAllTime(sleepHistoryNBean.getHeartBeatRateFastAllTime());
        sleep_sleepace_dataModel.setHeartBeatRateSlowAllTime(sleepHistoryNBean.getHeartBeatRateSlowAllTime());
        sleep_sleepace_dataModel.setBreathRateFastAllTime(sleepHistoryNBean.getBreathRateFastAllTime());
        sleep_sleepace_dataModel.setBreathRateSlowAllTime(sleepHistoryNBean.getBreathRateSlowAllTime());
        sleep_sleepace_dataModel.setFallAlseepAllTime(sleepHistoryNBean.getFallSleepAllTime());
        sleep_sleepace_dataModel.setSleepDuration(sleepHistoryNBean.getSleepDuration());
        sleep_sleepace_dataModel.setDeepSleepAllTime(sleepHistoryNBean.getDeepSleepDuration());
        sleep_sleepace_dataModel.setDeepSleepPercent(sleepHistoryNBean.getDeepSleepPercent());
        return sleep_sleepace_dataModel;
    }

    /**
     * (0步数，1心率，2睡眠)
     *
     * @param watchHistoryNBean
     * @param dataType
     * @return
     */
    @NonNull
    public static synchronized void getWatch_W516_24HDataModel(WatchHistoryNBean watchHistoryNBean, int dataType) {

        //因为做了步数，心率，睡眠的区分，需要查询数据库是否存在，存在则存储另外另个
        //DeviceBean deviceBean = AppConfiguration.deviceBeanList.get(JkConfiguration.DeviceType.WATCH_W516);
        // if (deviceBean != null) {
        //只存储当前设备Id的数据
        Logger.myLog("updateWatch_W516_24HDataModel ,watchHistoryNBean.getTotalCalories()=" + watchHistoryNBean.getTotalCalories() + ",watchHistoryNBean.getTotalDistance()=" + watchHistoryNBean.getTotalDistance());
        if (AppConfiguration.braceletID.equals(watchHistoryNBean.getDeviceId())) {
            Watch_W516_24HDataModel watch_w516_24HDataModel = new Watch_W516_24HDataModel();
            Watch_W516_24HDataModel watch_w516_watch_w516_24HDataModelByDeviceId = Watch_W516_24HDataModelAction.findWatch_W516_Watch_W516_24HDataModelByDeviceId(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), watchHistoryNBean.getDateStr(), AppConfiguration.braceletID);
            if (watch_w516_watch_w516_24HDataModelByDeviceId == null) {
                //没有存储过，直接存储
                watch_w516_24HDataModel.setUserId(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));
                watch_w516_24HDataModel.setDeviceId(AppConfiguration.braceletID);
                watch_w516_24HDataModel.setTimestamp(System.currentTimeMillis());
                watch_w516_24HDataModel.setReportId(watchHistoryNBean.getWristbandSportDetailId());
                watch_w516_24HDataModel.setDateStr(watchHistoryNBean.getDateStr());
                watch_w516_24HDataModel.setTotalSteps(watchHistoryNBean.getTotalSteps());
                watch_w516_24HDataModel.setTotalCalories(TextUtils.isEmpty(watchHistoryNBean.getTotalCalories()) ? 0 : Integer.parseInt(watchHistoryNBean.getTotalCalories()));
                watch_w516_24HDataModel.setTotalDistance(TextUtils.isEmpty(watchHistoryNBean.getTotalDistance()) ? 0 : Float.parseFloat(watchHistoryNBean.getTotalDistance()));
                watch_w516_24HDataModel.setStepArray(dataType == JkConfiguration.WatchDataType.STEP ? watchHistoryNBean.getStepDetailArray() : "");
                watch_w516_24HDataModel.setSleepArray(dataType == JkConfiguration.WatchDataType.SLEEP ? watchHistoryNBean.getSleepDetailArray() : "");
                watch_w516_24HDataModel.setHrArray(dataType == JkConfiguration.WatchDataType.HEARTRATE ? watchHistoryNBean.getHeartRateDetailArray() : "");
                setSleepAndHrState(watchHistoryNBean, dataType, watch_w516_24HDataModel);
                ParseData.saveWatch_W516_24HDataModel(watch_w516_24HDataModel);
            } else {
                //存储过，更新

                watch_w516_watch_w516_24HDataModelByDeviceId.setReportId(watchHistoryNBean.getWristbandSportDetailId());
                Calendar calendar = Calendar.getInstance();
                boolean iscurrentDay = watchHistoryNBean.getDateStr().equals(getTimeByyyyyMMddhhmmss(calendar.getTimeInMillis()));

                //如果是当天的数据不需要去存储了

                long step = watchHistoryNBean.getTotalSteps();
                long cal = TextUtils.isEmpty(watchHistoryNBean.getTotalCalories()) ? 0 : Integer.parseInt(watchHistoryNBean.getTotalCalories());
                float dis = TextUtils.isEmpty(watchHistoryNBean.getTotalDistance()) ? 0 : Float.parseFloat(watchHistoryNBean.getTotalDistance());
                if (iscurrentDay) {
                    if (watch_w516_watch_w516_24HDataModelByDeviceId.getTotalSteps() < step) {
                        watch_w516_watch_w516_24HDataModelByDeviceId.setTotalSteps(watchHistoryNBean.getTotalSteps());
                    }
                    if (watch_w516_watch_w516_24HDataModelByDeviceId.getTotalDistance() < dis) {
                        watch_w516_watch_w516_24HDataModelByDeviceId.setTotalDistance(dis);
                    }
                    if (watch_w516_watch_w516_24HDataModelByDeviceId.getTotalCalories() < cal) {
                        watch_w516_watch_w516_24HDataModelByDeviceId.setTotalCalories(cal);
                    }

                } else {
                    watch_w516_watch_w516_24HDataModelByDeviceId.setTotalSteps(watchHistoryNBean.getTotalSteps());
                    watch_w516_watch_w516_24HDataModelByDeviceId.setTotalCalories(cal);
                    watch_w516_watch_w516_24HDataModelByDeviceId.setTotalDistance(dis);
                }

                switch (dataType) {
                    case JkConfiguration.WatchDataType.STEP:
                        if (iscurrentDay) {

                            if (TextUtils.isEmpty(watch_w516_watch_w516_24HDataModelByDeviceId.getStepArray())) {
                                watch_w516_watch_w516_24HDataModelByDeviceId.setStepArray(watchHistoryNBean.getStepDetailArray());
                            }
                        } else {
                            watch_w516_watch_w516_24HDataModelByDeviceId.setStepArray(watchHistoryNBean.getStepDetailArray());
                        }
                        break;
                    case JkConfiguration.WatchDataType.HEARTRATE:
                        if (iscurrentDay) {
                            if (TextUtils.isEmpty(watch_w516_watch_w516_24HDataModelByDeviceId.getHrArray())) {
                                watch_w516_watch_w516_24HDataModelByDeviceId.setHrArray(watchHistoryNBean.getHeartRateDetailArray());
                            }
                        } else {
                            watch_w516_watch_w516_24HDataModelByDeviceId.setHrArray(watchHistoryNBean.getHeartRateDetailArray());
                        }
                        break;
                    case JkConfiguration.WatchDataType.SLEEP:
                        if (iscurrentDay) {
                            if (TextUtils.isEmpty(watch_w516_watch_w516_24HDataModelByDeviceId.getSleepArray())) {
                                watch_w516_watch_w516_24HDataModelByDeviceId.setSleepArray(watchHistoryNBean.getSleepDetailArray());
                            }

                        } else {
                            watch_w516_watch_w516_24HDataModelByDeviceId.setSleepArray(watchHistoryNBean.getSleepDetailArray());
                        }
                        break;
                }
                setSleepAndHrState(watchHistoryNBean, dataType, watch_w516_watch_w516_24HDataModelByDeviceId);
                ParseData.updateWatch_W516_24HDataModel(watch_w516_watch_w516_24HDataModelByDeviceId);
            }
        }
        //  }
    }

    public static String getTimeByyyyyMMddhhmmss(Long time) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = new Date(time);
        return sdFormat.format(date2);
    }

    /**
     * 对心率和睡眠数据做处理
     *
     * @param watchHistoryNBean
     * @param dataType
     * @param watch_w516_24HDataModel
     */
    private static void setSleepAndHrState(WatchHistoryNBean watchHistoryNBean, int dataType, Watch_W516_24HDataModel watch_w516_24HDataModel) {
        if (dataType == JkConfiguration.WatchDataType.HEARTRATE) {
            String heartRateDetailArray = watch_w516_24HDataModel.getHrArray();
            Integer[] hrArry = new Gson().fromJson(heartRateDetailArray, Integer[].class);
            if (hrArry == null) {
                watch_w516_24HDataModel.setHasHR(WatchData.NO_HR);
                watch_w516_24HDataModel.setAvgHR(0);
            } else {
                watch_w516_24HDataModel.setHasHR(WatchData.NO_HR);
                ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(hrArry));
                int validCount = 0, validSum = 0;
                for (int i = 0; i < arrayList.size(); i++) {
                    int integer = arrayList.get(i);
                    if (integer != 0) {
                        if (integer >= 30) {
                            watch_w516_24HDataModel.setHasHR(WatchData.HAS_HR);
                        }
                        if (integer >= 30) {
                            validCount++;
                            validSum += arrayList.get(i);
                        }
                    }
                }
                watch_w516_24HDataModel.setAvgHR(validCount == 0 ? 0 : Math.round(validSum / (float) validCount));
            }

        }

        if (dataType == JkConfiguration.WatchDataType.SLEEP) {
            String sleepDetailArray = watch_w516_24HDataModel.getSleepArray();
            Integer[] sleepArry = new Gson().fromJson(sleepDetailArray, Integer[].class);
            if (sleepArry == null) {
                watch_w516_24HDataModel.setHasSleep(WatchData.NO_SLEEP);
            } else {
                if (sleepDetailArray.contains("250") || sleepDetailArray.contains("251") || sleepDetailArray.contains("252") || sleepDetailArray.contains("253")) {
                    watch_w516_24HDataModel.setHasSleep(WatchData.HAS_SLEEP);
                }
            }
        }
    }

    public static DeviceBean getLastBindDevice() {
        long timeTamp = 0;
        DeviceBean resultDeviceBean = null;
        if (AppConfiguration.deviceBeanList.size() == 1) {
            for (int deviceTypeI : AppConfiguration.deviceBeanList.keySet()) {
                DeviceBean deviceBean = AppConfiguration.deviceBeanList.get(deviceTypeI);
                // TODO: 2019/1/28 单机版也要暂存储的为0，应该也需要排序
                if (deviceBean.timeTamp >= timeTamp) {
                    timeTamp = deviceBean.timeTamp;
                    resultDeviceBean = deviceBean;
                }
            }
        } else {
            for (int deviceTypeI : AppConfiguration.deviceBeanList.keySet()) {
                DeviceBean deviceBean = AppConfiguration.deviceBeanList.get(deviceTypeI);
                // TODO: 2019/1/28 单机版也要暂存储的为0，应该也需要排序
                if (deviceBean.deviceType == JkConfiguration.DeviceType.BODYFAT) {
                    continue;
                }
                if (deviceBean.timeTamp >= timeTamp) {
                    timeTamp = deviceBean.timeTamp;
                    resultDeviceBean = deviceBean;
                }
            }
        }
        return resultDeviceBean;
    }

    public static boolean isSame(int startHour, int startMin, int endHour, int endMin) {
        if (startHour == endHour && startMin == endMin) {
            return true;
        } else {
            return false;
        }
    }


    //如果开始时间+间隔小于结束时间是正常的
    public static boolean settingAbleTime(int startHour, int startMin, int endHour, int endMin, int pertion) {

        if (startHour * 60 + startMin + pertion <= endHour * 60 + endMin) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean comHour(int startHour, int startMin, int endHour, int endMin) {


        if (startHour * 60 + startMin < endHour * 60 + endMin) {
            return true;
        } else {
            return false;
        }

        /*if (startHour > endHour) {
            return false;
        } else if (startHour == endHour) {
            if (startMin >= endMin) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }*/

    }

    public static boolean comHourSame(int startHour, int startMin, int endHour, int endMin) {


        if (startHour * 60 + startMin == endHour * 60 + endMin) {
            return true;
        } else {
            return false;
        }

        /*if (startHour > endHour) {
            return false;
        } else if (startHour == endHour) {
            if (startMin >= endMin) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }*/

    }

}
