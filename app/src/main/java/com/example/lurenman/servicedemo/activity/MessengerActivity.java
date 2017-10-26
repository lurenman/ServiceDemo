package com.example.lurenman.servicedemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lurenman.servicedemo.R;
import com.example.lurenman.servicedemo.service.MessengerService;

/**
 * @author: baiyang.
 * Created on 2017/10/25.
 * 发现那个单独开启的服务进程log在本进程应用是无法显示
 * 实现了不同进程间的通讯。
 */

public class MessengerActivity extends BaseActivity {
    private static final String TAG = "MessengerActivity";
    private Button bt_BindService;
    private Button bt_UnBindService;
    private Button bt_ServiceMethod;
    /**
     * 与服务端交互的Messenger
     */
    Messenger mService = null;

    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;

    /**
     * 实现与服务端链接的对象
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            /**
             * 通过服务端传递的IBinder对象,创建相应的Messenger
             * 通过该Messenger对象与服务端进行交互
             */
            mService = new Messenger(iBinder);
            mBound = true;
            Log.e(TAG, "绑定成功调用：onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
            Log.e(TAG, "解除绑定成功调用：onServiceConnected");
        }
    };
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_messenger);
        bt_BindService = (Button) findViewById(R.id.bt_BindService);
        bt_UnBindService = (Button) findViewById(R.id.bt_UnBindService);
        bt_ServiceMethod = (Button) findViewById(R.id.bt_ServiceMethod);

    }

    @Override
    protected void initEnvent() {
        super.initEnvent();
        bt_BindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当前Activity绑定服务端
                bindService(new Intent(MessengerActivity.this, MessengerService.class), mConnection,
                        Context.BIND_AUTO_CREATE);
                Log.e(TAG, "绑定调用：bindService");
            }
        });
        bt_UnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Unbind from the service
                if (mBound) {
                    unbindService(mConnection);
                    mBound = false;
                    Log.e(TAG, "解除绑定调用：unbindService");
                }
            }
        });
        bt_ServiceMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mBound) {
                    return;
                }
                // 创建与服务交互的消息实体Message
                Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
                try {
                    //发送消息
                    mService.send(msg);
                    Log.e(TAG, "发送消息成功");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
            Log.e(TAG, "onDestroy解除绑定调用：unbindService");
        }
    }
}
