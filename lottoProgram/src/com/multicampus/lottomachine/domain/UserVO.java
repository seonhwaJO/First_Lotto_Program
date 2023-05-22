package com.multicampus.lottomachine.domain;

public class UserVO {
	private String userID;
	private String userName;
	private int userMoney;
	private int lottoPaper;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserMoney() {
		return userMoney;
	}
	public void setUserMoney(int userMoney) {
		this.userMoney = userMoney;
	}
	public int getLottoPaper() {
		return lottoPaper;
	}
	public void setLottoPaper(int lottoPaper) {
		this.lottoPaper = lottoPaper;
	}
	public void chargingMoney(int chargingMoney) {
		this.userMoney+=chargingMoney;
		
	}
}
