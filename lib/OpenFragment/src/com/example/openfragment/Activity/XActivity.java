package com.example.openfragment.Activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.openfragment.ManageBean.FragmentRegisterBean;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

public abstract class XActivity extends Activity {

	public Map<String, List<FragmentRegisterBean>> Register = new HashMap<String, List<FragmentRegisterBean>>();
	public Map<String, String> check = new HashMap<String, String>();

	public Bundle mBundle = new Bundle();

	/**
	 * 设置包含openfragment的父布局(即模板布局)<br>
	 * 
	 * @return int类型的：布局Id(如：R.layout.main)
	 */
	protected abstract int setMainLayout();

	/**
	 * 设置本Activity是否需要监听网络变化<br>
	 * 注意：使用网络时请注意检查清单文件中的网络访问访问权限，如果权限错误，将导致应用异常.
	 * 
	 * @return boolean类型的：mIsNeedNetwork
	 */
	protected abstract boolean setIsNeedNetwork();

	/**
	 * 设置本Activity是否需要使用handler<br>
	 * 
	 * @return boolean类型的：IsNeedHandler
	 */
	protected abstract boolean setIsNeedHandler();

	/**
	 * 用来初始化网络变化监听器的方法<br>
	 * 重写时请调用initNetworkEvent(String url,int timeout)方法
	 */
	protected abstract void initNetEvent();

	/**
	 * 用来初始化网络变化监听器的方法<br>
	 * 
	 * @url 用来判断是否存在外网的访问链接，能正常访问则表示存在外网，否则表示不存在外网
	 * @timeout 超时时间
	 */
	protected abstract void initNetworkEvent(String url, int timeout);

	/**
	 * 重写此方法时请注意：加上@Subscribe 标签<br>
	 * 网络变化时就会调用的方法<br>
	 * 返回(以下的状态之一(根据实际环境返回)):<br>
	 * UNKNOWN("unknown")<br>
	 * WIFI_CONNECTED("connected to WiFi")<br>
	 * WIFI_CONNECTED_HAS_INTERNET( "connected to WiFi (Internet available)")
	 * <br>
	 * WIFI_CONNECTED_HAS_NO_INTERNET(
	 * "connected to WiFi (Internet not available)")<br>
	 * MOBILE_CONNECTED("connected to mobile network")<br>
	 * OFFLINE("offline")
	 * 
	 */
	public abstract void onConnectivityChanged(ConnectivityChanged event);

	/**
	 * 用来存放findViewByID的地方<br>
	 * 
	 * 警告： 后期可能会将此处修改为反射获取ID
	 */
	protected abstract void findView();

	/**
	 * 用来初始化view,提供AddView,SetViewGone,SetViewInVisibility等方法<br>
	 * 
	 */
	protected abstract void initView();

	/**
	 * 用来给View添加监听事件<br>
	 * 
	 */
	protected abstract void onClick();

	/**
	 * 对数据进行操作，当mIsNeedHandler为真时，使用handler的消息传递进行对view的更新<br>
	 * 
	 */
	protected abstract void initData();

	/**
	 * 用来向本类中的mhHandler发送消息,建议在initData方法中使用<br>
	 * 警告：本方法只有在mIsNeedHandler为真的时候才可以使用.
	 */
	protected abstract void setHandlerMessage(int MessageWhat, Bundle mbBundle);;

	/**
	 * 子线程的交互信息接收，用来更新界面<br>
	 * 注意：本方法只有在mIsNeedHandler()方法返回为真的时候才会调用.
	 */
	protected abstract void GetHandlerMessage();

	/**
	 * 用来跳转到指定的activity,可带数据和不带数据<br>
	 * 警告：本方法只有在mIsNeedHandler为真的时候才可以使用.(未确认)
	 */
	public abstract void setDataToActivity(Class<?> ActivityClass, Bundle mbBundle);

	/**
	 * 用来从fragment传递数据到父Activity中<br>
	 * 警告：本方法只有在mIsNeedHandler为真的时候才可以使用.(未确认)
	 */
	public abstract void setDataToParentActivity(Bundle mbBundle);

