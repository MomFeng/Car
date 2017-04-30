package com.example.administrator.car.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.car.Interface.MyActivity;
import com.example.administrator.car.R;

/**
 * 汽车壁纸的Activity
 * Created by MomFeng on 2017/4/30 0030.
 */

public class PhotoActivity extends MyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
    }
}
