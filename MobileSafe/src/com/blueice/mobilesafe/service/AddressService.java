package com.blueice.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blueice.mobilesafe.GlobalParams;
import com.blueice.mobilesafe.R;
import com.blueice.mobilesafe.db.dao.NumberAddrQueryUtils;

public class AddressService extends Service {

	private Context context;
	private TelephonyManager telephonyManager;
	private MyPhoneStateListener listener;
	private OutCallReceiver receiver; // 去电的广播接收者。
	private WindowManager wm;
	private WindowManager.LayoutParams params;
	private View toastView;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
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

		// 获取系统的窗体管理类，用来生成自定义的Toast.
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);

		// 电话管理对象。
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		listener = new MyPhoneStateListener();
		/**
		 * 监听 第一个参数是 PhoneStateListener 的对象
		 * 
		 * 第二个参数是监听的类型。LISTEN_CALL_STATE
		 */
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

		/*
		 * 用代码注册广播接收者。
		 */
		receiver = new OutCallReceiver();
		// 意图匹配器。
		IntentFilter filter = new IntentFilter(
				"android.intent.action.NEW_OUTGOING_CALL");
		registerReceiver(receiver, filter);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		// 取消监听。
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		Log.i("MyLog", "AddressService 关闭..");

		/*
		 * 用代码注销广播接收者。
		 */
		unregisterReceiver(receiver);
		receiver = null;

	}

	/**
	 * 显示自定义的Toast.
	 * 
	 * @param text
	 *            要显示的文本。
	 */
	public void showMyToast(String text) {

		toastView = View.inflate(context, R.layout.my_toast, null);
		TextView tv = (TextView) toastView.findViewById(R.id.tv);
		LinearLayout ll_bg = (LinearLayout) toastView.findViewById(R.id.ll_bg);

		/**
		 * 设置Toast的拖动事件。
		 */
		toastView.setOnTouchListener(new OnTouchListener() {
			int startX;
			int startY;

			@Override
			public boolean onTouch(View view, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: // 手指按下时

					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					Log.i("MyLog", "开始位置:" + startX + "," + startY);
					break;

				case MotionEvent.ACTION_MOVE: // 手指按下移动时。

					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();
					Log.i("MyLog", "新的位置:" + newX + "," + newY);

					int dx = newX - startX;
					int dy = newY - startY;
					Log.i("MyLog", "偏移量:" + dx + "," + dy);

					// 改变Toast的参数。
					params.x += dx;
					params.y += dy;
					// 处理parms的边限问题。
					if (params.x < 0) {
						params.x = 0;
					}

					if (params.y < 0) {
						params.y = 0;
					}

					if (params.x > GlobalParams.WIN_WIDTH - view.getWidth()) {
						params.x = GlobalParams.WIN_WIDTH - view.getWidth();
					}

					if (params.y > GlobalParams.WIN_HEIGHT - view.getHeight()) {
						params.y = GlobalParams.WIN_HEIGHT - view.getHeight();
					}

					// 更新View。
					wm.updateViewLayout(view, params);

					// 更新开始位置为当前位置。
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();

					break;

				case MotionEvent.ACTION_UP: // 手指放开时

					// 保存最后的位置。
					Editor edit = GlobalParams.sp.edit();
					edit.putInt("toastdx", params.x);
					edit.putInt("toastdy", params.y);
					edit.commit();
					break;

				}
				return true;// 事件处理完毕，不再传递了。如果设置了此控件的点击事件，则也是无效的。
			}
		});

		int[] bgs = { R.drawable.call_locate_white,
				R.drawable.call_locate_orange, R.drawable.call_locate_blue,
				R.drawable.call_locate_gray, R.drawable.call_locate_green };
		int index = GlobalParams.sp.getInt("toastStyle", 0);
		ll_bg.setBackgroundResource(bgs[index]);

		// 窗体管理类的参数设置。
		params = new WindowManager.LayoutParams();

		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;

		/*
		 * 如果要Toast可以被移动就要去移除Toast的
		 * WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE和修改LayoutParams
		 * .TYPE_TOAST;
		 * TYPE_PRIORITY_PHONE是一个拥有电话优先级的一种窗体类型。需要添加权限SYSTEM.ALERT_WINDOW.
		 */
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON // 保持屏幕开启
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 不允许有焦点

		// params.type = WindowManager.LayoutParams.TYPE_TOAST;
		// params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //
		// 保持屏幕开启
		// | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE // 不允许有焦点
		// | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE; // 不允许点击

		params.format = PixelFormat.TRANSLUCENT; // 半透明。

		/*
		 * 指定Toast的对齐方式和位置。 当gravity指定为top+left后，x代表距离left多少像素。y代表距离top多少像素。
		 * 如gravicty指定为center类型则，x,y无效。 由此可以设计手指拖动Toast
		 */
		params.gravity = Gravity.TOP + Gravity.LEFT;
		params.x = GlobalParams.sp.getInt("toastdx", 100); // 从sp获取位置的偏移量。
		params.y = GlobalParams.sp.getInt("toastdy", 100);

		// params.windowAnimations =
		// com.android.internal.R.style.Animation_Toast; //动画.
		// params.setTitle("Toast"); //设置Toast的Title

		tv.setText(text);
		wm.addView(toastView, params);

	}

	/**
	 * 自定义的电话状态监听类
	 */
	class MyPhoneStateListener extends PhoneStateListener {
		/*
		 * 当电话状态改变时。 第一个参数是状态标志。 第二个参数是来电号码。
		 */
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);

			switch (state) {
			// 当电话铃声响起时。(来电时)
			case TelephonyManager.CALL_STATE_RINGING:
				// 根据获得的电话号码，查询它的归属地。
				String address = NumberAddrQueryUtils
						.queryNumber(incomingNumber);
				// Log.i("MyLog", incomingNumber+"");
				// Log.i("MyLog", address);
				//
				showMyToast(address);

				break;
			// 当电话空闲、挂断、来电拒接的状态。
			case TelephonyManager.CALL_STATE_IDLE:
				// 自定义Toast不再显示。
				if (toastView != null) {
					wm.removeView(toastView);
				}
				break;
			}

		}

	}

	/**
	 * 打电话的广播接收类，用来处理去电的归属地查询。 注意：加权限和注册Receiver.
	 * 广播接收者可以在Manifest文件中注册，也可以在服务中注册和注销。
	 */
	private class OutCallReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			Log.i("MyLog", "有电话打出...");

			// 获取去电的电话号码。
			String phone = getResultData();

			// 查询数据库，获取地址。
			String address = NumberAddrQueryUtils.queryNumber(phone);

			// 显示Toast.
			// PromptManager.showToast(context, address);
			showMyToast(address);

		}

	}

}
