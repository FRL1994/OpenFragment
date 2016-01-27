package com.example.openfragment.Activity;

import java.util.List;
import com.example.openfragment.ManageBean.FragmentRegisterBean;
import com.example.openfragment.fragment.BaseFragment;
import com.github.pwittchen.networkevents.library.NetworkEvents;
import com.squareup.otto.Bus;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * 建议将数据获取的类写成抽象类，然后界面处理的类设置为继承数据获取的抽象类，这样就可以将数据和界面完全分离。<br>
 * 通过DataActivity中的setHandlerMessage发送给handler，
 * 在viewActivity中GetHandlerMessage到消息，然后，进行view操作
 */
@SuppressLint("NewApi")
public abstract class BaseActivity extends XActivity {

	private Bus mBus;
	private NetworkEvents mNetworkEvents;
	private boolean mIsNeedNetwork = false;

	private boolean mIsNeedHandler = false;
	private Handler mhHandler;

	public FragmentManager manager;
	public FragmentTransaction transaction;

	public Context mContext;

	private LinearLayout returnLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(setMainLayout());

		mContext = this;

		manager = getFragmentManager();

		mIsNeedNetwork = setIsNeedNetwork();
		mIsNeedHandler = setIsNeedHandler();
		initNetEvent();

		if (mIsNeedHandler) {
			mhHandler = new Handler() {
				public void handleMessage(android.os.Message msg) {
					GetHandlerMessage();
				};
			};
		} else {
			Log.e("Handler", "本activity中未使用handler进行线程间的交互");
		}

		findView();

