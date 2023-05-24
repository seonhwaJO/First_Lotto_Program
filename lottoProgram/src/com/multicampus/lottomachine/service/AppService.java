package com.multicampus.lottomachine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.multicampus.lottomachine.controller.LottoMachineController;
import com.multicampus.lottomachine.controller.UserController;
import com.multicampus.lottomachine.domain.UserVO;
import com.multicampus.lottomachine.exception.DuplicationNubersException;
import com.multicampus.lottomachine.exception.NumberOutOfBoundException;

/** 로또머신 프로그램 */
public class AppService {
	private LottoMachineController lottoController = new LottoMachineController();				//로또머신 컨트롤러
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	//메뉴 입력을 위한 입력변수
	private UserController userController = new UserController();
	private enum MenuType{ 	//로또 선택 메뉴																															
		LOTTO,STATICS,BUY,CHARGING		//Question enum에서 한글자는 안되는 것 같다.
	}
	private UserVO userVO = new UserVO();	//생성된 유저를 담음
	public void start() {		//로또 머신 프로그램 시작부분 : 종료부 구현, 메뉴 분기
		//TODO 2단계 : 사용자 정보를 받을 예정(사용자 아이디, 게임머니 입력) - 통계 앞부분에 아이디 보여주면서 .. 
		try {
		userVO = userController.setUserInfo();	//사용자 정보 추가
		while (true) {	//TODO 여기 쪼개기
				/** 메뉴를 보여주고 사용자입력을 받아 사용자 값을 셋팅 */
				lottoController.showProgramMenu(userVO);	//메뉴를 보여주기
				String selectedMenu = br.readLine();		// 이 메뉴는 종료값이라는 로또와 관계없는 값을 받아서 여기서 처리함
				if(selectedMenu.toLowerCase().equals("q")||selectedMenu.toLowerCase().equals("quit")) {	break; } // 종료를 원하면 프로그램 끄기
				
				MenuType menuType=MenuType.valueOf(selectedMenu.toUpperCase());	//종료가 아니라면 메뉴타입 저장, 여기서 없는 문자는 에러를 발생시킴
				switch(menuType) {
					case CHARGING:	//금액 충전
						userController.chargingMoney(userVO);
						break;
					case BUY:	//로또 구매
						if(userVO.getUserMoney()<1000) {	//돈이 부족한지 검사
							System.out.println("돈이 부족합니다. 충전해주세요");
							continue;
						}
						lottoController.buyLotto(userVO);
						break;
					case LOTTO:	//로또 게임을 원하는 경우
						if(userVO.getLottoPaper()==0) {
							System.out.println("보유하신 로또가 없습니다. 구매해주세요");
							continue;
						}
						int UsingPaper = lottoController.setCustomNumberList(userVO);		//사용자 값 셋팅 후 게임 여부 묻기
						//TODO yes/ no 안쓰면 다시 입력 메뉴로 감 - 못가게 변경
						if(lottoController.isRunningGame()) {			//게임 여부 확인
							userController.setGameUser(UsingPaper);
							lottoController.startLottoGame();				//게임을 계속 진행하면 당첨번호를 생성하고 결과값을 저장
						}else {
							continue;															//안하면 원래 메뉴로 가기
						}	
						break;
					case STATICS:
						lottoController.showStaticsLottoWinning();	//게임 통계 보여주기
						break;
				}
			}
		} catch (NumberOutOfBoundException e) { //입력값이 범위에 맞지 않을 때 에러
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {				//숫자가 아닌 값을 입력했을 때 에러
			System.out.println(e.getMessage());
		} catch (DuplicationNubersException e) { 		//중복값을 넣었을 때 에러
			System.out.println(e.getMessage());
		} catch (NullPointerException e) { 						// 사용자값이 Null인데 get요청 했을 때 에러 
			System.out.println(e.getMessage());
		} catch(IllegalArgumentException e) {				// enum에 없는 메뉴 선택 했을 때 에러
			System.out.println("잘못 입력하셨습니다.");
		}catch (IOException e) {										// 입력 에러
			System.out.println(e.getMessage());
		}
	}
	
	
}