package com.multicampus.lottomachine.domain;

/** 사용자 메뉴에 사용할 메뉴타입*/

// Question 
public enum MenuType {
    SIGN_UP(1,"회원가입",false),
    LOG_IN(2,"로그인",false),
    DEPOSIT(3,"금액충전",true),
    BUY_LOTTO(4,"로또구매",true),
    LOTTO_GAME(5,"로또게임",true),
    MEMBER_GAME_RESULT(6,"사용자 당첨 내역",true),
    EXIT(7,"종료",false);
	
	private int code;
	private String description;
	private boolean loginRequired;
	
	MenuType(int code, String description, boolean loginRequired){
		this.code = code;
		this.description = description;
		this.loginRequired = loginRequired;
	}
	
	public int getCode() {
		return code;
	}
	public String getDescription() {
		return description; 
	}
	
	public boolean isLoginRequired() {
		return loginRequired;
	}
	
	public static MenuType getMenuTypeByCode(int code) {	//메뉴 번호로 메뉴타입 고르기
		for(MenuType menu: MenuType.values()) {
			if(menu.getCode() == code)
					return menu;
		}
		return null;
	}
}
