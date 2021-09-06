package com.isport.brandapp.repository;


import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.action.DeviceTypeTableAction;
import com.isport.blelibrary.db.action.scale.Scale_FourElectrode_DataModelAction;
import com.isport.blelibrary.db.action.sleep.Sleep_Sleepace_SleepNoticeModelAction;
import com.isport.blelibrary.db.action.watch.Watch_SmartBand_SportDataModelAction;
import com.isport.blelibrary.db.table.DeviceTypeTable;
import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.db.table.watch.Watch_SmartBand_SportDataModel;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.BindDevice;
import com.isport.brandapp.Home.bean.http.BindDeviceList;
import com.isport.brandapp.R;
import com.isport.brandapp.arithmetic.WeightStandard;
import com.isport.brandapp.arithmetic.WeightStandardImpl;
import com.isport.brandapp.bind.bean.ClockTimeBean;
import com.isport.brandapp.bind.bean.DeviceState;
import com.isport.brandapp.device.band.bean.BandDayBean;
import com.isport.brandapp.device.band.bean.BandHistoryList;
import com.isport.brandapp.device.scale.bean.ScaleBean;
import com.isport.brandapp.device.scale.bean.ScaleDayBean;
import com.isport.brandapp.device.scale.bean.ScaleHistoryBean;
import com.isport.brandapp.device.scale.bean.ScaleHistroyList;
import com.isport.brandapp.device.scale.bean.ScaleReportBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.parm.db.BaseDbParms;
import com.isport.brandapp.parm.db.DeviceDbParms;
import com.isport.brandapp.parm.db.DeviceHistoryParms;
import com.isport.brandapp.parm.db.DeviceIdParms;
import com.isport.brandapp.parm.db.ProgressShowParms;
import com.isport.brandapp.parm.db.ScaleReportParms;
import com.isport.brandapp.util.RequestCode;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import phone.gym.jkcq.com.commonres.commonutil.UserUtils;


public class MainResposition<T, T1, T2, T3> {


    private static final String TAG = MainResposition.class.getSimpleName();

