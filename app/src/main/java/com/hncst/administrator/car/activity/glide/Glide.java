package com.hncst.administrator.car.activity.glide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.Button;

import com.example.administrator.car.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 写启动页面的FragmentActivity
 * Created by MomFeng on 2017/5/16 0016.
 */

public class Glide extends FragmentActivity{

    private ViewPager viewpager;
    List<Fragment> mFragment = new ArrayList<Fragment>();
    FragmentPagerAdapter fragmentadapter;

    private Button btn_one , btn_two , btn_three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_glide);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_three = (Button) findViewById(R.id.btn_three);

        OneFragment fragmentone = new OneFragment();
        TwoFragment fragmenttwo = new TwoFragment();
        ThreeFragment fragmenthree = new ThreeFragment();
        mFragment.add(fragmentone);
        mFragment.add(fragmenttwo);
        mFragment.add(fragmenthree);

        fragmentadapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };

        viewpager.setAdapter(fragmentadapter);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        btn_one.setBackgroundResource(R.drawable.radio_red);
                        btn_two.setBackgroundResource(R.drawable.radio_white);
                        btn_three.setBackgroundResource(R.drawable.radio_white);
                        break;
                    case 1:
                        btn_one.setBackgroundResource(R.drawable.radio_white);
                        btn_two.setBackgroundResource(R.drawable.radio_red);
                        btn_three.setBackgroundResource(R.drawable.radio_white);
                        break;
                    case 2:
                        btn_one.setBackgroundResource(R.drawable.radio_white);
                        btn_two.setBackgroundResource(R.drawable.radio_white);
                        btn_three.setBackgroundResource(R.drawable.radio_red);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
