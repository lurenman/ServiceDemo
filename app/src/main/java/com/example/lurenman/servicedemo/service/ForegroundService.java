package com.example.lurenman.servicedemo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.lurenman.servicedemo.R;

/**
 * @author: baiyang.
 * Created on 2017/10/26.
 *  前台服务必须为状态栏提供通知，状态栏位于“正在进行”标题下方，这意味着除非服务停止或从前台删除，否则不能清除通知
 */

public class ForegroundService extends Service {
    private static final String TAG = "ForegroundService";
    /**
     * id不可设置为0,否则不能设置为前台service
     */
    private static final int NOTIFICATION_DOWNLOAD_PROGRESS_ID = 0x0001;

    private boolean isRemove=false;//是否需要移除
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * Notification
     */
    public void createNotification(){
        //使用兼容版本
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        //设置状态栏的通知图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置通知栏横条的图标
       // builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.can_upload_icon));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //右上角的时间显示
        builder.setShowWhen(true);
        //设置通知栏的标题内容
        builder.setContentTitle("I am Foreground Service!!!");
        //创建通知
        Notification notification = builder.build();
        //设置为前台服务
        startForeground(NOTIFICATION_DOWNLOAD_PROGRESS_ID,notification);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        int i=intent.getExtras().getInt("cmd");
        if(i==0){
            if(!isRemove) {
                createNotification();
                Log.e(TAG,"createNotification");
            }
            isRemove=true;
        }else {
            //移除前台服务
            if (isRemove) {
                stopForeground(true);
                Log.e(TAG,"移除前台服务");
            }
            isRemove=false;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroy");
        //移除前台服务
        if (isRemove) {
            stopForeground(true);
            Log.e(TAG,"移除前台服务");
        }
        isRemove=false;
        super.onDestroy();
    }
}
