package com.multicampus.lottomachine.domain;

import java.util.Random;
import java.util.TreeSet;

public class WinningLottoVO {
	/** 당첨 번호 리스트 , 재정의 불가 */
	private final TreeSet<Integer> winningVo =new TreeSet<>(); 	//	Question final을 붙일 때 컬렉션은 함수로 보고, 함수 오버로딩 불가하다 생각해야 하는가?
	int bonusNumber;
	/** 당첨 번호 생성자, 랜덤함수 호출 */
	public WinningLottoVO() { setList(); }
	/** 당첨 번호 가져오기 */
	public TreeSet<Integer> getList() {
		return this.winningVo;
	}
	
	/** 당첨 번호 생성 */
	public void setList() {
		if(winningVo.size() == 6) {	// 한번 생성한 경우 더이상 추가하거나 빼지 않음
			throw new NullPointerException("로또번호는 이미 생성되었습니다.");
		}
		Random rd = new Random();
		for(int i = 0 ; winningVo.size() < 6 ; i++) {
			int number=rd.nextInt(45)+1;
			winningVo.add(number);
		}
		while(true) {
			if(!winningVo.contains(bonusNumber)&bonusNumber==0) {
				bonusNumber = rd.nextInt(45)+1;
				break;
			}
		}
	}
	public int getBonusNumber() {
		return bonusNumber;
	}
}
