package com.own.freshtoyou.util.MyJSON;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyJSON {

    public Activity activity;

    public static final String SERVER_LOC = "http://pest.fgifast1.vipnps.vip";

    private CallBack callBack = null;

    private String TAG = "MyJSON";

    private String timeLine;

    private File file;

    String head,des;

    private boolean noPic=false;

    public MyJSON(Activity activity) {
        this.activity = activity;
    }

    public void setData(File file, String head, String des){
        this.file = file;
        this.head = head;
        this.des = des;
    }

    public void request()  {
        ThreadPool.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormatLine = new SimpleDateFormat("MMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                timeLine = simpleDateFormatLine.format(date);
                div("FILE");
                div("String");
            }
        });
    }

    public void div(String TAG){
        switch (TAG){
            case "FILE":
                uploadMultiFile();
                break;
            case "String":
                uploadStringData();
                break;
            default:
                break;
        }
    }

    public void myToast(String TAG){
        final String toastContent;
        switch (TAG){
            case "SUCCESS":
                toastContent="反馈成功"; break;
            case "FAIL":
                toastContent="反馈失败"; break;
            case "INPUT":
                toastContent="请填写好文字描述"; break;
            case "INTERNET":
                toastContent="网络连接出错"; break;
            case "START":
                toastContent="开始发送"; break;
            default:
                toastContent="错误";
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,toastContent,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadMultiFile() {//将图片发送到服务器
        if(file==null){
            noPic=true;
            return;
        }
        noPic=false;
        final String url = MyJSON.SERVER_LOC +"/PestRecognition/AppFeedbackImg.php";
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image1", timeLine+".", fileBody)
                .build();
        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .build();
        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("!!!!!!", "uploadMultiFile() e=" + e);
                myToast("FAIL");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("!!!!!!", "uploadMultiFile() response=" + response.body().string());
                myToast("SUCCESS");
            }});
    }

    private void uploadStringData() {
        if(head == null && des == null || head.equals("") && des == null || head.equals("") && des.equals("")){
            myToast("INPUT");
            return;
        }
        myToast("START");
        String jsonBody = "{\n" +
                "        \"address\": \""+timeLine+"."+"\",\n" +
                "        \"type\": \""+head+"\",\n" +
                "        \"pestdesc\": \""+des+"\"\n" +
                "    }";
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL url = new URL(MyJSON.SERVER_LOC+"/PestRecognition/AppFeedbackData.php");
            URLConnection conn = url.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.write(jsonBody);
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            final String UTF8_BOM = "\uFEFF";
            if (result.startsWith(UTF8_BOM)) {
                result = result.substring(1);
            }
            Log.d("!!!!!!",result);
            if(noPic)
                if(result.equals("400"))
                    myToast("SUCCESS");
                else  myToast("FAIL");
        } catch (IOException e) {
            myToast("INTERNET");
        }//使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }









}