package com.blueice.wlwsd;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class LaunchView extends Activity {


	private Typeface typeface;
	
	Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch_view);
		
	    //字体MgOpenCosmeticaBold.ttf放置于assets/font/路径下
		Typeface typeface=Typeface.createFromAsset(getAssets(),"font/wqymh.TTF");
		
		
		 //获取程序版本号。
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo("com.blueice.yqzzds", 0);
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            versionNumber.setTypeface(typeface);
            versionNumber.setText("Version " + pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //图片延迟.
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(LaunchView.this,LoginActivity.class);
                startActivity(intent);
                LaunchView.this.finish();
            }
        },3000);
		
	}

}
