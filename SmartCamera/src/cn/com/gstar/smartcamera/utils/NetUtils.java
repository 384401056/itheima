package cn.com.gstar.smartcamera.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类。
 *
 */
public class NetUtils {
	/**
	 * 检察网络。
	 * 
	 * @return boolean
	 */
	public static boolean checkNet(Context context) {

		// 1.判断WIFI

		boolean isWIFI = isWifiConnection(context);

		// 2.判断Mobile.
		boolean isMOBILE = isMobileConnection(context);

		// 如果为Mobile，判断是哪个APN被选中。
		// if (isMOBILE) {
		// readAPN(context);
		// }

		if (!isWIFI && !isMOBILE) {
			return false;
		}

		return true;
	}

	/**
	 * 判断是哪个APN被选中。
	 * 
	 * @param context
	 */
	// private static void readAPN(Context context) {
	//
	// //获取APN列表的Uri.
	// Uri APN_URL = Uri.parse("context://telephony/carriers/preferapn");
	// //谷歌的4.0手机对该操作的权限进行了屏蔽。模拟器也是。
	//
	//
	// ContentResolver resolver = context.getContentResolver();
	//
	// //返回被选中的Cursor,因为APN只有一个会被选中，所以只会返回一个。
	// Cursor cursor = resolver.query(APN_URL, null, null, null, null);
	//
	// //通过Cursor得到APN中某一列的值。
	// if(cursor!=null&&cursor.moveToFirst()){
	//
	// //将取到的代理服务器地址和端口存入全局变量文件中。
	// GlobalParams.PROXY = cursor.getString(cursor.getColumnIndex("proxy"));
	//
	// GlobalParams.PORT = cursor.getInt(cursor.getColumnIndex("port"));
	// }
	//
	// }

	/**
	 * 判断MOBILE是否连接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isMobileConnection(Context context) {

		// 获取连接的管理类。
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE);

		// 获取Mobile的信息类。
		NetworkInfo networkType = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (networkType != null) {
			return networkType.isConnected();
		}

		return false;
	}

	/**
	 * 判断WIFI是否连接。
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isWifiConnection(Context context) {

		// 获取连接的管理类。
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE);

		// 获取wifi的信息类。
		NetworkInfo networkType = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (networkType != null) {
			return networkType.isConnected();
		}

		return false;
	}
}
