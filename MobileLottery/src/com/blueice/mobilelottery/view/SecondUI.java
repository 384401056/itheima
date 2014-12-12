package com.blueice.mobilelottery.view;

import com.blueice.mobilelottery.ConstValue;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * 简单界面二。
 *
 */
public class SecondUI extends BaseUI {

	private TextView tv = null;

	public SecondUI(Context context) {
		super(context);
	}

	
	@Override
	public void init() {
		tv = new TextView(context);
		LayoutParams params = tv.getLayoutParams(); // 获取TextView原来的Params
		params = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT); // 修改Params。
		tv.setLayoutParams(params); // 设置Params.
		tv.setBackgroundColor(Color.MAGENTA);
		tv.setText("第二个简单界面。");
	}

	public int getID() {
		return ConstValue.VIEW_SECOND;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOnClickListener() {
		// TODO Auto-generated method stub
		
	}
}
