package com.multicampus.lottomachine.domain;

import java.util.ArrayList;

public class WinningStaticsVO {
	private int roundNumber;	//회차번호
	ArrayList<UserLottoInputVO> userInputList;//사용자 선택값
	WinningLottoVO winningSet;//당첨 로또번호
	ArrayList<ResultLottoVO> resultList;//당첨 정보값
	
	
	public WinningStaticsVO(int roundNumber, ArrayList<UserLottoInputVO> userInputList, WinningLottoVO winningSet,
			ArrayList<ResultLottoVO> resultList) {
		this.roundNumber = roundNumber;
		this.userInputList= userInputList; 
		this.winningSet = winningSet;
		this.resultList = resultList;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public ArrayList<UserLottoInputVO> getUserInputList() {
		return userInputList;
	}

	public void setUserInputList(ArrayList<UserLottoInputVO> userInputList) {
		this.userInputList = userInputList;
	}

	public WinningLottoVO getWinningSet() {
		return winningSet;
	}

	public void setWinningSet(WinningLottoVO winningSet) {
		this.winningSet = winningSet;
	}

	public ArrayList<ResultLottoVO> getResultList() {
		return resultList;
	}

	public void setResultListt(ArrayList<ResultLottoVO> resultListt) {
		this.resultList = resultListt;
	}

}
