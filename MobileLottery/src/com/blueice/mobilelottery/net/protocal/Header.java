package com.blueice.mobilelottery.net.protocal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlSerializer;

import com.blueice.mobilelottery.ConstValue;

/**
 * XML文件的Header部分。
 * 
 * 问题
 * 1.userName不知道从何输入。
 *
 */
public class Header {

	private List<Leaf> list = new ArrayList<Leaf>();

	private Leaf agenterid = new Leaf("agenterid");
	private Leaf source = new Leaf("source");
	private Leaf compress = new Leaf("compress");
	/**
	 * 信息识别 时间戳+6位随机数。
	 */
	private Leaf messengerid = new Leaf("messengerid");
	/**
	 * 时间戳
	 */
	private Leaf timestamp = new Leaf("timestamp");

	/**
	 * 时间+代理商密钥(常量应该放在jni中去调用。)+Body明文的加密数据。body从函数外传入。
	 */
	private Leaf digest = new Leaf("digest");

	/**
	 * 操作指命。（不定）
	 */
	private Leaf transactiontype = new Leaf("transactiontype");

	/**
	 * 用户名。（不定）
	 */
	private Leaf username = new Leaf("username");

	public void serializerHeader(XmlSerializer serializer, String body) {

		agenterid.setTagValue("889931");
		list.add(agenterid);

		source.setTagValue("ivr");
		list.add(source);

		compress.setTagValue("DES");
		list.add(compress);

		// 生成时间戳
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(new Date());
		// 生成随机数。
		Random random = new Random();
		int num = random.nextInt(999999) + 1;
		// 对随机数不满六位在前面加0。
		DecimalFormat decimal = new DecimalFormat("000000");
		String strRandomNum = decimal.format(num);
		messengerid.setTagValue(time + strRandomNum);
		list.add(messengerid);

		timestamp.setTagValue(time);
		list.add(timestamp);

		String orgInfo = time + ConstValue.AGENTER_PASSWORD + body;
		digest.setTagValue(DigestUtils.md5Hex(orgInfo));
		list.add(digest);

		/**
		 * 这个值是在message.getXml()方法只从element中得到的。
		 */
		list.add(transactiontype);
		
		/**
		 * 登陆的请求自行设置。
		 */
		list.add(username);
		
		
		try {

			serializer.startTag(null, "header");
			/**
			 * 循环生成header中的元素
			 */
			for (Leaf leaf : list) {
				leaf.serializerLeaf(serializer);
			}
			
			serializer.endTag(null, "header");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Leaf getTransactiontype() {
		return transactiontype;
	}

	public Leaf getUsername() {
		return username;
	}

}
