package com.multicampus.lottomachine.dao;

import java.sql.SQLException;

import com.multicampus.lottomachine.dto.MemberDTO;

public interface MemberDAO {
	void insertMember(MemberDTO memberDTO) throws SQLException;	//사용자 등록 후 nickname을 반환
	boolean checkDuplicate(String value,String inputValue) throws SQLException ;	//아이디나 닉네임을 중복 체크할 때 사용
	
}
