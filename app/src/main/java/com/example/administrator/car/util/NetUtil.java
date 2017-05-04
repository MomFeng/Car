package com.example.administrator.car.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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
    private RecyclerView recyclerView;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //加载网络成功进行UI的更新,处理得到的图片资源
                case 0:
                    //通过message，拿到字节数组
                    byte[] Picture = (byte[]) msg.obj;
                    //使用BitmapFactory工厂，把字节数组转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    Bundle b = msg.getData();
                    String url = b.getString("url");
                    //通过imageview，设置图片
                    ImageView imageView = (ImageView) recyclerView.findViewWithTag(url);
                    //imageView.setImageBitmap(bitmap);
                    if(imageView != null && Picture != null){
                        System.out.println("------执行到了设置图片------" + Picture.toString());
                        imageView.setImageBitmap(bitmap);
                    }

                    //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                    break;
                //当加载网络失败执行的逻辑代码
                case 1:
                    break;
            }
        }
    };

    public NetUtil(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

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
                //listbitmap.add(BitmapFactory.decodeByteArray(Picture_bt, 0, Picture_bt.length));
                Bitmap bitmap = BitmapFactory.decodeByteArray(Picture_bt, 0, Picture_bt.length);
            }
        });
    }

    /**
     *
     * @param img
     * @param url
     */
    public void setimagefromnet(ImageView img , final String url){
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
                //listbitmap.add(BitmapFactory.decodeByteArray(Picture_bt, 0, Picture_bt.length));
                Message message = handler.obtainMessage();
                message.obj = Picture_bt;
                message.what = 0;
                Bundle b = new Bundle();
                b.putString("url", url);
                message.setData(b);
                message.sendToTarget();
                //handler.sendMessage(message);
                //Bitmap bitmap = BitmapFactory.decodeByteArray(Picture_bt, 0, Picture_bt.length);
            }
        });
    }

}
