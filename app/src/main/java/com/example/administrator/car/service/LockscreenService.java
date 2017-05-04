package com.example.administrator.car.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.administrator.car.BroadcastReceiver.LockdcreenReceiver;
import com.example.administrator.car.activity.LockscreenActivity;

/**
 * 用于动态注册监听锁屏广播的一个Service
 * Created by MomFeng on 2017/4/26 0026.
 */

public class LockscreenService extends Service{

    //监听锁屏的广播
    private LockdcreenReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        //1.创建广播接收者对象
        mReceiver = new LockdcreenReceiver();
        //2.创建intent-filter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);

        //3.注册广播接收者
        registerReceiver(mReceiver, filter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
