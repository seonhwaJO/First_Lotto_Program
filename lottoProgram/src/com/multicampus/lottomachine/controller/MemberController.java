package com.multicampus.lottomachine.controller;

import com.multicampus.lottomachine.exception.LoginAttemptsExceededException;
import com.multicampus.lottomachine.service.MemberService;
import com.multicampus.lottomachine.view.MemberView;

/** 회원 Controller */
public class MemberController {
	private MemberService memberService = new MemberService(); // 회원 관련 처리를 하는 service
	private MemberView memberView = new MemberView(); // 회원 관련 처리시 console에 출력할 내용

	public void signUp(String menuDescription) { // 회원가입 처리
		memberView.displaySelectedMenu(menuDescription); // 메뉴정보를 보여줌 - 시작할 때 뭐하는지 알기위해 추가
		memberService.inputMemberInfo(); // 사용자 값 입력하기
		String resultInfo = memberService.getResultInfo(); // 사용자 정보 가져오기
		memberView.showCompletedMessage(menuDescription, resultInfo); // 완료된 가입정보를 콘솔에 출력
		memberService.setMemberNull(); // 회원가입 후 사용자값을 null처리(회원가입 후 로그인 해야 사용 가능)
	}

	// TODO 3단계에서는 DB 사용
	public void login(String menuDescription) {// 사용자 로그인
		memberView.displaySelectedMenu(menuDescription);
		try {
			memberService.executeLogin();
			String resultInfo = memberService.getResultInfo(); // 사용자 정보 가져오기
			memberView.showCompletedMessage(menuDescription, resultInfo); // 완료된 가입정보를 콘솔에 출력
		} catch (LoginAttemptsExceededException e) {	// 로그인 시도 횟수 5번 초과, 메인 메뉴로 돌아감 
			System.out.println(e.getMessage());
		}
	}

	public boolean isLogginedIn() {
		return memberService.isLogginedIn();
	}
}
