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
	private Leaf lotteryid = new Leaf("lotteryid");
	
	/**
	 * 第几期彩票。
	 */
	private Leaf issues = new Leaf("issues","1");
	
	
	
	//*************************服务器返回的数据*****************************

	private String ISSUE;
	
	private String lasttime;
	
	public String getISSUE() {
		return ISSUE;
	}


	public void setISSUE(String iSSUE) {
		ISSUE = iSSUE;
	}




	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	
	//********************************************************************
	
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
		return "12002";
	}
	
	public Leaf getLotteryid() {
		return lotteryid;
	}
	
	

}
