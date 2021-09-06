package phone.gym.jkcq.com.socialmodule.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.socialmodule.fragment.MineFragment;
import phone.gym.jkcq.com.socialmodule.fragment.PageAllFragment;

public class HorizontalAdapter extends FragmentStateAdapter {

    String url = "";

    public HorizontalAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            PageAllFragment.newInstance(url, position, JkConfiguration.DynamicInfoType.FOLLOW);

        } else if (position == 1) {
            PageAllFragment.newInstance(url, position, JkConfiguration.DynamicInfoType.ALL);
        } else {
            MineFragment.newInstance(url, position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
