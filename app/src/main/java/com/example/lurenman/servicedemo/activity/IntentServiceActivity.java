package com.example.lurenman.servicedemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.lurenman.servicedemo.R;
import com.example.lurenman.servicedemo.service.MyIntentService;

/**
 * @author: baiyang.
 * Created on 2017/10/26.
 * 它本质是一种特殊的Service,继承自Service并且本身就是一个抽象类
 * 它可以用于在后台执行耗时的异步任务，当任务完成后会自动停止
 * 它拥有较高的优先级，不易被系统杀死（继承自Service的缘故），因此比较适合执行一些高优先级的异步任务
 * 它内部通过HandlerThread和Handler实现异步操作
 * 创建IntentService时，只需实现onHandleIntent和构造方法，onHandleIntent为异步方法，可以执行耗时操作
 */

public class IntentServiceActivity extends BaseActivity implements MyIntentService.UpdateUI {

    private ImageView iv_image;
    /**
     * 图片地址集合
     */
    private String url[] = {
            "http://img.blog.csdn.net/20160903083245762",
            "http://img.blog.csdn.net/20160903083252184",
            "http://img.blog.csdn.net/20160903083257871",
            "http://img.blog.csdn.net/20160903083257871",
            "http://img.blog.csdn.net/20160903083311972",
            "http://img.blog.csdn.net/20160903083319668",
            "http://img.blog.csdn.net/20160903083326871"
    };


    private final Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            iv_image.setImageBitmap((Bitmap) msg.obj);
        }
    };


    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_intentservice);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        Intent intent = new Intent(this, MyIntentService.class);
        for (int i = 0; i < 7; i++) {//循环启动任务
            intent.putExtra(MyIntentService.DOWNLOAD_URL, url[i]);
            intent.putExtra(MyIntentService.INDEX_FLAG, i);
            startService(intent);
        }
        MyIntentService.setUpdateUI(this);


    }

    @Override
    protected void loadData() {

    }

    //必须通过Handler去更新，该方法为异步方法，不可更新UI
    @Override
    public void updateUI(Message message) {
        mUIHandler.sendMessageDelayed(message, message.what * 1000);
    }
}
