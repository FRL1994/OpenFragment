package com.example.openfragment.ManageBean;

public class FragmentRegisterBean {

	public String AcitvityName;
	public int FragmentID;

	public FragmentRegisterBean(String AcitvityName, int FragmentID) {
		this.FragmentID = FragmentID;
		this.AcitvityName = AcitvityName;
	}

	/**
	 * 根据Fragment的ID获取到相关的布局TAG<br>
	 * 
	 * @return String类型的：ParentLayoutTAG 或者: null
	 */
	public String getActivityName(int FragmentID) {
		if (this.FragmentID == FragmentID) {
			return this.AcitvityName;
		} else {
			return null;
		}
	}

	/**
	 * 根据Fragment的父控件TAG获取到该Fragment的ID
	 * 
	 * @return int类型的:FragmentID 或者 int类型的数字： -1;<br>
	 *         -1表示没有找到相关的id
	 */
	public int getFragmentID(String AcitvityName) {
		if (this.AcitvityName.equals(AcitvityName)) {
			return FragmentID;
		} else {
			return -1;
		}
	}
}
