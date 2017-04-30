package com.example.administrator.car.mainviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.car.R;

/**
 * APP主界面的第二个Fragment（免费）
 * Created by MomFeng on 2017/4/14 0014.
 */

public class TwoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view_two = mInflater.inflate(R.layout.fragment_two,null);
        return view_two;
    }
}
