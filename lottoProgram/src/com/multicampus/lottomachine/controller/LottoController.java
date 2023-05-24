package com.multicampus.lottomachine.controller;

import com.multicampus.lottomachine.domain.MemberVO;
import com.multicampus.lottomachine.exception.NoTicketException;
import com.multicampus.lottomachine.service.LottoService;
import com.multicampus.lottomachine.view.LottoView;

public class LottoController {
	private LottoService lottoService = new LottoService();	//로또 게임 관련 처리를 하는 서비스
	private LottoView lottoView = new LottoView();	//로또 게임 뷰를 처리
	private MemberVO memberVO;
	
	public LottoController(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	
	public void runLottoGame(String menuDescription) {	//로또 게임 
		try {
			if(memberVO.getPurchaseLottoTicket()==0) 		//구매한 티켓이 없으면 게임을 할 수 없음 
				throw new NoTicketException("티켓이 없어서 게임을 진행할 수 없습니다. 전체 메뉴로 돌아갑니다."); 
			lottoView.displaySelectedMenu(menuDescription,memberVO.getUserName());	// 메뉴 보여줌
			
			lottoService.LottoTicketPurchaseService(this.memberVO);	//사용할 로또 입력
			String resultInfo = lottoService.getResultDescription();
			
			lottoView.showCompletedMessage(menuDescription, resultInfo);// 사용자 입력 로또값 보여주기
			startLottoGame();
		}catch(NullPointerException e){
			System.out.println(e.getMessage());
		}catch(NoTicketException e) {
			System.out.println(e.getMessage());
		}
	} 
	public void startLottoGame() {	// 로또게임 시작함
		lottoService.LottoGamePlayService();	//로또게임을 할지 물어보고 로또 게임을 함.
		lottoView.showResultWinningStatics(lottoService.getWinningStaticsList());
	}
	public void showAllGamestatics() {
		try {
			lottoView.showAllGamesStatics(lottoService.getWinningStaticsList(),memberVO);
		}catch(NullPointerException e){
			System.out.println(e.getMessage());
		}
	}
}
