package com.example.administrator.car.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.car.R;
import com.example.administrator.car.view.LockLayer;
import com.example.administrator.car.view.PullDoorView;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class LockscreenActivity extends Activity{

    public static int MSG_LOCK_SUCESS = 0x123;// 解锁成功
    private ImageView ivHint;// 下方的动态图片
    private AnimationDrawable animArrowDrawable = null;
    //private View lockView;
    private LockLayer lockLayer;

    /**
     * 消息处理
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (MSG_LOCK_SUCESS == msg.what) {
                lockLayer.unlock();
                finish(); // 锁屏成功时，结束我们的Activity界面
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_lockscreen);
        PullDoorView.setHandler(mHandler);

        //lockView = View.inflate(this, R.layout.activity_lockscreen, null);
        //lockLayer = new LockLayer(this);
        //lockLayer.setLockView(lockView);// 设置要展示的页面
        //lockLayer.lock();// 开启锁屏

        ivHint = (ImageView)findViewById(R.id.iv_hint);
        ivHint.setImageResource(R.drawable.slider_tip_anim);
        animArrowDrawable = (AnimationDrawable) ivHint.getDrawable();
        animArrowDrawable.start();
    }
}
