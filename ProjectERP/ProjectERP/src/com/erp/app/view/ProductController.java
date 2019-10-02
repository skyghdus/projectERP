package com.erp.app.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import com.erp.app.MainApp;
import com.erp.app.model.Product;
import com.erp.app.model.Resource;

import javafx.fxml.Initializable;

public class ProductController implements Initializable {
	@FXML private TableView<Product> resourceTable;
	@FXML private TableColumn<Product, String> nameColumn;
	@FXML private TableColumn<Product, Integer> idColumn;
	@FXML private TableColumn<Product, String> categoryColumn;
	@FXML private Label nameLabel;
	@FXML private Label idLabel;
	@FXML private Label priceLabel;
	@FXML private Label stackLabel;
	@FXML private Label costLabel;
	@FXML private TextArea searchTa;
	
	private MainApp mainApp;
	public ProductController() {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
		idColumn.setCellValueFactory(cellData->cellData.getValue().idProperty().asObject());
		categoryColumn.setCellValueFactory(cellData->cellData.getValue().categoryProperty());
		productDetails(null);
		resourceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> productDetails(newValue));
	}
	public void productDetails(Product product) {
		if(product != null) {
			nameLabel.setText(product.getName());
			idLabel.setText(Integer.toString(product.getID()));
			priceLabel.setText(Integer.toString(product.getSellingPrice()));
			stackLabel.setText(Integer.toString(product.getStack()));
			costLabel.setText(Integer.toString(product.getProductionCost()));
		} else {
			nameLabel.setText("");
			idLabel.setText("");
			priceLabel.setText("");
			stackLabel.setText("");
			costLabel.setText("");
		}
	}
	
	@FXML
	private void searchButton() {
		int i;
		for(i=0;i<mainApp.getProductData().size();i++) {
			if(mainApp.getProductData().get(i).getName().equals(searchTa.getText())) {
				break;
			}
		}
		resourceTable.getSelectionModel().select(mainApp.getProductData().get(i));
		
	}
	
	@FXML
	private void handleDeleteProduct() {
	    int selectedIndex = resourceTable.getSelectionModel().getSelectedIndex();
	    
	    mainApp.insert("delete from product_resource where productID = " + resourceTable.getSelectionModel().getSelectedItem().getID() +";");
	    mainApp.insert("delete from product where productID = " + resourceTable.getSelectionModel().getSelectedItem().getID() + ";");
	    resourceTable.getItems().remove(selectedIndex);
	}
	
	@FXML
	private void handleNewProduct() {
	    Product tempProduct = new Product();
	    //Resource tempResource = new Resource();
	    mainApp.insert("insert into product(productName, sellingPrice, productCost, stack) values('dummy', 0, 0, 0);");
	    boolean okClicked = mainApp.showProductEditDialog(tempProduct);
	    if (okClicked) {
	    	try {
	    		mainApp.insert("update product set productName = '" + tempProduct.getName() + "', sellingPrice = " 
	    		+ tempProduct.getSellingPrice() + ", productCost = " + tempProduct.getProductionCost() + ", stack = " 
	    		+ tempProduct.getStack() + ", category = '" + tempProduct.getCategory() + "' where productName = 'dummy';");
	    		mainApp.getProductData().add(tempProduct);
	    	} catch(Exception e) {
	    		System.out.println("update product set productName = '" + tempProduct.getName() + "', sellingPrice = " 
	    	    		+ tempProduct.getSellingPrice() + ", productCost = " + tempProduct.getProductionCost() + ", stack = " 
	    	    		+ tempProduct.getStack() + " where productName = 'dummy';" + e.getMessage());
	    	}
	    } else {
	    	mainApp.insert("delete from product where productName ='dummy';");
	    	mainApp.insert("alter table product auto_increment=1;");
	    	//더미 삭제
	    }
	}
	@FXML
	private void handleEditProduct() {
	    Product selectedProduct = resourceTable.getSelectionModel().getSelectedItem();
	    
	    if (selectedProduct != null) {
	        boolean okClicked = mainApp.showProductEditDialog(selectedProduct);
	        if (okClicked) {
	        	try {
	        		mainApp.insert("update product set productName = '" + selectedProduct.getName() + "', sellingPrice = " 
	        	    		+ selectedProduct.getSellingPrice() + ", productCost = " + selectedProduct.getProductionCost() + ", stack = " 
	        	    		+ selectedProduct.getStack() + ", category = '" + selectedProduct.getCategory() + "' where productID = " + selectedProduct.getID()+ ";");
	        		productDetails(selectedProduct);
	        	} catch(Exception e) {
	        		System.out.println(e.getMessage());
	        	}
	            
	        }

	    } else {
	        // 占쎈툡�눧�떯苡э옙猷� 占쎄퐨占쎄문占쎈릭筌욑옙 占쎈륫占쎈릭占쎈뼄.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Product Selected");
	        alert.setContentText("Please select a product in the table.");

	        alert.showAndWait();
	    }
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // 占쎈�믭옙�뵠�뇡遺용퓠 observable �뵳�딅뮞占쎈뱜 占쎈쑓占쎌뵠占쎄숲�몴占� �빊遺쏙옙占쎈립占쎈뼄.
        resourceTable.setItems(mainApp.getProductData());
        
    }

}
