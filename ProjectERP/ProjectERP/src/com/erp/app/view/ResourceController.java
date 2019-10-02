package com.erp.app.view;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import com.erp.app.MainApp;
import com.erp.app.model.Resource;

import javafx.fxml.Initializable;

public class ResourceController implements Initializable {
	@FXML TableView<Resource> resourceTable;
	@FXML TableColumn<Resource, String> nameColumn;
	@FXML TableColumn<Resource, Integer> idColumn;
	@FXML TableColumn<Resource, String> Categorize;
	@FXML private TextArea searchTa;
	@FXML private Label nameLabel;
	@FXML private Label standardLabel;
	@FXML private Label priceLabel;
	@FXML private Label stackLabel;
	@FXML private PieChart pieChart;
	@FXML private BarChart<String, Integer> bChart;
	@FXML private LineChart<String, Integer> lineChart;
	@FXML private CategoryAxis bxaxis;
	@FXML private NumberAxis byaxis;
	@FXML private CategoryAxis lxaxis;
	@FXML private NumberAxis lyaxis;
	ResultSet rs;
	
	private MainApp mainApp;
	public ResourceController() {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
		idColumn.setCellValueFactory(cellData->cellData.getValue().idProperty().asObject());
		Categorize.setCellValueFactory(cellData->cellData.getValue().categoryProperty());
		resourceDetails(null);
		resourceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> resourceDetails(newValue));
		//pieChart.setData(FXCollections.observableArrayList());
	}
	public void resourceDetails(Resource resource) {
		if(resource != null) {
			nameLabel.setText(resource.getName());
			standardLabel.setText(Integer.toString(resource.getStandard()));
			priceLabel.setText(Integer.toString(resource.getPrice()));
			stackLabel.setText(Integer.toString(resource.getStack()));
		} else {
			nameLabel.setText("");
			standardLabel.setText("");
			priceLabel.setText("");
			stackLabel.setText("");
		}
	}
	
	@FXML
	private void searchButton() {
		int i;
		for(i=0;i<mainApp.getResourceData().size();i++) {
			if(mainApp.getResourceData().get(i).getName().equals(searchTa.getText())) {
				break;
			}
		}
		resourceTable.getSelectionModel().select(mainApp.getResourceData().get(i));
		
	}
	
	@FXML
	private void handleChart() {
		bChart.getData().clear();
		lineChart.getData().clear();
		Resource selectedResource = resourceTable.getSelectionModel().getSelectedItem();
		XYChart.Series<String, Integer> lineChartData = new XYChart.Series<String, Integer>();
		XYChart.Series<String, Integer> barChartData = new XYChart.Series<String, Integer>();
		ObservableList<String> lineX = FXCollections.observableArrayList();
		ObservableList<String> barX = FXCollections.observableArrayList();
		ObservableList<Integer> lineY = FXCollections.observableArrayList();
		ObservableList<Integer> barY = FXCollections.observableArrayList();
		lineX.add(selectedResource.getName());
		lineY.add(selectedResource.getStandard());
		barX.add(selectedResource.getName());
		barY.add(selectedResource.getStack());
		lineChartData.getData().add(new XYChart.Data<String, Integer>(lineX.get(0), lineY.get(0)));
		barChartData.getData().add(new XYChart.Data<String, Integer>(barX.get(0), barY.get(0)));
		lxaxis.setCategories(lineX);
		bxaxis.setCategories(barX);
		bChart.getData().add(barChartData);
		lineChart.getData().add(lineChartData);
		
	}
	
	@FXML
	private void handleBarChart() {
		bChart.getData().clear();
		lineChart.getData().clear();
		XYChart.Series<String, Integer> lineChartData = new XYChart.Series<String, Integer>();
		XYChart.Series<String, Integer> barChartData = new XYChart.Series<String, Integer>();
		ObservableList<String> lineX = FXCollections.observableArrayList();
		ObservableList<String> barX = FXCollections.observableArrayList();
		ObservableList<Integer> lineY = FXCollections.observableArrayList();
		ObservableList<Integer> barY = FXCollections.observableArrayList();
		int size = resourceTable.getItems().size();
		for(int i = 0; i<size; i++) {
			lineX.add(resourceTable.getItems().get(i).getName());
			barX.add(resourceTable.getItems().get(i).getName());
			lineY.add(resourceTable.getItems().get(i).getStandard());
			barY.add(resourceTable.getItems().get(i).getStack());
			//list.add(new PieChart.Data(resourceTable.getItems().get(i).getName(), resourceTable.getItems().get(i).getStack()));
			//lineChartData.getData().add(e)
			lineChartData.getData().add(new XYChart.Data<String, Integer>(lineX.get(i), lineY.get(i)));
			barChartData.getData().add(new XYChart.Data<String, Integer>(barX.get(i), barY.get(i)));
			
		}
		lxaxis.setCategories(lineX);
		bxaxis.setCategories(barX);
		
		//lineChartData.getData().addAll(new XYChart.Data<String, Number>());
		bChart.getData().add(barChartData);
		lineChart.getData().add(lineChartData);
		
	}
	
	@FXML
	private void handlePieChart() {
		ObservableList<Data> list = FXCollections.observableArrayList();
		int size = resourceTable.getItems().size();
		for(int i = 0; i<size; i++) {
			list.add(new PieChart.Data(resourceTable.getItems().get(i).getName(), resourceTable.getItems().get(i).getStack()));
		}
		pieChart.setData(list);
		
		//String sql = "insert into resource(name, r_id, price, stack) values('test', 4, 5, 6);";
		//mainApp.insert(sql);
		//mainApp.test();
	}
	@FXML
	private void handleDeleteResource() {
	    int selectedIndex = resourceTable.getSelectionModel().getSelectedIndex();
	    mainApp.insert("delete from resource where resourceID = " + resourceTable.getSelectionModel().getSelectedItem().getID() + ";");
	    resourceTable.getItems().remove(selectedIndex);
	    //System.out.println(selectedIndex);
	}
	
	@FXML
	private void handleNewResource() {
	    Resource tempResource = new Resource();
	    boolean okClicked = mainApp.showResourceEditDialog(tempResource);
	    if (okClicked) {
	    	try {
		    	mainApp.insert("insert into resource(resourceName, price, stack, category, standard) values('" + tempResource.getName() + "', " + tempResource.getPrice() + ", " + tempResource.getStack() + ", '" + tempResource.getCategory() + "', "+ tempResource.getStandard() +");");
		        rs = mainApp.search("select * from resource where resourceName = '" + tempResource.getName() + "' and price = " + tempResource.getPrice()
		        + " and stack = " + tempResource.getStack() + " and category = '" + tempResource.getCategory() + "';");
		        if(rs.next()) {
		        	tempResource.setID(rs.getInt(2));
		        }
		        mainApp.getResourceData().add(tempResource);
	    	} catch(Exception e) {
	    		System.out.println("select * from resource where resourceName = '" + tempResource.getName() + "'; : " + e.getMessage());
	    	}
	    }
	}
	@FXML
	private void handleEditResource() {
	    Resource selectedResource = resourceTable.getSelectionModel().getSelectedItem();
	    if (selectedResource != null) {
	        boolean okClicked = mainApp.showResourceEditDialog(selectedResource);
	        if (okClicked) {
	        	try {
	        		mainApp.insert("update resource set resourceName = '" + selectedResource.getName() + "', category = '" + selectedResource.getCategory() + "', price = " + selectedResource.getPrice() + ", stack = " + selectedResource.getStack() + ", standard = " + selectedResource.getStandard() + " where resourceID = " + selectedResource.getID() + ";");
	        	} catch(Exception e) {
	        		System.out.println(e.getMessage());
	        	}
	            resourceDetails(selectedResource);
	        }

	    } else {
	        // 占쎈툡�눧�떯苡э옙猷� 占쎄퐨占쎄문占쎈릭筌욑옙 占쎈륫占쎈릭占쎈뼄.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Person Selected");
	        alert.setContentText("Please select a person in the table.");

	        alert.showAndWait();
	    }
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // 占쎈�믭옙�뵠�뇡遺용퓠 observable �뵳�딅뮞占쎈뱜 占쎈쑓占쎌뵠占쎄숲�몴占� �빊遺쏙옙占쎈립占쎈뼄.
        resourceTable.setItems(mainApp.getResourceData());
    }

}
