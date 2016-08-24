package com.example.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.R;
import com.example.demo.entity.NewsBean;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView的adapter
 * 
 * @author Administrator
 *
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_FOOTER = 1;
	private Context mContext;
	private List<NewsBean> mData;

	public NewsAdapter(Context context) {
		this.mContext = context;
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 */
	public void setmDate(List<NewsBean> mData) {
		if (mData != null) {
			this.mData = mData;
		} else {
			mData = new ArrayList<NewsBean>();
			this.mData = mData;
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	// 这个方法主要用于适配渲染数据到View中。方法提供给你了一个viewHolder，而不是原来的convertView。
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (holder instanceof ItemViewHolder) {
			NewsBean news = mData.get(position);
			if (news == null) {
				return;
			}
			((ItemViewHolder) holder).mTitle.setText(news.getTitle());
			((ItemViewHolder) holder).mDesc.setText(news.getDigest());
			// 用毕加索加载图片
			Picasso.with(mContext).load(news.getImgsrc()).into(((ItemViewHolder) holder).mNewsImg);
			// Placeholders-空白或者错误占位图片：picasso提供了两种占位图片，未加载完成或者加载发生错误的时需要一张图片作为提示。
			// Picasso.with(context).load(url).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder_error).into(imageView);
			// 图片转换：转换图片以适应布局大小并减少内存占用
			// Picasso.with(context).load(url).resize(50, 50).centerCrop().into(imageView);
		}

	}

	// 这个方法主要生成为每个Item
	// inflater出一个View，但是该方法返回的是一个ViewHolder。方法是把View直接封装在ViewHolder中，
	// 然后我们面向的是ViewHolder这个实例，当然这个ViewHolder需要我们自己去编写。
	// 直接省去了当初的convertView.setTag(holder)和convertView.getTag()这些繁琐的步骤。

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
		ItemViewHolder vh = new ItemViewHolder(v);
		return vh;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	public class ItemViewHolder extends RecyclerView.ViewHolder {

		public TextView mTitle;
		public TextView mDesc;
		public ImageView mNewsImg;

		public ItemViewHolder(View v) {
			super(v);
			mTitle = (TextView) v.findViewById(R.id.tvTitle);
			mDesc = (TextView) v.findViewById(R.id.tvDesc);
			mNewsImg = (ImageView) v.findViewById(R.id.ivNews);
		}
	}

}
