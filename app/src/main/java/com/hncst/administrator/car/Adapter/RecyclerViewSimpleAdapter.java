package com.hncst.administrator.car.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.administrator.car.R;
import com.hncst.administrator.car.util.NetUtil;

import java.util.List;

/**
 * 汽车壁纸的RecyclerView的适配器
 * Created by MomFeng on 2017/5/04 0004.
 */
public class RecyclerViewSimpleAdapter extends RecyclerView.Adapter<MyViewHolder>{

	private LayoutInflater mInflater;
	protected List<String> mDatas;
	private NetUtil net;
	public static String[] URLS;
	private Context mContext;

	public interface OnItemClickListener
	{
		void onItemClick(View view, int position);
		void onItemLongClick(View view, int position);
	}
	public OnItemClickListener mOnItemClickListener;

	public void setmOnItemClickListener(OnItemClickListener listener){
		this.mOnItemClickListener = listener;
	}

	
	public RecyclerViewSimpleAdapter(Context context, List<String> datas , RecyclerView recyclerView){
		this.mContext = context;
		net = new NetUtil(recyclerView);
		mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
		URLS = new String[datas.size()];
		for(int i=0;i< datas.size();i++){
			URLS[i] = datas.get(i);
		}
	}

	@Override
	public int getItemCount() {	
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		// TODO Auto-generated method stub
		holder.img.setAdjustViewBounds(true);
		ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
		layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		/*ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
		params.height = heights.get(position);//把随机的高度赋予itemView布局
		holder.itemView.setLayoutParams(params);//把params设置给itemView布局*/

		/*LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.img.getLayoutParams();
		float itemwidth = holder.itemView.getContext()*/

		/**
		 * 自己的图片加载库
		 * holder.img.setImageResource(R.mipmap.ic_launcher);
		 * holder.img.setTag(mDatas.get(position));
		 * System.out.println("设置图片TAG----------" + mDatas.get(position));
		 * net.setimagefromnet(mDatas.get(position));
		 * */

		/***
		 * Glide图片加载框架
		 */
		Glide.with(mContext)
				.load(mDatas.get(position))
				.placeholder(R.mipmap.ic_launcher)
				.crossFade()
				.into(holder.img);

		setUpItemEvent(holder);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		View view = mInflater.inflate(R.layout.item_rv_photo, arg0,false);
		MyViewHolder viewHolder = new MyViewHolder(view);
		return viewHolder;
	}

	public void addData(int pos){
		//mDatas.add(pos , "Insert one");

		//notifyDataSetChanged();
		notifyItemInserted(pos);
	}

	public void deleteDate(int pos){
		//mDatas.remove(pos);
		notifyItemRemoved(pos);
	}

	protected void setUpItemEvent(final MyViewHolder holder){
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

class MyViewHolder extends ViewHolder{

	ImageView img;

	public MyViewHolder(View arg0) {
		super(arg0);

		img = (ImageView) arg0.findViewById(R.id.img_photo_view);
		// TODO Auto-generated constructor stub
	}
}
