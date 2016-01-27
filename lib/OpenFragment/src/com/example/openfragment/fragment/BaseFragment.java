package com.example.openfragment.fragment;

import java.util.ArrayList;
import java.util.List;
import com.example.openfragment.Activity.BaseActivity;
import com.example.openfragment.ManageBean.FragmentRegisterBean;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public abstract class BaseFragment extends XFragment {

	public Context mContext;
	public int mFragmentID = 0;

	private BaseActivity mBaseActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBaseActivity = (BaseActivity) getActivity();
		mContext = this.getActivity();
		mFragmentID = getId();

		SetTAG();

	}

	@Override
	protected void SetLayoutTAG(String ParentLayoutViewTAG) {

		if (ParentLayoutViewTAG == null || mFragmentID <= 0) {
			Log.e("com.example.openfragment.fragment.XFragment", "ParentLayoutViewTAG不能为空或者未设ID");
			return;
		}

		if (mBaseActivity.Register.isEmpty() || mBaseActivity.Register == null) {
			FragmentRegisterBean mfragmentRegisterBean = new FragmentRegisterBean(ParentLayoutViewTAG, mFragmentID);
			List<FragmentRegisterBean> mRegisterList = new ArrayList<FragmentRegisterBean>();
			mRegisterList.add(mfragmentRegisterBean);
			mBaseActivity.Register.put(ParentLayoutViewTAG, mRegisterList);
		} else {
			List<FragmentRegisterBean> mRegisterList = mBaseActivity.Register.get(ParentLayoutViewTAG);
			FragmentRegisterBean mfragmentRegisterBean = new FragmentRegisterBean(ParentLayoutViewTAG, mFragmentID);
			mRegisterList.add(mfragmentRegisterBean);
			mBaseActivity.Register.put(ParentLayoutViewTAG, mRegisterList);
		}

	}

	@Override
	public void getData(Bundle mbBundle) {
		// 这里还没写好，请注意
		//此处施工
	}

}
