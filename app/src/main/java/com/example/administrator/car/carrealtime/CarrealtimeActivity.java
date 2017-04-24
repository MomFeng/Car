package com.example.administrator.car.carrealtime;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.administrator.car.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19 0019.
 */

public class CarrealtimeActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private Button btn_back_carrealtime;
    private ViewPager vp_pager_carreal;
    private TabLayout tab_title_carreal;
    private List<Fragment> mFragment = new ArrayList<>();
    private FragmentPagerAdapter fragmentadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("-----------------------------执行到了CarrealtimeActivity");
        //hideBottomUIMenu();
        setContentView(R.layout.acticity_carrealtime);

        initView();
        initViewPager();
        initEvent();
    }

    public void initViewPager(){
        fragmentadapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private String[] titles = new String[]{"数字显示", "仪表显示"};

            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };

        vp_pager_carreal.setAdapter(fragmentadapter);
        tab_title_carreal.setupWithViewPager(vp_pager_carreal);
    }
    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void initEvent() {
        vp_pager_carreal.setOnPageChangeListener(this);
        btn_back_carrealtime.setOnClickListener(this);
        /*new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 240; i++) {
                    cv_speed.setSpeed(i);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();*/
    }

    private void initView() {
        btn_back_carrealtime = (Button) findViewById(R.id.btn_back_carrealtime);
        vp_pager_carreal = (ViewPager) findViewById(R.id.vp_pager_carreal);
        tab_title_carreal = (TabLayout) findViewById(R.id.tab_title_carreal);

        //cv_speed = (MycarView) findViewById(R.id.cv_speed);
        CarrealtimeLeftFragment fragment_left = new CarrealtimeLeftFragment();
        CarrealtimeRightFragment fragment_light = new CarrealtimeRightFragment();
        mFragment.add(fragment_left);
        mFragment.add(fragment_light);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_back_carrealtime:
                CarrealtimeActivity.this.finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
