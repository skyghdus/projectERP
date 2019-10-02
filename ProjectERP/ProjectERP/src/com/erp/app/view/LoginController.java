package com.erp.app.view;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.erp.app.MainApp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML TextField tf;
	@FXML PasswordField pf;
	private MainApp mainApp;
	private Stage dialogStage;
	private ResultSet rs;
	private boolean okClicked = false;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	@FXML
	public void loginHandler() {
		try {
			rs = mainApp.search("select department from adminTable where id = '" + tf.getText() + "' and password = '" + pf.getText() + "';");
			if(rs.next()) {
				okClicked = true;
				mainApp.setLoginDepartment(rs.getString(1));
				System.out.println();
				dialogStage.close();
				
			} else {
				System.out.println("id나 password를 다시 입력하여 주세요");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	public boolean isOkClicked() {
        return okClicked;
    }
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        //clientTable.setItems(mainApp.getClientData());
    }
}
