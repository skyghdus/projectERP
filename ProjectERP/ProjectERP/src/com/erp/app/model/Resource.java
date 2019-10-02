package com.erp.app.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * �옱猷� 紐⑤뜽 �겢�옒�뒪 
 * @author jeonghoyeon
 *
 */

public class Resource {
	private IntegerProperty standard;
	private final StringProperty resourceName;
	private final IntegerProperty resourceID;
	private IntegerProperty price;
	private IntegerProperty stack;
	private StringProperty category;
	
	public Resource() {
		this.standard = new SimpleIntegerProperty();
		this.resourceName = new SimpleStringProperty();
		this.resourceID = new SimpleIntegerProperty();
		this.price = new SimpleIntegerProperty();
		this.stack = new SimpleIntegerProperty();
		this.category = new SimpleStringProperty();
	}
	
	public Resource(String resourceName, int resourceID, int price, int stack, String category, int n) {
		this.standard = new SimpleIntegerProperty(n);
		this.resourceName = new SimpleStringProperty(resourceName);
		this.resourceID = new SimpleIntegerProperty(resourceID);
		this.price = new SimpleIntegerProperty(price);
		this.stack = new SimpleIntegerProperty(stack);
		this.category = new SimpleStringProperty(category);
	}
	public Resource(String resourceName, int resourceID, int price, int stack) {
		this.resourceName = new SimpleStringProperty(resourceName);
		this.resourceID = new SimpleIntegerProperty(resourceID);
		this.price = new SimpleIntegerProperty(price);
		this.stack = new SimpleIntegerProperty(stack);
		//this.category = new SimpleStringProperty(category);
	}
	
	public IntegerProperty standardProperty() {
		return standard;
	}

	public StringProperty nameProperty() {
        return resourceName;
    }
	public IntegerProperty idProperty() {
        return resourceID;
    }
	public IntegerProperty priceProperty() {
        return price;
    }
	public IntegerProperty stackProperty() {
        return stack;
    }
	
	public StringProperty categoryProperty() {
		return category;
	}
	
	public int getStandard() {
		return standard.get();
	}
	
	public void setStandard(int n) {
		this.standard.set(n);
	}
	
	public String getCategory() {
		return category.get();
	}
	
	public void setCategory(String s) {
		this.category.set(s);
	}
	
	public String getName() {
		return resourceName.get();
	}
	
	public void setName(String name) {
		this.resourceName.set(name);
	}
	
	public int getID() {
		return resourceID.get();
	}
	
	public void setID(int id) {
		this.resourceID.set(id);
	}
	
	public int getPrice() {
		return price.get();
	}
	
	public void setPrice(int price) {
		this.price.set(price);
	}
	
	public int getStack() {
		return stack.get();
	}
	public void setStack(int stack) {
		this.stack.set(stack);
	}
	public void subStack(int n) {
		this.stack.set(getStack() - n);
	}
	
  }
