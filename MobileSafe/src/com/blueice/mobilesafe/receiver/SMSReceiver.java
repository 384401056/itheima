package com.blueice.mobilesafe.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.blueice.mobilesafe.R;
import com.blueice.mobilesafe.service.GPSService;

/**
 * 接收短信的Receiver.
 *
 */
public class SMSReceiver extends BroadcastReceiver {

	private Context context;
	private DevicePolicyManager dpm;
	private SharedPreferences sp;

	@Override
	public void onReceive(Context context, Intent intent) {

		// 定义DevicePolicyManager对象。
		dpm = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);

		sp = context.getSharedPreferences("setting", Context.MODE_PRIVATE);

		this.context = context;

		// 获取从意图传过来的短信byte[],而且是多条的短信。pdu是短信协议。
		Object[] objs = (Object[]) intent.getExtras().get("pdus");

		// 循环取出每一条短信。
		for (Object b : objs) {

			SmsMessage sms = SmsMessage.createFromPdu((byte[]) b);

			// 获取发送者号码。
			String sender = sms.getOriginatingAddress();

			// 获取短信内容。
			String body = sms.getMessageBody();

			// 获取时间。
			long time = sms.getTimestampMillis();
			

			// 先判断发送者号码。
			if (sender.equals(sp.getString("safenumber", null))) {

				if (body.equals("#*location*#")) {
					// 获取GPS信息。
					Log.i("MyLog", "获取GPS信息。");

					// 启动GPS服务，GPS信息。
					Intent gpsService = new Intent(context, GPSService.class);
					context.startService(gpsService);

					String lastLocation = sp.getString("lastlocation", null);

					// 发送短信给安全号码.
					if (lastLocation == null) {
						SmsManager.getDefault().sendTextMessage(
								sp.getString("safenumber", null), null,
								"Getting Location.... ", null, null);
					} else {
						SmsManager.getDefault().sendTextMessage(
								sp.getString("safenumber", null), null,
								lastLocation, null, null);
					}

					// 终止短信，不止其它应用接到此信息。
					abortBroadcast();
				}

				if (body.equals("#*alarm*#")) {
					// 播放报警音乐。
					Log.i("MyLog", "播放报警音乐。");

					playAlarmMusic();

					abortBroadcast();
				}

				if (body.equals("#*wipedata*#")) {
					// 远程数据销毁
					Log.i("MyLog", "远程数据销毁。");

					ComponentName who = new ComponentName(context,
							DeviceAdmin.class);
					if (dpm.isAdminActive(who)) {

//						dpm.wipeData(0);// 恢复出厂设置,模拟器会重启。

						// 清除外置存储卡的信息。
//						dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
					} else {
						SmsManager.getDefault().sendTextMessage(
								sp.getString("safenumber", null), null,
								"Not POLICY", null, null);
					}

					abortBroadcast();
				}

				if (body.equals("#*lockscreen*#")) {
					// 远程锁屏
					Log.i("MyLog", "远程锁屏。");

					ComponentName who = new ComponentName(context,
							DeviceAdmin.class);
					if (dpm.isAdminActive(who)) {
						dpm.lockNow();
//						dpm.resetPassword("", 0); //修改锁屏密码。
					} else {
						SmsManager.getDefault().sendTextMessage(
								sp.getString("safenumber", null), null,
								"Not POLICY", null, null);
					}

					abortBroadcast();
				}

			}

		}
	}

	/**
	 * 播放报警音乐。
	 */
	private void playAlarmMusic() {

		MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
		mediaPlayer.setLooping(false); // 设置是否循环播放。
		mediaPlayer.setVolume(1.0f, 1.0f); // 设置左右声道的音量。
		mediaPlayer.start(); // 开始播放。
	}

}
