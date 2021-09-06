package com.isport.brandapp.repository;


import android.util.Log;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.managers.Constants;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.parm.http.EditeUserParm;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import brandapp.isport.com.basicres.action.BaseAction;
import brandapp.isport.com.basicres.action.UserInformationBeanAction;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.entry.UserInformationBean;
import brandapp.isport.com.basicres.gen.UserInformationBeanDao;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class CustomRepository<T, T1, T2, T3> {


    private static final String TAG = CustomRepository.class.getSimpleName();

    public Observable<T> requst(PostBody<T1, T2, T3> postBody, boolean isRetureString) {
        return new NetworkBoundResource<T>() {
            @Override
            public Observable<T> getFromDb() {
                if (!isRetureString) {
                    return Observable.create(new ObservableOnSubscribe<T>() {
                        @Override
                        public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                            //获取用户信息
                            Log.e(TAG, "-1");
                            UserInformationBean userInfoByUserId = UserInformationBeanAction.findUserInfoByUserId(Constants.defUserId);
                            UserInfoBean loginBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(UIUtils.getContext()));
                            Log.e(TAG, "0");
                            UserInformationBean userInformationBean;
                            UserInfoBean userInfoBean = null;
                            if (userInfoByUserId != null) {
                                Log.e(TAG, "1");
                                //查询数据库，返回userInfo
                                userInfoBean = new UserInfoBean();
                                userInfoBean.setBirthday(userInfoByUserId.getBirthday());
                                userInfoBean.setNickName(userInfoByUserId.getNickName());
                                userInfoBean.setGender(userInfoByUserId.getGender());
                                userInfoBean.setHeight(userInfoByUserId.getBodyHeight() + "");
                                userInfoBean.setWeight(userInfoByUserId.getBodyWeight() + "");
                                userInfoBean.setUserId(Constants.defUserId);
                                userInfoBean.setHeadUrl(userInfoByUserId.getHeadImage());
                                userInfoBean.setHeadUrlTiny(userInfoByUserId.getHeadImage_s());
                            } else {
                                Log.e(TAG, "2");
                                //数据库中没有，则存储
                                userInformationBean = new UserInformationBean();
                                userInformationBean.setBirthday("1990-01-01");
                                userInformationBean.setBodyHeight(170);
                                userInformationBean.setBodyWeight(60);
                                userInformationBean.setGender("Male");
                                userInformationBean.setAge(TimeUtils.getAge("1990-01-01"));
                                userInformationBean.setUserId(Constants.defUserId);
                                userInformationBean.setId((long) 0);//本地用户默认使用0为id
                                userInformationBean.setUseNetwork(false);
                                //没有时存储默认
                                userInfoBean = new UserInfoBean();
                                userInfoBean.setBirthday("1990-01-01");
                                userInfoBean.setGender("Male");
                                userInfoBean.setHeight("170");
                                userInfoBean.setWeight("60");
                                userInfoBean.setUserId(Constants.defUserId);
                                Log.e(TAG, "3");
                                if (loginBean != null) {
                                    userInfoBean.setHeadUrl(loginBean.getHeadUrl());
                                    userInfoBean.setHeadUrlTiny(loginBean.getHeadUrlTiny());
                                    userInformationBean.setHeadImage(loginBean.getHeadUrl());
                                    userInformationBean.setHeadImage_s(loginBean.getHeadUrlTiny());
                                }
                                UserInformationBeanDao userInformationBeanDao = BaseAction.getUserInformationBeanDao();
                                try {
                                    userInformationBeanDao.insertOrReplace(userInformationBean);
                                } catch (Exception e) {
                                    Log.e(TAG, "Exception 11 " + e.toString());
                                }

                            }
                            AppConfiguration.saveUserInfo(userInfoBean);
                            emitter.onNext((T) userInfoBean);
                            Log.e(TAG, "4");
                        }
                    });
                } else {
                    //本地设置用户信息
                    return Observable.create(new ObservableOnSubscribe<T>() {
                        @Override
                        public void subscribe(ObservableEmitter<T> emitter) throws Exception {

                            PostBody<EditeUserParm, BaseUrl, BaseDbPar> postBody1 = (PostBody<EditeUserParm, BaseUrl,
                                    BaseDbPar>) postBody;
                            //存储更新用户信息
                            UserInformationBean userInfoByUserId = UserInformationBeanAction.findUserInfoByUserId(Constants.defUserId);
                            UserInformationBeanDao userInformationBeanDao = BaseAction.getUserInformationBeanDao();
                            UserInfoBean loginBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(UIUtils.getContext()));

                            Log.e(TAG, "00");
                            if (userInfoByUserId != null) {
                                Log.e(TAG, "11");
                                //查询数据库，有则更新
                                userInfoByUserId.setBirthday(postBody1.data.birthday);
                                userInfoByUserId.setBodyHeight(Integer.parseInt(postBody1.data.height));
                                userInfoByUserId.setBodyWeight(Integer.parseInt(postBody1.data.weight));
                                userInfoByUserId.setGender(postBody1.data.gender);
                                userInfoByUserId.setAge(TimeUtils.getAge(postBody1.data.birthday));
                                userInfoByUserId.setUserId(Constants.defUserId);
                                userInfoByUserId.setUseNetwork(false);
                                userInfoByUserId.setNickName(postBody1.data.nickName);
                                userInfoByUserId.setHeadImage(loginBean.getHeadUrl());
                                userInfoByUserId.setHeadImage_s(loginBean.getHeadUrlTiny());
                                userInformationBeanDao.update(userInfoByUserId);
                                //设置完成，进入首页
                                AppSP.putBoolean(UIUtils.getContext(), AppSP.IS_FIRST, false);
                                String[] split = postBody1.data.birthday.split("-");
                                String weight = postBody1.data.weight.contains(".") ? postBody1.data.weight.split("\\.")[0] : postBody1.data.weight;
                                //设置用户信息到SDK
                                ISportAgent.getInstance().setUserInfo(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(weight), Float.parseFloat(postBody1.data.height),
                                        postBody1.data.gender.equals("Male") ? 1 : 0,
                                        TimeUtils.getAge(postBody1.data.birthday), Constants.defUserId);
                                //缓存到本地
                                UserInfoBean userInfoBean = new UserInfoBean();
                                userInfoBean.setBirthday(postBody1.data.birthday);
                                userInfoBean.setGender(postBody1.data.gender);
                                userInfoBean.setHeight(postBody1.data.height);
                                userInfoBean.setWeight(postBody1.data.weight);
                                userInfoBean.setHeadUrl(loginBean.getHeadUrl());
                                userInfoBean.setHeadUrlTiny(loginBean.getHeadUrl());
                                userInfoBean.setUserId(Constants.defUserId);
                                AppConfiguration.saveUserInfo(userInfoBean);
                            }
                            emitter.onNext((T) postBody1.data.nickName);
                            Log.e(TAG, "44");
                        }
                    });
                }
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
                return (Observable<T>) RetrofitClient.getInstance().post(postBody, isRetureString);
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


    public Observable<UserInfoBean> requstData(PostBody<T1, T2, T3> postBody, String url, boolean isRetureString) {
        return new NetworkBoundResource<UserInfoBean>() {
            @Override
            public Observable<UserInfoBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<UserInfoBean> getNoCacheData() {
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
            public Observable<UserInfoBean> getRemoteSource() {
                return (Observable<UserInfoBean>) RetrofitClient.getInstance().post(postBody, isRetureString);
            }

            @Override
            public void saveRemoteSource(UserInfoBean remoteSource) {

            }
        }.getAsObservable();
    }

    /**
     * 下载记录，可首页，可历史
     *
     * @return
     */
    public Observable<T> requst(PostBody<T1, T2, T3> postBody) {
        return new NetworkBoundResource<T>() {
            @Override
            public Observable<T> getFromDb() {
                return null;
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
                return false;
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
