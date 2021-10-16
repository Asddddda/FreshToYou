package com.own.freshtoyou.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.own.freshtoyou.R;
import com.own.freshtoyou.base.BaseFragment;
import com.own.freshtoyou.util.BusUtil.BusUtil;
import com.own.freshtoyou.util.BusUtil.EventUtil;
import com.own.freshtoyou.util.BusUtil.ThreadModel;
import com.own.freshtoyou.util.MyJSON.MyJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.own.freshtoyou.util.Other.makeStatusBarTransparent;

public class FunFragment extends BaseFragment{

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

    }

    @Override
    public void initListener() {
    }
    @Override
    public int getContentViewID() {
        return R.layout.fun_layout;
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
//        MyJSON myJSON = new MyJSON(this.getContext());
//        myJSON.request("Sendlostproperty.php", "");
//        BusUtil.getDefault().register(this);

    }
}
