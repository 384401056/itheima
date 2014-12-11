package com.blueice.mobilelottery.view;

import com.blueice.mobilelottery.ConstValue;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * 简单界面一。
 *
 */
public class FirstUI extends BaseUI {

	private TextView tv = null;
	
	public FirstUI(Context context) {
		super(context);
		init();
	}

	public View getChildView(){
		return tv;
	}

	private void init() {
		tv = new TextView(context);
		LayoutParams params = tv.getLayoutParams(); //获取TextView原来的Params
		params = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT); //修改Params。
		tv.setLayoutParams(params );  //设置Params.
		tv.setBackgroundColor(Color.GREEN);
		tv.setText("第一个简单界面。");
	}
	
	
	public int getID(){
		return ConstValue.VIEW_FIRST;
	}
}
