package com.example.webservice_client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btn01;

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

		

		btn03 = (Button) findViewById(R.id.button3);
		btn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("MyLog","btn03");
				byKsoap2();
			}
		});

	}

	/**
	 * 通过HTTPURLConnection的方法来访问WebService
	 */
	private void byURLConnection() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					URL url = new URL("http://192.168.0.109:9999/WebService");
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();

					connection.setDoInput(true);
					connection.setDoOutput(true);
					connection.setRequestProperty("Content-Type",
							"text/xml; charset=utf-8");
					connection.setRequestMethod("POST");

					OutputStream os = connection.getOutputStream();

					StringBuffer sBuffer = new StringBuffer(2048);

					// 将请求头放入StringBuffer中。
					sBuffer.append("<?xml version=\"1.0\" ?>")
							.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">")
							.append("<S:Body><ns2:findUserById xmlns:ns2=\"http://mywebservice.blueice.com/\">")
							.append("<arg0>123</arg0>")
							.append("</ns2:findUserById></S:Body></S:Envelope>");

					os.write(sBuffer.toString().getBytes());

					InputStream is = connection.getInputStream();

					byte[] bytes = new byte[1024];
					int len = 0;
					final StringBuilder sBuilder = new StringBuilder(2048);
					while ((len = is.read(bytes)) != -1) {

						String str = new String(bytes, 0, len, "UTF-8");
						sBuilder.append(str);

					}

					// 将Runnable加入到UI线程的信息队列中（也就是主线程中）去执行。
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this,
									sBuilder.toString(), Toast.LENGTH_LONG)
									.show();
						}
					});

					Log.i("MyLog", sBuilder.toString());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 通过KSoap2的类来访问WebService
	 */
	private void byKsoap2() {
		
	    // 定义webservice的命名空间
	    final String SERVICE_NAMESPACE = "http://mywebservice.blueice.com/";
	    // 定义webservice提供服务的url
	    final String SERVICE_URL = "http://192.168.0.109:7418/WebService";
		
	    final String METHOD_NAME ="findUserById";
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
					ht.debug = true;
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					
					SoapObject soapObject = new SoapObject(SERVICE_NAMESPACE, METHOD_NAME);
					
					soapObject.addProperty("arg0", 123);
					
//					envelope.setOutputSoapObject(soapObject); //以下面的语句同意。
					envelope.bodyOut = soapObject;
					
					ht.call(null,envelope);
					
					if(envelope.getResponse()!=null){

						SoapObject result = (SoapObject) envelope.bodyIn;
//						Log.i("MyLog",result.toString());
						SoapObject obj1 = (SoapObject) result.getProperty(0);
//						Log.i("MyLog",obj1.toString());
//						Log.i("MyLog",obj1.getPropertyCount()+"");
						
						final String name = obj1.getProperty("name").toString();

//						Log.i("MyLog",name);
						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
							}
						});
						
						
					}else{
						Log.i("MyLog","envelope is null");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					Log.i("MyLog",e.toString());
				}
			}
		}).start();

	}
}
