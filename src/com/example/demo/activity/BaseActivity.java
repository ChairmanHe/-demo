package com.example.demo.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 一个通用的activity
 * 
 * @author Administrator
 */
public class BaseActivity extends FragmentActivity {
	Toast toast;
	/**
	 * 存放一个activity的集合
	 */
	public static List<Activity> sActivityList = new ArrayList<Activity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		// 用来设置屏幕横屏显示
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//去掉activity的标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 用来添加所有的activity到集合里面，用来退出所有的activity
		sActivityList.add(this);
	}

	/**
	 * 按两次后退出应用程序
	 */
	public static void exit(Context context) {
		try {
			for (int i = 0; i < sActivityList.size(); i++) {
				if (null != sActivityList.get(i)) {
					sActivityList.get(i).finish();
				}
			}
		} catch (Exception e) {
		}
	}

	// =====================================================================

	/**
	 * Toast和Log的输出
	 * 
	 * @param text
	 */
	public void toast(String text) {
		toast.setText(text);
		toast.show();
	}

	/**
	 * log的输出
	 * 
	 * @param log
	 */
	public void log(String log) {
		Log.d("TAG", this.getClass().getName() + "输出：" + log);
	}

	public void toastAndLog(String text, String log) {
		toast(text);
		log(log);
	}

	/**
	 * 界面的跳转
	 * 
	 * @param clazz
	 * @param isFinish
	 */
	public void jump(Class<?> clazz, boolean isFinish) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		if (isFinish) {
			this.finish();
		}
	}

	/**
	 * 
	 * @param intent
	 * @param isFinish
	 */
	public void jump(Intent intent, boolean isFinish) {
		startActivity(intent);
		if (isFinish) {
			this.finish();
		}
	}

	/**
	 * 判空 返回true，就说明有EditText未输入内容 返回false，就说明EditText都输入了
	 */
	public boolean isEmpty(EditText... ets) {

		for (EditText editText : ets) {
			if (TextUtils.isEmpty(editText.getText().toString())) {
				// 如果直接为setError方法提供Stirng类型参数
				// 有可能出现的提示文字使用的颜色，与错误提示框的背景色重复
				// 造成提示文字不可见
				// 但是，setError接受的参数类型是CharSequence类型
				// 所以，更换一下参数的类型，不是用标准的Stirng，而是使用安卓提供的
				// 可扩展String，为可扩展Stirng指定一个不同于提示框背景的颜色
				// <font color="颜色值">
				// editText.setError(Html.fromHtml("<font
				// color=#ff0000>请输入完整</font>"));
				return true;
			}
		}

		return false;
	}

	/**
	 * 增加一个启动activity的动画
	 */
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
	}

	/**
	 * 重写返回按钮 增加一个返回finish的动画
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

	/**
	 * 重写finish方法 增加一个activity结束掉的动画
	 */
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

}
