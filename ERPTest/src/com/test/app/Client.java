package com.test.app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
	private final StringProperty c_name;
	private final IntegerProperty c_ID;
	private final StringProperty address;
	private final IntegerProperty account;
	private final IntegerProperty corporate_registration_number;
	
	public Client(String name, int id, String address, int account, int corporate_n) {
		this.c_name = new SimpleStringProperty(name);
		this.c_ID = new SimpleIntegerProperty(id);
		this.address = new SimpleStringProperty(address);
		this.account = new SimpleIntegerProperty(account);
		this.corporate_registration_number = new SimpleIntegerProperty(corporate_n);
	}
	
	public String getName() {
		return c_name.get();
	}
	
	public void setName(String name) {
		this.c_name.set(name);
	}
	
	public int getID() {
		return c_ID.get();
	}
	
	public void setID(int id) {
		this.c_ID.set(id);
	}
	
	public String getAddress() {
		return address.get();
	}
	
	public void setAddress(String address) {
		this.address.set(address);
	}
	
	public int getAccount() {
		return account.get();
	}
	
	public void setAccount(int account) {
		this.account.set(account);
	}
	
	public int getCorporateNumber() {
		return corporate_registration_number.get();
	}
	
	public void setCorporateNumber(int num) {
		this.corporate_registration_number.set(num);
	}
}
