package com.hncst.administrator.car.mainviewpager;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.administrator.car.R;
import com.hncst.administrator.car.Adapter.RecyclerViewSimpleAdapter;
import com.hncst.administrator.car.Adapter.RecyclerViewThreeAdapter;
import com.hncst.administrator.car.Bean.NewsBean;
import com.hncst.administrator.car.activity.NewsActivity;
import com.hncst.administrator.car.util.RecyclerViewClickListener;
import com.hncst.administrator.car.util.RecyclerViewClickListener2;
import com.hncst.administrator.car.util.SimpleUtil;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.administrator.car.R.string.intent;

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
 * <p>
 * 采用ActivityOptions下的方法进行跳转，6.0手机闪退，4.4测试通过，暂时放弃。
 * <p>
 * RecyclerView添加，删除，更新数据：
 * notifyDataSetChanged()：更新所有数据
 * notifyItemInserted(int position)：在position位置插入数据的时候更新
 * notifyItemRemoved(int position)：移除postion位置的数据的时候更新
 * notifyItemChanged(int position)：当postion位置数据有改变时候更新
 * notifyItemMoved(int fromPosition, int toPosition)：移除从位置formPosition到toPosition位置数据更新
 * notifyItemRangeChanged(int positionStart, int itemCount)
 * notifyItemRangeInserted(int positionStart, int itemCount)
 * notifyItemRangeRemoved(int positionStart, int itemCount)
 */
public class ThreeFragment extends Fragment {

    private RecyclerView review_three;
    private RecyclerViewThreeAdapter adapter;
    public List<NewsBean> mDatas = new ArrayList<>();
    private LinearLayout lin_three_loading;

    private AVLoadingIndicatorView avi_three_loading;
    private SwipeRefreshLayout swipe_three_refresh;

    private String url = "http://news-at.zhihu.com/api/4/news/latest";
    private String url1 = "http://news-at.zhihu.com/api/4/story-extra/";

    //第一次刷新的数据条数
    private int refresh_one;
    //第二次刷新的数据条数
    private int refresh_two;

    final Handler handler = new Handler() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:

                    review_three.setLayoutManager(new LinearLayoutManager(getActivity()));
                    lin_three_loading.setVisibility(View.GONE);
                    adapter = new RecyclerViewThreeAdapter(getActivity(), mDatas, review_three);
                    review_three.setAdapter(adapter);

                    adapter.setmOnItemClickListener(new RecyclerViewThreeAdapter.OnItemClickListener() {
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onItemClick(View view, int position) {
                            SimpleUtil.showToast(getActivity(), "Click---" + position);

                            /**
                             * google的MD风格方案
                             */
                            Intent intent = new Intent(getActivity(), NewsActivity.class);
                            // shareView: 需要共享的视图
                            // "shareName": 设置的android:transitionName="shareName"
                            String bitmapurl = mDatas.get(position).getPhotourl();
                            intent.putExtra("bitmapurl", bitmapurl);
                            intent.putExtra("title", mDatas.get(position).getTitle());
                            intent.putExtra("_id", mDatas.get(position).getId());
                            startActivity(intent);

                    /*ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(), view, "shareName");
                    ActivityCompat.startActivity(getActivity(), intent,
                            options.toBundle());*/


                    /*ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), view, "shareName");
                    startActivity(intent, options.toBundle());*/

                            /**
                             * 第三方的方案
                             */
                    /*final Intent intent = new Intent(getActivity(), NewsActivity.class);
                    String bitmapurl = mDatas.get(position).getPhotourl();
                    intent.putExtra("bitmapurl", bitmapurl);
                    ActivityTransitionLauncher.with(getActivity()).from(view).launch(intent);*/
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {
                            SimpleUtil.showToast(getActivity(), "LongClick---" + position);
                        }
                    });
                    break;
                //无新的数据可刷新
                case 0x02:
                    showsnackbar();
                    swipe_three_refresh.setRefreshing(false);
                    break;
                //有新的数据可刷新
                case 0x00:
                    adapter.notifyDataSetChanged();
                    swipe_three_refresh.setRefreshing(false);
                    break;
            }


        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view_three = mInflater.inflate(R.layout.fragment_three, null);

        //清除数据
        mDatas.clear();

        lin_three_loading = (LinearLayout) view_three.findViewById(R.id.lin_three_loading);
        avi_three_loading = (AVLoadingIndicatorView) view_three.findViewById(R.id.avi_three_loading);
        review_three = (RecyclerView) view_three.findViewById(R.id.review_three);
        swipe_three_refresh = (SwipeRefreshLayout) view_three.findViewById(R.id.swipe_three_refresh);

        //设置删除添加效果
        review_three.setItemAnimator(new DefaultItemAnimator());
        swipe_three_refresh.setColorSchemeColors(Color.parseColor("#44aaff"));
        //初始化
        adapter = new RecyclerViewThreeAdapter();

