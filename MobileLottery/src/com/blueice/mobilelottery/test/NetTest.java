package com.blueice.mobilelottery.test;

import java.io.InputStream;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.net.HttpClientUtils;
import com.blueice.mobilelottery.net.NetUtils;
import com.blueice.mobilelottery.net.protocal.Message;
import com.blueice.mobilelottery.net.protocal.element.CurrentIssues;
import com.blueice.mobilelottery.net.protocal.element.UserLoginElement;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * 网络测试类。
 *
 */
public class NetTest extends AndroidTestCase {

	
	
	public void testNetType(){
		HttpClientUtils clientUtils = new HttpClientUtils();
		
		Message message = new Message();
		CurrentIssues element = new CurrentIssues();
		String xml = message.getXml(element);
		
		InputStream is = clientUtils.sendXML(ConstValue.LOTTERY_URI, xml);
		
		byte[] buffer = new byte[2048];
		int len = 0;
		String str = "";
		
		try {
			while ((len = is.read(buffer)) != -1) {
				str = new String(buffer, 0, len);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Log.i("MyLog",str);
	}
	
	
}
