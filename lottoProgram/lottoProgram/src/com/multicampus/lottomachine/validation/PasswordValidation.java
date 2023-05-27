package com.multicampus.lottomachine.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.multicampus.lottomachine.exception.OutOfRangeException;

public class PasswordValidation implements Validation{
	private static final String PASSWORD_PATTERN="^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$"; // 영문, 숫자, 특수문자
	private Matcher match;
	@Override
	public void validate(String input) throws OutOfRangeException {
		match = Pattern.compile(PASSWORD_PATTERN).matcher(input);
		if(!match.find()){
			throw new OutOfRangeException("비밀번호는 8자리 이상, 숫자, 특수문자, 영문을 조합해주세요.");
		}
	}
}
