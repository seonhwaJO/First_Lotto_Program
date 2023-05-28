package com.multicampus.lottomachine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import com.multicampus.lottomachine.common.FieldInfo;
import com.multicampus.lottomachine.dao.MemberDAO;
import com.multicampus.lottomachine.dao.MemberDAOImpl;
import com.multicampus.lottomachine.domain.MemberVO;
import com.multicampus.lottomachine.dto.MemberDTO;
import com.multicampus.lottomachine.exception.DuplicationException;
import com.multicampus.lottomachine.exception.LoginAttemptsExceededException;
import com.multicampus.lottomachine.exception.LoginFailException;
import com.multicampus.lottomachine.exception.NobalanceException;
import com.multicampus.lottomachine.exception.OutOfRangeException;
import com.multicampus.lottomachine.validation.BalanceRangeValidation;
import com.multicampus.lottomachine.validation.DefaultValidation;
import com.multicampus.lottomachine.validation.DuplicationValidation;
import com.multicampus.lottomachine.validation.PasswordValidation;
import com.multicampus.lottomachine.validation.TicketRangeValidation;
import com.multicampus.lottomachine.validation.Validation;

/** 회원 정보 관련 서비스 */
public class MemberService {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 사용자 입력값 처리
	private MemberDAO memberDAO = new MemberDAOImpl();	//사용자 정보를 DB에서 가져옴
	private MemberDTO memberDTO; //VO를 DTO로 대체
	private Validation valid;
	/** 입력값 처리 */
	public String inputMemberInfo() { // 회원가입
		String id= null; // 아이디, 널 체크, 중복 체크
		String password = null; // 패스워드
		String name = null; // 이름
		String nickName = null;	//닉네임 , 중복 체크
		while (true) { // 사용자의 정보를 입력받음. 정상적으로 받을 때까지 종료되지 않음
			try {
				id = getInput(FieldInfo.ID, id);	//아이디 입력받기
				password = getInput(FieldInfo.PASSWORD, password);	//패스워드 입력받기
				name = getInput(FieldInfo.NAME,name);	//이름 입력받기
				nickName = getInput(FieldInfo.NICKNAME, nickName);	//닉네임 입력받기
				memberDAO.insertMember(new MemberDTO(id, name, password, nickName));	// 선언된 memberDTO에 넣으면 null처리를 해야해서 바로 넣음
				break;
			} catch(DuplicationException e) {		//중복값일 때
				System.out.println(e.getMessage());
			}catch (OutOfRangeException e) {		// vaild 조건을 만족하지 못할 때 나는 에러
				System.out.println(e.getMessage());
			} catch (NullPointerException e) {// enter칠 때
				System.out.println("[경고]아무것도 입력하지 않았습니다. 값을 다시 입력해주세요.");		
			} catch(SQLException e) {		//sql 에러
				System.out.println("[에러]SQL 문제입니다.------------------------------------------------------"); //while문 안에 있는 게 문제 - 웹을 사용한다면 while은 필요 없다.
				e.printStackTrace();
			}catch (IOException e) { // 알수 없는 입력 오류
				System.out.println("[에러]입력이 정상작동하지 않습니다.");
			}
		}
		return nickName ;	// view에 ~~님 가입이 완료되었습니다. 로그인 후 사용해주세요 -> 메인메뉴
	}
	
	public MemberDTO executeLogin() throws LoginAttemptsExceededException { // 로그인 처리
		String id = null; // 사용자 아이디
		String password = null; // 사용자 패스워드
		int loginAttemptsLeft = 5;	//로그인이 허락되는 횟수는 5
		
		while (loginAttemptsLeft != 0) {
			try {
				id = Validation.checkNullValue(readInput(FieldInfo.ID.getFieldName(), br, id));
				password = Validation.checkNullValue(readInput(FieldInfo.PASSWORD.getFieldName(), br, password));
				boolean checkAccount = memberDAO.checkUserByAccount(id, password);
				if(!checkAccount) {
					throw new LoginFailException("[경고]로그인에 실패했습니다.");
				}
				memberDTO = memberDAO.getMemberbyID(id);
				break;
			} catch (LoginFailException e) {
				loginAttemptsLeft--;	// 로그인 failCount 잔여횟수 줄이기
				if(loginAttemptsLeft>0) {	//count가 0이 아닐때 
					id = null;	// id 초기화
					password = null;	//패스워드 초기화
					System.out.println(e.getMessage());
					System.out.println("[경고]로그인 시도 잔여 횟수"+(loginAttemptsLeft));	//잔여횟수 표시
				}else{	//count가 0이면 예외를 던저서 메인메뉴로 돌아가게 만듬
					throw new LoginAttemptsExceededException("[실패]로그인 처리 횟수를 초과했습니다.");	
				}
			}	catch (NullPointerException e) {	//값을 입력하지 않으면 비교에 넣지 않기 때문에 count를 줄이지 않음
				System.out.println("[경고]아무것도 입력하지 않았습니다. 값을 다시 입력해주세요.");		
			} catch(SQLException e) {		//sql 에러
				System.out.println("[에러]SQL 문제입니다.------------------------------------------------------"); //while문 안에 있는 게 문제 - 웹을 사용한다면 while은 필요 없다.
				e.printStackTrace();
			}catch (IOException e) {
				System.out.println("[에러]입력이 정상작동하지 않습니다.");
			}
		}
		return memberDTO;
	}

private String getInput(FieldInfo fieldInfo,String value) throws NullPointerException,IOException,DuplicationException,SQLException {	//input값을 유형별로 체크해서 입력함
	if(value!=null) {	//이전에 filed값을 입력 한 경우 넘어감
		return value;
	}
	String input = null;
	while(input==null) {	// input값이 정상적으로 입력될 때까지
		input = readInput(fieldInfo.getFieldName(), br, input);		//사용자 입력을 받음
		Validation.checkNullValue(input)	;	//입력값 null처리를 검증함
		if(fieldInfo.isCheck() == null) {	// 체크가 필요없는 항목 객체생성 - 비어있음
			valid = new DefaultValidation();
		}else if(fieldInfo.isCheck() ) {	// 중복체크  객체 생성
			boolean isDuplicate = memberDAO.checkDuplicate(fieldInfo.getFieldValue(), input);
			valid = new DuplicationValidation(fieldInfo.getFieldValue(),isDuplicate);	
		}else {		//패스워드 객체 생성
			valid = new PasswordValidation();	//검증 생성
		}
		valid.validate(input);	//검증을 함
	}
	return input;
}

