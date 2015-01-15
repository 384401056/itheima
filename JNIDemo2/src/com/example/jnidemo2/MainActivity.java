package com.example.jnidemo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	static{
		
		System.loadLibrary("Funcs");
		
	}
	
	DataProvider dp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dp = new DataProvider();
	}
	
	
	public void add(View view){
		Toast.makeText(MainActivity.this,dp.add(20, 30)+"", 1).show();//调用jni对两个数相加.
	}
	
	
	public void returnStr(View view){
		Toast.makeText(MainActivity.this,dp.returnStr("World"), 1).show();//调用jni对字符串"World"加上"Hello".
	}
	
	public void returnArray(View view){
		int[] array = {10,20,30};
		int[] resArray = dp.intMethod(array);//调用jni对数组中的每个值加上5.
		for(int i=0;i<resArray.length;i++){
			System.out.println("["+i+"]="+resArray[i]);
		}
	}
}










