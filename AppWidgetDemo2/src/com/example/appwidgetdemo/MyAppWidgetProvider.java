package com.example.appwidgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyAppWidgetProvider extends AppWidgetProvider {

	private Intent serviceintent;
	
	private static final String MY_ACTION_UPDATE = "my.action.APPWIDGET_UPDATE";
	private static final String MY_ACTION_ONCLICK = "my.action.APPWIDGET_ONCLICK";
	

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);	
		
	}

	/**
	 * 删除最后一个AppWidget时，关闭服务。
	 */
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);

		serviceintent=new Intent(context,UpdateService.class);
		context.stopService(serviceintent);
		Log.i("MyLog","STOP_Service");
	}

	/**
	 * 添加第一个Appwidget时，打开服务。
	 * 再添加Appwidget不会重新再开打开一个服务。
	 */
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		
		//开启后台服务，更新AppWidget
		serviceintent=new Intent(context,UpdateService.class);
        context.startService(serviceintent);

        Log.i("MyLog","START_Service");
	}


	@Override
	public void onReceive(Context context, Intent intent) {
	
		String action = intent.getAction();
		if(MY_ACTION_ONCLICK.equals(action)){
			
			Log.i("MyLog", "MY_ACTION_ONCLICK");

		}else if(MY_ACTION_UPDATE.equals(action)){
			
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_appwidget);
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			ComponentName component = new ComponentName(context,MyAppWidgetProvider.class);
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(component);
			
			remoteViews.setTextViewText(R.id.button1, intent.getStringExtra("str"));
			
			appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
			
			Log.i("MyLog","UPDATE_Service");
			
		}else{
	
			super.onReceive(context, intent);
		}

	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        
        Intent intent = new Intent();
        intent.setAction(MY_ACTION_ONCLICK);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_appwidget);
        
        //设置AppWidget的点击事件。
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        
//      Intent intent = new Intent(context,MainActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
        remoteViews.setOnClickPendingIntent(R.id.button1, pendingIntent);     
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        
        Log.i("MyLog",Thread.currentThread().getId()+" onUpdate");
		
	}
	
	

}












































