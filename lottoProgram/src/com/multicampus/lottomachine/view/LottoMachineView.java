package com.multicampus.lottomachine.view;

import java.util.ArrayList;
import java.util.TreeSet;

import com.multicampus.lottomachine.domain.CustomerLottoVO;
import com.multicampus.lottomachine.domain.StaticsWinningVO;
import com.multicampus.lottomachine.domain.UserVO;
//화면에 출력할 메뉴 보여주기
public class LottoMachineView {
	public void showProgramMenu(UserVO userVo) {//전체 메뉴
		System.out.println("===================================================");
		System.out.println("\t\t로또 프로그램 메뉴 입니다.");
		System.out.println("===================================================");
		System.out.println(userVo.getUserID()+"님 원하는 메뉴를 선택해주세요. 현재 보유금액은 "+userVo.getUserMoney());
		System.out.println("Buy. 로또구매");
		System.out.println("Lotto. 로또게임");
		System.out.println("Statics. 로또게임 통계");
		System.out.println("Charging. 보유금액 충전");
		System.out.println("Q. 종료");
	}
	public void showCustomerInputMessage(int i) {
		System.out.println(i+"번째 장입니다. 자동은 '자동'입력, 수동은 숫자를 입력해주세요");	
		System.out.println("로또 번호 6개를 입력해주세요. 번호는 1~45입니다");
	}
	public void showUsingPaper(int userPaper) {//입력값 요청
		System.out.print("현재 보유한 로또는 "+userPaper+"입니다. 몇장의 로또를 사용하시겠습니까?");		
	}
	public void showStartGameMenu() {//게임 진행 여부
		System.out.println("로또게임을 시작하시겠습니까? yes(y), no(n)");
	}
	public void showCustomerSetting(ArrayList<CustomerLottoVO> customArrayList) {	//사용자 입력값 보여주기
		System.out.println("구매하신 내역은 다음과 같습니다.");
		for(CustomerLottoVO  customNumber:customArrayList) {
			System.out.println(customNumber.getList());
		}
	}
	public void showWinningResult(TreeSet<Integer> winningNumber, int resultIntNumber, TreeSet<Integer> resultNumbers) {//당첨정보 보여주기
		System.out.println("당첨 번호는 : "+winningNumber+"입니다.");
		System.out.println("귀하는 총 "+resultIntNumber+"개가 당첨되었습니다. 당첨내역"+resultNumbers);
	}
	public void showStaticsWinning(ArrayList<StaticsWinningVO> winningStatics) {	//당첨통계 보여주기
		System.out.println("===================================================");
		System.out.println("\t\t로또 내역 입니다.");
		System.out.println("===================================================");
		for(StaticsWinningVO vo:winningStatics) {
			System.out.println(vo.getRoundNumber()+"회차당첨번호 : "+vo.getWinningList());
			System.out.print( "사용자 번호 : ["+vo.getAutoInfo()+"]"+vo.getCustomerList());
			System.out.print(" 당첨 개수 :"+vo.getNumberOfWinning()+" - "+vo.getResultList());
			System.out.printf(" 당첨확률 : %.3f\n",vo.getStaticsLottoWinning());
		}
	}
	public void showBuyMenu(UserVO userInfo) {
		int possibleBuyLotto = userInfo.getUserMoney()/1000;
		System.out.println("===================================================");
		System.out.println("\t\t로또 구매 메뉴 입니다.");
		System.out.println("===================================================");
		System.out.println("현재 보유금액은 "+userInfo.getUserMoney()+"입니다. "+possibleBuyLotto+"장까지 구매 가능합니다.");
		System.out.print("몇 장 구매하시겠습니까?");
	}
	public void showNumberPurchases(int lottoPaper, int userMoney) {
		System.out.println("총 "+lottoPaper+"장이 구매되었습니다. 현재 보유금액은 "+userMoney+"원입니다. ");
	}
}
