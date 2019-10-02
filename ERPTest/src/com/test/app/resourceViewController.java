package com.test.app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import javafx.scene.

public class resourceViewController implements Initializable {

	private MainApp main;
	@FXML TextField tfr1;
	@FXML TextField tfr2;
	@FXML TextField tfr3;
	@FXML TextField tfr4;
	@FXML Button addb;
	@FXML Button delb;
	@FXML public ComboBox<String> comb;
	ObservableList<String> list = FXCollections.observableArrayList();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery("SELECT * FROM resource");
			while(rs.next()) {
				comb.getItems().add((rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public resourceViewController() {
		
	}
	public void setMainApp(MainApp app) {
		this.main = app;
	}
	public void addButton(ActionEvent event) {
		String query = "insert into resource values(?, ?, ? ,?);";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Resource resource;
		try{
			resource = new Resource(tfr1.getText(), Integer.parseInt(tfr2.getText()), Integer.parseInt(tfr3.getText()), Integer.parseInt(tfr4.getText()));
			pstmt=Controller.conn.prepareStatement(query);
			pstmt.setString(1, resource.getName());
			pstmt.setInt(2, resource.getID());
			pstmt.setInt(3, resource.getPrice());
			pstmt.setInt(4, resource.getStack());
			/*pstmt.setString(1, tfname.getText());
			pstmt.setInt(2, Integer.parseInt(tfid.getText()));
			pstmt.setInt(3, Integer.parseInt(tfprice.getText()));
			pstmt.setInt(4, Integer.parseInt(tfstack.getText()));*/
			pstmt.executeUpdate();
			
			//rs = pstmt.executeQuery("SELECT * FROM resource");

			//Print results
            while(rs.next()) {
            	System.out.println(rs.getString("name") + "|" + rs.getInt(2) + "|" + rs.getInt(3) + "|" + rs.getInt(4));
            	//tfresult.setText(rs.getString("name") + "|" + rs.getInt(2) + "|" + rs.getInt(3) + "|" + rs.getInt(4)+"\n");
            	
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

}
