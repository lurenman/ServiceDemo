package com.example.lurenman.servicedemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author: baiyang.
 * Created on 2017/10/26.
 */

public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";
    public static final String DOWNLOAD_URL="download_url";
    public static final String INDEX_FLAG="index_flag";
    public static UpdateUI updateUI;


    public static void setUpdateUI(UpdateUI updateUIInterface){
        updateUI=updateUIInterface;
    }

    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG,"onStart");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroy");
        super.onDestroy();
    }
    /**
     * 实现异步任务的方法
     * @param intent Activity传递过来的Intent,数据封装在intent中
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //在子线程中进行网络请求
        Bitmap bitmap=downloadUrlBitmap(intent.getStringExtra(DOWNLOAD_URL));
        Message msg1 = new Message();
        msg1.what = intent.getIntExtra(INDEX_FLAG,0);
        msg1.obj =bitmap;
        //通知主线程去更新UI
        if(updateUI!=null){
            updateUI.updateUI(msg1);
        }
        Log.e(TAG,"onHandleIntent");
    }
    public interface UpdateUI{
        void updateUI(Message message);
    }
    private Bitmap downloadUrlBitmap(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        Bitmap bitmap=null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            bitmap= BitmapFactory.decodeStream(in);
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}
