package com.example.administrator.car.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.car.R;
import com.example.administrator.car.util.NetUtil;

import java.util.List;

/**
 * 汽车壁纸的RecyclerView的适配器
 * Created by MomFeng on 2017/5/04 0004.
 */
public class RecyclerViewSimpleAdapter extends RecyclerView.Adapter<MyViewHolder>{

	private LayoutInflater mInflater;
	private Context context;
	protected List<String> mDatas;
	private NetUtil net;

	public interface OnItemClickListener
	{
		void onItemClick(View view, int position);
		void onItemLongClick(View view, int position);
	}
	public OnItemClickListener mOnItemClickListener;

	public void setmOnItemClickListener(OnItemClickListener listener){
		this.mOnItemClickListener = listener;
	}

	
	public RecyclerViewSimpleAdapter(Context context, List<String> datas){
		this.context = context;
		net = new NetUtil();
		mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
	}
	
	@Override
	public int getItemCount() {	
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		// TODO Auto-generated method stub
		//11
		holder.img.setImageResource(R.mipmap.ic_launcher);
		net.setimagefromnet(holder.img , mDatas.get(position));
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
