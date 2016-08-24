package com.example.demo;

import org.litepal.LitePalApplication;

import com.example.demo.listener.NetworkConnectivityListener;

public class MyApp extends LitePalApplication {
	/**
	 * 网络的监听
	 */
	private NetworkConnectivityListener mNetChangeReceiver;

	private static MyApp instance;
	// 保存网络状态
	private int netType;

	public static MyApp getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		// 1.启动网络监听
		mNetChangeReceiver = new NetworkConnectivityListener();
		mNetChangeReceiver.startListening(this);
	}

	// 2.在app中保存网络监听的状态

	public NetworkConnectivityListener getNetworkListener() {
		return mNetChangeReceiver;
	}

	public int getNetType() {
		return netType;
	}

	public void setNetType(int netType) {
		this.netType = netType;
	}

	// public void onDestroy() {
	// // 3.退出程序的时候取消网络监听
	// mNetChangeReceiver.stopListening();
	// android.os.Process.killProcess(android.os.Process.myPid());
	// }

}
