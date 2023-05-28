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
		closeAll(null,pstmt,con);
	}

	@Override
	public boolean checkDuplicate(String value, String inputValue) throws SQLException {// 중복값 확인
		String sql = "select count(*) from member where " + value + " = ?";
		int count = 0;
		Connection con = jdbcConnector.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, inputValue);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			count = rs.getInt(1);
		}
		closeAll(rs,pstmt,con);
		return count > 0;
	}
	
	@Override
	public boolean checkUserByAccount(String id, String password) throws SQLException {
		String sql = "select count(*) from member where id =? and password = ?";
		int count = 0;
		Connection con = jdbcConnector.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			count = rs.getInt(1);
		}
		closeAll(rs,pstmt,con);
		return count > 0;
	}
	
	@Override
	public MemberDTO getMemberbyID(String id) throws SQLException {
		String sql = "select *from member where id = ?";
		Connection con = jdbcConnector.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
    	MemberDTO member = new MemberDTO();
        if (rs.next()) {
        	member.setId(rs.getString("id"));
        	member.setPassword(rs.getString("password"));
        	member.setName(rs.getString("name"));
        	member.setNickName(rs.getString("nickname"));
        	member.setBalance(rs.getInt("balance"));
        	member.setTicket(rs.getInt("ticket"));
        }
		closeAll(rs,pstmt,con);
		return member;
	}
	
	@Override
	public void updateBalance(String id,int totalBalance) throws SQLException{
		String sql = "update member set balance = ? where id = ?";
		Connection con = jdbcConnector.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, totalBalance);
		pstmt.setString(2, id);
		pstmt.executeUpdate();
		closeAll(null,pstmt,con);
	}
	
	@Override
	public void updateTicket(String id, int totalTicket) throws SQLException {
		String sql = "update member set ticket = ? where id = ?";
		Connection con = jdbcConnector.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, totalTicket);
		pstmt.setString(2, id);
		pstmt.executeUpdate();
		closeAll(null,pstmt,con);
	}
	
	private void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) {	//전체 connection 닫기;
	    try {
	        if (rs != null) {
	            rs.close();
	        }
	        if (pstmt != null) {
	            pstmt.close();
	        }
	        if (con != null) {
	            con.close();
	        }
	    } catch (SQLException e) {
	        // 예외 처리 또는 로그 작성 등을 수행할 수 있습니다.
	        e.printStackTrace();
	    }
	}
}
