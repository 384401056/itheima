package com.blueice.mobilesafe;

import android.app.Activity;
import android.view.View;

public abstract class BaseSetupActivity extends Activity {
	
	/**
	 * 下一页按钮事件
	 */
	public abstract void showNext();
	
	/**
	 * 上一页按钮事件
	 */
	public abstract void showPre();
	

	public void next(View view){
		showNext();
		
	}

	public void pre(View view){
		showPre();
		
	}
	
}
