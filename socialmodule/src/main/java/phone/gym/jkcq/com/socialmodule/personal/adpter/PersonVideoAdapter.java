package phone.gym.jkcq.com.socialmodule.personal.adpter;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;
import phone.gym.jkcq.com.socialmodule.fragment.VideoPersonalFragment;

public class PersonVideoAdapter extends FragmentStateAdapter {

    String userId;

    List<DynamBean> list;

    public PersonVideoAdapter(FragmentActivity fragmentActivity, List<DynamBean> list, String userId) {
        super(fragmentActivity);
        this.list = list;
        this.userId = userId;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("PersonVideoAdapter", "createFragment:" + position);
        return VideoPersonalFragment.newInstance(list.get(position), JkConfiguration.DynamicInfoType.PRODUCTION, userId);
        //return VideoAllFragment.newInstance(list.get(position), JkConfiguration.DynamicInfoType.PRODUCTION);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
