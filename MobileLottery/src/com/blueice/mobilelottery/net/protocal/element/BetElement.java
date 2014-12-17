package com.blueice.mobilelottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.blueice.mobilelottery.net.protocal.Element;
import com.blueice.mobilelottery.net.protocal.Leaf;

public class BetElement extends Element {

	// lotteryid string * 玩法编号
	private Leaf lotteryid = new Leaf("lotteryid");
	// issue string * 期号（当前销售期）
	// lotterycode string * 投注号码，注与注之间^分割
	private Leaf lotterycode = new Leaf("lotterycode");

	// issue string * 期号（当前销售期）
	private Leaf issue = new Leaf("issue");

	// lotterynumber string 注数
	private Leaf lotterynumber = new Leaf("lotterynumber");
	// lotteryvalue string 方案金额，以分为单位
	private Leaf lotteryvalue = new Leaf("lotteryvalue");
	// appnumbers string 倍数
	private Leaf appnumbers = new Leaf("appnumbers");
	// issuesnumbers string 追期
	private Leaf issuesnumbers = new Leaf("issuesnumbers");
	// issueflag int * 是否多期追号 0否，1多期
	private Leaf issueflag = new Leaf("issueflag");

	private Leaf bonusstop = new Leaf("bonusstop", "1");

	/*****************服务器端的返回******************/
	// actvalue int * 用户账户余额
	private String actvalue;


	public String getActvalue() {
		return actvalue;
	}

	public void setActvalue(String actvalue) {
		this.actvalue = actvalue;
	}

	/*********************************************/

	@Override
	public void serializerElement(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "element");
			lotteryid.serializerLeaf(serializer);
			issue.serializerLeaf(serializer);
			lotteryvalue.serializerLeaf(serializer);
			lotterynumber.serializerLeaf(serializer);
			appnumbers.serializerLeaf(serializer);
			issuesnumbers.serializerLeaf(serializer);
			lotterycode.serializerLeaf(serializer);
			issueflag.serializerLeaf(serializer);
			bonusstop.serializerLeaf(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		return "12006";
	}

	public Leaf getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Leaf lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Leaf getLotterycode() {
		return lotterycode;
	}

	public void setLotterycode(Leaf lotterycode) {
		this.lotterycode = lotterycode;
	}

	public Leaf getIssue() {
		return issue;
	}

	public void setIssue(Leaf issue) {
		this.issue = issue;
	}

	public Leaf getLotterynumber() {
		return lotterynumber;
	}

	public void setLotterynumber(Leaf lotterynumber) {
		this.lotterynumber = lotterynumber;
	}

	public Leaf getLotteryvalue() {
		return lotteryvalue;
	}

	public void setLotteryvalue(Leaf lotteryvalue) {
		this.lotteryvalue = lotteryvalue;
	}

	public Leaf getAppnumbers() {
		return appnumbers;
	}

	public void setAppnumbers(Leaf appnumbers) {
		this.appnumbers = appnumbers;
	}

	public Leaf getIssuesnumbers() {
		return issuesnumbers;
	}

	public void setIssuesnumbers(Leaf issuesnumbers) {
		this.issuesnumbers = issuesnumbers;
	}

	public Leaf getIssueflag() {
		return issueflag;
	}

	public void setIssueflag(Leaf issueflag) {
		this.issueflag = issueflag;
	}

	public Leaf getBonusstop() {
		return bonusstop;
	}

	public void setBonusstop(Leaf bonusstop) {
		this.bonusstop = bonusstop;
	}
	
	

}
