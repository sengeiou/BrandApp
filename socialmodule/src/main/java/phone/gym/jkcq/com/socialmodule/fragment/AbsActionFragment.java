package phone.gym.jkcq.com.socialmodule.fragment;

import brandapp.isport.com.basicres.BaseFragment;

public abstract class AbsActionFragment extends BaseFragment {


    public boolean isFirstResume=true;
    public abstract void loadMoreData();

    public abstract  void resetData();

    public void jumpToVideoActivity(){

    }

}
