package com.example.demo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import com.example.demo.MyApp;
import com.example.demo.R;
import com.example.demo.adapter.NewsAdapter2;
import com.example.demo.entity.NewsBean;
import com.example.demo.utils.OkHttpUtil;
import com.example.demo.utils.PrefUtils;
import com.google.gson.Gson;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 网址：file:///D:/file/%E5%88%86%E7%B1%BB%E8%B5%84%E6%96%99/%E7%AC%AC%E4%B8%89%E6
 * %96%B9%E6%A1%86%E6%9E%B6/litepal%E6%93%8D%E4%BD%9Csqlite%E6%A1%86%E6%9E%B6/
 * LitePal%E6%95%B0%E6%8D%AE%E5%BA%93%E6%A1%86%E6%9E%B6%E7%9A%84%E4%BD%BF%E7%94%
 * A8%20-%20cbooy%20-%20%E5%8D%9A%E5%AE%A2%E5%9B%AD.mhtml
 *
 * Litepal数据库映射插入到数据库 1.在要插入的对象上面继承DataSupport
 * 2.在application继承LitePalApplication 3.配置
 * 导入jar包，在assets目录下新建litepal.xml，指定数据库名字和版本以及映射关系
 * 
 */
public class LitepalActivity extends BaseActivity implements OnClickListener {
	private NewsAdapter2 adapter;
	private ArrayList<NewsBean> data;
	private String TAG = "LitepalActivity";
	private ListView mListView;
	private Button mdelect;
	private Button select;
	private Button update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.litepal_activity);
		// 初始化数据库 调用此方法即可完成数据库的创建
		SQLiteDatabase writableDatabase = Connector.getWritableDatabase();
		mListView = (ListView) findViewById(R.id.listView1);
		mdelect = (Button) findViewById(R.id.bt_deltct);
		select = (Button) findViewById(R.id.bt_select);
		update = (Button) findViewById(R.id.bt_update);
		// 设置监听器
		mdelect.setOnClickListener(this);
		select.setOnClickListener(this);
		update.setOnClickListener(this);
		sendHttpData("http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html");
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
				// 用litepal保存到数据库中
				NewsBean.saveAll(data);
				adapter = new NewsAdapter2(LitepalActivity.this, data);
				mListView.setAdapter(adapter);

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
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return beans;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_deltct:
			// 根据id删除一条数据
			DataSupport.delete(NewsBean.class, 7);
			DataSupport.deleteAll(NewsBean.class, "docid=?", "BV6040M800031H2L");
			break;
		case R.id.bt_update:
			// 修改一条数据，直接使用静态方法
			// DataSupport.update(Class<?> modelClass, ContentValues values,long
			// id)
			ContentValues values = new ContentValues();
			values.put("title", "李志刚是个傻逼");
			DataSupport.update(NewsBean.class, values, 4);
			// 修改多条，直接使用静态方法 DataSupport.updateAll(News.class, values, "title =
			// ? and commentcount > ?", "xxxxx", "0");
			// DataSupport.updateAll(modelClass, values, conditions)
			// 比如约束条件中有一个占位符，那么后面就应该填写一个参数，如果有两个占位符，后面就应该填写两个参数，以此类推
			ContentValues value = new ContentValues();
			value.put("digest", "李志刚是个傻逼");
			DataSupport.updateAll(NewsBean.class, value, "id=?", "4");
			
			break;
		case R.id.bt_select:
			ArrayList<NewsBean> list = (ArrayList<NewsBean>) DataSupport.where("id like ?", "2").find(NewsBean.class);
			NewsAdapter2 adapter = new NewsAdapter2(LitepalActivity.this, list);
			mListView.setAdapter(adapter);
			// 通用的几个查询方式，使用id
			// NewsBean NewsBean = DataSupport.find(NewsBean.class, 1);
			// NewsBean firstNewsBean = DataSupport.findFirst(NewsBean.class);
			// NewsBean lastNewsBean = DataSupport.findLast(NewsBean.class);
			// List<NewsBean> NewsBeanList = DataSupport.findAll(NewsBean.class,
			// 1, 3, 5, 7);
			// List<NewsBean> NewsBeList = DataSupport.findAll(NewsBean.class,
			// new long[] { 1, 3, 5, 7 });
			// List<NewsBean> allNewsBean = DataSupport.findAll(NewsBean.class);
			break;

		}

	}

}
