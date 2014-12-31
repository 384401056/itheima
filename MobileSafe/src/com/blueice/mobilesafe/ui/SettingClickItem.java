package com.blueice.mobilesafe.ui;

import com.blueice.mobilesafe.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingClickItem extends RelativeLayout{

	private TextView tv_title;
	private TextView tv_context;
	
	//控件的自定义属性。
	private String myTitle;

	public SettingClickItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	/**
	 * 使用XML生成VIEW时，调用的是两个参数的方法。
	 * @param context
	 * @param attrs
	 */
	public SettingClickItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		myTitle = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.blueice.mobilesafe", "myTitle");

		initView(context);
	}

	public SettingClickItem(Context context) {
		super(context);
		initView(context);
	}
	
	
	private void initView(Context context) {
		
		//第三个参数就是将布局文件变成View并放入SettingSwichItem中。(让this成为它的父类)
		View.inflate(context, R.layout.setting_clickitem, this);
		
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_context = (TextView) this.findViewById(R.id.tv_context);
		setTitle(myTitle);//设置标题文字.

	}
	
	
	/**
	 * //设置小标题文字.
	 * @param title 文字.
	 */
	public void setTitle(String title){
		tv_title.setText(title);
	}
	
	/**
	 * 设置内容文字
	 * @param context 文字
	 */
	public void setContext(String context){
		tv_context.setText(context);
	}

	
	public String getMyTitle() {
		return myTitle;
	}

	public void setMyTitle(String myTitle) {
		this.myTitle = myTitle;
	}
	
	

}
















