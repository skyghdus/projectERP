package com.erp.app.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

import com.erp.app.MainApp;
import com.erp.app.model.Product;
import javafx.fxml.Initializable;

public class RootController implements Initializable {
	//@FXML private Label resource;
	//@FXML private ToggleButton resource;
	//@FXML private ToggleButton product;
	//@FXML private ToggleButton client;
	//@FXML private ToggleButton order;
	/*@FXML private MenuItem resource;
	@FXML private MenuItem product;
	@FXML private MenuItem client;
	@FXML private MenuItem order;
	@FXML private Menu product1;*/
	
	private MainApp mainApp;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	@FXML
	private void handleProduct() {
	    mainApp.showProductOverview();
	}
	@FXML
	private void handleResource() {
	    mainApp.showResourceOverview();
	}
	@FXML
	private void handleClient() {
	    mainApp.showClientOverview();
	}
	@FXML
	private void handleOrder() {
	    mainApp.showOrderOverview();
	}
	@FXML
	private void handleStaff() {
		mainApp.showStffOverview();
	}
	@FXML
	private void handleManufacturing() {
		mainApp.showManufactOverview();
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // 占쎈�믭옙�뵠�뇡遺용퓠 observable �뵳�딅뮞占쎈뱜 占쎈쑓占쎌뵠占쎄숲�몴占� �빊遺쏙옙占쎈립占쎈뼄.
        //resourceTable.setItems(mainApp.getResourceData());
    }
}
