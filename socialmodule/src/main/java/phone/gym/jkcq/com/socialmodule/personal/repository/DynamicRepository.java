package phone.gym.jkcq.com.socialmodule.personal.repository;

import com.google.gson.Gson;

import java.util.List;

import brandapp.isport.com.basicres.commonnet.net.RxScheduler;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.requst.RequestAddDynamicBean;
import phone.gym.jkcq.com.socialmodule.bean.requst.RequestDynamicBean;
import phone.gym.jkcq.com.socialmodule.bean.requst.RequestDynamicHomeBean;
import phone.gym.jkcq.com.socialmodule.bean.requst.RequestNextHomeBean;
import phone.gym.jkcq.com.socialmodule.bean.requst.RequsestDynamicPraise;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultLikeBean;
import phone.gym.jkcq.com.socialmodule.net.APIService;
import phone.gym.jkcq.com.socialmodule.net.RetrofitClient;

public class DynamicRepository {
    /**
     * 获取主页的动态
     */

    public static Observable<List<DynamBean>> getCommunityDynamic(String userId, int dataNumbers, int dynamicInfoType) {
        return new NetworkBoundResource<List<DynamBean>>() {
            @Override
            public Observable<List<DynamBean>> getFromDb() {
                return null;
            }

            @Override
            public Observable<List<DynamBean>> getNoCacheData() {
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
            public Observable<List<DynamBean>> getRemoteSource() {

                RequestBody requestBody = null;
                RequestDynamicHomeBean requset = new RequestDynamicHomeBean();
                requset.setMeUserId(userId);
                requset.setDataNumbs(dataNumbers);
                requset.setDynamicInfoType(dynamicInfoType);
                Gson gson = new Gson();
                requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(requset));

                return RetrofitClient.getRetrofit().create(APIService.class).getCommunityDynamic(requestBody).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(List<DynamBean> bean) {

            }
        }.getAsObservable();

    }

    /**
     * 获取当前数据的向上或者向下的动态
     */

    public static Observable<List<DynamBean>> getNextCommunityDynamic(String userId,String dynamicInfoId,int direction,int dataNumbers, int dynamicInfoType) {
        return new NetworkBoundResource<List<DynamBean>>() {
            @Override
            public Observable<List<DynamBean>> getFromDb() {
                return null;
            }

            @Override
            public Observable<List<DynamBean>> getNoCacheData() {
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
            public Observable<List<DynamBean>> getRemoteSource() {

                RequestBody requestBody = null;
                RequestNextHomeBean requset = new RequestNextHomeBean();
                requset.setMeUserId(userId);
                requset.setDataNums(dataNumbers);
                requset.setDirection(direction);
                requset.setDynamicInfoId(dynamicInfoId);
                requset.setDynamicInfoType(dynamicInfoType);
                Gson gson = new Gson();
                requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(requset));

                return RetrofitClient.getRetrofit().create(APIService.class).getCommunityDynamic(requestBody).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(List<DynamBean> bean) {

            }
        }.getAsObservable();

    }

    /**
     * 获取我的页面的动态
     */
    public static Observable<ListData<DynamBean>> getHomePageDynamic(String userId, int size, int page, int videoType) {
        return new NetworkBoundResource<ListData<DynamBean>>() {
            @Override
            public Observable<ListData<DynamBean>> getFromDb() {
                return null;
            }

            @Override
            public Observable<ListData<DynamBean>> getNoCacheData() {
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
            public Observable<ListData<DynamBean>> getRemoteSource() {

                RequestBody requestBody = null;
                RequestDynamicBean requset = new RequestDynamicBean();
                requset.setUserId(userId);
                requset.setVideoType(videoType);
                requset.setSize(size);
                requset.setPage(page);
                Gson gson = new Gson();
                requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(requset));

                return RetrofitClient.getRetrofit().create(APIService.class).getHomePageDynamic(requestBody).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(ListData<DynamBean> bean) {

            }
        }.getAsObservable();

    }


    /**
     * 点赞功能
     *
     * @param
     * @return
     */
    public static Observable<ResultLikeBean> dynamicPraise(String dynamicId, String userId, String toUserId) {
        return new NetworkBoundResource<ResultLikeBean>() {
            @Override
            public Observable<ResultLikeBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<ResultLikeBean> getNoCacheData() {
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
            public Observable<ResultLikeBean> getRemoteSource() {

                RequestBody requestBody = null;
                RequsestDynamicPraise requsetDynamicPraise = new RequsestDynamicPraise();
                requsetDynamicPraise.setDynamicInfoId(dynamicId);
                requsetDynamicPraise.setMeUserId(userId);
                requsetDynamicPraise.setToUserId(toUserId);
                Gson gson = new Gson();
                requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(requsetDynamicPraise));

                return RetrofitClient.getRetrofit().create(APIService.class).optionDynamicPraise(requestBody).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(ResultLikeBean bean) {

            }
        }.getAsObservable();

    }

    /**
     * 举报功能
     *
     * @param
     * @return
     */
    public static Observable<Integer> reportDynamic(String dynamicId, int reportType) {
        return new NetworkBoundResource<Integer>() {
            @Override
            public Observable<Integer> getFromDb() {
                return null;
            }

            @Override
            public Observable<Integer> getNoCacheData() {
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
            public Observable<Integer> getRemoteSource() {


                return RetrofitClient.getRetrofit().create(APIService.class).optionReportDynamic(dynamicId, reportType).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(Integer bean) {

            }
        }.getAsObservable();

    }


    /**
     * 删除动态
     *
     * @param
     * @return
     */
    public static Observable<Boolean> deleteDynamic(String dynamicId) {
        return new NetworkBoundResource<Boolean>() {
            @Override
            public Observable<Boolean> getFromDb() {
                return null;
            }

            @Override
            public Observable<Boolean> getNoCacheData() {
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
            public Observable<Boolean> getRemoteSource() {


                return RetrofitClient.getRetrofit().create(APIService.class).deletDynamic(dynamicId).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(Boolean bean) {

            }
        }.getAsObservable();

    }

    /**
     * 发布动态
     *
     * @param
     * @return
     */
    public static Observable<String> sendNewDynamic(String userId, String content, String coverUrl, String videoUrl) {
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
                RequestBody requestBody = null;
                RequestAddDynamicBean requsetDynamicPraise = new RequestAddDynamicBean();
                requsetDynamicPraise.setUserId(userId);
                requsetDynamicPraise.setContent(content);
                requsetDynamicPraise.setVideoUrl(videoUrl);
                requsetDynamicPraise.setCoverUrl(coverUrl);
                Gson gson = new Gson();
                requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(requsetDynamicPraise));

                return RetrofitClient.getRetrofit().create(APIService.class).addNewDynamic(requestBody).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(String bean) {

            }
        }.getAsObservable();

    }
}
