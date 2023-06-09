package application.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.sql.Connection;
import java.sql.SQLException;

public class Controller implements Initializable{
    @FXML
    private TextField textfield1;
    @FXML
    private TextField textfield2;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button button;
    @FXML
    private ChoiceBox<String> choicebox1;
    @FXML
    private ChoiceBox<String> choicebox2;
    @FXML
    private ChoiceBox<String> choicebox3;
    @FXML
    private ChoiceBox<String> choicebox4;
    @FXML
    private ImageView myImageView;
    
    
    private String[] hr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private String[] mn = {"00", "30"};
    String text1;
    String text2;
    String hr_str;
    String mn_str;
    String hr_str2;
    String mn_str2;
    String color;
    String buttonStyle = 
    		"-fx-font-size: 20px;" +
    	    "-fx-font-family: 'Serif';" +
    	    "-fx-font-weight: bold;" +
    	    "-fx-padding: 5px 10px;" +
    	    "-fx-background-color: linear-gradient(to bottom, #2196F3, #64B5F6);" +
    	    "-fx-border-width: 2px;" +
    	    "-fx-border-radius: 30px;" +
    	    "-fx-text-fill: white;" +
    	    "-fx-cursor: hand;" +
    	    "-fx-background-radius: 30px;";
    
    public void closeScene(ActionEvent event, Stage stage) {
	    stage.close();
	}
    public String submit(ActionEvent event) {
    	text1 = textfield1.getText();
    	text2 = textfield2.getText();
    	hr_str = choicebox1.getValue();
    	mn_str = choicebox2.getValue();
    	if(hr_str == null || mn_str == null) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Submit Failed");
            alert.setHeaderText("Start time cannot be empty");
            alert.setContentText("please choose your \"From\" section");
            alert.showAndWait();
    		return "failed";
    	}
    	hr_str2 = choicebox3.getValue();
    	mn_str2 = choicebox4.getValue();
    	if(hr_str2 == null || mn_str2== null) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Submit Failed");
            alert.setHeaderText("End time cannot be empty");
            alert.setContentText("please choose your \"To\" section");
            alert.showAndWait();
    		return "failed";
    	}
    	color = colorPicker.getValue().toString();
    	int hr_int = Integer.parseInt(hr_str);
    	int hr_int2 = Integer.parseInt(hr_str2);
    	int mn_int = Integer.parseInt(mn_str);
    	int mn_int2 = Integer.parseInt(mn_str2);
    	if ((hr_int * 60 + mn_int) >= (hr_int2 * 60 + mn_int2 )) {
            // Display an alert indicating the failure
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Submit Failed");
            alert.setHeaderText("The start time must be earlier then the end time");
            alert.setContentText("please modify your time section");
            alert.showAndWait();
            return "failed"; // Exit the method since submission failed
        }
    	if(text1.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Submit Failed");
            alert.setHeaderText("Mission cannot be empty");
            alert.setContentText("please fill in your mission section");
            alert.showAndWait();
            return "failed"; // Exit the method since submission failed
    	}
    	if(text2.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Submit Failed");
            alert.setHeaderText("Place cannot be empty");
            alert.setContentText("please fill in your place section");
            alert.showAndWait();
            return "failed"; // Exit the method since submission failed
    	}
    	
    	Connection connection = null;
        try
        {
          connection = DriverManager.getConnection("jdbc:sqlite:task_db.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.
          statement.executeUpdate("INSERT INTO task_db (mission, place, hr1, mn1, hr2, mn2, is_deleted, color) VALUES ('" + text1 + "', '" + text2 + "', '" + hr_str + "', '" + mn_str + "', '" + hr_str2 + "', '" + mn_str2 + "', 0, '" + color + "')");
          System.out.println("data added");
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.err.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
        return "success";
    }
    public Button getButton() {
    	return button;
    }
    public String gettext1() {
    	return text1;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		choicebox1.getItems().addAll(hr);
		choicebox2.getItems().addAll(mn);
		choicebox3.getItems().addAll(hr);
		choicebox4.getItems().addAll(mn);
		button.setStyle(buttonStyle);
		myImageView.setVisible(true);
	}
}