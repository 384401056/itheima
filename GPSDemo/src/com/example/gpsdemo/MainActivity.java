package com.example.gpsdemo;


import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



public class MainActivity extends Activity {

	private MyLoacationListener listener;
	private LocationManager lm;
	private TextView tv01;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv01 = (TextView) findViewById(R.id.tv01);
		
		//获得一个位置服务.
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		//获取每一个位置服务的提供者。
//		List<String> providers = lm.getAllProviders();	
		//打印出所有提供者。
//		for(String prov:providers){
//			
//			Log.i("MyLog",prov);
//		}
		
		
		listener = new MyLoacationListener();
		
		//通过设置条件自动选择一个精度最好的位置提供者。
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String bestProvider = lm.getBestProvider(criteria , true);
		
		lm.requestLocationUpdates(bestProvider, 10000, 10, listener);
		
		Log.i("MyLog", bestProvider);
		
		lm.requestLocationUpdates("gps", 0, 0, listener); //使用gps为提供者。
		
	}
	
	
	class MyLoacationListener implements LocationListener{

		/**
		 * 当位置改变时。
		 */
		@Override
		public void onLocationChanged(Location location) {
			
			String longitude = "经度："+location.getLongitude();
			String latitude =  "纬度："+location.getLatitude();
			String accuracy = "精确度："+ location.getAccuracy();
			
			Date date = new Date(location.getTime());
			
			String time = "时间："+date.toLocaleString();
			String speed = "速度："+location.getSpeed();
			
			tv01.setText(longitude+"\n"+latitude+"\n"+accuracy+"\n"+time+"\n"+speed);
			
			
			
			Log.i("MyLog",longitude);
			Log.i("MyLog",latitude);
			Log.i("MyLog",accuracy);
			Log.i("MyLog",time);
			Log.i("MyLog",speed);
			
		}

		/**
		 * 当状态改变时。开启或关闭。
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * 当一个Providers可用时。
		 */
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * 当一个Providers不可用时。
		 */
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		//取消位置监听
		lm.removeUpdates(listener);
		listener = null;
		
	}
	
}
































