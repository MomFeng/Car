package com.example.administrator.car.mainviewpager;

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
 * Created by Administrator on 2017/4/14 0014.
 */

public class ThreeFragment extends Fragment {

    private ViewPager vp_pager_three;
    private PagerAdapter adapter;
    private List<ImageView> mImageViews = new ArrayList<ImageView>();
    private int[] mImgIds = new int[] { R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view_three = mInflater.inflate(R.layout.fragment_three,null);

        initData();

        vp_pager_three = (ViewPager) view_three.findViewById(R.id.vp_pager_three);

        adapter = new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {

                container.addView(mImageViews.get(position));
                return mImageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object)
            {
                container.removeView(mImageViews.get(position));
            }

            @Override
            public int getCount() {
                return mImageViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        vp_pager_three.setAdapter(adapter);

        return view_three;
    }

    private void initData()
    {
        for (int imgId : mImgIds)
        {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgId);
            mImageViews.add(imageView);
        }
    }
}
