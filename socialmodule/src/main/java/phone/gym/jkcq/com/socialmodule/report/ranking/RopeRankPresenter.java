package phone.gym.jkcq.com.socialmodule.report.ranking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.socialmodule.bean.PraiseInfo;
import phone.gym.jkcq.com.socialmodule.bean.RankInfo;
import phone.gym.jkcq.com.socialmodule.personal.repository.UserRepository;

public class RopeRankPresenter extends BasePresenter<RopeRankView> {

    private RopeRankView mRankView;

    public RopeRankPresenter(RopeRankView friendView) {
        this.mRankView = friendView;
    }

    /**
     * 排行点赞
     */
    public void PraiseRank(String meUserId, String toUserId, int motionType, int timeType) {
        Map<String, Object> map = new HashMap();
        map.put("meUserId", meUserId);
        map.put("toUserId", toUserId);
        map.put("motionType", motionType);
        map.put("timeType", timeType);
        UserRepository.praiseRank(map).as(mRankView.bindAutoDispose()).subscribe(new BaseObserver<PraiseInfo>(BaseApp.getApp()) {
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
            public void onNext(PraiseInfo info) {
                mRankView.onSuccessPraise(info);
            }
        });
    }

    /**
     * 获取排行信息
     */
    public void getRankInfo(String day, String tye, String userId) {
        UserRepository.getRopeRankInfo(day, tye, userId).as(mRankView.bindAutoDispose()).subscribe(new BaseObserver<List<RankInfo>>(BaseApp.getApp()) {
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
            public void onNext(List<RankInfo> info) {
                mRankView.getRankInfoSuccess(info);
            }
        });
    }
}
