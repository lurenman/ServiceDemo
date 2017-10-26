package com.example.lurenman.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author: baiyang.
 * Created on 2017/10/25.
 */

public class BinderService extends Service {
    private static final String TAG = "BinderService";
    private LocalBinder binder = new LocalBinder();
    /**
     * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口
     */
    public class LocalBinder extends Binder {
        // 声明一个方法，getService。（提供给客户端调用）
      public BinderService getService() {
            // 返回当前对象BinderService,这样我们就可在客户端端调用Service的公共方法了
            return BinderService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    /**
     * 解除绑定时调用
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"----------------"+"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"----------------"+"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"----------------"+"onDestroy");
    }
    /**
     * 公共方法
     * @return
     */
    public String getMessage()
    {
        return "成功调用到BinderService中的方法";
    }
}
