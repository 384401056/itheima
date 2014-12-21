package com.blueice.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.blueice.mobilesafe.ui.SettingSwichItem;

public class SettingActivity extends Activity {

	private SettingSwichItem item_update;
	private Editor editor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		editor = GlobalParams.sp.edit();
		init();
		
	}


	private void init() {
		
		item_update = (SettingSwichItem)findViewById(R.id.item_update);
		itemUpdateInit();

	}


	/**
	 * 是否开启更新栏的初始化。
	 */
	private void itemUpdateInit() {

		if (GlobalParams.sp.getBoolean("update", false)) {
			item_update.setChecked(true);
		} else {
			item_update.setChecked(false);
		}

		item_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// 如果cb_status已经被选中，
				if (item_update.isChecked()) {
					
					item_update.setChecked(false);
					editor.putBoolean("update", false); // 存储到SharedPreferences中。
					
				} else {
					
					item_update.setChecked(true);
					editor.putBoolean("update", true); // 存储到SharedPreferences中。
					
				}
				
				editor.commit(); //必须commit，否则存储不生效。
			}
		});
	}
	
}
