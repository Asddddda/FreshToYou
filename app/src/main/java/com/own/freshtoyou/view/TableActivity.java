package com.own.freshtoyou.view;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.own.freshtoyou.base.BaseActivity;
import com.own.freshtoyou.data.RAM;
import com.own.freshtoyou.presenter.FragmentAdapter;
import com.own.freshtoyou.util.Other;
import com.google.android.material.tabs.TabLayout;
import com.own.freshtoyou.R;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static com.own.freshtoyou.util.Other.makeStatusBarTransparent;

public class TableActivity extends BaseActivity {

    private final int[] TabRes = new int[]{R.drawable.check, R.drawable.con,R.drawable.fun,R.drawable.per};

    private final int[] selectTabRes = new int[]{R.drawable.check_c, R.drawable.con_c,R.drawable.fun_c,R.drawable.per};

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private ActionBar actionBar;

    private ServerSocket serverForPCSocket;
    private Socket pcSocket;
    private ServerSocket serverForEspSocket;
    private Socket espSocket;

    private InputStream inputPCStream;
    private OutputStream outputPCStream;
    private InputStream inputEspStream;
    private OutputStream outputEspStream;

    private double t;
    private double expT;
    private double h;


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
    protected void onResume() {
        super.onResume();
        Dataing();
    }

    private void Dataing(){
        t=expT=h=0;
        ServerForPCThread pcThread = new ServerForPCThread();
        pcThread.start();
        ServerForEspThread espThread = new ServerForEspThread();
        espThread.start();
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

    @Override
    public int getContentViewID() {
        return R.layout.table_layout;
    }

    @Override
    public void destroy() { }

    @Override
    public void onClick(View v) { }

    class ServerForPCThread extends Thread{
        @Override
        public void run() {
            super.run();
            int port = 8888;
            try {
                serverForPCSocket = new ServerSocket(port);//监听port端口，这个程序的通信端口就是port了
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("!!!!!!","pc port fail");
            }
            while(true){
                try {
                    pcSocket = serverForPCSocket.accept();
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TableActivity.this,"PC CONNECTED",Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
//                    inputPCStream = pcSocket.getInputStream();
                    outputPCStream = pcSocket.getOutputStream();
                    //启动接收线程
                    SendPCThread sendPCThread = new SendPCThread();
//                    sendPCThread.setVal(t,h);
                    sendPCThread.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
    }

    class SendPCThread extends Thread{
        String temp = "";
//        private double t = 0;
//        private double h = 0;
//
//        private void setVal(double t,double h){
//            this.t = t;
//            this.h = h;
//        }

        @Override
        public void run() {
            super.run();
            while(true){
                OutputStreamWriter opsw = new OutputStreamWriter(outputPCStream);
                BufferedWriter bw = new BufferedWriter(opsw);
                try {
                    temp = "温度："+t+"℃  CO2浓度：18%  湿度："+h+"%  O2浓度：2.9%                                 ";
                    bw.write(temp+"d: "+t+"\n\n");
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ServerForEspThread extends Thread{
        @Override
        public void run() {
            super.run();
            int port = 8848;
            try {
                serverForEspSocket = new ServerSocket(port);//监听port端口，这个程序的通信端口就是port了
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("!!!!!!","esp sever fail");
            }
            while(true){
                try {
                    espSocket = serverForEspSocket.accept();
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TableActivity.this,"ESP CONNECTED",Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                    inputEspStream = espSocket.getInputStream();
                    outputEspStream = espSocket.getOutputStream();
                    //启动接收线程
                    ReceiveEspThread receiveEspThread = new ReceiveEspThread();
                    receiveEspThread.start();
                    SendEspThread sendEspThread = new SendEspThread();
                    sendEspThread.start();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(true){
                                t = receiveEspThread.getT();
                                h = receiveEspThread.getH();
                                RAM.setT(t+"");
                                RAM.setH(h+"");
                                sendEspThread.setTem(expT);
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
    }

    class SendEspThread extends Thread{
        private double t = 0;

        private void setTem(double t){
            this.t = t;
        }

//        private final SharedPreferences sp = getSharedPreferences("sets",MODE_PRIVATE);

        @Override
        public void run() {
            super.run();
            RAM.setSet_ts_t("10");
            while(true){
//                t = Double.parseDouble(sp.getString("ts_tem_set","2.5"));
                t = Double.parseDouble(RAM.getSet_ts_t());
                OutputStreamWriter opsw = new OutputStreamWriter(outputEspStream);
                BufferedWriter bw = new BufferedWriter(opsw);
                try {
                    bw.write(""+this.t);
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ReceiveEspThread extends Thread{
        private double t = 0;
        private double h = 0;

        private double getT(){
            return this.t;
        }

        private double getH(){
            return this.h;
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    final byte[] buf = new byte[1024];
                    final int len = inputEspStream.read(buf);
                    String[] s2 = new String(buf, 0, len).split(" ");
//                    Log.d("!!!!!!", "temp :"+s2[2] + " "+s2[4]);
                    this.t = Double.parseDouble(s2[2]);
                    this.h = Double.parseDouble(s2[4]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