        //获取数据的线程
        new Thread() {
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

        swipe_three_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            getjsonfromnetrefresh();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                /*NewsBean bean = new NewsBean();
                bean.setStar("111");
                bean.setMessage("15");
                bean.setTitle("新刷新出来的");
                bean.setId("9434365");
                bean.setPhotourl("https://pic2.zhimg.com/v2-b2a8a66b8a369f6c7026713656b62de1.jpg");
                adapter.addData(1,bean);*/

                /*for (int i = 0; i < 5; i++) {
                    mDatas.add(0,bean);
                }
                adapter.addDatalot(1,5);*/
                /*adapter.addData(1,bean);
                adapter.addData(2,bean);
                adapter.addData(3,bean);
                adapter.addData(4,bean);
                adapter.addData(5,bean);*/

                /*for (int i = 0; i < mDatas.size(); i++) {
                    adapter.deleteDate(i);
                }*/

                //adapter.notifyItemRangeRemoved(0, 5);

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                //adapter.notifyDataSetChanged();

            }
        });

        //第一种点击事件方法
        /**review_three.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(),new RecyclerViewClickListener.OnItemClickListener() {
        @Override public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(),"Click "+position,Toast.LENGTH_SHORT).show();
        final ImageView img = (ImageView) view.findViewById(R.id.img_three_like);
        img.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        img.setImageResource(R.mipmap.timeline_trend_icon_like);
        }
        });
        }

        @Override public void onItemLongClick(View view, int position) {
        Toast.makeText(getActivity() ,"Long Click "+mDatas.get(position),Toast.LENGTH_SHORT).show();
        }
        }));*/

        /**
         * 运用GestureDetector点击事件的方法*/
        /*review_three.addOnItemTouchListener(new RecyclerViewClickListener2(getActivity(), review_three,
                new RecyclerViewClickListener2.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        SimpleUtil.showToast(getActivity() , "Click " + position);

                        ImageView img = (ImageView) view.findViewById(R.id.img_three_like);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SimpleUtil.showToast(getActivity() , "执行到了图片的点击事件");
                            }
                        });

                        Toast.makeText(getActivity(), "Click " + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        SimpleUtil.showToast(getActivity() , "Click " + position);
                        //Toast.makeText(getActivity(), "Long Click " + position, Toast.LENGTH_SHORT).show();
                    }
                }));*/
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
            refresh_one = array.length();
            for (int i = 0; i < array.length(); i++) {
                JSONObject stories = (JSONObject) array.get(i);
                NewsBean bean = new NewsBean();
                bean.setId(stories.getString("id"));
                bean.setTitle(stories.getString("title"));
                JSONArray array_images = new JSONArray(stories.getString("images"));
                bean.setPhotourl((String) array_images.get(0));

                Request request1 = new Request.Builder().url(url1 + bean.getId()).build();
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

    public void getjsonfromnetrefresh() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();

        try {
            JSONObject jsonObject = new JSONObject(json);
            String json_stories = jsonObject.getString("stories");
            JSONArray array = new JSONArray(json_stories);
            //如果有新的数据
            if (array.length() > refresh_one) {
                refresh_one+=1;
                JSONObject stories = (JSONObject) array.get(0);
                NewsBean bean = new NewsBean();
                bean.setId(stories.getString("id"));
                bean.setTitle(stories.getString("title"));
                JSONArray array_images = new JSONArray(stories.getString("images"));
                bean.setPhotourl((String) array_images.get(0));

                Request request1 = new Request.Builder().url(url1 + bean.getId()).build();
                Response response1 = client.newCall(request1).execute();
                String json1 = response1.body().string();
                JSONObject json_c = new JSONObject(json1);
                bean.setMessage(json_c.getString("comments"));
                bean.setStar(json_c.getString("popularity"));

                mDatas.add(0,bean);

                Message m = new Message();
                m.what = 0x00;
                handler.sendMessage(m);
            }
            //没有新的数据
            else {
                Message m = new Message();
                m.what = 0x02;
                handler.sendMessage(m);
            }

            /*for (int i = 0; i < array.length(); i++) {
                JSONObject stories = (JSONObject) array.get(i);
                NewsBean bean = new NewsBean();
                bean.setId(stories.getString("id"));
                bean.setTitle(stories.getString("title"));
                JSONArray array_images = new JSONArray(stories.getString("images"));
                bean.setPhotourl((String) array_images.get(0));

                Request request1 = new Request.Builder().url(url1 + bean.getId()).build();
                Response response1 = client.newCall(request1).execute();
                String json1 = response1.body().string();
                JSONObject json_c = new JSONObject(json1);
                bean.setMessage(json_c.getString("comments"));
                bean.setStar(json_c.getString("popularity"));

                mDatas.add(bean);
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 顶部弹出Snackbar的方法
     */
    private void showsnackbar() {
        TSnackbar snackbar = TSnackbar
                .make(getActivity().findViewById(android.R.id.content), "暂无新内容!", TSnackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#ffffff"));
        snackbar.setAction("cancel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        snackbar.setActionTextColor(Color.parseColor("#ff0000"));
        snackbarView.setBackgroundColor(Color.parseColor("#44aaff"));
        snackbar.show();
    }

}
