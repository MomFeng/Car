package com.hncst.administrator.car.Interface;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;

import com.hncst.administrator.car.util.ActivityManager;
import com.hncst.administrator.car.util.AnnotationUtilView;
import com.hncst.administrator.car.util.TouchHelepr;

import java.util.List;

/**
 * 用于被activity类继承的基类，可以实现去除标题栏，集成管理activity
 * Created by MomFeng on 2017/4/30 0030.
 *
 * 2017/5/17
 * 去除主题，使seterror方法可兼容
 * 增加管理activity
 */
public class MyActivity extends AppCompatActivity {
    private TouchHelepr mTouchHelepr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AnnotationUtilView.injectActivity(this);
        ActivityManager.OnCreateActivity(this); //添加到ActivityManager中
        super.onCreate(savedInstanceState);
        List<Activity> list = ActivityManager.GetActivityList();
        System.out.println("list.size()---" + list.size());
        //setTheme(android.R.style.Theme_Light_NoTitleBar);
    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        super.setContentView(layoutResID);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.OnDestroyActivity(this); //从ActivityManager中移除
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mTouchHelepr==null)
            mTouchHelepr=new TouchHelepr(getWindow());
        boolean consume=mTouchHelepr.processTouchEvent(ev);
        if(!consume) return super.dispatchTouchEvent(ev);
        return false;
        //return super.dispatchTouchEvent(ev)||mTouchHelepr.processTouchEvent(ev);
    }
}