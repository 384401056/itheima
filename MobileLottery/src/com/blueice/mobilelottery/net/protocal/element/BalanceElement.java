package com.blueice.mobilelottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.blueice.mobilelottery.net.protocal.Element;

public class BalanceElement extends Element {

	//*******************服务器端的回复****************
	/**
	 *   accounvalues string * 账户金额
         investvalues   可投注金额
         cashvalues   可提现金额
	 */
	
	private String investvalues;
	private String accounvalues;
	
	//***********************************************
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		//请求无内容
	}

	


	@Override
	public String getTransactionType() {
		return "11007";
	}

	public String getInvestvalues() {
		return investvalues;
	}


	public String getAccounvalues() {
		return accounvalues;
	}
	
	public void setInvestvalues(String investvalues) {
		this.investvalues = investvalues;
	}

	public void setAccounvalues(String accounvalues) {
		this.accounvalues = accounvalues;
	}


}
