package com.hncst.administrator.car.mainviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.car.R;

import java.util.ArrayList;
import java.util.List;

/**
 * APP主界面的第三个Fragment（商品）
 * Created by MomFeng on 2017/4/14 0014.
 */

public class ThreeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view_three = mInflater.inflate(R.layout.fragment_three,null);

        return view_three;
    }
}
