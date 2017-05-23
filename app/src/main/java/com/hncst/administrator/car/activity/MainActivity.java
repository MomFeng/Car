package com.hncst.administrator.car.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.hncst.administrator.car.Interface.BindLayout;
import com.hncst.administrator.car.Interface.BindView;
import com.hncst.administrator.car.Interface.BindonClick;
import com.hncst.administrator.car.Interface.MyFeagmentAvtivity;
import com.example.administrator.car.R;
import com.hncst.administrator.car.application.MyApplication;
import com.hncst.administrator.car.mainviewpager.FourFragment;
import com.hncst.administrator.car.mainviewpager.MainFragment;
import com.hncst.administrator.car.mainviewpager.ThreeFragment;
import com.hncst.administrator.car.mainviewpager.TwoFragment;
import com.hncst.administrator.car.view.MyView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 程序入口的Activity
 * Created by MomFeng on 2017/4/13 0013.
 */

@BindLayout(R.layout.activity_main)
public class MainActivity extends MyFeagmentAvtivity{

    //Viewpager
    @BindView(R.id.vpg_pager)
    ViewPager vpg_pager;
    //viewpager适配器
    //private PagerAdapter adapter;
    //底部菜单的四个按钮
    //private LinearLayout btn_main, btn_two, btn_three, btn_four;
    //底部菜单四个按钮中的图片
    //private ImageButton imgb_tab_main, imgb_tab_two, imgb_tab_three, imgb_tab_four;

    //用来存放布局
    private List<MyView> mViews = new ArrayList<>();

    @BindView(R.id.tv_title_mian)
    TextView tv_title_mian;

    private List<Fragment> mFragment = new ArrayList<Fragment>();
    private FragmentPagerAdapter fragmentadapter;
    private SlidingMenu menu;

    @BindView(R.id.tv_tab_main)
    MyView tv_tab_main;
    @BindView(R.id.tv_tab_two)
    MyView tv_tab_two;
    @BindView(R.id.tv_tab_three)
    MyView tv_tab_three;
    @BindView(R.id.tv_tab_four)
    MyView tv_tab_four;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化控件
        initView();
        //初始化viewpager
        initViewPager();
        //所有的监听事件
        initEvent();

