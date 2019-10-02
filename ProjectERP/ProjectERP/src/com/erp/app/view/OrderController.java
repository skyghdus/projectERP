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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import com.erp.app.MainApp;
import com.erp.app.model.Client;
import com.erp.app.model.Order;
import com.erp.app.model.Product;
import com.erp.app.model.Resource;
import java.util.Calendar;
import java.util.Formatter;

public class OrderController implements Initializable {
	@FXML TextField crntf;
	@FXML Label ceoname;
	@FXML ComboBox<String> cb1;
	@FXML TextField stacktf;
	@FXML TextField etctf;
	@FXML Label orderOK;
	@FXML Label pricela;
	//@FXML Label noStack;
	//@FXML Label maxStack;
	//@FXML Slider slider;
	@FXML private DatePicker dp;
	@FXML ComboBox<String> cb2;
	@FXML private TableView<Order> orderTable;
	@FXML private TableColumn<Order, String> crnColumn;
	@FXML private TableColumn<Order, String> productNameColumn;
	@FXML private TableColumn<Order, Integer> stackColumn;
	@FXML private TableColumn<Order, Integer> priceColumn;
	@FXML private TableColumn<Order, String> orderDateColumn;
	@FXML private TableColumn<Order, String> etcColumn;
	private MainApp mainApp;
	ObservableList<Product> productData = FXCollections.observableArrayList();
	ObservableList<Client> dummy = FXCollections.observableArrayList();
	Client tempClient;
	Product tempProduct;
	Order order;
	ResultSet rs;

	public OrderController() {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		crnColumn.setCellValueFactory(cellData->cellData.getValue().crnProperty());
		productNameColumn.setCellValueFactory(cellData->cellData.getValue().productNameProperty());
		stackColumn.setCellValueFactory(cellData->cellData.getValue().stackProperty().asObject());
		priceColumn.setCellValueFactory(cellData->cellData.getValue().priceProperty().asObject());
		orderDateColumn.setCellValueFactory(cellData->cellData.getValue().orderDateProperty());
		etcColumn.setCellValueFactory(cellData->cellData.getValue().etcProperty());
		//slider.setMax(0);
		//slider.setMin(0);
		OrderDetails(null);
		orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> OrderDetails(newValue));
	}
	
	public void OrderDetails(Order order) {
		if(order != null) {
			crntf.setText(order.getCRN());
			ceoname.setText(order.getCeoName());
			stacktf.setText(Integer.toString(order.getStack()));
			etctf.setText(order.getETC());
			orderOK.setText("");
			pricela.setText(Integer.toString(order.getPrice()));
			
		} else {
			crntf.setText("");
			ceoname.setText("");
			stacktf.setText("");
			etctf.setText("");
			orderOK.setText("");
			pricela.setText("");
		}
	}
	@FXML
	public void selectClient() {
		String str1 = cb2.getValue();
		int i=0;
		for(i=0;i<dummy.size();i++) {
			if(str1.equals(dummy.get(i).getName())) {
				break;
			}
		}
		tempClient = dummy.get(i);
		crntf.setText(tempClient.getCorporateNumber());
		ceoname.setText(tempClient.getCeoName());
	}
	@FXML
	public void selectProduct() {
		String str1 = cb1.getValue();
		int i = 0;
		for(i=0;i<productData.size();i++) {
			if(str1.equals(productData.get(i).getName())) {
				break;
			}
		}
		tempProduct = productData.get(i);
		//slider.setMax(tempProduct.getStack());
		//maxStack.setText(Integer.toString(tempProduct.getStack()));
		orderOK.setText(Integer.toString(tempProduct.getStack()));
		
	}
	/*@FXML
	public void sliderController() {
		pricela.setText(Integer.toString((int)slider.getValue() *  tempProduct.getSellingPrice()));
		noStack.setText(Integer.toString((int)slider.getValue()));
	}*/
	
	@FXML
	public void dpt() {
		System.out.println(dp.getValue().toString());
	}
	
	@FXML
	public void handleOK() {
		//날짜 받아오는 부분
		Calendar cal = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		Formatter f = new Formatter(sb);
		f.format("%tY-%tm-%te", cal, cal, cal);
		//종료
		//if(
		int id;
		tempClient.setLastOrder(f.toString());
		try {
			if(dp.getValue() == null) {
				mainApp.insert("update client set lastOrder = '" + f.toString() + "' where clientID = " + tempClient.getID() + ";");
				mainApp.insert("insert into orderTable(clientID, productID, stack, orderDate, etc) values(" + tempClient.getID() + ", " 
						+ tempProduct.getID() + ", " + Integer.parseInt(stacktf.getText()) + ", '" + f.toString() + "', '" + etctf.getText() + "');");
				tempProduct.setStack(tempProduct.getStack()-Integer.parseInt(stacktf.getText()));
				mainApp.insert("update product set stack = " + tempProduct.getStack() + " where productID = " + tempProduct.getID() + ";");
				rs = mainApp.search("select orderID from orderTable where clientID = " + tempClient.getID() + " and productID = " 
				+ tempProduct.getID() + " and orderDate = '" + f.toString() + "';");
				if(rs.next()) {
					id=rs.getInt(1);
					mainApp.getOrderData().add(new Order(id, crntf.getText(), tempProduct.getName(), Integer.parseInt(stacktf.getText())
							, tempProduct.getSellingPrice()*Integer.parseInt(stacktf.getText()), f.toString(), etctf.getText()
							, tempProduct, tempClient));
				} else {
					System.out.println("orderData 저장오류");
				}
			} else {
				mainApp.insert("update client set lastOrder = '" + f.toString() + "' where clientID = " + tempClient.getID() + ";");
				mainApp.insert("insert into orderTable(clientID, productID, stack, orderDate, etc) values(" + tempClient.getID() + ", " 
						+ tempProduct.getID() + ", " + Integer.parseInt(stacktf.getText()) + ", '" + dp.getValue().toString() + "', '" + etctf.getText() + "');");
				tempProduct.setStack(tempProduct.getStack()-Integer.parseInt(stacktf.getText()));
				mainApp.insert("update product set stack = " + tempProduct.getStack() + " where productID = " + tempProduct.getID() + ";");
				rs = mainApp.search("select orderID from orderTable where clientID = " + tempClient.getID() + " and productID = " 
				+ tempProduct.getID() + " and orderDate = '" + dp.getValue().toString() + "';");
				if(rs.next()) {
					id=rs.getInt(1);
					mainApp.getOrderData().add(new Order(id, crntf.getText(), tempProduct.getName(), Integer.parseInt(stacktf.getText())
							, tempProduct.getSellingPrice()*Integer.parseInt(stacktf.getText()), dp.getValue().toString(), etctf.getText()
							, tempProduct, tempClient));
				} else {
					System.out.println("orderData 저장오류");
				}
			}
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	@FXML
	public void handleDelete() {
		try {
			int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
			mainApp.insert("delete from orderTable where orderID = " + orderTable.getSelectionModel().getSelectedItem().getID() + ";");
		    orderTable.getItems().remove(selectedIndex);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        dummy = FXCollections.observableArrayList(mainApp.getClientData());
        productData = FXCollections.observableArrayList(mainApp.getProductData());
        for(int i=0;i<dummy.size();i++) {
        	cb2.getItems().add(dummy.get(i).getName());
        }
        for(int i=0;i<productData.size();i++) {
        	cb1.getItems().add(productData.get(i).getName());
        }

        orderTable.setItems(mainApp.getOrderData());
    }
}
