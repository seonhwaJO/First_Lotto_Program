package com.multicampus.lottomachine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.multicampus.lottomachine.domain.UserVO;
import com.multicampus.lottomachine.exception.NumberOutOfBoundException;

public class UserService {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private UserVO userVO;	//사용자 정보를 입력할 객체
	public void setUserVO() throws IOException {
		userVO = new UserVO();
		System.out.print("사용자 이름 : ");
		userVO.setUserName(br.readLine());
		System.out.print("사용자 아이디 : "); 
		userVO.setUserID(br.readLine());
		while(true) {
			System.out.print("보유금액(최소 1000원) : ");
			int money = Integer.parseInt(br.readLine());			//숫자 아니면 에러
			if(money <1000) {
				System.out.println("금액이 너무 적습니다.");
				continue;
			}else {
				userVO.setUserMoney(money);
			}
			break;
		}
	}
	public UserVO getUserVO() {
		if(userVO == null)
			throw new NullPointerException("사용자 정보가 없습니다.");
		return this.userVO;
	}
	public int chargingUserMoney() throws NumberFormatException, NumberOutOfBoundException,IOException {
		int chargingMoney=0;
		chargingMoney	= Integer.parseInt(br.readLine());
		if(chargingMoney<1000) {
			throw new NumberOutOfBoundException("충전은 1000원 이상 가능합니다.");
		}
		userVO.chargingMoney(chargingMoney);
		return userVO.getUserMoney();
	}
	public void setGameUser(int usingPaper) {
		int userPaper = userVO.getLottoPaper()-usingPaper;
		userVO.setLottoPaper(userPaper);		
	}

}
