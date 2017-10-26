package com.example.lurenman.servicedemo.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lurenman.servicedemo.R;
import com.example.lurenman.servicedemo.service.BinderService;

/**
 * @author: baiyang.
 * Created on 2017/10/26.
 */

public class BinderServiceTest2Activity extends BaseActivity {
    private static final String TAG = "BinderServiceTest2Activ";
    private Button bt_BindService;
    private Button bt_UnBindService;
    private Button bt_ServiceMethod;
    private BinderService mService;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "绑定成功调用：onServiceConnected");
            // 获取Binder
            BinderService.LocalBinder binder = (BinderService.LocalBinder) iBinder;
            mService = binder.getService();
        }

        /**
         * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
         * 例如内存的资源不足时这个方法才被自动调用。
         */

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService=null;
            Log.e(TAG, "解除绑定成功调用：onServiceConnected");
        }
    };
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_binderservice_test2);
        bt_BindService = (Button) findViewById(R.id.bt_BindService);
        bt_UnBindService = (Button) findViewById(R.id.bt_UnBindService);
        bt_ServiceMethod = (Button) findViewById(R.id.bt_ServiceMethod);

    }

    @Override
    protected void initEnvent() {
        super.initEnvent();
        //创建绑定对象
        final Intent intent = new Intent(this, BinderService.class);
        super.initEnvent();
        //绑定服务
        bt_BindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //绑定服务根据调用者有关，要是getApplicationContext()调用生命周期可想而知
                //  getApplicationContext().bindService(intent, conn, Service.BIND_AUTO_CREATE);
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
                Log.e(TAG, "绑定调用：bindService");
            }
        });
        //解除绑定
        bt_UnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 解除绑定
                if (mService != null) {
                    mService = null;
                    //getApplicationContext().unbindService(conn);
                    unbindService(conn);
                    Log.e(TAG, "解除绑定调用：unbindService");
                }
            }
        });
        //调用服务中的方法
        bt_ServiceMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mService != null) {
                    String message = mService.getMessage();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
        // 要不解除绑定会报这个错误has leaked ServiceConnection可能会引起内存泄漏吧.
        // 解除绑定
        if(mService!=null) {
            mService = null;
            unbindService(conn);
            Log.e(TAG, "onDestroy解除绑定调用：unbindService");
        }
    }
}
