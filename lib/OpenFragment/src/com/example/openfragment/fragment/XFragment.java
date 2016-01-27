package com.example.openfragment.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class XFragment extends Fragment {

	/**
	 * 用来注册静态Fragment到Avtivity管理器中<br>
	 * 重写时请调用SetLayoutTAG(String ParentLayoutViewTAG)<br>
	 * <br>
	 * 警告: 请勿在本方法中进行其他操作.
	 */
	protected abstract void SetTAG();

	/**
	 * 这里需要通过父view的Id去查找父布局中已注册的存在的其他view的id组，然后在组中注册上本fragment的id<br>
	 * 
	 * @ParentLayoutViewTAG 所在的父布局的TAG标签
	 */
	protected abstract void SetLayoutTAG(String ParentLayoutViewTAG);

	@Override
	public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	/**
	 * 这里是暴露在外的操作Fragment的view的方法，通过对获取到数据的取值，判断，来更新数据
	 * 
	 * @ParentLayoutViewTAG 所在的父布局的TAG标签
	 */
	public abstract void getData(Bundle mBundle);
}
