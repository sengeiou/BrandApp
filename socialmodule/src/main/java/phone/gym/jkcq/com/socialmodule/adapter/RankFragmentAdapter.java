package phone.gym.jkcq.com.socialmodule.adapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.fragment.SportRankFragment;

public class RankFragmentAdapter extends FragmentStateAdapter {

    String userId;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    public RankFragmentAdapter(FragmentActivity fragmentActivit) {
        super(fragmentActivit);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return SportRankFragment.newInstance(FriendConstant.RANK_STEPS, "");
            case 1:
                return SportRankFragment.newInstance(FriendConstant.RANK_CAL, "");
            case 2:
                return SportRankFragment.newInstance(FriendConstant.RANK_RUN_OUTSIDE, "");
            case 3:
                return SportRankFragment.newInstance(FriendConstant.RANK_RUN_INSIDE, "");
            case 4:
                return SportRankFragment.newInstance(FriendConstant.RANK_RIDE, "");
            case 5:
                return SportRankFragment.newInstance(FriendConstant.RANK_WALK, "");

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
        return 6;
    }

    public ArrayList<Fragment> getFragmentList(){
        return  mFragmentList;
    }
}
