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
	
		
		/**
		 * 从一个子线程线程中发送Messaage到主线程中的Handler.从子线程到主线程。
		 */
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
						Log.i("MyLog",Thread.currentThread().getName()+"===>"+"sendMessage");	
						handler.sendMessage(msg);
						
						
					}
				}).start();
			}
		});
		
		
		/**
		 * 从一个子线程线程中发送Messaage到子线程中Handler,发送和接收都在一个子线程中。
		 */
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
		
		/**
		 * 从一个定制线程中发送Message到另一个线程中的Handler，Handler在哪个线程，
		 * 由onLooperPrepared()方法中的设置有关，可以是主线程也可以是子线程。
		 * 定制线程的run()方法要在super.run()之前才会被执行。
		 */
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//新建一个HandlerThread制定线程,并设置线程的名称。
				MyHandlerThread myHandlerThread = new MyHandlerThread("MY_HANDLER_THREAD");
				myHandlerThread.start();
				//如果执行一个循环的信息队列，可以用下面的语句退出子线程。
//				myHandlerThread.quit();
			}
		});
		
		
		
		/**
		 * 主线程中的Handler将一个Runnable加入信息队列中。Runnable的run()方法被执行。
		 * 同时又发送了一个Message到handleMessage()方法中,handleMessage()方法也会被执行。
		 * 如果在Runnable中加上一句：handler.removeMessages(1);则handleMessage()方法就不会被执行了。
		 */
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
//						handler.removeMessages(1);
						Log.i("MyLog", Thread.currentThread().getId()+"==============>Runnable");
						handler.postDelayed(this, 3000);
					}
				};
				

				handler.post(runnable);
				handler.sendEmptyMessage(1);
				
			}
		});
		
		
		
		
		
		/**
		 * 在子线程中循环执行handleMessage()方法.直到点击Stop按钮
		 */
		findViewById(R.id.star).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				new Thread(new Runnable() {
					@Override
					public void run() {

						//初始化一个当前线程的Looper
						Looper.prepare();
						
						//创建Handler.Handler()的默认构造函数相关联当前线程的Looper
						handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								super.handleMessage(msg);
								
								Log.i("MyLog",Thread.currentThread().getId()+"===>子线程在执行...."+msg.what);	

								sendEmptyMessageDelayed(1, 3000);
								
							}
						};
						Log.i("MyLog",Thread.currentThread().getId()+"===>"+"sendMessage");	
						handler.sendEmptyMessage(1);

						Looper.loop();//运行在这个线程的消息队列。
					}
				}).start();
				
			}
		});
		
		/**
		 * 移除子线程中handler的Message.中止子线程的执行。
		 */
		findViewById(R.id.stop).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler.removeMessages(1);
			}
		});
		
		
	}
	
	private Handler handler;

	
	class MyHandlerThread extends HandlerThread{
		public MyHandlerThread(String name) {
			super(name);
		}

		

		@Override
		protected void onLooperPrepared() {
			super.onLooperPrepared();
			//获得制定线程的Looper.
			Looper lp = this.getLooper();
			//将Looper传给Handler.
			Handler handler = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					
					Log.i("MyLog",Thread.currentThread().getName()+"===>"+msg.obj.toString());	

				}
			};
			
			Message msg = Message.obtain();
			msg.obj = "Message.";
			Log.i("MyLog",Thread.currentThread().getName()+"===>"+"sendMessage");	
			handler.sendMessage(msg);
			
		}


		@Override
		public void run() {
			
			Log.i("MyLog",Thread.currentThread().getId()+"===>run");	
			
			super.run();
			

		}
		
		
		
	}
}




























