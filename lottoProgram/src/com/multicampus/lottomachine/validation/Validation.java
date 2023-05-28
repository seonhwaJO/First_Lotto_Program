package com.multicampus.lottomachine.validation;

import com.multicampus.lottomachine.exception.OutOfRangeException;

public interface Validation {	// 각 값들을 검증할 클래스
	public void validate(String input) throws OutOfRangeException;	// 해당 값과 일치하지 않으면 예외처리
	
	static String checkNullValue(String input) {	// 객체 생성 없이 쓸 때 
		if (input.isEmpty()) {
			throw new NullPointerException(); // null값이면 에러처리
		}
		return input;
	}
	
	default void checkValue() {	// 객체 생성하고 쓸 때
		System.out.println("[경고]interface의 default");
	}
}
