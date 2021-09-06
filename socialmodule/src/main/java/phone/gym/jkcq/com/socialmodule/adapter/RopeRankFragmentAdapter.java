package phone.gym.jkcq.com.socialmodule.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.report.ranking.RopeRankFragment;

public class RopeRankFragmentAdapter extends FragmentStateAdapter {

    String userId;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    public RopeRankFragmentAdapter(FragmentActivity fragmentActivit) {
        super(fragmentActivit);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return RopeRankFragment.newInstance(FriendConstant.RANK_RUN_INSIDE, "");
            case 1:
                return RopeRankFragment.newInstance(FriendConstant.RANK_RIDE, "");


        }
//        if (position == 0) {
//            mFragmentList.add(SportRankFragment.newInstance(userId, ""));
//            return mFragmentList.get(0);
//
//        } else if (position == 1) {
//            mFragmentList.add(SportRankFragment.newInstance(userId, ""));
//            return mFragmentList.get(1);
//        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public ArrayList<Fragment> getFragmentList() {
        return mFragmentList;
    }
}
