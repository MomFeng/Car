package com.hncst.administrator.car.mainviewpager;

import android.app.DownloadManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.car.R;
import com.hncst.administrator.car.Adapter.RecyclerViewThreeAdapter;
import com.hncst.administrator.car.Bean.NewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * APP主界面的第三个Fragment（咨讯）
 * Created by MomFeng on 2017/4/14 0014.
 * <p>
 * 当前信息API接口：http://news-at.zhihu.com/api/4/news/latest
 * 获取点赞数量接口：http://news-at.zhihu.com/api/4/story-extra/
 * long_comments : 长评论总数
 * popularity : 点赞总数
 * short_comments : 短评论总数
 * comments : 评论总数
 */

public class ThreeFragment extends Fragment {

    private RecyclerView review_three;
    private RecyclerViewThreeAdapter adapter;
    public List<NewsBean> mDatas = new ArrayList<>();

    private String url = "http://news-at.zhihu.com/api/4/news/latest";
    private String url1 = "http://news-at.zhihu.com/api/4/story-extra/";

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            review_three.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new RecyclerViewThreeAdapter(getActivity(), mDatas, review_three);
            review_three.setAdapter(adapter);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view_three = mInflater.inflate(R.layout.fragment_three, null);

        review_three = (RecyclerView) view_three.findViewById(R.id.review_three);

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

        return view_three;
    }

    public void getjsonfromnet() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();

        try {
            JSONObject jsonObject = new JSONObject(json);
            String json_stories = jsonObject.getString("stories");
            JSONArray array = new JSONArray(json_stories);
            for (int i = 0; i < array.length(); i++) {
                JSONObject stories = (JSONObject) array.get(i);
                NewsBean bean = new NewsBean();
                bean.setId(stories.getString("id"));
                bean.setTitle(stories.getString("title"));
                JSONArray array_images = new JSONArray(stories.getString("images"));
                bean.setPhotourl((String) array_images.get(0));

                Request request1 = new Request.Builder().url(url1+bean.getId()).build();
                Response response1 = client.newCall(request1).execute();
                String json1 = response1.body().string();
                JSONObject json_c = new JSONObject(json1);
                bean.setMessage(json_c.getString("comments"));
                bean.setStar(json_c.getString("popularity"));

                mDatas.add(bean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Message m = new Message();
        m.what = 1;
        handler.sendMessage(m);
    }
}
