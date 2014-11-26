package com.example.appwidgetdemo;

import java.util.Date;
import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

public class MyAppWidgetProvider extends AppWidgetProvider {

	private Context context;
	private AppWidgetManager appWidgetManager;
	private RemoteViews remoteViews;
	private int[] appWidgetIds;
	
	private static final String MY_ACTION_STOP = "my.action.APPWIDGET_STOP";
	
	MyHandler myHandler = new MyHandler();
	UpdateWidgetRunnable myRunnable = new UpdateWidgetRunnable();
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.i("MyLog",Thread.currentThread().getName()+"onDeleted");
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.i("MyLog",Thread.currentThread().getName()+"onDisabled");

	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Log.i("MyLog",Thread.currentThread().getName()+"onEnabled");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		
		String action = intent.getAction();
		if(MY_ACTION_STOP.equals(action)){
			
			myHandler.removeCallbacks(myRunnable);

		}
		
		
		Log.i("MyLog",Thread.currentThread().getName()+"onReceive");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.i("MyLog",Thread.currentThread().getName()+"onUpdate");
		
		this.context=context;
		this.appWidgetManager = appWidgetManager;
		this.appWidgetIds = appWidgetIds;
		this.remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_appwidget);
	
		
		//在消息队列中更新AppWidget中的文字
		Thread thread = new Thread(myRunnable);
		thread.start();

//		myHandler.post(myRunnable);
		
		

	}
	
	class UpdateWidgetRunnable implements Runnable {
		@Override
		public void run() {
			
			Log.i("MyLog",Thread.currentThread().getName()+"Runnable");
			
			Random random = new Random();
			int i = random.nextInt(1000);
			Message message = new Message();
			message.arg1 = i;
			myHandler.sendMessage(message);

		}
	}

	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			
			Log.i("MyLog", Thread.currentThread().getName()+msg.arg1+"");

			for (int i = 0; i < appWidgetIds.length; i++) {

				Intent intent = new Intent(context,MainActivity.class);
				PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
				
				remoteViews.setOnClickPendingIntent(R.id.button1, pendingIntent);
				
				remoteViews.setTextViewText(R.id.button1, msg.arg1+"");

				appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
			}
			

			myHandler.postDelayed(myRunnable, 1000);
			
		}	
	}


}












































