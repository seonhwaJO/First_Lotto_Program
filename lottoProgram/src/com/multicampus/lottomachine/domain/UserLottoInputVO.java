package com.multicampus.lottomachine.domain;

import java.util.Random;
import java.util.TreeSet;

import com.multicampus.lottomachine.exception.DuplicationNubersException;
import com.multicampus.lottomachine.exception.OutOfRangeException;

public class UserLottoInputVO implements InsertData{
	private TreeSet<Integer> userInputSet=new TreeSet<>();
	String isAuto="수동";
	public UserLottoInputVO(String input) {
		if(input.equals("자동")) {//자동이면 Random함수로 만들어서 넣음
			setIsAuto(input);
			setAutoInputSet();
		}else {//자동이 아니면 입력값을 확인해서 넣어야 함
			setUserInputSet(input);
		}
	}
	private void setAutoInputSet() {	//로또 번호 자동 생성
		Random rd = new Random();	//얘는 중복이 없다. 검증 필요도 없다.
		for(int i=0; userInputSet.size()<=6 ; i++) {	// 중복값을 넣지 않기 때문에 트리셋 사이즈가 6이 될 때까지 해야 함
			userInputSet.add((rd.nextInt(45))+1);
		}
	}
	
	private void setUserInputSet(String input) throws NumberFormatException,OutOfRangeException,DuplicationNubersException{	//로또 번호 수동 입력
		String[] inputs= input.split(",");
		if(inputs.length!=6) {
			throw new OutOfRangeException("번호는 6개를 입력해주세요");
		}
		for(String number : inputs) { 			//에러포인트2 : 숫자가 아닌경우 - NumberFormatException
			int lottoNumber = Integer.parseInt(number);	
			if(lottoNumber<1||lottoNumber>45) {			//에러포인트3 : 로또번호는 1~45입니다. 
				throw new OutOfRangeException("로또 번호는 1~45까지 가능합니다."); 
			}
			if(userInputSet.contains(lottoNumber)) {				//에러포인트4 : 로또 번호는 중복 체크 
				throw new DuplicationNubersException("입력값이 중복되었습니다.");
			}
			userInputSet.add(lottoNumber);
		}
	}
	@Override
	public String getResultDescription() {
		StringBuilder userLottoResult =new StringBuilder( "로또 번호 : ");
	    boolean isFirst = true;
		for(int result : userInputSet) {
	        if (!isFirst) {
	            userLottoResult.append(", ");
	        } else {
	            isFirst = false;
	        }
	        userLottoResult.append(result);
		}
		return userLottoResult.toString();
	}
	
	public TreeSet<Integer> getList(){	//입력된 사용자 데이터 가져오기 
		if(userInputSet.size()< 0) {
			throw new NullPointerException("사용자 로또 번호가 없습니다.");//사용자정의에러3 - 입력값이 없습니다. 
		}
		return userInputSet;
	}
	public String getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}
}
