package com.multicampus.lottomachine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.multicampus.lottomachine.dto.MemberDTO;

public class MemberDAOImpl implements MemberDAO {

	private final JDBCconnector jdbcConnector = new JDBCconnector();

	@Override
	public void insertMember(MemberDTO memberDTO)  throws SQLException  {	//회원 가입 처리
		String sql = "insert into member values(?,?,?,?,0,0);";
		Connection con = jdbcConnector.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, memberDTO.getId());
		pstmt.setString(2, memberDTO.getPassword());
		pstmt.setString(3, memberDTO.getName());
		pstmt.setString(4, memberDTO.getNickName());
        int count = pstmt.executeUpdate();
        if(count==0) {
        	throw new SQLException();
        }
	}

	@Override
	public boolean checkDuplicate(String value, String inputValue) throws SQLException {
		String sql = "select count(*) from member where " + value + " = ?";
		int count = 0;
		Connection con = jdbcConnector.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, inputValue);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			count = rs.getInt(1);
		}
		return count > 0;
	}
}
