package com.blueice.mobilelottery.net.protocal;

import org.xmlpull.v1.XmlSerializer;

/**
 * Body数据中的请求节点父类。
 *
 */
public abstract class Element {




	/**
	 * 序列化。
	 * @param serializer XmlSerializer对象。
	 */
	public abstract void serializerElement(XmlSerializer serializer);
	
	/**
	 * 获取请求的标识。
	 * @return 
	 */
	public abstract String getTransactionType();
	
	
//	/**
//	 * 序列化。
//	 * @param serializer XmlSerializer对象。
//	 */
//	public void serializerElement(XmlSerializer serializer){
//		issues.setTagValue("1");
//		try {
//			
//			serializer.startTag(null,"element");
//			lotteryid.serializerLeaf(serializer);
//			issues.serializerLeaf(serializer);
//			serializer.endTag(null, "element");
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
//	/**
//	 * 获取请求的标识。
//	 * @return 
//	 */
//	public String getTransactionType(){
//		
//		return "1202";
//		
//	}
	
	
}












