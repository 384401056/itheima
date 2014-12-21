package com.blueice.mobilesafe.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSService extends Service {

	private MyLoacationListener listener;
	private LocationManager lm;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		listener = new MyLoacationListener();

		// 通过设置条件自动选择一个精度最好的位置提供者。
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String bestProvider = lm.getBestProvider(criteria, true);

		lm.requestLocationUpdates(bestProvider, 10000, 10, listener);// 使用bestProvider为提供者。

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 取消位置监听
		lm.removeUpdates(listener);
		listener = null;

	}

	class MyLoacationListener implements LocationListener {

		/**
		 * 当位置发生改变时。如果没有获取到GPS此方法是不会被调用的。
		 */
		@Override
		public void onLocationChanged(Location location) {

			PointDouble point = null;
			
			//将标准坐标转换为火星坐标。
			try {
				
				InputStream is = getAssets().open("axisoffset.dat");
				//获取assets媒体文件中的文件流。
				ModifyOffset offset = ModifyOffset.getInstance(is);
				point = offset.s2c(new PointDouble(location.getLongitude(), location.getLatitude()));

			} catch (IOException e) {
				e.printStackTrace();
			}
			 catch (Exception e) {
				e.printStackTrace();
			}
			
			String longitude = "Lon：" + point.x+"\n";
			String latitude = "Lat：" + point.y+"\n";
//			String accuracy = "Acc：" + location.getAccuracy()+"\n";
			
//			Date date = new Date(location.getTime());
//			String time = "Tim：" + date.toLocaleString()+"\n";
//			String speed = "Spe：" + location.getSpeed()+"\n";
			

			//将最后一次位置信息保存在SharePreference中。
			
			SharedPreferences sp = getSharedPreferences("setting", Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			
			editor.putString("lastlocation", longitude+latitude);
			editor.commit();
		
		
//			Log.i("MyLog", longitude);
//			Log.i("MyLog", latitude);
//			Log.i("MyLog", accuracy);
//			Log.i("MyLog", time);
//			Log.i("MyLog", speed);

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

}
