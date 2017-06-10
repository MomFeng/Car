package com.hncst.administrator.car.mainviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.car.R;
import com.hncst.administrator.car.activity.IllegalAvtivity;
import com.hncst.administrator.car.activity.KotlinActivity;
import com.hncst.administrator.car.activity.PhotoActivity;
import com.hncst.administrator.car.application.MyApplication;
import com.hncst.administrator.car.carrealtime.CarrealtimeActivity;
import com.hncst.administrator.car.util.SimpleUtil;
import com.hncst.administrator.car.view.HealthView;

/**
 * PP主界面的第一个Fragment（首页）
 * Created by MomFeng on 2017/4/14 0014.
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    private HealthView sur_health;
    private MyApplication app;
    //实时车况，汽车壁纸
    private LinearLayout lea_car_main,lea_wallpaper_main;
    private View view_main;
    private LinearLayout lea_main_illegal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        view_main = mInflater.inflate(R.layout.fragment_main,null);
        //初始化控件
        initView();
        //初始化点击事件
        initEvent();
        return view_main;
    }

    private void initEvent() {

        //实时车况
        lea_car_main.setOnClickListener(this);
        //汽车壁纸
        lea_wallpaper_main.setOnClickListener(this);
        //违章查询
        lea_main_illegal.setOnClickListener(this);

        app = (MyApplication) getActivity().getApplication();
        if(app.isStart()){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    app.setStart(false);
                    for (float i = 1; i <= 200; i+=0.5) {
                        sur_health.setmHealth(i);
                        sur_health.setmHealth_int((int)((i+0.5)/2));
                        app.setmHealth(i);
                        app.setmHealth_int((int)((i+0.5)/2));
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }else{
            sur_health.setmHealth(app.getmHealth());
            sur_health.setmHealth_int(app.getmHealth_int());
        }
    }

    private void initView() {
        sur_health = (HealthView) view_main.findViewById(R.id.sur_health);
        lea_car_main = (LinearLayout) view_main.findViewById(R.id.lea_car_main);
        lea_wallpaper_main = (LinearLayout) view_main.findViewById(R.id.lea_wallpaper_main);
        lea_main_illegal = (LinearLayout) view_main.findViewById(R.id.lea_main_illegal);
    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch(v.getId()){
            //实时车况
            case R.id.lea_car_main:
                SimpleUtil.ToIntent(getContext() , CarrealtimeActivity.class);
                /*i = new Intent(getContext() , CarrealtimeActivity.class);
                startActivity(i);*/
                break;
            //汽车壁纸
            case R.id.lea_wallpaper_main:
                SimpleUtil.ToIntent(getContext() , PhotoActivity.class);
                /*i = new Intent(getContext() , PhotoActivity.class);
                startActivity(i);*/
                break;
            //违章查询
            case R.id.lea_main_illegal:
                SimpleUtil.ToIntent(getContext() , IllegalAvtivity.class);
                break;
        }
    }
}
