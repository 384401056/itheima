package com.blueice.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class Setup4Activity extends BaseSetupActivity {

	private CheckBox cb_proteting;
	
	private Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		
		editor = GlobalParams.sp.edit();
		init();
		
	}

	
	private void init() {
		cb_proteting = (CheckBox) findViewById(R.id.cb_proteting);
		
		
		boolean  protecting = GlobalParams.sp.getBoolean("protecting", false);
		if(protecting){
			//手机防盗已经开启了
			cb_proteting.setText("手机防盗已经开启");
			cb_proteting.setChecked(true);
		}else{
			//手机防盗没有开启
			cb_proteting.setText("手机防盗没有开启");
			cb_proteting.setChecked(false);
		}
		
		cb_proteting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked){
					cb_proteting.setText("手机防盗已经开启");
				}else{
					cb_proteting.setText("手机防盗没有开启");
				}
				
				//保存选择的状态
				editor.putBoolean("protecting", isChecked);
				editor.commit();
			}
		});
		
	}

	@Override
	public void showNext() {
		editor.putBoolean("configsetup", true);
		editor.commit();
		
		Intent intent = new Intent(this,SafeActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this,Setup3Activity.class);
		startActivity(intent);
		finish();
	}
}
