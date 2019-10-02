package com.erp.app.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/";
	String databaseName = "ERPtest";
	//static String timeZone = "?serverTimeZone=UTC&useSSL=false";
	static String timeZone = "?characterEncoding=UTF-8&serverTimezone=UTC";
	static String USERNAME = "root";
	static String PASSWORD = "1q2w3e4r%";
	
	public DBConnection() {
		try
		{
			url = url + databaseName + timeZone;
			//Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("com.mysql.cj.jdbc.Driver");
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimeZone=UTC&useSSL=false", "root", "1q2w3e4r%");
			System.out.println("connecting...");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			if(con != null) {
				System.out.println("connected");
			} else {
				System.out.println("fail");
			}
			st = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("데이터베이스 연결 오류 : " + e.getMessage());
			if(e.getMessage().equals("Communications link failure\n" + 
					"\n" + 
					"The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.")) {
				System.out.println("zzzzz");
				//e.toString();
			} else {
				try {
					st.executeUpdate("create database"+databaseName + ";");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}
			}
			//System.out.println(e.toString());
		}
	}
	
	public ResultSet selectQuery(String s) {
		try {
			rs = st.executeQuery(s);
			return rs;
		} catch(Exception e) {
			System.out.println("데이터베이스 검색 오류 : " +e.getMessage());
			//return -1;
		}
		return null;
		
	}
	
	public boolean insertQuery(String text) {
		try
		{
			/*select - executeQurey
			 * insert, delete, update, create, alter - executeUpdate*/
			st.executeUpdate(text);
			return true;
			//if(rs.next()) {
			//	return true;
			//}
		}
		catch(Exception e)
		{
			System.out.println("데이터베이스 검색 오류 : " + text + "에서 " +e.getMessage() + "가 발생하였습니다.");
		}
		return false;
	}
	public void test() {
		try {
			rs = st.executeQuery("select * from resource");
			while(rs.next()) {
				String name = rs.getString("name");
				int id = rs.getInt("r_id");
				int price = rs.getInt(3);
				int stack = rs.getInt(4);
				System.out.println(name + " " + id + " " + price + " " + stack);
				
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
