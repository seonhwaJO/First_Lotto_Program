package com.multicampus.lottomachine.controller;

import java.io.IOException;

import com.multicampus.lottomachine.domain.UserVO;
import com.multicampus.lottomachine.exception.NumberOutOfBoundException;
import com.multicampus.lottomachine.service.UserService;
import com.multicampus.lottomachine.view.UserInfoView;

public class UserController {
	private UserService userService = new UserService();
	private UserInfoView userView = new UserInfoView();
	public UserVO setUserInfo() throws IOException {
		userView.welcomeUser();
		userService.setUserVO();
		return userService.getUserVO();
	}
	public UserVO getUserInfo() {
		return userService.getUserVO();
	}
	public void chargingMoney(UserVO userVO) throws IOException{
		while(true) {
			try {
				userView.showChargingMenu(userVO);
				int totalMoney = userService.chargingUserMoney();
				userView.showChargingResult(totalMoney);
				break;
			}catch(NumberOutOfBoundException e) {
				System.out.println(e.getMessage());
			}catch(NumberFormatException e) {
				System.out.println("잘못입력하셨습니다. 숫자만 입력해주세요");
			}
		}
	}
	public void setGameUser(int usingPaper) {
		userService.setGameUser(usingPaper);
	}
	
}
