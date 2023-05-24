package com.multicampus.lottomachine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.multicampus.lottomachine.domain.MemberVO;
import com.multicampus.lottomachine.exception.LoginAttemptsExceededException;
import com.multicampus.lottomachine.exception.LoginFailException;
import com.multicampus.lottomachine.exception.NobalanceException;
import com.multicampus.lottomachine.exception.OutOfRangeException;

/** 회원 정보 관련 서비스 */
public class MemberService {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 사용자 입력값 처리
	private MemberVO memberVO = new MemberVO(); // 사용자의 입력을 저장하기 위한 객체

	/** 입력값 처리 */
	public void inputMemberInfo() { // 회원가입
		String userID = null; // 객체에 저장할 아이디
		String password = null; // 객체에 저장할 패스워드
		String userName = null; // 객체에 저장할 이름
		int balance = 0;

		while (true) { // 사용자의 정보를 입력받음. 정상적으로 받을 때까지 종료되지 않음
			try {
				userID = checkNullValue(readInput("회원 아이디", br, userID)); // 아이디 입력을 받고 -> 널체크 -> 입력받고
				password = checkNullValue(readInput("패스워드", br, password));
				userName = checkNullValue(readInput("회원 이름", br, userName));
				// balance는 int값이고, null이나 1000원 이하는 입력할 수 없고, 숫자만 입력해야함
				if (balance == 0) { // balance값이 없는 경우에만 입력을 진행
					String balanceInput = checkNullValue(readInput("보유금액", br));
					balance = checkInputNumberValue(balanceInput, 1000);
				}
				break;
			} catch (OutOfRangeException e) {
				System.out.println(e.getMessage());
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해주세요.");
			} catch (NullPointerException e) {// enter칠 때
				System.out.println("아무것도 입력하지 않았습니다. 값을 다시 입력해주세요.");
			} catch (IOException e) { // 알수 없는 입력 오류
				System.out.println("입력이 정상작동하지 않습니다.");
			}
		}
		createMemberInfo(userID, password, userName, balance); // memberVO 입력
	}

	public MemberVO executeLogin() throws LoginAttemptsExceededException { // 로그인 처리
		String userID; // 사용자 아이디
		String password; // 사용자 패스워드
		int errorCount = 0; // 패스워드 틀린 횟수
		while (true) { // 사용자 정보를 입력 받음
			try {
				userID = readInput("회원 아이디", br);
				password = readInput("패스워드", br);
				checkLoginID(userID, password); // 로그인 정보 확인
				return memberVO;
			} catch (LoginFailException e) { // 로그인 실패 에러 처리
				errorCount++; // 실패할 때마다 에러카운트 증가
				if (errorCount >= 5) { // 5번을 넘으면 전체 메뉴로 넘김
					throw new LoginAttemptsExceededException("로그인 시도 횟수를 초과했습니다. 전체 메뉴로 돌아갑니다.");
				}
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println("입력이 정상작동하지 않습니다.");
			}
		}
	}

	public void chargeBalance() { // 금액 충전
		String previousValue = String.valueOf(memberVO.getBalance()); // 대부분의 입력값이 String이므로 Strng으로 입력
		int balance = 0; // 입력한 balance 값을 처리
		int totalBalance = 0; // 이전 값과 최종값을 더한 값
		String balanceInput; // 사용자 입력
		while (true) {
			try {
				balanceInput = checkNullValue(readInput("보유금액", previousValue, br)); // 입력값 null check
				balance = checkInputNumberValue(balanceInput, 1000); // 입력값 검증 - 보편적인 것으로 바꾸자
				totalBalance = balance + memberVO.getBalance(); // 이전 금액과 합치기
				break;
			} catch(NumberFormatException e) {
				System.out.println("숫자만 입력해주세요.");
			}catch (OutOfRangeException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println("입력이 정상작동하지 않습니다.");
			}
		}
		memberVO.setBalance(totalBalance); // balance를 업데이트 된 값으로 변경
	}

