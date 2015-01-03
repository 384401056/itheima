package com.blueice.mobilesafe.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.blueice.mobilesafe.beam.AppInfo;

public class AppInfoEngine {

	
	/**
	 * 获取手机中的所有APP信息列表。
	 * @param context 上下文
	 * @return
	 */
	public static List<AppInfo> getAppInfos(Context context){
		
		List<AppInfo> result = new ArrayList<AppInfo>();
		
		//通过context获取包管理类。
		PackageManager pm = context.getPackageManager();
		
		//获取手机上的所有安装程序的信息.
//		List<ApplicationInfo> installedApplications = pm.getInstalledApplications(0);
		
		
		//获取手机上的所有安装程序的包信息.得到PackageInfo相当于得到一个应用程序的Mainfest文件。
		List<PackageInfo> infos = pm.getInstalledPackages(0);
		
		for(PackageInfo info:infos){
			
			AppInfo appInfo = new AppInfo();
			appInfo.setPackName(info.packageName);//获取包名.
			
			//info.applicationInfo 相应于进入Mainfest文件的Application标签。
			appInfo.setIcon(info.applicationInfo.loadIcon(pm));//获取应用程序图标。
			appInfo.setName(info.applicationInfo.loadLabel(pm).toString()); //获取应用程序名称。
			
			//获取应用程序的标志符。
			int flags = info.applicationInfo.flags;
			
			if((flags&ApplicationInfo.FLAG_SYSTEM)==0){
				//是用户应用。
				appInfo.setUserApp(true);
			}else{
				//是系统系统。
				appInfo.setUserApp(false);
			}
			
			if((flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)==0){
				//在内存中
				appInfo.setInRom(true);
			}else{
				//在SD卡上。
				appInfo.setInRom(false);
			}
			
			
			result.add(appInfo);
		}
		
		return result;
	}
	
	
	
}



































