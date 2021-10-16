package com.own.freshtoyou.presenter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.own.freshtoyou.view.ownModeFragment;
import com.own.freshtoyou.view.selModeFragment;

public class ConAdaptor extends FragmentPagerAdapter {
    public ConAdaptor(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new selModeFragment();
        } else {
            return new ownModeFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