	/**
	 * 用来向Fragment发送更新界面消息,建议在GetHandlerMessage方法中使用<br>
	 * 警告：本方法只有在mIsNeedHandler为真的时候才可以使用.(未确认)
	 */
	public abstract void setDataToFragment(int FragmentID, Bundle mbBundle);

	/**
	 * 提供将View嵌入到模板布局中的方法<br>
	 * PS:请注意：目前无法判断fragment是否加载，所以请不要在同一个界面同时添加两个相同的fragment
	 * 
	 * @ActivityName 当前Activity的名字
	 * @ParentView 父控件view
	 * @ChildViewLayoutID 要嵌入的布局ID
	 */
	public abstract void AddView(String ActivityName, LinearLayout ParentView, int ChildViewLayoutID);

	/**
	 * 将父控件置于GONE状态<br>
	 * 提供隐蔽fragment的功能，fragment被隐蔽的时候 ，将会运行fragment的onPause方法<br>
	 * 
	 * @ParentView 父控件view
	 * @ChildViewLayoutID 要嵌入的布局ID
	 */
	public abstract void SetViewGone(LinearLayout ParentView, String ParentLayoutViewTAG);

	/**
	 * 将父控件置于InVisibility状态<br>
	 * 提供隐蔽fragment的功能，fragment被隐蔽的时候 ，将会运行fragment的onPause方法<br>
	 * 
	 * @ParentView 父控件view
	 * @ChildViewLayoutID 要嵌入的布局ID
	 */
	public abstract void SetViewInVisibility(LinearLayout ParentView, String ParentLayoutViewTAG);

	/**
	 * 将父控件置于Visibility状态<br>
	 * 提供显示已隐蔽fragment的功能，fragment被显示的时候 ，将会运行fragment的onResume方法<br>
	 * 
	 * @ParentView 父控件view
	 * @ChildViewLayoutID 要嵌入的布局ID
	 */
	public abstract void SetViewVisibility(LinearLayout ParentView, String ParentLayoutViewTAG);

	/**
	 * 提供将fragment从模板布局中的移除方法<br>
	 * 
	 * @ChildViewLayoutID 要嵌入的布局ID
	 */
	public abstract void RemoveView(LinearLayout ParentView, String ParentLayoutViewTAG);

	/**
	 * 通过Activity的线性布局Id,找到返回Activity的线性布局View;
	 * 
	 * @ParentId Activity的线性布局Id
	 */
	public abstract LinearLayout getView(int ParentId);

	/**
	 * 设置线性布局高度,高度默认为布局中的属性,宽度默认为充满内容
	 * 
	 * @ParentView 父控件view
	 * @height 要设置的高度（单位：Px）(后期可能改为dp)
	 */
	public abstract void setHeight(LinearLayout ParentView, int height);

	/**
	 * 设置线性布局宽度,宽度默认为布局中的属性,高度默认为充满内容
	 * 
	 * @ParentView 父控件view
	 * @width 要设置的宽度（单位：Px）(后期可能改为dp)
	 */
	public abstract void SetWidth(LinearLayout ParentView, int width);

	/**
	 * 设置线性布局宽高,默认值为布局中的属性
	 * 
	 * @ParentView 父控件view
	 * @height 要设置的高度（单位：Px）(后期可能改为dp)
	 * @width 要设置的宽度（单位：Px）(后期可能改为dp)
	 * 
	 *        * @建议 ParentView 可以通过activity 去getview，从而获取到需要的view
	 */
	public abstract void SetHeightAndWidth(LinearLayout ParentView, int height, int width);

	/**
	 * 设置线性布局的背景
	 * 
	 * @ParentView 父控件view
	 * @background 要设置的背景图片
	 * 
	 * @建议 ParentView 可以通过activity 去getview，从而获取到需要的view
	 */
	public abstract void setBackground(LinearLayout ParentView, Drawable background);

	/**
	 * 设置线性布局的背景颜色
	 * 
	 * @ParentView 父控件view
	 * @color 要设置的颜色
	 */
	public abstract void setBackgroundColor(LinearLayout ParentView, int color);
}
