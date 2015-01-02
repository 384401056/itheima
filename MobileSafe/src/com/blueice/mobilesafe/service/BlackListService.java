package com.blueice.mobilesafe.service;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ObjectUtils.Null;

import com.android.internal.telephony.ITelephony;
import com.blueice.mobilesafe.db.dao.BlackListDao;
import com.blueice.mobilesafe.db.dao.NumberAddrQueryUtils;
import com.blueice.mobilesafe.service.AddressService.MyPhoneStateListener;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Observable;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BlackListService extends Service {

	private InnerSmsReceiver receiver;
	private BlackListDao dao;
	private TelephonyManager telephonyManager;
	private MyPhoneStateListener listener;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		
		Log.i("MyLog", "BlackListService 开启..");
		dao = new BlackListDao(BlackListService.this); //数据库操作对象。
		
		/*
		 * 来电监听
		 * 
		 */
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//电话管理对象。
		listener = new MyPhoneStateListener();
		/**
		 * 监听 第一个参数是 PhoneStateListener 的对象
		 * 
		 * 第二个参数是监听的类型。LISTEN_CALL_STATE
		 */
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		
		
		
		/*
		 * 用代码注册短信接收者。
		 */
		receiver = new InnerSmsReceiver();
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");//意图匹配器。
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		registerReceiver(receiver, filter);
		
		super.onCreate();
		
	}

	@Override
	public void onDestroy() {
		
		
		Log.i("MyLog", "BlackListService 关闭..");
		
		/*
		 * 取消来电监听。
		 */
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		
		/*
		 * 注销短信接收者。
		 */
		unregisterReceiver(receiver);
		receiver = null;
		
		super.onDestroy();
	}
	

	/**
	 * 短信拦截服务的接收者类。
	 *
	 */
	private class InnerSmsReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			
			Log.i("MyLog", "收到短信");
			// 获取从意图传过来的短信byte[],而且是多条的短信。pdu是短信协议。
			Object[] objs = (Object[]) intent.getExtras().get("pdus");

			// 循环取出每一条短信。
			for (Object obj : objs) {
				
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
				String sender = sms.getOriginatingAddress();// 获取发送者号码。
				String body = sms.getMessageBody();// 获取短信内容。
				long time = sms.getTimestampMillis();// 获取时间。

				String mode = dao.findMode(sender); //通过号码查询该号码的拦截模式。如果没有此号码返回null
				
				if("2".equals(mode) || "3".equals(mode)){
					Log.i("MyLog","拦截短信..");
					
					//拦截短信
					abortBroadcast();
				}
				
				/**
				 * 如果短信内容中包含特定字符，就进行拦截。
				 * 正真的商业项目中，要用到语言分词技术。如：Lucene开源项目。
				 
				if(body.contains("gaoyanbin")){
					Log.i("MyLog","拦截短信..");
					
					//拦截短信
					abortBroadcast();
				}
				
				*/
			}
		}
		
	}
	
	
	
	
	
	
	/**
	 * 自定义的电话状态监听类,用于电话黑名单的拦截。
	 */
	class MyPhoneStateListener extends PhoneStateListener {
		/*
		 * 当电话状态改变时。 第一个参数是状态标志。 第二个参数是来电号码。
		 */
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			

			switch (state) {
			// 当电话铃声响起时。(来电时)
			case TelephonyManager.CALL_STATE_RINGING:
				// 根据获得的电话号码，查询它是否在黑名单中。
				String mode = dao.findMode(incomingNumber);
				if("1".equals(mode) || "3".equals(mode)){
					Log.i("MyLog","拦截电话..");
					
					
					
					/**
					 * 注册一个内容观察者，等数据库中的数据改变后再执行删除
					 */
					Uri uri = Uri.parse("content://call_log/calls");
					getContentResolver().registerContentObserver(uri, true, new MyContentObserve(incomingNumber,new Handler()));

					/*
					 * 由于此方法是在另外一个进程中执行的远程服务的方法。所以执行的先后顺序可能不一样。
					 * 可能会比下一条语句deleteCallLog(incomingNumber);后执行，此时就会出现通话记录
					 * 没有被删除的情况。所以要用内容观察者的方式来删除通话记录。
					 */
					endCall();
//					deleteCallLog(incomingNumber);//删除通话记录。从联系人应用中获取私有数据。
					
					
					
					
				}
				break;
				
			// 当电话空闲、挂断、来电拒接的状态。
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			}
			
			super.onCallStateChanged(state, incomingNumber);
		}

	}
	
	
	/**
	 * 自定义的内容观察者类。用来删除黑名单号码的通话记录。
	 */
	class MyContentObserve extends  ContentObserver{

		private String incomingNumber;
		
		public MyContentObserve(String incomingNumber, Handler handler) {
			super(handler);
			this.incomingNumber = incomingNumber;
		}

		/**
		 * 当内容改变时，此方法被调用。
		 */
		@Override
		public void onChange(boolean selfChange) {

			Log.i("MyLog","数据库内容改变了...");
			
			//当执行到此方法时，就不再使用内容观察者了。所以要注销内容观察者。
			getContentResolver().unregisterContentObserver(this);
			
			//删除黑名单号码的通话记录。。从联系人应用中获取私有数据。
			deleteCallLog(incomingNumber);
			
			super.onChange(selfChange);
		}
		
		
		
	}
	

	/**
	 * 在另外一个进程中执行的远程服务的方法。
	 * 挂断电话。需要相应的权限。由于1.5版本以后，Android对挂断电话进行了修改,原本用来挂断电话的对象被隐藏了。
	 * 要想调用系统挂断电话的方法，需要通过反射来得到拥有此方法的对象ServiceManager。
	 */
	public void endCall() {
		
		try {
			
//			IBinder iBinder = ServiceManager.getService(TELEPHONY_SERVICE);
			
			//得到ServiceManage的字节码
			Class clazz = BlackListService.class.getClassLoader().loadClass("android.os.ServiceManager");
			//得到字节码类中的方法。
			Method method = clazz.getDeclaredMethod("getService", String.class);
			
			//得到了系统正真拥有挂断电话方法的对象。
			IBinder iBinder = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
			
			/*
			 * 调用endCall方法挂断电话。执行这步前，要导入对应的aidl文件。
			 */
			ITelephony.Stub.asInterface(iBinder).endCall();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * 通过内容提供者删除黑名单号码的通话记录。从联系人应用中获取私有数据。
	 * 需要android.permission.READ_CALL_LOG 和 android.permission.WRITE_CALL_LOG权限
	 */
	public void deleteCallLog(String incomingNumber) {
		
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse("content://call_log/calls"); //call_log是通话记录，calls是联系人数据库中保存通话记录的表名。
		
		//通过number来删除数据库中的数据。
		resolver.delete(uri, "number=?", new String[]{incomingNumber});
		
	}
	
	


}















