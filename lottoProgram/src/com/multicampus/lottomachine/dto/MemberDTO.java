package com.multicampus.lottomachine.dto;

//회원정보 DTO
public class MemberDTO {
	private String id;	//회원 아이디
	private String password;	//회원패스워드
	private String name;		//회원 이름
	private String nickName;		//회원닉네임
	int balance;	//회원 보유금액
	int ticket;	//회원 보유 티켓
	public MemberDTO() {}	//sql 입력을 위해
	public MemberDTO(String id, String name, String password, String nickName) {	//회원가입을 위해
		this.id = id;
		this.name = nickName;
		this.password = password;
		this.nickName=nickName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getTicket() {
		return ticket;
	}
	public void setTicket(int ticket) {
		this.ticket = ticket;
	}
	public boolean isEmpty() {	// 사용자가 로그인 했는지 확인하는 메서드
		return id == null || id.isEmpty();	//ID 확인해서 없으면 False
	}
}
