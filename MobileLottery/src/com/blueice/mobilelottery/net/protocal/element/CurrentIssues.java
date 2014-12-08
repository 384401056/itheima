package com.blueice.mobilelottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.blueice.mobilelottery.net.protocal.Element;
import com.blueice.mobilelottery.net.protocal.Leaf;

/**
 * 获取三色球当前期的数据
 *
 */
public class CurrentIssues extends Element {

	/*
	 * 彩票种类。
	 */
	private Leaf lotteryid = new Leaf("lotteryid","118");
	
	/**
	 * 第几期彩票。
	 */
	private Leaf issues = new Leaf("issues","1");
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		try {
			
			serializer.startTag(null,"element");
			lotteryid.serializerLeaf(serializer);
			issues.serializerLeaf(serializer);
			serializer.endTag(null, "element");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		return "1201";
	}

}
