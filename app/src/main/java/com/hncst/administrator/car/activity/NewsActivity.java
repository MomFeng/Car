package com.hncst.administrator.car.activity;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.car.R;
import com.hncst.administrator.car.Interface.BindLayout;
import com.hncst.administrator.car.Interface.BindView;
import com.hncst.administrator.car.Interface.MyActivity;
import com.kogitune.activity_transition.ExitActivityTransition;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 展示咨讯详情页的activity
 * Created by MomFeng on 2017/5/21 0021.
 *
 * 消息详情的API
 * http://news-at.zhihu.com/api/4/news/9429750
 */
@BindLayout(R.layout.activity_news)
public class NewsActivity extends MyActivity{

    @BindView(R.id.img_news_photo)
    private ImageView img_news_photo;
    @BindView(R.id.tv_news_title)
    private TextView tv_news_title;
    @BindView(R.id.tv_news_content)
    private TextView tv_news_content;
    private ExitActivityTransition exitTransition;


    private String id;
    private String url = "http://news-at.zhihu.com/api/4/news/";

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            JSONObject jsonObject = null;
            String json_stories = "";
            try {
                jsonObject = new JSONObject((String) msg.obj);
                json_stories = jsonObject.getString("body");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                Glide.with(NewsActivity.this)
                        .load(jsonObject.getString("image"))
                        .bitmapTransform(new CropTransformation(NewsActivity.this, 600, 300, CropTransformation.CropType.CENTER) , new VignetteFilterTransformation(NewsActivity.this, new PointF(0.5F, 0.5F), new float[]{0.0F, 0.0F, 0.0F}, 0.0F, 0.8F))
                        .into(img_news_photo);
                tv_news_content.setText(Html.fromHtml(json_stories));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ActivityTransition.with(getIntent()).to(findViewById(R.id.img_news_photo)).start(savedInstanceState);

        Intent intent=getIntent();
        if(intent != null)
        {
            String bitmapurl = intent.getStringExtra("bitmapurl");
            //Glide.with(NewsActivity.this).load(bitmapurl).into(img_news_photo);
            tv_news_title.setText(intent.getStringExtra("title"));
            id = intent.getStringExtra("_id");
        }

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    getjsonfromnet();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.img_news_photo)).start(savedInstanceState);
    }

    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        NewsActivity.this.finishAfterTransition();
    }*/

    public void getjsonfromnet() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url+id).build();
        System.out.println("url+id" + url+id);
        Response response = client.newCall(request).execute();
        String json = response.body().string();

        Message m = new Message();
        m.what = 1;
        m.obj = json;
        handler.sendMessage(m);

    }
}
