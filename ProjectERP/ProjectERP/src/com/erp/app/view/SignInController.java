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

public class SignInController implements Initializable {
	@FXML TextField tf;
	@FXML TextField dtf;
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
	public boolean isOkClicked() {
        return okClicked;
    }
	@FXML
	public void create() {
		try {
			mainApp.insert("insert into adminTable(id, password, department) values('" + tf.getText() + "', '" + pf.getText() + "', '"
					+ dtf.getText() +"');");
			dialogStage.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	@FXML
	public void delete() {
		try {
			mainApp.insert("delete from admintable where id = '" + tf.getText() + "' and password = '" + pf.getText() + "';");
			dialogStage.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	

}
