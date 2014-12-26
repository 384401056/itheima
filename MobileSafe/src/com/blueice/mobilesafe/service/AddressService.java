package com.blueice.mobilesafe.service;

import com.blueice.mobilesafe.db.dao.NumberAddrQueryUtils;
import com.blueice.mobilesafe.utils.PromptManager;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.location.GpsStatus.Listener;
import android.os.IBinder;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class AddressService extends Service {

	private TelephonyManager telephonyManager;
	private MyPhoneStateListener listener;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Log.i("MyLog", "AddressService 开启..");
		//电话管理对象。
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		listener = new MyPhoneStateListener();
		/**
		 * 监听 第一个参数是  PhoneStateListener 的对象
		 * 
		 * 第二个参数是监听的类型。LISTEN_CALL_STATE
		 */
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);		
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		//取消监听。
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		Log.i("MyLog", "AddressService 关闭..");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	
	
	class MyPhoneStateListener extends PhoneStateListener{

		/*
		 * 当电话状态改变时。
		 * 第一个参数是状态标志。
		 * 第二个参数是来电号码。
		 */
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			
			switch (state) {
			//当电话铃声响起时。(来电时)
			case TelephonyManager.CALL_STATE_RINGING:
				//根据获得的电话号码，查询它的归属地。
				String address = NumberAddrQueryUtils.queryNumber(incomingNumber);
				Log.i("MyLog", incomingNumber+"");
				Log.i("MyLog", address);
				PromptManager.showToast(getApplicationContext(), address);
				break;
			//当电话挂断的状态。
			case TelephonyManager.CALL_STATE_OFFHOOK:
				
				break;
			}
			
		}
		
	}

}














