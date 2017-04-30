package com.example.administrator.car.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.car.activity.LockscreenActivity;
import com.example.administrator.car.service.LockscreenService;

/**
 * 用于监听锁屏事件的一个广播
 * Created by MomFeng on 2017/4/26 0026.
 */

public class LockdcreenReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            System.out.println("service----------------5");
            Intent lockscreen = new Intent(context, LockscreenActivity.class);

            lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(lockscreen);
        }
    }
}
