package com.example.administrator.car.carrealtime;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.car.R;
import com.example.administrator.car.application.MyApplication;
import com.example.administrator.car.view.LinechartsmallView;
import com.example.administrator.car.view.MycarView;
import com.example.administrator.car.view.ThemometerView;

/**
 * 实时车况Activity的左边的数字显示的Fragment
 * Created by MomFeng on 2017/4/21 0021.
 */

public class CarrealtimeLeftFragment extends Fragment implements View.OnClickListener {

    private AnimatorSet mRightOutSetY, mLeftInSetY;
    private AnimatorSet mRightOutSetY_themometer, mLeftInSetY_themometer;

    private AnimatorSet mRightOutSetX, mLeftInSetX;

    private FrameLayout fl_speede_num, fl_rotating_num, fl_themometer_num;
    private LinearLayout lin_back_num, lin_front_num;
    private LinearLayout lin_back_rotating, lin_front_rotating;
    private LinearLayout lin_back_themometer, lin_front_themometer;
    private Boolean mIsShowBack_speed = true;
    private Boolean mIsShowBack_rotating = true;
    private Boolean mIsShowBack_themometer = true;

    private MycarView car_left_speed;
    private TextView tv_carleft_speed, tv_carleft_themometer, tv_carleft_rotating;
    private ThemometerView th_carleft_themometer;
    private LinechartsmallView lc_carleft_linechar;

    private boolean isrun = true;

    private Thread t;

    public static int speed;

    private MyApplication app;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_carleft_speed.setText(msg.arg1 + "");
            tv_carleft_themometer.setText(msg.arg2 + "");
            tv_carleft_rotating.setText(lc_carleft_linechar.getRoatting()+"");
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view = mInflater.inflate(R.layout.fragment_carrealleft, null);

        fl_speede_num = (FrameLayout) view.findViewById(R.id.fl_speede_num);
        fl_rotating_num = (FrameLayout) view.findViewById(R.id.fl_rotating_num);
        fl_themometer_num = (FrameLayout) view.findViewById(R.id.fl_themometer_num);

        lin_back_num = (LinearLayout) view.findViewById(R.id.lin_back_num);
        lin_front_num = (LinearLayout) view.findViewById(R.id.lin_front_num);

        lin_back_rotating = (LinearLayout) view.findViewById(R.id.lin_back_rotating);
        lin_front_rotating = (LinearLayout) view.findViewById(R.id.lin_front_rotating);

        lin_back_themometer = (LinearLayout) view.findViewById(R.id.lin_back_themometer);
        lin_front_themometer = (LinearLayout) view.findViewById(R.id.lin_front_themometer);

        car_left_speed = (MycarView) view.findViewById(R.id.car_left_speed);
        tv_carleft_speed = (TextView) view.findViewById(R.id.tv_carleft_speed);
        th_carleft_themometer = (ThemometerView) view.findViewById(R.id.th_carleft_themometer);
        tv_carleft_themometer = (TextView) view.findViewById(R.id.tv_carleft_themometer);

        lc_carleft_linechar = (LinechartsmallView) view.findViewById(R.id.lc_carleft_linechar);
        tv_carleft_rotating = (TextView) view.findViewById(R.id.tv_carleft_rotating);

        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离
        initEvent();

        app = (MyApplication) getActivity().getApplication();
        if (app.getisrunleft()) {
            app.setIsrunleft(false);
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    int theme = 20;
                    int b = 0;
                    int i = 0;
                    int c = 1;
                    while (isrun) {
                        if (i == 240) {
                            c = -1;
                        }
                        System.out.println("i---" + i);
                        if (i == 0) {
                            c = 1;
                        }
                        if (theme == 80) {
                            b = -1;
                        }
                        if (theme == 20) {
                            b = 1;
                        }
                        i = i + c;
                        theme = theme + b;
                        speed = i;
                        car_left_speed.setSpeed(i);
                        th_carleft_themometer.setThemometerView(theme);

                        //Message message = handler.obtainMessage();
                        Message m = new Message();
                        m.what = 0;
                        m.arg1 = i;
                        m.arg2 = theme;
                        //Bundle bundle = new Bundle();
                        //bundle.putInt();
                        //m.setData(bundle);
                        //m.sendToTarget();
                        handler.sendMessage(m);

                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.start();
        }
        return view;
    }

