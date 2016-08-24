package com.example.demo.activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.example.demo.R;
import com.example.demo.entity.EventBusTest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * eventbus的使用方法
 * 
 * @author Administrator
 *
 */
public class EventBusActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_bus);
		// 第一： 在需要接受消息的类oncreat中注册
		EventBus.getDefault().register(this);

		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 发送消息
				EventBus.getDefault().post(new EventBusTest("测试", "测试成功"));
			}
		});

	}

	// 第三 在需要接受消息的地方写这个方法掉用
	@Subscribe(threadMode = ThreadMode.MAIN) // 在ui线程执行
	public void onUserEvent(EventBusTest event) {
		Log.i("harvic", "OnEvent收到了消息：" + event.getA());
	}

	@Subscribe(threadMode = ThreadMode.BACKGROUND) // 在后台线程执行
	public void onUser1Event(EventBusTest event) {
		Log.i("harvic", "OnEvent1收到了消息：" + event.getA());
	}

	@Subscribe(threadMode = ThreadMode.ASYNC) // 强制在后台执行
	public void onUser2Event(EventBusTest event) {
		Log.i("harvic", "OnEvent2收到了消息：" + event.getA());
	}

	@Subscribe(threadMode = ThreadMode.POSTING) // 默认方式, 在发送线程执行
	public void onUser3Event(EventBusTest event) {
		Log.i("harvic", "OnEvent3收到了消息：" + event.getA());
	}

	// 这个是以前的eventbus的方法 3.0以后就换了
	// public void onEvent(EventBusTest event) {
	// Log.d("harvic", "OnEvent收到了消息：" + event.getA());
	// }
	//
	// // SecondEvent接收函数一
	// public void onEventMainThread(EventBusTest event) {
	//
	// Log.d("harvic", "onEventMainThread收到了消息：" + event.getA());
	// }
	//
	// // EventBusTest接收函数二
	// public void onEventBackgroundThread(EventBusTest event) {
	// Log.d("harvic", "onEventBackground收到了消息：" + event.getA());
	// }
	//
	// // EventBusTest接收函数三
	// public void onEventAsync(EventBusTest event) {
	// Log.d("harvic", "onEventAsync收到了消息：" + event.getA());
	// }

	@Override
	protected void onDestroy() {
		// 第二：解除绑定
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

}
