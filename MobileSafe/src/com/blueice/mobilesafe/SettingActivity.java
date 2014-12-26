package com.blueice.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.blueice.mobilesafe.service.AddressService;
import com.blueice.mobilesafe.ui.SettingSwichItem;
import com.blueice.mobilesafe.utils.ServiceUtils;

public class SettingActivity extends Activity {

	private Context context;
	private SettingSwichItem item_update;
	private SettingSwichItem item_showAddress;
	private Intent shwoAddressService;
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

	}

	/**
	 * 由于服务可以在应用管理中手动关闭，所以要在此方法中再判断一次。
	 */
	@Override
	protected void onResume() {
		super.onResume();

		// 判断服务是否在运行
		boolean isRunning = ServiceUtils.isServiceRunning(context,"com.blueice.mobilesafe.service.AddressService");
		if (isRunning) {
			item_showAddress.setChecked(true); // 如果运行中为选中状态。
		} else {
			item_showAddress.setChecked(false);
		}
	}

	/**
	 * 是否开启来电归属地显示
	 */
	private void itemShowAddressInit() {

		shwoAddressService = new Intent(this, AddressService.class);

		// 判断服务是否在运行
		boolean isRunning = ServiceUtils.isServiceRunning(context,
				"com.blueice.mobilesafe.service.AddressService");
		if (isRunning) {
			item_showAddress.setChecked(true); // 如果运行中为选中状态。
		} else {
			item_showAddress.setChecked(false);
		}

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

				editor.commit(); // 必须commit，否则存储不生效。
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

				editor.commit(); // 必须commit，否则存储不生效。
			}
		});
	}

}
