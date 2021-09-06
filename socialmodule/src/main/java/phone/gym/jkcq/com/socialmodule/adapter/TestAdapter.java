package phone.gym.jkcq.com.socialmodule.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TestAdapter extends FragmentStateAdapter {
    public TestAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public TestAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public TestAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return  new TestFragment();
            case 1:
                return  new TestFragment();
            case 2:
                return  new TestFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }



}
