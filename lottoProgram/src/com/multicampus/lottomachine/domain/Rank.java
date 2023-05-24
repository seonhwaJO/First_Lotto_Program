package com.multicampus.lottomachine.domain;

/** 사용자 메뉴에 사용할 메뉴타입*/

public enum Rank {
	FIRST_PLACE("1등",2000000000,0.00000012),
	SECOND_PLACE("2등",30000000,0.00000073),
	THIRD_PLACE("3등",1500000,0.000028),
	FOURTH_PLACE("4등",50000,0.0014),
	FIFTH_PLACE("5등",5000,0.022),
	LOSER("낙첨",0,0);
	
	private String description;
	private int prize;
	double probability;
	
	Rank(String description, int prize,double probability){
		this.description = description;
		this.prize = prize;
		this.probability = probability;
	}
	
	public int getprize() {
		return prize;
	}
	public String getDescription() {
		return description; 
	}
}
