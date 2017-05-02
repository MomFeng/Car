package com.example.administrator.car.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.car.Interface.MyActivity;
import com.example.administrator.car.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        tv = (TextView) findViewById(R.id.tv);

        gethttpfromokhttp();
    }

    /**
     * get请求方式
     * 请求百度网页的源码数据
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
                .url("http://image.baidu.com/channel/star/list/%E5%A5%B3%E6%98%8E%E6%98%9F").build();

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
                //子线程
                //main thread1
                Log.e("okhttp",Thread.currentThread().getName() + "结果  " + response.body().string().trim());
                //tv.setText(response.body().string());
            }
        });
        //  call.cancel();取消任务

        //同步方法,一般不用
       /* try {
            Response execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
