package com.test.app;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;

import java.io.IOException;
import java.sql.*;
//import com.test.app.view.Controller;

public class MainApp extends Application {
	//private Stage primaryStage;
	//private BorderPane root;
	@Override
	public void start(Stage primaryStage) throws Exception { 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Root.fxml"));

		Parent root = loader.load(); 
		Scene scene = new Scene(root); 

		primaryStage.setTitle("ERP test"); 
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Controller cont = new Controller();
		cont.connect1();
		launch(args);
		/*Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		launch(args);
		try{

			//Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driverr");

			//Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/test?serverTimeZone=UTC", "root", "1q2w3e4r%");

			//Execute a query
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM resource");

			//Print results
            while(rs.next()) {
            	//System.out.println(rs.getString("dept_name") + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getDouble("salary"));

            }


            // close a connection
            stmt.close();
            conn.close();

		}catch (SQLException ex) {
			//Handle errors for JDBC
			ex.printStackTrace();
		} catch (Exception e){
		    //Handle errors for Class.forName
			e.printStackTrace();
		}*/
	}
	public void showResourceView() {
	    try {
	        // 연락처 요약을 가져온다.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("showResource.fxml"));
	        AnchorPane resourceOverview = (AnchorPane) loader.load();

	        // 연락처 요약을 상위 레이아웃 가운데로 설정한다.
	       // Parent root = root.setCenter(resourceOverview);

	        // 메인 애플리케이션이 컨트롤러를 이용할 수 있게 한다.
	        resourceViewController controller = loader.getController();
	        controller.setMainApp(this);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
