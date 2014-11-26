package com.example.appwidgetdemo;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;

public class GlobalParameter extends Application {

	private Activity mianActivity; 
	
	
	
	public Activity getMianActivity() {
		return mianActivity;
	}

	public void setMianActivity(Activity mianActivity) {
		this.mianActivity = mianActivity;
	}



	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}



	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}



	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}



	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	
}
