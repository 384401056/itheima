package com.blueice.mobilelottery;

import java.util.LinkedList;
import java.util.List;

/**
 * 	需要用到的常理接口类。
 * @author ServerAdmin
 *
 */
public interface ConstValue {

	/**
	 * 字符集
	 */
	String ENCODING = "UTF-8";
	
	/**
	 * 代理的ID
	 */
	String AGENTERID = "00088888";
	
	/**
	 * 请求的来源设备。
	 */
	String SOURCE = "ivr";
	
	/**
	 * 加密方式.
	 */
	String COMPRESS = "DES";
	
	/**
	 * 代理商密钥(应该放在jni中去调用)
	 */
	String AGENTER_PASSWORD = "9ab62a694d8bf6ced1fab6acd48d02f8";
	
	/**
	 * des加密用密钥
	 */
	String DES_PASSWORD = "9b2648fcdfbad80f";
	/**
	 * 服务器地址(应该放在jni中去调用)
	 */
	String LOTTERY_URI = "http://192.168.0.122/MLServer/Entrance";// 10.0.2.2模拟器如果需要跟PC机通信127.0.0.1

	
	/**
	 * 测试的BaseUI的ID
	 */
	int VIEW_FIRST = 1;
	int VIEW_SECOND = 2;
	
	/**
	 * BaseUI的ID
	 */
	int VIEW_HELL = 10;
	int VIEW_SSQ = 15;
	int VIEW_SHOPPING = 20;
	int VIEW_PREBET = 25;
	int VIEW_LOGIN = 30;
	
	/**
	 * 双色球标示
	 */
	int SSQ=118;
	/**
	 * 服务器返回成功状态码
	 */
	String SUCCESS="0";
	
}























