package com.erp.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.erp.app.MainApp;
import com.erp.app.model.Resource;
import com.erp.app.model.Staff;


public class StaffDialogController implements Initializable {
	@FXML DatePicker dp;
	@FXML TextField nameTf;
	@FXML TextField birthDayTf;
	@FXML TextField ageTf;
	@FXML TextField rankTf;
	@FXML TextField phoneNumberTf;
	@FXML TextField addressTf;
	@FXML TextField accountTf;
	@FXML TextField socialNumberTf;
	private Stage dialogStage;
    private Staff staff;
    private boolean okClicked = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	public void setStaff(Staff staff) {
        this.staff = staff;

        nameTf.setText(staff.getName());
        dp.getEditor().setText(staff.getBirthday());
        //birthDayTf.setText(staff.getBirthday());
        ageTf.setText(Integer.toString(staff.getAge()));
        rankTf.setText(staff.getRank());
        phoneNumberTf.setText(staff.getPhoneNumber());
        addressTf.setText(staff.getAddress());
        accountTf.setText(staff.getAccountNumber());
        socialNumberTf.setText(staff.getSocialNumber());
    }
	
	public boolean isOkClicked() {
        return okClicked;
    }
	
	@FXML
	public void handleOk() {
		
		staff.setName(nameTf.getText());
        //staff.setBirthday(birthDayTf.getText());
		if(dp.getValue() != null) {
			staff.setBirthday(dp.getValue().toString());
		}
        staff.setAge(Integer.parseInt(ageTf.getText()));
        staff.setRank(rankTf.getText());
        staff.setPhoneNumber(phoneNumberTf.getText());
        staff.setAddress(addressTf.getText());
        staff.setAccountNumber(accountTf.getText());
        staff.setSocialNumber(socialNumberTf.getText());
        okClicked = true;
        dialogStage.close();
		//System.out.println(dp.getValue());
	}
	
	@FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
