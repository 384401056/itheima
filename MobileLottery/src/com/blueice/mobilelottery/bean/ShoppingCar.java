package com.blueice.mobilelottery.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 购物车信息封装类。
 *
 */
public class ShoppingCar extends Observable {

	//*************************单例模式***********************************************
	private static ShoppingCar instance = new ShoppingCar(); 
	
	private  ShoppingCar() {
		super();
	}
	
	public static ShoppingCar getInstance() {
		if(instance==null){
			instance =  new ShoppingCar();
		}
		
		return instance;
		
	}
	
	//***********************************************************************
	
	
	/**
	 * 与投注相关的信息。
	 * 投注：
		lotteryid string * 玩法编号
		issue string * 期号（当前销售期）
		lotterycode string * 投注号码，注与注之间^分割
		lotterynumber string  注数
		lotteryvalue string  方案金额，以分为单位
		
		appnumbers string  倍数
		issuesnumbers string  追期
		issueflag int * 是否多期追号 0否，1多期
		bonusstop int * 中奖后是否停止：0不停，1停
	 * 
	 */
	




	/**
	 * 玩法编号
	 */
	private int lotteryid;
	
	private String issue;
	private Long lotterynumber;//注数,由计算得来，所以不能有set方法。
	private Long lotteryvalue;//金额。由计算得来，不能有set方法。
	
	//为了保证一个购物车只有一种彩种的集合，所以先new出来，也不能有set方法。
	private List<Ticket> tickets = new ArrayList<Ticket>(); 
	
	
	
	
	
	/**
	 * 得到总的注数信息。
	 * @return Long类
	 */
	public Long getLotterynumber() {
		
		lotterynumber = (long) 0;
		for(Ticket item:tickets){
			
			lotterynumber+=item.getNum();
		}
		return lotterynumber;
	}
	
	/**
	 * 得到总的投注金额。
	 * @return 金额.
	 */
	public Long getLotteryvalue() {
		
		lotteryvalue = 2*getLotterynumber();
		
		return lotteryvalue;
	}
	
	public int getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(int ssq) {
		this.lotteryid = ssq;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public List<Ticket> getTickets() {
		return tickets;
	}
	
	
}
