package com.example.jnidemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	public native String helloworldFromC(); //声明调用C语言中的方法接口。
	static{
		System.loadLibrary("CFunc");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
	}
	
	
	public void onClick(View view){
		
		
		Toast.makeText(MainActivity.this, helloworldFromC(), Toast.LENGTH_LONG).show();
		
	}

}
