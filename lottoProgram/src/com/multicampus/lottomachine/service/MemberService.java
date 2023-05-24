package com.multicampus.lottomachine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.multicampus.lottomachine.domain.MemberVO;
import com.multicampus.lottomachine.exception.LoginAttemptsExceededException;
import com.multicampus.lottomachine.exception.LoginFailException;
import com.multicampus.lottomachine.exception.OutOfRangeException;

/** 회원 정보 관련 서비스 */
public class MemberService {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	// 사용자 입력값 처리
	private MemberVO memberVO = new MemberVO();	// 사용자의 입력을 저장하기 위한 객체
	
	public void inputMemberInfo() {	// 사용자 입력을 처리하는 메서드
		String userID=null;	//객체에 저장할 아이디 
		String password=null;	//객체에 저장할 패스워드
		String userName=null;	//객체에 저장할 이름
		int balance=0;

		while (true) {	//사용자의 정보를 입력받음. 정상적으로 받을 때까지 종료되지 않음
			try {
				userID = checkNullValue(readInput("회원 아이디", br, userID));	// 아이디 입력을 받고 -> 널체크 -> 입력받고
				password = checkNullValue(readInput("패스워드", br, password));
				userName = checkNullValue(readInput("회원 이름", br, userName));
				// balance는 int값이고, null이나 1000원 이하는 입력할 수 없고, 숫자만 입력해야함
				if(balance == 0) {
					String balanceInput =checkNullValue(readInput("보유금액", br));
					balance = checkBalanceInput(balanceInput);
				}
				break;
			} catch(OutOfRangeException e) {
				System.out.println(e.getMessage());
			}catch(NumberFormatException e) { 
				System.out.println("숫자를 입력해주세요.");
			} catch (NullPointerException e) {// enter칠 때
				System.out.println("아무것도 입력하지 않았습니다. 값을 다시 입력해주세요.");
			} catch (IOException e) {	//알수 없는 입력 오류
				System.out.println("입력이 정상작동하지 않습니다.");
			}
		}
		createMemberInfo(userID, password, userName, balance);
	}

	private int checkBalanceInput(String balanceInput) {
		int balance = Integer.parseInt(balanceInput);	// 여기서 에러발생
		if(balance<1000) {
			throw new OutOfRangeException("1000원 이상만 충전 가능합니다."); 
		}
		return balance;
	}

	public MemberVO executeLogin() throws  LoginAttemptsExceededException{
		String userID;	//사용자 아이디
		String password;	//사용자 패스워드
		int errorCount=0;	//패스워드 틀린 횟수
		while(true) { // 사용자 정보를 입력 받음
			try {
				userID = readInput("회원 아이디", br);	
				password = readInput("패스워드", br);	
				checkLoginID(userID,password);	//로그인 정보 확인
				return memberVO;
			} catch(LoginFailException e) {
				errorCount++;	//실패할 때마다 에러카운트 증가
				if(errorCount >=5) {	// 5번을 넘으면 전체 메뉴로 넘김
					throw new LoginAttemptsExceededException("로그인 시도 횟수를 초과했습니다. 전체 메뉴로 돌아갑니다.");
				}
				System.out.println(e.getMessage());
			}catch(IOException e) {
				System.out.println("입력이 정상작동하지 않습니다.");
			}
		}
	}
	
	private void checkLoginID(String userID, String password) {	// 패스워드 확인 
		if(userID.equals("java")&&password.equals("qwer1234")) {
			memberVO = new MemberVO("java","qwer1234","자바쓰",1000);	// 패스워드 맞으면 memberVO객체에 정보를 넣어서 반환
		}else {
			throw new LoginFailException("아이디/패스워드가 잘못되었습니다. 다시 입력해주세요.");
		}
	}

	private String readInput(String message, BufferedReader br) throws IOException { 
		System.out.println(message + "(을/를) 입력해주세요");
		return br.readLine();
	}
	
	private String readInput(String message, BufferedReader br, String value) throws IOException { 
		// 회원가입 입력을 위한 부분, 만약 이전 입력값이 있으면 넘어감, 하지만 처음 입력하거나 잘못 입력한 부분이라면 입력을 수행함
		if (value!=null) {	// 이전에 입력한 값이면 입력을 받지 않음
			return value;
		}
		System.out.println(message + "(을/를) 입력해주세요");
		return br.readLine();
	}

	public void createMemberInfo(String userID, String password, String userName, int balance) { // 회원가입 서비스
		memberVO = new MemberVO(userID, password, userName,balance);
	}

	public String checkNullValue(String value) { // 입력 값이 null인지 확인
		if (value.isEmpty()) {
			throw new NullPointerException(); // null값이면 에러처리
		}
		return value;
	}

	public MemberVO getMemberInfo() { // 멤버 정보 가져오기
		return memberVO;
	}

	public String getResultInfo() {	//사용자 입력 받고 그 결과를 출력
		return memberVO.getResultDescription();
	}

	public void setMemberNull() {
		memberVO=new MemberVO(null,null,null,0);
	}

	public boolean isLogginedIn() {			
		memberVO = getMemberInfo();
		return memberVO != null && !memberVO.isEmpty(); 
	}


}
