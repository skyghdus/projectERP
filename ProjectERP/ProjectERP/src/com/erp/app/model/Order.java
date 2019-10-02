package com.erp.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
	private IntegerProperty orderID;
	private final StringProperty corporate_registration_number;
	private final StringProperty productName;
	private Product product;
	private Client client;
	private IntegerProperty stack;
	private IntegerProperty price;
	private StringProperty orderDate;
	private StringProperty etc;
	
	public Order() {
		this.orderID = new SimpleIntegerProperty();
		this.corporate_registration_number = new SimpleStringProperty();
		this.productName = new SimpleStringProperty();
		this.client = new Client();
		this.product = new Product();
		this.stack = new SimpleIntegerProperty();
		this.price = new SimpleIntegerProperty();
		this.orderDate = new SimpleStringProperty();
		this.etc = new SimpleStringProperty();
	}
	
	public Order(int id, String crn, String productName, int stack, int price, String orderDate, String etc, Product product, Client client) {
		this.orderID = new SimpleIntegerProperty(id);
		this.corporate_registration_number = new SimpleStringProperty(crn);
		this.productName = new SimpleStringProperty(productName);
		this.client = client;
		this.product = product;
		this.stack = new SimpleIntegerProperty(stack);
		this.price = new SimpleIntegerProperty(product.getSellingPrice()*stack);
		this.orderDate = new SimpleStringProperty(orderDate);
		this.etc = new SimpleStringProperty(etc);
	}
	
	public IntegerProperty idProperty() {
		return orderID;
	}
	
	public StringProperty crnProperty() {
		return corporate_registration_number;
	}
	
	public StringProperty productNameProperty() {
		return productName;
	}
	
	public StringProperty productProperty() { //product ��ü �̸�
		return product.nameProperty();
	}
	
	public IntegerProperty stackProperty() {
		return stack;
	}
	
	public IntegerProperty priceProperty() {
		return price;
	}
	
	public StringProperty orderDateProperty() {
		return orderDate;
	}
	
	public StringProperty etcProperty() {
		return etc;
	}
	
	public int getID() {
		return orderID.get();
	}
	public void setID(int id) {
		this.orderID.set(id);
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	public Product getProduct() {
		return product;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getCRN() {
		return corporate_registration_number.get();
	}
	
	public void setCRN(String crn) {
		this.corporate_registration_number.set(crn);
	}
	
	public String getProductName() {
		return productName.get();
	}
	
	public void setProductName(String name) {
		this.productName.set(name);
	}
	
	public int getStack() {
		return stack.get();
	}
	
	public void setStack(int stack) {
		this.stack.set(stack);
	}
	
	public int getPrice() {
		return price.get();
	}
	
	public void setPrice(int price) {
		this.price.set(price);
	}
	
	public String getOrderDate() {
		return orderDate.get();
	}
	
	public void setOrderDate(String date) {
		this.orderDate.set(date);
	}
	
	public String getETC() {
		return etc.get();
	}
	
	public void setETC(String str) {
		this.etc.set(str);
	}
	
	public String getCeoName() {
		return client.getCeoName();
	}
}
