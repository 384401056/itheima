package com.example.hellojni;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class HelloJni extends Activity {

	private TextView tv = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_jni);
        
        tv = (TextView)findViewById(R.id.tv01);
        
        tv.setText(stringFromJNI()+" "+add(20,30)+"");
        
    }

    //=============native方法================
    public native String stringFromJNI();
    public native int add(int x,int y);
    
    static{
    	System.loadLibrary("hello_jni");
    }
    //======================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello_jni, menu);
        return true;
    }
}
