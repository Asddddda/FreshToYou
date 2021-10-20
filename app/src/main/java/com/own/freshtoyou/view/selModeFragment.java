package com.own.freshtoyou.view;

import android.widget.TextView;

import com.own.freshtoyou.R;
import com.own.freshtoyou.base.BaseFragment;
import com.own.freshtoyou.data.RAM;

public class selModeFragment extends BaseFragment {
    private TextView sel_t_text;
    private TextView sel_h_text;

    @Override
    public void initView() {
        sel_t_text = getActivity().findViewById(R.id.sel_t_text);
        sel_h_text = getActivity().findViewById(R.id.sel_h_text);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sel_t_text.setText(RAM.getT());
                                sel_h_text.setText(RAM.getH());
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void initListener() {

    }

    @Override
    public int getContentViewID() {
        return R.layout.sel_mode_layout;
    }

    @Override
    public void destroy() {

    }
}