	public int chargeBalance() { // 금액 충전
		String id = memberDTO.getId();
		String previousValue = String.valueOf(memberDTO.getBalance());
		int balance = 0; // 입력한 balance 값을 처리
		int totalBalance = 0; // 이전 값과 최종값을 더한 값
		String balanceInput; // 사용자 입력
		valid = new BalanceRangeValidation();
		while(true) {
			try {
				balanceInput = Validation.checkNullValue(readInput(FieldInfo.BALANCE.getFieldName(), previousValue, br,null)); // 입력값 null check
				valid.validate(balanceInput);
				balance = Integer.parseInt(balanceInput);
				totalBalance = balance+memberDTO.getBalance();
				memberDAO.updateBalance(id,totalBalance);
				memberDTO = memberDAO.getMemberbyID(id);
				break;
			}catch (OutOfRangeException e) {
				System.out.println(e.getMessage());
			}catch (NumberFormatException e) {
				System.out.println("[경고]숫자만 입력가능합니다.");
			}catch(SQLException e) {		//sql 에러
				System.out.println("[에러]SQL 문제입니다.------------------------------------------------------"); //while문 안에 있는 게 문제 - 웹을 사용한다면 while은 필요 없다.
				e.printStackTrace();
			}catch(IOException e) {
				System.out.println("[에러]입력이 정상작동하지 않습니다.");
			}
		}
		return memberDTO.getBalance();
	}

	public int buyLottoTicket() throws NobalanceException{ // 티켓 구매
		int totalBalance = memberDTO.getBalance(); // 이전 보유금액
		valid = new TicketRangeValidation(totalBalance);
		String id = memberDTO.getId();
		String previoustTicket = String.valueOf(memberDTO.getTicket()); // 현재 티켓 보유 숫자
		int ticket = 0; // 티켓
		int totalTicket = 0; // 총 티켓
		String ticketInput;
		
		while (true) {
			ticket=0;
			try {
				ticketInput = Validation.checkNullValue(readInput("티켓", previoustTicket, br,null)); // 입력값 null check
				valid.validate(ticketInput);
				ticket= Integer.parseInt(ticketInput);
				totalTicket = ticket + Integer.parseInt(previoustTicket);	// 티켓 변경 = 이전 티켓+입력 티켓
				totalBalance -= (ticket * 1000);	//보유금액 변경
				memberDAO.updateBalance(id,totalBalance);	//보유 금액 다시 입력
				memberDAO.updateTicket(id,totalTicket);		//구매 티켓 다시 입력
				memberDTO = memberDAO.getMemberbyID(id);	//정보 다시 가져오기
				break;
			} catch(NumberFormatException e) {
				System.out.println("[경고]숫자만 입력해주세요.");
			}catch (OutOfRangeException e) {
				System.out.println(e.getMessage());
			}catch(SQLException e) {		//sql 에러
				System.out.println("[에러]SQL 문제입니다.------------------------------------------------------"); //while문 안에 있는 게 문제 - 웹을 사용한다면 while은 필요 없다.
				e.printStackTrace();
			}catch (IOException e) {
				System.out.println("[에러]입력이 정상작동하지 않습니다.");
			}
		}
		return ticket;
	}

	private String readInput(String message, BufferedReader br, String value) throws IOException { // 사용자 입력값 처리 - 기존값 체크시 여기
		return readInput(message,null ,br,value);
	}
	
	private String readInput(String filedValue, String previousValue, BufferedReader br,String value) throws IOException { 	//사용자 입력값 처리 
		// 보여줘야 하는 값이 있을 때, 만약 회원 정보 수정이  있다면 사용할 수 있음
		if (value != null) { // 이전에 입력한 값이면 입력을 받지 않음
			return value;
		}
		if(previousValue!=null) {	// 보여줘야 하는 값이 있는 경우
		System.out.println("현재 " + filedValue + "은/는 " + previousValue + "입니다.");
		}
		System.out.print(filedValue+"을 입력해주세요 : ");
		return br.readLine();
	}

	public boolean isLogginedIn() { // 로그인이 되어있는지 확인
		return memberDTO!=null&&!memberDTO.isEmpty();
	}

	public MemberDTO getMember() {
		return memberDTO;
	}
}
