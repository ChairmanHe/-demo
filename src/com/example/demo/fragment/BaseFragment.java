package com.example.demo.fragment;

import com.example.demo.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import butterknife.ButterKnife;

/**
 * 一个通用的frgment
 * 
 * @author hjt
 */
// 笔记：
// 但当我们实例化自定义Fragment时，为什么官方推荐Fragment.setArguments(Bundle
// bundle)这种方式来传递参数，而不推荐通过构造方法直接来传递参数呢？
// fragment.setArguments(args);
public class BaseFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// mViewPager.setOffscreenPageLimit(3);
	}

	public void toast(String text) {
		BaseActivity activity = (BaseActivity) getActivity();
		activity.toast(text);
	}

	public void log(String log) {
		Log.d("TAG", this.getClass().getName() + "" + log);
	}

	public void toastAndLog(String text, String log) {
		toast(text);
		log(log);
	}

	/**
	 * 用来跳转页面
	 */
	public void jump(Class<?> clazz, boolean isFinish) {
		Intent intent = new Intent(getActivity(), clazz);
		startActivity(intent);
		if (isFinish) {
			getActivity().finish();
		}
	}

	/**
	 * 跳转页面
	 * 
	 * @param intent
	 * @param isFinish
	 */
	public void jump(Intent intent, boolean isFinish) {
		startActivity(intent);
		if (isFinish) {
			getActivity().finish();
		}
	}

	/**
	 * 在destory中解除绑定
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

}
