package com.blueice.mobilesafe.service;

import com.blueice.mobilesafe.db.dao.NumberAddrQueryUtils;
import com.blueice.mobilesafe.utils.PromptManager;

import android.R;
import android.R.integer;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.location.GpsStatus.Listener;
import android.os.IBinder;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

public class AddressService extends Service {

	private Context context;
	private TelephonyManager telephonyManager;
	private MyPhoneStateListener listener;
	private OutCallReceiver receiver; //去电的广播接收者。
	private WindowManager wm;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		this.context = AddressService.this;
		
		init();
		
	}

	
	private void init() {
		
		Log.i("MyLog", "AddressService 开启..");

		//获取窗体管理类，用来生成自定义的Toast.
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);

		View view = View.inflate(context,R.layout.toast, null);
		
		//窗体管理类的参数设置。
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		
		 params.height = WindowManager.LayoutParams.WRAP_CONTENT;
         params.width = WindowManager.LayoutParams.WRAP_CONTENT;
         params.type = WindowManager.LayoutParams.TYPE_TOAST;
         params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //保持屏幕开启
                 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  //不允许有焦点
                 | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE; //不允许点击
         params.format = PixelFormat.TRANSLUCENT; //半透明。
//         params.windowAnimations = com.android.internal.R.style.Animation_Toast; //动画.
//         params.setTitle("Toast");  //设置Toast的Title
         
		 wm.addView(view, params);
		
		//电话管理对象。
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		listener = new MyPhoneStateListener();
		/**
		 * 监听 第一个参数是  PhoneStateListener 的对象
		 * 
		 * 第二个参数是监听的类型。LISTEN_CALL_STATE
		 */
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);		
		
		
		/*
		 * 用代码注册广播接收者。
		 */
		receiver = new OutCallReceiver();
		//意图匹配器。
		IntentFilter filter = new IntentFilter("android.intent.action.NEW_OUTGOING_CALL");
		registerReceiver(receiver, filter);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		//取消监听。
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		Log.i("MyLog", "AddressService 关闭..");
		
		/*
		 * 用代码注销广播接收者。
		 */
		unregisterReceiver(receiver);
		receiver = null;
		
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
	
	
	/**
	 * 打电话的广播接收类，用来处理去电的归属地查询。
	 * 注意：加权限和注册Receiver.
	 * 广播接收者可以在Manifest文件中注册，也可以在服务中注册和注销。
	 */
	private class OutCallReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			Log.i("MyLog", "有电话打出...");
			
			//获取去电的电话号码。
			String phone = getResultData();
			
			//查询数据库，获取地址。
			String address = NumberAddrQueryUtils.queryNumber(phone);
			
			//显示Toast.
			PromptManager.showToast(context, address);
			
		}

	}

}














