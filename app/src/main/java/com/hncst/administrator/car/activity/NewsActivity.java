package com.hncst.administrator.car.activity;

import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
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
    @BindView(R.id.avi_news_contentloading)
    private AVLoadingIndicatorView avi_news_contentloading;
    @BindView(R.id.tv_news_contentloading)
    private TextView tv_news_contentloading;
    @BindView(R.id.btn_news_comment)
    private Button btn_news_comment;

    @BindView(R.id.web_news_news)
    private WebView web_news_news;

    private ExitActivityTransition exitTransition;

    private String id;
    private String url = "http://news-at.zhihu.com/api/4/news/";
    private String share_html = "";
    private String share_css = "";

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what){
                case 1:
                    JSONObject jsonObject = null;
                    //String json_stories = "";
                    try {
                        jsonObject = new JSONObject((String) msg.obj);
                        share_html = jsonObject.getString("body");

                        /*String filePath = "/sdcard/Test/";
                        String fileName = "log.txt";
                        writeTxtToFile(share_html, filePath, fileName);*/
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

                    /**
                     * 运用网页的加载方式加载数据
                     */
                    //<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />;
                    //share_css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />"+share_css;
                    //web_news_news.loadUrl(share_html);
                    web_news_news.loadDataWithBaseURL(null , share_html , "text/html", "UTF-8", null);
                    web_news_news.setWebViewClient(new WebViewClient() {

                        @Override
                        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            // TODO Auto-generated method stub
                            //view.loadUrl(share_html);
                            view.loadDataWithBaseURL(null , share_html , "text/html", "UTF-8", null);
                            // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                            return true;
                        }
                    });
                    break;
                case 0x12:
                    avi_news_contentloading.setVisibility(View.GONE);
                    tv_news_contentloading.setVisibility(View.GONE);
                    btn_news_comment.setVisibility(View.VISIBLE);
                    //tv_news_content.setText(((CharSequence) msg.obj));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 加载网页
         */
        web_news_news.getSettings().setJavaScriptEnabled(true);// 开启对javascript支持
        web_news_news.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//开启缓存
        web_news_news.setFocusable(true);


        //ActivityTransition.with(getIntent()).to(findViewById(R.id.img_news_photo)).start(savedInstanceState);
        tv_news_title.setVisibility(View.GONE);
        btn_news_comment.setVisibility(View.GONE);
        Intent intent=getIntent();
        if(intent != null)
        {
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
        String urk_css = j.getString("css");
        JSONArray url_css = new JSONArray(urk_css);
        String css = (String) url_css.get(0);
        Request request1 = new Request.Builder().url(css).build();
        Response response1 = client.newCall(request1).execute();
        String share_css = response1.body().string();
        System.out.println("json1---------" + share_css);

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
                            //float h=w/(drawable.getIntrinsicWidth()/drawable.getIntrinsicHeight());

                            //drawable.setBounds(0, 0,screenWidth,(int)h);
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

    @BindonClick({R.id.btn_news_back , R.id.btn_news_comment})
    public void myonclick(View v){
        switch(v.getId()){
            case R.id.btn_news_back:
                NewsActivity.this.finish();
                break;
            //查看评论点击事件
            case R.id.btn_news_comment:
                Intent i = new Intent(NewsActivity.this , CommentActivity.class);
                startActivity(i);
                break;
        }
    }


    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }

}
