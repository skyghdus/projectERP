package com.erp.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import com.erp.app.MainApp;
import com.erp.app.model.Client;
import com.erp.app.model.Order;
import com.erp.app.model.Product;
import com.erp.app.model.Resource;
import com.erp.app.model.Staff;
import java.util.Calendar;
import java.util.Formatter;

public class StaffController implements Initializable {
	@FXML Label namela;
	@FXML Label birthdayla;
	@FXML Label agela;
	@FXML Label rankla;
	@FXML Label phonela;
	@FXML Label addressla;
	@FXML Label accountla;
	@FXML Label socialla;
	@FXML TableView<Staff> staffTable;
	@FXML TableColumn<Staff, String> nameColumn;
	@FXML TableColumn<Staff, String> rankColumn;
	@FXML TableColumn<Staff, String> phoneColumn;
	MainApp mainApp;
	public StaffController() {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
		rankColumn.setCellValueFactory(cellData->cellData.getValue().rankProperty());
		phoneColumn.setCellValueFactory(cellData->cellData.getValue().phoneNumberProperty());
		staffDetails(null);
		staffTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> staffDetails(newValue));
	}
	public void staffDetails(Staff staff) {
		if(staff != null) {
			namela.setText(staff.getName());
			birthdayla.setText(staff.getBirthday());
			agela.setText(Integer.toString(staff.getAge()));
			rankla.setText(staff.getRank());
			phonela.setText(staff.getPhoneNumber());
			addressla.setText(staff.getAddress());
			accountla.setText(staff.getAccountNumber());
			socialla.setText(staff.getSocialNumber());
			
		} else {
			namela.setText("");
			birthdayla.setText("");
			agela.setText("");
			rankla.setText("");
			phonela.setText("");
			addressla.setText("");
			accountla.setText("");
			socialla.setText("");
		}
	}
	
	@FXML
	public void handleNew() {
		Staff tempStaff = new Staff();
	    boolean okClicked = mainApp.showStaffEditDialog(tempStaff);
	    if (okClicked) {
	    	try {
	    		mainApp.insert("insert into staffTable(name, birthDay, age, rank1, phoneNumber, address, account, socialNumber) values('"
	    				+ tempStaff.getName() + "', '" + tempStaff.getBirthday() + "', " + tempStaff.getAge() + ", '" + tempStaff.getRank() 
	    				+ "', '" + tempStaff.getPhoneNumber() + "', '" + tempStaff.getAddress() + "', '" + tempStaff.getAccountNumber() + "', '"
	    				+ tempStaff.getSocialNumber() + "');");
	    		mainApp.getStaffData().add(tempStaff);
	    	} catch(Exception e) {
	    		System.out.println(e.getMessage());
	    	}
	    }
	}
	@FXML
	public void handleEdit() {
		Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
	    if (selectedStaff != null) {
	        boolean okClicked = mainApp.showStaffEditDialog(selectedStaff);
	        if (okClicked) {
	        	try {
		        	mainApp.insert("update staffTable set name = '" + selectedStaff.getName() + "', birthDay = '" + selectedStaff.getBirthday() 
		        	+ "', age = " + selectedStaff.getAge() + ", rank1 = '" + selectedStaff.getRank() + "', phoneNumber = '" + selectedStaff.getPhoneNumber() 
		        	+ "', address = '" + selectedStaff.getAddress() + "', account = '" + selectedStaff.getAccountNumber() + "', socialNumber = '" + selectedStaff.getSocialNumber() 
		        	+ "' where staffID = " + selectedStaff.getID() + ";");
		            staffDetails(selectedStaff);
	        	} catch(Exception e) {
	        		System.out.println(e.getMessage());
	        	}
	        }

	    } else {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Staff Selected");
	        alert.setContentText("Please select a Staff in the table.");

	        alert.showAndWait();
	    }
	}
	@FXML
	public void handleDelete() {
		int selectedIndex = staffTable.getSelectionModel().getSelectedIndex();
		try {
			mainApp.insert("delete from staffTable where staffID = " + staffTable.getSelectionModel().getSelectedItem().getID() + ";");
			staffTable.getItems().remove(selectedIndex);
		} catch(Exception e) {
			System.out.println("삭제실패 : "+ e.getMessage());
		}
	    
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        staffTable.setItems(mainApp.getStaffData());
    }
}
