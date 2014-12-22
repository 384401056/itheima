package com.blueice.mobilesafe;

import com.blueice.mobilesafe.receiver.DeviceAdmin;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class Setup4Activity extends BaseSetupActivity {

	private CheckBox cb_proteting;
	
	private Editor editor;
	
	private DevicePolicyManager dpm; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		
		editor = GlobalParams.sp.edit();
		
		//定义DevicePolicyManager对象。
		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		
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
					on_DeviceAdmin();
					
				}else{
					cb_proteting.setText("手机防盗没有开启");
					off_DevceAdmin();
					
				}
				
				//保存选择的状态
				editor.putBoolean("protecting", isChecked);
				editor.commit();
			}
		});
		
	}

	/**
	 * 关闭远程控制的权限
	 */
	protected void off_DevceAdmin() {
		ComponentName who = new ComponentName(this, DeviceAdmin.class);
		if(dpm.isAdminActive(who)){
			dpm.removeActiveAdmin(who);
		}
	}


	/**
	 * 开启远程控制的权限
	 */
	private void on_DeviceAdmin() {
		// 创建一个添设备管理员的意图。
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

		// 意图的参数，即要激活哪个。
		ComponentName who = new ComponentName(this,DeviceAdmin.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,who);

		//激活时出现的用户提示信息.
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				getResources().getString(R.string.sample_device_admin_description));

		startActivity(intent);
		
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