	public void buyLottoTicket() throws NobalanceException{ // 티켓 구매
		String previoustTicket = String.valueOf(memberVO.getPurchaseLottoTicket()); // 현재 티켓 보유 숫자
		int ticket = 0; // 티켓
		int totalTicket = 0; // 총 티켓
		int totalBalance = memberVO.getBalance(); // 이전 보유금액
		if(totalBalance<1000) {
			throw new NobalanceException("보유금액이 부족합니다. 전체메뉴로 돌아갑니다.");
		}
		String ticketInput;
		while (true) {
			try {
				ticketInput = checkNullValue(readInput("티켓", previoustTicket, br)); // 입력값 null check
				ticket = checkInputNumberValue(ticketInput, 0,totalBalance); // 입력값 검증
				
				totalTicket = ticket + memberVO.getPurchaseLottoTicket(); // 전체 값 저장
				totalBalance -= (ticket * 1000);
				break;
			} catch(NumberFormatException e) {
				System.out.println("숫자만 입력해주세요.");
			}catch (OutOfRangeException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println("입력이 정상작동하지 않습니다.");
			}
		}
		memberVO.setBalance(totalBalance); // setter를 만들 것인지 고민해 볼 필요가 있음
		memberVO.setPurchaseLottoTicket(totalTicket);
	}

	private String readInput(String message, BufferedReader br) throws IOException { // 사용자 입력값 처리 - String이 아닌 값은 여기서
		return readInput(message,null ,br,null);
	}

	private String readInput(String message, BufferedReader br, String value) throws IOException { // 사용자 입력값 처리 - 기존값 체크시 여기
		return readInput(message,null ,br,value);
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

	public void createMemberInfo(String userID, String password, String userName, int balance) { // memberVO 생성
		memberVO = new MemberVO(userID, password, userName, balance);
	}

	/** 값 겁사 */
	public String checkNullValue(String value) { // 입력 값이 null인지 확인
		if (value.isEmpty()) {
			throw new NullPointerException(); // null값이면 에러처리
		}
		return value;
	}

//	private int validateInputBalance(String inpuValue, int validationValue) {
//		int intInpuValue = Integer.parseInt(inpuValue);
//		if (intInpuValue < validationValue) {
//		throw new OutOfRangeException(validationValue + " 이상만 충전 가능합니다.");
//		}
//		return intInpuValue;
//	}
//	
//	private int validateInputTicket(String ticketInput, int totalBalance, int validationValue) {
//		int intInputValue = Integer.parseInt(ticketInput); 
//		if (totalBalance!=0&&totalBalance < (intInputValue * 1000)) {
//		throw new OutOfRangeException("보유금액 보다 구매금액이 많습니다. .");
//		}
//		if (intInputValue <= validationValue) {
//			throw new OutOfRangeException(validationValue + " 이상만 충전 가능합니다.");
//		}
//		return 0;
//	}
//	
	
	private int checkInputNumberValue(String inputValue, int validationValue) { // 보유금액 입력값 체크, 보유금액과 상관없을때
		return checkInputNumberValue(inputValue,validationValue,0);
	}

	private int checkInputNumberValue(String inputValue, int validationValue, int totalValue) {	// 사용자가 정수 값을 입력했을 때 검증
		int intInputValue = Integer.parseInt(inputValue); // 여기서 에러발생
		if (totalValue!=0&&totalValue < (intInputValue * 1000)) {
			throw new OutOfRangeException("보유금액 보다 구매금액이 많습니다. .");
		}
		if (intInputValue <= validationValue) {
			throw new OutOfRangeException(validationValue + " 이상만 충전 가능합니다.");
		}
		return intInputValue;
	}

	public boolean isLogginedIn() { // 로그인이 되어있는지 확인
		memberVO = getMemberInfo();
		return memberVO != null && !memberVO.isEmpty();
	}

	private void checkLoginID(String userID, String password) { // 로그인 값 확인
		if (userID.equals("java") && password.equals("qwer1234")) {
			memberVO = new MemberVO("java", "qwer1234", "자바쓰", 10000); // 패스워드 맞으면 memberVO객체에 정보를 넣어서 반환
		} else {
			throw new LoginFailException("아이디/패스워드가 잘못되었습니다. 다시 입력해주세요.");
		}
	}

	/** getter */
	public MemberVO getMemberInfo() { // 멤버 정보 가져오기
		return memberVO;
	}

	public String getResultInfo() { // 사용자 입력 받고 그 결과를 출력
		return memberVO.getResultDescription();
	}

	/** setter */
	public void setMemberNull() { // 회원 가입 후 로그인 null 처리, 혹시 로그아웃 메뉴가 추가되면 또 사용 가능
		memberVO = new MemberVO(null, null, null, 0);
	}
}