        //启动锁屏服务
        SharedPreferences sp = getSharedPreferences("config" , MODE_PRIVATE);
        if(sp.getBoolean("islock" , true)){
            MyApplication app = (MyApplication) getApplication();
            app.StartService();
        }

    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }*/

    //监听事件的方法
    private void initEvent() {

        /*tv_tab_main.setOnClickListener(this);
        tv_tab_two.setOnClickListener(this);
        tv_tab_three.setOnClickListener(this);
        tv_tab_four.setOnClickListener(this);*/

        //vpg_pager的滑动事件
        vpg_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                //int currentItem = vpg_pager.getCurrentItem();
                switch (arg0) {
                    case 0:
                        tv_title_mian.setText(R.string.index);
                        menu.setTouchModeAbove(
                                SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case 1:
                        tv_title_mian.setText(R.string.free);
                        // 当在其他位置的时候，设置不可以拖拽出来(SlidingMenu.TOUCHMODE_NONE)，或只有在边缘位置才可以拖拽出来TOUCHMODE_MARGIN
                        menu.setTouchModeAbove(
                                SlidingMenu.TOUCHMODE_NONE);
                        break;
                    case 2:
                        tv_title_mian.setText(R.string.information);
                        // 当在其他位置的时候，设置不可以拖拽出来(SlidingMenu.TOUCHMODE_NONE)，或只有在边缘位置才可以拖拽出来TOUCHMODE_MARGIN
                        menu.setTouchModeAbove(
                                SlidingMenu.TOUCHMODE_NONE);
                        break;
                    case 3:
                        tv_title_mian.setText(R.string.my);
                        // 当在其他位置的时候，设置不可以拖拽出来(SlidingMenu.TOUCHMODE_NONE)，或只有在边缘位置才可以拖拽出来TOUCHMODE_MARGIN
                        menu.setTouchModeAbove(
                                SlidingMenu.TOUCHMODE_NONE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (positionOffset > 0) {
                    MyView leftView = mViews.get(position);
                    leftView.setAlpha(1 - positionOffset);
                    MyView rightView = mViews.get(position + 1);
                    rightView.setAlpha(positionOffset);
                }/*else{
                    MyView leftView = mViews.get(position);
                    leftView.setAlpha(0);
                }*/
                if (position > mFragment.size()) {
                    vpg_pager.setCurrentItem(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    //初始化控件
    private void initView() {

        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.leftmenu);

        //vpg_pager = (ViewPager) findViewById(R.id.vpg_pager);
        //tv_title_mian = (TextView) findViewById(R.id.tv_title_mian);

        /*tv_tab_main = (MyView) findViewById(R.id.tv_tab_main);
        tv_tab_two = (MyView) findViewById(R.id.tv_tab_two);
        tv_tab_three = (MyView) findViewById(R.id.tv_tab_three);
        tv_tab_four = (MyView) findViewById(R.id.tv_tab_four);*/
        tv_tab_main.setAlpha(1);
        //sur_health = new HealthView_view(this);

        //吧所有的fragment加入一个集合当中
        MainFragment fragment1 = new MainFragment();
        TwoFragment fragment2 = new TwoFragment();
        ThreeFragment fragment3 = new ThreeFragment();
        FourFragment fragment4 = new FourFragment();
        mFragment.add(fragment1);
        mFragment.add(fragment2);
        mFragment.add(fragment3);
        mFragment.add(fragment4);
        mViews.add(tv_tab_main);
        mViews.add(tv_tab_two);
        mViews.add(tv_tab_three);
        mViews.add(tv_tab_four);

    }

    //初始化viewpager
    private void initViewPager() {
        /* //初始化四个布局
        LayoutInflater mInflater = LayoutInflater.from(MainActivity.this);
        View view_main = mInflater.inflate(R.layout.fragment_main,null);
        View view_two = mInflater.inflate(R.layout.fragment_two,null);
        View view_three = mInflater.inflate(R.layout.fragment_three,null);
        View view_four = mInflater.inflate(R.layout.fragment_four,null);
        //添加布局
        mViews.add(view_main);
        mViews.add(view_two);
        mViews.add(view_three);
        mViews.add(view_four);*/

        //初始化适配器
        /*adapter = new PagerAdapter() {

            //销毁布局
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            //放入布局
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };*/

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

        vpg_pager.setAdapter(fragmentadapter);
    }

    @BindonClick({R.id.tv_tab_main,R.id.tv_tab_two,R.id.tv_tab_three,R.id.tv_tab_four})
    public void myonClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tab_main:
                vpg_pager.setCurrentItem(0);
                tv_title_mian.setText(R.string.index);
                tv_tab_two.setAlpha(0);
                tv_tab_three.setAlpha(0);
                tv_tab_four.setAlpha(0);
                break;
            case R.id.tv_tab_two:
                vpg_pager.setCurrentItem(1);
                tv_title_mian.setText(R.string.free);
                tv_tab_main.setAlpha(0);
                tv_tab_three.setAlpha(0);
                tv_tab_four.setAlpha(0);
                break;
            case R.id.tv_tab_three:
                vpg_pager.setCurrentItem(2);
                tv_title_mian.setText(R.string.information);
                tv_tab_two.setAlpha(0);
                tv_tab_main.setAlpha(0);
                tv_tab_four.setAlpha(0);
                break;
            case R.id.tv_tab_four:
                vpg_pager.setCurrentItem(3);
                tv_title_mian.setText(R.string.my);
                tv_tab_two.setAlpha(0);
                tv_tab_three.setAlpha(0);
                tv_tab_main.setAlpha(0);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.mipmap.ic_launcher);
        normalDialog.setTitle("");
        normalDialog.setMessage("确定要离开么");
        normalDialog.setPositiveButton("退出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                });
        normalDialog.setNegativeButton("再逛逛",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.show();
    }

    //整个加载完毕才会执行此方法
    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
}
