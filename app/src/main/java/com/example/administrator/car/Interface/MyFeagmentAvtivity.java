package com.example.administrator.car.Interface;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.example.administrator.car.util.AnnotationUtilView;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class MyFeagmentAvtivity extends FragmentActivity{
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