    public Observable<T> requst(PostBody<T1, T2, T3> postBody) {
        return new NetworkBoundResource<T>() {
            @Override
            public Observable<T> getFromDb() {
                return Observable.create(new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                        BaseDbParms dbParm = (BaseDbParms) postBody.dbParm;
                        Logger.myLog(TAG,"dbParm requestCode == " + dbParm.requestCode);
                        switch (dbParm.requestCode) {
                            case RequestCode.Request_getBindDeviceList:
                                Logger.myLog(TAG,"Request_getBindDeviceList");
                                //获取设备列表
                                //查询deviceType数据库，看有哪些设备注册
                                List<DeviceTypeTable> all = DeviceTypeTableAction.getAll(TokenUtil
                                        .getInstance().getPeopleIdStr(BaseApp.getApp()));
                                BindDeviceList bean = new BindDeviceList();
                                bean.list = new ArrayList<BindDevice>();
                                if (all != null) {
                                    Logger.myLog(TAG,"Request_getBindDeviceList all != null");
                                    //说明有存储
                                    for (int i = 0; i < all.size(); i++) {
                                        BindDevice deviceBean = new BindDevice();
                                        deviceBean.setDevicetName(all.get(i).getDeviceName());
                                        deviceBean.setDeviceTypeId(all.get(i).getDeviceType());
                                        deviceBean.setMac(all.get(i).getMac());
                                        deviceBean.setUserId(Integer.parseInt(TokenUtil.getInstance().getPeopleIdStr
                                                (BaseApp.getApp())));
                                        deviceBean.setDeviceId(all.get(i).getDeviceId());
                                        deviceBean.setTimestamp(all.get(i).getTimeTamp());
                                        deviceBean.setCreateTime(0);
                                        bean.list.add(deviceBean);
                                        Logger.myLog(TAG,"Request_getBindDeviceList== " + deviceBean.toString());
                                    }
                                    if (!((ProgressShowParms) dbParm).show)
                                        NetProgressObservable.getInstance().hide("Request_getBindDeviceList");
                                    emitter.onNext((T) bean);
                                } else {
                                    Logger.myLog(TAG,"Request_getBindDeviceList all == null");
                                    //说明没有绑定设备
                                    if (!((ProgressShowParms) dbParm).show)
                                        NetProgressObservable.getInstance().hide("Request_getBindDeviceList");
                                    emitter.onNext((T) bean);
                                }
                                break;
                            case RequestCode.Request_bindDevice:
                                Logger.myLog(TAG,"----Request_bindDevice");
                                ISportAgent.getInstance().bindDevice(((DeviceDbParms) dbParm).deviceType, (
                                        (DeviceDbParms) dbParm).deviceMac, ((DeviceDbParms) dbParm).deviceId, (
                                        (DeviceDbParms) dbParm).userId, (
                                        (DeviceDbParms) dbParm)
                                        .deviceName);
                                DeviceState deviceState = new DeviceState();
                                deviceState.setUserId(-1);
                                emitter.onNext((T) deviceState);
                                break;
                            case RequestCode.Request_getScalerReportData:
                                Logger.myLog(TAG,"Request_getScalerReportData");
                                ScaleReportParms scaleReportParms = (ScaleReportParms) postBody.dbParm;
                                Scale_FourElectrode_DataModel scaleFourElectrodeDataModelByDeviceIdAndTimeTamp = null;
                                if (scaleReportParms.mScale_fourElectrode_dataModel == null) {
                                    scaleFourElectrodeDataModelByDeviceIdAndTimeTamp =
                                            Scale_FourElectrode_DataModelAction
                                                    .findScaleFourElectrodeDataModelByDeviceIdAndTimeTamp
                                                            (scaleReportParms
                                                                            .timeTamp,
                                                                    TokenUtil
                                                                            .getInstance().getPeopleIdStr(BaseApp.getApp()));
                                } else {
                                    scaleFourElectrodeDataModelByDeviceIdAndTimeTamp = scaleReportParms
                                            .mScale_fourElectrode_dataModel;
                                }

                                String deviceId1 = scaleReportParms.deviceId;
                                //去掉查询操作
//                                        Scale_FourElectrode_DataModelAction
//                                                .findScaleFourElectrodeDataModelByDeviceIdAndTimeTamp(deviceId1,
//                                                        timeTamp,Integer.parseInt(TokenUtil.getInstance()
// .getPeopleIdStr(BaseApp.getApp())));
                                ScaleReportBean scaleReportBean = new ScaleReportBean();
                                scaleReportBean.setWeight(scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getWeight
                                        () + "");
                                scaleReportBean.setBmi(CommonDateUtil.formatOnePoint
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBMI()) + "");
                                scaleReportBean.setCreatTime(scaleFourElectrodeDataModelByDeviceIdAndTimeTamp
                                        .getTimestamp());
//                                骨骼肌率 = 骨骼肌重量 / 体重 *100%
//                                        肌肉率 = 肌肉重量/体重 *100%
//                                        水分 = 体重 * 含水量
//                                去脂体重 = 体重 - 脂肪重量

//                                private double BFP;//脂肪率%
//                                private double SLM;//肌肉重kg
//                                private double BWP;//水含量%
//                                private double BMC;//骨盐量
//                                private double VFR;//内脏脂肪等级
//                                private double PP;//蛋白质%
//                                private double SMM;//骨骼肌kg
//                                private double BMR;//基础代谢
//                                private double BMI;//身体质量指数
//                                private double SBW;//标准体重kg
//                                private double MC;//肌肉控制
//                                private double WC;//体重控制
//                                private double FC;//脂肪控制
//                                private int MA;//身体年龄
//                                private int SBC;//评分
                                List<ScaleBean> list = new ArrayList<>();

                                ArrayList<String> resultStr;
                                WeightStandard weightStandard = new WeightStandardImpl();
                                //取出用户信息

                                UserInfoBean userInfo = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance()
                                        .getPeopleIdStr(BaseApp.getApp()));

                                int userHeight = 168, userAge = 18;
                                String userGender = "Female";
                                if (userInfo != null) {
                                    String strHeight = userInfo.getHeight();
                                    userHeight = (int) Double.parseDouble(strHeight);
                                    String birthday = userInfo.getBirthday();
                                    userAge = UserUtils.getAge(birthday);
                                    userGender = userInfo.getGender();
                                }

                                ScaleBean scaleBeanScore = new ScaleBean();//分数
                                scaleBeanScore.setTitle(UIUtils.getString(R.string.composite_score));
                                scaleBeanScore.setValue(scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSBC() + "");
                                resultStr = weightStandard.compositeScoreStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSBC() + "");
                                scaleBeanScore.standard = resultStr.get(0);
                                scaleBeanScore.color = resultStr.get(1);
                                list.add(scaleBeanScore);

                                //新版没有肥胖度了
                                /*ScaleBean scaleBeanFAT = new ScaleBean();//肥胖度
                                scaleBeanFAT.setTitle(UIUtils.getString(R.string.obesity_degree));
                                scaleBeanFAT.setValue("5.5_%");
                                scaleBeanFAT.setImgInt(R.drawable.fatp);
                                list.add(scaleBeanFAT);*/
                                //体重 体重的结果等于bmi的结果
                                ScaleBean scaleBeanWeight = new ScaleBean();
                                scaleBeanWeight.setTitle(UIUtils.getString(R.string.body_weight));
                                scaleBeanWeight.setValue(scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getWeight()
                                        + "_" + UIUtils.getString(R.string.body_fat_util));
                                resultStr = weightStandard.bmiStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBMI());
                                scaleBeanWeight.standard = resultStr.get(0);
                                scaleBeanWeight.color = resultStr.get(1);

                                scaleBeanWeight.setImgInt(R.drawable.weight);
                                list.add(scaleBeanWeight);

                                //BMI
                                ScaleBean scaleBeanBMI = new ScaleBean();
                                scaleBeanBMI.setTitle(UIUtils.getString(R.string.bmi));
                                scaleBeanBMI.setValue(CommonDateUtil.formatOnePoint
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBMI()) + "_");
                                resultStr = weightStandard.bmiStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBMI());
                                scaleBeanBMI.standard = resultStr.get(0);
                                scaleBeanBMI.color = resultStr.get(1);
                                scaleBeanBMI.setImgInt(R.drawable.bmi);
                                list.add(scaleBeanBMI);
                                //体脂率
                                ScaleBean scaleBeanFatP = new ScaleBean();
                                scaleBeanFatP.setTitle(UIUtils.getString(R.string.bfp));
                                scaleBeanFatP.setValue(CommonDateUtil.formatOnePoint
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBFP()) +
                                        "_%");

                                resultStr = weightStandard.bfpStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBFP(), userGender,
                                                userAge);
                                scaleBeanFatP.standard = resultStr.get(0);
                                scaleBeanFatP.color = resultStr.get(1);

                                scaleBeanFatP.setImgInt(R.drawable.bodyfatrate);
                                list.add(scaleBeanFatP);
                                //脂肪重量 脂肪重量的结果等于体脂率的结果
                                ScaleBean scaleBeanFatWeight = new ScaleBean();//脂肪重量
                                scaleBeanFatWeight.setTitle(UIUtils.getString(R.string.fatweight));
                                scaleBeanFatWeight.setValue(CommonDateUtil.formatOnePoint
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBFP() *
                                                scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getWeight() /
                                                (float) 100) + "_" + UIUtils.getString(R.string.body_fat_util));

                                resultStr = weightStandard.bfpStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBFP(), userGender,
                                                userAge);
                                scaleBeanFatWeight.standard = resultStr.get(0);
                                scaleBeanFatWeight.color = resultStr.get(1);

                                scaleBeanFatWeight.setImgInt(R.drawable.fatmass);
                                list.add(scaleBeanFatWeight);
                                //骨骼肌率
                                ScaleBean scaleBeanSkeletalMuscle = new ScaleBean();
                                scaleBeanSkeletalMuscle.setTitle(UIUtils.getString(R.string.body_bone));
                                scaleBeanSkeletalMuscle.setValue(CommonDateUtil.formatOnePoint
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSMM() * 100 /
                                                scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getWeight()) + "_%");
                                scaleBeanSkeletalMuscle.setImgInt(R.drawable.rateofskeletaluscle);

                                resultStr = weightStandard.skeleton_muscle_massStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSMM(), userGender,
                                                userHeight);
                                scaleBeanSkeletalMuscle.standard = resultStr.get(0);
                                scaleBeanSkeletalMuscle.color = resultStr.get(1);

                                list.add(scaleBeanSkeletalMuscle);
                                //骨骼肌重量  skeleton_muscle_massStandardWithValue
                                ScaleBean scaleBeanSkeletalSkeletalMuscleWeight = new ScaleBean();
                                scaleBeanSkeletalSkeletalMuscleWeight.setTitle(UIUtils.getString(R.string.skeleton_muscle_mass));
                                scaleBeanSkeletalSkeletalMuscleWeight.setValue
                                        (CommonDateUtil.formatOnePoint
                                                (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSMM()) + "_" + UIUtils.getString(R.string.body_fat_util));
                                scaleBeanSkeletalSkeletalMuscleWeight.setImgInt(R.drawable.skeletalmuscleweight);

                                resultStr = weightStandard.skeleton_muscle_massStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSMM(), userGender,
                                                userHeight);
                                scaleBeanSkeletalSkeletalMuscleWeight.standard = resultStr.get(0);
                                scaleBeanSkeletalSkeletalMuscleWeight.color = resultStr.get(1);

                                list.add(scaleBeanSkeletalSkeletalMuscleWeight);
                                //肌肉率
                                ScaleBean scaleBeanSkeletalRateOfMuscle = new ScaleBean();
                                scaleBeanSkeletalRateOfMuscle.setTitle(UIUtils.getString(R.string.muscle_rate));
                                scaleBeanSkeletalRateOfMuscle.setValue(CommonDateUtil.formatOnePoint
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSLM() * 100 /
                                                scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getWeight()) + "_%");

                                resultStr = weightStandard.muscle_massStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSLM(), userGender,
                                                userHeight);
                                scaleBeanSkeletalRateOfMuscle.standard = resultStr.get(0);
                                scaleBeanSkeletalRateOfMuscle.color = resultStr.get(1);


                                scaleBeanSkeletalRateOfMuscle.setImgInt(R.drawable.rateofmuscle);
                                list.add(scaleBeanSkeletalRateOfMuscle);
                                //肌肉重量
                                ScaleBean scaleBeanSkeletalMuscleWeight = new ScaleBean();
                                scaleBeanSkeletalMuscleWeight.setTitle(UIUtils.getString(R.string.muscle_mass));
                                scaleBeanSkeletalMuscleWeight.setValue
                                        (CommonDateUtil.formatOnePoint
                                                (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSLM()) + "_" + UIUtils.getString(R.string.body_fat_util));

                                resultStr = weightStandard.muscle_massStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getSLM(), userGender,
                                                userHeight);
                                scaleBeanSkeletalMuscleWeight.standard = resultStr.get(0);
                                scaleBeanSkeletalMuscleWeight.color = resultStr.get(1);

                                scaleBeanSkeletalMuscleWeight.setImgInt(R.drawable.muscleweight);
                                list.add(scaleBeanSkeletalMuscleWeight);
                                //内脏脂肪
                                ScaleBean scaleBeanSkeletalVisceralFat = new ScaleBean();
                                scaleBeanSkeletalVisceralFat.setTitle(UIUtils.getString(R.string.visceral_fat));
                                scaleBeanSkeletalVisceralFat.setValue
                                        (CommonDateUtil.formatOnePoint
                                                (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getVFR()) + UIUtils
                                                .getString(R.string.lv));

                                resultStr = weightStandard.visceral_fatStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getVFR());
                                scaleBeanSkeletalVisceralFat.standard = resultStr.get(0);
                                scaleBeanSkeletalVisceralFat.color = resultStr.get(1);

                                scaleBeanSkeletalVisceralFat.setImgInt(R.drawable.visceralfat);
                                list.add(scaleBeanSkeletalVisceralFat);
                                //水份
                                ScaleBean scaleBeanSkeletalMoisture = new ScaleBean();
                                scaleBeanSkeletalMoisture.setTitle(UIUtils.getString(R.string.water));
                                scaleBeanSkeletalMoisture.setValue(CommonDateUtil.formatOnePoint
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBWP()) + "_%");

                                resultStr = weightStandard.bwpStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBWP(), userGender);
                                scaleBeanSkeletalMoisture.standard = resultStr.get(0);
                                scaleBeanSkeletalMoisture.color = resultStr.get(1);
                                scaleBeanSkeletalMoisture.setImgInt(R.drawable.moisture);
                                list.add(scaleBeanSkeletalMoisture);
                                //水含量
                                ScaleBean scaleBeanSkeletalWaterContent = new ScaleBean();
                                scaleBeanSkeletalWaterContent.setTitle(UIUtils.getString(R.string.water_content));
                                scaleBeanSkeletalWaterContent.setValue
                                        (CommonDateUtil.formatOnePoint
                                                (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBWP() *
                                                        scaleFourElectrodeDataModelByDeviceIdAndTimeTamp
                                                                .getWeight() /
                                                        (float) 100) + "_" + UIUtils.getString(R.string.body_fat_util));
                                scaleBeanSkeletalWaterContent.setImgInt(R.drawable.water);

                                resultStr = weightStandard.bwpStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBWP(), userGender);
                                scaleBeanSkeletalWaterContent.standard = resultStr.get(0);
                                scaleBeanSkeletalWaterContent.color = resultStr.get(1);
                                list.add(scaleBeanSkeletalWaterContent);
                                //基础新陈代谢
                                ScaleBean scaleBeanSkeletalBasalMetabolism = new ScaleBean();
                                scaleBeanSkeletalBasalMetabolism.setTitle(UIUtils.getString(R.string.basal_metabolism));
                                scaleBeanSkeletalBasalMetabolism.setValue
                                        (CommonDateUtil.formatOnePoint
                                                (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBMR()) + "_");
                                scaleBeanSkeletalBasalMetabolism.setImgInt(R.drawable.basalmetabolism);
                                resultStr = weightStandard.basal_metabolismStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBMR(), userGender,
                                                userAge);
                                scaleBeanSkeletalBasalMetabolism.standard = resultStr.get(0);
                                scaleBeanSkeletalBasalMetabolism.color = resultStr.get(1);


                                list.add(scaleBeanSkeletalBasalMetabolism);
                                //骨量
                                ScaleBean scaleBeanSkeletalBasalBoneMass = new ScaleBean();
                                scaleBeanSkeletalBasalBoneMass.setTitle(UIUtils.getString(R.string.bone_mass));
                                scaleBeanSkeletalBasalBoneMass.setValue
                                        (CommonDateUtil.formatOnePoint
                                                (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBMC()) + "_" + UIUtils.getString(R.string.body_fat_util));
                                scaleBeanSkeletalBasalBoneMass.setImgInt(R.drawable.bonemass);

                                resultStr = weightStandard.bone_massStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBMC(), userGender,
                                                userAge);
                                scaleBeanSkeletalBasalBoneMass.standard = resultStr.get(0);
                                scaleBeanSkeletalBasalBoneMass.color = resultStr.get(1);

                                list.add(scaleBeanSkeletalBasalBoneMass);
                                //蛋白质
                                ScaleBean scaleBeanSkeletalBasalProtein = new ScaleBean();
                                scaleBeanSkeletalBasalProtein.setTitle(UIUtils.getString(R.string.protein));
                                scaleBeanSkeletalBasalProtein.setValue
                                        (CommonDateUtil.formatOnePoint
                                                (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getPP()) + "_%");
                                scaleBeanSkeletalBasalProtein.setImgInt(R.drawable.protein);
                                resultStr = weightStandard.proteinStandardWithValue
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getPP());
                                scaleBeanSkeletalBasalProtein.standard = resultStr.get(0);
                                scaleBeanSkeletalBasalProtein.color = resultStr.get(1);
                                list.add(scaleBeanSkeletalBasalProtein);
                                //去脂体重 不要算
                                ScaleBean scaleBeanSkeletalBasalTofatweight = new ScaleBean();
                                scaleBeanSkeletalBasalTofatweight.setTitle(UIUtils.getString(R.string.ffm));
                                scaleBeanSkeletalBasalTofatweight.setValue(CommonDateUtil.formatOnePoint
                                        (scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getWeight() -
                                                scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getBFP() *
                                                        scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getWeight()
                                                        / (float) 100) + "_" + UIUtils.getString(R.string.body_fat_util));
                                scaleBeanSkeletalBasalTofatweight.setImgInt(R.drawable.tofatweight);
                                list.add(scaleBeanSkeletalBasalTofatweight);

                                ScaleBean bodyAge = new ScaleBean();
                                bodyAge.setTitle(UIUtils.getString(R.string.body_age));
                                bodyAge.setValue(scaleFourElectrodeDataModelByDeviceIdAndTimeTamp.getMA() + "_" +
                                        UIUtils.getString(R.string.age));
                                bodyAge.setImgInt(R.drawable.icon_body_age);
                                list.add(bodyAge);

                                scaleReportBean.setList(list);
                                NetProgressObservable.getInstance().hide("Request_getScalerReportData");
                                emitter.onNext((T) scaleReportBean);
                                break;
                            case RequestCode.Request_getWatchHistoryData:
                                Logger.myLog("Request_getWatchHistoryData");
                                DeviceHistoryParms scaleHistoryParms1 = (DeviceHistoryParms) postBody.dbParm;
                                String deviceIdWatch = scaleHistoryParms1.deviceId;
                                long timeTampLast = scaleHistoryParms1.timeTamp;
                                List<Watch_SmartBand_SportDataModel>
                                        ltTodayTenDataWatchSmartBandSportDataModelByDeviceIdAndDateStr =
                                        Watch_SmartBand_SportDataModelAction
                                                .findLtTodayTenDataWatchSmartBandSportDataModelByDeviceIdAndDateStr
                                                        (deviceIdWatch, timeTampLast);
                                ArrayList<BandDayBean> historyBean = new ArrayList<>();
                                if (ltTodayTenDataWatchSmartBandSportDataModelByDeviceIdAndDateStr != null) {
                                    for (int i = 0; i <
                                            ltTodayTenDataWatchSmartBandSportDataModelByDeviceIdAndDateStr.size();
                                         i++) {
                                        Watch_SmartBand_SportDataModel watch_smartBand_sportDataModel =
                                                ltTodayTenDataWatchSmartBandSportDataModelByDeviceIdAndDateStr.get(i);
                                        BandDayBean bandDayBean = new BandDayBean();
                                        bandDayBean.buildTime = TimeUtils.changeStrDateToLong2
                                                (watch_smartBand_sportDataModel.getDateStr());
                                        bandDayBean.stepNum = watch_smartBand_sportDataModel.getTotalSteps() + "";
                                        bandDayBean.stepKm = watch_smartBand_sportDataModel.getTotalDistance() + "";
                                        bandDayBean.calorie = watch_smartBand_sportDataModel.getTotalCalories() + "";
                                        historyBean.add(bandDayBean);
                                    }
                                }
                                BandHistoryList bandHistoryList = new BandHistoryList();
                                bandHistoryList.setList(historyBean);
                                NetProgressObservable.getInstance().hide("Request_getWatchHistoryData");
                                emitter.onNext((T) new BaseResponse<>());
                                break;
                            case RequestCode.Request_getScaleHistoryData:
                                Logger.myLog("Request_getScaleHistoryData");
                                DeviceHistoryParms scaleHistoryParms = (DeviceHistoryParms) postBody.dbParm;
                                int pageSize = scaleHistoryParms.pageSize;
                                String deviceId = scaleHistoryParms.deviceId;
                                String currentMonth = scaleHistoryParms.currentMonth;
                                //第一次查询，查询<=当月的所有值
                                //暂定单月单月查询,根据pageSize距离当月多少月来算要查询的月
                                NetProgressObservable.getInstance().hide("Request_getScaleHistoryData");
                                BaseResponse<ScaleHistroyList> scaleHistroyListBaseResponse = new BaseResponse<>();
                                ScaleHistroyList scaleHistroyList = new ScaleHistroyList();
                                List<ScaleHistoryBean> scaleHistoryBeanList = new ArrayList<>();
                                ScaleHistoryBean scaleHistoryBean = new ScaleHistoryBean();
                                ArrayList<ScaleDayBean> datalist = new ArrayList<>();
                                String mCurrentMonth = null;
                                if (pageSize == 0) {
                                    mCurrentMonth = "";
                                    Calendar currentCalendar = Calendar.getInstance();
                                    //当前月向前移
//                                    currentCalendar.add(Calendar.MONTH, -scaleHistoryParms.pageSize);
                                    //获取月string
                                    String timeByyyyyMM = TimeUtils.getTimeByyyyyMMdd(currentCalendar);
                                    int year = TimeUtils.getYear(currentCalendar);
                                    int month = TimeUtils.getMonth(currentCalendar);
                                    //查询这次的一个月数据,当查询为空则返回isLastData，提示没有更多了
                                    List<Scale_FourElectrode_DataModel> all1Month = Scale_FourElectrode_DataModelAction
                                            .getAllLastMonthData(timeByyyyyMM, TokenUtil
                                                    .getInstance
                                                            ()
                                                    .getPeopleIdStr
                                                            (BaseApp.getApp()));
                                    //查询所有数据,获取第一条，判断数据所处的月，然后单独查询当月和之后的月是否有数据
                                    //第一次获取，或者重新下拉刷新
                                    if (all1Month == null) {
                                        //没有数据的情况,直接返回null
                                        emitter.onNext((T) new BaseResponse<>());
                                    } else {
                                        //查询第一条所处的月,并查询出当月数据
                                        Scale_FourElectrode_DataModel scale_fourElectrode_dataModel1 = all1Month.get(0);
                                        //有数据的最近的一个月的String
                                        String timeByyyyyMM1 = TimeUtils.getTimeByyyyyMMdd
                                                (scale_fourElectrode_dataModel1.getTimestamp());
                                        //查询这个月的数据
                                        // TODO: 2019/1/14 是否在这儿要用具体的日期来查询,小于等于当天大于等于月初
                                        //获取当月最初一天的日期
                                        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
                                        currentCalendar.set(Calendar.HOUR, 0);
                                        currentCalendar.set(Calendar.MINUTE, 0);
                                        currentCalendar.set(Calendar.SECOND, 0);
                                        //月的开始，和结束
                                        String timeByyyyyMMFirst = TimeUtils.getTimeByyyyyMMdd(currentCalendar);
                                        all1Month = Scale_FourElectrode_DataModelAction
                                                .getAllMonthData(timeByyyyyMMFirst, timeByyyyyMM1, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp
                                                        ()));
                                        for (int i = 0; i < all1Month.size(); i++) {
                                            Logger.myLog(all1Month.get(i).toString());
                                        }
                                        //查询这个月之前的所有数据
                                        List<Scale_FourElectrode_DataModel> all1MonthLast =
                                                Scale_FourElectrode_DataModelAction
                                                        .getLastMonthData(timeByyyyyMMFirst, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                                        if (all1MonthLast == null) {
                                            //没有历史数据了，currentMonth,将会返回null
                                            mCurrentMonth = "";
                                        } else {
                                            //还有数据，则返回第一条数据的月，作为下一个查询的起点
                                            Scale_FourElectrode_DataModel scale_fourElectrode_dataModel1Last =
                                                    all1MonthLast.get(0);
                                            //有数据的最近的一个月的String
                                            timeByyyyyMMFirst = TimeUtils.getTimeByyyyyMMdd
                                                    (scale_fourElectrode_dataModel1Last.getTimestamp());
                                            mCurrentMonth = timeByyyyyMMFirst;
                                        }
                                        //拿一个月的数据
                                        scaleHistroyListBaseResponse.setCode(2000);
                                        scaleHistroyListBaseResponse.setIslastdata(all1MonthLast == null ? true :
                                                false);
                                        scaleHistoryBean.setMonthTitle(true);
                                        scaleHistoryBean.setMonth(year + "-" + CommonDateUtil.formatTwoStr(month));
                                        if (all1Month.size() == 1) {
                                            //当月只有一条数据
                                            scaleHistoryBean.setLeftFatPersent("--");
                                            scaleHistoryBean.setLeftWeight("--");
                                        } else {
                                            //多条数据，比较第一条和最后一条
                                            Scale_FourElectrode_DataModel scale_fourElectrode_dataModel = all1Month
                                                    .get(0);
                                            Scale_FourElectrode_DataModel scale_fourElectrode_dataModelLast = all1Month
                                                    .get(all1Month.size() - 1);
                                            float weight = scale_fourElectrode_dataModel.getWeight();
                                            float weightLast = scale_fourElectrode_dataModelLast.getWeight();
                                            float leftWeight = weight - weightLast;
                                            if (leftWeight != 0) {
                                                scaleHistoryBean.setLeftWeight(leftWeight + "");
                                            } else {
                                                scaleHistoryBean.setLeftWeight("--");
                                            }
                                            double bfp = scale_fourElectrode_dataModel.getBFP();
                                            double bfpLast = scale_fourElectrode_dataModelLast.getBFP();
                                            double leftNFP = bfp - bfpLast;
                                            if (leftNFP != 0) {
                                                scaleHistoryBean.setLeftFatPersent(leftNFP + "");
                                            } else {
                                                scaleHistoryBean.setLeftFatPersent("--");
                                            }
                                        }
                                        for (int i = 0; i < all1Month.size(); i++) {
                                            Scale_FourElectrode_DataModel scale_fourElectrode_dataModel = all1Month
                                                    .get(i);
                                            ScaleDayBean scaleDayBean = new ScaleDayBean();
                                            float weight = scale_fourElectrode_dataModel.getWeight();
                                            double bfp = scale_fourElectrode_dataModel.getBFP();
                                            scaleDayBean.weight = weight + "";
                                            scaleDayBean.fatpersent = bfp + "";
                                            //和上次比较的值
                                            if (i + 1 <= all1Month.size() - 1) {
                                                //存在下一条数据
                                                Scale_FourElectrode_DataModel scale_fourElectrode_dataModelLast =
                                                        all1Month
                                                                .get(i + 1);
                                                float weightLast = scale_fourElectrode_dataModelLast.getWeight();
                                                float leftWeight = weight - weightLast;
                                                if (leftWeight != 0) {
                                                    scaleDayBean.leftweight = leftWeight + "";
                                                } else {
                                                    scaleDayBean.leftweight = "--";
                                                }
                                                double bfpLast = scale_fourElectrode_dataModelLast.getBFP();
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
                                            scaleDayBean.creatTime = scale_fourElectrode_dataModel.getTimestamp();
                                            datalist.add(scaleDayBean);
                                        }
                                    }
                                } else {
                                    //根据currentMonth来查询数据
                                    Calendar calendar = Calendar.getInstance();
                                    String[] split1 = currentMonth.split("-");
                                    calendar.set(Calendar.YEAR, Integer.parseInt(split1[0]));
                                    calendar.set(Calendar.MONTH, Integer.parseInt(split1[1]) - 1);
                                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                                    calendar.set(Calendar.HOUR, 0);
                                    calendar.set(Calendar.MINUTE, 0);
                                    calendar.set(Calendar.MILLISECOND, 0);
                                    String timeByyyyyMMddFisrt = TimeUtils.getTimeByyyyyMMdd(calendar);
                                    List<Scale_FourElectrode_DataModel> all1Month = Scale_FourElectrode_DataModelAction
                                            .getAllMonthData(timeByyyyyMMddFisrt, currentMonth, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                                    String[] split = currentMonth.split("-");
                                    int year = Integer.parseInt(split[0]);
                                    int month = Integer.parseInt(split[1]);
                                    //查询之前月的所有数据
                                    List<Scale_FourElectrode_DataModel> all1MonthLast =
                                            Scale_FourElectrode_DataModelAction
                                                    .getLastMonthData(year + "-" + CommonDateUtil.formatTwoStr(month)
                                                            + "-01", TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                                    //加载更多
                                    if (all1Month == null) {
                                        //没有数据的情况,直接返回null
                                        emitter.onNext((T) new BaseResponse<>());
                                    } else {
                                        if (all1MonthLast == null) {
                                            //没有历史数据了，currentMonth,将会返回null
                                            mCurrentMonth = "";
                                        } else {
                                            //还有数据，则返回第一条数据的月，作为下一个查询的起点
                                            Scale_FourElectrode_DataModel scale_fourElectrode_dataModel1Last =
                                                    all1MonthLast.get(0);
                                            //有数据的最近的一个月的String
                                            String timeByyyyyMMLast = TimeUtils.getTimeByyyyyMMdd
                                                    (scale_fourElectrode_dataModel1Last.getTimestamp());
                                            mCurrentMonth = timeByyyyyMMLast;
                                        }
                                        //拿下一个月的数据
                                        scaleHistroyListBaseResponse.setCode(2000);
                                        scaleHistroyListBaseResponse.setIslastdata(all1MonthLast == null ? true :
                                                false);
                                        scaleHistoryBean.setMonthTitle(true);
                                        scaleHistoryBean.setMonth(year + "-" + CommonDateUtil.formatTwoStr(month));
                                        if (all1Month.size() == 1) {
                                            //当月只有一条数据
                                            scaleHistoryBean.setLeftFatPersent("--");
                                            scaleHistoryBean.setLeftWeight("--");
                                        } else {
                                            //多条数据，比较第一条和最后一条
                                            Scale_FourElectrode_DataModel scale_fourElectrode_dataModel = all1Month
                                                    .get(0);
                                            Scale_FourElectrode_DataModel scale_fourElectrode_dataModelLast = all1Month
                                                    .get(all1Month.size() - 1);
                                            float weight = scale_fourElectrode_dataModel.getWeight();
                                            float weightLast = scale_fourElectrode_dataModelLast.getWeight();
                                            float leftWeight = weight - weightLast;
                                            if (leftWeight != 0) {
                                                scaleHistoryBean.setLeftWeight(leftWeight + "");
                                            } else {
                                                scaleHistoryBean.setLeftWeight("--");
                                            }
                                            double bfp = scale_fourElectrode_dataModel.getBFP();
                                            double bfpLast = scale_fourElectrode_dataModelLast.getBFP();
                                            double leftNFP = bfp - bfpLast;
                                            if (leftNFP != 0) {
                                                scaleHistoryBean.setLeftFatPersent(leftNFP + "");
                                            } else {
                                                scaleHistoryBean.setLeftFatPersent("--");
                                            }
                                        }
                                        for (int i = 0; i < all1Month.size(); i++) {
                                            Scale_FourElectrode_DataModel scale_fourElectrode_dataModel = all1Month
                                                    .get(i);
                                            ScaleDayBean scaleDayBean = new ScaleDayBean();
                                            float weight = scale_fourElectrode_dataModel.getWeight();
                                            double bfp = scale_fourElectrode_dataModel.getBFP();
                                            scaleDayBean.weight = weight + "";
                                            scaleDayBean.fatpersent = bfp + "";
                                            //和上次比较的值
                                            if (i + 1 <= all1Month.size() - 1) {
                                                //存在下一条数据
                                                Scale_FourElectrode_DataModel scale_fourElectrode_dataModelLast =
                                                        all1Month
                                                                .get(i + 1);
                                                float weightLast = scale_fourElectrode_dataModelLast.getWeight();
                                                float leftWeight = weight - weightLast;
                                                if (leftWeight != 0) {
                                                    scaleDayBean.leftweight = leftWeight + "";
                                                } else {
                                                    scaleDayBean.leftweight = "--";
                                                }
                                                double bfpLast = scale_fourElectrode_dataModelLast.getBFP();
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
                                            scaleDayBean.creatTime = scale_fourElectrode_dataModel.getTimestamp();
                                            datalist.add(scaleDayBean);
                                        }
                                    }
                                }
                                scaleHistoryBean.setDatalist(datalist);
                                scaleHistoryBeanList.add(scaleHistoryBean);
                                scaleHistroyList.mNextMonth = mCurrentMonth;
                                scaleHistroyList.list = scaleHistoryBeanList;
                                Logger.myLog("Request_getScaleHistoryData MONTH == " + scaleHistoryBean.toString());
                                scaleHistroyListBaseResponse.setData(scaleHistroyList);
                                //解析数据，拼成网络版本一直的model
                                EventBus.getDefault().post(new MessageEvent(scaleHistroyListBaseResponse, MessageEvent.SCALE_HISTORYDATA
                                ));
                                emitter.onNext((T) scaleHistroyListBaseResponse);
                                break;
                            case RequestCode.Request_getClockTime:
                                Logger.myLog("Request_getClockTime");
                                ClockTimeBean clockTimeBean = new ClockTimeBean();
                                try {
                                    String noticeTimeByDeviceId = Sleep_Sleepace_SleepNoticeModelAction
                                            .findNoticeTimeByDeviceId(((DeviceIdParms) dbParm).deviceId);
                                    Logger.myLog("Request_getClockTime == " + noticeTimeByDeviceId + " deviceId==" +
                                            ((DeviceIdParms) dbParm).deviceId);
                                    if (!StringUtil.isBlank(noticeTimeByDeviceId)) {
                                        clockTimeBean = new ClockTimeBean();
                                        clockTimeBean.setClockTime(noticeTimeByDeviceId);
                                        Logger.myLog("Request_getClockTime000");
                                    }
                                } catch (Exception e) {
                                    Logger.myLog(e.toString());
                                }
                                NetProgressObservable.getInstance().hide();
                                Logger.myLog("Request_getClockTime111");
                                emitter.onNext((T) clockTimeBean);
                                Logger.myLog("Request_getClockTime222");
                                break;
                            default:
                                break;
                        }
                    }
                });//给时间绑定设备
//                .delay(((BaseDbParms) postBody.dbParm).requestCode == RequestCode.Request_bindDevice ? 100 : 0,
//                       TimeUnit.MILLISECONDS)
            }

            @Override
            public Observable<T> getNoCacheData() {
                return null;
            }

            @Override
            public boolean shouldFetchRemoteSource() {
                return false;
            }

            @Override
            public boolean shouldStandAlone() {
                return postBody.isStandAlone;
            }

            @Override
            public Observable<T> getRemoteSource() {
                return (Observable<T>) RetrofitClient.getInstance().post(postBody);
            }

            @Override
            public void saveRemoteSource(T remoteSource) {
                if (remoteSource instanceof String) {

                } else if (remoteSource instanceof UserInfoBean) {
                    AppConfiguration.saveUserInfo((UserInfoBean) remoteSource);
                } else {

                }
            }
        }.getAsObservable();
    }
}
