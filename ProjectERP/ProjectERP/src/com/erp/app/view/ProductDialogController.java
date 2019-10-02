package com.erp.app.view;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.erp.app.MainApp;
import com.erp.app.model.Product;
import com.erp.app.model.Resource;


public class ProductDialogController implements Initializable {
	@FXML private TableView<Resource> resourceTable;
	@FXML private TableColumn<Resource, String> nameColumn;
	@FXML private TableColumn<Resource, Integer> stackColumn;
	@FXML TextField tf1;
	@FXML TextField tf2;
	@FXML TextField tf3;
	@FXML TextField tf4;
	@FXML TextField tf5;
	@FXML TextField tf6;
	@FXML Label la1;
	@FXML Button bt3;
	private ObservableList<String> dummy = FXCollections.observableArrayList();
	private Stage dialogStage;
    private Product product;
    private MainApp mainApp;
    private boolean okClicked = false;
    Resource tempResource = new Resource();
    //Resource resource[]=new Resource[100];
    ArrayList<Resource> resource = new ArrayList<>();
    int cnt=0;
    int resourceCnt=0;
    ResultSet rs;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
		stackColumn.setCellValueFactory(cellData->cellData.getValue().stackProperty().asObject());
		//nameColumn.setCellValueFactory(product.nameProperty());
		resourceDetails(null);
		resourceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> resourceDetails(newValue));
	}
	
	public void resourceDetails(Resource resource) {
		if(resource != null) {
			la1.setText(Integer.toString(resource.getStack()));
			//combox1.getItems().add(Integer.toString(resource.getStack()));
			//combox1.setItems(dummy);
			/*for(int i=0;i<resource.getStack();i++) {
				combox1.getItems().add(Integer.toString(i+1));	
			}*/
			
			//nameLabel.setText(resource.getName());
			//idLabel.setText(Integer.toString(resource.getID()));
			//priceLabel.setText(Integer.toString(resource.getPrice()));
			//stackLabel.setText(Integer.toString(resource.getStack()));
		} else {
			//nameLabel.setText("");
			//idLabel.setText("");
			//priceLabel.setText("");
			//stackLabel.setText("");
		}
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	public void setProduct(Product product) {
        this.product = product;

        tf1.setText(product.getName());
        tf2.setText(product.getCategory());
        tf3.setText(Integer.toString(product.getSellingPrice()));
        tf4.setText(Integer.toString(product.getStack()));
        tf5.setText(Integer.toString(product.getProductionCost()));
    }
	public void setResource(ArrayList<Resource> resource) {
		this.resource = resource;
		
	}
	public boolean isOkClicked() {
        return okClicked;
    }
	@FXML
    private void handleOk() {
		product.setName(tf1.getText());
		product.setCategory(tf2.getText());
		product.setSellingPrice(Integer.parseInt(tf3.getText()));
		product.setStack(Integer.parseInt(tf4.getText()));
		product.setProductionCost(Integer.parseInt(tf5.getText()));
		int id;
		int stack;
		try {
			rs = mainApp.search("select productID from product where productName = 'dummy';");
			if(rs.next()) {
				id = rs.getInt(1);
				product.setID(id);
				for(int i = 0; i < resource.size(); i++) {
					mainApp.insert("insert into product_resource(productID, resourceID, resourceName, price, stack) values(" 
					+ id + ", " + resource.get(i).getID() + ", '" + resource.get(i).getName() + "', " + resource.get(i).getPrice()
					+ ", " + resource.get(i).getStack() + ");");
					/*mainApp.insert("update client set clientID = " + selectedClient.getID() 
		    		+ ", clientName = '" + selectedClient.getName() + "', corporate_registration_number = '"
		    		+ selectedClient.getCorporateNumber() + "', ceoName = '" + selectedClient.getCeoName() + "', ceoSocialNumber = '"
		    		+ selectedClient.getSN() + "', addressCode = " + selectedClient.getAddressCode() + ", phoneNumber = '" + selectedClient.getPhoneNumber()
		    		+ "', account = '" + selectedClient.getAccount() + "' where clientID = " + selectedClient.getID() + ";");*/
					rs=mainApp.search("select stack from resource where resourceID = " + resource.get(i).getID() + ";");
					if(rs.next()) {
						mainApp.insert("update resource set stack = " + (rs.getInt(1) - resource.get(i).getStack()) + " where resourceID = "
								+ resource.get(i).getID() + ";");
					}
				}
			} 
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		for(int i = 0; i<cnt;i++) {
			System.out.println("사용된 재료 : "+resource.get(i).getName() + ", " + resource.get(i).getStack());
			for(int k = 0; k<resourceCnt; k++) {
				Resource selectedResource = mainApp.getResourceData().get(k);
				if(resource.get(i).getName().equals(selectedResource.getName())) {
					selectedResource.subStack(resource.get(i).getStack());
					//System.out.println("빼기 성공");  //테스트용도
					break;
				} else {
					//System.out.println("빼기 실패"); //테스트 용
				}
			}
		}
		cnt = 0;
        okClicked = true;
        dialogStage.close();
    }
	@FXML
    private void handleCancel() {
        dialogStage.close();
    }
	@FXML
	private void handleSetStack() {
		Resource selectedResource = resourceTable.getSelectionModel().getSelectedItem();
		
		resource.add(new Resource(selectedResource.getName(), selectedResource.getID(), selectedResource.getPrice(), Integer.parseInt(tf6.getText())));
		cnt++;
		int temp = Integer.parseInt(tf5.getText());
		tf5.setText(Integer.toString(temp + Integer.parseInt(tf6.getText())*selectedResource.getPrice()));
		tf3.setText(Integer.toString((int)(Integer.parseInt(tf5.getText())*1.3)));
	}
	
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        resourceCnt = mainApp.getResourceData().size();
        ObservableList<Resource> temp = FXCollections.observableArrayList();
        if(!product.getMaterial().isEmpty()) {
        	for(int i=0;i<product.getMaterial().size();i++) {
        		temp.add(product.getMaterial().get(i));
        	}
            resourceTable.setItems(temp);
        } else {
        	resourceTable.setItems(mainApp.getResourceData());
        }

    }
}