		initView();
		onClick();
		initData();

	}

	@Override
	protected void initNetworkEvent(String url, int timeout) {
		if (mIsNeedNetwork) {
			mBus = new Bus();
			mNetworkEvents = new NetworkEvents(mContext, mBus).withPingUrl(url).withPingTimeout(timeout)
					.withoutWifiAccessPointsScan();
		} else {
			Log.e("NetWork", "本activity中未使用网络");
		}
	}

	@Override
	protected void setHandlerMessage(int MessageWhat, Bundle mBundle) {
		Message message = new Message();
		message.what = MessageWhat;
		message.setData(mBundle);
		mhHandler.sendMessage(message);
	};

	@Override
	public void setDataToFragment(int FragmentID, Bundle mbBundle) {
		BaseFragment mFragment = (BaseFragment) manager.findFragmentById(FragmentID);
		mFragment.getData(mbBundle);
	};

	@Override
	public void setDataToActivity(Class<?> ActivityClass, Bundle mbBundle) {
		// 调用此方法能从fragment跳转到其他的actvity
		// 此处施工
	}

	public void setDataToParentActivity(Bundle mbBundle) {
		// 调用此方法能从fragment传递数据到本activity
		// 此处施工
	}

	@Override
	public void AddView(String AcitvityName, LinearLayout ParentView, int ChildViewID) {

		if (ParentView == null || ChildViewID <= 0) {
			Log.e("OpenfragmentLib", "AddView方法传入参数错误");
			return;
		}

		int ParentViewId = ParentView.getId();

		String getActivity = check.get(ParentViewId + "");
		if (getActivity == null) {
			check.put(ParentViewId + "", AcitvityName);
			View view = LayoutInflater.from(mContext).inflate(ChildViewID, null);
			view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			ParentView.addView(view);
			ParentView.setVisibility(View.VISIBLE);
		} else {
			Toast.makeText(mContext, "已存在此fragment，请删除此Fragment后重新加载", Toast.LENGTH_SHORT).show();
			Log.e("OpenfragmentLib", "已存在此fragment，请删除此Fragment后重新加载");
		}
	}

	@Override
	public void SetViewGone(LinearLayout ParentView, String ParentLayoutViewTAG) {
		boolean IsHaveRegisterError = false;

		if (ParentView == null || ParentLayoutViewTAG == null || ParentLayoutViewTAG.trim().equals("")) {
			Log.e("OpenfragmentLib", "RemoveView方法传入参数错误");
			return;
		}

		ParentView.setVisibility(View.GONE);

		List<FragmentRegisterBean> mRegisterList = Register.get(ParentLayoutViewTAG);

		for (int i = 0; i < mRegisterList.size(); i++) {
			FragmentRegisterBean mfragmentRegisterBean = mRegisterList.get(i);

			if (mfragmentRegisterBean != null) {
				int mFragmentID = mfragmentRegisterBean.getFragmentID(ParentLayoutViewTAG);
				if (mFragmentID != -1) {
					BaseFragment mFragment = (BaseFragment) manager.findFragmentById(mFragmentID);
					mFragment.onPause();

					IsHaveRegisterError = true;
				}
			}

			if (!IsHaveRegisterError) {
				Log.e("OpenfragmentLib", "请检查注册的fragment的信息，注册的信息可能存在错误");
			}
		}
	}

	@Override
	public void SetViewInVisibility(LinearLayout ParentView, String ParentLayoutViewTAG) {
		boolean IsHaveRegisterError = false;

		if (ParentView == null || ParentLayoutViewTAG == null || ParentLayoutViewTAG.trim().equals("")) {
			Log.e("OpenfragmentLib", "RemoveView方法传入参数错误");
			return;
		}

		ParentView.setVisibility(View.INVISIBLE);

		List<FragmentRegisterBean> mRegisterList = Register.get(ParentLayoutViewTAG);

		for (int i = 0; i < mRegisterList.size(); i++) {
			FragmentRegisterBean mfragmentRegisterBean = mRegisterList.get(i);

			if (mRegisterList != null) {
				int mFragmentID = mfragmentRegisterBean.getFragmentID(ParentLayoutViewTAG);
				if (mFragmentID != -1) {
					BaseFragment mFragment = (BaseFragment) manager.findFragmentById(mFragmentID);
					mFragment.onPause();

					IsHaveRegisterError = true;
				}
			}

			if (!IsHaveRegisterError) {
				Log.e("OpenfragmentLib", "请检查注册的fragment的信息，注册的信息可能存在错误");
			}

		}
	}

	@Override
	public void SetViewVisibility(LinearLayout ParentView, String ParentLayoutViewTAG) {
		boolean IsHaveRegisterError = false;

		if (ParentView == null || ParentLayoutViewTAG == null || ParentLayoutViewTAG.trim().equals("")) {
			Log.e("OpenfragmentLib", "RemoveView方法传入参数错误");
			return;
		}

		ParentView.setVisibility(View.VISIBLE);

		List<FragmentRegisterBean> mRegisterList = Register.get(ParentLayoutViewTAG);

		for (int i = 0; i < mRegisterList.size(); i++) {
			FragmentRegisterBean mfragmentRegisterBean = mRegisterList.get(i);

			if (mRegisterList != null) {

				int mFragmentID = mfragmentRegisterBean.getFragmentID(ParentLayoutViewTAG);
				if (mFragmentID != -1) {
					BaseFragment mFragment = (BaseFragment) manager.findFragmentById(mFragmentID);
					mFragment.onResume();

					IsHaveRegisterError = true;
				}
			}

			if (!IsHaveRegisterError) {
				Log.e("OpenfragmentLib", "请检查注册的fragment的信息，注册的信息可能存在错误");
			}
		}
	}

	@Override
	public void RemoveView(LinearLayout ParentView, String ParentLayoutViewTAG) {
		boolean IsHaveRegisterError = false;

		if (ParentView == null || ParentLayoutViewTAG == null || ParentLayoutViewTAG.trim().equals("")) {
			Log.e("OpenfragmentLib", "RemoveView方法传入参数错误");
			return;
		}

		ParentView.removeAllViews();
		List<FragmentRegisterBean> mRegisterList = Register.get(ParentLayoutViewTAG);

		if (mRegisterList != null) {

			for (int i = 0; i < mRegisterList.size(); i++) {
				FragmentRegisterBean mfragmentRegisterBean = mRegisterList.get(i);
				int mFragmentID = mfragmentRegisterBean.getFragmentID(ParentLayoutViewTAG);
				if (mFragmentID != -1) {
					BaseFragment mFragment = (BaseFragment) manager.findFragmentById(mFragmentID);

					transaction = manager.beginTransaction();
					transaction.remove(mFragment);
					
					int ParentViewid = ParentView.getId();
					check.remove(ParentViewid+"");

					IsHaveRegisterError = true;
				}
			}

			if (!IsHaveRegisterError) {
				Log.e("OpenfragmentLib", "请检查注册的fragment的信息，注册的信息可能存在错误");
			}

			transaction.commit();
			transaction = null;
		}

		Register.remove(ParentLayoutViewTAG);
	}

	@Override
	public LinearLayout getView(int ParentId) {
		returnLinearLayout = (LinearLayout) findViewById(ParentId);
		return returnLinearLayout;
	}

	@Override
	public void setHeight(LinearLayout ParentView, int height) {
		ParentView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height));
	}

	@Override
	public void SetWidth(LinearLayout ParentView, int width) {
		ParentView.setLayoutParams(new LinearLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void SetHeightAndWidth(LinearLayout ParentView, int height, int width) {
		ParentView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
	}

	@Override
	public void setBackground(LinearLayout ParentView, Drawable background) {
		ParentView.setBackground(background);
	}

	@Override
	public void setBackgroundColor(LinearLayout ParentView, int color) {
		ParentView.setBackgroundColor(color);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mIsNeedNetwork) {
			mBus.register(mContext);
			mNetworkEvents.register();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mIsNeedNetwork) {
			mBus.unregister(mContext);
			mNetworkEvents.unregister();
		}
	}

	@Override
	protected void onStop() {
		// 需要在stop或者onDestroy时清空在actvity的fragment
		// 此处施工
		super.onStop();
	}

}
