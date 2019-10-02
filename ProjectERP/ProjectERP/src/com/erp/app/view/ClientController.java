package com.erp.app.view;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.erp.app.MainApp;
import com.erp.app.model.Client;
import com.erp.app.model.Product;
import com.erp.app.model.Resource;

import javafx.fxml.Initializable;

public class ClientController implements Initializable {
	@FXML private TableView<Client> clientTable;
	@FXML private TableColumn<Client, Integer> idColumn;
	@FXML private TableColumn<Client, String> nameColumn;
	@FXML private TableColumn<Client, String> cnColumn;
	@FXML private TableColumn<Client, String> ceoNameColumn;
	@FXML private TableColumn<Client, String> reDateColumn;
	@FXML private Label lano;
	@FXML private Label laname;
	@FXML private Label laceoname;
	@FXML private Label lasn;
	@FXML private Label laph;
	@FXML private Label laac;
	@FXML private Label laetc;
	
	private MainApp mainApp;
	private ResultSet rs;
	
	public ClientController() {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		idColumn.setCellValueFactory(cellData->cellData.getValue().idProperty().asObject());
		nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
		cnColumn.setCellValueFactory(cellData->cellData.getValue().corporateNumberProperty());
		ceoNameColumn.setCellValueFactory(cellData->cellData.getValue().ceoNameProperty());
		
		reDateColumn.setCellValueFactory(cellData->cellData.getValue().lastOrderProperty());
		clientDetails(null);
		clientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> clientDetails(newValue));
	}
	
	public void clientDetails(Client client) {
		if(client != null) {
			laname.setText(client.getName());
			lano.setText(client.getCorporateNumber());
			laceoname.setText(client.getCeoName());
			lasn.setText(client.getSN());
			laph.setText(client.getPhoneNumber());
			laac.setText(Integer.toString(client.getAddressCode()));
			laetc.setText(client.getAddress());
			
		} else {
			laname.setText("");
			lano.setText("");
			laceoname.setText("");
			lasn.setText("");
			laph.setText("");
			laac.setText("");
			laetc.setText("");
		}
	}
	
	@FXML
	private void handleDeleteClient() {
	    int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
	    mainApp.insert("delete from client where clientID = " + clientTable.getSelectionModel().getSelectedItem().getID() + ";");
	    clientTable.getItems().remove(selectedIndex);
	}
	
	@FXML
	private void handleNewClient() {
	    Client tempClient = new Client();
	    boolean okClicked = mainApp.showClientEditDialog(tempClient);
	    
	    if (okClicked) {
	    	try {
		    	mainApp.insert("insert into client(clientName, corporate_registration_number, ceoName, "
		    			+ "ceoSocialNumber, addressCode, address, phoneNumber, account, lastOrder) "
		    			+ "values('"+tempClient.getName() + "', '" + tempClient.getCorporateNumber() + "', '" 
		    			+ tempClient.getCeoName() + "', '" + tempClient.getSN() + "', " + tempClient.getAddressCode()
		    			+ ", '" + tempClient.getAddress() + "', '" + tempClient.getPhoneNumber()+ "', '" 
		    			+ tempClient.getAccount() + "', '" + tempClient.getLastOrder() + "');");
		        rs = mainApp.search("select clientID from client where clientName = '" + tempClient.getName() + "';");
		        if(rs.next()) {
		        	tempClient.setID(rs.getInt(1));
		        }
		        mainApp.getClientData().add(tempClient);
	    	} catch(Exception e) {
	    		System.out.println("select clientID from client where clientName = '" + tempClient.getName() + "'; : "+ e.getMessage());
	    	}
	    }
	}
	@FXML
	private void handleEditClient() {
	    Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
	    if (selectedClient != null) {
	        boolean okClicked = mainApp.showClientEditDialog(selectedClient);
	        if (okClicked) {
	        	try {
	        		mainApp.insert("update client set clientID = " + selectedClient.getID() 
	        		+ ", clientName = '" + selectedClient.getName() + "', corporate_registration_number = '"
	        		+ selectedClient.getCorporateNumber() + "', ceoName = '" + selectedClient.getCeoName() + "', ceoSocialNumber = '"
	        		+ selectedClient.getSN() + "', addressCode = " + selectedClient.getAddressCode() + ", phoneNumber = '" + selectedClient.getPhoneNumber()
	        		+ "', account = '" + selectedClient.getAccount() + "' where clientID = " + selectedClient.getID() + ";");
	        		clientDetails(selectedClient);
	        	} catch(Exception e) {
	        		System.out.println("update client set clientID = " + selectedClient.getID() 
	        		+ ", clientName = '" + selectedClient.getName() + "', corporate_registration_number = '"
	        		+ selectedClient.getCorporateNumber() + "', ceoName = '" + selectedClient.getCeoName() + "', ceoSocialNumber = '"
	        		+ selectedClient.getSN() + "', addressCode = " + selectedClient.getAddressCode() + ", phoneNumber = '" + selectedClient.getPhoneNumber()
	        		+ "', account = '" + selectedClient.getAccount() + "' where clientID = " + selectedClient.getID() + ";" + e.getMessage());
	        	}
	            
	        }

	    } else {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Client Selected");
	        alert.setContentText("Please select a client in the table.");

	        alert.showAndWait();
	    }
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        clientTable.setItems(mainApp.getClientData());
    }

}
