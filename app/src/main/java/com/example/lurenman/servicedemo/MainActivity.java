package com.example.lurenman.servicedemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lurenman.servicedemo.activity.BinderServiceActivity;
import com.example.lurenman.servicedemo.activity.ForegroundActivity;
import com.example.lurenman.servicedemo.activity.IntentServiceActivity;
import com.example.lurenman.servicedemo.activity.MessengerActivity;
import com.example.lurenman.servicedemo.activity.TestServiceActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private TextView tv_TestService;
    private TextView tv_BinderService;
    private TextView tv_MessengerService;
    private TextView tv_ForegroundService;
    private TextView tv_MyIntentService;
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_TestService = (TextView) findViewById(R.id.tv_TestService);
        tv_BinderService = (TextView) findViewById(R.id.tv_BinderService);
        tv_MessengerService = (TextView) findViewById(R.id.tv_MessengerService);
        tv_ForegroundService = (TextView) findViewById(R.id.tv_ForegroundService);
        tv_MyIntentService = (TextView) findViewById(R.id.tv_MyIntentService);
        tv_TestService.setOnClickListener(this);
        tv_BinderService.setOnClickListener(this);
        tv_MessengerService.setOnClickListener(this);
        tv_ForegroundService.setOnClickListener(this);
        tv_MyIntentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == tv_TestService) {
            Intent intent = new Intent(MainActivity.this, TestServiceActivity.class);
            startActivity(intent);
        }
        if (view == tv_BinderService) {
            Intent intent = new Intent(MainActivity.this, BinderServiceActivity.class);
            startActivity(intent);
        }

        if (view == tv_MessengerService) {
            Intent intent = new Intent(MainActivity.this, MessengerActivity.class);
            startActivity(intent);
        }
        if (view==tv_ForegroundService)
        {
            Intent intent = new Intent(MainActivity.this, ForegroundActivity.class);
            startActivity(intent);
        }
        if (view==tv_MyIntentService)
        {
            Intent intent = new Intent(MainActivity.this, IntentServiceActivity.class);
            startActivity(intent);
        }
    }
    public boolean serviceIsRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Application.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.lurenman.servicedemo.service.TestService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            //这种直接System.exit(0) 主activity不走onDestroy方法
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit()
    {
        if (!isExit)
        {
            isExit = true;
            Toast.makeText(getApplicationContext(),"再按一次退出程序", Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
        else
        {
            finish();
            System.exit(0);
        }
    }

}
