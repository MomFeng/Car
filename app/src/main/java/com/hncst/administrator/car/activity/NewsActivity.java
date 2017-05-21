package com.hncst.administrator.car.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.car.R;
import com.hncst.administrator.car.Interface.BindLayout;
import com.hncst.administrator.car.Interface.BindView;
import com.hncst.administrator.car.Interface.MyActivity;
import com.kogitune.activity_transition.ActivityTransition;

/**
 * 展示咨讯详情页的activity
 * Created by MomFeng on 2017/5/21 0021.
 */
@BindLayout(R.layout.activity_news)
public class NewsActivity extends MyActivity{

    @BindView(R.id.img_news_photo)
    private ImageView img_news_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTransition.with(getIntent()).to(findViewById(R.id.img_news_photo)).start(savedInstanceState);


        Intent intent=getIntent();
        if(intent !=null)
        {
            String bitmapurl = intent.getStringExtra("bitmapurl");
            Glide.with(NewsActivity.this).load(bitmapurl).into(img_news_photo);
        }
    }
}
