package com.erp.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
	private final IntegerProperty c_ID;
	private final StringProperty c_name;
	private final StringProperty corporate_registration_number; //string
	private StringProperty ceoName;
	private StringProperty c_SN; //string
	private IntegerProperty addressCode;
	private StringProperty address;
	private StringProperty phoneNumber;  //string
	private StringProperty account;  //string
	private StringProperty lastOrder;
	
	
	public Client() {
		this.c_ID = new SimpleIntegerProperty();
		this.c_name = new SimpleStringProperty();
		this.corporate_registration_number = new SimpleStringProperty();
		this.ceoName = new SimpleStringProperty();
		this.c_SN = new SimpleStringProperty();
		this.addressCode = new SimpleIntegerProperty();
		this.address = new SimpleStringProperty();
		this.phoneNumber = new SimpleStringProperty();
		this.account = new SimpleStringProperty();
		this.lastOrder = new SimpleStringProperty();
	}
	
	public Client(int id, String name, String corporate_n, String ceoName, String sn, int addressCode, String address, String phone, String account, String lastOrder) {
		this.c_ID = new SimpleIntegerProperty(id);
		this.c_name = new SimpleStringProperty(name);
		this.corporate_registration_number = new SimpleStringProperty(corporate_n);
		this.ceoName = new SimpleStringProperty(ceoName);
		this.c_SN = new SimpleStringProperty(sn);
		this.addressCode = new SimpleIntegerProperty(addressCode);
		this.address = new SimpleStringProperty(address);
		this.phoneNumber = new SimpleStringProperty(phone);
		this.account = new SimpleStringProperty(account);
		if(lastOrder!=null) {
			this.lastOrder = new SimpleStringProperty(lastOrder);
		} else {
			this.lastOrder = new SimpleStringProperty("1900-01-01");
		}
	}
	
	public StringProperty nameProperty() {
        return c_name;
    }
	public IntegerProperty idProperty() {
        return c_ID;
    }
	public StringProperty snProperty() {
        return c_SN;
    }
	public StringProperty ceoNameProperty() {
		return ceoName;
	}
	public StringProperty addressProperty() {
        return address;
    }
	public IntegerProperty addressCodeProperty() {
        return addressCode;
    }
	public StringProperty accountProperty() {
        return account;
    }
	public StringProperty phProperty() {
        return phoneNumber;
    }
	public StringProperty corporateNumberProperty() {
        return corporate_registration_number;
    }
	public StringProperty lastOrderProperty() {
		if(lastOrder.get().equals("1900-01-01")) {
			return new SimpleStringProperty("최근 주문일자 없음");
		} else {
			return lastOrder;
		}
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
	
	public String getSN() {
		return c_SN.get();
	}
	
	public void setSN(String id) {
		this.c_SN.set(id);
	}
	
	public String getCeoName() {
		return ceoName.get();
	}
	
	public void setCeoName(String name) {
		this.ceoName.set(name);
	}
	
	public String getAddress() {
		return address.get();
	}
	
	public void setAddress(String address) {
		this.address.set(address);
	}
	
	public int getAddressCode() {
		return addressCode.get();
	}
	
	public void setAddressCode(int code) {
		this.addressCode.set(code);
	}
	
	public String getAccount() {
		return account.get();
	}
	
	public void setAccount(String account) {
		this.account.set(account);
	}
	
	public String getPhoneNumber() {
		return phoneNumber.get();
	}
	
	public void setPhoneNumber(String num) {
		this.phoneNumber.set(num);
	}
	
	public String getCorporateNumber() {
		return corporate_registration_number.get();
	}
	
	public void setCorporateNumber(String num) {
		this.corporate_registration_number.set(num);
	}
	
	public String getLastOrder() {
		return lastOrder.get();
	}
	
	public void setLastOrder(String lastOrder) {
		this.lastOrder.set(lastOrder);
	}
}
