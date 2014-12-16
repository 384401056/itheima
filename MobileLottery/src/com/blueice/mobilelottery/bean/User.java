package com.blueice.mobilelottery.bean;

public class User {

	private String userName;
	private String passWord;
	
	private float money;
	

	public void setMoney(float money) {
		this.money = money;
	}
	public float getMoney() {
		return money;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
	
}
