package com.isport.brandapp.sport.response;


import com.isport.brandapp.App;
import com.isport.brandapp.Home.bean.SportLastDataBeanList;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.sport.bean.IphoneSportListVo;
import com.isport.brandapp.sport.bean.IphoneSportWeekVo;
import com.isport.brandapp.sport.bean.ResultHistorySportSummarizingData;
import com.isport.brandapp.sport.bean.ResultSportHistroyList;
import com.isport.brandapp.sport.bean.SportDetailData;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.util.InitCommonParms;

import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;


public class SportRepository {


    public Observable<ResultHistorySportSummarizingData> getHistorySummarData() {
        return new NetworkBoundResource<ResultHistorySportSummarizingData>() {

            @Override
            public Observable<ResultHistorySportSummarizingData> getFromDb() {
                return null;
            }

            @Override
            public Observable<ResultHistorySportSummarizingData> getNoCacheData() {
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
            public Observable<ResultHistorySportSummarizingData> getRemoteSource() {
                InitCommonParms<ResultHistorySportSummarizingData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTTOTAL;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(App.getApp());
                return (Observable<ResultHistorySportSummarizingData>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_SPORT_HISTORY_SUMMAR_DATA).getPostBody());
            }

            @Override
            public void saveRemoteSource(ResultHistorySportSummarizingData remoteSource) {

            }
        }.getAsNetObservable();
    }

    public Observable<IphoneSportListVo> getWeekTotal(long times) {
        return new NetworkBoundResource<IphoneSportListVo>() {

            @Override
            public Observable<IphoneSportListVo> getFromDb() {
                return null;
            }

            @Override
            public Observable<IphoneSportListVo> getNoCacheData() {
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
            public Observable<IphoneSportListVo> getRemoteSource() {
                InitCommonParms<IphoneSportListVo, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                //http://192.168.10.203:8767/isport/concumer-wristband/wristband/iphoneSport/selectWeekData?userId=1&time=1
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTWEEKDATA;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(App.getApp());
                baseUrl.extend1 = times + "";
                return (Observable<IphoneSportListVo>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_SPORT_HISTORY_WEEK_SUMMAR_DATA).getPostBody());
            }

            @Override
            public void saveRemoteSource(IphoneSportListVo remoteSource) {

            }
        }.getAsObservable();

    }

    public Observable<IphoneSportListVo> getMonthTotal(long times) {
        return new NetworkBoundResource<IphoneSportListVo>() {

            @Override
            public Observable<IphoneSportListVo> getFromDb() {
                return null;
            }

            @Override
            public Observable<IphoneSportListVo> getNoCacheData() {
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
            public Observable<IphoneSportListVo> getRemoteSource() {
                InitCommonParms<IphoneSportWeekVo, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                //http://192.168.10.203:8767/isport/concumer-wristband/wristband/iphoneSport/selectMonthData?userId=104&time=1551434180000
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTMONTHDATA;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(App.getApp());
                baseUrl.extend1 = times + "";
                return (Observable<IphoneSportListVo>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_SPORT_HISTORY_MONTH_SUMMAR_DATA).getPostBody());
            }

            @Override
            public void saveRemoteSource(IphoneSportListVo remoteSource) {

            }
        }.getAsObservable();
    }


    public Observable<ResultHistorySportSummarizingData> getTotal() {
        return new NetworkBoundResource<ResultHistorySportSummarizingData>() {

            @Override
            public Observable<ResultHistorySportSummarizingData> getFromDb() {
                return null;
            }

            @Override
            public Observable<ResultHistorySportSummarizingData> getNoCacheData() {
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
            public Observable<ResultHistorySportSummarizingData> getRemoteSource() {
                InitCommonParms<SportSumData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTTOTAL;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(App.getApp());
                return (Observable<ResultHistorySportSummarizingData>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_SPORT_HISTORY_SUMMAR_DATA).getPostBody());
            }

            @Override
            public void saveRemoteSource(ResultHistorySportSummarizingData remoteSource) {

            }
        }.getAsObservable();
    }


    public Observable<SportLastDataBeanList> getLastData() {
        return new NetworkBoundResource<SportLastDataBeanList>() {

            @Override
            public Observable<SportLastDataBeanList> getFromDb() {
                return null;
            }

            @Override
            public Observable<SportLastDataBeanList> getNoCacheData() {
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
            public Observable<SportLastDataBeanList> getRemoteSource() {
                InitCommonParms<SportSumData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTLASTALL;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(App.getApp());
                return (Observable<SportLastDataBeanList>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_SPORT_LAST_ALL).getPostBody());
            }

            @Override
            public void saveRemoteSource(SportLastDataBeanList remoteSource) {

            }
        }.getAsObservable();
    }

