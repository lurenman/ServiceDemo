package com.example.lurenman.servicedemo.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lurenman.servicedemo.R;
import com.example.lurenman.servicedemo.service.TestService;

/**
 * @author: baiyang.
 * Created on 2017/10/25.
 */

public class TestServiceActivity extends BaseActivity  {
    private Button bt_StartService;
    private Button bt_StopService;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_testservice);
        bt_StartService = (Button) findViewById(R.id.bt_StartService);
        bt_StopService = (Button) findViewById(R.id.bt_StopService);

    }

    @Override
    protected void initEnvent() {
        super.initEnvent();
        final Intent intent = new Intent(TestServiceActivity.this, TestService.class);
        bt_StartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startService(intent);
            }
        });
        bt_StopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               stopService(intent);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
