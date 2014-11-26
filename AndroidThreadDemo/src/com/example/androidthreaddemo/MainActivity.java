package com.example.androidthreaddemo;

import java.io.IOException;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.os.AsyncTask;  

public class MainActivity extends Activity {

	private Button btn01 = null;
	private Button btn02 = null;
	private Button btn03 = null;
	private Button btn04 = null;
	private ImageView imageView = null;
	
	private final String IMAGE_URL = "http://img1.gtimg.com/tech/pics/hv1/116/101/1739/113104346.jpg";
	
	MyHandler handler = new MyHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageView = (ImageView)findViewById(R.id.imageView1);
		
		btn01 = (Button)findViewById(R.id.button1);
		btn01.setOnClickListener(new OnClickListener(){		
			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {
					
					@Override
					public void run() {

						final Drawable drawable = loadImageFromNetwork(IMAGE_URL);
						
						runOnUiThread(new Runnable() {
							public void run() {
								imageView.setImageDrawable(drawable);
							}
						});
						
					}
				}).start();

			}
		});
		
		
		btn02 = (Button)findViewById(R.id.button2);
		btn02.setOnClickListener(new OnClickListener(){		
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						
						Log.i("MyLog", Thread.currentThread().getName()+"===========>Runnable");
						
						Drawable drawable = loadImageFromNetwork(IMAGE_URL);
						Message msg = new Message();
						msg.obj = drawable;
						
						//此Handler是在主线程中创建的，所以传递信息后可以在handleMessage中更新UI.
						handler.sendMessage(msg);
					}
				}).start();
			}
		});
		
		
		btn03 = (Button)findViewById(R.id.button3);
		btn03.setOnClickListener(new OnClickListener(){		
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						
						final Drawable drawable = loadImageFromNetwork(IMAGE_URL);
						
						imageView.post(new Runnable() {
							@Override
							public void run() {
								imageView.setImageDrawable(drawable);
							}
						});
						
					}
				}).start();
			}
		});
		
		
		btn04 = (Button)findViewById(R.id.button4);
		btn04.setOnClickListener(new OnClickListener(){		
			@Override
			public void onClick(View v) {
				new MyAsyncTask().execute(IMAGE_URL);
			}
		});
	}
	
	
	
	private Drawable loadImageFromNetwork(String imageURL) {
		Drawable drawable = null;
		try {
			
			drawable = Drawable.createFromStream(new URL(imageURL).openStream(), "image.jpg");
	
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return drawable;
	}
	
	
	
	private class MyAsyncTask extends AsyncTask<String,Void, Drawable>{

		@Override
		protected Drawable doInBackground(String... params) {
			return loadImageFromNetwork(IMAGE_URL);
		}

		@Override
		protected void onPostExecute(Drawable result) {
			imageView.setImageDrawable(result);
		}

	}
	
	private class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			Drawable drawable = (Drawable)msg.obj;
			imageView.setImageDrawable(drawable);
			Log.i("MyLog", Thread.currentThread().getName()+"===========>handleMessage");
			
		}
		
	}
	
}













































