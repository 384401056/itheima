package com.blueice.mobilesafe.receiver;

import java.io.UnsupportedEncodingException;


import com.blueice.mobilesafe.utils.PromptManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 开机启动的广播接收类
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	private SharedPreferences sp;
	private TelephonyManager tm;
	
	@Override
	public void onReceive(Context context, Intent intent) {

		/*
		 * 由于是机器重启后执行的程序，所以 SharedPreferences 要重新创建。否则是为空的。
		 */
		sp = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
		
		boolean protecting = sp.getBoolean("protecting", false);
		
		if (protecting) {
			// 开启防盗保护才执行这个地方
			tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			// 读取之前保存的SiM信息；
			String saveSim = sp.getString("sim", null)+"5556";

			// 读取当前的sim卡信息
			String realSim = tm.getSimSerialNumber();

			// 比较是否一样
			if (saveSim.equals(realSim)) {
				// sim没有变更，还是同一个哥们
				return;
			} else {
				// sim 已经变更 发一个短信给安全号码
				
				Log.i("MyLog", "sim 已经变更");
				
				PromptManager.showToast(context, "SIM卡已经变更");

				String smsStr = "";
				//发送的短信内容。
				try {
					
					smsStr = new String("SIM卡已经变更....".getBytes(), "UTF-8");
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				SmsManager.getDefault().sendTextMessage(sp.getString("safenumber", null), null,"SIM卡已经变更....", null, null);
			}

		}
	}

}
