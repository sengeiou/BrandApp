package phone.gym.jkcq.com.socialmodule.personal.view;

import brandapp.isport.com.basicres.mvp.BaseView;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;

public interface PersonalVideoView extends BaseView {


    public void successPersonalVideo(ListData<DynamBean> dynamBeanListData);

    public void successDeleteAction(Boolean status);

}
