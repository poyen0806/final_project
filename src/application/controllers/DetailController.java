package application.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.Statement;

import application.Main;
import application.models.Task;
import application.views.CalendarAppView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.SQLException;

public class DetailController{
	@FXML
    private Label time;
	@FXML
    private Label place;
	@FXML
    private Label mission;
	@FXML
    private ColorPicker colorPicker;
	@FXML
    private RadioButton deleteButton;
	@FXML
    private Button sendButton;
	
	String placeStr;
	String missionStr;
	String colorStr;
	Task task;
	String labelStyle = 
	"-fx-font-family: 'Serif';" 
	+ "-fx-text-fill: #003366;" 
	+ "-fx-font-size: 20px;" 
	+ "-fx-font-weight: bold;" 
	;
	
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
	
	String buttonStyle2 = 
	"-fx-font-size: 14px;" +
    "-fx-font-family: 'Serif';" +
    "-fx-font-weight: bold;" +
    "-fx-padding: 5px 10px;" +
    "-fx-background-color: transparent;" +
    "-fx-border-width: 2px;" +
    "-fx-border-radius: 30px;" +
    "-fx-text-fill: #003366;" +
    "-fx-border-color: #003366; "+
    "-fx-cursor: hand;" +
    "-fx-background-radius: 30px;";
	
	String colorPickerStyle = 
	"-fx-font-size: 12px;" +
	"-fx-font-family: 'Serif';" +
    "-fx-font-weight: bold;" +
    "-fx-padding: 5px 10px;" +
    "-fx-text-fill: #003366;" ;
	
	public void setTask(Task task) {
        this.task = task;
        placeStr = task.getLocation();
        missionStr = task.getDescription();
        time.setText("Time: " + task.getStartTime() + " ~ "+ task.getEndTime());
        time.setStyle(labelStyle);
		place.setText("Location: " + placeStr);
		place.setStyle(labelStyle);
		mission.setText("Task: " + missionStr);
		mission.setStyle(labelStyle);
		colorPicker.setValue(task.getColor());
		colorPicker.setStyle(colorPickerStyle);
		sendButton.setStyle(buttonStyle);
		deleteButton.setStyle(buttonStyle2);
    }
	public void closeScene(ActionEvent event, Stage stage, Stage calendar) {
	    stage.close();
	    calendar.close();
    	CalendarAppView calendarAppView = new CalendarAppView(stage);
    	Scene scene = new Scene(calendarAppView.getView(), calendarAppView.getWidth(), calendarAppView.getHeight());
    	scene.addEventFilter(KeyEvent.KEY_PRESSED, event2 -> PopupWindow.handleKeyPressed(event2));
    	scene.getStylesheets().add(Main.class.getResource("/application/css/mainpage.css").toExternalForm());
    	stage.setScene(scene);
        stage.setTitle("Task Planner");
        stage.show();
	}
	public Button getButton() {
    	return sendButton;
    }
	public void submit(ActionEvent event) {
		colorStr = colorPicker.getValue().toString();
		Connection connection = null;
        try
        {
          connection = DriverManager.getConnection("jdbc:sqlite:task_db.db");
          Statement statement = connection.createStatement();
            // set timeout to 30 sec.
          if(deleteButton.isSelected()) {
        	  statement.executeUpdate("DELETE FROM task_db WHERE mission = \"" + missionStr + "\" AND place = \"" + placeStr +"\"");
          }
          else {
        	  statement.executeUpdate("UPDATE task_db SET color = \"" + colorStr + "\" WHERE mission = \"" + missionStr + "\" AND place = \"" + placeStr + "\"");
          }
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
}
