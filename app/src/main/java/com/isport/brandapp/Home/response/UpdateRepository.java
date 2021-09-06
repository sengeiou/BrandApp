package com.isport.brandapp.Home.response;

/**
 * @创建者 bear
 * @创建时间 2019/3/30 11:34
 * @描述
 */
public class UpdateRepository {

//    public static Observable<String> updateWatchHistoryData() {
//        return new NetworkBoundResource<String>() {
//            @Override
//            public Observable<String> getFromDb() {
//                return null;
//            }
//
//            @Override
//            public Observable<String> getNoCacheData() {
//                return null;
//            }
//
//            @Override
//            public boolean shouldFetchRemoteSource() {
//                return false;
//            }
//
//            @Override
//            public boolean shouldStandAlone() {
//                return false;
//            }
//
//            @Override
//            public Observable<String> getRemoteSource() {
//
//                InitCommonParms<SportDetailData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
//                BaseUrl baseUrl = new BaseUrl();
//                baseUrl.url1 = JkConfiguration.Url.SPORT;
//                baseUrl.url2 = JkConfiguration.Url.IPHONESPORTDETAIL;
//                baseUrl.url3 = JkConfiguration.Url.INSERTSELECTIVE;
//                return (Observable<String>) RetrofitClient.getInstance().post(parInitCommonParms
//                        .setPostBody
//                                (!(App.appType() == App.httpType)).setParms(sportSumData).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.ADD_SPORT_DETAIL).getPostBody());
////                }
////                return null;
//            }
//
//            @Override
//            public void saveRemoteSource(String remoteSource) {
//
//            }
//        }.getAsObservable();
//    }
}
