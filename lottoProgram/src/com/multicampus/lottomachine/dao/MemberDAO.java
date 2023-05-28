package com.multicampus.lottomachine.dao;

import java.sql.SQLException;

import com.multicampus.lottomachine.dto.MemberDTO;

public interface MemberDAO {
	void insertMember(MemberDTO memberDTO) throws SQLException;	//사용자 등록 후 nickname을 반환
	boolean checkDuplicate(String value,String inputValue) throws SQLException ;	//아이디나 닉네임을 중복 체크할 때 사용
	boolean checkUserByAccount(String id,String password) throws SQLException ;	//로그인 체크
	MemberDTO getMemberbyID(String id) throws SQLException ;	// 로그인 아이디로 회원 정보 가져오기
	void updateBalance(String id,int totalBalance)throws SQLException;	// 보유금액 조정
	void updateTicket(String id, int totalTicket)throws SQLException;	// 보유 티켓 조정
}
