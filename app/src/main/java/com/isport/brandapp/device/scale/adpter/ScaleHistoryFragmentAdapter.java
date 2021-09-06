package com.isport.brandapp.device.scale.adpter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.isport.brandapp.device.scale.ScaleCommon;
import com.isport.brandapp.device.scale.fragment.ScaleMonthHistoryFragment;

public class ScaleHistoryFragmentAdapter extends FragmentStateAdapter {


    public ScaleHistoryFragmentAdapter(FragmentActivity fragmentActivit) {
        super(fragmentActivit);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ScaleMonthHistoryFragment.newInstance(ScaleCommon.list.get(position));


//        if (position == 0) {
//            mFragmentList.add(SportRankFragment.newInstance(userId, ""));
//            return mFragmentList.get(0);
//
//        } else if (position == 1) {
//            mFragmentList.add(SportRankFragment.newInstance(userId, ""));
//            return mFragmentList.get(1);
//        }
    }


    @Override
    public int getItemCount() {
        return ScaleCommon.list.size();
    }

}
