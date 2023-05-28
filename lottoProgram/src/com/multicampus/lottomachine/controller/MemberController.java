package com.multicampus.lottomachine.controller;

import com.multicampus.lottomachine.dto.MemberDTO;
import com.multicampus.lottomachine.exception.LoginAttemptsExceededException;
import com.multicampus.lottomachine.exception.NobalanceException;
import com.multicampus.lottomachine.service.MemberService;
import com.multicampus.lottomachine.view.MemberView;

/** 회원 Controller */
public class MemberController {
	private MemberService memberService = new MemberService(); // 회원 관련 처리를 하는 service
	private MemberView memberView = new MemberView(); // 회원 관련 처리시 console에 출력할 내용

	public void signUp(String menuDescription) { // 회원가입 처리
		memberView.displaySelectedMenu(menuDescription); // 메뉴정보를 보여줌 - 시작할 때 뭐하는지 알기위해 추가
		String nickname = memberService.inputMemberInfo(); // 사용자 값 입력하기, 완료되면 nickname 받음
		String resultInfo = nickname+"님 회원가입이 완료되었습니다. 로그인후 사용해주세요.";
		memberView.showCompletedMessage(menuDescription, resultInfo); // 완료된 가입정보를 콘솔에 출력
	}

	public MemberDTO login(String menuDescription) {// 사용자 로그인
		memberView.displaySelectedMenu(menuDescription);
		try {
			String nickname = memberService.executeLogin().getNickName();	// 사용자 입력하고 반환값에서 닉네임 가져오기
			String resultInfo = nickname+"님 로그인이 완료되었습니다. 메뉴를 사용하실 수 있습니다.";
			memberView.showCompletedMessage(menuDescription, resultInfo); // 완료된 가입정보를 콘솔에 출력
		} catch (LoginAttemptsExceededException e) {	// 로그인 시도 횟수 5번 초과, 메인 메뉴로 돌아감 
			System.out.println(e.getMessage());
		}
		return getMember() ;
	}

	public void chargeBalance(String menuDescription) {	//보유 금액 충전
		memberView.displaySelectedMenu(menuDescription); // 메뉴정보를 보여줌 
		int balance = memberService.chargeBalance();	//금액 충전 처리
		String resultInfo = balance+"원이 충전되었습니다. 메뉴로 돌아갑니다.";
		memberView.showCompletedMessage(menuDescription, resultInfo); // 완료된 가입정보를 콘솔에 출력
	}

	public void buyLottoTicket(String menuDescription) {		// 로또 구매 처리 
		try {
			memberView.displaySelectedMenu(menuDescription); // 메뉴정보를 보여줌
			int buyTicket = memberService.buyLottoTicket();
			String resultInfo = buyTicket+"개의 티켓을 구매했습니다. 메뉴로 돌아갑니다.";
			memberView.showCompletedMessage(menuDescription, resultInfo); // 완료된 가입정보를 콘솔에 출력
		}catch(NobalanceException e) {	//보유금액 없으면 메뉴로 돌아감
			System.out.println(e.getMessage());
		}
	}
	
	public boolean isLogginedIn() {	//로그인 확인
		return memberService.isLogginedIn();
	}
	
	private MemberDTO getMember() {
		return memberService.getMember();
	}
}
