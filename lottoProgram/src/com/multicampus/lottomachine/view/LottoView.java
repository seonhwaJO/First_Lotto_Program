package com.multicampus.lottomachine.view;

import java.util.ArrayList;

import com.multicampus.lottomachine.domain.MemberVO;
import com.multicampus.lottomachine.domain.ResultLottoVO;
import com.multicampus.lottomachine.domain.UserLottoInputVO;
import com.multicampus.lottomachine.domain.WinningStaticsVO;

public class LottoView extends MenuView{
	public void displaySelectedMenu(String menuDescription,String userName) { // 선택한 메뉴를 보여주는 메서드
		System.out.println("===================================================");
		System.out.println("\t\t"+userName+"님, "+ menuDescription + " 메뉴 입니다.");
		System.out.println("===================================================");
	}

	public void showResultWinningStatics(ArrayList<WinningStaticsVO> winningStaticsList) throws NullPointerException{
		if(winningStaticsList.isEmpty()) {
			throw new NullPointerException("게임 기록이 없습니다.");
		}
			int lastIndex = winningStaticsList.size()-1;
			System.out.println("===================================================");
			System.out.println("\t\t"+winningStaticsList.get(lastIndex).getRoundNumber()+"회차 게임 결과입니다.");
			System.out.println("===================================================");
			
			WinningStaticsVO winningStatics = winningStaticsList.get(lastIndex);
			System.out.println("<사용자 입력 로또 번호>");
			for(UserLottoInputVO userInput:winningStatics.getUserInputList()) {
				System.out.print("["+userInput.getIsAuto()+"]");
				System.out.println(userInput.getList());
			}
			
			System.out.println("<당첨번호>");
			System.out.println(winningStatics.getWinningSet().getList());
			
			System.out.println("<당첨 결과 리스트>");
			for(ResultLottoVO resultVO :winningStatics.getResultList()) {
				System.out.print("당첨 결과 :"+resultVO.getRank().getDescription()+"\t");
				System.out.print(" 당첨번호 : "+resultVO.getList()+"\t");
				System.out.println("당첨 금액 :" + resultVO.getReceivedAmount()+"원");
			}
	}
	public void showAllGamesStatics(ArrayList<WinningStaticsVO> winningStaticsList,MemberVO memberVO) throws NullPointerException{
		if(winningStaticsList.isEmpty()) {
			throw new NullPointerException("게임 기록이 없습니다.");
		}
		System.out.println("********************전체 개요********************");
		System.out.println("전체 게임 횟수 : "+memberVO.getPurchaseAmount()/1000+"\t 로또 사용 금액 : "+memberVO.getPurchaseAmount());
		System.out.println("당첨 금액 : "+memberVO.getReceivedAmount());
		for(int i =0;i<winningStaticsList.size();i++) {
			System.out.println("********************"+winningStaticsList.get(i).getRoundNumber()+"회차 결과********************");
			
			WinningStaticsVO winningStatics = winningStaticsList.get(i);
			System.out.println("<사용자 입력 로또 번호>");
			for(UserLottoInputVO userInput:winningStatics.getUserInputList()) {
				System.out.print("["+userInput.getIsAuto()+"]");
				System.out.print(userInput.getList());
			}
			
			System.out.println("<당첨번호>");
			System.out.println(winningStatics.getWinningSet().getList());
			
			System.out.println("<당첨 결과 리스트>");
			for(ResultLottoVO resultVO :winningStatics.getResultList()) {
				System.out.print("당첨 결과 :"+resultVO.getRank().getDescription()+"\t");
				System.out.print(" 당첨번호 : "+resultVO.getList()+"\t");
				System.out.println("당첨 금액 :" + resultVO.getReceivedAmount()+"원");
			}
		}
	}
}
