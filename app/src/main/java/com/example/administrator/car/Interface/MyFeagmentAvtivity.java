package com.example.administrator.car.Interface;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.example.administrator.car.util.ActivityManager;
import com.example.administrator.car.util.AnnotationUtilView;

import java.util.List;

/**
 * 用于给Fragment继承的基类
 * Created by MomFeng on 2017/5/8 0008.
 */

public class MyFeagmentAvtivity extends FragmentActivity{
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
}
