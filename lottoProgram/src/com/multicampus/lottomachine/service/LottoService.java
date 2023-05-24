package com.multicampus.lottomachine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.multicampus.lottomachine.domain.MemberVO;
import com.multicampus.lottomachine.domain.ResultLottoVO;
import com.multicampus.lottomachine.domain.UserLottoInputVO;
import com.multicampus.lottomachine.domain.WinningLottoVO;
import com.multicampus.lottomachine.domain.WinningStaticsVO;
import com.multicampus.lottomachine.exception.DuplicationNubersException;
import com.multicampus.lottomachine.exception.OutOfRangeException;

public class LottoService {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private ArrayList<UserLottoInputVO> userInputList;
	private ArrayList<ResultLottoVO> resultList;
	private WinningLottoVO winningVO;
	private ArrayList<WinningStaticsVO> winningStaticList = new ArrayList<>();
	MemberVO memberVO;
	int useTicket;
	int roundNumber;
	
	public void LottoTicketPurchaseService(MemberVO memberVO) {
		String inputUseTicketString;	//사용할 로또를 입력받을 값
		this.memberVO = memberVO;
		useTicket= 0;
		int toalTicket = memberVO.getPurchaseLottoTicket();
		while(true) {
			try {
				if(useTicket == 0) {// 몇장 쓸껀지 선택한 적이 없으면 입력값을 받음
					inputUseTicketString = checkNullValue(readInput("사용할 티켓 수",String.valueOf(toalTicket),br)); 		// 몇장 쓸건지 물어봄
					useTicket = checkInputNumberValue(inputUseTicketString, 0,toalTicket);	// 몇장 쓸건지 사용자 입력
				}
				IputLottoNumbers(useTicket);	//몇장 쓸껀지 있으면 로또를 구매
				break;
			}catch(DuplicationNubersException e) {
				System.out.println(e.getMessage());
			}catch (OutOfRangeException e) {
				System.out.println(e.getMessage());
			}catch(NumberFormatException e) {
				System.out.println("숫자만 입력하세요");
			}catch(IOException e) {
				System.out.println("입력이 정상작동하지 않습니다.");
			}
		}
	}

	private void IputLottoNumbers (int NumberOfTicket){
		String insertNumbers;	//사용자 입력값
		UserLottoInputVO userInputVO ;	// 로또 값이 들어갈 객체
		userInputList = new ArrayList<>();
		while(NumberOfTicket!=0) {
			try {
				insertNumbers = checkNullValue(readInput("로또번호(자동/번호6개) ",br));
				userInputVO = new UserLottoInputVO(insertNumbers);
				userInputList.add(userInputVO);
				NumberOfTicket--;
			}catch(NumberFormatException e){
				System.out.println("숫자만 입력해주세요");
			}catch(OutOfRangeException e){
				System.out.println(e.getMessage());
			}catch(DuplicationNubersException e){
				System.out.println(e.getMessage());
			}catch(IOException e){
				System.out.println("입력이 정상작동하지 않습니다.");
			}
		}
	}

	public int LottoGamePlayService() {
		//서비스 진행 할껀지 물음
		while(true) {
			try {
				String isPlayGame = checkNullValue(readInput("게임진행 여부(y/n)",br));
				if(isPlayGame.toLowerCase().equals("y")) {
					checkLottoResult();
					break;
				}else if(isPlayGame.toLowerCase().equals("n")) {
					System.out.println("게임을 종료합니다.");
					break;
				}else {
					throw new NumberFormatException();
				}
			}catch(NullPointerException e) {
				System.out.println("값을 입력해주세요");
			}catch(NumberFormatException e) {
				System.out.println("y혹은 n만 입력하세요.");
			}catch (IOException e) {
				System.out.println("입력이 처리되지 않았습니다.");
			}
		}
		return useTicket;
	}
	private void checkLottoResult() {
		winningVO = new WinningLottoVO();	//로또 번호 만들기 
		roundNumber+=1;		//회차 회수를 올린다. 
		resultList = new ArrayList<>();
		memberVO.setPurchaseAmount(memberVO.getPurchaseAmount()+(useTicket*1000));	//로또 구매사용금액 
		memberVO.setPurchaseLottoTicket(memberVO.getPurchaseLottoTicket()-useTicket);
		for(int i=0;i<useTicket;i++) {
			ResultLottoVO result= new ResultLottoVO(userInputList.get(i).getList(), winningVO);	//당첨정보객체 생성
			memberVO.setReceivedAmount(memberVO.getReceivedAmount()+result.getReceivedAmount());
			resultList.add(result);
		}
		WinningStaticsVO statics =new WinningStaticsVO(roundNumber,userInputList,winningVO,resultList);	//회차 당첨정보 객체 생성
		winningStaticList.add(statics);	//리스트에 회차정보 추가
	}

	private String readInput(String message, BufferedReader br) throws IOException { // 사용자 입력값 처리 - String이 아닌 값은 여기서
		return readInput(message,null ,br,null);
	}

	private String readInput(String message, String previousValue, BufferedReader br) throws IOException { // 사용자 입력값 처리 - 이전값을 보여줘야 할 때
		return readInput(message, previousValue,br,null);
	}
	
	private String readInput(String message, String previousValue, BufferedReader br,String value) throws IOException { 	//사용자 입력값 처리 
		// 보여줘야 하는 값이 있을 때, 만약 회원 정보 수정이  있다면 사용할 수 있음
		if (value != null) { // 이전에 입력한 값이면 입력을 받지 않음
			return value;
		}
		if(previousValue!=null) {	// 보여줘야 하는 값이 있는 경우
		System.out.println("현재 " + message + "은/는 " + previousValue + "입니다.");
		}
		System.out.println(message + "(을/를) 입력해주세요");
		return br.readLine();
	}

	/** 값 겁사 */
	public String checkNullValue(String value) { // 입력 값이 null인지 확인
		if (value.isEmpty()) {
			throw new NullPointerException(); // null값이면 에러처리
		}
		return value;
	}
	private int checkInputNumberValue(String inputValue, int validationValue, int totalValue) {	// 사용자가 정수 값을 입력했을 때 검증
		int intInputValue = Integer.parseInt(inputValue); // 여기서 에러발생
		if (totalValue!=0&&totalValue < intInputValue) {
			throw new OutOfRangeException("보유티켓보다 많이 사용할 수 없습니다.");
		}
		if (intInputValue < validationValue) {
			throw new OutOfRangeException(validationValue + " 이상만 사용가능합니다.");
		}
		return intInputValue;
	}

	public String getResultDescription() {// 해당 회차의 입력값 만큼만 출력하기
		StringBuilder userLottoResult =new StringBuilder( "입력하신 전체 로또 번호 입니다.\n ");
		int lastIndex = userInputList.size()-1;	// 마지막 인덱스 번호 가져오기
			
		for(int i=useTicket-1;i>=0;i--) {	// 마지막 인덱스 번호에서 구입한 매수 만큼만 출력하기 , 입력순서대로 출력
			userLottoResult.append(userInputList.get(lastIndex-i).getResultDescription()+"\n");
		}
		return userLottoResult.toString();
	}

	public ArrayList<WinningStaticsVO> getWinningStaticsList() {
		return winningStaticList;
	}
}
