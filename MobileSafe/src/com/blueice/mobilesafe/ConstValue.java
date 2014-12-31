package com.blueice.mobilesafe;

/**
 * 	需要用到的常量接口类。
 * @author ServerAdmin
 *
 */
public interface ConstValue {

	/**
	 * 字符集
	 */
	String ENCODING = "UTF-8";

	/**
	 * 服务器地址(应该放在jni中去调用)
	 */
	String URI = "http://192.168.1.100/MLServer/xxx.html";

	/**
	 * 手机归属地数据库路径。
	 */
	String DBPATH = "data/data/com.blueice.mobilesafe/files/address.db";
	
	
	String[] toastStyle = {"半透明","活力橙","卫士蓝","金属灰","苹果绿"};
	
}























