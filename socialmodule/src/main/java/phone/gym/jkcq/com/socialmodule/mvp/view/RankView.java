package phone.gym.jkcq.com.socialmodule.mvp.view;

import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;
import phone.gym.jkcq.com.socialmodule.bean.PraiseInfo;
import phone.gym.jkcq.com.socialmodule.bean.RankInfo;

public interface RankView extends BaseView {

     void onSuccessPraise(PraiseInfo info);
     void getRankInfoSuccess(List<RankInfo> listData);
}
