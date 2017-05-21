package com.hncst.administrator.car.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.car.R;
import com.hncst.administrator.car.Bean.NewsBean;
import com.hncst.administrator.car.Text;

import java.util.List;

/**
 * 咨讯页面的recyclerview的适配器
 * Created by MomFeng on 2017/5/20 0020.
 */

public class RecyclerViewThreeAdapter extends RecyclerView.Adapter<MyViewHolder_three>{

    private LayoutInflater mInflater;
    protected List<NewsBean> mDatas;
    private Context mContext;

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public RecyclerViewThreeAdapter.OnItemClickListener mOnItemClickListener;

    public RecyclerViewThreeAdapter(){

    }

    public RecyclerViewThreeAdapter(Context context, List<NewsBean> datas , RecyclerView recyclerView){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    public void setmOnItemClickListener(RecyclerViewThreeAdapter.OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder_three onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_rv_three, parent, false);
        MyViewHolder_three viewHolder = new MyViewHolder_three(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder_three holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        holder.tv_title.setText(mDatas.get(position).getTitle());
        holder.tv_three_message.setText(mDatas.get(position).getMessage());
        holder.tv_three_star.setText(mDatas.get(position).getStar());

        /***
         * Glide图片加载框架
         */
        Glide.with(mContext)
                .load(mDatas.get(position).getPhotourl())
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.img_images);

        holder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.img_like.setImageResource(R.mipmap.timeline_trend_icon_like);
            }
        });

        setUpItemEvent(holder);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    protected void setUpItemEvent(final MyViewHolder_three holder){
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });

            //longclick
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView , layoutPosition);
                    return false;
                }
            });
        }
    }
}

class MyViewHolder_three extends RecyclerView.ViewHolder {

    TextView tv_title, tv_three_star, tv_three_message;
    ImageView img_images , img_like;

    public MyViewHolder_three(View arg0) {
        super(arg0);

        tv_title = (TextView) arg0.findViewById(R.id.tv_three_title);
        img_images = (ImageView) arg0.findViewById(R.id.img_three_images);
        tv_three_star = (TextView) arg0.findViewById(R.id.tv_three_star);
        tv_three_message = (TextView) arg0.findViewById(R.id.tv_three_message);
        img_like = (ImageView) arg0.findViewById(R.id.img_three_like);
        // TODO Auto-generated constructor stub
    }
}
