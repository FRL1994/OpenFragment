package com.example.openfragmentdemo.activitybase;

import com.example.openfragment.Activity.BaseActivity;
import com.example.openfragmentdemo.R;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.squareup.otto.Subscribe;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivityBase extends BaseActivity {

	private LinearLayout mMainFragTop;
	public Button mButton;

	@Override
	protected int setMainLayout() {
		return R.layout.activity_main;
	}

	@Override
	protected boolean setIsNeedNetwork() {
		return true;
	}

	@Override
	protected boolean setIsNeedHandler() {
		return false;
	}

	@Override
	protected void initNetEvent() {
		initNetworkEvent("http://www.baidu.com", 1000);
	}

	@Subscribe
	@Override
	public void onConnectivityChanged(ConnectivityChanged event) {
		switch (event.getConnectivityStatus()) {
		case WIFI_CONNECTED:
			Log.e("ConnectivityChanged", "wifi在线(不确定有没有外网)");
			break;
		case WIFI_CONNECTED_HAS_NO_INTERNET:
			Log.e("ConnectivityChanged", "wifi在线(没有外网)");
			break;
		case WIFI_CONNECTED_HAS_INTERNET:
			Log.e("ConnectivityChanged", "wifi在线(可以访问到外部网络)");
			break;
		case MOBILE_CONNECTED:
			Log.e("ConnectivityChanged", "流量在线");
			break;
		case OFFLINE:
			Log.e("ConnectivityChanged", "离线");
			break;
		case UNKNOWN:
			Log.e("ConnectivityChanged", "网络状态未知");
			break;
		}
	}

	@Override
	protected void findView() {
		mMainFragTop = (LinearLayout) findViewById(R.id.main_frag_top);
		mButton = (Button) findViewById(R.id.add_fragment);
	}

	@Override
	protected void initView() {
		AddView(this.getClass().getName(), mMainFragTop, R.layout.main_frag);
		setBackgroundColor(mMainFragTop, Color.BLUE);
	}

	@Override
	protected void onClick() {
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RemoveView(mMainFragTop, "Top");
				AddView(this.getClass().getName(), mMainFragTop, R.layout.main_frag);
				mButton.setEnabled(false);
			}
		});
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void GetHandlerMessage() {

	}

}
