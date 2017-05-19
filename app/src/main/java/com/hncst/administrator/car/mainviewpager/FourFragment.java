package com.hncst.administrator.car.mainviewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.car.R;
import com.hncst.administrator.car.activity.SettingActivity;

/**
 * APP主界面的第四个Fragment（我的）
 * Created by MomFeng on 2017/4/14 0014.
 */

public class FourFragment extends Fragment implements View.OnClickListener {

    private LinearLayout lea_setting_four;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view_four = mInflater.inflate(R.layout.fragment_four, null);

        lea_setting_four = (LinearLayout) view_four.findViewById(R.id.lea_setting_four);
        lea_setting_four.setOnClickListener(this);

        return view_four;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.lea_setting_four:
                Intent i = new Intent(context,SettingActivity.class);
                startActivity(i);
                break;
        }
    }
}
