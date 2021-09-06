package phone.gym.jkcq.com.socialmodule.report.view;

import brandapp.isport.com.basicres.mvp.BaseView;
import phone.gym.jkcq.com.socialmodule.bean.ListDataCommend;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultCommentBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultLikeBean;

public interface CommentView extends BaseView {


    public void successAddCommend();
    public void failAddCommend();

    public void successDelCommend(int commendnumb,int allNumber);
    public void failDelCommend();

    public void successGetCommend(ResultCommentBean<ListDataCommend> data,String positionDynamicCommentId);
    public void failGetCommend();


    public void successLike(ResultLikeBean resultLikeBean,boolean isTop,String dynamicCommentId);

}
