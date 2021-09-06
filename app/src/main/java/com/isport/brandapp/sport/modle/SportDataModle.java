package com.isport.brandapp.sport.modle;

import com.isport.brandapp.Home.fragment.LatLongData;
import com.isport.brandapp.sport.bean.HrBean;
import com.isport.brandapp.sport.bean.IndoorRunDatas;
import com.isport.brandapp.sport.bean.PaceBean;
import com.isport.brandapp.sport.bean.SportDetailData;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.sport.bean.runargs.ArgsForInRunService;
import com.isport.brandapp.util.StepsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.action.SportDataAction;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import com.isport.blelibrary.utils.CommonDateUtil;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.entry.SportBean;

public class SportDataModle {


    public long saveSportData(ArgsForInRunService argsForInRunService, IndoorRunDatas indoorRunDatas, int sportType) {

        SportBean bean = new SportBean();
        bean.setStartTimestamp(argsForInRunService.phoneStartTime);
        bean.setEndTimestamp(System.currentTimeMillis());
        bean.setSporttype(sportType);

        bean.setUserId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
        if (sportType == JkConfiguration.SportType.sportBike) {
            bean.setStep("0");
        } else if (sportType == JkConfiguration.SportType.sportIndoor) {
            bean.setStep(indoorRunDatas.totalStep + "");
        } else {
            //如果步数相差太大就用距离转换成步数
            if (indoorRunDatas.totalStep - indoorRunDatas.disToStep > 500) {
                bean.setStep(indoorRunDatas.totalStep + "");
            } else {
                bean.setStep(indoorRunDatas.totalStep + "");
            }
        }
        bean.setTimeLong((int) indoorRunDatas.timer);

        /*indoorRunDatas.heartRateMap.put(56l, 88);
        indoorRunDatas.heartRateMap.put(59l, 87);
        indoorRunDatas.heartRateMap.put(60l, 90);*/
        if (indoorRunDatas.heartRateMap.size() == 0) {
            bean.setHeartRateArr("");
            bean.setMinHeartRate("0");
            bean.setMaxHeartRate("0");
            bean.setAvgHeartRate("0");

        } else {
            List<HrBean> mapKeyList = new ArrayList<HrBean>(indoorRunDatas.heartRateMap.values());
            bean.setMinHeartRate(Collections.max(mapKeyList).getHeartRate() + "");
            bean.setMaxHeartRate(Collections.min(mapKeyList).getHeartRate() + "");
            bean.setHeartRateArr(mapKeyList.toString());

            int sumHrBean = 0;
            for (int i = 0; i < mapKeyList.size(); i++) {
                sumHrBean += mapKeyList.get(i).getHeartRate();
            }
            int avg = sumHrBean / mapKeyList.size();
            bean.setAvgHeartRate(avg + "");
        }


        if (indoorRunDatas.paceBean.size() == 0) {
            bean.setMaxPace(JkConfiguration.strPace);
            bean.setMinPace(JkConfiguration.strPace);
            bean.setPaceArr("");

        } else {
            List<PaceBean> mapKeyList = new ArrayList<PaceBean>(indoorRunDatas.paceBean.values());
            PaceBean paceBeanMax = Collections.max(mapKeyList);
            PaceBean paceBeanMin = Collections.min(mapKeyList);
            bean.setMaxPace(paceBeanMin.getPace());
            bean.setMinPace(paceBeanMax.getPace());
            bean.setPaceArr(mapKeyList.toString());
            Logger.e(bean.getPaceArr());
        }
        if (indoorRunDatas.distance < 0.02) {
            bean.setAvgPace(JkConfiguration.strPace);
            bean.setAvgSpeed("0");
        } else {

            double avgSpeed = indoorRunDatas.distance * 1000 / (indoorRunDatas.timer) * 3.6;

            bean.setAvgSpeed(CommonDateUtil.formatOnePoint(avgSpeed));
            //bean.setAvgSpeed(StepsUtils.calPaceBean(avgSpeed).getPace());

            PaceBean paceBean = StepsUtils.calPaceBean(60 / avgSpeed, 0);
            if (paceBean != null) {
                bean.setAvgPace(paceBean.getPace());
            } else {
                bean.setAvgPace(JkConfiguration.strPace);
            }
        }

        Iterator<LinkedHashMap.Entry<Integer, ArrayList<LatLongData>>> it = indoorRunDatas.latMap.entrySet().iterator();
        ArrayList<ArrayList<LatLongData>> lists = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<Integer, ArrayList<LatLongData>> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            ArrayList<LatLongData> latLongData = entry.getValue();
            if (latLongData.size() > 2) {
                lists.add(entry.getValue());
            }
        }
        ArrayList sumList = new ArrayList();

