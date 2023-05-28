package application.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.Statement;

import application.models.Task;
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
	public void setTask(Task task) {
        this.task = task;
        placeStr = task.getLocation();
        missionStr = task.getDescription();
        time.setText("Time: " + task.getStartTime() + " ~ "+ task.getEndTime());
		place.setText("Location: " + placeStr);
		mission.setText("Task: " + missionStr);
		colorPicker.setValue(task.getColor());
        System.out.println(task.getDescription());
        System.out.println(task.getLocation());
        System.out.println(task.getColor());
        System.out.println(task.getStartTime());
        System.out.println(task.getEndTime());
    }
	public void closeScene(ActionEvent event, Stage stage) {
	    stage.close();
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
