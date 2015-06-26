package com.blueice.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.example.jnidemonew.R;

public class MainActivity extends Activity {
	
	public native String getURL(); //声明调用C语言中的方法接口。

	static {
		System.loadLibrary("CFunc");
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		Log.i("MyLog",getURL());
		
	}
}
