package phone.gym.jkcq.com.socialmodule.report.ranking;

import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;
import phone.gym.jkcq.com.socialmodule.bean.PraiseInfo;
import phone.gym.jkcq.com.socialmodule.bean.RankInfo;

public interface RopeRankView extends BaseView {

     void onSuccessPraise(PraiseInfo info);
     void getRankInfoSuccess(List<RankInfo> listData);
}
