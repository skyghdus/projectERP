package com.test.app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
 

public class comboBoxController implements Initializable {
	@FXML public Label la1;
	@FXML public TextField tf1;
	@FXML public ComboBox<String> combox1;
	ObservableList<String> list = FXCollections.observableArrayList("test1", "test2", "test3","test4", "추가");
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		combox1.setItems(list);
	}
	
	public void comboChange(ActionEvent event) {
		if(combox1.getValue()!="추가") {
			la1.setText(combox1.getValue());
		} else {
			combox1.getItems().add((tf1.getText()));
		}
		//la1.setText(combox1.getValue());
	}
	public void buttonAction(ActionEvent event) {
        combox1.getItems().addAll("Ram", "Ben", "Steve", "Ma");
        
    }

}
