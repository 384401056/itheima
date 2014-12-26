package com.blueice.mobilesafe;

import org.apache.commons.lang3.time.FastDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.blueice.mobilesafe.service.AddressService;
import com.blueice.mobilesafe.ui.SettingSwichItem;

public class SettingActivity extends Activity {

	private SettingSwichItem item_update;
	
	private SettingSwichItem item_showAddress;
	private Intent shwoAddressService;
	
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
		
		item_showAddress = (SettingSwichItem) findViewById(R.id.item_showAddress);
		itemShowAddressInit();

	}

	/**
	 * 是否开启来电归属地显示
	 */
	private void itemShowAddressInit() {
		
		shwoAddressService = new Intent(this,AddressService.class); 

		if(GlobalParams.sp.getBoolean("showAddress", false)){
			item_showAddress.setChecked(true);
		}else{
			item_showAddress.setChecked(false);
		}
		
		item_showAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果已被选中。
				if(item_showAddress.isChecked()){
					item_showAddress.setChecked(false);
					editor.putBoolean("showAddress", false); //存储到SharedPreferences中。
					//关闭服务
					stopService(shwoAddressService);
					Log.i("MyLog", "shwoAddressService stop..");
					
				}else{
					item_showAddress.setChecked(true);
					editor.putBoolean("showAddress", true);  //存储到SharedPreferences中。
					//开启服务
					startService(shwoAddressService);
					Log.i("MyLog", "shwoAddressService start..");
				}
				
				editor.commit(); //必须commit，否则存储不生效。
			}
		});
		
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
