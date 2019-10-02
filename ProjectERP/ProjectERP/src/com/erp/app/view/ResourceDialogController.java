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
import com.erp.app.model.Resource;

public class ResourceDialogController implements Initializable {
	@FXML TextField tf1;
	@FXML TextField tf2;
	@FXML TextField tf3;
	@FXML TextField tf4;
	@FXML TextField standardtf;
	private Stage dialogStage;
    private Resource resource;
    private boolean okClicked = false;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	public void setResource(Resource resource) {
        this.resource = resource;

        tf1.setText(resource.getName());
        tf2.setText(resource.getCategory());
        tf3.setText(Integer.toString(resource.getPrice()));
        tf4.setText(Integer.toString(resource.getStack()));
        standardtf.setText(Integer.toString(resource.getStandard()));
    }
	public boolean isOkClicked() {
        return okClicked;
    }
	@FXML
    private void handleOk() {
		resource.setName(tf1.getText());
        resource.setCategory(tf2.getText());
        resource.setPrice(Integer.parseInt(tf3.getText()));
        resource.setStack(Integer.parseInt(tf4.getText()));
        resource.setStandard(Integer.parseInt(standardtf.getText()));
        okClicked = true;
        dialogStage.close();
    }
	@FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
