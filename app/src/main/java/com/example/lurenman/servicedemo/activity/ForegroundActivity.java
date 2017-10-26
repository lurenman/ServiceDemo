package com.example.lurenman.servicedemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.lurenman.servicedemo.R;
import com.example.lurenman.servicedemo.service.ForegroundService;

/**
 * @author: baiyang.
 * Created on 2017/10/26.
 */

public class ForegroundActivity extends BaseActivity {
    private Button bt_StartService;
    private Button bt_StopService;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_foreground);
        bt_StartService = (Button) findViewById(R.id.bt_StartService);
        bt_StopService = (Button) findViewById(R.id.bt_StopService);

    }

    @Override
    protected void initEnvent() {
        super.initEnvent();
        final Intent intent = new Intent(this,ForegroundService.class);
        bt_StartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("cmd",0);//0,开启前台服务,1,关闭前台服务
                startService(intent);
            }
        });
        bt_StopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("cmd",1);//0,开启前台服务,1,关闭前台服务
                startService(intent);
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
