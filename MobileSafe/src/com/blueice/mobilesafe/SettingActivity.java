package com.blueice.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.blueice.mobilesafe.service.AddressService;
import com.blueice.mobilesafe.service.BlackListService;
import com.blueice.mobilesafe.ui.SettingClickItem;
import com.blueice.mobilesafe.ui.SettingSwichItem;
import com.blueice.mobilesafe.utils.ServiceUtils;

public class SettingActivity extends Activity {

	private Context context;
	
	private SettingSwichItem item_update;
	private SettingSwichItem item_showAddress;
	private SettingClickItem item_toastStyle;
	private SettingSwichItem item_blacklist;
	
	/*
	 * 服务的Intent
	 */
	private Intent shwoAddressService;
	private Intent blackListService;
	
	private Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		this.context = SettingActivity.this;
		editor = GlobalParams.sp.edit();
		init();

	}

	private void init() {

		item_update = (SettingSwichItem) findViewById(R.id.item_update);
		itemUpdateInit();

		item_showAddress = (SettingSwichItem) findViewById(R.id.item_showAddress);
		itemShowAddressInit();
		
		item_toastStyle = (SettingClickItem) findViewById(R.id.item_toastStyle);
		item_toastStyleInit();
		
		item_blacklist = (SettingSwichItem) findViewById(R.id.item_blacklist);
		item_blacklistInit();
	}

	


	/**
	 * 由于服务可以在应用管理中手动关闭，所以要在此方法中再判断一次。
	 */
	@Override
	protected void onResume() {
		super.onResume();

		// 判断归属地服务是否在运行
		boolean isAddressServiceRunning = ServiceUtils.isServiceRunning(context,"com.blueice.mobilesafe.service.AddressService");
		item_showAddress.setChecked(isAddressServiceRunning);// 如果运行中为选中状态。
		
		
		//判断黑名单拦截是否在运行
		boolean isBlackListRunning = ServiceUtils.isServiceRunning(context,"com.blueice.mobilesafe.service.BlackListService");
		item_blacklist.setChecked(isBlackListRunning); // 如果运行中为选中状态。

	}
	
	
	/**
	 * 开启和关闭黑名单拦截。
	 */
	private void item_blacklistInit() {
		
		blackListService = new Intent(this, BlackListService.class);

		//判断黑名单拦截是否在运行
		boolean isBlackListRunning = ServiceUtils.isServiceRunning(context,"com.blueice.mobilesafe.service.BlackListService");
		item_blacklist.setChecked(isBlackListRunning); // 如果运行中为选中状态。
		
		
		item_blacklist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 如果已被选中。
				if (item_blacklist.isChecked()) {
					item_blacklist.setChecked(false);
					// 关闭服务
					stopService(blackListService);
				} else {
					item_blacklist.setChecked(true);
					// 开启服务
					startService(blackListService);
				}
			}
		});
		
		
	}
	
	
	/**
	 * 设置归属地显示的风格。
	 */
	private void item_toastStyleInit() {
		
		//设置控件的内容文字。
		final int which = GlobalParams.sp.getInt("toastStyle", 0);
		item_toastStyle.setContext(ConstValue.toastStyle[which]);

		//设置控件的点击事件。
		item_toastStyle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new Builder(context);
				
				builder.setTitle(item_toastStyle.getMyTitle());
				
				//第三个参数，要使用DialogInterface.OnClickListener()全名，否则找不到这个接口,因为和外层点击事件重名了。
				builder.setSingleChoiceItems(ConstValue.toastStyle , which, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//保存选取的参数
						Editor edit = GlobalParams.sp.edit();
						edit.putInt("toastStyle", which);
						edit.commit();
						
						//设置内容文字信息
						item_toastStyle.setContext(ConstValue.toastStyle[which]);
						
						dialog.dismiss();
					}
				});
				
				builder.setNegativeButton("取消",null);
				
				builder.show();
			}
		});
		
	}
	

	/**
	 * 开启和关闭来电归属地显示
	 */
	private void itemShowAddressInit() {

		shwoAddressService = new Intent(this, AddressService.class);

		// 判断归属地服务是否在运行
		boolean isAddressServiceRunning = ServiceUtils.isServiceRunning(context, "com.blueice.mobilesafe.service.AddressService");
		item_showAddress.setChecked(isAddressServiceRunning);// 如果运行中为选中状态。

		item_showAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 如果已被选中。
				if (item_showAddress.isChecked()) {
					item_showAddress.setChecked(false);
					// 关闭服务
					stopService(shwoAddressService);

				} else {
					item_showAddress.setChecked(true);
					// 开启服务
					startService(shwoAddressService);

				}
			}
		});

	}

	/**
	 * 是否开启自动更新。
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

				editor.commit(); // 必须commit，否则存储不生效。
			}
		});
	}

}
