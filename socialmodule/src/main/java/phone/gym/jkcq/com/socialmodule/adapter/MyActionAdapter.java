package phone.gym.jkcq.com.socialmodule.adapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.socialmodule.fragment.AbsActionFragment;
import phone.gym.jkcq.com.socialmodule.fragment.LikeFragment;
import phone.gym.jkcq.com.socialmodule.fragment.ProductionFragment;

public class MyActionAdapter extends FragmentStateAdapter {

    String userId;
    private ArrayList<AbsActionFragment> mFragmentList = new ArrayList<>();

    public MyActionAdapter(FragmentActivity fragmentActivity, String userId) {
        super(fragmentActivity);
        this.userId = userId;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            mFragmentList.add(ProductionFragment.newInstance(userId, JkConfiguration.DynamicInfoType.PRODUCTION));
            return mFragmentList.get(0);

        } else if (position == 1) {
            mFragmentList.add(LikeFragment.newInstance(userId, JkConfiguration.DynamicInfoType.LIKE));
            return mFragmentList.get(1);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public ArrayList<AbsActionFragment> getFragmentList(){
        return  mFragmentList;
    }
}
