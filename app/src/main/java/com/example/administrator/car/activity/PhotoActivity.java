package com.example.administrator.car.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.administrator.car.Adapter.RecyclerViewSimpleAdapter;
import com.example.administrator.car.Interface.MyActivity;
import com.example.administrator.car.R;
import com.example.administrator.car.util.SpacesItemDecoration;

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
    public String html;
    public List<String> strurl = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        initViews();
        //获取网络数据
        gethttpfromokhttp();
    }

    private void initViews(){
        rv_photo_view = (RecyclerView) findViewById(R.id.rv_photo_view);
    }

    Handler handmer = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    rv_photo_view.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    //rv_photo_view.setLayoutManager(new LinearLayoutManager(PhotoActivity.this));
                    //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PhotoActivity.this, LinearLayoutManager.VERTICAL, false);
                    //rv_photo_view.setLayoutManager(linearLayoutManager);
                    /*SpacesItemDecoration decoration=new SpacesItemDecoration(15);
                    rv_photo_view.addItemDecoration(decoration);*/
                    adapter = new RecyclerViewSimpleAdapter(PhotoActivity.this, strurl ,rv_photo_view );
                    rv_photo_view.setAdapter(adapter);
                    break;
                case 1:

                    break;
            }
        }
    };

    /**
     * get请求方式
     * 请求百度图片的源码数据并解析出来
     */
    public void gethttpfromokhttp() {
        //创建网络处理的对象
        OkHttpClient client = new OkHttpClient.Builder()
                //设置读取数据的时间
                .readTimeout(5, TimeUnit.SECONDS)
                //对象的创建
                .build();
        //创建一个网络请求的对象，如果没有写请求方式，默认的是get
        //在请求对象里面传入链接的URL地址
        Request request = new Request.Builder()
                //    http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1493886460543_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E6%B1%BD%E8%BD%A6
                //    http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=star.cate&fr=&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E6%B1%BD%E8%BD%A6&oq=%E6%B1%BD%E8%BD%A6&rsp=-1
                //.url("http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1493886460543_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E6%B1%BD%E8%BD%A6").build();
                .url("https://image.baidu.com/search/index?tn=baiduimage&st=-1&ipn=r&ct=201326592&nc=1&lm=-1&cl=2&ie=utf-8&word=%E6%B1%BD%E8%BD%A6&ie=utf-8&istype=2&fm=se0").build();
        //call就是我们可以执行的请求类
        Call call = client.newCall(request);
        //异步方法，来执行任务的处理，一般都是使用异步方法执行的
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                Log.e("okhttp",Thread.currentThread().getName() + "结果  " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功
                //Log.e("okhttp",Thread.currentThread().getName() + "结果  " + response.body().string().trim());
                //"thumbURL":[\d\D\s\S]*?gp=0.jpg
                html = response.body().string();
                Pattern pattern = Pattern.compile("\"thumbURL\":[\\d\\D\\s\\S]*?0.jpg");
                Pattern pattern_text = Pattern.compile("imgData',[\\d\\D\\s\\S]*?]");
                Matcher matcher = pattern.matcher(html);
                Matcher matcher_text = pattern_text.matcher(html);
                if(matcher_text.find()){
                    System.out.println("html ----------- " + matcher_text.group());
                }
                while(matcher.find()) {
                    String url = matcher.group().substring(12,matcher.group().length());
                    System.out.println(url);
                    strurl.add(url);
                }
                //设置recyclerview的布局管理
                Message m = new Message();
                m.what = 0;
                handmer.sendMessage(m);
            }
        });

    }
}
