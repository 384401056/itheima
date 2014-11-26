package com.example.appwidgetdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;


/**
 * 通过使用后台服务来向AppWidget发送广播。
 *
 */
public class MainActivity extends Activity {

//	private Button btn01;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		btn01 = (Button)findViewById(R.id.button1);
//		btn01.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});
		
//		
//		GlobalParameter global = (GlobalParameter)getApplicationContext();
//		global.setMianActivity(this);
	}
	
	
	public void btnClick(View v){
		Toast.makeText(this, "Button Click", Toast.LENGTH_LONG).show();
	}

	
}
