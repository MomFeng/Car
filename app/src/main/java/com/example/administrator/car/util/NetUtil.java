package com.example.administrator.car.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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
 * 网络的工具类
 * Created by MomFeng on 2017/5/4 0004.
 */
public class NetUtil {

    public List<Bitmap> listbitmap = new ArrayList<>();
    public String html;
    public List<String> strurl = new ArrayList<>();

    /**
     * 从网络的图片链接加载图片并返回
     * @return
     */
    public void getphotofromurl(String url){
        //1.创建一个okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request.Builder对象，设置参数，请求方式如果是Get，就不用设置，默认就是Get
        Request request = new Request.Builder()
                .url(url)
                .build();
        //3.创建一个Call对象，参数是request对象，发送请求
        Call call = okHttpClient.newCall(request);
        //4.异步请求，请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到从网上获取资源，转换成我们想要的类型
                byte[] Picture_bt = response.body().bytes();
                //使用BitmapFactory工厂，把字节数组转化为bitmap
                listbitmap.add(BitmapFactory.decodeByteArray(Picture_bt, 0, Picture_bt.length));
            }
        });
    }

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
                .url("http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=star.cate&fr=&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E6%B1%BD%E8%BD%A6&oq=%E6%B1%BD%E8%BD%A6&rsp=-1").build();

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
                html = response.body().string().trim();
                Pattern pattern = Pattern.compile("\"middleURL\":[\\d\\D\\s\\S]*?0.jpg");
                Matcher matcher = pattern.matcher(html);
                while(matcher.find()) {
                    System.out.println(matcher.group());
                    strurl.add(matcher.group());
                }
            }
        });

    }
}
