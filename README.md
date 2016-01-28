# OpenFragment
OpenFragment (lib And Demo)

##这个lib目前是可用的，但是可能存在内存泄露的问题（等找到更好的办法以后我再进行修改）
##PS：一个activity中，相同tag的fragment最多只能添加一个。

使用方法：

1.继承BaseActivity
```
public class MainActivityBase extends BaseActivity {}
```

2.在继承BaseActivity的activity中定义界面模板（建议使用线性布局，其他布局可能出现无法预料的情况）(如下：R.layout.activity_main为模板布局)
```
	@Override
	protected int setMainLayout() {
		return R.layout.activity_main;
	}
```

3.编写继承BaseFragment的fragment，并设置标签（标签用来找到本fragment）：
```
public class MyFragment extends BaseFragment {
	@Override
	protected void SetTAG() {
	//设置标签
		SetLayoutTAG("Top");
	}
}
```

4.编写继承BaseFragment的fragment的引用布局：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >

    <fragment
        android:id="@+id/MyFagment"
        android:name="com.example.openfragmentdemo.fragment.MyFragment"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

</LinearLayout>
```

5.在模板布局中增加删除fragment（建议在initView（）或者onClick()方法中进行操作）:
  ```
  //增加：
		AddView(this.getClass().getName(), mMainFragTop, R.layout.main_frag);
		  
  //移除：
    RemoveView(mMainFragTop, "Top");
  ```

  
