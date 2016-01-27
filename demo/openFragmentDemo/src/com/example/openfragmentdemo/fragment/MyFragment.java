package com.example.openfragmentdemo.fragment;

import com.example.openfragment.fragment.BaseFragment;
import com.example.openfragmentdemo.R;
import com.example.openfragmentdemo.activitybase.A;
import com.example.openfragmentdemo.activitybase.MainActivityBase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyFragment extends BaseFragment {

	private Button show_fragment2;
	private MainActivityBase mMainActivity;
	private LinearLayout mMainFragTop;

	@Override
	protected void SetTAG() {
		SetLayoutTAG("Top");
	}

	@Override
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMainActivity = (MainActivityBase) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_main, container, false);

		mMainFragTop = (LinearLayout) mMainActivity.findViewById(R.id.main_frag_top);

		show_fragment2 = (Button) view.findViewById(R.id.show_fragment2);

		show_fragment2.setOnClickListener(new OnClickListener() {

			@Override
			@SuppressLint("NewApi")
			public void onClick(View v) {
				mMainActivity.RemoveView(mMainFragTop, "Top");
				mMainActivity.mButton.setEnabled(true);
			}
		});
		return view;
	}

}
