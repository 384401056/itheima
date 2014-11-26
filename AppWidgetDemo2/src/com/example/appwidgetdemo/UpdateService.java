package com.example.appwidgetdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


public class UpdateService extends Service {

	private Thread thread;
	private Boolean flag = true;
	
	private static final String MY_ACTION_UPDATE = "my.action.APPWIDGET_UPDATE";
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("MyLog","Service---->onCreate");
	}




	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.i("MyLog","Service---->onStartCommand");
		return super.onStartCommand(intent, flags, startId);
		
	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		this.flag = false;
		Log.i("MyLog","Service---->onDestroy");
	}


	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.i("MyLog","Service---->onStart");
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				
				while(flag){
				
					Random random = new Random();
					int i = random.nextInt(1000);				
					String str = i+"";
					
					Intent intent = new Intent();
					intent.putExtra("str",str);
					intent.setAction(MY_ACTION_UPDATE);
					sendBroadcast(intent);
					
					Log.i("MyLog",Thread.currentThread().getId()+" Runnable===========>sendBroadcast");
					
					try{
						Thread.sleep(3000);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}


	//定义内容类继承Binder
    public class LocalBinder extends Binder{
        //返回本地服务
    	UpdateService getService(){
            return UpdateService.this;
        }
    }



	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
