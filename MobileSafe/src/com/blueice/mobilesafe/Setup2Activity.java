package com.blueice.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.blueice.mobilesafe.ui.SettingSwichItem;
import com.blueice.mobilesafe.utils.PromptManager;

public class Setup2Activity extends BaseSetupActivity {

	private TelephonyManager tm;
	private SettingSwichItem setup2_sim;
	private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		editor = GlobalParams.sp.edit();
		init();
		
	}

	private void init() {
		
		setup2_sim = (SettingSwichItem) findViewById(R.id.siv_setup2_sim);
		
		//如果没有绑定下SIM卡。
		if(TextUtils.isEmpty(GlobalParams.sp.getString("sim", null))){
			setup2_sim.setChecked(false);
		}else{
			setup2_sim.setChecked(true);
		}
		
		setup2_sim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(setup2_sim.isChecked()){
					setup2_sim.setChecked(false);
					
					editor.putString("sim", null);
					
				}else{
					setup2_sim.setChecked(true);
					
					String sim = tm.getSimSerialNumber();//获取当前SIM卡的序列号。
					editor.putString("sim", sim);
				}
				editor.commit();
			}
		});
		
		
		
		
	}

	@Override
	public void showNext() {
		// 取出是否绑定sim
		String sim = GlobalParams.sp.getString("sim", null);
		
		if (TextUtils.isEmpty(sim)) {
			// 没有绑定
			PromptManager.showToast(this, "sim卡没有绑定");
			return;
		}

		Intent intent = new Intent(this, Setup3Activity.class);
		startActivity(intent);
		finish();

	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this,Setup1Activity.class);
		startActivity(intent);
		finish();
		
	}
}
