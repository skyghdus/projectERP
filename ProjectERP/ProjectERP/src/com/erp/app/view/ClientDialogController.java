package com.erp.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.erp.app.MainApp;
import com.erp.app.model.Client;
import com.erp.app.model.Resource;

public class ClientDialogController implements Initializable {
	@FXML TextField tf1;
	@FXML TextField tf2;
	@FXML TextField tf3;
	@FXML TextField tf4;
	@FXML TextField tf5;
	@FXML TextField tf6;
	@FXML TextField tf7;
	@FXML TextField tf8;
	@FXML TextField tf9;
	private Stage dialogStage;
    private Client client;
    int flag = 0;
    private boolean okClicked = false;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	public void setClient(Client client) {
        this.client = client;
        if(client.getName()==null) {
        	flag = 1;
        }
        System.out.println(flag);
        tf1.setText(Integer.toString(client.getID()));
        tf2.setText(client.getName());
        tf3.setText(client.getCorporateNumber());
        tf4.setText(client.getCeoName());
        tf5.setText(client.getSN());
        tf6.setText(Integer.toString(client.getAddressCode()));
        tf7.setText(client.getAddress());
        tf8.setText(client.getPhoneNumber());
        tf9.setText(client.getAccount());
    }
	public boolean isOkClicked() {
        return okClicked;
    }
	@FXML
    private void handleOk() {
		client.setID(Integer.parseInt(tf1.getText()));
		client.setName(tf2.getText());
		client.setCorporateNumber(tf3.getText());
		client.setCeoName(tf4.getText());
		client.setSN(tf5.getText());
		client.setAddressCode(Integer.parseInt(tf6.getText()));
		client.setAddress(tf7.getText());
		client.setPhoneNumber(tf8.getText());
		client.setAccount(tf9.getText());
		if(flag == 1) {
			client.setLastOrder("1900-01-01");
		}
        okClicked = true;
        dialogStage.close();
    }
	@FXML
    private void handleCancel() {
        dialogStage.close();
    }
	
}
