package com.blueice.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SafeActivity extends Activity {

	private static final String TAG = "MyLog";

	private TextView safe_number;
	private ImageView img_lock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safe);

		init();
		
	}
	
	/**
	 * 判断是否进行过设置向导。
	 */
	private void init() {
		
		safe_number = (TextView) findViewById(R.id.safe_number);
		img_lock = (ImageView) findViewById(R.id.img_lock);
		
		boolean configSteup = GlobalParams.sp.getBoolean("configsetup", false);
		
		if(!configSteup){
			startSetup();
		}else{
			safe_number.setText(GlobalParams.sp.getString("safenumber", null));
			if(GlobalParams.sp.getBoolean("protecting", false)){
				img_lock.setImageResource(R.drawable.lock);
			}
		}
		
	}


	/**
	 * 进入配置向导页面。并且finish SafeActivity
	 */
	private void startSetup() {
		Intent intent  = new Intent(SafeActivity.this,Setup1Activity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 重新进入配置向导按钮事件。
	 * @param view
	 */
	public void reEnterSetup(View view){
		startSetup();
	}
}
