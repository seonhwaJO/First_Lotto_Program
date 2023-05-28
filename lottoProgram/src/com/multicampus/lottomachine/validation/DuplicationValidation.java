package com.multicampus.lottomachine.validation;

import com.multicampus.lottomachine.exception.DuplicationException;
import com.multicampus.lottomachine.exception.OutOfRangeException;

public class DuplicationValidation implements Validation{
	private String fieldName;
	private boolean check;
	public DuplicationValidation(String input) {
		
	}
	public DuplicationValidation(String fieldName, boolean check){	//필드이름(중복검사용), 체크값(중복검사), 입력값(널 검사용)	
		this.fieldName = fieldName;
		this.check = check;
	}
	@Override
	public void validate(String input) throws OutOfRangeException {	//중복 여부 확인
		if(check) {
			throw new DuplicationException("[경고]이미 있는 "+fieldName+"입니다.");
		}
	}
}
