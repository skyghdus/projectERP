package com.test.app;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 재료 모델 클래스 
 * @author jeonghoyeon
 *
 */

public class Resource {
	private final StringProperty resourceName;
	private final IntegerProperty resourceID;
	private IntegerProperty price;
	private IntegerProperty stack;
	
	public Resource(String resourceName, int resourceID, int price, int stack) {
		this.resourceName = new SimpleStringProperty(resourceName);
		this.resourceID = new SimpleIntegerProperty(resourceID);
		this.price = new SimpleIntegerProperty(price);
		this.stack = new SimpleIntegerProperty(stack);
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
	
  }
