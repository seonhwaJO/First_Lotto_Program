package com.multicampus.lottomachine.service;

import java.util.Scanner;

import com.multicampus.lottomachine.domain.MenuType;
import com.multicampus.lottomachine.view.ApplicationView;

public class ApplicationService {
	private ApplicationView appView = new ApplicationView();
	public void start() {
		appView.welcomeMessage();
		try (Scanner scanner = new Scanner(System.in)) { // 메뉴 입력받을 변수, 간단한 입력이므로 scanner 사용
			while (true) {
				appView.showMenu();
				int menuNum = scanner.nextInt();
				MenuType menuType = MenuType.getMenuTypeByCode(menuNum); // 사용자에게 입력받은 메뉴값으로 메뉴 선택
				try {
					switch (menuType) {
					case SIGN_UP: // 회원가입 처리
						break;
					case LOG_IN: // 로그인 처리
//						memberController.login();
						break;
					case DEPOSIT: // 금액 충전 처리
						break;
					case BUY_LOTTO: // 로또 구매 처리
						break;
					case LOTTO_GAME: // 로또 게임 처리
						break;
					case MEMBER_GAME_RESULT:	// 사용자 게임 정보 확인
						break;
					case EXIT: // 프로그램 종료
						return;
					default:	//잘못된 메뉴 입력 시
						throw new NumberFormatException("없는 메뉴입니다.");
					}// switch end
				} catch (NumberFormatException e) { // 에러 처리 : 메뉴 잘못 처리
					System.out.println(e.getMessage());
				} // catch close
			}
		}
	}
}
