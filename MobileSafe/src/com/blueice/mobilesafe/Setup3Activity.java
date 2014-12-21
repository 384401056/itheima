package com.blueice.mobilesafe;

import org.apache.commons.lang3.StringUtils;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blueice.mobilesafe.utils.PromptManager;


public class Setup3Activity extends BaseSetupActivity {

	
	private EditText setup3_phone;
	private Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		
		editor = GlobalParams.sp.edit();
		init();
	}

	private void init() {
		
		setup3_phone = (EditText) findViewById(R.id.et_setup3_phone);
		setup3_phone.setText(GlobalParams.sp.getString("safenumber", null));

	}

	@Override
	public void showNext() {
		String phone = setup3_phone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			PromptManager.showToast(this, "安全号码还没有设置");
			return;
		}else{
			//保存安全号码
			editor.putString("safenumber", phone);
			editor.commit();
			
			Intent intent = new Intent(this,Setup4Activity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this,Setup2Activity.class);
		startActivity(intent);
		finish();
	}
	
	
	/**
	 * 选择联系人的点击事件
	 * @param view
	 */
	public void selectContact(View view){
		Intent intent = new Intent(this,SelectContactActivity.class);
		startActivityForResult(intent, 0);
	}
	
	/**
	 * 接收返回值的事件。
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(data==null){
			return;
		}else{
			
			String phoneString = data.getStringExtra("phone");
			
			//替换号码中的空格和“-”
			phoneString = StringUtils.replaceEach(phoneString, new String[]{" ","-"}, new String[]{"",""});

			setup3_phone.setText(phoneString);
		}
		
	}
	
}















