package com.test.app;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 제품 모델 클래스
 * @author jeonghoyeon
 *
 */
public class Product {
	private final StringProperty productName;
	private final IntegerProperty productID;
	private Resource[] material;
	private IntegerProperty sellingPrice;
	private int productionCost;
	private IntegerProperty stack;
	
	public Product(String name, int id, Resource[] arr) {
		this.productName = new SimpleStringProperty(name);
		this.productID = new SimpleIntegerProperty(id);
		for(int i=0;i<arr.length;i++) {
			this.material[i] = arr[i];
		}
		for(int i=0;i<material.length;i++) {
			this.productionCost = this.productionCost + material[i].getPrice();
		}
		this.sellingPrice = new SimpleIntegerProperty((int) (this.productionCost*1.3));
		this.stack = new SimpleIntegerProperty(0);
	}
	
	public String getName() {
		return productName.get();
	}
	
	public void setName(String name) {
		this.productName.set(name);
	}
	
	public int getID() {
		return productID.get();
	}
	
	public void setID(int id) {
		this.productID.set(id);
	}
	
	public Resource[] getResource() {
		return material;
	}
	
	public void setResource(Resource[] arr) {
		this.material=arr;
	}
	
	public int getSellingPrice() {
		return sellingPrice.get();
	}
	
	public void setSellingPrice(int price) {
		this.sellingPrice.set(price);
	}
	
	public int getProductionCost() {
		return productionCost;
	}
	
	public void setProductionCost(int cost) {
		this.productionCost = cost;
	}
	
	public int getStack() {
		return stack.get();
	}
	
	public void setStack(int stack) {
		this.stack.set(stack);
	}
	
	
}
