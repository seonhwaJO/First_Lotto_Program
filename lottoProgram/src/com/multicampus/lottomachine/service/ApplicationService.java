package com.multicampus.lottomachine.service;

import java.util.Scanner;

import com.multicampus.lottomachine.controller.LottoController;
import com.multicampus.lottomachine.controller.MemberController;
import com.multicampus.lottomachine.domain.MemberVO;
import com.multicampus.lottomachine.domain.MenuType;
import com.multicampus.lottomachine.dto.MemberDTO;
import com.multicampus.lottomachine.exception.NotNeccessaryLogin;
import com.multicampus.lottomachine.view.ApplicationView;

public class ApplicationService {
	private ApplicationView appView = new ApplicationView(); // 어플리케이션 뷰
	private MemberController memberController = new MemberController(); // 사용자 컨트롤러
	private LottoController lottoController; // 로또 게임 컨트롤러
	private MemberDTO memberDTO = new MemberDTO();
	
	public void start() {
		appView.welcomeMessage();
		while (true) {
			try { // 메뉴 입력받을 변수, 간단한 입력이므로 scanner 사용
				Scanner scanner = new Scanner(System.in);
				appView.showMenu();
				int menuNum = Integer.parseInt(scanner.nextLine());
				MenuType menuType = MenuType.getMenuTypeByCode(menuNum); // 사용자에게 입력받은 메뉴값으로 메뉴 선택
				if(!menuType.isLoginRequired()&&!memberDTO.isEmpty()) {
					throw new NotNeccessaryLogin("로그인이 필요없습니다.");
				}
				if (menuType.isLoginRequired() && !isLoggedIn()) {
					appView.accessErrorView();
					menuType = MenuType.LOG_IN;
				}
				switch (menuType) {
				case SIGN_UP: // 회원가입 처리
					memberController.signUp(menuType.getDescription());
					break;
				case LOG_IN: // 로그인 처리
					memberDTO = memberController.login(menuType.getDescription());
//						 lottoController= new LottoController(memberDTO);
					break;
				case DEPOSIT: // 금액 충전 처리
					memberController.chargeBalance(menuType.getDescription());
					break;
				case BUY_LOTTO: // 로또 구매 처리
					memberController.buyLottoTicket(menuType.getDescription());
					break;
				case LOTTO_GAME: // 로또 게임 처리
					lottoController.runLottoGame(menuType.getDescription());
					break;
				case MEMBER_GAME_RESULT: // 사용자 게임 정보 확인
					lottoController.showAllGamestatics();
					break;
				case LOGOUT:
					memberDTO = new MemberDTO();
					//TODO 정확히 구현하려면 controller안에 MemberVO를 넣어서, Service쪽 VO를 비워야 하지만, 
					//2단계에서는 데이터가 날아가기때문에 거기까지 하지 않겠다.  -> 3단계에서 구현
					appView.showLogout();
					break;
				case EXIT: // 프로그램 종료
					return;
				default: // 잘못된 메뉴 입력 시
					throw new NumberFormatException("없는 메뉴입니다.");
				}// switch end
			} catch(NotNeccessaryLogin e) {
				System.out.println(e.getMessage());
			}catch (NumberFormatException e) { // 에러 처리 : 메뉴 잘못 처리
				System.out.println("잘못된 형식입니다.");
			} // catch close
		}
	}

	private boolean isLoggedIn() {
		return memberController.isLogginedIn();
	}
}
