package com.example.lurenman.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author: baiyang.
 * Created on 2017/10/25.
 * 参考博客
 * http://blog.csdn.net/javazejian/article/details/52709857
 */

public class TestService extends Service {
    private static final String TAG = "TestService";


    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "----------------" + "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG, "----------------" + "onStart");
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "----------------" + "onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "----------------" + "onDestroy");
    }
}
