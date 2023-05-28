package com.multicampus.lottomachine.validation;

import com.multicampus.lottomachine.exception.NobalanceException;
import com.multicampus.lottomachine.exception.OutOfRangeException;

public class TicketRangeValidation implements Validation{
	int balance;
	int inputTicket;
	
	public TicketRangeValidation(int balance) {
		this.balance= balance;
		if(balance<=1000) {
			throw new NobalanceException("[경고]보유금액이 부족합니다. 전체메뉴로 돌아갑니다.");
		}
	}
	
	@Override
	public void validate(String input) throws OutOfRangeException,NumberFormatException {
		inputTicket = Integer.parseInt(input);
		if(balance<=inputTicket*1000) {
			throw new OutOfRangeException("[경고]보유한 금액 이상으로 티켓을 살 수 없습니다.");
		}
		if(inputTicket <=0) {
			throw new OutOfRangeException("[경고]1장 이상 입력하셔야 합니다.");
		}
	}
}
