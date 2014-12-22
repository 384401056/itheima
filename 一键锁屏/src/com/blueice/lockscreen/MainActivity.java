package com.blueice.lockscreen;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DevicePolicyManager dpm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		 * 要实现一键锁屏
		 * 
		 * 1.定义DevicePolicyManager对象。
		 * 
		 * 2.创建一个继承自DeviceAdminReceiver的类。并注册广播接收者。
		 */

		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

	}

	/**
	 * 一键锁屏的点击事件。
	 * 
	 * @param view
	 */
	public void lockScreen(View view) {

		ComponentName who = new ComponentName(this, LockScreen.class);

		//如果已经获取设备管理员权限，则锁屏。
		if (dpm.isAdminActive(who)) {

			dpm.lockNow();// 锁屏。
			dpm.resetPassword("", 0); // 设置锁屏密码。

			//清除外置存储卡的信息。
			// dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);


			// dpm.wipeData(0);//恢复出厂设置。
		}else{
			Toast.makeText(this, "还没获取设备管理员权限", Toast.LENGTH_LONG).show();
			return;
		}

	}

	/**
	 * 用代码去开启设备管理员。
	 * 
	 * @param view
	 */
	public void deviceadmin(View view) {

		// 创建一个添设备管理员的意图。
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

		// 意图的参数，即要激活哪个。
		ComponentName who = new ComponentName(this,
				LockScreen.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,who);

		// 激活之前出现的用户提示信息
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				"开启一键锁屏的用户提示信息。");

		startActivity(intent);

	}
	
	/**
	 * 卸载应用。
	 * @param view
	 */
	public void uninstall(View view){
		//1.首先将有设备管理员权限的权限去除。
		ComponentName who = new ComponentName(this, LockScreen.class);
		if(dpm.isAdminActive(who)){
			dpm.removeActiveAdmin(who);
		}
		
		//卸载应用
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:"+getPackageName()));
		startActivity(intent);
	}
}








































