package com.blueice.mobilelottery.bean;

/**
 * 
 * 投注信息封装类。
 *
 */
public class Ticket {
	private String redNum; //红球号码。
	private String blueNum;//蓝球号码。
	
	private long num; //注数。

	public String getRedNum() {
		return redNum;
	}

	public void setRedNum(String redNum) {
		this.redNum = redNum;
	}

	public String getBlueNum() {
		return blueNum;
	}

	public void setBlueNum(String blueNum) {
		this.blueNum = blueNum;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long l) {
		this.num = l;
	}
	
	
}
