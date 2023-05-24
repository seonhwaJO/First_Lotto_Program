package com.multicampus.lottomachine.domain;

/** 회원정보 객체*/
public class MemberVO implements InsertData{
	private String userID; // 회원 아이디
	private String password;	//회원 패스워드
	private String userName;//사용자 이름
	private int balance;	// 보유금액
	private int purchaseLottoTicket;	//구매한 로또 티켓 - 회원가입 시 0
	private int purchaseAmount;	//구매금액
	private int receivedAmount;	//수령금액

	public MemberVO() {}	//기본 생성자
	public MemberVO(String userID, String password, String userName,int balance) {
		this.userID = userID;
		this.password = password;
		this.userName = userName;
		this.balance = balance;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
	public int getPurchaseLottoTicket() {
		return purchaseLottoTicket;
	}
	public void setPurchaseLottoTicket(int purchaseLottoTicket) {
		this.purchaseLottoTicket = purchaseLottoTicket;
	}
	public String getResultDescription() {	// toString으로 하려다가 패스워드 빼야 해서 새로 정의함
		if(userID == null || userName == null) {
			throw new NullPointerException("추가한 사용자가 없습니다");
		}
		return "회원 아이디 : "+userID+" 회원 이름 : "+userName+" 보유금액 : "+balance+"원 구매로또개수 : "+purchaseLottoTicket;
	}
	public boolean isEmpty() {	// 사용자가 로그인 했는지 확인하는 메서드
		return userID == null || userID.isEmpty();	//ID 확인해서 없으면 False
	}
	public int getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(int purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public int getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(int receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
}
