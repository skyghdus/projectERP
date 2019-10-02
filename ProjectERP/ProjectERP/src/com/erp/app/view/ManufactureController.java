package com.erp.app.view;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import com.erp.app.MainApp;
import com.erp.app.model.Client;
import com.erp.app.model.Manufacturing;
import com.erp.app.model.Order;
import com.erp.app.model.Product;
import com.erp.app.model.Resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;

public class ManufactureController implements Initializable {
	@FXML Label costla;
	@FXML Label costla2;
	@FXML Label manufactured;
	@FXML ComboBox<String> cb;
	@FXML TextField tf1;
	@FXML ColorPicker cp;
	@FXML TableView<Manufacturing> manufactTable;
	@FXML TableColumn<Manufacturing, String> nameColumn;
	@FXML TableColumn<Manufacturing, Integer> sellingPriceColumn;
	@FXML TableColumn<Manufacturing, Integer> productionCostColumn;
	@FXML TableColumn<Manufacturing, Integer> stackColumn;
	@FXML TableColumn<Manufacturing, String> colorColumn;
	@FXML TableColumn<Manufacturing, String> manufacturedColumn;
	@FXML TableColumn<Manufacturing, String> startColumn;
	@FXML TableColumn<Manufacturing, String> endColumn;
	private MainApp mainApp;
	ObservableList<Product> productData = FXCollections.observableArrayList();
	ObservableList<Resource> resourceData = FXCollections.observableArrayList();
	Product tempProduct;
	private ResultSet rs;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		nameColumn.setCellValueFactory(cellData->cellData.getValue().getProduct().nameProperty());
		sellingPriceColumn.setCellValueFactory(cellData->cellData.getValue().getSellingPriceProperty().asObject());
		productionCostColumn.setCellValueFactory(cellData->cellData.getValue().getProductionCostProperty().asObject());
		manufacturedColumn.setCellValueFactory(cellData->cellData.getValue().getManufacturedProperty());
		stackColumn.setCellValueFactory(cellData->cellData.getValue().getStackProperty().asObject());
		colorColumn.setCellValueFactory(cellData->cellData.getValue().getColorProperty());
		startColumn.setCellValueFactory(cellData->cellData.getValue().getStartDateProperty());
		endColumn.setCellValueFactory(cellData->cellData.getValue().getEndDateProperty());
		manufactDetails(null);
		manufactTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> manufactDetails(newValue));
	}
	
	public void manufactDetails(Manufacturing m) {
		if(m != null) {
			cb.getSelectionModel().select(m.getProduct().getName());
			costla.setText(Integer.toString(m.getSellingPrice()));
			costla2.setText(Integer.toString(m.getProductionCost()));
			tf1.setText(Integer.toString(m.getStack()));
			manufactured.setText(m.getManufactured());
			cp.setValue(Color.web(m.getColor()));
		} else {
			costla.setText("");
			costla2.setText("");
			manufactured.setText("");
		}
	}
	
	@FXML
	public void selectProduct() {
		String str1 = cb.getValue();
		int i = 0;
		for(i=0;i<productData.size();i++) {
			if(str1.equals(productData.get(i).getName())) {
				break;
			}
		}
		
		tempProduct = productData.get(i);
		//System.out.println(tempProduct.getStack());
		//tempProduct.setStack(50);
		costla.setText(Integer.toString(tempProduct.getSellingPrice()));
		costla2.setText(Integer.toString(tempProduct.getProductionCost()));
		
	}
	@FXML
	public void delete() {
		Manufacturing selectedManufact = manufactTable.getSelectionModel().getSelectedItem();
		 int selectedIndex = manufactTable.getSelectionModel().getSelectedIndex();
		try {
			mainApp.insert("delete from manufacturing where manufacID = " + selectedManufact.getID() + ";");
			manufactTable.getItems().remove(selectedIndex);
			//mainApp.getManufactData().remove(selectedIndex);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@FXML
	public void endButton() {
		Calendar cal = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		Formatter f = new Formatter(sb);
		f.format("%tY-%tm-%te", cal, cal, cal);
		String str1 = cb.getValue();
		int i = 0;
		for(i=0;i<productData.size();i++) {
			if(str1.equals(productData.get(i).getName())) {	
				break;
			}
		}
		tempProduct = productData.get(i);
		
		Manufacturing selectedManufact = manufactTable.getSelectionModel().getSelectedItem();
		ArrayList<Integer> nums = new ArrayList<>();
		ArrayList<Integer> stacks = new ArrayList<>();
		if(manufactured.getText().equals("No") || manufactured.getText().equals("no")) {
			try {
				mainApp.insert("update manufacturing set manufactured = 'Yes', endDate = '" + f.toString() + "' where manufacID = " + selectedManufact.getID() + ";");
				selectedManufact.set("end", f.toString());
				selectedManufact.set("manufactured", "Yes");
				tempProduct.setStack(tempProduct.getStack()+selectedManufact.getStack());
				mainApp.insert("update product set stack = " + tempProduct.getStack() + " where productID = " + tempProduct.getID() + ";");
				manufactured.setText("Yes");
				rs = mainApp.search("select * from product_resource where productID = " + tempProduct.getID() + ";");
				while(rs.next()) {
					nums.add(rs.getInt(2));
					stacks.add(rs.getInt(5));
				}
				for(i = 0; i < nums.size(); i++) {
					int k = 0;
					mainApp.insert("update resource set stack = stack - " + stacks.get(i) * selectedManufact.getStack() 
							+ " where resourceID = " + nums.get(i) + ";");
					for(k=0;k<resourceData.size();k++) {
						if(resourceData.get(k).getID() == nums.get(i)) {
							break;
						}
					}
					resourceData.get(k).setStack(resourceData.get(k).getStack() - stacks.get(i)*selectedManufact.getStack());
					
				}
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	@FXML
	public void startButton() {
		Calendar cal = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		Formatter f = new Formatter(sb);
		f.format("%tY-%tm-%te", cal, cal, cal);
		try {
			mainApp.insert("insert into manufacturing(productID, manufactured, stack, color, startDate) values("+tempProduct.getID()+", 'No', "
					+ Integer.parseInt(tf1.getText()) + ", '" + cp.getValue().toString() + "', '" + f.toString() + "');");
			rs = mainApp.search("select manufacID from manufacturing where productID = " + tempProduct.getID() + " and stack = " 
					+ Integer.parseInt(tf1.getText()) + " and color = '" + cp.getValue().toString() + "' and startDate = '" 
					+ f.toString() + "';");
			if(rs.next()) {
				int rid = rs.getInt(1);
				mainApp.getManufactData().add(new Manufacturing(rid, tempProduct, Integer.parseInt(tf1.getText()), "No", cp.getValue().toString(), 
						Integer.parseInt(costla.getText()), Integer.parseInt(costla2.getText()), f.toString(), ""));
			}
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        
        productData = FXCollections.observableArrayList(mainApp.getProductData());
        resourceData = FXCollections.observableArrayList(mainApp.getResourceData());
        for(int i=0;i<productData.size();i++) {
        	cb.getItems().add(productData.get(i).getName());
        }

        // �뀒�씠釉붿뿉 observable 由ъ뒪�듃 �뜲�씠�꽣瑜� 異붽��븳�떎.
        manufactTable.setItems(mainApp.getManufactData());
    }

}
