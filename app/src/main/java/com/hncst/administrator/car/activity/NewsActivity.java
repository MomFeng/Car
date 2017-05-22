package com.hncst.administrator.car.activity;

import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.car.R;
import com.hncst.administrator.car.Interface.BindLayout;
import com.hncst.administrator.car.Interface.BindView;
import com.hncst.administrator.car.Interface.BindonClick;
import com.hncst.administrator.car.Interface.MyActivity;
import com.kogitune.activity_transition.ExitActivityTransition;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    @BindView(R.id.btn_news_back)
    private Button btn_news_back;
    @BindView(R.id.tv_three_title)
    private TextView tv_three_title;
    @BindView(R.id.rel_news_loading)
    private RelativeLayout rel_news_loading;
    private ExitActivityTransition exitTransition;

    private String id;
    private String url = "http://news-at.zhihu.com/api/4/news/";

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what){
                case 1:
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
                        rel_news_loading.setVisibility(View.GONE);
                        tv_news_title.setVisibility(View.VISIBLE);
                        //tv_news_content.setText(Html.fromHtml(json_stories));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x12:
                    tv_news_content.setText(((CharSequence) msg.obj));
                    //textview.setText((CharSequence) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ActivityTransition.with(getIntent()).to(findViewById(R.id.img_news_photo)).start(savedInstanceState);
        tv_news_title.setVisibility(View.GONE);
        Intent intent=getIntent();
        if(intent != null)
        {
            String bitmapurl = intent.getStringExtra("bitmapurl");
            tv_three_title.setText(intent.getStringExtra("title"));
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        NewsActivity.this.finishAfterTransition();
    }*/

    public void getjsonfromnet() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url+id).build();
        System.out.println("url+id" + url+id);
        Response response = client.newCall(request).execute();
        String json = response.body().string();

        JSONObject j = new JSONObject(json);
        htmlWebPic(j.getString("body"));

        Message m = new Message();
        m.what = 1;
        m.obj = json;
        handler.sendMessage(m);

    }

    /**
     * 加载html中图片的方法
     */
    public void htmlWebPic(final String htmlContent) {
        Thread t = new Thread(new Runnable() {
            Message msg = handler.obtainMessage();

            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        URL url = null;
                        Drawable drawable = null;
                        try {
                            url = new URL(source);
                            drawable = Drawable.createFromStream(
                                    url.openStream(), null);

                            //int screenWidth = getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
                            //int screenHeight = getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，
                            //int w=screenWidth;
                            //int h=w/(drawable.getIntrinsicWidth()/drawable.getIntrinsicHeight());

                            //drawable.setBounds(0, 0,w,h);
                            drawable.setBounds(0, 0,(drawable.getIntrinsicWidth())*5,(drawable.getIntrinsicHeight())*5);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return drawable;
                    }
                };
                CharSequence result = Html.fromHtml(htmlContent, imageGetter, null);
                msg.what = 0x12;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
        t.start();
    }

    @BindonClick({R.id.btn_news_back})
    public void myonclick(View v){
        switch(v.getId()){
            case R.id.btn_news_back:
                NewsActivity.this.finish();
                break;
        }

    }


}
