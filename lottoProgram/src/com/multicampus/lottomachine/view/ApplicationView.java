package com.multicampus.lottomachine.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//전체 프로그램(application) View
public class ApplicationView implements errorView{

	public void welcomeMessage() {	//프로그램 시작 메세지 
			System.out.println("반갑습니다. 로또프로그램입니다.");
			LocalDateTime currentDateTime = LocalDateTime.now();    // 현재 날짜와 시간 가져오기 
			String dateTimeString = currentDateTime.format
					(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일  HH:mm:ss"));
			System.out.println("현재 날짜와 시간 : "+dateTimeString);	// 현재 날짜와 시간을 알려줌
			
	}
	
	public void showMenu() {	// 프로그램 메뉴
		System.out.println("===================================================");
		System.out.println("\t\t로또 프로그램 메뉴 입니다.");
		System.out.println("===================================================");
		System.out.println("1. 회원가입 2. 로그인  3. 금액 충전 4. 로또 구매  5. 로또 게임   6.사용자 게임 정보 확인	7. 종료");
		System.out.print("원하는 메뉴를 골라주세요 : ");
	}

	@Override
	public void accessErrorView() {
			System.out.println("로그인이 필요합니다. 로그인 메뉴로 이동합니다.");
	}
	
}
