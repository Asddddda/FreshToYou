package com.own.freshtoyou.view;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.own.freshtoyou.base.BaseActivity;
import com.own.freshtoyou.presenter.FragmentAdapter;
import com.own.freshtoyou.util.Other;
import com.google.android.material.tabs.TabLayout;
import com.own.freshtoyou.R;

import static com.own.freshtoyou.util.Other.makeStatusBarTransparent;

public class TableActivity extends BaseActivity {

    private final int[] TabRes = new int[]{R.drawable.check, R.drawable.con,R.drawable.fun,R.drawable.per};

    private final int[] selectTabRes = new int[]{R.drawable.check_c, R.drawable.con_c,R.drawable.fun_c,R.drawable.per};

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(actionBar!=null)
            actionBar.hide();
        if(ContextCompat.checkSelfPermission(TableActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TableActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            //这里会调用后面的onRequestPermissionResult
        }
    }

    @Override
    public void initView() {
//        makeStatusBarTransparent(this);
        actionBar = getSupportActionBar();
        viewPager = findViewById(R.id.viewpager_content);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),0));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0;i<4;i++){
            tabLayout.getTabAt(i).view.setBackgroundResource(TabRes[i]);
        }
        tabLayout.getTabAt(0).view.setBackgroundResource(R.drawable.check_c);

    }


    @Override
    public void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < 4; i++) {
                    if (tab == tabLayout.getTabAt(i)) {
                        tabLayout.getTabAt(i).view.setBackgroundResource(selectTabRes[i]);
                        viewPager.setCurrentItem(i);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                for (int i = 0; i < 4; i++) {
                    if (tab == tabLayout.getTabAt(i)) {
                        tabLayout.getTabAt(i).view.setBackgroundResource(TabRes[i]);
                    }
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

//    private View getIconView(int i, boolean pressed){
//        View view;
//        if(!pressed) {
//            view = getLayoutInflater().inflate(R.layout.customtab, null);
//            view.findViewById(R.id.icon).setBackgroundResource(TabRes[i]);
//        } else {
//            view = getLayoutInflater().inflate(R.layout.customtab, null);
//            view.findViewById(R.id.icon).setBackgroundResource(selectTabRes[i]);
//        }
//        return view;
//    }

    @Override
    public int getContentViewID() {
        return R.layout.table_layout;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onClick(View v) {

    }
}
