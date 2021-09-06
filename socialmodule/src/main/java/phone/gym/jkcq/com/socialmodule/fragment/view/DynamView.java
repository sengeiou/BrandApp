package phone.gym.jkcq.com.socialmodule.fragment.view;

import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;

public interface DynamView extends BaseView {


    public void succcessDynamList(List<DynamBean> list);
    public void failFirstDynamList();
    public void firstNoContentDynamList();
    public void succcessUpDynamList(List<DynamBean> list);
    public void succcessNextDynamList(List<DynamBean> list);
}
