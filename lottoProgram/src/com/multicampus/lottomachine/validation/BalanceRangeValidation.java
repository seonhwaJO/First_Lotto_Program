package com.multicampus.lottomachine.validation;

import com.multicampus.lottomachine.exception.OutOfRangeException;

public class BalanceRangeValidation implements Validation{
	int inputBalance;
	
	@Override
	public void validate(String input) throws OutOfRangeException,NumberFormatException {
		inputBalance = Integer.parseInt(input);
		if(inputBalance <1000) {
			throw new OutOfRangeException("[경고]1000원 이상 입력하셔야 합니다.");
		}
	}
}
