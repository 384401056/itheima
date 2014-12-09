package com.blueice.mobilelottery.engine;

import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.bean.ServerResponsMessage;
import com.blueice.mobilelottery.net.HttpClientUtils;
import com.blueice.mobilelottery.utils.DES;


/**
 * 业务类中的公共部分提取到此类中，所有的业务类都要继承此类。
 *
 */
public abstract class BaseEngine {

	/**
	 * 发送生成的请求XML文件，以及解析并校验服务器返回的XML数据，成功返回一个ServerResponsMessage对象。则否返回null
	 * @param xml 请求XML文件。
	 * @return ServerResponsMessage对象
	 */
	public ServerResponsMessage getResponse(String xml) {
		// 存返回值的Bean
		ServerResponsMessage respons = new ServerResponsMessage();

		// 这里并没有使用NetUtils检查网络?
		HttpClientUtils clientUtils = new HttpClientUtils();
		InputStream is = clientUtils.sendXML(ConstValue.LOTTERY_URI, xml);

		/**
		 * 2.解析XML文件。获取时间截.digest和body
		 */

		// 如果输入流不为空。
		if (is != null) {
			XmlPullParser parser = Xml.newPullParser();
			int eventType = 0;
			String tagName = "";

			try {
				parser.setInput(is, ConstValue.ENCODING);
				eventType = parser.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT) {

					switch (eventType) {
					case XmlPullParser.START_TAG:

						tagName = parser.getName();// 获取标签名。

						if ("timestamp".equals(tagName)) {
							respons.setTimestamp(parser.nextText());
						}

						if ("digest".equals(tagName)) {
							respons.setDigest(parser.nextText());
						}

						if ("body".equals(tagName)) {
							respons.setBodyDES(parser.nextText());
						}

						break;

					default:
						break;
					}
					eventType = parser.next();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			/**
			 * 3.把body解密后变为明文。存入返回值对象。
			 * 
			 */
			DES des = new DES();
			String body = "<body>"
					+ des.authcode(respons.getBodyDES(), "ENCODE",
							ConstValue.DES_PASSWORD) + "</body>";

			respons.setBody(body);

			/**
			 * 4.把时间截+代理商密钥+body的明文，并MD5加密，然后与服务器端的digest信息相比。
			 */
			String md5Hex = DigestUtils.md5Hex(respons.getTimestamp()
					+ ConstValue.AGENTER_PASSWORD + body);

			// 如果md5的加密码信息，与服务器端返回的加密码信息一致，则解密返回内容。
			if (md5Hex.equals(respons.getDigest())) {
				return respons;
			}

		}
		
		return null;
	}

}
