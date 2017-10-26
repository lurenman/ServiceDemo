package com.example.lurenman.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * @author: baiyang.
 * Created on 2017/10/25.
 * 此Service通过设置android:process=":remote",创建了单独的进程
 * 以此来实现不同进程间的通信
 */

public class MessengerService extends Service {
    public static final int MSG_SAY_HELLO = 1;
    private static final String TAG = "MessengerService";
    /**
     * 用于接收从客户端传递过来的数据
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Log.e(TAG,"实现不同进程间通讯成功");
                    Toast.makeText(getApplicationContext(), "实现不同进程间通讯成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    /**
     * 创建Messenger并传入Handler实例对象
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    /**
     * 当绑定Service时,该方法被调用,将通过mMessenger返回一个实现
     * IBinder接口的实例对象
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
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
}
