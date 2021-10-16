package com.own.freshtoyou.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.own.freshtoyou.R;
import com.own.freshtoyou.base.BaseFragment;
import com.own.freshtoyou.presenter.ConAdaptor;
import com.own.freshtoyou.presenter.FragmentAdapter;
import com.own.freshtoyou.util.BusUtil.BusUtil;
import com.own.freshtoyou.util.BusUtil.EventUtil;
import com.own.freshtoyou.util.BusUtil.ThreadModel;
import com.own.freshtoyou.util.MyJSON.MyJSON;
import com.own.freshtoyou.view.others.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.own.freshtoyou.util.Other.makeStatusBarTransparent;

public class ConFragment extends BaseFragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String []heads = {"选择运输模式","自定义模式"};

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        viewPager = getActivity().findViewById(R.id.con_viewpager_content);
        tabLayout = getActivity().findViewById(R.id.con_heads);
        viewPager.setAdapter(new ConAdaptor(getChildFragmentManager(),0));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0;i<2;i++){
            tabLayout.getTabAt(i).setText(heads[i]);
        }
        tabLayout.getTabAt(0).setText(heads[0]);
    }

    @Override
    public void initListener() {
    }
    @Override
    public int getContentViewID() {
        return R.layout.con_layout;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        onDetach();
        destroy();
    }
}
