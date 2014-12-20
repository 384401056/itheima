package com.blueice.mobilesafe.ui;

import com.blueice.mobilesafe.GlobalParams;
import com.blueice.mobilesafe.R;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingSwichItem extends RelativeLayout{

	private TextView tv_title;
	private TextView tv_context;
	private CheckBox cb_status;
	
	//控件的自定义属性。
	private String myTitle;
	private String desc_on;
	private String desc_off;
	
	
	
	public SettingSwichItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	/**
	 * 使用XML生成VIEW时，调用的是两个参数的方法。
	 * @param context
	 * @param attrs
	 */
	public SettingSwichItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		myTitle = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.blueice.mobilesafe", myTitle);
		desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.blueice.mobilesafe", desc_on);
		desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.blueice.mobilesafe", desc_off);

		initView(context);
	}

	public SettingSwichItem(Context context) {
		super(context);
		initView(context);
	}
	
	
	private void initView(Context context) {
		
		final Editor editor = GlobalParams.sp.edit();

		//第三个参数就是将布局文件变成View并放入SettingSwichItem中。(让this成为它的父类)
		View.inflate(context, R.layout.setting_swichitem, this);
		
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_context = (TextView) this.findViewById(R.id.tv_context);
		cb_status = (CheckBox) this.findViewById(R.id.cb_status);
		
		tv_title.setText(myTitle);
		
		if(GlobalParams.sp.getBoolean("update", false)){
			setChecked(true);
		}else{
			setChecked(false);
		}
		
		
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果cb_status已经被选中，
				if(isChecked()){
					setChecked(false);
					editor.putBoolean("update", false);
				}else{
					setChecked(true);
					editor.putBoolean("update", true);
				}
				
				editor.commit();
			}
		});
		
	}
	
	private boolean isChecked(){
		return cb_status.isChecked();
	}
	
	private void setChecked(boolean checked){
		
		if(checked){
			tv_context.setText(desc_on);
		}else{
			tv_context.setText(desc_off);
		}
		
		cb_status.setChecked(checked);
	}

}
















