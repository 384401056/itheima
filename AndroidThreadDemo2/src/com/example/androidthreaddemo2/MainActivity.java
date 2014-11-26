package com.example.androidthreaddemo2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
							
						//新建一个Handler，并传入主线的Looper。这样这个Handler就相当于是在主线中创建的。可用它发送消息到主线中。
						Handler handler = new Handler(Looper.getMainLooper()){
							@Override
							public void handleMessage(Message msg) {
								super.handleMessage(msg);
								
								Log.i("MyLog",Thread.currentThread().getName()+"===>"+msg.obj.toString());
							}
						};
						
						Message msg = new Message();
						msg.obj = "Handler=============>Message.";
						handler.sendMessage(msg);
						
						
					}
				}).start();
			}
		});
		
		
		
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				new Thread(new Runnable() {
					@Override
					public void run() {

						//初始化一个当前线程的Looper
						Looper.prepare();
						
						//创建Handler.Handler()的默认构造函数相关联当前线程的Looper
						Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								super.handleMessage(msg);
								
								Log.i("MyLog",Thread.currentThread().getName()+"===>"+msg.obj.toString());	

							}
						};
					
						Message msg = new Message();
						msg.obj = "Handler=============>Message.";
						Log.i("MyLog",Thread.currentThread().getName()+"===>"+"sendMessage");	
						handler.sendMessage(msg);
						
						Looper.loop();//运行在这个线程的消息队列。
					}
				}).start();
				
			}
		});
		
		
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//新建一个HandlerThread制定线程,并设置线程的名称。
				MyHandlerThread myHandlerThread = new MyHandlerThread("MY_HANDLER_THREAD");
				myHandlerThread.start();
			}
		});
		
		
		
		
		findViewById(R.id.button4).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				
				final Handler handler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						
						Log.i("MyLog", Thread.currentThread().getId()+"==============>handleMessage");
						
						this.sendEmptyMessageDelayed(1, 3000);
					}
				};
				
				final Runnable runnable = new Runnable() {			
					@Override
					public void run() {
						handler.removeMessages(1);
						Log.i("MyLog", Thread.currentThread().getId()+"==============>Runnable");
						handler.postDelayed(this, 3000);
					}
				};

				handler.post(runnable);
				handler.sendEmptyMessage(1);
				
			}
		});
		
		
		
	}
	
	
	
	class MyHandlerThread extends HandlerThread{
		public MyHandlerThread(String name) {
			super(name);
		}

		

		@Override
		protected void onLooperPrepared() {
			super.onLooperPrepared();
			//获得制定线程的Looper.
//			Looper lp = this.getLooper();
			//将Looper传给Handler.
			Handler handler = new Handler(getMainLooper()){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					
					Log.i("MyLog",Thread.currentThread().getName()+"===>"+msg.obj.toString());	
					
				}
			};
			
			Message msg = new Message();
			msg.obj = "Message.";
			Log.i("MyLog",Thread.currentThread().getName()+"===>"+"sendMessage");	
			handler.sendMessage(msg);
		}



		@Override
		public void run() {
			super.run();
			Log.i("MyLog",Thread.currentThread().getId()+"===>run");	

		}
		
		
		
	}
}




























