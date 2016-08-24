package com.example.demo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.demo.MyApp;
import com.example.demo.R;
import com.example.demo.adapter.NewsAdapter;
import com.example.demo.entity.NewsBean;
import com.example.demo.utils.CacheUtils;
import com.example.demo.utils.OkHttpUtil;
import com.example.demo.utils.PrefUtils;
import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

/**
 * 通过网络请求来加载图片
 * 
 * @author Administrator
 *
 */
// 缓存json
// OKhttp封装类发送请求
// SwipeRefreshLayout谷歌自带的下拉刷新
// RecyclerView 代替listview
// gson解析
// 发送网络请求后将json数据保存到SharedPreferences中 下次取数据 如果有数据 直接从SharedPreferences中取
// 然后在发送网络请求

// String url =
// "http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";

public class HttpActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

	String TAG = "HttpActivity";
	/**
	 * Google官方推出的下拉刷新的控件
	 */
	private SwipeRefreshLayout mSwipeRefreshWidget;
	/**
	 * support.v7包下面自带的recyclerview
	 */
	private RecyclerView mRecyclerView;
	private Gson gson = new Gson();
	/**
	 * 解析得到的新闻数据
	 */
	private ArrayList<NewsBean> datas;
	private NewsAdapter mAdapter;
	private LinearLayoutManager mLayoutManager;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
		// 初始化控件
		setviews();
		// 发送请求获取数据 这里发送之前在请求的数据里面去找
		String cache = CacheUtils.getCache(url, MyApp.getInstance());
		if (!TextUtils.isEmpty(cache)) {
			datas = (ArrayList<NewsBean>) parseJson(cache); // 如果缓存存在,直接解析数据,
															// 无需访问网路
		}

		// onRefresh();
		// sendHttpData("http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html");
		// // 不管有没有缓存,
		// 都获取最新数据,
		// 保证数据最新
		// 添加RecyclerView滑动的监听器
		mRecyclerView.setOnScrollListener(mOnScrollListener);
	}

	/**
	 * 添加RecyclerView滑动的监听器 SCROLL_STATE_FLING，这个参数表示你手离开后ListView还在“飞”中，理解？
	 * SCROLL_STATE_IDLE，这个参数表示ListView停下不动了
	 * SCROLL_STATE_TOUCH_SCROLL，这个参数表示你手还在ListView上
	 */
	private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
		private int lastVisibleItem;

		@Override
		public void onScrollStateChanged(int newState) {
			if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
				// 加载更多
				sendHttpData("http://c.m.163.com/nc/article/headline/T1348647909107/25-35.html");
			}
		}

		@Override
		public void onScrolled(int arg0, int arg1) {
			// 寻找最后一个item的下标值
			lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
		}
	};

	// 设置adapter
	private void setAdapter() {
		mAdapter = new NewsAdapter(this);
		mAdapter.setmDate(datas);
		mRecyclerView.setAdapter(mAdapter);
	}

	private void setviews() {
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		// 设置为垂直布局，这也是默认的
		// mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
		// 设置增加或删除条目的动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
		// 通过颜色资源文件设置进度动画的颜色资源
		mSwipeRefreshWidget.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.primary_light,
				R.color.accent);

		// 添加刷新的监听器 setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener
		// listener)
		mSwipeRefreshWidget.setOnRefreshListener(this);

		// 2 setRefreshing(boolean refreshing)
		// 通知部件刷新状态改变了
		// refreshing View是否显示刷新进度
		onRefresh();
	}

	// 显示进度框
	public void showProgress() {
		mSwipeRefreshWidget.setRefreshing(true);
	}

	// 隐藏进度框
	public void hideProgress() {
		mSwipeRefreshWidget.setRefreshing(false);
	}

	/**
	 * 重写下拉刷新的方法
	 */
	@Override
	public void onRefresh() {
		showProgress();
		sendHttpData("http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html");
	}

	private void sendHttpData(final String url) {
		OkHttpUtil.ResultCallback<String> loadNewsCallback = new OkHttpUtil.ResultCallback<String>() {
			@Override
			public void onSuccess(String response) {
				// 数据刷新完后隐藏
				hideProgress();
				Log.i(TAG, response.toString());
				// 将解析出来的json保存到SharedPreferences 键就是url的值 值就是json字符串
				PrefUtils.setString(MyApp.getInstance(), url, response);
				datas = (ArrayList<NewsBean>) parseJson(response);
				// 设置RecyclerView的adapter
				setAdapter();
			}

			@Override
			public void onFailure(Exception e) {
				Log.i(TAG, e.toString());
			}
		};
		OkHttpUtil.get(url, loadNewsCallback);
	}

	// josn数据解析
	private List<NewsBean> parseJson(String response) {
		ArrayList<NewsBean> beans = new ArrayList<NewsBean>();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray jsonArray = json.getJSONArray("T1348647909107");
			for (int i = 1; i < jsonArray.length(); i++) {
				JSONObject jo = jsonArray.getJSONObject(i);
				NewsBean news = gson.fromJson(jo.toString(), NewsBean.class);
				beans.add(news);
			}
			Log.i(TAG, "" + beans.size());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return beans;
	}
	/**
	 * 设置请求回调监听
	 */
	// public interface OnLoadNewsListListener {
	// void onSuccess(List<NewsBean> list);
	// void onFailure(String msg, Exception e);
	// }
	// 显示加载失败的信息

}
