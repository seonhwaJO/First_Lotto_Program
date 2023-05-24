package com.multicampus.lottomachine.controller;

import java.io.IOException;

import com.multicampus.lottomachine.domain.UserVO;
import com.multicampus.lottomachine.exception.NumberOutOfBoundException;
import com.multicampus.lottomachine.service.LottoMachineService;
import com.multicampus.lottomachine.view.LottoMachineView;

// 로또 프로그램 컨트롤러 : 프로그램의 분기를 처리 
public class LottoMachineController {
	private LottoMachineService lottoService = new LottoMachineService();	// 로또 서비스 처리 객체
	private LottoMachineView lottoView = new LottoMachineView(); //로또 뷰 처리 객체
	public void showProgramMenu(UserVO userVo) {	//로또 프로그램 시작 메뉴 보여줌
		lottoView.showProgramMenu(userVo);
	}

	public int setCustomNumberList(UserVO userVO) throws IOException{	//사용자 입력값을 셋팅하고 게임을 진행할 것인지 물음
		int usingPaper=0;
		while(true) {
			try {
			lottoView.showUsingPaper(userVO.getLottoPaper());	//몇장 쓸건지 물어봄
			usingPaper=lottoService.setUsingPaper(userVO);
			break;
			}catch(NumberOutOfBoundException e) {
				System.out.println(e.getMessage());
			}
		}
		for(int i=0;i<usingPaper;i++) {//입력값 반복
			try {
				lottoView.showCustomerInputMessage(i+1);
				lottoService.setCustomList(); 							//사용자에게 받은 입력값을 객체에 넣을 메서드
			}catch(NumberOutOfBoundException e) {
				System.out.println(e.getMessage());
			}
		}
		lottoView.showCustomerSetting(lottoService.getCustomArrayList()); 		//사용자 입력값을 다시 한번 보여줌
		lottoView.showStartGameMenu();				//로또 게임을 진행할 것인지 묻는 메서드
		return usingPaper;
	}
	
	public boolean isRunningGame() throws IOException{	//사용자의 게임 진행 여부를 받아 true/false로 반환 
		return lottoService.isRunnningGames();
	}
	
	public void startLottoGame() {	//게임 시작을 선택하면 로또 번호를 생성하고 결과값을 저장
		lottoService.checkLottoNumber();	//로또 당첨 번호 생성, 당첨번호 저장
		//TODO 당첨내역 ArrayLIST로 변경
		lottoView.showWinningResult(lottoService.getWinningList(), lottoService.getResultIntNumber(),
				lottoService.getResultList());	//로또 당첨 번호 및 내 당첨 번호 보여주기
	}
	
	public void showStaticsLottoWinning() {		// 사용자 당첨 정보 보여주기
		lottoView.showStaticsWinning(lottoService.getWinningStaticsInfo());					//뷰에 당첨정보 전달해서 출력
	}

	public void buyLotto(UserVO userInfo) throws IOException{	//로또를 사는 메서드 
		while(true) {
			try {
			lottoView.showBuyMenu(userInfo);
			lottoService.buyLottoPaper(userInfo);
			break;
			} catch (NumberOutOfBoundException e) {
				System.out.println(e.getMessage());
			}catch (NumberFormatException e) {
				System.out.println("숫자만 입력 가능합니다.");
			}
		}
		lottoView.showNumberPurchases(userInfo.getLottoPaper(),userInfo.getUserMoney());
	}
}
