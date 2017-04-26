package com.keep.alive.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.keep.alive.keeplive.KeepLiveManager;

public class MyService extends Service {
    private ChangeReceiver mReceiver;
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        keepAlive();
        mReceiver = new ChangeReceiver();
        IntentFilter f0 = new IntentFilter("android.intent.action.SCREEN_ON");
        IntentFilter f1 = new IntentFilter("android.intent.action.SCREEN_OFF");

        this.registerReceiver(mReceiver, f0);
        this.registerReceiver(mReceiver, f1);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        if (mReceiver != null) {
            this.unregisterReceiver(mReceiver);
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            /**被关闭时再启动服务，确保不被杀死*/
            @Override
            public void run() {
                try {
                    MyService.this.startService(new Intent(MyService.this, MyService.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);
        super.onDestroy();
    }

    class ChangeReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_ON")) {// 屏幕点亮
                KeepLiveManager.finishKeepLiveActivity();
            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                KeepLiveManager.startKeepLiveActivity();
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void keepAlive() {
        try {
            Notification notification = new Notification();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            startForeground(0, notification); // 设置为前台服务避免kill，Android4.3及以上需要设置id为0时通知栏才不显示该通知；
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
