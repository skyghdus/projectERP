package com.erp.app.model;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//import com.erp.app.model.Resource;

/**
 * �젣�뭹 紐⑤뜽 �겢�옒�뒪
 * @author jeonghoyeon
 *
 */
public class Product {
	private final StringProperty productName;
	private final IntegerProperty productID;
	private ArrayList<Resource> material = new ArrayList<>();
	private IntegerProperty sellingPrice;
	private int productionCost;
	private IntegerProperty stack;
	private StringProperty category;
	
	public Product() {
		this.productName = new SimpleStringProperty();
		this.productID = new SimpleIntegerProperty();
		this.sellingPrice = new SimpleIntegerProperty();
		//this.material = new Resource[100];
		this.productionCost = 0;
		this.stack = new SimpleIntegerProperty();
		this.category = new SimpleStringProperty();
	}
	public Product(String name, int id, int stack, ArrayList<Resource> arr, String category) {
		this.productName = new SimpleStringProperty(name);
		this.productID = new SimpleIntegerProperty(id);
		//this.material=new Resource[arr.length];
		for(int i=0;i<arr.size();i++) {
			this.material.add(arr.get(i));
			//this.material.set(i, arr[i]);
		}
		//material.add(e)
		for(int i=0;i<material.size();i++) {
			this.productionCost = this.productionCost + (material.get(i).getPrice()*material.get(i).getStack());
		}
		this.sellingPrice = new SimpleIntegerProperty((int) (this.productionCost*1.3));
		this.stack = new SimpleIntegerProperty(stack);
		this.category = new SimpleStringProperty(category);
	}
	public Product(String name, int id, ArrayList<Resource> arr) {
		this.productName = new SimpleStringProperty(name);
		this.productID = new SimpleIntegerProperty(id);
		//this.material=new Resource[arr.length];
		for(int i=0;i<arr.size();i++) {
			this.material.add(arr.get(i));
			//this.material.set(i, arr[i]);
		}
		//material.add(e)
		for(int i=0;i<material.size();i++) {
			this.productionCost = this.productionCost + (material.get(i).getPrice()*material.get(i).getStack());
		}
		this.sellingPrice = new SimpleIntegerProperty((int) (this.productionCost*1.3));
		this.stack = new SimpleIntegerProperty();
	}
	public Product(String name, int id, int price, int cost, int stack) {
		this.productName = new SimpleStringProperty(name);
		this.productID = new SimpleIntegerProperty(id);
		this.sellingPrice = new SimpleIntegerProperty(price);
		this.productionCost = cost;
		this.stack = new SimpleIntegerProperty(stack);
	}
	
	public StringProperty nameProperty() {
        return productName;
    }
	public IntegerProperty idProperty() {
        return productID;
    }
	public IntegerProperty priceProperty() {
        return sellingPrice;
    }
	public IntegerProperty stackProperty() {
        return stack;
    }
	public StringProperty categoryProperty() {
		return category;
	}
	
	public String getCategory() {
		return category.get();
	}
	
	public void setCategory(String s) {
		this.category.set(s);
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
	
	public ArrayList<Resource> getMaterial() {
		return material;
	}
	
	public void setMaterial(ArrayList<Resource> arr) {
		this.material.clear();
		for(int i = 0; i < arr.size() ; i++) {
			this.material.add(arr.get(i));
		}
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
