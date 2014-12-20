package com.blueice.mobilesafe;

import com.blueice.mobilesafe.ui.SettingSwichItem;
import android.app.Activity;
import android.os.Bundle;

public class SettingActivity extends Activity {

	private SettingSwichItem item_update;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
//		init();
		
	}


	private void init() {
		
		item_update = (SettingSwichItem) findViewById(R.id.item_update);
		
	}
	
}
