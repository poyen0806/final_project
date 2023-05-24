package application.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private Button button;
    @FXML
    private ChoiceBox<String> choicebox1;
    @FXML
    private ChoiceBox<String> choicebox2;
    @FXML
    private ChoiceBox<String> choicebox3;
    @FXML
    private ChoiceBox<String> choicebox4;
    
    private String[] hr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private String[] mn = {"00", "30"};
    String text1;
    String text2;
    String hr_str;
    String mn_str;
    String hr_str2;
    String mn_str2;
    
    public void closeScene(ActionEvent event, Stage stage) {
	    stage.close();
	}
    public void submit(ActionEvent event) {
    	text1 = textfield1.getText();
    	text2 = textfield2.getText();
    	hr_str = choicebox1.getValue();
    	mn_str = choicebox2.getValue();
    	hr_str2 = choicebox3.getValue();
    	mn_str2 = choicebox4.getValue();
    	Connection connection = null;
        try
        {
          connection = DriverManager.getConnection("jdbc:sqlite:task_db.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.
          statement.executeUpdate("INSERT INTO task_db (mission, place, hr1, mn1, hr2, mn2, is_deleted) VALUES ('" + text1 + "', '" + text2 + "', '" + hr_str + "', '" + mn_str + "', '" + hr_str2 + "', '" + mn_str2 + "', 0)");
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
	}
}