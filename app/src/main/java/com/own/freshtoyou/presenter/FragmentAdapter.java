package com.own.freshtoyou.presenter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.own.freshtoyou.view.CheckFragment;
import com.own.freshtoyou.view.ConFragment;
import com.own.freshtoyou.view.FunFragment;
import com.own.freshtoyou.view.PerFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new CheckFragment();
        } else if (position == 1){
            return new ConFragment();
        } else if (position == 2){
            return new FunFragment();
        } else {
            return new PerFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
