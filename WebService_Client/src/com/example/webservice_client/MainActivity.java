package com.example.webservice_client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.blueice.mywebservice.User;
import com.blueice.mywebservice.WebServiceFunc;
import com.blueice.mywebservice.WebServiceFuncService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btn01;
	private Button btn02;
	private Button btn03;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn01 = (Button) findViewById(R.id.button1);
		btn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				byURLConnection();
			}
		});
		
		
		btn02 = (Button) findViewById(R.id.button2);
		btn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				byWebService();
			}
		});

		
		btn03 = (Button) findViewById(R.id.button3);
		btn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
	}
	
	private void byURLConnection(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {	
				
					URL url = new URL("http://192.168.0.95:7418/WebService");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					
					connection.setDoInput(true);
					connection.setDoOutput(true);
					connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
					connection.setRequestMethod("POST");
					
					OutputStream os = connection.getOutputStream();
					
					StringBuffer sBuffer = new StringBuffer(2048);
					
					//将请求头放入StringBuffer中。
					sBuffer
						.append("<?xml version=\"1.0\" ?>")
						.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">")
						.append("<S:Body><ns2:findUserById xmlns:ns2=\"http://mywebservice.blueice.com/\">")
						.append("<arg0>123</arg0>")
						.append("</ns2:findUserById></S:Body></S:Envelope>");
					
					
					os.write(sBuffer.toString().getBytes());
					
					InputStream is = connection.getInputStream();
					
					byte[] bytes = new byte[1024];
					int len = 0;
					final StringBuilder sBuilder = new StringBuilder(2048);
					while((len=is.read(bytes))!=-1){
						
						String str = new String(bytes,0,len, "UTF-8");
						sBuilder.append(str);
						
					}
					
                    //将Runnable加入到UI线程的信息队列中（也就是主线程中）去执行。
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, sBuilder.toString(),Toast.LENGTH_LONG).show();
						}
					});

					Log.i("MyLog",sBuilder.toString());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	
	private void byWebService(){
		
		new Thread(new Runnable() {
			@Override
			public void run() {

				//获取接口。
				WebServiceFunc ws = new WebServiceFuncService().getWebServiceFuncPort();
				final User user = ws.findUserById(123);

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(MainActivity.this, user.getName().toString(), Toast.LENGTH_LONG).show();
					}
				});

				Log.i("MyLog",user.getName().toString());

			}
		}).start();
		
	}
	
}


















