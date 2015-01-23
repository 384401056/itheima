package com.example.androidasynctastdemo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private ProgressBar pb01 = null;
	private TextView tv01 = null;
	private EditText ed01 = null;

	private static String TAG = "MyLog";
	
	
	private static final  boolean DEVELOPER_MODE = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (DEVELOPER_MODE) {
	         StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	                 .detectDiskReads()
	                 .detectDiskWrites()
	                 .detectNetwork() 
	                 .penaltyLog()
	                 .build());
	         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
	                 .detectLeakedSqlLiteObjects()
	                 .detectLeakedClosableObjects()
	                 .penaltyLog()
	                 .penaltyDeath()
	                 .build());
	     }
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pb01 = (ProgressBar)findViewById(R.id.progressBar1);
		tv01 = (TextView)findViewById(R.id.textView1);
		ed01 = (EditText)findViewById(R.id.editText1);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String url = ed01.getText().toString().trim();
				
				if(!"".equals(url)){
					//执行同步任务。将输入的url传入。
					new MyAsyncTast().execute(url);
					
				}else{
					Toast.makeText(MainActivity.this, "请输入地址!", Toast.LENGTH_LONG).show();
				}

			}
		});

	}
	
	
	
	class MyAsyncTast extends AsyncTask<String,Integer,String>{
		@Override
		protected String doInBackground(String... params) {

			try {
				
				HttpClient client = new DefaultHttpClient();
				HttpPost postMethod = new HttpPost(params[0].toString());  //POST请求。
//				HttpGet get = new HttpGet(params[0].toString());   //GET请求
				HttpResponse response = client.execute(postMethod); //发起请求 
				
				Log.i(TAG, "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码  
			    Log.i(TAG, "result = " + response.getEntity());//获取服务器响应内容  
				
				HttpEntity entity = response.getEntity();
				
				if(entity!=null){
				
					long lenght = entity.getContentLength(); //此方法只有使用POST请求时才有用。Http返回数据的总长度。

					String result = null;  //最终的返回值。
					int toUpdate = 0;  //更新进度的值。
					
					InputStream is = entity.getContent();    //得到输入流。
					
					if(is!=null){
						ByteArrayOutputStream baos = new ByteArrayOutputStream(); //创建输出流
						byte[] buff = new byte[1024];
						int len = -1; //一次读取的长度。
						int count = 0;//总读取的长度。
						
						
						//对接输入输出流。
						while((len = is.read(buff))!=-1){
							baos.write(buff, 0, len);
							count += len;
							
							//更新进度。
							if(lenght>0){
								toUpdate = (int)((count/(float)lenght)*100);
								publishProgress(toUpdate);
							}
							
							Thread.sleep(300);
						}
						
						result = new String(baos.toByteArray(), "utf-8");
					}
					
					is.close();
					
					return result;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		//返回UI，输出结果。
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			tv01.setText(result);
		}

		//更新进度。
		@Override
		protected void onProgressUpdate(Integer... values) {
			pb01.setProgress(values[0]);
		}

		
		
		
	}

}
