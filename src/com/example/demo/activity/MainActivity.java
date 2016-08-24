package com.example.demo.activity;

import com.example.demo.R;
import com.example.demo.utils.ConnectNetTools;
import com.example.demo.utils.NetUtil;
import com.example.demo.view.BottomDialog;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;

//连续按两次退出程序
//程序最小化到桌面，下次进入直接进入到该界面
//点击弹出底部选择
//发送请求时调用网络判断
public class MainActivity extends BaseActivity {

	private long firstExitingTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}

	@OnClick({ R.id.bt_buttom, R.id.bt_dialog, R.id.bt_eventbus, R.id.bt_sendhttp, R.id.bt_plus,R.id.bt_litepal })
	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.bt_buttom:
			// NetworkConnectivityListener.IsNormalNet(); 调用这个方法能判断有没有网络
			// ConnectNetTools.getIntance(this).hasNetwork() 调用这个方法判断网络并且弹出设置框
			if (ConnectNetTools.getIntance(MainActivity.this).hasNetwork()) {
				CreateDialog(this, PHOTO).show();
			}
			// //调用NetUtil判断网络请求 并且弹出设置对话框
			// if(!NetUtil.isNetworkAvalible(MainActivity.this)){
			// NetUtil.checkNetwork(MainActivity.this);
			// }else{
			// CreateDialog(this, PHOTO).show();
			// }
			break;
		case R.id.bt_dialog:
			jump(EventBusActivity.class, false);
			break;
		case R.id.bt_eventbus:
			jump(EventBusActivity.class, false);
			break;
		case R.id.bt_sendhttp:
			jump(HttpActivity.class, false);
			break;
		case R.id.bt_plus:
			jump(PlusHttpActivity.class, false);
			break;
		case R.id.bt_litepal:
			jump(LitepalActivity.class, false);
			break;
		}

	}

	// ================================================================================================
	// 点击弹出底部选择
	public final static int PHOTO = 0;
	public final static int AUDIO = 1;
	public final static int VIDEO = 2;
	private BottomDialog dialog;
	private int curTag;
	private final static String[] photoDialogTxts = { "打开照相机", "从手机相册获取" };
	private final static String[] audioDialogTxts = { "打开麦克风", "从本地获取" };
	private final static String[] videoDialogTxts = { "打开摄像机", "从本地获取" };

	private BottomDialog CreateDialog(Context context, int id) {
		if (dialog == null) {
			dialog = new BottomDialog(context);
			/*
			 * dialog.setFirstButtonClick(click);
			 * dialog.setSecondButtonClick(click);
			 * dialog.setThirdButtonClick(click);
			 */
		}
		curTag = id;
		switch (id) {
		case PHOTO:
			dialog.setButtonText(photoDialogTxts);
			break;
		case AUDIO:
			dialog.setButtonText(audioDialogTxts);
			break;
		case VIDEO:
			dialog.setButtonText(videoDialogTxts);
			break;
		default:
			break;
		}
		return dialog;
	}

	/**
	 * 程序最小化到桌面，下次进入直接进入到该界面
	 */

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 连续按两次退出从程序
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - firstExitingTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
				firstExitingTime = System.currentTimeMillis();
			} else {
				exit(this);
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
