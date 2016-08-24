package com.example.demo.activity;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.demo.MyApp;
import com.example.demo.R;
import com.example.demo.adapter.NewsAdapter2;
import com.example.demo.entity.EventBusTest;
import com.example.demo.entity.NewsBean;
import com.example.demo.utils.DateUtil;
import com.example.demo.utils.OkHttpUtil;
import com.example.demo.utils.PrefUtils;
import com.example.demo.view.XListView;
import com.example.demo.view.XListView.IXListViewListener;
import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

/**
 * 1. XListView 实现上拉加载下拉刷新
 *  2. 布局里面实现了自定义的progressbar的图片旋转 步骤
 * 需要在progressbar中引入style 然后自定义一个style 然后style中引入anima中自定义的一个旋转动画
 */
public class PlusHttpActivity extends BaseActivity implements IXListViewListener {
	private XListView mListView;
	private ArrayList<NewsBean> mData;
	private NewsAdapter2 mAdapter;
	private String TAG = "PlusHttpActivity";
	/**
	 * 发送请求后得到的数据
	 */
	private ArrayList<NewsBean> data;
	private List<NewsBean> datas = new ArrayList<NewsBean>();
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plus_http_activity);
		// 注册EventBus
		EventBus.getDefault().register(this);
		mListView = (XListView) findViewById(R.id.xlistview);
		// 设置上拉加载
		mListView.setPullLoadEnable(true);
		// 设置刷新可用
		// mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		sendHttpData("http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html");
	}

	// 第三 在需要接受消息的地方写这个方法掉用
	@Subscribe(threadMode = ThreadMode.MAIN) // 在ui线程执行
	public void onUserEvent(ArrayList<NewsBean> data) {
		mAdapter = new NewsAdapter2(this, data);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// 将之前的数据清空 然后开始刷新数据
				data.clear();
				sendHttpData("http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html");
				onUserEvent(data);
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				sendHttpData("http://c.m.163.com/nc/article/headline/T1348647909107/21-40.html");
				onUserEvent(data);
				datas.addAll(data);
				mAdapter.notifyDataSetChanged();
			}
		}, 2000);

	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(DateUtil.getCurrentDate());
	}

	/**
	 * 发送http请求得到当前的数据
	 * 
	 * @param url
	 */
	private void sendHttpData(final String url) {
		OkHttpUtil.ResultCallback<String> loadNewsCallback = new OkHttpUtil.ResultCallback<String>() {
			@Override
			public void onSuccess(String response) {
				// 数据刷新完后隐藏
				Log.i(TAG, response.toString());
				// 将解析出来的json保存到SharedPreferences 键就是url的值 值就是json字符串
				PrefUtils.setString(MyApp.getInstance(), url, response);
				data = (ArrayList<NewsBean>) parseJson(response);
				// 发送消息
				EventBus.getDefault().post(data);
			}

			@Override
			public void onFailure(Exception e) {

			}
		};
		OkHttpUtil.get(url, loadNewsCallback);
	}

	// josn数据解析
	private List<NewsBean> parseJson(String response) {
		Gson gson = new Gson();
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

	@Override
	protected void onDestroy() {
		// 第二：解除绑定
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
}
