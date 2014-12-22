package com.blueice.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ToolsActivity extends Activity {

	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);
		
		this.context = ToolsActivity.this;
	}
	
	
	/**
	 * 号码归属地按钮事件。
	 * @param view
	 */
	public void numberQuery(View view){
		Intent intent = new Intent(context,NumberQueryActivity.class);
		startActivity(intent);
	}
}
