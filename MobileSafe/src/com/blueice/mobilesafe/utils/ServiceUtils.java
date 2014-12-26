package com.blueice.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * 与服务相关的工具类。
 * @author Administrator
 *
 */
public class ServiceUtils {

	/**
	 * 检查某个服务还在运行
	 * @param context
	 * @param serviceName 服务的名称。
	 * @return
	 */
	public static boolean isServiceRunning(Context context,String serviceName){
		
		//不仅可以管理Activity还可管理Service.
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		
		//返回正在运行的服务List.maxNum为返回的最大服务数。
		int maxNum = 100;
		List<RunningServiceInfo> services = activityManager.getRunningServices(maxNum );
		
		//循环取出服务的className与传入的服务名称(含包名和类名)比较，如果有相同的，说明此服务还在运行。
		for(RunningServiceInfo info : services) {
			
			String name = info.service.getClassName();
			
			if(name.equals(serviceName)){
				return true;
			}
		}
		
		return false;
	}
	
}
