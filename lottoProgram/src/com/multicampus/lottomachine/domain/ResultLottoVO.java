package com.multicampus.lottomachine.domain;

import java.util.TreeSet;

public class ResultLottoVO {	// 당첨 내역 생성
	private TreeSet<Integer> resultList = new TreeSet<Integer>();		//당첨정보를 저장할 리스트
	private TreeSet<Integer> customerList;	//사용자 리스트
	private WinningLottoVO winningSet;	//당첨 리스트
	private int resultNumber;	//당첨 개수를 저장할 리스트
	private Rank rank;	//당첨등수
	private int receivedAmount; //당첨금
	
	public ResultLottoVO(TreeSet<Integer> customNumbers,WinningLottoVO winningSet) {
		this.customerList = customNumbers;
		this.winningSet = winningSet;
		this.setList();	//당첨 정보 셋팅
	}
	
	public TreeSet<Integer> getList() {
		if(customerList==null || winningSet.getList() == null) {
			throw new NullPointerException("사용자 입력이나 당첨번호가 없습니다. 결과값을 반환할 수 없습니다.");
		}
		return this.resultList;
	}

	private void setList() {
		for(Integer number:this.customerList) {		//향상된 for문으로 winning리스트에 사용자 번호가 있는지 확인
			if(this.winningSet.getList().contains(number)) {
				this.resultList.add(number);
			}
		}
		setResultNumber();	//당첨 개수 셋팅
		setScore();
		setReceivedAmount(receivedAmount);
	}
	
	private void setScore() {
		if(resultNumber==6) {
			setRank(Rank.FIRST_PLACE);
		}else if(resultNumber==5 && customerList.contains(winningSet.getBonusNumber())) {
			setRank(Rank.SECOND_PLACE);
		}else if(resultNumber==5) {
			setRank(Rank.THIRD_PLACE);
		}else if(resultNumber==4) {
			setRank(Rank.FOURTH_PLACE);
		}else if(resultNumber==3) {
			setRank(Rank.FIFTH_PLACE);
		}else{
			setRank(Rank.LOSER);
		}
	}

	private void setResultNumber() {	//당첨 개수 셋팅
		resultNumber = resultList.size();	//당첨 개수에 리스트 사이즈 입력
	}
	
	public int getResultNumber() {	//당첨 개수 반환
		return this.resultNumber;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public int getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(int receivedAmount) {
		this.receivedAmount = rank.getprize();
	}
	
	
}