package com.multicampus.lottomachine.view;

/** 메뉴에 공통적으로 사용할 내용 */
public class MenuView {
	public void displaySelectedMenu(String menuDescription) { // 선택한 메뉴를 보여주는 메서드
		System.out.println("===================================================");
		System.out.println("\t\t\t" + menuDescription + " 메뉴 입니다.");
		System.out.println("===================================================");
	}

	public void showCompletedMessage(String message) {
		//값을 넣은 객체의 결과값을 출력
		showCompletedMessage(message, null);
	}

	public void showCompletedMessage(String message, String resultDescription) {
		//값을 넣은 객체의 결과값을 출력 
		// 큰쪽에서 값이 없을 때 조건 처리하는 것이 관리하기 더 용이 하다고 함.
		System.out.println("===================================================");
		System.out.println(message + "(이/가) 완료되었습니다.");
		if (resultDescription != null) {	//사용자 결과 처 설명이 없으면 출력하지 않음
			System.out.println("===================================================");	// 미관상
			System.out.println(resultDescription);
		}
	}
}
