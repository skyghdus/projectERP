package com.erp.app.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Staff {
	private final IntegerProperty id;
	private final StringProperty name;
	private final StringProperty birthday;
	private final IntegerProperty age;
	private StringProperty rank;
	private final StringProperty phoneNumber;
	private StringProperty address;
	private StringProperty accountNumber;
	private final StringProperty socialNumber;
	
	public Staff() {
		this.id = new SimpleIntegerProperty();
		this.name = new SimpleStringProperty();
		this.birthday = new SimpleStringProperty();
		this.age = new SimpleIntegerProperty();
		this.rank = new SimpleStringProperty();
		this.phoneNumber = new SimpleStringProperty();
		this.address = new SimpleStringProperty();
		this.accountNumber = new SimpleStringProperty();
		this.socialNumber = new SimpleStringProperty();
	}
	
	public Staff(int id, String name, String birthday, int age, String rank, String phone, String address, String account, String sc) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.birthday = new SimpleStringProperty(birthday);
		this.age = new SimpleIntegerProperty(age);
		this.rank = new SimpleStringProperty(rank);
		this.phoneNumber = new SimpleStringProperty(phone);
		this.address = new SimpleStringProperty(address);
		this.accountNumber = new SimpleStringProperty(account);
		this.socialNumber = new SimpleStringProperty(sc);
	}
	
	public IntegerProperty idProperty() {
        return id;
    }
	
	public StringProperty nameProperty() {
        return name;
    }
	
	public StringProperty birthdayProperty() {
		return birthday;
	}
	
	public IntegerProperty ageProperty() {
		return age;
	}
	
	public StringProperty rankProperty() {
		return rank;
	}
	
	public StringProperty phoneNumberProperty() {
		return phoneNumber;
	}
	
	public StringProperty addressProperty() {
		return address;
	}
	
	public StringProperty accountProperty() {
		return accountNumber;
	}
	
	public StringProperty socialNumberProperty() {
		return socialNumber;
	}
	
	public int getID() {
		return id.get();
	}
	
	public void setID(int id) {
		this.id.set(id);
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getBirthday() {
		return birthday.get();
	}
	
	public void setBirthday(String birth) {
		this.birthday.set(birth);
	}
	
	public int getAge() {
		return age.get();
	}
	
	public void setAge(int age) {
		this.age.set(age);
	}
	
	public String getRank() {
		return rank.get();
	}
	
	public void setRank(String rank) {
		this.rank.set(rank);
	}
	
	public String getPhoneNumber() {
		return phoneNumber.get();
	}
	
	public void setPhoneNumber(String number) {
		this.phoneNumber.set(number);
	}
	
	public String getAddress() {
		return address.get();
	}
	
	public void setAddress(String address) {
		this.address.set(address);
	}
	
	public String getAccountNumber() {
		return accountNumber.get();
	}
	
	public void setAccountNumber(String account) {
		this.accountNumber.set(account);
	}
	
	public String getSocialNumber() {
		return socialNumber.get();
	}
	
	public void setSocialNumber(String socialNumber) {
		this.socialNumber.set(socialNumber);
	}
}
