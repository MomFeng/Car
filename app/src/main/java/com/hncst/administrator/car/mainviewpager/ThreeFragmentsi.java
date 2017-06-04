package com.hncst.administrator.car.mainviewpager;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.administrator.car.R;
import com.hncst.administrator.car.Adapter.RecyclerViewThreeAdapter;
import com.hncst.administrator.car.Bean.NewsBean;
import com.hncst.administrator.car.activity.NewsActivity;
import com.hncst.administrator.car.util.SimpleUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
public class ThreeFragmentsi extends Fragment {


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view_three = mInflater.inflate(R.layout.fragment_rv_three_si, null);

        return view_three;
    }
}