    private void initEvent() {
        fl_speede_num.setOnClickListener(this);
        fl_rotating_num.setOnClickListener(this);
        fl_themometer_num.setOnClickListener(this);
    }

    // 设置动画
    private void setAnimators() {
        mRightOutSetY = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_outy);
        mLeftInSetY = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_iny);

        mRightOutSetY_themometer = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_outy);
        mLeftInSetY_themometer = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_iny);

        mRightOutSetX = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_outx);
        mLeftInSetX = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_inx);


        // 设置点击事件
        mRightOutSetY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fl_speede_num.setClickable(false);
            }
        });
        mLeftInSetY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fl_speede_num.setClickable(true);
            }
        });

        // 设置点击事件
        mRightOutSetY_themometer.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fl_themometer_num.setClickable(false);
            }
        });
        mLeftInSetY_themometer.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fl_themometer_num.setClickable(true);
            }
        });

        // 设置点击事件
        mRightOutSetX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fl_rotating_num.setClickable(false);
            }
        });
        mLeftInSetX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fl_rotating_num.setClickable(true);
            }
        });
    }

    // 改变视角距离, 贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        lin_back_num.setCameraDistance(scale);
        lin_front_num.setCameraDistance(scale);

        lin_back_rotating.setCameraDistance(scale);
        lin_front_rotating.setCameraDistance(scale);

        lin_back_themometer.setCameraDistance(scale);
        lin_front_themometer.setCameraDistance(scale);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 翻转卡片
            case R.id.fl_speede_num:
                // 正面朝上
                if (!mIsShowBack_speed) {
                    //mRightOutSet.setTarget(lin_front_num);
                    //mLeftInSet.setTarget(lin_back_num);
                    mRightOutSetY.setTarget(lin_back_num);
                    mLeftInSetY.setTarget(lin_front_num);
                    mRightOutSetY.start();
                    mLeftInSetY.start();
                    mIsShowBack_speed = true;
                } else { // 背面朝上
                    //mRightOutSetY.setTarget(lin_back_num);
                    //mLeftInSet.setTarget(lin_front_num);
                    mRightOutSetY.setTarget(lin_front_num);
                    mLeftInSetY.setTarget(lin_back_num);
                    mRightOutSetY.start();
                    mLeftInSetY.start();
                    mIsShowBack_speed = false;
                }
                break;
            case R.id.fl_rotating_num:
                if (!mIsShowBack_rotating) {
                    //mRightOutSet.setTarget(lin_front_num);
                    //mLeftInSet.setTarget(lin_back_num);
                    mRightOutSetX.setTarget(lin_back_rotating);
                    mLeftInSetX.setTarget(lin_front_rotating);
                    mRightOutSetX.start();
                    mLeftInSetX.start();
                    mIsShowBack_rotating = true;
                } else { // 背面朝上
                    //mRightOutSet.setTarget(lin_back_num);
                    //mLeftInSet.setTarget(lin_front_num);
                    mRightOutSetX.setTarget(lin_front_rotating);
                    mLeftInSetX.setTarget(lin_back_rotating);
                    mRightOutSetX.start();
                    mLeftInSetX.start();
                    mIsShowBack_rotating = false;
                }
                break;
            case R.id.fl_themometer_num:
                if (!mIsShowBack_themometer) {
                    //mRightOutSet.setTarget(lin_front_num);
                    //mLeftInSet.setTarget(lin_back_num);
                    mRightOutSetY_themometer.setTarget(lin_back_themometer);
                    mLeftInSetY_themometer.setTarget(lin_front_themometer);
                    mRightOutSetY_themometer.start();
                    mLeftInSetY_themometer.start();
                    mIsShowBack_themometer = true;
                } else { // 背面朝上
                    //mRightOutSetY.setTarget(lin_back_num);
                    //mLeftInSet.setTarget(lin_front_num);
                    mRightOutSetY_themometer.setTarget(lin_front_themometer);
                    mLeftInSetY_themometer.setTarget(lin_back_themometer);
                    mRightOutSetY_themometer.start();
                    mLeftInSetY_themometer.start();
                    mIsShowBack_themometer = false;
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        isrun = false;
        app.setIsrunleft(true);
        super.onDestroy();
    }
}
