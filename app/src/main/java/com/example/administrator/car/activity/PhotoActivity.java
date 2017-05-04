package com.example.administrator.car.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.car.Adapter.RecyclerViewSimpleAdapter;
import com.example.administrator.car.Interface.MyActivity;
import com.example.administrator.car.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 汽车壁纸的Activity
 * Created by MomFeng on 2017/4/30 0030.
 */

public class PhotoActivity extends MyActivity {

    private RecyclerView rv_photo_view;
    private RecyclerViewSimpleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        //获取网络数据
        //gethttpfromokhttp();
        initViews();


        //设置recyclerview的布局管理
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_photo_view.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //adapter = new RecyclerViewSimpleAdapter(this, strurl);
        rv_photo_view.setAdapter(adapter);

    }

    private void initViews(){
        rv_photo_view = (RecyclerView) findViewById(R.id.rv_photo_view);
    }

}
