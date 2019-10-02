package com.test.app;

import java.net.URL;
import java.util.ResourceBundle;


import java.sql.*;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller implements Initializable {
	@FXML public Button bt1;
	@FXML public TextField tfname;
	@FXML public TextField tfid;
	@FXML public TextField tfprice;
	@FXML public TextField tfstack;
	@FXML public TextArea tfresult;
	@FXML public TableColumn col1;
	@FXML public TableColumn col2;
	@FXML public TableColumn col3;
	@FXML public TableColumn col4;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
			}
	public Controller() {
		url = url+timeZone;
	}
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/test";
	static String timeZone = "?characterEncoding=UTF-8&serverTimezone=UTC";
	static Connection conn = null;
	static String USERNAME = "root";
	static String PASSWORD = "1q2w3e4r%";
	
	public boolean connect1() {
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
	
	public void buttonAction(ActionEvent event) {
		String query = "insert into resource values(?, ?, ? ,?);";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Resource resource;
		try{
			resource = new Resource(tfname.getText(), Integer.parseInt(tfid.getText()), Integer.parseInt(tfprice.getText()), Integer.parseInt(tfstack.getText()));
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, resource.getName());
			pstmt.setInt(2, resource.getID());
			pstmt.setInt(3, resource.getPrice());
			pstmt.setInt(4, resource.getStack());
			/*pstmt.setString(1, tfname.getText());
			pstmt.setInt(2, Integer.parseInt(tfid.getText()));
			pstmt.setInt(3, Integer.parseInt(tfprice.getText()));
			pstmt.setInt(4, Integer.parseInt(tfstack.getText()));*/
			pstmt.executeUpdate();
			
			rs = pstmt.executeQuery("SELECT * FROM resource");

			//Print results
            while(rs.next()) {
            	System.out.println(rs.getString("name") + "|" + rs.getInt(2) + "|" + rs.getInt(3) + "|" + rs.getInt(4));
            	tfresult.setText(rs.getString("name") + "|" + rs.getInt(2) + "|" + rs.getInt(3) + "|" + rs.getInt(4)+"\n");
            	
            	/*col1.
            	col2.setText(rs.getString(2));
            	col3.setText(rs.getString(3));
            	col4.setText(rs.getString(4));*/
            }
		}catch (SQLException ex) {
			//Handle errors for JDBC
			ex.printStackTrace();
		} catch (Exception e){
		    //Handle errors for Class.forName
			e.printStackTrace();
		}
		System.out.println("저장완료");
	}
	public boolean add(Resource resource) {
		return true;
	}
	public void delete() {
		PreparedStatement pstmt = null;
		String query = "delete from resource;";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
