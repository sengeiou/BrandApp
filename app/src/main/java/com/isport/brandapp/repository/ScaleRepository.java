package com.isport.brandapp.repository;


import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.scale.bean.ScaleInsertBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.util.InitCommonParms;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;


public class ScaleRepository<T, T1, T2, T3>{


    /**
     * 上传体脂称测量数据
     *
     * @param scale_fourElectrode_dataModel
     * @return
     */
    public static Observable<UpdateSuccessBean> requst(Scale_FourElectrode_DataModel scale_fourElectrode_dataModel) {
        return new NetworkBoundResource<UpdateSuccessBean>() {
            @Override
            public Observable<UpdateSuccessBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<UpdateSuccessBean> getNoCacheData() {
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
            public Observable<UpdateSuccessBean> getRemoteSource() {
                ScaleInsertBean scaleInsertBean = new ScaleInsertBean();
                scaleInsertBean.setFatsteelyardTargetId(null);
                scaleInsertBean.setTimestamp(scale_fourElectrode_dataModel.getTimestamp());
                Logger.myLog("去上传 getUserId == "+scale_fourElectrode_dataModel.getUserId());
                scaleInsertBean.setUserId(scale_fourElectrode_dataModel.getUserId());
                scaleInsertBean.setDeviceId(scale_fourElectrode_dataModel.getDeviceId());
                scaleInsertBean.setDateStr(scale_fourElectrode_dataModel.getDateStr());
                scaleInsertBean.setBfp_bodyFatPercent((float) scale_fourElectrode_dataModel.getBFP());
                scaleInsertBean.setBmc_boneMineralContent((float) scale_fourElectrode_dataModel.getBMC());
                scaleInsertBean.setBmi((float) scale_fourElectrode_dataModel.getBMI());
                scaleInsertBean.setBmr_basalMetabolicRate((float) scale_fourElectrode_dataModel.getBMR());
                scaleInsertBean.setBodyWeight(scale_fourElectrode_dataModel.getWeight());
                scaleInsertBean.setBwp_bodyWeightPercent((float) scale_fourElectrode_dataModel.getBWP());
                scaleInsertBean.setFc_fatControl((float) scale_fourElectrode_dataModel.getFC());
                scaleInsertBean.setMa_bodyAge(scale_fourElectrode_dataModel.getMA());
                scaleInsertBean.setMc_muscleControl((float) scale_fourElectrode_dataModel.getMC());
                scaleInsertBean.setPp_proteinPercent((float) scale_fourElectrode_dataModel.getPP());
                scaleInsertBean.setSbc_individualScore(scale_fourElectrode_dataModel.getSBC());
                scaleInsertBean.setSbw_standardBodyWeight((float) scale_fourElectrode_dataModel.getSBW());
                scaleInsertBean.setSlm_muscleWeight((float) scale_fourElectrode_dataModel.getSLM());
                scaleInsertBean.setSmm_skeletalMuscleMass((float) scale_fourElectrode_dataModel.getSMM());
                scaleInsertBean.setVfr_visceralFatRating((float) scale_fourElectrode_dataModel.getVFR());
                scaleInsertBean.setWc_weightControl((float) scale_fourElectrode_dataModel.getWC());
                InitCommonParms<ScaleInsertBean, BaseUrl, BaseDbPar> initCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.FAT_STEELYARD;
                baseUrl.url2 = JkConfiguration.Url.FATSTEELYARD_TARGET;
                baseUrl.url3 = JkConfiguration.Url.INSERTSELECTIVE;
                return (Observable<UpdateSuccessBean>) RetrofitClient.getInstance().post(initCommonParms.setPostBody
                        (!(App.appType()==App.httpType)).setParms(scaleInsertBean).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.FATSTEELYARD_UPDATE).getPostBody());
            }

            @Override
            public void saveRemoteSource(UpdateSuccessBean remoteSource) {

            }
        }.getAsObservable();
    }
}
