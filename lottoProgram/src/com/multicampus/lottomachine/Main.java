package com.multicampus.lottomachine;

import com.multicampus.lottomachine.service.ApplicationService;

public class Main {
	public static void main(String[] args) {
		ApplicationService appService = new ApplicationService(); //appservice 객체생성
		appService.start();  //appservice 시작
	}
}
