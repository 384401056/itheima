package com.blueice.mobilelottery.net.protocal;

import org.xmlpull.v1.XmlSerializer;

/**
 * Body数据中的节点。
 *
 */
public class Element {

	/*
	 * 彩票种类编号。此值不确定要从外部输入。
	 */
	private Leaf lotteryid = new Leaf("lotteryid");
	
	/**
	 * 第几期。
	 */
	private Leaf issues = new Leaf("issues");

	
	public Leaf getLotteryid() {
		return lotteryid;
	}
	
	
	public void serializerElement(XmlSerializer serializer){
		issues.setTagValue("1");
		try {
			
			serializer.startTag(null,"element");
			lotteryid.serializerLeaf(serializer);
			issues.serializerLeaf(serializer);
			serializer.endTag(null, "element");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
