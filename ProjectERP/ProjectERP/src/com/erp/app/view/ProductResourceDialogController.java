//not used

package com.erp.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.erp.app.MainApp;
import com.erp.app.model.Product;
import com.erp.app.model.Resource;

public class ProductResourceDialogController implements Initializable {
	@FXML TextArea ta1;
	@FXML TextField tf2;
	@FXML TextField tf3;
	@FXML TextField tf4;
	@FXML TextField tf5;
	@FXML public ComboBox<String> combox1;
	ObservableList<String> list = FXCollections.observableArrayList("test1", "test2", "test3","test4", "추가");
	private Stage dialogStage;
    private Resource resource;
    private Resource[] arr;
    private boolean okClicked = false;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		combox1.setItems(list);
	}
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	/*public void setResource(Resource resource) {
        this.resource = resource;

        tf1.setText(resource.getName());
        tf2.setText(Integer.toString(resource.getID()));
        tf3.setText(Integer.toString(resource.getSellingPrice()));
        tf4.setText(Integer.toString(resource.getStack()));
        tf5.setText(Integer.toString(product.getProductionCost()));
    }*/
	public boolean isOkClicked() {
        return okClicked;
    }
	@FXML
    private void handleOk() {
		//resource.setName(tf1.getText());
		//product.setID(Integer.parseInt(tf2.getText()));
		//product.setSellingPrice(Integer.parseInt(tf3.getText()));
		//product.setStack(Integer.parseInt(tf4.getText()));
		//product.setProductionCost(Integer.parseInt(tf5.getText()));
        okClicked = true;
        dialogStage.close();
    }
	@FXML
    private void handleCancel() {
        dialogStage.close();
        //return price;
    }
}
