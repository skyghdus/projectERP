package com.test.app;

import java.sql.*;



public class DBA {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driber";
	static String url = "jdbc:mysql://localhost:3306/test";
	static String timeZone = "?serverTimeZone=UTC&useSSL=false";
	static Connection conn = null;
	static String USERNAME = "root";
	static String PASSWORD = "1q2w3e4r%";
	public DBA() {
		url = url+timeZone;
	}
	
	public boolean connect() {
		try {
			System.out.println("connecting...");
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
			if(conn != null) {
				System.out.println("connected");
			} else {
				System.out.println("fail");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found exection");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean add(Resource resource) {
		return true;
	}
}
