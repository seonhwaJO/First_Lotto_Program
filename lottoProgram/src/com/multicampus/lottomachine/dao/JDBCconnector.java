package com.multicampus.lottomachine.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCconnector {
	private static final String url = "jdbc:mysql://192.168.0.14:3306/lotto_machine?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";	//jdbc url
	private static final String user="root";	//db id
	private static final String password="qwer1234";	//db pw
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, user, password);	// 연결하기 
	}
}
