package com.erp.app.model;

import javafx.beans.property.*;


public class Manufacturing {
	private IntegerProperty manufacID;
	private Product product;
	private IntegerProperty sellingCost;
	private IntegerProperty productionCost;
	private IntegerProperty stack;
	private StringProperty manufactured;
	private StringProperty color;
	private StringProperty startDate;
	private StringProperty endDate;
	
	public Manufacturing() {
		this.manufacID = new SimpleIntegerProperty();
		this.product = new Product();
		this.stack = new SimpleIntegerProperty();
		this.productionCost = new SimpleIntegerProperty();
		this.sellingCost = new SimpleIntegerProperty();
		this.manufactured = new SimpleStringProperty();
		this.color = new SimpleStringProperty();
		this.startDate = new SimpleStringProperty();
		this.endDate = new SimpleStringProperty();
	}
	
	public Manufacturing(int id, Product product, int stack, String manufactured, String color, int selling, int production, String sDate, String eDate) {
		this.manufacID = new SimpleIntegerProperty(id);
		this.product = product;
		this.stack = new SimpleIntegerProperty(stack);
		this.productionCost = new SimpleIntegerProperty(production);
		this.sellingCost = new SimpleIntegerProperty(selling);
		this.manufactured = new SimpleStringProperty(manufactured);
		this.color = new SimpleStringProperty(color);
		this.startDate = new SimpleStringProperty(sDate);
		this.endDate = new SimpleStringProperty(eDate);
	}
	
	public IntegerProperty getIDProperty() {
		return manufacID;
	}
	
	public IntegerProperty getStackProperty() {
		return stack;
	}
	
	public StringProperty getManufacturedProperty() {
		return manufactured;
	}
	
	public StringProperty getColorProperty() {
		return color;
	}
	public IntegerProperty getSellingPriceProperty() {
		return sellingCost;
	}
	public IntegerProperty getProductionCostProperty() {
		return productionCost;
	}
	public StringProperty getStartDateProperty() {
		return startDate;
	}
	public StringProperty getEndDateProperty() {
		return endDate;
	}
	public String getStartDate() {
		return startDate.get();
	}
	public String getEndDate() {
		return endDate.get();
	}
	
	public int getID() {
		return manufacID.get();
	}
	
	public int getStack() {
		return stack.get();
	}
	
	public String getManufactured() {
		return manufactured.get();
	}
	
	public Product getProduct() {
		return product;
	}
	
	public String getColor() {
		return color.get();
	}
	
	public int getSellingPrice() {
		return sellingCost.get();
	}
	
	public int getProductionCost() {
		return productionCost.get();
	}
	
	public <T> void set(String s, T t) {
		if(s.equals("id")) {
			this.manufacID.set((int)t);
		} else if(s.equals("stack")) {
			this.stack.set((int)t);
		} else if(s.equals("product")) {
			this.product = (Product)t;
		} else if(s.equals("manufactured")) {
			this.manufactured.set((String)t);
		} else if(s.equals("color")) {
			this.color.set((String) t);
		} else if(s.equals("selling")) {
			this.sellingCost.set((int)t);
		} else if(s.equals("production")) {
			this.productionCost.set((int)t);
		} else if(s.equals("start")) {
			this.startDate.set((String)t);
		} else if(s.equals("end")) {
			this.endDate.set((String)t);
		} else {
			System.out.println(s+" is unknown keyword");
		}
	}
}
