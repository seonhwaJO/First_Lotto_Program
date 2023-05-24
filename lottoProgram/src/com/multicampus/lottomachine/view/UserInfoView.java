package com.multicampus.lottomachine.view;

import com.multicampus.lottomachine.domain.UserVO;

public class UserInfoView {

	public void welcomeUser() {
		System.out.println("===================================================");
		System.out.println("\t\t로또 프로그램 입니다.");
		System.out.println("===================================================");
		System.out.println("사용자 정보를 입력해주세요.");
	}

	public void showChargingMenu(UserVO userVO) {
		System.out.println("===================================================");
		System.out.println("\t\t금액 충전 메뉴입니다.");
		System.out.println("===================================================");
		System.out.println("현재 보유금액 : "+userVO.getUserMoney());
		System.out.println("얼마를 충전하시겠습니까?");
	}

	public void showChargingResult(int totalMoney) {
		System.out.println("충전이 완료되었습니다.  현재 보유금액은 "+totalMoney);
	}
	
}
