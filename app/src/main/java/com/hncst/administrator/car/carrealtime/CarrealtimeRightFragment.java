package com.hncst.administrator.car.carrealtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.car.R;
import com.hncst.administrator.car.application.MyApplication;
import com.hncst.administrator.car.view.MycarView;

/**
 * 实时车况Activity的右边的仪表显示的Fragment
 * Created by MomFeng on 2017/4/21 0021.
 */

public class CarrealtimeRightFragment extends Fragment{

    private MycarView car_right_speed;
    private MyApplication app;
    private boolean isrun = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view = mInflater.inflate(R.layout.fragment_carrealright,null);

        car_right_speed = (MycarView) view.findViewById(R.id.car_right_speed);

        app = (MyApplication) getActivity().getApplication();
        if(app.getIsrunright()) {
            app.setIsrunright(false);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (isrun) {
                        car_right_speed.setSpeed(CarrealtimeLeftFragment.speed);
                    }
                }
            }.start();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        isrun = false;
        app.setIsrunright(true);
        super.onDestroy();
    }
}
