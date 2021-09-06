package phone.gym.jkcq.com.socialmodule.personal.view;

import brandapp.isport.com.basicres.entry.bean.UpdatePhotoBean;
import brandapp.isport.com.basicres.mvp.BaseView;

public interface UpgradeImageView extends BaseView {


    public void successSaveImageUrl(String url);

    public void successSaveHeadUrl(UpdatePhotoBean bean);

    public void successOption();

}