    public Observable<ResultSportHistroyList> getHistory(String userid, String offset) {
        return new NetworkBoundResource<ResultSportHistroyList>() {

            @Override
            public Observable<ResultSportHistroyList> getFromDb() {
                return null;
            }

            @Override
            public Observable<ResultSportHistroyList> getNoCacheData() {
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
            public Observable<ResultSportHistroyList> getRemoteSource() {
                InitCommonParms<SportSumData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTDATEBYUSERID;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(App.getApp());
                baseUrl.extend1 = offset;
                return (Observable<ResultSportHistroyList>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_SPORT_HISTORY_DATA).getPostBody());
            }

            @Override
            public void saveRemoteSource(ResultSportHistroyList remoteSource) {

            }
        }.getAsObservable();
    }

    public Observable<ResultSportHistroyList> getHistoryWeek(String userid, long time) {
        return new NetworkBoundResource<ResultSportHistroyList>() {

            @Override
            public Observable<ResultSportHistroyList> getFromDb() {
                return null;
            }

            @Override
            public Observable<ResultSportHistroyList> getNoCacheData() {
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
            public Observable<ResultSportHistroyList> getRemoteSource() {
                //http://192.168.10.203:8767/isport/concumer-wristband/wristband/iphoneSport/selectWeekDataList?userId=1&time=1
                InitCommonParms<SportSumData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTWEEKDATALIST;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(App.getApp());
                baseUrl.extend1 = time + "";
                return (Observable<ResultSportHistroyList>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_SPORT_HISTORY_WEEK_DATA).getPostBody());
            }

            @Override
            public void saveRemoteSource(ResultSportHistroyList remoteSource) {

            }
        }.getAsObservable();
    }


    public Observable<ResultSportHistroyList> getHistoryMonth(String userid, long time) {
        return new NetworkBoundResource<ResultSportHistroyList>() {

            @Override
            public Observable<ResultSportHistroyList> getFromDb() {
                return null;
            }

            @Override
            public Observable<ResultSportHistroyList> getNoCacheData() {
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
            public Observable<ResultSportHistroyList> getRemoteSource() {
                //http://192.168.10.203:8767/isport/concumer-wristband/wristband/iphoneSport/selectWeekDataList?userId=1&time=1
                InitCommonParms<SportSumData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTMONTHDATALIST;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(App.getApp());
                baseUrl.extend1 = time + "";
                return (Observable<ResultSportHistroyList>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_SPORT_HISTORY_MONTH_DATA).getPostBody());
            }

            @Override
            public void saveRemoteSource(ResultSportHistroyList remoteSource) {

            }
        }.getAsObservable();
    }


    public static Observable<UpdateSuccessBean> addSportSummarrequst(SportSumData
                                                                             sportSumData) {
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

                InitCommonParms<SportSumData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.INSERTSELECTIVE;

                //POST /wristband/iphoneSport/insertSelective
                return (Observable<UpdateSuccessBean>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setParms(sportSumData).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.ADD_SPORT_SUMMER).getPostBody());
//                }
//                return null;
            }

            @Override
            public void saveRemoteSource(UpdateSuccessBean remoteSource) {

            }
        }.getAsObservable();
    }

    public static Observable<SportSumData> getSportByPrimaryKey(String publicshId) {
        return new NetworkBoundResource<SportSumData>() {
            @Override
            public Observable<SportSumData> getFromDb() {
                return null;
            }

            @Override
            public Observable<SportSumData> getNoCacheData() {
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
            public Observable<SportSumData> getRemoteSource() {

                InitCommonParms<SportSumData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTBYPRIMARYKEY;
                baseUrl.userid = publicshId;
                return (Observable<SportSumData>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_BYPRIMARYKEY_SPORT_DATA).getPostBody());
//                }
//                return null;
            }

            @Override
            public void saveRemoteSource(SportSumData remoteSource) {

            }
        }.getAsObservable();
    }

    public static Observable<String> getMainHomeSportData() {
        return new NetworkBoundResource<String>() {
            @Override
            public Observable<String> getFromDb() {
                return null;
            }

            @Override
            public Observable<String> getNoCacheData() {
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
            public Observable<String> getRemoteSource() {

                InitCommonParms<String, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTHOMEDATE;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp());
                return (Observable<String>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.HOME_DATA_SPORT).getPostBody());
//                }
//                return null;
            }

            @Override
            public void saveRemoteSource(String remoteSource) {

            }
        }.getAsObservable();
    }

    public static Observable<String> addSportDetail(SportDetailData
                                                            sportSumData) {
        return new NetworkBoundResource<String>() {
            @Override
            public Observable<String> getFromDb() {
                return null;
            }

            @Override
            public Observable<String> getNoCacheData() {
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
            public Observable<String> getRemoteSource() {

                InitCommonParms<SportDetailData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.IPHONESPORTDETAIL;
                baseUrl.url3 = JkConfiguration.Url.INSERTSELECTIVE;
                return (Observable<String>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setParms(sportSumData).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.ADD_SPORT_DETAIL).getPostBody());
//                }
//                return null;
            }

            @Override
            public void saveRemoteSource(String remoteSource) {

            }
        }.getAsObservable();
    }


}