        for (int i = 0; i < lists.size(); i++) {
            sumList.add(lists.get(i));
        }


        if (sumList.size() != 0) {
            bean.setCoorArr(sumList.toString());
        } else {
            bean.setCoorArr("");
        }

        bean.setDistance(CommonDateUtil.formatTwoPoint(indoorRunDatas.distance));
        bean.setCalories(CommonDateUtil.formatInterger(indoorRunDatas.calories));

        return SportDataAction.saveSportData(bean);
    }


    public SportDetailData getSportDetailDataById(long id, String publicId) {
        SportBean sportBean = SportDataAction.getLastData(id);
        sportBean.setPublicId(publicId);
        SportDataAction.saveSportData(sportBean);
        SportDetailData detailData = new SportDetailData();
        if (sportBean != null) {
            detailData.setCoorArr(sportBean.getCoorArr());
            detailData.setHeartRateArr(sportBean.getHeartRateArr());
            detailData.setId(sportBean.getId());
            detailData.setPaceArr(sportBean.getPaceArr());
            detailData.setIphoneSportId(sportBean.getPublicId() + "");
            return detailData;
        } else {
            detailData.setCoorArr("");
            detailData.setHeartRateArr("");
            detailData.setId(sportBean.getId());
            detailData.setPaceArr("");
            return detailData;
        }
    }


    public boolean delectSport(long id) {
        if (SportDataAction.deletSportBean(id)) {
            return true;
        } else {
            return false;
        }
    }

    public SportSumData getSummerData(long id) {


        SportSumData sumData = new SportSumData();

        /**
         *  String userId;
         int type;
         String avgPace;
         String avgSpeed;
         String calories;
         String distance;
         long endTimestamp;
         long startTimestamp;
         String maxHeartRate;
         String maxPace;
         String minHeartRate;
         String minPace;
         String step;
         int timeLong;
         */

        SportBean sportBean = SportDataAction.getLastData(id);
        if (sportBean != null) {
            sumData.setUserId(sportBean.getUserId());
            sumData.setType(sportBean.getSporttype());
            sumData.setAvgPace(sportBean.getAvgPace());
            sumData.setAvgSpeed(sportBean.getAvgSpeed());
            sumData.setCalories(sportBean.getCalories());
            sumData.setDistance(sportBean.getDistance());
            sumData.setAvgPace(sportBean.getAvgPace());
            sumData.setTimeLong(sportBean.getTimeLong());
            sumData.setStartTimestamp(sportBean.getStartTimestamp());
            sumData.setEndTimestamp(sportBean.getEndTimestamp());
            sumData.setStep(sportBean.getStep());
            sumData.setMaxPace(sportBean.getMaxPace());
            sumData.setMinPace(sportBean.getMinPace());
            sumData.setMaxHeartRate(sportBean.getMaxHeartRate());
            sumData.setMinHeartRate(sportBean.getMinHeartRate());
            sumData.setTimeLong(sportBean.getTimeLong());
            sumData.setAvgHeartRate(sportBean.getAvgHeartRate());
            return sumData;
        } else {
            sumData.setAvgPace(JkConfiguration.strPace);
            sumData.setDistance(JkConfiguration.strPace);
            sumData.setEndTimestamp(System.currentTimeMillis());
            sumData.setCalories("0");
            sumData.setType(0);
            return sumData;
        }

    }
}
