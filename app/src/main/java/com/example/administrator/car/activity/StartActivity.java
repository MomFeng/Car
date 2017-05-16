package com.example.administrator.car.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.administrator.car.Interface.MyActivity;
import com.example.administrator.car.R;
import com.example.administrator.car.activity.glide.Glide;

/**
 * 启动页动画
 * Created by MomFeng on 2017/5/16 0016.
 */

public class StartActivity extends MyActivity {

    private boolean isfirst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //setContentView(R.layout.activity_start);

        View view=View.inflate(StartActivity.this, R.layout.activity_start, null);
        setContentView(view);
        AlphaAnimation aphaAmation=new AlphaAnimation(0.3f, 1.0f);
        aphaAmation.setDuration(2000);
        view.startAnimation(aphaAmation);
        aphaAmation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {

                SharedPreferences sp =getSharedPreferences("config",StartActivity.MODE_PRIVATE);
                isfirst = sp.getBoolean("isfirst" , true);
                Intent i = null;
                if(isfirst){
                    i = new Intent(StartActivity.this,Glide.class);
                }else{
                    i = new Intent(StartActivity.this, LoginActivity.class);
                }
                startActivity(i);
                StartActivity.this.finish();
            }
        } );
    }
}
