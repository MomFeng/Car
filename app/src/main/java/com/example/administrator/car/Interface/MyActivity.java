package com.example.administrator.car.Interface;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.administrator.car.util.AnnotationUtilView;

/**
 * 用于被activity类继承的基类，可以实现去除标题栏
 * Created by MomFeng on 2017/4/30 0030.
 */
public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AnnotationUtilView.injectActivity(this);
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Light_NoTitleBar);
    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        super.setContentView(layoutResID);
    }
}