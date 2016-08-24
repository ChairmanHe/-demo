package com.example.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.R;
import com.example.demo.adapter.NewsAdapter.ItemViewHolder;
import com.example.demo.entity.NewsBean;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView的adapter
 */
public class NewsAdapter2 extends BaseAdapter {
	private Context Context;
	private List<NewsBean> mData;

	public NewsAdapter2(Context context, ArrayList<NewsBean> mData) {
		this.Context = context;
		this.mData = mData;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(Context).inflate(R.layout.item_news, parent, false);
			holder = new ViewHolder();
			holder.mTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.mDesc = (TextView) convertView.findViewById(R.id.tvDesc);
			holder.mNewsImg = (ImageView) convertView.findViewById(R.id.ivNews);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		NewsBean news = mData.get(position);
		holder.mTitle.setText(news.getTitle());
		holder.mDesc.setText(news.getDigest());
		// 用毕加索加载图片
		Picasso.with(Context).load(news.getImgsrc()).into(holder.mNewsImg);
		return convertView;
	}

	class ViewHolder {
		TextView mTitle;
		TextView mDesc;
		ImageView mNewsImg;
	}
}
