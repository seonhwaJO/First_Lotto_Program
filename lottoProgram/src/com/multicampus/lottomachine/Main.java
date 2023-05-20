package com.multicampus.lottomachine;

import com.multicampus.lottomachine.service.ApplicationService;

public class Main {
	public static void main(String[] args) {
		ApplicationService appservice = new ApplicationService(); //appservice 객체생성
		appservice.start();  //appservice 시작
	}
}
