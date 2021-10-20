package com.own.freshtoyou.view;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.own.freshtoyou.R;
import com.own.freshtoyou.base.BaseFragment;
import com.own.freshtoyou.data.RAM;
import com.own.freshtoyou.data.User;
import com.own.freshtoyou.data.UserManager;

import static android.content.Context.MODE_PRIVATE;

public class ownModeFragment extends BaseFragment {

    private EditText ts_tem_set;

    private TextView own_t_text;
    private TextView own_h_text;

    @Override
    public void initView() {
        ts_tem_set = getActivity().findViewById(R.id.own_tem_trans_edit);
        own_h_text = getActivity().findViewById(R.id.own_h_text);
        own_t_text = getActivity().findViewById(R.id.own_t_text);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                own_t_text.setText(RAM.getT());
                                own_h_text.setText(RAM.getH());
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        ts_tem_set.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setTsTem();
                }
                return false;
            }
        });

    }

    @Override
    public void initListener() {
    }

    private void setTsTem(){
//        SharedPreferences.Editor editor = getActivity().getSharedPreferences("sets",MODE_PRIVATE).edit();
//        editor.putString("ts_tem_set",ts_tem_set.getText().toString());
//        editor.apply();
        RAM.setSet_ts_t(ts_tem_set.getText().toString());
    }

    @Override
    public int getContentViewID() {
        return R.layout.own_mode_layout;
    }

    @Override
    public void destroy() {

    }
}
